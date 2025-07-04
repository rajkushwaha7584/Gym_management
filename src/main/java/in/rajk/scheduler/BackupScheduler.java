package in.rajk.scheduler;

import in.rajk.service.CSVExportService;
import in.rajk.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.nio.file.Path;

@Component
public class BackupScheduler {

    @Autowired
    private CSVExportService exportService;

    @Autowired
    private MailService mailService;

    /**
     * Scheduled to run every Saturday at 1:00 AM.
     */
   // @Scheduled(cron = "0 * * * * *") // every minute
    @Scheduled(cron = "0 0 1 ? * SAT")
    public void generateWeeklyBackup() {
        try {
            Path path = exportService.exportAllDataAsZip();
            System.out.println("✅ Weekly backup ZIP created: " + path);

            mailService.sendBackupZipToAdmin(path);
        } catch (Exception e) {
            System.err.println("❌ Failed to create or send weekly backup: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
