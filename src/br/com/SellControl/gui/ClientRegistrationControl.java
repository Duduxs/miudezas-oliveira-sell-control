package br.com.SellControl.gui;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import br.com.SellControl.dao.ClientDAO;
import br.com.SellControl.dao.DaoFactory;
import br.com.SellControl.db.ControlException;
import br.com.SellControl.model.entities.Client;
import br.com.SellControl.util.Alerts;
import br.com.SellControl.util.Constraints;
import br.com.SellControl.util.Mask;
import br.com.SellControl.util.WebServiceCep;
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

public class ClientRegistrationControl implements Initializable {

	@FXML
	private TextField txtCode;
	@FXML
	private TextField txtName;
	@FXML
	private TextField txtEmail;
	@FXML
	private TextField txtCEP;
	@FXML
	private TextField txtCPF;
	@FXML
	private TextField txtAddress;
	@FXML
	private TextField txtNeighborhood;
	@FXML
	private TextField txtCity;
	@FXML
	private TextField txtPhone;
	@FXML
	private TextField txtCellphone;
	@FXML
	private TextField txtNumber;
	@FXML
	private TextField txtComplement;

	@FXML
	private TextField txtSearch;

	@FXML
	private ComboBox<String> comboBoxUF;
	// Variable for save all my States/UF in comboBox.
	private ObservableList<String> obsList;
	// Variable for save all my Client in tableViewClient.
	private ObservableList<Client> obsListClient;

	@FXML
	private TableView<Client> tableViewClient;
	@FXML
	private TabPane tabPaneClient;

	@FXML
	private TableColumn<Client, Integer> tableColumnCode;
	@FXML
	private TableColumn<Client, String> tableColumnName;
	@FXML
	private TableColumn<Client, String> tableColumnCPF;
	@FXML
	private TableColumn<Client, String> tableColumnEmail;
	@FXML
	private TableColumn<Client, String> tableColumnPhone;
	@FXML
	private TableColumn<Client, String> tableColumnCellphone;
	@FXML
	private TableColumn<Client, String> tableColumnCEP;
	@FXML
	private TableColumn<Client, String> tableColumnAddress;
	@FXML
	private TableColumn<Client, Integer> tableColumnNumber;
	@FXML
	private TableColumn<Client, String> tableColumnComplement;
	@FXML
	private TableColumn<Client, String> tableColumnNeighborhood;
	@FXML
	private TableColumn<Client, String> tableColumnCity;
	@FXML
	private TableColumn<Client, String> tableColumnState;

	@FXML
	private Tab tabConsultCustomer;
	@FXML
	private Tab tabPersonalData;

	@FXML
	private Button btnClean;
	@FXML
	private Button btnEdit;
	@FXML
	private Button btnSave;
	@FXML
	private Button btnDelete;
	@FXML
	private Button btnSearchConsultCustomer;
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
		txtName.setText(null);
		txtEmail.setText(null);
		txtCEP.setText("");
		txtCPF.setText("");
		txtAddress.setText(null);
		txtNeighborhood.setText(null);
		txtCity.setText(null);
		txtCellphone.setText("");
		txtPhone.setText("");
		txtNumber.setText(null);
		txtComplement.setText(null);
		comboBoxUF.getSelectionModel().select(null);

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
			throw new ControlException(e.getMessage(), "message", null, "fields cannot be empty", AlertType.ERROR);
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
			throw new ControlException(e.getMessage(), "message", null, "Client not found!", AlertType.ERROR);
		}
	}

	// Method for intialize something in start of the program
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		initializeComboBox();
		initializeNodes();
		initializeMask();
		initializeConstraints();
	

	}

	private void initializeComboBox() {
		// Include in my list all states from Brazil
		List<String> UF = Arrays.asList("AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO", "MA", "MT", "MS", "MG",
				"PA", "PB", "PR", "PE", "PI", "RJ", "RN", "RS", "RO", "RR", "SC", "SP", "SE", "TO");

		obsList = FXCollections.observableArrayList(UF);
		comboBoxUF.setItems(obsList);
		// PE in comboBox will be select first
		comboBoxUF.getSelectionModel().select(16);
	}

	/*
	 * When the FXML Panel start this method will be responsible for load the
	 * columns to save my client datas.
	 */
	private void initializeNodes() {

		// Initialize all columns at the my tableViewClient to insert data later.
		tableColumnCode.setCellValueFactory(new PropertyValueFactory<>("id"));
		tableColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
		tableColumnCPF.setCellValueFactory(new PropertyValueFactory<>("cpf"));
		tableColumnEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
		tableColumnPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
		tableColumnCellphone.setCellValueFactory(new PropertyValueFactory<>("cellphone"));
		tableColumnCEP.setCellValueFactory(new PropertyValueFactory<>("cep"));
		tableColumnAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
		tableColumnNumber.setCellValueFactory(new PropertyValueFactory<>("number"));
		tableColumnComplement.setCellValueFactory(new PropertyValueFactory<>("complement"));
		tableColumnNeighborhood.setCellValueFactory(new PropertyValueFactory<>("neighborhood"));
		tableColumnCity.setCellValueFactory(new PropertyValueFactory<>("city"));
		tableColumnState.setCellValueFactory(new PropertyValueFactory<>("state"));
	

	}

	// Use the mask in the some form data.
	private void initializeMask() {
		Mask.maskCEP(txtCEP);
		Mask.maskCPF(txtCPF);
		Mask.maskPhone(txtPhone);
		Mask.maskPhone(txtCellphone);

	}
	// Set max lenght for my TextFields
	private void initializeConstraints() {
		Constraints.setTextFieldMaxLength(txtName, 20);
		Constraints.setTextFieldMaxLength(txtEmail, 32);
		Constraints.setTextFieldMaxLength(txtAddress, 30);
		Constraints.setTextFieldMaxLength(txtNumber, 5);
		Constraints.setTextFieldMaxLength(txtComplement, 60);
		Constraints.setTextFieldMaxLength(txtNeighborhood, 30);
		Constraints.setTextFieldMaxLength(txtCity, 25);
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

	// Method for find Cep in WebServiceCep
	@FXML
	private void onTxtCepKeyAction() {
		// Load
		WebServiceCep webServiceCep = WebServiceCep.searchCep(txtCEP.getText());
		// Set
		if (webServiceCep.wasSuccessful()) {
			txtAddress.setText(webServiceCep.getLogradouroFull());
			txtCity.setText(webServiceCep.getCidade());
			txtNeighborhood.setText(webServiceCep.getBairro());
			comboBoxUF.getSelectionModel().select(webServiceCep.getUf());
		}
	}

}
