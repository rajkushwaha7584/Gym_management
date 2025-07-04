package in.rajk.repository;

import in.rajk.model.Member;
import in.rajk.model.ReminderLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface ReminderLogRepository extends JpaRepository<ReminderLog, Long> {
    Optional<ReminderLog> findByMemberAndReminderDate(Member member, LocalDate reminderDate);
}
