package br.com.SellControl.gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXPasswordField;

import br.com.SellControl.application.Program;
import br.com.SellControl.dao.DaoFactory;
import br.com.SellControl.dao.EmployeeDAO;
import br.com.SellControl.model.entities.Employee;
import br.com.SellControl.model.exception.ControlException;
import br.com.SellControl.util.Alerts;
import br.com.SellControl.util.Constraints;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class LoginScreenControl implements Initializable {
	private Double posX = 0.0;
	private Double posY = 0.0;

	private static Stage mainScreenStage;
	private static Scene mainScreenScene;

	@FXML
	private AnchorPane anchorPaneLoginScreen;

	@FXML
	private TextField txtEmail;
	@FXML
	private TextField txtPassword;
	// User Register//
	@FXML
	private TextField txtName;
	@FXML
	private JFXPasswordField txtRegisterPassword;
	@FXML
	private TextField txtRegisterEmail;
	@FXML
	ComboBox<String> comboBoxAcess_level;
	// Variable for save all my AcessLevel in comboBox.
	private ObservableList<String> obsListAcess;

	@FXML
	private Label lblSignIn;
	@FXML
	private Label lblUserRegister;
	@FXML
	private ImageView imgWomen;

	@FXML
	private Button btnCreateAccount;

	// User Register //

	@FXML
	private Label lblSignUp;
	@FXML
	private Label lblUserLogin;
	@FXML
	private Pane pane;

	@FXML
	private Button btnLogin;

	@FXML
	private ImageView imgMen;
	
	//Lbl panel
	@FXML
	private Label lblOne;
	@FXML
	private Label lblTwo;
	@FXML
	private Label lblThree;
	
	
	//Lbl panel

	// gets
	public static Stage getMainScreenStage() {
		return mainScreenStage;
	}

	public static Scene getMainScreenScene() {
		return mainScreenScene;
	}

	// For dragging the screen
	@FXML
	public void setOnMousePressed() {
		Program.getMainScene().setOnMousePressed(event -> {

			posX = Program.getMainStage().getX() - event.getScreenX();
			posY = Program.getMainStage().getY() - event.getScreenY();
		});

	}

	@FXML
	public void setOnMouseDragged() {
		Program.getMainScene().setOnMouseDragged(event -> {

			Program.getMainStage().setX(event.getScreenX() + posX);
			Program.getMainStage().setY(event.getScreenY() + posY);
		});

	}
	// For Dragging the screen

	// Closing the window
	@FXML
	public void onXMouseClickedAction() {

		Optional<ButtonType> result = Alerts.showConfirmation("Attention!", "Do you really want to do that?");
		if (result.get() == ButtonType.OK) {
			System.exit(1);
		}
	}

	// First form Login (Clicked on btn Login)
	@FXML
	public void onBtnLoginAction() {

		// Create employeeDAO and call EmployeeLogin passing the field email and
		// password to the bd
		EmployeeDAO employeeDAO = DaoFactory.createEmployeeDAO();
		employeeDAO.EmployeeLogin(txtEmail.getText(), txtPassword.getText());
		// If the login is true then show a new window
		if (employeeDAO.getLoginVerification()) {
			try {
				// Hide the loginScreen, hide, not close.
				Program.getMainStage().hide();
				txtEmail.setText("");
				txtPassword.setText("");
				// Open and get the MainScreenFXML.
				Parent parent = FXMLLoader.load(getClass().getResource("/br/com/SellControl/gui/Main.fxml"));

				Stage stage = new Stage();
				mainScreenStage = stage;
				Scene scene = new Scene(parent);
				mainScreenScene = scene;
				removeBar();
				stage.setScene(scene);
				stage.initStyle(StageStyle.TRANSPARENT);
				stage.show();

			} catch (IOException e) {
				throw new ControlException(e.getMessage(), null, null, null, null, false);
			}
		}

	}

	// Second form Login (Pressed Enter in TXT or btn login)
	@FXML
	public void onEnterPressed(KeyEvent event) {
		if (event.getCode() == KeyCode.ENTER) {
			onBtnLoginAction();
		}
	}

	// Method for create account
	@FXML
	public void onbtnCreateAccountAction() {
		// Create a employee in model
		Employee employee = makeEmployee();
		// Create a employee in Dao
		EmployeeDAO employeeDAO = DaoFactory.createEmployeeDAO();
		// Use SQL Command
		employeeDAO.insert(employee);
		// Clean the elements in TextField.
		onBtnCleanAction();
		initializeComboBox();
		// Show a success message
		Alerts.showAlert("Message", null, "Registered user!", AlertType.INFORMATION);
	}

	// Switch Pane
	@FXML
	public void onLblSignUpMouseClicked() {
		// Set pane X to (345.0 ->) and all Items from SignIn invisible and all items
		// from signUp Visible.

		pane.setLayoutX(345.0);
		lblOne.setText("Hello, Friend !");
		lblTwo.setText("Enter your personal details");
		lblThree.setText("and start journey with us");
		imgMen.setVisible(false);
		lblUserLogin.setVisible(false);
		txtEmail.setVisible(false);
		txtPassword.setVisible(false);
		btnLogin.setVisible(false);
		lblSignUp.setVisible(false);

		imgWomen.setVisible(true);
		lblUserRegister.setVisible(true);
		txtRegisterEmail.setVisible(true);
		txtRegisterPassword.setVisible(true);
		txtName.setVisible(true);
		comboBoxAcess_level.setVisible(true);
		btnCreateAccount.setVisible(true);
		lblSignIn.setVisible(true);
		// On changed, clear all txt too.
		onBtnCleanAction();

	}

	@FXML
	public void onLblSignInMouseClicked() {
		// Set pane X (0.0 <-) and all Items from SignUp invisible and all items from
		// signIn Visible.
		pane.setLayoutX(0.0);
		lblOne.setText("Welcome Back !");
		lblTwo.setText("Welcome back to the system.");
		lblThree.setText("Have a good experience.");
		imgWomen.setVisible(false);
		lblUserRegister.setVisible(false);
		txtRegisterEmail.setVisible(false);
		txtRegisterPassword.setVisible(false);
		txtName.setVisible(false);
		comboBoxAcess_level.setVisible(false);
		btnCreateAccount.setVisible(false);
		lblSignIn.setVisible(false);

		imgMen.setVisible(true);
		lblUserLogin.setVisible(true);
		txtEmail.setVisible(true);
		txtPassword.setVisible(true);
		btnLogin.setVisible(true);
		lblSignUp.setVisible(true);
		// On changed, clear all txt too.
		onBtnCleanAction();
	}
	// Switch Pane

	// Make employee for insert in create account.
	private Employee makeEmployee() {
		try {
			// The code don't need a value from textField.
			String name = txtName.getText().trim();
			String password = txtRegisterPassword.getText();
			String email = txtRegisterEmail.getText();
			String acess_level = comboBoxAcess_level.getSelectionModel().getSelectedItem();

			return new Employee(1, name, "111-111-111.11", email, password, "1", acess_level, "(11)1111-1111",
					"(11)1111-1111", "11111-111", "1", 1, "1", "1", "1", "1");
		}
		// If have any field empty, them throw this exception
		catch (NumberFormatException e) {
			throw new ControlException(e.getMessage(), "message", null, "fields cannot be empty", AlertType.ERROR,
					true);
		}

	}

	// Clean all my textFields/ CB
	private void onBtnCleanAction() {
		// "" = not change
		// Clean all elements in TextField.

		txtName.setText(null);
		txtEmail.setText(null);
		txtPassword.setText(null);
		txtRegisterEmail.setText(null);
		txtRegisterPassword.setText(null);
		comboBoxAcess_level.getSelectionModel().select(0);

	}

	// Remove the iconBar
	public void removeBar() {
		mainScreenStage.resizableProperty().setValue(Boolean.FALSE);
		mainScreenStage.initStyle(StageStyle.UTILITY);
		mainScreenStage.initStyle(StageStyle.UNDECORATED);
	}

	// Method for intialize something in start of the program
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initializeConstraints();
		initializeComboBox();

	}

	// Set max lenght for my TextFields
	private void initializeConstraints() {
		Constraints.setTextFieldMaxLength(txtName, 20);
		Constraints.setTextFieldMaxLength(txtEmail, 32);
		Constraints.setTextFieldMaxLength(txtRegisterEmail, 32);
		Constraints.setTextFieldMaxLength(txtPassword, 10);
		Constraints.setTextFieldMaxLength(txtRegisterPassword, 10);

	}

	// Initialize my CB with User/ADM or only User.
	private void initializeComboBox() {
		EmployeeDAO employeeDAO = DaoFactory.createEmployeeDAO();
		List<String> acess = new ArrayList<>();
		if (employeeDAO.selectADM() == 0) {
			// Include in my list the acess from system.
			acess = Arrays.asList("User", "Adm");
		} else
			acess = Arrays.asList("User");

		obsListAcess = FXCollections.observableArrayList(acess);
		comboBoxAcess_level.setItems(obsListAcess);
		// User in comboBox will be select first
		comboBoxAcess_level.getSelectionModel().select(0);
	}

}
