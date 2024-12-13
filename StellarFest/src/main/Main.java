package main;
import controllers.UserController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import views.ChangeProfileView;
import views.LoginView;
import views.RegisterView;

public class Main extends Application {
	
	private static Stage stage;
	
	public static void redirect(Scene newScene) {
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
		LoginView loginView = new LoginView();
		RegisterView registerView = new RegisterView();
		ChangeProfileView changeProfileView = new ChangeProfileView();
		
		UserController userController = new UserController();
		
		primaryStage.setTitle("StellarFest");
		primaryStage.setScene(loginView.getLoginScene());
		primaryStage.show();
	}

}
