package br.com.SellControl.gui;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import br.com.SellControl.application.Program;
import br.com.SellControl.dao.EmployeeDAO;
import br.com.SellControl.util.Alerts;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class MainScreenControl implements Initializable {
	// For identify the userLogged
	public static String userLogged;
	// Format the date in American format.

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
	private Date d = new Date();

	@FXML
	private Button btnClient;
	@FXML
	private Button btnEmployee;
	@FXML
	private Button btnProvider;
	@FXML
	private Button btnProduct;
	@FXML
	private Button btnPointOfSales;
	@FXML
	private Button btnSalesHistory;
	@FXML
	private Button btnExit;

	@FXML
	private Label txtLoggedAs;
	// From my date
	@FXML
	private Label txtLoggedDate;

	@FXML
	public void onBtnClientAction() {
		loadView("/br/com/SellControl/gui/ClientRegistration.fxml");
	}

	@FXML
	public void onBtnEmployeeAction() {
		loadView("/br/com/SellControl/gui/EmployeeRegistration.fxml");
	}

	@FXML
	public void onBtnProviderAction() {
		loadView("/br/com/SellControl/gui/ProviderRegistration.fxml");
	}

	@FXML
	public void onbtnExitAction() {
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
		// Set date
		String format = sdf.format(d);
		txtLoggedDate.setText(format);

		// Set the user where i catch in EmployeeDAO logged in screen.
		txtLoggedAs.setText(userLogged);

	}

	public void LoginVerification() {
		Boolean isUser = EmployeeDAO.isUser;
		if (isUser.equals(true)) {
			btnProduct.setDisable(true);
			btnProvider.setDisable(true);
			btnPointOfSales.setDisable(true);
			btnSalesHistory.setDisable(true);
		}
	}

	private synchronized void loadView(String absoluteName) {
		try {
			// catch the screen, opening the screen in the parameter.
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			// Load the screen (Obsvisouly Anchor bcauz the FXML in the parameter is always
			// a
			// node AnchorPane).
			AnchorPane newAnchorPane = loader.load();
			// Catch the reference of this screen
			Scene mainScene = txtLoggedAs.getScene();
			/*
			 * Catch the reference of AnchorPane Principal Screen getRoot -> Get the first
			 * element of principal FXMl (AncrollPane) and get their content.
			 */
			AnchorPane mainAnchorPane = ((AnchorPane) mainScene.getRoot());
			// Save the reference to mainAnchorPane Children. (Vbox and Img)
			Node mainMenu = mainAnchorPane.getChildren().get(0);
			Node mainImage = mainAnchorPane.getChildren().get(1);
			// Clear all children from mainAnchorPane
			mainAnchorPane.getChildren().clear();
			/*
			 * Add the mainMenu (VBox) to principal screen and include all children from the
			 * screen in parameter
			 */

			mainAnchorPane.getChildren().add(mainMenu);
			mainAnchorPane.getChildren().add(mainImage);
			mainAnchorPane.getChildren().addAll(newAnchorPane.getChildren());
			// Set the AnchorPane and TabPane X and Y from Other FXML
			mainAnchorPane.getChildren().get(2).setLayoutX(256.0);
			mainAnchorPane.getChildren().get(2).setLayoutY(0.0);
			mainAnchorPane.getChildren().get(3).setLayoutX(256.0);
			mainAnchorPane.getChildren().get(3).setLayoutY(88.0);

		} catch (IOException e) {
			Alerts.showAlert("IO Exception", "Error loading view", e.getMessage(), AlertType.ERROR);

		}
	}
}
