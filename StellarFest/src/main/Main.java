package main;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {
	
	Scene regisScene;
	Scene loginScene;
	Scene editProfileScene;
	
	void loginFunction(Stage primaryStage) {
		BorderPane bp = new BorderPane();
		bp.setTop(createNavBar(primaryStage));
		
		loginScene = new Scene(bp, 1600, 800); 
		
		//Email
		Label emailLbl = new Label("Email: ");
		TextField emailTf = new TextField();
		
		//Password
		Label passLbl = new Label("Password: ");
		PasswordField passTf = new PasswordField();
		passTf.setMinWidth(300);
		
		GridPane gp = new GridPane();
		gp.setAlignment(Pos.CENTER);
		gp.setVgap(10);
		
		gp.add(emailLbl, 0, 0); 
		gp.add(emailTf, 1, 0);
		gp.add(passLbl, 0, 1);
		gp.add(passTf, 1, 1);
		
		VBox vb = new VBox(20);
		vb.setAlignment(Pos.CENTER);
		
		Label registLabel = new Label("Login Page");
		
		Button regisBtn = new Button("Login");
		
		vb.getChildren().addAll(registLabel, gp, regisBtn);
		
		bp.setCenter(vb);
		
	}
	
	void registFunction(Stage primaryStage) {
		
		
		BorderPane bp = new BorderPane();
		bp.setTop(createNavBar(primaryStage));
		
		regisScene = new Scene(bp, 1600, 800); 
		
		//Email
		Label emailLbl = new Label("Email: ");
		TextField emailTf = new TextField();
		
		//Username
		Label nameLbl = new Label("Username: ");
		TextField nameTf = new TextField();
		
		//Password
		Label passLbl = new Label("Password: ");
		PasswordField passTf = new PasswordField();
		passTf.setMinWidth(300);
		
		//Role
		Label roleLbl = new Label("Role: ");	
		ComboBox<String> rolePicker = new ComboBox<>();
		rolePicker.getItems().addAll("Event Organizer", "Vendor", "Guest");
	
		GridPane gp = new GridPane();
		gp.setAlignment(Pos.CENTER);
		gp.setVgap(10);
		
		gp.add(emailLbl, 0, 0);
		gp.add(emailTf, 1, 0);
		gp.add(nameLbl, 0, 1);
		gp.add(nameTf, 1, 1);
		gp.add(passLbl, 0, 2);
		gp.add(passTf, 1, 2);
		gp.add(roleLbl, 0, 3);
		gp.add(rolePicker, 1, 3);
		
		VBox vb = new VBox(20);
		vb.setAlignment(Pos.CENTER);
		
		Label registLabel = new Label("Register Page");
		
		Button regisBtn = new Button("Sign up");
		
		vb.getChildren().addAll(registLabel, gp, regisBtn);
		
		bp.setCenter(vb);
	}
	
	void editProfileFunction(Stage primaryStage) {
		BorderPane bp = new BorderPane();
		bp.setTop(createNavBar(primaryStage));
		
		editProfileScene = new Scene(bp, 1600, 800); 
		
		//Email
		Label emailLbl = new Label("Email: ");
		TextField emailTf = new TextField();
		
		//Name
		Label nameLbl = new Label("Username: ");
		TextField nameTf = new TextField();
		
		//Password
		Label passLbl = new Label("Password: ");
		PasswordField passTf = new PasswordField();
		passTf.setMinWidth(300);
		
		GridPane gp = new GridPane();
		gp.setAlignment(Pos.CENTER);
		gp.setVgap(10);
		
		gp.add(emailLbl, 0, 0); 
		gp.add(emailTf, 1, 0);
		gp.add(nameLbl, 0, 1);
		gp.add(nameTf, 1, 1);
		gp.add(passLbl, 0, 2);
		gp.add(passTf, 1, 2);
		
		VBox vb = new VBox(20);
		vb.setAlignment(Pos.CENTER);
		
		Label registLabel = new Label("Edit Profile Page");
		
		Button regisBtn = new Button("Edit");
		
		vb.getChildren().addAll(registLabel, gp, regisBtn);
		
		bp.setCenter(vb);
		
	}
	
	private HBox createNavBar(Stage primaryStage) {
	    Button loginBtn = new Button("Login");
	    Button registerBtn = new Button("Register");
	    Button editProfileBtn = new Button("Edit Profile");

	    loginBtn.setOnAction(e -> primaryStage.setScene(loginScene));
	    registerBtn.setOnAction(e -> primaryStage.setScene(regisScene));
	    editProfileBtn.setOnAction(e -> primaryStage.setScene(editProfileScene));

	    HBox navBar = new HBox(10);
	    navBar.setAlignment(Pos.CENTER);
	    navBar.getChildren().addAll(loginBtn, registerBtn, editProfileBtn);
	    navBar.setStyle("-fx-padding: 10; -fx-background-color: #dddddd;");

	    return navBar;
	}
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		loginFunction(primaryStage);
	    registFunction(primaryStage);
	    editProfileFunction(primaryStage);

	    primaryStage.setScene(regisScene); 
	    primaryStage.setTitle("StellarFest");
	    primaryStage.show();
	}

}
