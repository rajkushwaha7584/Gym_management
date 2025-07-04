package in.rajk.service;

import in.rajk.model.*;
import in.rajk.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
public class CSVExportService {

    @Autowired private MemberRepository memberRepo;
    @Autowired private BmiRecordRepository bmiRepo;
    @Autowired private DeletedMemberRepository deletedRepo;
    @Autowired private MessageLogRepository messageLogRepo;

    /**
     * Main method to create a ZIP backup and return the path.
     */
    public Path exportAllDataAsZip() throws IOException {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        Path exportFolder = Paths.get("export_tmp/" + timestamp);
        Files.createDirectories(exportFolder);

        exportMembers(exportFolder.resolve("members.csv"));
        exportBMI(exportFolder.resolve("bmi_history.csv"));
        exportDeleted(exportFolder.resolve("deleted_members.csv"));
        exportLogs(exportFolder.resolve("message_logs.csv"));

        String zipFileName = "backup_" + timestamp + ".zip";
        Path zipPath = exportFolder.resolve(zipFileName);

        try (ZipOutputStream zos = new ZipOutputStream(Files.newOutputStream(zipPath))) {
            Files.list(exportFolder)
                .filter(path -> !path.getFileName().toString().endsWith(".zip"))
                .forEach(file -> {
                    try {
                        zos.putNextEntry(new ZipEntry(file.getFileName().toString()));
                        Files.copy(file, zos);
                        zos.closeEntry();
                    } catch (IOException e) {
                        System.err.println("❌ Error zipping file: " + file.getFileName());
                        e.printStackTrace();
                    }
                });
        }

        System.out.println("✅ ZIP Backup created: " + zipPath);
        return zipPath;
    }

    /**
     * Clean old folders (older than 7 days) and create new ZIP backup.
     */
    public Path exportAllDataAsZipAndCleanOld() throws IOException {
        Path exportRoot = Paths.get("export_tmp");

        if (Files.exists(exportRoot)) {
            Files.list(exportRoot)
                .filter(Files::isDirectory)
                .filter(dir -> {
                    try {
                        return Files.getLastModifiedTime(dir).toMillis()
                                < System.currentTimeMillis() - 7 * 24 * 60 * 60 * 1000L;
                    } catch (IOException e) {
                        return false;
                    }
                })
                .forEach(dir -> {
                    try {
                        Files.walk(dir)
                            .sorted((a, b) -> b.compareTo(a)) // delete children first
                            .forEach(path -> {
                                try {
                                    Files.deleteIfExists(path);
                                } catch (IOException ignored) {}
                            });
                        System.out.println("🧹 Deleted old backup folder: " + dir);
                    } catch (IOException e) {
                        System.err.println("❌ Failed to delete old backup: " + dir);
                    }
                });
        }

        return exportAllDataAsZip();
    }

    private void exportMembers(Path filePath) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath.toFile()))) {
            writer.println("ID,Name,Phone,Email,JoinDate,ExpiryDate");
            for (Member m : memberRepo.findAll()) {
                writer.printf("%d,%s,%s,%s,%s,%s%n",
                        m.getId(), m.getFullName(), m.getPhone(), m.getEmail(),
                        m.getRegistrationDate(), m.getExpiryDate());
            }
        }
    }

    private void exportBMI(Path filePath) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath.toFile()))) {
            writer.println("Date,Age,Gender,Height,Weight,BMI,Category,MemberId");
            for (BmiRecord r : bmiRepo.findAll()) {
                writer.printf("%s,%d,%s,%.2f,%.2f,%.2f,%s,%d%n",
                        r.getDate(), r.getAge(), r.getGender(),
                        r.getHeight(), r.getWeight(), r.getBmi(),
                        r.getCategory(), r.getMember().getId());
            }
        }
    }

    private void exportDeleted(Path filePath) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath.toFile()))) {
            writer.println("ID,Full Name,Email,Mobile Number,Deleted On");
            for (DeletedMember d : deletedRepo.findAll()) {
                writer.printf("%d,%s,%s,%s,%s%n",
                        d.getId(),
                        d.getFullName(),
                        d.getEmail(),
                        d.getMobileNumber(),
                        d.getDeletedOn());
            }
        }
    }

    private void exportLogs(Path filePath) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath.toFile()))) {
            writer.println("ID,Member Name,Phone,Email,Message,Status,Timestamp");
            for (MessageLog l : messageLogRepo.findAll()) {
                writer.printf("%d,%s,%s,%s,%s,%s,%s%n",
                        l.getId(),
                        l.getMemberName(),
                        l.getPhone(),
                        l.getEmail(),
                        escape(l.getMessageContent()),
                        l.isSuccess() ? "SUCCESS" : "FAILED",
                        l.getTimestamp());
            }
        }
    }

    /**
     * Escape line breaks and commas from message content for safe CSV
     */
    private String escape(String text) {
        if (text == null) return "";
        return "\"" + text.replace("\"", "\"\"") + "\"";
    }
}
