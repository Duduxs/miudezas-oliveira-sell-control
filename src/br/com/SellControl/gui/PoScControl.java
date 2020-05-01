package br.com.SellControl.gui;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

public class PoScControl implements Initializable {

	@FXML
	private TextField txtDate;
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		setDate();
		
	}
	
	
	//Load the actual date form system.
	public void setDate() {
		// Create a new date
		Date now = new Date();
		// Use American format
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		// Put the new date in American format
		String dateformated = sdf.format(now);
		// Set my txtDate
		txtDate.setText(dateformated);
		
	}

}
