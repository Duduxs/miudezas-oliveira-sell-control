package br.com.SellControl.application;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Program extends Application {

	 
	@Override
	public void start(Stage primaryStage) {
		try {

			Parent parent = FXMLLoader.load(getClass().getResource("/br/com/SellControl/gui/LoginScreen.fxml"));
			Scene scene = new Scene(parent);
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.show();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		launch(args);

	}

}
