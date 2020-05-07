package br.com.SellControl.gui;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import br.com.SellControl.dao.DaoFactory;
import br.com.SellControl.dao.ProviderDAO;
import br.com.SellControl.model.entities.Provider;
import br.com.SellControl.model.exception.ControlException;
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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class ProviderRegistrationControl implements Initializable {
	@FXML
	private TextField txtCode;
	@FXML
	private TextField txtName;
	@FXML
	private TextField txtEmail;
	@FXML
	private TextField txtCEP;
	@FXML
	private TextField txtCNPJ;
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
	// Variable for save all my Provider in tableViewProvider.
	private ObservableList<Provider> obsListProvider;

	@FXML
	private TableView<Provider> tableViewProvider;
	@FXML
	private TabPane tabPaneProvider;

	@FXML
	private TableColumn<Provider, Integer> tableColumnCode;
	@FXML
	private TableColumn<Provider, String> tableColumnName;
	@FXML
	private TableColumn<Provider, String> tableColumnCEP;
	@FXML
	private TableColumn<Provider, String> tableColumnEmail;
	@FXML
	private TableColumn<Provider, String> tableColumnPhone;
	@FXML
	private TableColumn<Provider, String> tableColumnCellphone;
	@FXML
	private TableColumn<Provider, String> tableColumnCNPJ;
	@FXML
	private TableColumn<Provider, String> tableColumnAddress;
	@FXML
	private TableColumn<Provider, Integer> tableColumnNumber;
	@FXML
	private TableColumn<Provider, String> tableColumnComplement;
	@FXML
	private TableColumn<Provider, String> tableColumnNeighborhood;
	@FXML
	private TableColumn<Provider, String> tableColumnCity;
	@FXML
	private TableColumn<Provider, String> tableColumnState;

	@FXML
	private Tab tabConsultProvider;
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
	private void onBtnSaveAction() {
		// Create a provider in model
		Provider provider = makeProvider();
		// The code don't need a value from textField.
		txtCode.setText("1");
		// Create a provider in Dao
		ProviderDAO providerDAO = DaoFactory.createProviderDAO();
		// Use SQL Command
		providerDAO.insert(provider);
		// Clean the elements in TextField.
		onBtnCleanAction();
		// Show a success message
		Alerts.showAlert("Message", null, "Registered provider!", AlertType.INFORMATION);

	}

	@FXML
	private void onBtnEditAction() {
		// Create a provider in model
		Provider provider = makeProvider();
		// Set an id getting the element from TableView
		provider.setId(Integer.parseInt(txtCode.getText()));
		// Create a provider in Dao
		ProviderDAO providerDAO = DaoFactory.createProviderDAO();
		// Use SQL Command
		providerDAO.update(provider);
		// Clean the elements in TextField.
		onBtnCleanAction();
		// Show a success message
		Alerts.showAlert("Message", null, "Edited provider!", AlertType.INFORMATION);

	}

	@FXML
	private void onBtnDeleteAction() {
		// Create a provider in model
		Provider provider = makeProvider();
		// Set an id getting the element from TableView
		provider.setId(Integer.parseInt(txtCode.getText()));
		// Create a provider in Dao
		ProviderDAO providerDAO = DaoFactory.createProviderDAO();
		// Use SQL Command
		providerDAO.delete(provider);
		// Clean the elements in TextField.
		onBtnCleanAction();
		// Show a success message
		Alerts.showAlert("Message", null, "Deleted provider!", AlertType.INFORMATION);
	}

	@FXML
	private void onBtnCleanAction() {
		// "" = not change
		// Clean all elements in TextField.

		txtCode.setText("");
		txtName.setText(null);
		txtEmail.setText(null);
		txtCEP.setText("");
		txtCNPJ.setText("");
		txtAddress.setText(null);
		txtNeighborhood.setText(null);
		txtCity.setText(null);
		txtCellphone.setText("");
		txtPhone.setText("");
		txtNumber.setText(null);
		txtComplement.setText(null);
		comboBoxUF.getSelectionModel().select(null);

	}

	/*
	 * When i'm in the first tab and i pressed enter in txtName, this method will be
	 * throw and it will fill all TextFields on first tab.
	 */
	@FXML
	private void onTxtNameKeyPressed(KeyEvent evt) {
		if (evt.getCode().equals(KeyCode.ENTER)) {
			// Create a clientDao.
			ProviderDAO providerDAO = DaoFactory.createProviderDAO();
			// Create a client list and use sql command findClientByName
			Provider p = providerDAO.findProviderByName(txtName.getText());
			setProvider(p);
		}

	}

	// Search a list of provider on the Consult Provider tab at the txtSearch only
	// tipping
	@FXML
	private void onTxtSearchKeyPressedAction() {
		// Create a providerDao.
		ProviderDAO providerDAO = DaoFactory.createProviderDAO();
		// Create a provider list and use sql command findbyName
		List<Provider> list = providerDAO.findbyName(txtSearch.getText());
		// Now load all my provider from insert to the my obsListProvider.
		obsListProvider = FXCollections.observableArrayList(list);
		// Set my table putting all providers him.
		tableViewProvider.setItems(obsListProvider);
	}

	@FXML
	private void onConsultCustomerChanged() {
		// IF consultCustomer is selected them load my tableView with all my provider,
		// and show them.
		if (tabConsultProvider.isSelected())
			updateTableViewClient();
	}

	/*
	 * When the mouse clicked in my TableView in tuple i want to go to personal data
	 * tab with values.
	 */
	@FXML
	private void onTableViewProviderMouseClicked() {
		// This function only will be executed if have an element in TableView, so
		// avoiding an exception.
		if (tableViewProvider.getSelectionModel().getSelectedItem() != null) {

			// Set the tab and change them.
			SingleSelectionModel<Tab> selectionModel = tabPaneProvider.getSelectionModel();
			selectionModel.select(0);

			// A temporary variable for save the selected cells in TableView.
			Provider provider = tableViewProvider.getSelectionModel().getSelectedItem();
			// In the personalDataTab i set these items.
			txtCode.setText(provider.getId().toString());
			txtName.setText(provider.getName().toString());
			txtCNPJ.setText(provider.getCnpj().toString());
			txtEmail.setText(provider.getEmail().toString());
			txtPhone.setText(provider.getPhone().toString());
			txtCellphone.setText(provider.getCellphone().toString());
			txtCEP.setText(provider.getCep().toString());
			txtAddress.setText(provider.getAddress().toString());
			txtNumber.setText(provider.getNumber().toString());
			txtComplement.setText(provider.getComplement().toString());
			txtNeighborhood.setText(provider.getNeighborhood().toString());
			txtCity.setText(provider.getCity().toString());
			comboBoxUF.setValue(provider.getState());

		}
	}

	// Get the form and make provider. This is for the insert sql command in
	// btnSaveAction method.
	private Provider makeProvider() {
		try {
			// The code don't need a value from textField.
			Integer code = Integer.parseInt("1");
			String name = txtName.getText().trim();
			String cnpj = txtCNPJ.getText();
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

			return new Provider(code, name, cnpj, email, phone, cellphone, cep, address, number, complement,
					neighborhood, city, state);
		}
		// If have any field empty, them throw this exception
		catch (NumberFormatException e) {
			throw new ControlException(e.getMessage(), "message", null, "fields cannot be empty", AlertType.ERROR,
					true);
		}

	}

	// This method exists for set all form (TextFields) on my first tab, using the
	// attributes of the provider.
	private void setProvider(Provider p) {
		try {
			txtCode.setText(p.getId().toString());
			txtName.setText(p.getName());
			txtEmail.setText(p.getEmail());
			txtCEP.setText(p.getCep());
			txtCNPJ.setText(p.getCnpj());
			txtAddress.setText(p.getAddress());
			txtNeighborhood.setText(p.getNeighborhood());
			txtCity.setText(p.getCity());
			txtCellphone.setText(p.getCellphone());
			txtPhone.setText(p.getPhone());
			txtNumber.setText(p.getNumber().toString());
			txtComplement.setText(p.getComplement());
			comboBoxUF.getSelectionModel().select(p.getState());
		} catch (NullPointerException e) {
			throw new ControlException(e.getMessage(), "message", null, "Provider not found!", AlertType.ERROR, true);
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
	 * columns to save my provider datas.
	 */
	private void initializeNodes() {

		// Initialize all columns at the my tableViewprovider to insert data later.
		tableColumnCode.setCellValueFactory(new PropertyValueFactory<>("id"));
		tableColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
		tableColumnCNPJ.setCellValueFactory(new PropertyValueFactory<>("cnpj"));
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
		Mask.maskCNPJ(txtCNPJ);
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

		Constraints.setTextFieldInteger(txtNumber);

		Constraints.setTextFieldMaxLength(txtCEP, 9);
		Constraints.setTextFieldMaxLength(txtCNPJ, 18);
	}

	// Update my TableView, So, having the data from the columns.
	private void updateTableViewClient() {

		// Create a providerDAO.
		ProviderDAO providerDAO = DaoFactory.createProviderDAO();
		// Create a provider list and use sql command findAll.
		List<Provider> list = providerDAO.findAll();
		// Now load all my provider from insert to the my obsListProvider.
		obsListProvider = FXCollections.observableArrayList(list);
		// Set my table putting all provider him.
		tableViewProvider.setItems(obsListProvider);

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
		} else {
			throw new ControlException("CEP not found", null, null, null, null, false);
		}
	}
}
