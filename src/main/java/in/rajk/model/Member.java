package in.rajk.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Full name is required")
    private String fullName;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "\\d{10}", message = "Phone number must be 10 digits")
    private String phone;

    @NotBlank(message = "Email is required")
    @Email(message = "Enter a valid email")
    private String email;

    private LocalDate registrationDate;

    @Min(value = 1, message = "Duration must be at least 1 month")
    private int membershipDuration; // in months

    private boolean paymentStatus; // true = paid, false = unpaid
    private LocalDate expiryDate;
    // New Fields
    private String gender;
    private int age;
    private double height; // in cm
    private double weight; // in kg
    private String emergencyContact;
    private String address;
    private String paymentMode; // Online/Offline
    private double amountPaid;
    private String profilePicture; // Store the image filename
    
    //security
    @NotBlank(message = "Password is required")
    private String password;

    private String role = "ROLE_MEMBER"; // default role (other = "ROLE_ADMIN")
    
    public Member() {
    }

	public Member(Long id, @NotBlank(message = "Full name is required") String fullName,
			@NotBlank(message = "Phone number is required") @Pattern(regexp = "\\d{10}", message = "Phone number must be 10 digits") String phone,
			@NotBlank(message = "Email is required") @Email(message = "Enter a valid email") String email,
			LocalDate registrationDate,
			@Min(value = 1, message = "Duration must be at least 1 month") int membershipDuration,
			boolean paymentStatus, String gender, int age, double height, double weight, String emergencyContact,
			String address, String paymentMode, double amountPaid, String profilePicture,
			@NotBlank(message = "Password is required") String password, String role) {
		super();
		this.id = id;
		this.fullName = fullName;
		this.phone = phone;
		this.email = email;
		this.registrationDate = registrationDate;
		this.membershipDuration = membershipDuration;
		this.paymentStatus = paymentStatus;
		this.gender = gender;
		this.age = age;
		this.height = height;
		this.weight = weight;
		this.emergencyContact = emergencyContact;
		this.address = address;
		this.paymentMode = paymentMode;
		this.amountPaid = amountPaid;
		this.profilePicture = profilePicture;
		this.password = password;
		this.role = role;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public LocalDate getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(LocalDate registrationDate) {
		this.registrationDate = registrationDate;
	}

	public int getMembershipDuration() {
		return membershipDuration;
	}

	public void setMembershipDuration(int membershipDuration) {
		this.membershipDuration = membershipDuration;
	}

	public boolean isPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(boolean paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public String getEmergencyContact() {
		return emergencyContact;
	}

	public void setEmergencyContact(String emergencyContact) {
		this.emergencyContact = emergencyContact;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}

	public double getAmountPaid() {
		return amountPaid;
	}

	public void setAmountPaid(double amountPaid) {
		this.amountPaid = amountPaid;
	}

	public String getProfilePicture() {
		return profilePicture;
	}

	public void setProfilePicture(String profilePicture) {
		this.profilePicture = profilePicture;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

	@Override
	public String toString() {
		return "Member [id=" + id + ", fullName=" + fullName + ", phone=" + phone + ", email=" + email
				+ ", registrationDate=" + registrationDate + ", membershipDuration=" + membershipDuration
				+ ", paymentStatus=" + paymentStatus + ", expiryDate=" + expiryDate + ", gender=" + gender + ", age="
				+ age + ", height=" + height + ", weight=" + weight + ", emergencyContact=" + emergencyContact
				+ ", address=" + address + ", paymentMode=" + paymentMode + ", amountPaid=" + amountPaid
				+ ", profilePicture=" + profilePicture + ", password=" + password + ", role=" + role + "]";
	}

    

}
