package br.com.SellControl.gui;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import br.com.SellControl.util.Alerts;
import br.com.SellControl.util.Constraints;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;

public class LoginScreenControl implements Initializable {

	@FXML
	TextField txtEmail;
	@FXML
	TextField txtPassword;

	@FXML
	Button btnLogin;
	@FXML
	Button btnExit;
	
	public void onBtnExitAction() {
		Optional<ButtonType> result = Alerts.showConfirmation("Attention!", "Do you really want to do that?");
		if(result.get() == ButtonType.OK) {
			System.exit(1);
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
