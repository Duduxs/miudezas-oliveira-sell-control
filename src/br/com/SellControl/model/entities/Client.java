package br.com.SellControl.model.entities;

public class Client {

	private Integer id;
	private String 	name;
	private String 	cpf;
	private String 	email;
	private String 	phone;
	private String 	cellphone;
	private String 	cep;
	private String 	address;
	private Integer number;
	private String 	complement;
	private String 	neighborhood;
	private String 	city;
	private String 	state;
	
	public Client() {
		
	}
	
	public Client(Integer id, String name, String cpf, String email, String phone, String cellphone, String cep,
			String address, Integer number, String complement, String neighborhood, String city, String state) {
		
		this.id = id;
		this.name = name;
		this.cpf = cpf;
		this.email = email;
		this.phone = phone;
		this.cellphone = cellphone;
		this.cep = cep;
		this.address = address;
		this.number = number;
		this.complement = complement;
		this.neighborhood = neighborhood;
		this.city = city;
		this.state = state;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
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

	public String getCellphone() {
		return cellphone;
	}

	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public String getComplement() {
		return complement;
	}

	public void setComplement(String complement) {
		this.complement = complement;
	}

	public String getNeighborhood() {
		return neighborhood;
	}

	public void setNeighborhood(String neighborhood) {
		this.neighborhood = neighborhood;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Override
	public String toString() {
		return "Clients [id=" + id + ", name=" + name + ", cpf=" + cpf + ", email=" + email + ", phone=" + phone
				+ ", cellphone=" + cellphone + ", cep=" + cep + ", address=" + address + ", number=" + number
				+ ", complement=" + complement + ", neighborhood=" + neighborhood + ", city=" + city + ", state="
				+ state + "]";
	}
	
	
	
	

}
