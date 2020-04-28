package br.com.SellControl.application;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Program extends Application {

	private static Stage mainStage;
	 
	@Override
	public void start(Stage primaryStage) {
		try {

			Parent parent = FXMLLoader.load(getClass().getResource("/br/com/SellControl/gui/MainScreen.fxml"));
			Scene scene = new Scene(parent);
			primaryStage.setScene(scene);
			//Not resizable and size my screen to scene
			primaryStage.setResizable(false);
			primaryStage.sizeToScene();
			primaryStage.show();
			//Catch a reference to primaryStage.
			mainStage = primaryStage;

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	public static Stage getMainStage() {
		return mainStage;
	}

	public static void main(String[] args) {
		launch(args);

	}

}
