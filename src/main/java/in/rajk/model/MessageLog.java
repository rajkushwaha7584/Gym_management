package in.rajk.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class MessageLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String memberName;
    private String email;
    private String phone;

    @Column(length = 1500)
    private String messageContent;

    private boolean success;
    @Column(length = 65535, columnDefinition = "TEXT")
    private String errorMessage;
    private LocalDateTime timestamp;

    // ✅ No-arg constructor (needed)
    public MessageLog() {
    }

    // ✅ Optionally add parameterized constructors, getters, and setters
    // You can use Lombok if preferred
    public Long getId() {
        return id;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    
	public MessageLog(Long id, String memberName, String email, String phone, String messageContent, boolean success,
			String errorMessage, LocalDateTime timestamp) {
		super();
		this.id = id;
		this.memberName = memberName;
		this.email = email;
		this.phone = phone;
		this.messageContent = messageContent;
		this.success = success;
		this.errorMessage = errorMessage;
		this.timestamp = timestamp;
	}

	@Override
	public String toString() {
		return "MessageLog [id=" + id + ", memberName=" + memberName + ", email=" + email + ", phone=" + phone
				+ ", messageContent=" + messageContent + ", success=" + success + ", errorMessage=" + errorMessage
				+ ", timestamp=" + timestamp + "]";
	}
    
    
}
