package br.com.SellControl.application;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Program extends Application {

	private static Stage mainStage;
	private static Scene mainScene;

	@Override
	public void start(Stage primaryStage) {
		try {
			// Catch a reference to primaryStage.
			mainStage = primaryStage;
			Parent parent = FXMLLoader.load(getClass().getResource("/br/com/SellControl/gui/LoginScreen.fxml"));
			mainScene = new Scene(parent);
			removeBar();
			primaryStage.setScene(mainScene);
			primaryStage.initStyle(StageStyle.TRANSPARENT);
			primaryStage.show();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static Stage getMainStage() {
		return mainStage;
	}
	
	public static Scene getMainScene() {
		return mainScene;
	}

	public static void main(String[] args) {
		launch(args);

	}

	public void removeBar() {
		mainStage.resizableProperty().setValue(Boolean.FALSE);
		mainStage.initStyle(StageStyle.UTILITY);
		mainStage.initStyle(StageStyle.UNDECORATED);
	}

}
