package br.com.SellControl.gui;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import br.com.SellControl.dao.DaoFactory;
import br.com.SellControl.dao.SellDAO;
import br.com.SellControl.model.entities.Client;
import br.com.SellControl.model.entities.Sell;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class PaymentControl implements Initializable {

	private Client client = new Client();

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
		
		//Saving my sell in bd
		SellDAO sellDAO = DaoFactory.createSellDAO();
		sellDAO.insert(sell);

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		txtCash.setText("0");
		txtCard.setText("0");
		txtCheck.setText("0");
		txtTotal.setText(PoSControl.total.toString());
		client = PoSControl.client;
	}

}
