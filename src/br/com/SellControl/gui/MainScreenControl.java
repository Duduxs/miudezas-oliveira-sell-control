package br.com.SellControl.gui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class MainScreenControl implements Initializable {

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
	private Menu menuExit;
	
	@FXML
	private MenuItem menuItemClientControl;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		loadIcons();

	}

	
	public void loadIcons() {
		Image imgClient = new Image(getClass().getResourceAsStream("/br/com/SellControl/img/ico/clientes.png"));
		ImageView imgViewClient = new ImageView(imgClient);
		imgViewClient.setFitWidth(15);
		imgViewClient.setFitHeight(15);

		menuItemClientControl.setGraphic(imgViewClient);

	}

}
