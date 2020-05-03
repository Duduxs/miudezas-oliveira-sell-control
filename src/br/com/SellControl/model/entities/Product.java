package br.com.SellControl.model.entities;

public class Product {

	private Integer id;
	private String description;
	private Double price;
	private Integer Stock;
	// set, variable and updateTableViewPos all temporary
	private Double subTotal;

	private Provider provider;
	@SuppressWarnings("unused")
	private String providerName;

	public Product() {

	}

	public Product(Integer id, String description, Double price, Integer Stock, Provider provider) {
		this.id = id;
		this.description = description;
		this.price = price;
		this.Stock = Stock;
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

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Integer getQtdStock() {
		return Stock;
	}

	public void setQtdStock(Integer qtdStock) {
		this.Stock = qtdStock;
	}

	public Provider getProvider() {
		return provider;
	}

	public void setProvider(Provider provider) {
		this.provider = provider;
	}

	public void setSubTotal(Double subTotal) {
		this.subTotal = subTotal;
	}

	public Double getSubTotal() {
		return subTotal;
	}

	public String getProviderName() {
		return provider.getName();
	}

}
