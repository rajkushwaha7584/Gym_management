package in.rajk.controller;

import in.rajk.model.Member;
import in.rajk.model.MessageLog;
import in.rajk.repository.MemberRepository;
import in.rajk.repository.MessageLogRepository;
import in.rajk.service.WhatsAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Controller
public class WhatsAppController {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private WhatsAppService whatsAppService;

    @Autowired
    private MessageLogRepository messageLogRepository;

    // ✅ WhatsApp Panel Page
    @GetMapping("/whatsapp-reminder")
    public String showReminderPage() {
        return "whatsapp-reminder";
    }

    // ✅ Manual Trigger for WhatsApp Reminders
    @GetMapping("/send-reminders")
    public String sendExpiryReminders(Model model) {
        int sentCount = sendExpiryRemindersInternal();
        model.addAttribute("message", "📤 " + sentCount + " WhatsApp reminders sent.");
        model.addAttribute("members", memberRepository.findAll());
        return "members";
    }

    // ✅ Scheduled WhatsApp Reminders (every day at 9 AM)
    @Scheduled(cron = "0 0 9 * * *")
    public void sendExpiryRemindersScheduled() {
        sendExpiryRemindersInternal();
    }

    private int sendExpiryRemindersInternal() {
        LocalDate today = LocalDate.now();
        List<Member> members = memberRepository.findAll();
        int sentCount = 0;

        for (Member m : members) {
            LocalDate expiryDate = m.getRegistrationDate().plusMonths(m.getMembershipDuration());
            String phone = formatPhone(m.getPhone());

            if (expiryDate.minusDays(3).isEqual(today)) {
                String msg = "Hi " + m.getFullName() + ", your gym membership will expire on " + expiryDate + ". Please renew it soon.";
                whatsAppService.sendMessage(phone, msg); // ✅ This logs automatically
                sentCount++;
            } else if (expiryDate.isBefore(today)) {
                String msg = "Hi " + m.getFullName() + ", your gym membership expired on " + expiryDate + ". Please renew to continue your fitness journey 💪";
                whatsAppService.sendMessage(phone, msg); // ✅ This logs automatically
                sentCount++;
            }
        }

        return sentCount;
    }


    // ✅ Auto Email for <2 Months Left (daily at 10AM)
    @Scheduled(cron = "0 0 10 * * *")
    public void sendExpiryEmails() {
        LocalDate today = LocalDate.now();
        List<Member> members = memberRepository.findAll();

        for (Member m : members) {
            LocalDate expiryDate = m.getRegistrationDate().plusMonths(m.getMembershipDuration());
            long daysLeft = ChronoUnit.DAYS.between(today, expiryDate);

            if (daysLeft <= 60 && daysLeft > 29) {
                String subject = "⏳ Your GymByRaj Membership is ending soon!";
                String body = String.format("""
                        Dear %s,

                        Your gym membership will expire in about %d days (on %s).
                        Consider renewing now to stay fit and consistent 💪.

                        Regards,
                        GymByRaj Team
                        """, m.getFullName(), daysLeft, expiryDate);

                try {
                    System.out.println("[Email] Reminder sent to: " + m.getEmail());
                } catch (Exception e) {
                    System.err.println("❗ Email failed to: " + m.getEmail() + " | Reason: " + e.getMessage());
                }
            }
        }
    }

    // ✅ Broadcast Message to All Members
    @GetMapping("/broadcast-message")
    public String sendBroadcastMessage(@RequestParam(required = false) String text, Model model) {
        if (text == null || text.isBlank()) {
            text = "Hey GymByRaj! 💪\nDefault broadcast: Stay strong, stay consistent! 🙌";
        }

        List<Member> members = memberRepository.findAll();
        int sentCount = 0;

        for (Member m : members) {
            String phone = formatPhone(m.getPhone());
            try {
                whatsAppService.sendMessage(phone, text);
                sentCount++;
            } catch (Exception e) {
                System.err.println("❌ Failed to send to " + phone + ": " + e.getMessage());
            }
        }

        model.addAttribute("message", "📢 Broadcast sent to " + sentCount + " members!");
        model.addAttribute("members", members);
        return "members";
    }

    // ✅ Test Endpoint
    @GetMapping("/test-whatsapp")
    public String testWhatsAppMessage() {
        whatsAppService.sendMessage("+916264100725", "👋 Hello from GymByRaj! WhatsApp Cloud API is working.");
        return "redirect:/members";
    }

    // ✅ Format Phone to +91 Format
    private String formatPhone(String phone) {
        phone = phone.trim();
        if (!phone.startsWith("+")) {
            if (phone.startsWith("0")) {
                phone = phone.substring(1);
            }
            phone = "+91" + phone;
        }
        return phone;
    }

    @GetMapping("/message-logs")
    public String showMessageLogs(Model model) {
        List<MessageLog> logs = messageLogRepository.findAll(); // ✅ Requires injection
        model.addAttribute("logs", logs);
        return "message-logs"; // ✅ This file must exist
    }

}
