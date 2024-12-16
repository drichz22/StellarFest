package views;

import controllers.UserController;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
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

public class RegisterView {
	
	private BorderPane borderPane;
	private Scene registerScene;
	private Button registerBtn;
	private TextField emailTf;
	private TextField nameTf;
	private PasswordField passTf;
	private ComboBox<String> rolePicker;
	private Label messageLbl;
	
	public RegisterView() {
		this.borderPane = new BorderPane();

		this.registerScene = new Scene(borderPane, 800, 600);

		// Email
		Label emailLbl = new Label("Email: ");
		emailTf = new TextField();

		// Username
		Label nameLbl = new Label("Username: ");
		nameTf = new TextField();

		// Password
		Label passLbl = new Label("Password: ");
		passTf = new PasswordField();
		passTf.setMinWidth(300);

		// Role
		Label roleLbl = new Label("Role: ");
		rolePicker = new ComboBox<>();
		rolePicker.getItems().addAll("Event Organizer", "Vendor", "Guest"); //Ada 3 role 

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

		registerBtn = new Button("Sign up");
		
		Hyperlink loginLink = new Hyperlink ("Already Have an Account? Login Here!"); //Hyperlink untuk redirect ke login kalau sudah punya akun
		loginLink.setOnAction(event -> { //logic redirect ke login
			LoginView loginView = new LoginView();
			Main.redirect(loginView.getLoginScene());
		});
		
		messageLbl = new Label(); //error label untuk validasi
		messageLbl.setVisible(false);
		
		UserController userController = new UserController(null, this, null, "Register Page"); //init UserController untuk page ini
		
		registerBtn.setOnAction(Event->{ //set logika register btn 
			String email = emailTf.getText(); //dapatkan input user
			String username = nameTf.getText();
			String password = passTf.getText();
			String role = rolePicker.getValue();
			
			userController.register(email, username, password, role); //call method untuk register dari controller
		}); 

		vb.getChildren().addAll(registLabel, gp, registerBtn, loginLink, messageLbl); //add semua komponen ke VBox

		borderPane.setCenter(vb); //tengahkan semua layout
	}

	public Scene getRegisterScene() {
		return registerScene;
	}
	
	public void setValidationMessage(String valid) { //untuk label validasi
		messageLbl.setText(valid);
		messageLbl.setVisible(true);
		messageLbl.setTextFill(Color.RED);
	}

	public void showErrorMessage() { //untuk error message registrasi gagal
		messageLbl.setText("Registration failed!");
		messageLbl.setVisible(true);
		messageLbl.setTextFill(Color.RED);
	}
	
	public void showSuccessAlert() { //alert untuk menandakan registrasi berhasil
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Registration Status");
		alert.setHeaderText(null);
		alert.setContentText("Registration Successful!");
		alert.getButtonTypes().clear(); //hilangkan semua button (ok dan cancel)
	    alert.getButtonTypes().add(ButtonType.OK); //hanya terapkan button ok
		alert.showAndWait();
	}
	
}
