package br.com.SellControl.gui;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import br.com.SellControl.dao.DaoFactory;
import br.com.SellControl.dao.ProductDAO;
import br.com.SellControl.dao.ProviderDAO;
import br.com.SellControl.model.entities.Product;
import br.com.SellControl.model.entities.Provider;
import br.com.SellControl.model.exception.ControlException;
import br.com.SellControl.util.Alerts;
import br.com.SellControl.util.Constraints;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class ProductRegistrationControl implements Initializable {

	@FXML
	private TextField txtCode;
	@FXML
	private TextField txtDescription;
	@FXML
	private TextField txtPrice;
	@FXML
	private TextField txtStock;

	@FXML
	private TextField txtSearch;

	@FXML
	private ComboBox<Provider> comboBoxProvider;
	// Variable for save all my Providers in comboBox.
	private ObservableList<Provider> obsList;
	// Variable for save all my Product in tableViewProduct.
	private ObservableList<Product> obsListProduct;

	@FXML
	private TableView<Product> tableViewProduct;
	@FXML
	private TabPane tabPaneProduct;

	@FXML
	private TableColumn<Product, Integer> tableColumnCode;
	@FXML
	private TableColumn<Product, String> tableColumnDescription;
	@FXML
	private TableColumn<Product, Double> tableColumnPrice;
	@FXML
	private TableColumn<Product, Integer> tableColumnStock;
	@FXML
	private TableColumn<Product, Integer> tableColumnProvider;

	@FXML
	private Tab tabConsultProduct;
	@FXML
	private Tab tabProductData;

	@FXML
	private Button btnClean;
	@FXML
	private Button btnEdit;
	@FXML
	private Button btnSave;
	@FXML
	private Button btnDelete;
	@FXML
	private Button btnSearchConsultProduct;
	@FXML
	private Button btnSearchPersonalData;

	@FXML
	private void onBtnSaveAction() {
		// Create a product in model
		Product product = makeProduct();
		// The code don't need a value from textField.
		txtCode.setText("1");
		// Create a product in Dao
		ProductDAO productDAO = DaoFactory.createProductDAO();
		// Use SQL Command
		productDAO.insert(product);
		// Clean the elements in TextField.
		onBtnCleanAction();
		// Show a success message
		Alerts.showAlert("Message", null, "Registered product!", AlertType.INFORMATION);

	}

	@FXML
	private void onBtnEditAction() {
		// Create a product in model
		Product product = makeProduct();
		// Set an id getting the element from TableView
		product.setId(Integer.parseInt(txtCode.getText()));
		// Create a product in Dao
		ProductDAO productDAO = DaoFactory.createProductDAO();
		// Use SQL Command
		productDAO.update(product);
		// Clean the elements in TextField.
		onBtnCleanAction();
		// Show a success message
		Alerts.showAlert("Message", null, "Edited product!", AlertType.INFORMATION);

	}

	@FXML
	private void onBtnDeleteAction() {
		// Create a product in model
		Product product = makeProduct();
		// Set an id getting the element from TableView
		product.setId(Integer.parseInt(txtCode.getText()));
		// Create a product in Dao
		ProductDAO productDAO = DaoFactory.createProductDAO();
		// Use SQL Command
		productDAO.delete(product);
		// Clean the elements in TextField.
		onBtnCleanAction();
		// Show a success message
		Alerts.showAlert("Message", null, "Deleted product!", AlertType.INFORMATION);
	}

	@FXML
	private void onBtnCleanAction() {
		// "" = not change
		// Clean all elements in TextField.

		txtCode.setText("");
		txtDescription.setText(null);
		txtPrice.setText(null);
		txtStock.setText(null);
		comboBoxProvider.getSelectionModel().select(null);

	}

	/*
	 * When i'm in the first tab and i click in search, this method will be throw
	 * and it will fill all TextFields on first tab.
	 */
	@FXML
	private void onBtnSearchProductDataAction() {
		// Create a ProductDAO.
		ProductDAO productDAO = DaoFactory.createProductDAO();
		// Create a product list and use sql command findProductByName
		Product p = productDAO.findProductByName(txtDescription.getText());
		setProduct(p);

	}

	// Search a list of product on the Consult Product tab at the btn Search
	@FXML
	private void onBtnSearchConsultProductAction() {
		// Create a productDAO
		ProductDAO productDAO = DaoFactory.createProductDAO();
		// Create a product list and use sql command findbyName
		List<Product> list = productDAO.findbyName(txtSearch.getText());
		// Now load all my product from insert to the my obsListClient.
		obsListProduct = FXCollections.observableArrayList(list);
		// Set my table putting all product him.
		tableViewProduct.setItems(obsListProduct);
	}

	// Search a list of product on the Consult Product tab at the txtSearch only
	// tipping
	@FXML
	private void onTxtSearchKeyPressedAction() {
		// Create a producttDao.
		ProductDAO productDAO = DaoFactory.createProductDAO();
		// Create a product list and use sql command findbyName
		List<Product> list = productDAO.findbyName(txtSearch.getText());
		// Now load all my product from insert to the my obsListProduct.
		obsListProduct = FXCollections.observableArrayList(list);
		// Set my table putting all product him.
		tableViewProduct.setItems(obsListProduct);
	}

	@FXML
	private void onConsultProductChanged() {
		// IF consultCustomer is selected them load my tableView with all my product,
		// and show them.
		if (tabConsultProduct.isSelected())
			updateTableViewProduct();
	}

	/*
	 * When the mouse clicked in my TableView in tuple i want to go to personal data
	 * tab with values.
	 */
	@FXML
	private void onTableViewProductMouseClicked() {
		// This function only will be executed if have an element in TableView, so
		// avoiding an exception.
		if (tableViewProduct.getSelectionModel().getSelectedItem() != null) {

			// Set the tab and change them.
			SingleSelectionModel<Tab> selectionModel = tabPaneProduct.getSelectionModel();
			selectionModel.select(0);

			// A temporary variable for save the selected cells in TableView.
			Product product = tableViewProduct.getSelectionModel().getSelectedItem();
			// In the personalDataTab i set these items.
			txtCode.setText(product.getId().toString());
			txtDescription.setText(product.getDescription().toString());
			txtPrice.setText(product.getPrice().toString());
			txtStock.setText(product.getQtdStock().toString());

			Provider p = new Provider();
			ProviderDAO providerDAO = DaoFactory.createProviderDAO();
			p = providerDAO.findProviderByName(product.getProvider().getName());
			comboBoxProvider.setValue(p);

		}
	}

	// Get the form and make Product. This is for the insert sql command in
	// btnSaveAction method.
	private Product makeProduct() {
		try {
			// The code don't need a value from textField.
			Integer code = Integer.parseInt("1");
			String description = txtDescription.getText();
			Double price = Double.parseDouble(txtPrice.getText());
			Integer StockQuantity = Integer.parseInt(txtStock.getText());

			Provider provider = new Provider();
			provider = (Provider) comboBoxProvider.getSelectionModel().getSelectedItem();

			return new Product(code, description, price, StockQuantity, provider);
		}
		// If have any field empty, them throw this exception
		catch (NumberFormatException e) {
			throw new ControlException(e.getMessage(), "message", null, "fields cannot be empty", AlertType.ERROR,
					true);
		}

	}

	// This method exists for set all form (TextFields) on my first tab, using the
	// attributes of the Product.
	private void setProduct(Product p) {
		try {
			txtCode.setText(p.getId().toString());
			txtDescription.setText(p.getDescription());
			txtPrice.setText(p.getPrice().toString());
			txtStock.setText(p.getQtdStock().toString());

			Provider pr = new Provider();
			ProviderDAO providerDAO = DaoFactory.createProviderDAO();
			pr = providerDAO.findProviderByName(p.getProvider().getName());

			comboBoxProvider.getSelectionModel().select(pr);

		} catch (NullPointerException e) {
			throw new ControlException(e.getMessage(), "message", null, "Product not found!", AlertType.ERROR, true);
		}
	}

	// Method for intialize something in start of the program
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		initializeComboBox();
		initializeConstraints();
		initializeNodes();

	}

	private void initializeComboBox() {
		// Create providerDAO
		ProviderDAO providerDAO = DaoFactory.createProviderDAO();
		// Include in my list all providers
		List<Provider> providerList = providerDAO.findAll();
		// Avoid duplication datas
		comboBoxProvider.getItems().clear();
		// Add in my comboBox the providers
		obsList = FXCollections.observableArrayList(providerList);
		comboBoxProvider.setItems(obsList);
		// Position 0 in comboBox will be select first
		comboBoxProvider.getSelectionModel().select(0);
	}

	/*
	 * When the FXML Panel start this method will be responsible for load the
	 * columns to save my product datas.
	 */
	private void initializeNodes() {

		// Initialize all columns at the my tableViewProduct to insert data later.
		tableColumnCode.setCellValueFactory(new PropertyValueFactory<>("id"));
		tableColumnDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
		tableColumnPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
		tableColumnStock.setCellValueFactory(new PropertyValueFactory<>("QtdStock"));
		tableColumnProvider.setCellValueFactory(new PropertyValueFactory<>("Provider"));

	}

	// Set max lenght for my TextFields
	private void initializeConstraints() {
		Constraints.setTextFieldMaxLength(txtDescription, 100);
		Constraints.setTextFieldMaxLength(txtPrice, 8);
		Constraints.setTextFieldDouble(txtPrice);
		Constraints.setTextFieldMaxLength(txtStock, 7);
		Constraints.setTextFieldInteger(txtStock);

	}

	// Update my TableView, So, having the data from the columns.
	private void updateTableViewProduct() {

		// Create a productDAO.
		ProductDAO productDAO = DaoFactory.createProductDAO();
		// Create a product list and use sql command findAll.
		List<Product> list = productDAO.findAll();
		// Now load all my product from insert to the my obsListProduct.
		obsListProduct = FXCollections.observableArrayList(list);
		// Set my table putting all product him.
		tableViewProduct.setItems(obsListProduct);

	}

}
