package in.rajk.controller;

import in.rajk.scheduler.ReminderScheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReminderTestController {

    @Autowired
    private ReminderScheduler reminderScheduler;

    @GetMapping("/test/reminder")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String triggerExpiryReminder() {
        reminderScheduler.sendExpiryReminders();
        return "✅ Reminder triggered manually. Check your email console.";
    }

}
