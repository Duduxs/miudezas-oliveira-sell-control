package br.com.SellControl.model.entities;

public class Provider extends Client {

	private String cnpj;

	public Provider() {

	}

	public Provider(Integer id, String name, String cnpj, String email, String phone, String cellphone, String cep,
			String address, Integer number, String complement, String neighborhood, String city, String state) {
		this.setId(id);
		this.setName(name);
		this.setCnpj(cnpj);
		this.setEmail(email);
		this.setPhone(cellphone);
		this.setCellphone(cellphone);
		this.setCep(cep);
		this.setAddress(address);
		this.setNumber(number);
		this.setComplement(complement);
		this.setNeighborhood(neighborhood);
		this.setCity(city);
		this.setState(state);
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	@Override
	public String toString() {
		return this.getName();
	}

}
