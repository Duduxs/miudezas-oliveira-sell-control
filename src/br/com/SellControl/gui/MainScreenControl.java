package br.com.SellControl.gui;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import br.com.SellControl.application.Program;
import br.com.SellControl.dao.EmployeeDAO;
import br.com.SellControl.util.Alerts;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

public class MainScreenControl implements Initializable {
	// For identify the userLogged
	public static String userLogged;

	@FXML
	private MenuBar menuBarMain;

	@FXML
	private Menu menuClient;
	@FXML
	private Menu menuEmployee;
	@FXML
	private Menu menuProvider;
	@FXML
	private Menu menuProduct;
	@FXML
	private Menu menuSell;
	@FXML
	private Menu menuSettings;

	@FXML
	private MenuItem miClientControl;
	@FXML
	private MenuItem miEmployeeControl;
	@FXML
	private MenuItem miProviderControl;
	@FXML
	private MenuItem miStockControl;
	@FXML
	private MenuItem miProductConsultation;
	@FXML
	private MenuItem miOpenPOS;
	@FXML
	private MenuItem miSalesHistory;
	@FXML
	private MenuItem miChangeUser;
	@FXML
	private MenuItem miAbout;
	@FXML
	private MenuItem miExit;
	@FXML
	private Label txtLoggedAs;

	@FXML
	public void onMiChangeUserAction() {
		// Get actual stage (use any object)
		Stage MainScreen = (Stage) txtLoggedAs.getScene().getWindow();
		// Close
		MainScreen.close();
		// MainStage show
		Program.getMainStage().show();

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		LoginVerification();

		// Set the user where i catch in EmployeeDAO logged in screen.
		txtLoggedAs.setText(userLogged);

	}

	public void LoginVerification() {
		Boolean isUser = EmployeeDAO.isUser;
		if (isUser.equals(true)) {
			menuEmployee.setDisable(true);
			menuProduct.setDisable(true);
			menuProvider.setDisable(true);
			menuSell.setDisable(true);

		}
	}

	@FXML
	public void onMiExitAction() {
		Optional<ButtonType> result = Alerts.showConfirmation("Attention!", "Do you really want to do that?");
		if (result.get() == ButtonType.OK) {
			System.exit(1);
		}
	}

}
