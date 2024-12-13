package views;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class ChangeProfileView {
	
	private BorderPane borderPane;
	private Scene changeProfileScene;
	private Button changeProfileBtn;
	private TextField emailTf;
	private TextField nameTf;
	private PasswordField passTf;
	
	public ChangeProfileView() {
		this.borderPane = new BorderPane();
		
		this.changeProfileScene = new Scene(borderPane, 1600, 800); 
		
		//Email
		Label emailLbl = new Label("Email: ");
		emailTf = new TextField();
		
		//Name
		Label nameLbl = new Label("Username: ");
		nameTf = new TextField();
		
		//Password
		Label passLbl = new Label("Password: ");
		passTf = new PasswordField();
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
		
		changeProfileBtn = new Button("Edit");
		
		vb.getChildren().addAll(registLabel, gp, changeProfileBtn);
		
		borderPane.setCenter(vb);
	}
	
	public Scene getEditProfileScene() {
		return changeProfileScene;
	}

	public String getEmailInput() {
		return emailTf.getText();
	}
	
	public String getUsernameInput() {
		return nameTf.getText();
	}
	
	public String getPasswordInput() {
		return passTf.getText();
	}
	
	public void setRegisterButtonAction(EventHandler<ActionEvent> handler) {
		changeProfileBtn.setOnAction(handler);
	}

}
