package in.rajk.service;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import in.rajk.model.Member;

public class ExcelExporter {

    private XSSFWorkbook workbook;
    private Sheet sheet;
    private List<Member> memberList;

    public ExcelExporter(List<Member> memberList) {
        this.memberList = memberList;
        workbook = new XSSFWorkbook();
    }

    private void writeHeader() {
        sheet = workbook.createSheet("GymMembers");
        Row row = sheet.createRow(0);

        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        style.setFont(font);

        String[] headers = {
            "ID", "Name", "Email", "Phone", "Age", "Gender", "Height", "Weight",
            "Emergency Contact", "Address", "Membership Duration", "Payment Mode",
            "Payment Status", "Amount Paid", "Registration Date"
        };

        for (int i = 0; i < headers.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(style);
        }
    }

    private void writeData() {
        int rowCount = 1;

        for (Member m : memberList) {
            Row row = sheet.createRow(rowCount++);
            int col = 0;

            row.createCell(col++).setCellValue(m.getId());
            row.createCell(col++).setCellValue(m.getFullName());
            row.createCell(col++).setCellValue(m.getEmail());
            row.createCell(col++).setCellValue(m.getPhone());
            row.createCell(col++).setCellValue(m.getAge());
            row.createCell(col++).setCellValue(m.getGender());
            row.createCell(col++).setCellValue(m.getHeight());
            row.createCell(col++).setCellValue(m.getWeight());
            row.createCell(col++).setCellValue(m.getEmergencyContact());
            row.createCell(col++).setCellValue(m.getAddress());
            row.createCell(col++).setCellValue(m.getMembershipDuration());
            row.createCell(col++).setCellValue(m.getPaymentMode());
            row.createCell(col++).setCellValue(m.isPaymentStatus() ? "Paid" : "Unpaid");
            row.createCell(col++).setCellValue(m.getAmountPaid());
            row.createCell(col++).setCellValue(m.getRegistrationDate().toString());
        }
    }

    public void export(HttpServletResponse response) throws IOException {
        writeHeader();
        writeData();

        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }
}
