package in.rajk.model;

public class BmiRequest {
    private int age;
    private String gender;
    private double height; // in cm
    private double weight; // in kg
    
    
	public BmiRequest() {

	}
	public BmiRequest(int age, String gender, double height, double weight) {
		super();
		this.age = age;
		this.gender = gender;
		this.height = height;
		this.weight = weight;
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
	@Override
	public String toString() {
		return "BmiRequest [age=" + age + ", gender=" + gender + ", height=" + height + ", weight=" + weight + "]";
	}

    
}
