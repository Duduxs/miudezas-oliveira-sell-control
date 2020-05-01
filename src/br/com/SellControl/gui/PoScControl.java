package br.com.SellControl.gui;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import br.com.SellControl.dao.ClientDAO;
import br.com.SellControl.dao.DaoFactory;
import br.com.SellControl.dao.ProductDAO;
import br.com.SellControl.model.entities.Client;
import br.com.SellControl.model.entities.Product;
import br.com.SellControl.util.Alerts;
import br.com.SellControl.util.Mask;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class PoScControl implements Initializable {

	// For calc my subTotal and total in txtField
	Integer quantity = 0;
	Double total = 0.0, subtotal = 0.0, price = 0.0;

	@FXML
	private TextField txtDate;
	@FXML
	private TextField txtName;
	@FXML
	private TextField txtEmail;
	@FXML
	private TextField txtCPF;
	@FXML
	private TextField txtCode;
	@FXML
	private TextField txtProduct;
	@FXML
	private TextField txtQuantity;
	@FXML
	private TextField txtPrice;
	@FXML
	private TextField txtTotal;

	@FXML
	private TableColumn<Integer, Product> tableColumnID;
	@FXML
	private TableColumn<String, Product> tableColumnDescription;
	@FXML
	private TableColumn<Integer, Product> tableColumnQuantity;
	@FXML
	private TableColumn<Double, Product> tableColumnPrice;
	@FXML
	private TableColumn<Double, Product> tableColumnSubTotal;

	@FXML
	private TableView<Product> tableViewPointOfSell;

	@FXML
	private ObservableList<Product> obsListPointOfSell;

	@FXML
	private Button btnSearchCPF;
	@FXML
	private Button btnSearchCode;
	@FXML
	private Button btnAddItem;

	// Find client by CPF (1 form)
	@FXML
	public void onbtnSearchCPFAction() {
		try {
			// Create a new client
			Client client = new Client();
			// Create a dao
			ClientDAO clientDAO = DaoFactory.createClientDAO();
			// Use a new findClientByCPF and put in client
			client = clientDAO.findClientByCPF(txtCPF.getText());
			// Return an alert if client not found!
			if (client.getCpf() == null && txtCPF.getText().length() == 14) {
				Alerts.showAlert("message", null, "Client not found!", AlertType.ERROR);
			}
			// Set my textfields.
			txtName.setText(client.getName());
			txtEmail.setText(client.getEmail());
		} catch (NullPointerException e) {
			Alerts.showAlert("message", null, "Client not found!", AlertType.ERROR);
		}

	}

	// Find client by CPF (2 form)
	@FXML
	public void onTxtCPFKeyPressed(KeyEvent evt) {
		// Only if i pressed the enter key.
		if (evt.getCode().equals(KeyCode.ENTER)) {

			// Create a new client.
			Client client = new Client();
			// Create a dao.
			ClientDAO clientDAO = DaoFactory.createClientDAO();
			// Use a new findClientByCPF and put in client
			client = clientDAO.findClientByCPF(txtCPF.getText());
			// Return an alert if client not found!
			if (client.getCpf() == null && txtCPF.getText().length() == 14) {
				Alerts.showAlert("message", null, "Client not found!", AlertType.ERROR);
			}
			// Set my textfields.
			txtName.setText(client.getName());
			txtEmail.setText(client.getEmail());

		}
	}

	// Find product by Code (1 form)
	@FXML
	public void onbtnSearchCodeAction() {
		try {
			// Create a new product
			Product product = new Product();
			// Create a dao
			ProductDAO productDAO = DaoFactory.createProductDAO();
			// Use a new findClientByCPF and put in client
			product = productDAO.findProductByCode(txtCode.getText());
			// Set my textfields.
			txtProduct.setText(product.getDescription());
			txtPrice.setText(product.getPrice().toString());
		} catch (NullPointerException e) {
			Alerts.showAlert("message", null, "Product not found!", AlertType.ERROR);
		}

	}

	// Find product by Code (2 form)
	@FXML
	public void onTxtCodeKeyPressed(KeyEvent evt) {
		// Only if i pressed the enter key.
		if (evt.getCode().equals(KeyCode.ENTER)) {
			try {
				// Create a new product
				Product product = new Product();
				// Create a dao
				ProductDAO productDAO = DaoFactory.createProductDAO();
				// Use a new findClientByCPF and put in client
				product = productDAO.findProductByCode(txtCode.getText());
				// Set my textfields.
				txtProduct.setText(product.getDescription());
				txtPrice.setText(product.getPrice().toString());
			} catch (NullPointerException e) {
				Alerts.showAlert("message", null, "Product not found!", AlertType.ERROR);
			}
		}
	}

	@FXML
	public void onBtnAddItemAction() {
		// Get the text in txtQuantity and price for calc
		quantity = Integer.parseInt(txtQuantity.getText());
		price = Double.parseDouble(txtPrice.getText());
		// calc
		subtotal = quantity * price;
		total += subtotal;
		// Show in txtTotal
		txtTotal.setText(total.toString());
		updateTableViewPoS();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		setDate();
		// Format a valid cpf
		Mask.maskCPF(txtCPF);
		initializeNodes();

	}

	// When the FXML Panel start this method will be responsible for load the
	// columns
	public void initializeNodes() {
		tableColumnID.setCellValueFactory(new PropertyValueFactory<>("id"));
		tableColumnDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
		tableColumnQuantity.setCellValueFactory(new PropertyValueFactory<>("QtdStock"));
		tableColumnPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
		tableColumnSubTotal.setCellValueFactory(new PropertyValueFactory<>("subtotal"));
	}

	// Update my TableView, So, having the data from the columns.
	private void updateTableViewPoS() {

		// Create a productDAO
		ProductDAO productDAO = DaoFactory.createProductDAO();
		// Create a product list and use sql command findAll.
		List<Product> list = productDAO.findAll();
		// Now load all my itnes from insert to the my obsListPointOfSell.
		obsListPointOfSell = FXCollections.observableArrayList(list);
		// Set my table putting all pointOfSell him.
		tableViewPointOfSell.setItems(obsListPointOfSell);

	}

	// Load the actual date form system.
	public void setDate() {
		// Create a new date
		Date now = new Date();
		// Use American format
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		// Put the new date in American format
		String dateformated = sdf.format(now);
		// Set my txtDate
		txtDate.setText(dateformated);

	}

}
