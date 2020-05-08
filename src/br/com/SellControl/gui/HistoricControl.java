package br.com.SellControl.gui;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

import br.com.SellControl.dao.DaoFactory;
import br.com.SellControl.dao.ItemSellDAO;
import br.com.SellControl.dao.SellDAO;
import br.com.SellControl.model.entities.ItemSell;
import br.com.SellControl.model.entities.Sell;
import br.com.SellControl.util.Alerts;
import br.com.SellControl.util.Mask;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class HistoricControl implements Initializable {

	private DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy/MM/dd");

	// First Pane
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

	// Second pane

	@FXML
	private TextField txtSellDate;
	@FXML
	private TextField txtTotalValue;

	@FXML
	private Button btnSearchByDate;

	@FXML
	private TableView<ItemSell> tableViewItemSell;
	@FXML
	private ObservableList<ItemSell> obsListItemSell;

	@FXML
	private TableColumn<ItemSell, Integer> tableColumnItemSellID;
	@FXML
	private TableColumn<ItemSell, String> tableColumnProduct;
	@FXML
	private TableColumn<ItemSell, String> tableColumnQuantityPurchased;
	@FXML
	private TableColumn<ItemSell, Double> tableColumValue;
	@FXML
	private TableColumn<ItemSell, String> tableColumnSubTotal;

	@FXML
	private Label lblWarning;

	// Search data (1 form) (First Pane)
	@FXML
	public void onBtnSearchAction() {
		// Show my tableView
		updateTableViewSell();
		// If tableView isEmpty, them show a message.
		if (tableViewSell.getItems().isEmpty())
			Alerts.showAlert("Message", null, "History not found!", AlertType.INFORMATION);
	}

	// Search data (2 form) pressed enter (First Pane)
	public void onTxtEndDateKeyPressed(KeyEvent evt) {
		// Show my tableView if i pressed Enter
		if (evt.getCode().equals(KeyCode.ENTER)) {
			onBtnSearchAction();

		}

	}

	// Search date (1 form) (Second Pane)
	@FXML
	public void onBtnSearchByDateAction() {
		LocalDate sellDate = LocalDate.parse(txtSellDate.getText(), format);
		// Create sellDAO
		SellDAO sellDAO = DaoFactory.createSellDAO();
		// Use the method and save in Double variable
		Double totalValue = sellDAO.selectTotalSalesByDate(sellDate);
		// Set text
		txtTotalValue.setText(totalValue.toString());
		// If txtTotalValue after find is empty, them throw an alert.
		if (txtTotalValue.getText().equals("0.0"))
			Alerts.showAlert("Message", null, "Value on this Date not found!", AlertType.INFORMATION);
	}

	// Search date (2 form) (Second Pane)
	@FXML
	public void onTxtSellDateKeyPressed(KeyEvent evt) {
		// Show my TotalValue if i pressed Enter
		if (evt.getCode().equals(KeyCode.ENTER)) {
			onBtnSearchByDateAction();
		}
	}

	// Method for initialize something in start of the program
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Mask.DateMask(txtStartDate);
		Mask.DateMask(txtEndDate);
		Mask.DateMask(txtSellDate);
		initializeNodes();

	}

	/*
	 * When the FXML Panel start this method will be responsible for load the
	 * columns to save my sell datas.
	 */
	private void initializeNodes() {
		// FirstPane
		// Initialize all columns at the my tableViewClient to insert data later.
		tableColumnID.setCellValueFactory(new PropertyValueFactory<>("id"));
		tableColumnSellDate.setCellValueFactory(new PropertyValueFactory<>("dateSell"));
		tableColumnClientName.setCellValueFactory(new PropertyValueFactory<>("ClientName"));
		tableColumTotalSales.setCellValueFactory(new PropertyValueFactory<>("totalSell"));
		tableColumnOBS.setCellValueFactory(new PropertyValueFactory<>("obs"));
		// Second Pane
		tableColumnItemSellID.setCellValueFactory(new PropertyValueFactory<>("id"));
		tableColumnProduct.setCellValueFactory(new PropertyValueFactory<>("ProductName"));
		tableColumnQuantityPurchased.setCellValueFactory(new PropertyValueFactory<>("quantity"));
		tableColumValue.setCellValueFactory(new PropertyValueFactory<>("ProductPrice"));
		tableColumnSubTotal.setCellValueFactory(new PropertyValueFactory<>("subtotal"));

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

	// Update my TableView, So, having the data from the columns.
	private void updateTableViewItemSell() {

		// This function only will be executed if have an element in TableView, so
		// avoiding an exception.
		if (tableViewSell.getSelectionModel().getSelectedItem() != null) {
			// A temporary variable for save the selected cells in TableView.
			Sell sell = tableViewSell.getSelectionModel().getSelectedItem();
			// Create a itemSellDao.
			ItemSellDAO itemSellDAO = DaoFactory.createItemSellDAO();
			// Create a itemsell list and use sql command selectAllItensbyId;
			List<ItemSell> list = itemSellDAO.selectAllItensById(sell.getId());
			// Now load all my ItemSell from insert to the my obsListItemSell.
			obsListItemSell = FXCollections.observableArrayList(list);
			// Set my table putting all ItemSell him.
			tableViewItemSell.setItems(obsListItemSell);

			// If i clicked on item, them make some things appears
			if (tableViewItemSell.getItems().isEmpty()) {
				lblWarning.setVisible(true);
				tableViewItemSell.setOpacity(0.12);
			} else {
				// If i not clicked on item, them make some things disappears
				lblWarning.setVisible(false);
				tableViewItemSell.setOpacity(1);
			}

		}
	}

	/*
	 * When the mouse clicked in my TableView in tuple i want to go to other Table
	 * with values.
	 */
	@FXML
	private void onTableViewSellMouseClicked() {
		updateTableViewItemSell();
	}
}
