package views;

import controllers.UserController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import main.Main;

public class LoginView {
	
	private BorderPane borderPane;
	private Scene loginScene;
	private Button loginBtn;
	private TextField emailTf;
	private PasswordField passTf;
	private Label messageLbl;
	
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
		
		Label loginLabel = new Label("Login Page");
		
		loginBtn = new Button("Login");
		
		messageLbl = new Label();
		messageLbl.setVisible(false);
		
		UserController userController = new UserController(this, null, "Login Page");
		userController.Login();
			
		vb.getChildren().addAll(loginLabel, gp, loginBtn, messageLbl);
		
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
	
	public void setValidationMessage(String valid) {
		messageLbl.setText(valid);
		messageLbl.setVisible(true);
		messageLbl.setTextFill(Color.RED);
	}
	
	public void showSuccessMessage() {
		messageLbl.setText("Login Successful!");
		messageLbl.setVisible(true);
		messageLbl.setTextFill(Color.GREEN);
	}
	
	public void showErrorMessage() {
		messageLbl.setText("Login failed!");
		messageLbl.setVisible(true);
		messageLbl.setTextFill(Color.RED);
	}
	
	public void setLoginBtnAction(EventHandler<ActionEvent> handler) {
    	loginBtn.setOnAction(handler);
    }
}
