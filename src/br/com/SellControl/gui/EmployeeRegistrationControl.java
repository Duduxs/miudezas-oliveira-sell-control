package br.com.SellControl.gui;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import br.com.SellControl.dao.DaoFactory;
import br.com.SellControl.dao.EmployeeDAO;
import br.com.SellControl.model.entities.Employee;
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
import javafx.scene.control.PasswordField;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class EmployeeRegistrationControl implements Initializable {

	@FXML
	private PasswordField txtPassword;

	@FXML
	private TextField txtOffice;
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
	@FXML
	ComboBox<String> comboBoxAcess_level;

	// Variable for save all my States/UF in comboBox.
	private ObservableList<String> obsList;
	// Variable for save all my Employee in tableViewEmployee.
	private ObservableList<Employee> obsListEmployee;
	// Variable for save all my AcessLevel in comboBox.
	private ObservableList<String> obsListAcess;

	@FXML
	private TableView<Employee> tableViewEmployee;
	@FXML
	private TabPane tabPaneEmployee;

	@FXML
	private TableColumn<Employee, Integer> tableColumnCode;
	@FXML
	private TableColumn<Employee, String> tableColumnName;
	@FXML
	private TableColumn<Employee, String> tableColumnCPF;
	@FXML
	private TableColumn<Employee, String> tableColumnEmail;
	@FXML
	private TableColumn<Employee, String> tableColumnPhone;
	@FXML
	private TableColumn<Employee, String> tableColumnCellphone;
	@FXML
	private TableColumn<Employee, String> tableColumnCEP;
	@FXML
	private TableColumn<Employee, String> tableColumnAddress;
	@FXML
	private TableColumn<Employee, Integer> tableColumnNumber;
	@FXML
	private TableColumn<Employee, String> tableColumnComplement;
	@FXML
	private TableColumn<Employee, String> tableColumnNeighborhood;
	@FXML
	private TableColumn<Employee, String> tableColumnCity;
	@FXML
	private TableColumn<Employee, String> tableColumnState;
	@FXML
	private TableColumn<Employee, String> tableColumnPassword;
	@FXML
	private TableColumn<Employee, String> tableColumnOffice;
	@FXML
	private TableColumn<Employee, String> tableColumnAcessLevel;

	@FXML
	private Tab tabConsultEmployee;
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
	private Button btnSearchConsultEmployee;
	@FXML
	private Button btnSearchPersonalData;

	@FXML
	private void onBtnSaveAction() {
		// Create a employee in model
		Employee employee = makeEmployee();
		// The code don't need a value from textField.
		txtCode.setText("1");
		// Create a employee in Dao
		EmployeeDAO employeeDAO = DaoFactory.createEmployeeDAO();
		// Use SQL Command
		employeeDAO.insert(employee);
		// Clean the elements in TextField.
		onBtnCleanAction();
		// Show a success message
		Alerts.showAlert("Message", null, "Registered employee!", AlertType.INFORMATION);

	}

	@FXML
	private void onBtnEditAction() {
		// Create a employee in model
		Employee employee = makeEmployee();
		// Set an id getting the element from TableView
		employee.setId(Integer.parseInt(txtCode.getText()));
		// Create a employee in Dao
		EmployeeDAO employeeDAO = DaoFactory.createEmployeeDAO();
		// Use SQL Command
		employeeDAO.update(employee);
		// Clean the elements in TextField.
		onBtnCleanAction();
		// Show a success message
		Alerts.showAlert("Message", null, "Edited employee!", AlertType.INFORMATION);

	}

	@FXML
	private void onBtnDeleteAction() {
		// Create a employee in model
		Employee employee = makeEmployee();
		// Set an id getting the element from TableView
		employee.setId(Integer.parseInt(txtCode.getText()));
		// Create a employee in Dao
		EmployeeDAO emplyeeDAO = DaoFactory.createEmployeeDAO();
		// Use SQL Command
		emplyeeDAO.delete(employee);
		// Clean the elements in TextField.
		onBtnCleanAction();
		// Show a success message
		Alerts.showAlert("Message", null, "Deleted employee!", AlertType.INFORMATION);
	}

	@FXML
	private void onBtnCleanAction() {
		// "" = not change
		// Clean all elements in TextField.

		txtCode.setText("");
		txtName.setText(null);
		txtEmail.setText(null);
		txtPassword.setText(null);
		txtOffice.setText(null);
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
		comboBoxAcess_level.getSelectionModel().select(null);

	}

	// Search a list of employee on the Consult employee tab at the btn Search
	@FXML
	private void onBtnSearchConsultEmployeeAction() {
		// Create a employeeDao.
		EmployeeDAO employeeDAO = DaoFactory.createEmployeeDAO();
		// Create a employee list and use sql command findbyName
		List<Employee> list = employeeDAO.findbyName(txtSearch.getText());
		// Now load all my employee from insert to the my obsListEmployee.
		obsListEmployee = FXCollections.observableArrayList(list);
		// Set my table putting all clients him.
		tableViewEmployee.setItems(obsListEmployee);
	}

	/*
	 * When i'm in the first tab and i click in search, this method will be throw
	 * and it will fill all TextFields on first tab.
	 */
	@FXML
	private void onBtnSearchPersonalDataAction() {
		// Create a employeeDao.
		EmployeeDAO employeeDAO = DaoFactory.createEmployeeDAO();
		// Create a client list and use sql command findClientByName
		Employee e = employeeDAO.findEmployeeByName(txtName.getText());
		setEmployee(e);

	}

	// Search a list of employee on the Consult Employee tab at the txtSearch only
	// tipping
	@FXML
	private void onTxtSearchKeyPressedAction() {
		// Create a employeeDao.
		EmployeeDAO employeeDAO = DaoFactory.createEmployeeDAO();
		// Create a employee list and use sql command findbyName
		List<Employee> list = employeeDAO.findbyName(txtSearch.getText());
		// Now load all my employee from insert to the my obsListEmployee.
		obsListEmployee = FXCollections.observableArrayList(list);
		// Set my table putting all employee him.
		tableViewEmployee.setItems(obsListEmployee);
	}

	@FXML
	private void onConsultEmployeeChanged() {
		// IF consultEmployee is selected them load my tableView with all my employee,
		// and show them.
		if (tabConsultEmployee.isSelected())
			updateTableViewEmployee();
	}

	/*
	 * When the mouse clicked in my TableView in tuple i want to go to personal data
	 * tab with values.
	 */
	@FXML
	private void onTableViewEmployeeMouseClicked() {
		// This function only will be executed if have an element in TableView, so
		// avoiding an exception.
		if (tableViewEmployee.getSelectionModel().getSelectedItem() != null) {

			// Set the tab and change them.
			SingleSelectionModel<Tab> selectionModel = tabPaneEmployee.getSelectionModel();
			selectionModel.select(0);

			// A temporary variable for save the selected cells in TableView.
			Employee employee = tableViewEmployee.getSelectionModel().getSelectedItem();
			// In the personalDataTab i set these items.
			txtCode.setText(employee.getId().toString());
			txtName.setText(employee.getName().toString());
			txtCPF.setText(employee.getCpf().toString());
			txtEmail.setText(employee.getEmail().toString());
			txtPassword.setText(employee.getPassword().toString());
			txtOffice.setText(employee.getOffice().toString());
			comboBoxAcess_level.setValue(employee.getAcess_level());
			txtPhone.setText(employee.getPhone().toString());
			txtCellphone.setText(employee.getCellphone().toString());
			txtCEP.setText(employee.getCep().toString());
			txtAddress.setText(employee.getAddress().toString());
			txtNumber.setText(employee.getNumber().toString());
			txtComplement.setText(employee.getComplement().toString());
			txtNeighborhood.setText(employee.getNeighborhood().toString());
			txtCity.setText(employee.getCity().toString());
			comboBoxUF.setValue(employee.getState());

		}
	}

	// Get the form and make employee. This is for the insert sql command in
	// btnSaveAction method.
	private Employee makeEmployee() {
		try {
			// The code don't need a value from textField.
			Integer code = Integer.parseInt("1");
			String name = txtName.getText();
			String cpf = txtCPF.getText();
			String email = txtEmail.getText();
			String password = txtPassword.getText();
			String office = txtOffice.getText();
			String acess_level = comboBoxAcess_level.getSelectionModel().getSelectedItem();
			String phone = txtPhone.getText();
			String cellphone = txtCellphone.getText();
			String cep = txtCEP.getText();
			String address = txtAddress.getText();
			Integer number = Integer.parseInt(txtNumber.getText());
			String complement = txtComplement.getText();
			String neighborhood = txtNeighborhood.getText();
			String city = txtCity.getText();
			String state = comboBoxUF.getSelectionModel().getSelectedItem();

			return new Employee(code, name, cpf, email, password, office, acess_level, phone, cellphone, cep, address,
					number, complement, neighborhood, city, state);
		}
		// If have any field empty, them throw this exception
		catch (NumberFormatException e) {
			throw new ControlException(e.getMessage(), "message", null, "fields cannot be empty", AlertType.ERROR, true);
		}

	}

	// This method exists for set all form (TextFields) on my first tab, using the
	// attributes of the employee.
	private void setEmployee(Employee e) {
		try {
			txtCode.setText(e.getId().toString());
			txtName.setText(e.getName());
			txtEmail.setText(e.getEmail());
			txtPassword.setText(e.getPassword());
			txtOffice.setText(e.getOffice());
			comboBoxAcess_level.getSelectionModel().select(e.getAcess_level());
			txtCEP.setText(e.getCep());
			txtCPF.setText(e.getCpf());
			txtAddress.setText(e.getAddress());
			txtNeighborhood.setText(e.getNeighborhood());
			txtCity.setText(e.getCity());
			txtCellphone.setText(e.getCellphone());
			txtPhone.setText(e.getPhone());
			txtNumber.setText(e.getNumber().toString());
			txtComplement.setText(e.getComplement());
			comboBoxUF.getSelectionModel().select(e.getState());
		} catch (NullPointerException n) {
			throw new ControlException(n.getMessage(), "message", null, "Employee not found!", AlertType.ERROR, true);
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
		//Include in my list the acess from system.
		List<String> acess = Arrays.asList("User", "Administrator");

		obsListAcess = FXCollections.observableArrayList(acess);
		comboBoxAcess_level.setItems(obsListAcess);
		// User in comboBox will be select first
		comboBoxAcess_level.getSelectionModel().select(0);
	}

	/*
	 * When the FXML Panel start this method will be responsible for load the
	 * columns to save my employee datas.
	 */
	private void initializeNodes() {

		// Initialize all columns at the my tableViewEmployee to insert data later.
		tableColumnCode.setCellValueFactory(new PropertyValueFactory<>("id"));
		tableColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
		tableColumnCPF.setCellValueFactory(new PropertyValueFactory<>("cpf"));
		tableColumnEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
		tableColumnPassword.setCellValueFactory(new PropertyValueFactory<>("password"));
		tableColumnOffice.setCellValueFactory(new PropertyValueFactory<>("office"));
		tableColumnAcessLevel.setCellValueFactory(new PropertyValueFactory<>("acess_level"));
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
		Constraints.setTextFieldMaxLength(txtOffice, 15);
		Constraints.setTextFieldMaxLength(txtPassword, 10);
	}

	// Update my TableView, So, having the data from the columns.
	private void updateTableViewEmployee() {

		// Create a employeeDao.
		EmployeeDAO employeeDAO = DaoFactory.createEmployeeDAO();
		// Create a employee list and use sql command findAll.
		List<Employee> list = employeeDAO.findAll();
		// Now load all my employee from insert to the my obsListEmployee.
		obsListEmployee = FXCollections.observableArrayList(list);
		// Set my table putting all employee him.
		tableViewEmployee.setItems(obsListEmployee);

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
		else {
			throw new ControlException("CPF not found", null, null, null, null, false);
		}
	}

}
