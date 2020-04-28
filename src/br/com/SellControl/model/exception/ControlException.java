package br.com.SellControl.model.exception;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class ControlException extends RuntimeException{

	
	private static final long serialVersionUID = 1L;

	
	public ControlException(String msg, String title, String header, String content, AlertType type, Boolean show) {
		super(msg);
		if(show == true) {
		Alert alert = new Alert(type);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(content);
		alert.show();
		}
	}
}
