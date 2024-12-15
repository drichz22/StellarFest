package views;

import controllers.UserController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
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
		
		Label loginLabel = new Label("Login Page"); //Untuk judul page
		
		loginBtn = new Button("Login"); //Untuk button login (implementasi logika di controller)
		
		messageLbl = new Label(); //untuk label error
		messageLbl.setVisible(false);
		
		UserController userController = new UserController(this, null, null, "Login Page"); //inisialisasi controller
		userController.Login(); //call method untuk login
			
		vb.getChildren().addAll(loginLabel, gp, loginBtn, messageLbl); //add semua komponen di VBox
		
		borderPane.setCenter(vb); //set semuanya ke tengah
		
	}

	public Scene getLoginScene() {
		return loginScene;
	}
	
	//Untuk mengambil input dari user
	public String getEmailInput() {
		return emailTf.getText();
	}
	
	public String getPasswordInput() {
		return passTf.getText();
	}
	
	public void setValidationMessage(String valid) { //Label jika ada input yang tidak sesuai
		messageLbl.setText(valid);
		messageLbl.setVisible(true);
		messageLbl.setTextFill(Color.RED);
	}
	
	public void showErrorMessage() { //Label jika login gagal
		messageLbl.setText("Login failed!");
		messageLbl.setVisible(true);
		messageLbl.setTextFill(Color.RED);
	}
	
	public void showSuccessAlert() { //Alert untuk jika login berhasil
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Login Status");
		alert.setHeaderText(null);
		alert.setContentText("Login Successful!");
		alert.showAndWait();
	}
	
	public void setLoginBtnAction(EventHandler<ActionEvent> handler) { //setter btn untuk diterapkan logicnya di controller
    	loginBtn.setOnAction(handler);
    }
}
