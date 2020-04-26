package br.com.SellControl.model.entities;

public class Employee extends Client {

	private String password;
	private String office;
	private String acess_level;

	public Employee() {

	}

	public Employee(Integer id, String name, String cpf, String email, String password, String office,
			String acess_level, String phone, String cellphone, String cep, String address, Integer number,
			String complement, String neighborhood, String city, String state) {
		
		this.setId(id);
		this.setName(name);
		this.setCpf(cpf);
		this.setEmail(email);
		this.password = password;
		this.office = office;
		this.acess_level = acess_level;
		this.setPhone(phone);
		this.setCellphone(cellphone);
		this.setCep(cep);
		this.setAddress(address);
		this.setNumber(number);
		this.setComplement(complement);
		this.setNeighborhood(neighborhood);
		this.setCity(city);
		this.setState(state);
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getOffice() {
		return office;
	}

	public void setOffice(String office) {
		this.office = office;
	}

	public String getAcess_level() {
		return acess_level;
	}

	public void setAcess_level(String acess_level) {
		this.acess_level = acess_level;
	}

}
