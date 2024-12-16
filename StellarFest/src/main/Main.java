package main;
import controllers.EventOrganizerController;
import controllers.UserController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import views.AcceptGuestInvitationView;
import views.ChangeProfileView;
import views.CreateEventView;
import views.EditEventNameView;
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
		RegisterView registerView = new RegisterView();
//		ViewOrganizedEvents_View organizedEventView = new ViewOrganizedEvents_View();
//		CreateEventView createEventView = new CreateEventView();
//		EditEventNameView editEventNameView = new EditEventNameView();
		//AcceptGuestInvitationView acceptGuestInvitationView = new AcceptGuestInvitationView();
		
		//EventOrganizerController eoController = new EventOrganizerController();
		
		primaryStage.setTitle("StellarFest");

		primaryStage.setScene(registerView.getRegisterScene());
		//primaryStage.setScene(organizedEventView.getOrganizedEventScene());
//		primaryStage.setScene(loginView.getLoginScene());
//		primaryStage.setScene(organizedEventView.getOrganizedEventScene());
//		primaryStage.setScene(createEventView.getCreateEventScene());
//		primaryStage.setScene(editEventNameView.getEditEventNameScene());
		//primaryStage.setScene(acceptGuestInvitationView.getAcceptInvitationScene());

		primaryStage.show();
	}

}
