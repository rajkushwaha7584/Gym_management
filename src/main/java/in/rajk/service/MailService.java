package in.rajk.service;

import in.rajk.controller.PdfGenerator;
import in.rajk.model.Member;
import jakarta.activation.DataSource;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.util.ByteArrayDataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class MailService {

    @Autowired
    private JavaMailSender mailSender;

    /**
     * Sends welcome credentials email to new member with PDF attachment.
     */
    public void sendCredentialsEmail(String to, String name, String email, String password, Member member) {
        String subject = "🎉 Welcome to GymByRaj - Membership Confirmation";
        String formattedDate = member.getRegistrationDate().format(DateTimeFormatter.ofPattern("dd MMM yyyy"));
        String logoUrl = "https://yourdomain.com/images/gym_logo.png"; // Change this URL

        String htmlContent = "<html><head><style>" +
                "body { font-family: 'Segoe UI', sans-serif; background: #f2f2f2; }" +
                ".container { max-width: 600px; margin: 20px auto; background: #fff; border-radius: 8px;" +
                " box-shadow: 0 0 10px rgba(0,0,0,0.1); overflow: hidden; }" +
                ".header { background: #0d6efd; color: white; text-align: center; padding: 20px; }" +
                ".logo { max-height: 80px; } .content { padding: 20px; color: #333; }" +
                ".content h3 { color: #0d6efd; } .info { margin: 8px 0; } .label { font-weight: bold; }" +
                ".footer { background: #f0f0f0; padding: 10px; text-align: center; font-size: 0.9em; color: #777; }" +
                "</style></head><body>" +
                "<div class='container'>" +
                "<div class='header'><img src='" + logoUrl + "' class='logo' /><h2>Welcome to GymByRaj 💪</h2></div>" +
                "<div class='content'><p>Hello <strong>" + name + "</strong>,</p>" +
                "<p>Thank you for registering with us! Here are your login and membership details:</p>" +
                "<h3>🔐 Login Info</h3>" +
                "<div class='info'><span class='label'>Email:</span> " + email + "</div>" +
                "<div class='info'><span class='label'>Password:</span> " + password + "</div>" +
                "<h3>📋 Membership Details</h3>" +
                "<div class='info'><span class='label'>Gender:</span> " + member.getGender() + "</div>" +
                "<div class='info'><span class='label'>Age:</span> " + member.getAge() + " years</div>" +
                "<div class='info'><span class='label'>Height:</span> " + member.getHeight() + " cm</div>" +
                "<div class='info'><span class='label'>Weight:</span> " + member.getWeight() + " kg</div>" +
                "<div class='info'><span class='label'>Payment Date:</span> " + formattedDate + "</div>" +
                "<div class='info'><span class='label'>Amount Paid:</span> ₹" + member.getAmountPaid() + "</div>" +
                "<div class='info'><span class='label'>Duration:</span> " + member.getMembershipDuration() + " month(s)</div>" +
                "<p>Please keep this email safe. If you have any questions, contact our team anytime.</p></div>" +
                "<div class='footer'>© " + LocalDate.now().getYear() + " GymByRaj. Stay Strong. Stay Fit. 💪</div>" +
                "</div></body></html>";

        sendMailWithPdf(to, subject, htmlContent, member, password);
    }

    /**
     * Sends update notification to existing member.
     */
    public void sendUpdateNotificationEmail(String to, String name, Member member, String newPassword) {
        String subject = "🔄 Your Gym Membership Details Have Been Updated";
        String formattedDate = member.getRegistrationDate().format(DateTimeFormatter.ofPattern("dd MMM yyyy"));

        StringBuilder html = new StringBuilder();
        html.append("<html><body style='font-family: Arial, sans-serif; padding: 20px;'>")
            .append("<h2 style='color:#1e90ff;'>Hello ").append(name).append(",</h2>")
            .append("<p>This is to notify you that your gym membership details have been updated.</p>")
            .append("<h4>📝 Updated Details:</h4><ul>")
            .append("<li><strong>Gender:</strong> ").append(member.getGender()).append("</li>")
            .append("<li><strong>Age:</strong> ").append(member.getAge()).append("</li>")
            .append("<li><strong>Height:</strong> ").append(member.getHeight()).append(" cm</li>")
            .append("<li><strong>Weight:</strong> ").append(member.getWeight()).append(" kg</li>")
            .append("<li><strong>Emergency Contact:</strong> ").append(member.getEmergencyContact()).append("</li>")
            .append("<li><strong>Address:</strong> ").append(member.getAddress()).append("</li>")
            .append("<li><strong>Payment Mode:</strong> ").append(member.getPaymentMode()).append("</li>")
            .append("<li><strong>Amount Paid:</strong> ₹").append(member.getAmountPaid()).append("</li>")
            .append("<li><strong>Membership Duration:</strong> ").append(member.getMembershipDuration()).append(" month(s)</li>")
            .append("<li><strong>Registration Date:</strong> ").append(formattedDate).append("</li>");

        if (newPassword != null && !newPassword.isBlank()) {
            html.append("<li><strong>🔐 New Password:</strong> ").append(newPassword).append("</li>");
        }

        html.append("</ul>")
            .append("<p>If you did not request this change, please contact our team immediately.</p>")
            .append("<p style='margin-top:30px;'>Stay Fit 💪<br/>– GymByRaj</p>")
            .append("</body></html>");

        String pdfPassword = newPassword != null ? newPassword : member.getPassword();
        sendMailWithPdf(to, subject, html.toString(), member, pdfPassword);
    }

    /**
     * Sends HTML email with PDF attachment using retry if mail fails.
     */
    @Retryable(
        value = MessagingException.class,
        maxAttempts = 3,
        backoff = @Backoff(delay = 2000)
    )
    private void sendMailWithPdf(String to, String subject, String htmlContent, Member member, String rawPassword) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlContent, true);

            // PDF with masked password
            String masked = PdfGenerator.maskPassword(rawPassword);
            byte[] pdfBytes = PdfGenerator.generateMembershipPdf(member, masked);
            DataSource pdfAttachment = new ByteArrayDataSource(pdfBytes, "application/pdf");

            helper.addAttachment("MembershipDetails.pdf", pdfAttachment);

            mailSender.send(message);
            System.out.println("✅ Email sent to: " + to);
        } catch (Exception e) {
            System.err.println("❌ Failed to send email to: " + to + " - " + e.getMessage());
        }
    }

    /**
     * Sends reminder email to member before expiry.
     */
    public void sendReminderEmail(Member member, int daysLeft) {
        String to = member.getEmail();
        String subject = "⏰ Your Gym Membership expires in " + daysLeft + " day(s)";
        String body = "<h3>Hi " + member.getFullName() + ",</h3>" +
                "<p>This is a reminder that your GymByRaj membership will expire in <b>" + daysLeft + " day(s)</b>.</p>" +
                "<ul><li><strong>Phone:</strong> " + member.getPhone() + "</li>" +
                "<li><strong>Expiry Date:</strong> " + member.getExpiryDate() + "</li></ul>" +
                "<p>Please renew your membership to stay fit and active!</p>" +
              //  "<a href='https://gymbyraj.in/renew' style='background:#007bff;color:#fff;padding:10px 20px;border-radius:5px;text-decoration:none;'>Renew Now</a>" +
                "<br><br><p>Regards,<br><b>GymByRaj</b></p>";

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body, true);
            mailSender.send(message);
            System.out.println("📧 Reminder sent to: " + to);
        } catch (MessagingException e) {
            System.err.println("❌ Reminder email failed to: " + to + " - " + e.getMessage());
        }
    }

    /**
     * Sends backup ZIP file to admin email.
     */
    public void sendBackupZipToAdmin(Path zipPath) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo("raj24kush@gmail.com");
            helper.setSubject("📦 Weekly GymByRaj Data Backup");
            helper.setText("Attached is the auto-generated weekly backup ZIP.\n\nRegards,\nGymByRaj System");

            FileSystemResource file = new FileSystemResource(zipPath.toFile());
            helper.addAttachment(zipPath.getFileName().toString(), file);

            mailSender.send(message);
            System.out.println("✅ Weekly backup email sent.");
        } catch (Exception e) {
            System.err.println("❌ Failed to send backup email: " + e.getMessage());
        }
    }
}
