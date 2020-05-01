package br.com.SellControl.gui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

public class PaymentControl implements Initializable {

	PoSControl x = new PoSControl();
	
	// Only for test
	@FXML
	public TextField txtTotal;
	
	Double xy = x.total1;

	// Test
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		txtTotal.setText(xy.toString());
	}

}
