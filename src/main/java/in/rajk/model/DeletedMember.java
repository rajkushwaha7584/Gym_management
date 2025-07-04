package in.rajk.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class DeletedMember {

    @Id
    private Long id;

    private String fullName;
    private String email;
    private String gender;
    private int age;
    private double height;
    private double weight;
    private String address;
    private String mobileNumber;
    private LocalDate registrationDate;
    private int membershipDuration;
    private double amountPaid;

    private LocalDate deletedOn;

	public DeletedMember() {
	
	}

	public DeletedMember(Long id, String fullName, String email, String gender, int age, double height, double weight,
			String address, String mobileNumber, LocalDate registrationDate, int membershipDuration, double amountPaid,
			LocalDate deletedOn) {
		super();
		this.id = id;
		this.fullName = fullName;
		this.email = email;
		this.gender = gender;
		this.age = age;
		this.height = height;
		this.weight = weight;
		this.address = address;
		this.mobileNumber = mobileNumber;
		this.registrationDate = registrationDate;
		this.membershipDuration = membershipDuration;
		this.amountPaid = amountPaid;
		this.deletedOn = deletedOn;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
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

	public double getAmountPaid() {
		return amountPaid;
	}

	public void setAmountPaid(double amountPaid) {
		this.amountPaid = amountPaid;
	}

	public LocalDate getDeletedOn() {
		return deletedOn;
	}

	public void setDeletedOn(LocalDate deletedOn) {
		this.deletedOn = deletedOn;
	}

	@Override
	public String toString() {
		return "DeletedMember [id=" + id + ", fullName=" + fullName + ", email=" + email + ", gender=" + gender
				+ ", age=" + age + ", height=" + height + ", weight=" + weight + ", address=" + address
				+ ", mobileNumber=" + mobileNumber + ", registrationDate=" + registrationDate + ", membershipDuration="
				+ membershipDuration + ", amountPaid=" + amountPaid + ", deletedOn=" + deletedOn + "]";
	}

    
}
