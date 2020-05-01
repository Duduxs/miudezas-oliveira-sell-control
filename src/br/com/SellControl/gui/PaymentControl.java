package br.com.SellControl.gui;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import br.com.SellControl.dao.DaoFactory;
import br.com.SellControl.dao.ItemSellDAO;
import br.com.SellControl.dao.ProductDAO;
import br.com.SellControl.dao.SellDAO;
import br.com.SellControl.model.entities.Client;
import br.com.SellControl.model.entities.ItemSell;
import br.com.SellControl.model.entities.Product;
import br.com.SellControl.model.entities.Sell;
import br.com.SellControl.util.Alerts;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class PaymentControl implements Initializable {

	private Client client = new Client();

	private TableView<Product> tableViewShopping;
	PoSControl pos = new PoSControl();

	@FXML
	private TextField txtTotal;
	@FXML
	private TextField txtCash;
	@FXML
	private TextField txtCheck;
	@FXML
	private TextField txtRest;
	@FXML
	private TextField txtCard;
	@FXML
	private TextArea txtObs;

	@FXML
	private Button btnFinalize;

	@FXML
	public void onBtnFinalizeAction() {
		Double pcard, pcheck, pcash, totalPay, totalSell, rest;
		// Get the txtField
		pcard = Double.parseDouble(txtCard.getText());
		pcheck = Double.parseDouble(txtCheck.getText());
		pcash = Double.parseDouble(txtCash.getText());
		totalSell = Double.parseDouble(txtTotal.getText());
		// Sum the totalPay
		totalPay = pcard + pcheck + pcash;
		// Calc the rest
		rest = totalPay - totalSell;
		String.format("%.2f", rest);
		// Set
		txtRest.setText(rest.toString());
		// Client_id
		Sell sell = new Sell();
		sell.setClient(client);
		// Get date
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String now = sdf.format(date);
		// Pass the date and txt total,obs
		sell.setDateSell(now);
		sell.setTotalSell(totalSell);
		sell.setObs(txtObs.getText());

		// Create a sellDAO
		SellDAO sellDAO = DaoFactory.createSellDAO();

		// Saving my sell in bd if the client have balance for purchase.

		if (totalPay >= totalSell) {
			sellDAO.insert(sell);
			Alerts.showAlert("Message", null, "Items purchased!", AlertType.INFORMATION);
		} else
			Alerts.showAlert("Message", null, "Enough balance!", AlertType.ERROR);

		// Return the last id from sell
		sell.setId(sellDAO.selectLastSell());

		// get the Columns tableview
		TableColumn<Product, ?> col1 = tableViewShopping.getColumns().get(0);
		TableColumn<Product, ?> col2 = tableViewShopping.getColumns().get(2);
		TableColumn<Product, ?> col3 = tableViewShopping.getColumns().get(4);

		// Register products in table itemSell
		for (int i = 0; i < tableViewShopping.getItems().size(); i++) {
			
			int qtd_stock, qtd_buy, qtd_att;
			
			Product product = tableViewShopping.getItems().get(i);
			ProductDAO productDAO = DaoFactory.createProductDAO();
			
			// Get the rows Tableview
			
			ItemSell item = new ItemSell();
			item.setSell(sell);
			// Set the row and columns
			product.setId((Integer) col1.getCellObservableValue(product).getValue());
			item.setProduct(product);
			item.setQuantity((Integer) col2.getCellObservableValue(product).getValue());
			item.setSubtotal((Double) col3.getCellObservableValue(product).getValue());
			
			//Decrease my stock//
			
			// Get the actual qtd in stock
			qtd_stock = productDAO.returnActualStock(product.getId());
			// qtd_buy is equals a tableViewShopping, i mean qtd_actual in tableView in the PoSControl.
			qtd_buy = (Integer) col2.getCellObservableValue(product).getValue();
			// qtd att is equal a actual qtd in stock minus stock in tableview.
			qtd_att = qtd_stock - qtd_buy;
			// Finally decrease my stock.
			productDAO.decreaseStock(product.getId(), qtd_att);
			
			
			
			// Insert them in my BD
			ItemSellDAO itemSellDAO = DaoFactory.createItemSellDAO();
			itemSellDAO.insert(item);
		}
		
		

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		txtCash.setText("0");
		txtCard.setText("0");
		txtCheck.setText("0");
		txtTotal.setText(PoSControl.total.toString());
		client = PoSControl.client;
		tableViewShopping = PoSControl.tableViewPointOfSell2;

	}

}
