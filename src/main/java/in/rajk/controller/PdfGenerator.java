package in.rajk.controller;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import in.rajk.model.Member;
import org.springframework.core.io.ClassPathResource;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.time.format.DateTimeFormatter;

public class PdfGenerator {

    public static byte[] generateMembershipPdf(Member member, String maskedPassword) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Document document = new Document(PageSize.A4, 36, 36, 36, 36);

        try {
            PdfWriter writer = PdfWriter.getInstance(document, out);
            document.open();

            // Set Background Color
            PdfContentByte canvas = writer.getDirectContentUnder();
            Rectangle rect = new Rectangle(PageSize.A4);
            rect.setBackgroundColor(new Color(255, 248, 220)); // light cream
            canvas.rectangle(rect);

            // Load Logo
            try {
                ClassPathResource logoFile = new ClassPathResource("static/images/gym_logo.png");
                Image logo = Image.getInstance(logoFile.getInputStream().readAllBytes());
                logo.scaleToFit(80, 80);
                logo.setAlignment(Image.ALIGN_CENTER);
                document.add(logo);
                document.add(new Paragraph(" "));
            } catch (Exception e) {
                System.out.println("Logo not found: " + e.getMessage());
            }

            // Title
            com.lowagie.text.Font titleFont = new com.lowagie.text.Font(com.lowagie.text.Font.HELVETICA, 24, com.lowagie.text.Font.BOLD, new Color(0, 102, 204));
            Paragraph title = new Paragraph("\uD83C\uDFCB\uFE0F GymByRaj Membership Summary \uD83D\uDCAA", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(20f);
            document.add(title);

            // Table
            PdfPTable table = new PdfPTable(2);
            table.setWidthPercentage(100);
            table.setSpacingBefore(10f);
            table.setSpacingAfter(10f);
            table.setWidths(new float[]{2f, 5f});

            com.lowagie.text.Font labelFont = new com.lowagie.text.Font(com.lowagie.text.Font.HELVETICA, 12, com.lowagie.text.Font.BOLD, Color.DARK_GRAY);
            com.lowagie.text.Font valueFont = new com.lowagie.text.Font(com.lowagie.text.Font.HELVETICA, 12, com.lowagie.text.Font.NORMAL, Color.BLACK);

            addRow(table, "\uD83D\uDC64 Full Name", member.getFullName(), labelFont, valueFont);
            addRow(table, "\uD83D\uDC64 Email", member.getEmail(), labelFont, valueFont);
            addRow(table, "\uD83D\uDD11 Password", maskedPassword, labelFont, valueFont);
            addRow(table, "\u2642\uFE0F Gender", member.getGender(), labelFont, valueFont);
            addRow(table, "\uD83D\uDC76 Age", member.getAge() + " years", labelFont, valueFont);
            addRow(table, "\uD83C\uDFC3 Height", member.getHeight() + " cm", labelFont, valueFont);
            addRow(table, "\uD83D\uDCAA Weight", member.getWeight() + " kg", labelFont, valueFont);
            addRow(table, "\u26A0\uFE0F Emergency Contact", member.getEmergencyContact(), labelFont, valueFont);
            addRow(table, "\uD83C\uDFE0 Address", member.getAddress(), labelFont, valueFont);
            addRow(table, "\uD83D\uDCB3 Payment Mode", member.getPaymentMode(), labelFont, valueFont);
            addRow(table, "\u20B9 Amount Paid", "\u20B9 " + member.getAmountPaid(), labelFont, valueFont);
            addRow(table, "\uD83D\uDCC5 Registration Date",
                    member.getRegistrationDate().format(DateTimeFormatter.ofPattern("dd MMM yyyy")), labelFont, valueFont);
            addRow(table, "\uD83D\uDD5B Membership Duration", member.getMembershipDuration() + " months", labelFont, valueFont);

            document.add(table);

            // Footer
            com.lowagie.text.Font footerFont = new com.lowagie.text.Font(com.lowagie.text.Font.HELVETICA, 12, com.lowagie.text.Font.ITALIC, Color.GRAY);
            Paragraph footer = new Paragraph("\u2728 Thank you for being part of GymByRaj! Stay Fit \uD83D\uDCAA", footerFont);
            footer.setAlignment(Element.ALIGN_CENTER);
            footer.setSpacingBefore(30f);
            document.add(footer);

            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return out.toByteArray();
    }

    private static void addRow(PdfPTable table, String label, String value, com.lowagie.text.Font labelFont, com.lowagie.text.Font valueFont) {
        PdfPCell cell1 = new PdfPCell(new Phrase(label, labelFont));
        PdfPCell cell2 = new PdfPCell(new Phrase(value, valueFont));

        cell1.setBorderColor(Color.LIGHT_GRAY);
        cell2.setBorderColor(Color.LIGHT_GRAY);
        cell1.setPadding(8);
        cell2.setPadding(8);
        cell1.setBackgroundColor(new Color(224, 255, 255)); // LightCyan
        cell2.setBackgroundColor(new Color(248, 248, 255)); // GhostWhite

        table.addCell(cell1);
        table.addCell(cell2);
    }

    public static String maskPassword(String password) {
        if (password == null || password.length() < 4) {
            return "******";
        }
        return password.substring(0, 2) + "****" + password.substring(password.length() - 2);
    }
}
