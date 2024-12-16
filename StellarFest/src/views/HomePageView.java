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
            	//Main.redirect(viewAllEvents);
            });
            navbar.getChildren().add(viewAllEventsButton);
        } 
        else if (SessionManager.getLoggedInUser().getUser_role().equals("Event Organizer")) { //Khusus untuk EO
            Button viewOrganizedEvents = new Button("View Organized Events");
            viewOrganizedEvents.setStyle("-fx-text-fill: white; -fx-background-color: #555;");
            viewOrganizedEvents.setOnAction(Event->{
            	ViewOrganizedEvents_View viewOrgEvents = new ViewOrganizedEvents_View();
            	Main.redirect(viewOrgEvents.getOrganizedEventScene());
            });
            
            Button createEvent = new Button("Create Event");
            createEvent.setStyle("-fx-text-fill: white; -fx-background-color: #555;");
            createEvent.setOnAction(Event->{
            	CreateEventView createEventView = new CreateEventView();
            	Main.redirect(createEventView.getCreateEventScene());
            });
            
            navbar.getChildren().addAll(viewOrganizedEvents, createEvent);
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
        else if (SessionManager.getLoggedInUser().getUser_role().equals("Vendor")) { //Khusus untuk Vendor
        	Button myCoursesButton = new Button("Vendor");
            myCoursesButton.setStyle("-fx-text-fill: white; -fx-background-color: #555;");
            navbar.getChildren().add(myCoursesButton);
        }
        else if (SessionManager.getLoggedInUser().getUser_role().equals("Guest")) { //Khusus untuk Guest
        	Button acceptInvitationButton = new Button("Accept Invitation");
            acceptInvitationButton.setStyle("-fx-text-fill: white; -fx-background-color: #555;");
            acceptInvitationButton.setOnAction(Event->{
            	AcceptGuestInvitationView acceptInvitationView = new AcceptGuestInvitationView();
            	Main.redirect(acceptInvitationView.getAcceptInvitationScene());
            });
            
            Button viewInvitationButton = new Button("View Invitations");
            viewInvitationButton.setStyle("-fx-text-fill: white; -fx-background-color: #555;");
            viewInvitationButton.setOnAction(Event->{
            	ViewGuestInvitations_View viewInvitations = new ViewGuestInvitations_View();
            	Main.redirect(viewInvitations.getViewInvitationScene());
            });
            
            Button viewAcceptedEventButton = new Button("View Accepted Events");
            viewAcceptedEventButton.setStyle("-fx-text-fill: white; -fx-background-color: #555;");
            viewAcceptedEventButton.setOnAction(Event->{
            	ViewGuestAcceptedEvents_View viewAcceptedEvent = new ViewGuestAcceptedEvents_View();
            	Main.redirect(viewAcceptedEvent.getViewAcceptedEventScene());
            });
                     
            navbar.getChildren().addAll(acceptInvitationButton, viewInvitationButton, viewAcceptedEventButton, viewAcceptedEventDetailsButton);
        }

        return navbar;
    }

	public Scene getHomePageScene() {
		return homePageScene;
	}
	
}
