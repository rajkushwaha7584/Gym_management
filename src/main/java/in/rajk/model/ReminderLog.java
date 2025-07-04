package in.rajk.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "reminder_log", uniqueConstraints = @UniqueConstraint(columnNames = {"member_id", "reminder_date"}))
public class ReminderLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(name = "reminder_date", nullable = false)
    private LocalDate reminderDate;

    @Column(name = "days_before_expiry")
    private int daysBeforeExpiry;

    // Constructors
    public ReminderLog() {}

    public ReminderLog(Member member, LocalDate date, int daysBeforeExpiry) {
        this.member = member;
        this.reminderDate = date;
        this.daysBeforeExpiry = daysBeforeExpiry;
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public LocalDate getReminderDate() {
		return reminderDate;
	}

	public void setReminderDate(LocalDate reminderDate) {
		this.reminderDate = reminderDate;
	}

	public int getDaysBeforeExpiry() {
		return daysBeforeExpiry;
	}

	public void setDaysBeforeExpiry(int daysBeforeExpiry) {
		this.daysBeforeExpiry = daysBeforeExpiry;
	}

	@Override
	public String toString() {
		return "ReminderLog [id=" + id + ", member=" + member + ", reminderDate=" + reminderDate + ", daysBeforeExpiry="
				+ daysBeforeExpiry + "]";
	}


    
}
