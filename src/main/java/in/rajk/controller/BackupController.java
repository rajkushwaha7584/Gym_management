package in.rajk.controller;

import in.rajk.service.CSVExportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.PathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.nio.file.Path;

@Controller
public class BackupController {

    @Autowired
    private CSVExportService exportService;

    // ✅ Admin can download ZIP file backup directly
    @GetMapping("/admin/download-backup")
    public ResponseEntity<Resource> downloadBackup() throws IOException {
        Path zipPath = exportService.exportAllDataAsZip();
        Resource resource = new PathResource(zipPath);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + zipPath.getFileName().toString() + "\"")
                .body(resource);
    }

    // ✅ Backup trigger (just creates ZIP & returns location)
    @GetMapping("/export/backup")
    @ResponseBody
    public String exportBackup() {
        try {
            Path zip = exportService.exportAllDataAsZip();
            return "✅ Backup created at: " + zip.toAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
            return "❌ Failed to export backup: " + e.getMessage();
        }
    }
}
