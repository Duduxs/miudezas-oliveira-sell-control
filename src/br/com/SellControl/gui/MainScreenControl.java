package br.com.SellControl.gui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

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
		loadAndSetIcons();
		// Set the username where i catch in EmployeeDAO logged in screen.
		txtLoggedAs.setText(userLogged);

	}

	@FXML
	public void onMiExitAction() {
		System.exit(1);

	}

	// This method load and set icons!
	public void loadAndSetIcons() {
		// Loading the icons
		Image imgClient = new Image(getClass().getResourceAsStream("/br/com/SellControl/img/ico/clientes.png"));
		Image imgSetting = new Image(getClass().getResourceAsStream("/br/com/SellControl/img/ico/configuracoes.png"));
		Image imgProvider = new Image(getClass().getResourceAsStream("/br/com/SellControl/img/ico/fornecedores.png"));
		Image imgEmployee = new Image(getClass().getResourceAsStream("/br/com/SellControl/img/ico/funcionarios.png"));
		Image imgProduct = new Image(getClass().getResourceAsStream("/br/com/SellControl/img/ico/produtos.png"));
		//Image imgExit = new Image(getClass().getResourceAsStream("/br/com/SellControl/img/ico/sair.png"));
		Image imgSell = new Image(getClass().getResourceAsStream("/br/com/SellControl/img/ico/vendas.png"));
		// Saving their in ImageView
		ImageView imgViewClient = new ImageView(imgClient);
		ImageView imgViewSetting = new ImageView(imgSetting);
		ImageView imgViewProvider = new ImageView(imgProvider);
		ImageView imgViewEmployee = new ImageView(imgEmployee);
		ImageView imgViewProduct = new ImageView(imgProduct);
		//ImageView imgViewExit = new ImageView(imgExit);
		ImageView imgViewSell = new ImageView(imgSell);

		// Setting icons on the menu
		menuClient.setGraphic(imgViewClient);
		menuEmployee.setGraphic(imgViewEmployee);
		menuProvider.setGraphic(imgViewProvider);
		menuProduct.setGraphic(imgViewProduct);
		menuSell.setGraphic(imgViewSell);
		menuSettings.setGraphic(imgViewSetting);


	}
}
