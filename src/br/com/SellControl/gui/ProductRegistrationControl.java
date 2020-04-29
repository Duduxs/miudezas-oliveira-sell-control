package br.com.SellControl.gui;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import br.com.SellControl.dao.ClientDAO;
import br.com.SellControl.dao.DaoFactory;
import br.com.SellControl.dao.ProviderDAO;
import br.com.SellControl.model.entities.Client;
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
	private TextField txtStockQuantity;

	@FXML
	private TextField txtSearch;

	@FXML
	private ComboBox<Provider> comboBoxProvider;
	// Variable for save all my Product in tableViewProduct.
	private ObservableList<Provider> obsListProduct;

	@FXML
	private TableView<Client> tableViewProduct;
	@FXML
	private TabPane tabPaneProduct;

	@FXML
	private TableColumn<Client, Integer> tableColumnCode;
	@FXML
	private TableColumn<Client, String> tableColumnDescription;
	@FXML
	private TableColumn<Client, String> tableColumnPrice;
	@FXML
	private TableColumn<Client, String> tableColumnStockQuantity;
	@FXML
	private TableColumn<Client, String> tableColumnProvider;

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
		// Create a client in model
		Client client = makeClient();
		// The code don't need a value from textField.
		txtCode.setText("1");
		// Create a client in Dao
		ClientDAO clientDAO = DaoFactory.createClientDAO();
		// Use SQL Command
		clientDAO.insert(client);
		// Clean the elements in TextField.
		onBtnCleanAction();
		// Show a success message
		Alerts.showAlert("Message", null, "Registered client!", AlertType.INFORMATION);

	}

	@FXML
	private void onBtnEditAction() {
		// Create a client in model
		Client client = makeClient();
		// Set an id getting the element from TableView
		client.setId(Integer.parseInt(txtCode.getText()));
		// Create a client in Dao
		ClientDAO clientDAO = DaoFactory.createClientDAO();
		// Use SQL Command
		clientDAO.update(client);
		// Clean the elements in TextField.
		onBtnCleanAction();
		// Show a success message
		Alerts.showAlert("Message", null, "Edited client!", AlertType.INFORMATION);

	}

	@FXML
	private void onBtnDeleteAction() {
		// Create a client in model
		Client client = makeClient();
		// Set an id getting the element from TableView
		client.setId(Integer.parseInt(txtCode.getText()));
		// Create a client in Dao
		ClientDAO clientDAO = DaoFactory.createClientDAO();
		// Use SQL Command
		clientDAO.delete(client);
		// Clean the elements in TextField.
		onBtnCleanAction();
		// Show a success message
		Alerts.showAlert("Message", null, "Deleted client!", AlertType.INFORMATION);
	}

	@FXML
	private void onBtnCleanAction() {
		// "" = not change
		// Clean all elements in TextField.

		txtCode.setText("");
		txtDescription.setText(null);
		txtPrice.setText(null);
		txtStockQuantity.setText(null);
		comboBoxProvider.getSelectionModel().select(null);

	}

	// Search a list of client on the Consult Customer tab at the btn Search
	@FXML
	private void onBtnSearchConsultCustomerAction() {
		// Create a clientDao.
		ClientDAO clientDAO = DaoFactory.createClientDAO();
		// Create a client list and use sql command findbyName
		List<Client> list = clientDAO.findbyName(txtSearch.getText());
		// Now load all my clients from insert to the my obsListClient.
		obsListClient = FXCollections.observableArrayList(list);
		// Set my table putting all clients him.
		tableViewClient.setItems(obsListClient);
	}

	/*
	 * When i'm in the first tab and i click in search, this method will be throw
	 * and it will fill all TextFields on first tab.
	 */
	@FXML
	private void onBtnSearchPersonalDataAction() {
		// Create a clientDao.
		ClientDAO clientDAO = DaoFactory.createClientDAO();
		// Create a client list and use sql command findClientByName
		Client c = clientDAO.findClientByName(txtName.getText());
		setClient(c);

	}

	// Search a list of client on the Consult Customer tab at the txtSearch only
	// tipping
	@FXML
	private void onTxtSearchKeyPressedAction() {
		// Create a clientDao.
		ClientDAO clientDAO = DaoFactory.createClientDAO();
		// Create a client list and use sql command findbyName
		List<Client> list = clientDAO.findbyName(txtSearch.getText());
		// Now load all my clients from insert to the my obsListClient.
		obsListClient = FXCollections.observableArrayList(list);
		// Set my table putting all clients him.
		tableViewClient.setItems(obsListClient);
	}

	@FXML
	private void onConsultCustomerChanged() {
		// IF consultCustomer is selected them load my tableView with all my clients,
		// and show them.
		if (tabConsultCustomer.isSelected())
			updateTableViewClient();
	}

	/*
	 * When the mouse clicked in my TableView in tuple i want to go to personal data
	 * tab with values.
	 */
	@FXML
	private void onTableViewClientMouseClicked() {
		// This function only will be executed if have an element in TableView, so
		// avoiding an exception.
		if (tableViewClient.getSelectionModel().getSelectedItem() != null) {

			// Set the tab and change them.
			SingleSelectionModel<Tab> selectionModel = tabPaneClient.getSelectionModel();
			selectionModel.select(0);

			// A temporary variable for save the selected cells in TableView.
			Client client = tableViewClient.getSelectionModel().getSelectedItem();
			// In the personalDataTab i set these items.
			txtCode.setText(client.getId().toString());
			txtName.setText(client.getName().toString());
			txtCPF.setText(client.getCpf().toString());
			txtEmail.setText(client.getEmail().toString());
			txtPhone.setText(client.getPhone().toString());
			txtCellphone.setText(client.getCellphone().toString());
			txtCEP.setText(client.getCep().toString());
			txtAddress.setText(client.getAddress().toString());
			txtNumber.setText(client.getNumber().toString());
			txtComplement.setText(client.getComplement().toString());
			txtNeighborhood.setText(client.getNeighborhood().toString());
			txtCity.setText(client.getCity().toString());
			comboBoxUF.setValue(client.getState());

		}
	}

	// Get the form and make client. This is for the insert sql command in
	// btnSaveAction method.
	private Client makeClient() {
		try {
			// The code don't need a value from textField.
			Integer code = Integer.parseInt("1");
			String name = txtName.getText();
			String cpf = txtCPF.getText();
			String email = txtEmail.getText();
			String phone = txtPhone.getText();
			String cellphone = txtCellphone.getText();
			String cep = txtCEP.getText();
			String address = txtAddress.getText();
			Integer number = Integer.parseInt(txtNumber.getText());
			String complement = txtComplement.getText();
			String neighborhood = txtNeighborhood.getText();
			String city = txtCity.getText();
			String state = comboBoxUF.getSelectionModel().getSelectedItem();

			return new Client(code, name, cpf, email, phone, cellphone, cep, address, number, complement, neighborhood,
					city, state);
		}
		// If have any field empty, them throw this exception
		catch (NumberFormatException e) {
			throw new ControlException(e.getMessage(), "message", null, "fields cannot be empty", AlertType.ERROR,
					true);
		}

	}

	// This method exists for set all form (TextFields) on my first tab, using the
	// attributes of the client.
	private void setClient(Client c) {
		try {
			txtCode.setText(c.getId().toString());
			txtName.setText(c.getName());
			txtEmail.setText(c.getEmail());
			txtCEP.setText(c.getCep());
			txtCPF.setText(c.getCpf());
			txtAddress.setText(c.getAddress());
			txtNeighborhood.setText(c.getNeighborhood());
			txtCity.setText(c.getCity());
			txtCellphone.setText(c.getCellphone());
			txtPhone.setText(c.getPhone());
			txtNumber.setText(c.getNumber().toString());
			txtComplement.setText(c.getComplement());
			comboBoxUF.getSelectionModel().select(c.getState());
		} catch (NullPointerException e) {
			throw new ControlException(e.getMessage(), "message", null, "Client not found!", AlertType.ERROR, true);
		}
	}

	// Method for intialize something in start of the program
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		initializeComboBox();
		initializeNodes();
		initializeConstraints();

	}

	private void initializeComboBox() {
		// Create providerDAO
		ProviderDAO providerDAO = DaoFactory.createProviderDAO();
		// Include in my list all providers
		List<Provider> providerList = providerDAO.findAll();
		// Avoid duplication datas
		comboBoxProvider.getSelectionModel().clearSelection();
		//Add in my comboBox the providers
		obsListProduct = FXCollections.observableArrayList(providerList);
		comboBoxProvider.setItems(obsListProduct);
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
		tableColumnStockQuantity.setCellValueFactory(new PropertyValueFactory<>("qtd_stock"));
		tableColumnProvider.setCellValueFactory(new PropertyValueFactory<>("for_id"));

	}

	// Set max lenght for my TextFields
	private void initializeConstraints() {
		Constraints.setTextFieldMaxLength(txtDescription, 100);
		Constraints.setTextFieldMaxLength(txtPrice, 8);
		Constraints.setTextFieldDouble(txtPrice);
		Constraints.setTextFieldMaxLength(txtStockQuantity, 7);
		Constraints.setTextFieldInteger(txtStockQuantity);

	}

	// Update my TableView, So, having the data from the columns.
	private void updateTableViewClient() {

		// Create a clientDao.
		ClientDAO clientDAO = DaoFactory.createClientDAO();
		// Create a client list and use sql command findAll.
		List<Client> list = clientDAO.findAll();
		// Now load all my clients from insert to the my obsListClient.
		obsListClient = FXCollections.observableArrayList(list);
		// Set my table putting all clients him.
		tableViewClient.setItems(obsListClient);

	}

}
