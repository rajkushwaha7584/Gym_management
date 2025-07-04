package in.rajk.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class BmiRecord {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int age;
    private String gender;
    private double height;
    private double weight;
    private double bmi;
    private String category;
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    

	public BmiRecord() {
		
	}
	public BmiRecord(Long id, int age, String gender, double height, double weight, double bmi, String category,
			LocalDate date, Member member) {
		super();
		this.id = id;
		this.age = age;
		this.gender = gender;
		this.height = height;
		this.weight = weight;
		this.bmi = bmi;
		this.category = category;
		this.date = date;
		this.member = member;
	}
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
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

	public double getBmi() {
		return bmi;
	}

	public void setBmi(double bmi) {
		this.bmi = bmi;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}


	@Override
	public String toString() {
		return "BmiRecord [id=" + id + ", age=" + age + ", gender=" + gender + ", height=" + height + ", weight="
				+ weight + ", bmi=" + bmi + ", category=" + category + ", date=" + date + ", member=" + member + "]";
	}


    
}
