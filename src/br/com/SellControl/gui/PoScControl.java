package br.com.SellControl.gui;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import br.com.SellControl.dao.ClientDAO;
import br.com.SellControl.dao.DaoFactory;
import br.com.SellControl.dao.ProductDAO;
import br.com.SellControl.model.entities.Client;
import br.com.SellControl.model.entities.Product;
import br.com.SellControl.util.Alerts;
import br.com.SellControl.util.Mask;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class PoScControl implements Initializable {

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
	private Button btnSearchCPF;
	@FXML
	private Button btnSearchCode;

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
			//Return an alert if client not found!
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
			//Return an alert if client not found!
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
			txtQuantity.setText(product.getQtdStock().toString());
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
				txtQuantity.setText(product.getQtdStock().toString());
				txtPrice.setText(product.getPrice().toString());
			} catch (NullPointerException e) {
				Alerts.showAlert("message", null, "Product not found!", AlertType.ERROR);
			}
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		setDate();
		// Format a valid cpf
		Mask.maskCPF(txtCPF);

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
