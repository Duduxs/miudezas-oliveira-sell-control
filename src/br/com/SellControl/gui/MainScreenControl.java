package br.com.SellControl.gui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

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
	private MenuItem miDayPosition;
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

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// Set the username where i catch in EmployeeDAO logged in screen.
		txtLoggedAs.setText(userLogged);

	}

	@FXML
	public void onMiExitAction() {
		System.exit(1);

	}

}
