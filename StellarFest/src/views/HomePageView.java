package views;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import models.SessionManager;

public class HomePageView {
	
	private BorderPane borderPane;
	private Scene homePageScene;
	
	public HomePageView() {
		this.borderPane = new BorderPane();
		this.homePageScene = new Scene(borderPane, 800, 600);
		
		Label homeLabel = new Label("Home Page");
		homeLabel.setFont(Font.font("Verdana", 48));
		
		Label helloLabel = new Label("HELLO");
		helloLabel.setFont(Font.font("Verdana", 24));
		
		Label userName = new Label();
		userName.setText(SessionManager.getLoggedInUser().getUser_name());
		
		Label userEmail = new Label();
		userEmail.setText(SessionManager.getLoggedInUser().getUser_email());
		
		Label userRole = new Label();
		userRole.setText(SessionManager.getLoggedInUser().getUser_role());
		
		VBox vb = new VBox(20);
		vb.setAlignment(Pos.CENTER);
		
		vb.getChildren().addAll(homeLabel, helloLabel, userName, userEmail, userRole);
		
		borderPane.setCenter(vb);
	}

	public Scene getHomePageScene() {
		return homePageScene;
	}
	
}
