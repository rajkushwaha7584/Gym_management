package in.rajk.controller;

import in.rajk.model.Member;
import in.rajk.repository.MemberRepository;
import in.rajk.service.ExcelExporter;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.util.List;

@Controller
public class ExportController {

    @Autowired
    private MemberRepository memberRepository;

    @GetMapping("/export/excel")
    @PreAuthorize("hasRole('ADMIN')")
    public void exportToExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=gym_members.xlsx";
        response.setHeader(headerKey, headerValue);

        List<Member> listMembers = memberRepository.findAll();
        ExcelExporter excelExporter = new ExcelExporter(listMembers);
        excelExporter.export(response);
    }
}
