package main;
import controllers.EventOrganizerController;
import controllers.UserController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import views.ChangeProfileView;
import views.LoginView;
import views.RegisterView;
import views.ViewOrganizedEvents_View;

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
		stage = primaryStage;
		LoginView loginView = new LoginView();
		RegisterView registerView = new RegisterView();
		ChangeProfileView changeProfileView = new ChangeProfileView();
		ViewOrganizedEvents_View organizedEventView = new ViewOrganizedEvents_View();
		
		UserController userController = new UserController(loginView, registerView, "Register Page");
		EventOrganizerController eoController = new EventOrganizerController();
		
		primaryStage.setTitle("StellarFest");
		primaryStage.setScene(registerView.getRegisterScene());
		//primaryStage.setScene(organizedEventView.getOrganizedEventScene());
		primaryStage.show();
	}

}
