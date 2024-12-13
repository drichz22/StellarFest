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

public class LoginView {
	
	private BorderPane borderPane;
	private Scene loginScene;
	private Button loginBtn;
	private TextField emailTf;
	private PasswordField passTf;
	
	public LoginView() {
		this.borderPane = new BorderPane();
		this.loginScene = new Scene(borderPane, 800, 600);
		
		GridPane gp = new GridPane();
		gp.setAlignment(Pos.CENTER);
		gp.setHgap(5);
		gp.setVgap(5);
		
		//Email Field
		Label emailLbl = new Label("Email: ");
		emailTf = new TextField();
				
		//Password Field
		Label passLbl = new Label("Password: ");
		passTf = new PasswordField();
		passTf.setMinWidth(300);
		
		gp.add(emailLbl, 0, 0); 
		gp.add(emailTf, 1, 0);
		gp.add(passLbl, 0, 1);
		gp.add(passTf, 1, 1);
		
		VBox vb = new VBox(20);
		vb.setAlignment(Pos.CENTER);
		
		Label registLabel = new Label("Login Page");
		
		loginBtn = new Button("Login");
		
		vb.getChildren().addAll(registLabel, gp, loginBtn);
		
		borderPane.setCenter(vb);
		
	}

	public Scene getLoginScene() {
		return loginScene;
	}
	
	public String getEmailInput() {
		return emailTf.getText();
	}
	
	public String getPasswordInput() {
		return passTf.getText();
	}
	
	public void setLoginButtonAction(EventHandler<ActionEvent> handler) {
		loginBtn.setOnAction(handler);
	}

}
