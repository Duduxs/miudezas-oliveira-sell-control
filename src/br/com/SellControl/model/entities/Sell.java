package br.com.SellControl.model.entities;

public class Sell {

	private Integer id;
	private Client client;
	private String dateSell;
	private Double totalSell;
	private String obs;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public String getDateSell() {
		return dateSell;
	}

	public void setDateSell(String dateSell) {
		this.dateSell = dateSell;
	}

	public double getTotalSell() {
		return totalSell;
	}

	public void setTotalSell(double totalSell) {
		this.totalSell = totalSell;
	}

	public String getObs() {
		return obs;
	}

	public void setObs(String obs) {
		this.obs = obs;
	}

}
