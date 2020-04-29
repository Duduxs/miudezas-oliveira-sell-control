package br.com.SellControl.model.entities;

public class Product {

	private Integer id;
	private String description;
	private Integer price;
	private Integer qtdStock;

	private Provider provider;

	public Product() {

	}

	public Product(Integer id, String description, Integer price, Integer qtdStock, Provider provider) {
		this.id = id;
		this.description = description;
		this.price = price;
		this.qtdStock = qtdStock;
		this.provider = provider;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public Integer getQtdStock() {
		return qtdStock;
	}

	public void setQtdStock(Integer qtdStock) {
		this.qtdStock = qtdStock;
	}

	public Provider getProvider() {
		return provider;
	}

	public void setProvider(Provider provider) {
		this.provider = provider;
	}

}
