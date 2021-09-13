package bean;

public class member {
	private int id;
	private String name;
	private String phone;
	private boolean sex;
	public member(int id, String name, String phone, boolean sex) {
		super();
		this.id = id;
		this.name = name;
		this.phone = phone;
		this.sex = sex;
	}
	public member() {
		super();
	}
	public int getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public String getPhone() {
		return phone;
	}
	public boolean isSex() {
		return sex;
	}
	
	
}
