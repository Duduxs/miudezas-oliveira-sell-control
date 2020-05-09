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
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
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

	@FXML
	private Button btnLogin;
	
	public static Stage getMainScreenStage() {
		return mainScreenStage;
	}
	
	public static Scene getMainScreenScene() {
		return mainScreenScene;
	}

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

	@FXML
	public void onXMouseClickedAction() {

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
	public void removeBar() {
		mainScreenStage.resizableProperty().setValue(Boolean.FALSE);
		mainScreenStage.initStyle(StageStyle.UTILITY);
		mainScreenStage.initStyle(StageStyle.UNDECORATED);
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
