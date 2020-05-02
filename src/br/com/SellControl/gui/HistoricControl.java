package br.com.SellControl.gui;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import br.com.SellControl.dao.DaoFactory;
import br.com.SellControl.dao.SellDAO;
import br.com.SellControl.model.entities.Sell;
import br.com.SellControl.util.Alerts;
import br.com.SellControl.util.Mask;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class HistoricControl implements Initializable {

	@FXML
	private TextField txtStartDate;
	@FXML
	private TextField txtEndDate;

	@FXML
	private Button btnSearch;

	@FXML
	private TableView<Sell> tableViewSell;
	@FXML
	private ObservableList<Sell> obsListSell;

	@FXML
	private TableColumn<Sell, Integer> tableColumnID;
	@FXML
	private TableColumn<Sell, String> tableColumnSellDate;
	@FXML
	private TableColumn<Sell, String> tableColumnClientName;
	@FXML
	private TableColumn<Sell, Double> tableColumTotalSales;
	@FXML
	private TableColumn<Sell, String> tableColumnOBS;

	// Search data (1 form)
	@FXML
	public void onBtnSearchAction() {
		// Show my tableView
		updateTableViewSell();
		// If tableView isEmpty, them show a message.
		if (tableViewSell.getItems().isEmpty())
			Alerts.showAlert("Message", null, "History not found!", AlertType.INFORMATION);
	}

	// Search data (2 form) pressed enter
	public void onTxtEndDateKeyPressed(KeyEvent evt) {
		// Show my tableView if i pressed Enter
		if (evt.getCode().equals(KeyCode.ENTER)) {
			updateTableViewSell();
			// If tableView isEmpty, them show a message.
			if (tableViewSell.getItems().isEmpty())
				Alerts.showAlert("Message", null, "History not found!", AlertType.INFORMATION);

		}

	}
	// Method for intialize something in start of the program
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Mask.DateMask(txtStartDate);
		Mask.DateMask(txtEndDate);
		initializeNodes();

	}

	/*
	 * When the FXML Panel start this method will be responsible for load the
	 * columns to save my sell datas.
	 */
	private void initializeNodes() {

		// Initialize all columns at the my tableViewClient to insert data later.
		tableColumnID.setCellValueFactory(new PropertyValueFactory<>("id"));
		tableColumnSellDate.setCellValueFactory(new PropertyValueFactory<>("dateSell"));
		tableColumnClientName.setCellValueFactory(new PropertyValueFactory<>("ClientName"));
		tableColumTotalSales.setCellValueFactory(new PropertyValueFactory<>("totalSell"));
		tableColumnOBS.setCellValueFactory(new PropertyValueFactory<>("obs"));

	}

	// Update my TableView, So, having the data from the columns.
	private void updateTableViewSell() {

		// Create a sellDao.
		SellDAO sellDAO = DaoFactory.createSellDAO();
		// Create a sell list and use sql command selectSellByDate.
		List<Sell> list = sellDAO.selectSellByDate(txtStartDate.getText(), txtEndDate.getText());
		// Now load all my Sell from insert to the my obsListSell.
		obsListSell = FXCollections.observableArrayList(list);
		// Set my table putting all Sell him.
		tableViewSell.setItems(obsListSell);

	}

}
