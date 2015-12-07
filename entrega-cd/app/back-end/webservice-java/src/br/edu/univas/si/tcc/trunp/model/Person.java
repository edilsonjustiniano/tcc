package br.edu.univas.si.tcc.trunp.model;

public class Person {

	private String name;
	private String email;
	private String password;
	private String typeOfAccount;
	private String typeOfPerson;
	private String district;
	private String address;
	private String photo;
	private City livesIn;
	private Company worksIn;

	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getTypeOfAccount() {
		return typeOfAccount;
	}

	public void setTypeOfAccount(String typeOfAccount) {
		this.typeOfAccount = typeOfAccount;
	}

	public String getTypeOfPerson() {
		return typeOfPerson;
	}

	public void setTypeOfPerson(String typeOfPerson) {
		this.typeOfPerson = typeOfPerson;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public City getLivesIn() {
		return livesIn;
	}

	public void setLivesIn(City livesIn) {
		this.livesIn = livesIn;
	}

	public Company getWorksIn() {
		return worksIn;
	}

	public void setWorksIn(Company worksIn) {
		this.worksIn = worksIn;
	}

}
