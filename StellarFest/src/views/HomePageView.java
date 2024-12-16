package views;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import main.Main;
import models.SessionManager;

public class HomePageView {
	
	private BorderPane borderPane;
	private Scene homePageScene;
	
	public HomePageView() {
		this.borderPane = new BorderPane();
		this.homePageScene = new Scene(borderPane, 800, 600);
		
		HBox navbar = createNavBar(SessionManager.getLoggedInUser().getUser_role());
        borderPane.setTop(navbar);
		
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
	
	private HBox createNavBar(String userRole) {
        HBox navbar = new HBox(10);
        navbar.setAlignment(Pos.CENTER_LEFT);
        navbar.setStyle("-fx-background-color: #89CFF0; -fx-padding: 10;");

        // Tambahkan button untuk semua role
        Button homeButton = new Button("Home");
        Button changeProfileButton = new Button("Change Profile");

        homeButton.setStyle("-fx-text-fill: white; -fx-background-color: #555;");
        changeProfileButton.setStyle("-fx-text-fill: white; -fx-background-color: #555;");
        
        homeButton.setOnAction(Event->{
        	HomePageView homePageView = new HomePageView();
        	Main.redirect(homePageView.getHomePageScene());
        });
        
        changeProfileButton.setOnAction(Event->{
        	ChangeProfileView changeProfileView = new ChangeProfileView();
        	Main.redirect(changeProfileView.getChangeProfileScene());
        });

        navbar.getChildren().addAll(homeButton, changeProfileButton);

        // Role-based access button
        if (SessionManager.getLoggedInUser().getUser_role().equals("Admin")) {
            Button viewAllEventsButton = new Button("View All Events");
            viewAllEventsButton.setStyle("-fx-text-fill: white; -fx-background-color: #555;");
            viewAllEventsButton.setOnAction(Event->{
            	ViewAllEvents_View viewAllEvents = new ViewAllEvents_View();
            	Main.redirect(viewAllEvents.getAllEventsScene());
            });
            Button viewAllUsersButton = new Button("View All Events");
            viewAllUsersButton.setStyle("-fx-text-fill: white; -fx-background-color: #555;");
            viewAllUsersButton.setOnAction(Event->{
            	ViewAllUsers_View viewAllUser = new ViewAllUsers_View();
            	Main.redirect(viewAllUser.getAllUsersScene());
            });
            navbar.getChildren().addAll(viewAllEventsButton,viewAllUsersButton);
            
        } 
        else if (SessionManager.getLoggedInUser().getUser_role().equals("Event Organizer")) {
            Button viewOrganizedEventsButton = new Button("View Organized Events");
            viewOrganizedEventsButton.setStyle("-fx-text-fill: white; -fx-background-color: #555;");
            viewOrganizedEventsButton.setOnAction(Event->{
            	ViewOrganizedEvents_View viewOrganizedEvents = new ViewOrganizedEvents_View();
            	Main.redirect(viewOrganizedEvents.getOrganizedEventScene());
            });
            navbar.getChildren().add(viewOrganizedEventsButton);
        }
        else if (SessionManager.getLoggedInUser().getUser_role().equals("Vendor")) {
        	Button myCoursesButton = new Button("Vendor");
            myCoursesButton.setStyle("-fx-text-fill: white; -fx-background-color: #555;");
            navbar.getChildren().add(myCoursesButton);
        }
        else if (SessionManager.getLoggedInUser().getUser_role().equals("Guest")) {
        	Button acceptInvitationButton = new Button("Accept Invitation");
            acceptInvitationButton.setStyle("-fx-text-fill: white; -fx-background-color: #555;");
            acceptInvitationButton.setOnAction(Event->{
            	AcceptInvitationView acceptInvitationView = new AcceptInvitationView();
            	Main.redirect(acceptInvitationView.getAcceptInvitationScene());
            });
            
            Button viewInvitationButton = new Button("View Invitations");
            viewInvitationButton.setStyle("-fx-text-fill: white; -fx-background-color: #555;");
            viewInvitationButton.setOnAction(Event->{
            	ViewInvitations_View viewInvitations = new ViewInvitations_View();
            	Main.redirect(viewInvitations.getViewInvitationScene());
            });
            
            Button viewAcceptedEventButton = new Button("View Accepted Events");
            viewAcceptedEventButton.setStyle("-fx-text-fill: white; -fx-background-color: #555;");
            viewAcceptedEventButton.setOnAction(Event->{
            	ViewAcceptedEvents_View viewAcceptedEvent = new ViewAcceptedEvents_View();
            	Main.redirect(viewAcceptedEvent.getViewAcceptedEventScene());
            });
                     
            navbar.getChildren().addAll(acceptInvitationButton, viewInvitationButton, viewAcceptedEventButton);
        }

        return navbar;
    }

	public Scene getHomePageScene() {
		return homePageScene;
	}
	
}
