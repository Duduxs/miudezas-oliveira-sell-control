package br.com.SellControl.gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import br.com.SellControl.model.entities.Client;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class ClienteRegistrationControl implements Initializable {

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
	private TextField txtLandline;
	@FXML
	private TextField txtNumber;
	@FXML
	private TextField txtComplement;

	@FXML
	private ComboBox<String> comboBoxUF;

	private ObservableList<String> obsList;

	@FXML
	private Button btnNew;
	@FXML
	private Button btnEdit;
	@FXML
	private Button btnSave;
	@FXML
	private Button btnDelete;

	@FXML
	public void onBtnSaveAction() {

		Integer code = Integer.parseInt(txtCode.getText());
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

		List<Client> c = new ArrayList<>();
		c.add(new Client(code, name, cpf, email, phone, cellphone, cep, address, number, complement, neighborhood, city,
				state));

		for (Client x : c) {
			System.out.println(x);
		}

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		List<String> test = new ArrayList<>();
		test.add("PE");
		test.add("SP");
		test.add("RJ");

		obsList = FXCollections.observableArrayList(test);
		comboBoxUF.setItems(obsList);

	}

}
