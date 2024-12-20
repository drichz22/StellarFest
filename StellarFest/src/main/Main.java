package main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import views.RegisterView;

public class Main extends Application {
	
	private static Stage stage;
	
	public static void redirect(Scene newScene) { //logika untuk change page
		stage.setScene(newScene);
		stage.centerOnScreen();
		stage.setResizable(false);
		stage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		stage = primaryStage; //set stage yang di-redirect ke primary stage
		RegisterView registerView = new RegisterView(); //Mulai dari Register Page
	
		primaryStage.setTitle("StellarFest"); //Nama Program
	    primaryStage.setScene(registerView.getRegisterScene());

		primaryStage.show();
	}

}
