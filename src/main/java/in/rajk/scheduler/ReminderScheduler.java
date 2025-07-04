package in.rajk.scheduler;

import in.rajk.model.Member;
import in.rajk.model.ReminderLog;
import in.rajk.repository.MemberRepository;
import in.rajk.repository.ReminderLogRepository;
import in.rajk.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Component
public class ReminderScheduler {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private ReminderLogRepository reminderLogRepository;

    @Autowired
    private MailService mailService;

  //@Scheduled(fixedRate = 60000) // every 60 seconds
    @Scheduled(cron = "0 0 10 * * *") // Every day at 10:00 AM
    public void sendExpiryReminders() {
        LocalDate today = LocalDate.now();
        List<Integer> notifyDays = Arrays.asList(7, 5, 3, 1);

        List<Member> members = memberRepository.findAll();

        for (Member member : members) {
            if (member.getExpiryDate() != null) {
                long daysLeft = java.time.temporal.ChronoUnit.DAYS.between(today, member.getExpiryDate());

                if (notifyDays.contains((int) daysLeft)) {
                    // check if reminder already sent today
                    boolean alreadySent = reminderLogRepository
                            .findByMemberAndReminderDate(member, today)
                            .isPresent();

                    if (!alreadySent) {
                        mailService.sendReminderEmail(member, (int) daysLeft);

                        // log reminder
                        ReminderLog log = new ReminderLog(member, today, (int) daysLeft);
                        reminderLogRepository.save(log);
                    }
                }
            }
        }
    }

}
