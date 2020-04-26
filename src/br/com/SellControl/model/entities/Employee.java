package br.com.SellControl.model.entities;

public class Employee extends Client {

	
	private String password;
	private String office;
	private String acess_level;

	
	public Employee() {
		
	}

	public Employee(String password, String office, String acess_level) {
		this.password = password;
		this.office = office;
		this.acess_level = acess_level;
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
