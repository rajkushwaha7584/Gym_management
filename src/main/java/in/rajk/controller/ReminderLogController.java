package in.rajk.controller;

import in.rajk.model.ReminderLog;
import in.rajk.repository.ReminderLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin/reminders")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class ReminderLogController {

    @Autowired
    private ReminderLogRepository reminderLogRepository;

    @GetMapping
    public String viewLogs(Model model) {
        List<ReminderLog> logs = reminderLogRepository.findAll();
        model.addAttribute("logs", logs);
        return "admin/reminders"; // reminders.html
    }
}
