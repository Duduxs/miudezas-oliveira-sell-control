package br.com.SellControl.gui;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import br.com.SellControl.application.Program;
import br.com.SellControl.dao.DaoFactory;
import br.com.SellControl.dao.EmployeeDAO;
import br.com.SellControl.model.exception.ControlException;
import br.com.SellControl.util.Alerts;
import br.com.SellControl.util.Constraints;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class LoginScreenControl implements Initializable {

	@FXML
	private AnchorPane anchorPaneLoginScreen;

	@FXML
	private TextField txtEmail;
	@FXML
	private TextField txtPassword;

	@FXML
	private Button btnLogin;
	@FXML
	private Button btnExit;

	@FXML
	public void onBtnExitAction() {

		Optional<ButtonType> result = Alerts.showConfirmation("Attention!", "Do you really want to do that?");
		if (result.get() == ButtonType.OK) {
			System.exit(1);
		}
	}

	// First form Login ( Clicked on btn Login )
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
				// Open and get the MainScreenFXML.
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/br/com/SellControl/gui/MainScreen.fxml"));
				// Load the screen and put in root variable.
				Parent root = (Parent) loader.load();
				
				Stage stage = new Stage();
				Scene scene = new Scene(root);
				stage.setScene(scene);
				//Not resize screen and size my screen to scene
				stage.setResizable(false);
				stage.sizeToScene();
				stage.show();

				Alerts.showAlert("message", null, "Welcome!", AlertType.INFORMATION);

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

	// Method for intialize something in start of the program
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initializeConstraints();

	}

	// Set max lenght for my TextFields
	private void initializeConstraints() {
		Constraints.setTextFieldMaxLength(txtEmail, 32);
		Constraints.setTextFieldMaxLength(txtPassword, 10);

	}

}
