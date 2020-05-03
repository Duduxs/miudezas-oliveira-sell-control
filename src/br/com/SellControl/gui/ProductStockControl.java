package br.com.SellControl.gui;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import br.com.SellControl.dao.DaoFactory;
import br.com.SellControl.dao.ProductDAO;
import br.com.SellControl.dao.ProviderDAO;
import br.com.SellControl.model.entities.Product;
import br.com.SellControl.model.entities.Provider;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class ProductStockControl implements Initializable {

	@FXML
	private TextField txtDescription;
	@FXML
	private TextField txtCurrentStock;
	@FXML
	private TextField txtQuantity;

	@FXML
	private Button btnSearch;
	@FXML
	private Button btnAdd;

	@FXML
	private TableView<Product> tableViewProduct;
	@FXML
	private ObservableList<Product> obsListProduct;

	@FXML
	private TableColumn<Product, Integer> tableColumnID;
	@FXML
	private TableColumn<Product, String> tableColumnDescription;
	@FXML
	private TableColumn<Product, Double> tableColumnPrice;
	@FXML
	private TableColumn<Product, Integer> tableColumStockQuantity;
	@FXML
	private TableColumn<Product, String> tableColumnProvider;

	// Search a list of product on the btn Search
	@FXML
	private void onBtnSearchConsultProductAction() {
		// Create a productDAO
		ProductDAO productDAO = DaoFactory.createProductDAO();
		// Create a product list and use sql command findbyName
		List<Product> list = productDAO.findbyName(txtDescription.getText());
		// Now load all my product from insert to the my obsListClient.
		obsListProduct = FXCollections.observableArrayList(list);
		// Set my table putting all product him.
		tableViewProduct.setItems(obsListProduct);
	}

	// Search a list of product the txtSearch only tipping
	@FXML
	private void onTxtSearchKeyPressedAction() {
		// Create a producttDao.
		ProductDAO productDAO = DaoFactory.createProductDAO();
		// Create a product list and use sql command findbyName
		List<Product> list = productDAO.findbyName(txtDescription.getText());
		// Now load all my product from insert to the my obsListProduct.
		obsListProduct = FXCollections.observableArrayList(list);
		// Set my table putting all product him.
		tableViewProduct.setItems(obsListProduct);
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

			// A temporary variable for save the selected cells in TableView.
			Product product = tableViewProduct.getSelectionModel().getSelectedItem();
			// In the personalDataTab i set these items.
			txtCurrentStock.setText(product.getQtdStock().toString());
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initializeNodes();
		updateTableViewProduct();
	}

	/*
	 * When the FXML Panel start this method will be responsible for load the
	 * columns to save my sell datas.
	 */
	private void initializeNodes() {
		// Initialize all columns at the my tableViewProduct to insert data later.
		tableColumnID.setCellValueFactory(new PropertyValueFactory<>("id"));
		tableColumnDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
		tableColumnPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
		tableColumStockQuantity.setCellValueFactory(new PropertyValueFactory<>("QtdStock"));
		tableColumnProvider.setCellValueFactory(new PropertyValueFactory<>("ProviderName"));

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
