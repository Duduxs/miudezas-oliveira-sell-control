package br.com.SellControl.gui;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
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
	// Format the date in American format.
<<<<<<< HEAD
		private SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		private Date d = new Date();
=======
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
	private Date d = new Date();

>>>>>>> 9d4e633b2ca80cc8fb7638391e80c9549d4ce371
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
	private Menu menuConfigurations;

	@FXML
	private MenuItem miConsultClient;
	@FXML
	private MenuItem miConsultEmployee;
	@FXML
	private MenuItem miConsultProvider;
	@FXML
	private MenuItem miConsultProduct;
	@FXML
	private MenuItem miPoS;
	@FXML
	private MenuItem miSalesHistory;
	@FXML
	private MenuItem miChangeUser;
	@FXML
	private MenuItem miExit;

	@FXML
	private Label txtLoggedAs;
	@FXML
<<<<<<< HEAD
	private Label txtLoggedDate;
=======
	private Label txtDate;
>>>>>>> 9d4e633b2ca80cc8fb7638391e80c9549d4ce371

	@FXML
	public void onMiChangeUserAction() {
		// Get actual stage (use any object)
		Stage MainScreen = (Stage) txtLoggedAs.getScene().getWindow();
		// Close
		MainScreen.close();
		// MainStage show
		Program.getMainStage().show();

	}

	@FXML
	public void onMiExitAction() {
		Optional<ButtonType> result = Alerts.showConfirmation("Attention!", "Do you really want to do that?");
		if (result.get() == ButtonType.OK) {
			System.exit(1);
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
<<<<<<< HEAD
		LoginVerification();
		//Set date
		String format = sdf.format(d);
		txtLoggedDate.setText(format);
=======
>>>>>>> 9d4e633b2ca80cc8fb7638391e80c9549d4ce371

		// Set date
		String format = sdf.format(d);
		txtDate.setText(format);

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
}
