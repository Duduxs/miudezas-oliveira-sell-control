package br.com.SellControl.gui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class PaymentControl implements Initializable {

	// Only for test
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

	}

	// Test
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		txtCash.setText("0");
		txtCard.setText("0");
		txtCheck.setText("0");
		txtTotal.setText(PoSControl.total.toString());
	}

}
