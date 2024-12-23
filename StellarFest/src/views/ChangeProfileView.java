package views;

import controllers.UserController;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import main.Main;

public class ChangeProfileView {
	
	private BorderPane borderPane;
	private Scene changeProfileScene;
	private Button changeProfileBtn;
	private TextField emailTf;
	private TextField nameTf;
	private PasswordField passTf;
	private PasswordField newPassTf;
	private Label messageLbl;
	
	public ChangeProfileView() {
		this.borderPane = new BorderPane();
		
		this.changeProfileScene = new Scene(borderPane, 800, 600); 
		
		//Email
		Label emailLbl = new Label("Email: ");
		emailTf = new TextField();
		
		//Name
		Label nameLbl = new Label("Username: ");
		nameTf = new TextField();
		
		//Old Password
		Label oldPassLbl = new Label("Old Password: ");
		passTf = new PasswordField();
		passTf.setMinWidth(300);
		
		//New Password
		Label newPassLbl = new Label("New Password: ");
		newPassTf = new PasswordField();
		newPassTf.setMinWidth(300);
		
		GridPane gp = new GridPane();
		gp.setAlignment(Pos.CENTER);
		gp.setVgap(10);
		
		gp.add(emailLbl, 0, 0); 
		gp.add(emailTf, 1, 0);
		gp.add(nameLbl, 0, 1);
		gp.add(nameTf, 1, 1);
		gp.add(oldPassLbl, 0, 2);
		gp.add(passTf, 1, 2);
		gp.add(newPassLbl, 0, 3);
		gp.add(newPassTf, 1, 3);
		
		VBox vb = new VBox(20);
		vb.setAlignment(Pos.CENTER);
		
		Label changeProfileLabel = new Label("Change Profile Page");
		
		changeProfileBtn = new Button("Update");
		
		messageLbl = new Label(); //untuk label error
		messageLbl.setVisible(false);
		
		Button backButton = new Button("Back"); //Logika back btn untuk navigasi
        backButton.setOnAction(e -> {
        	HomePageView homePageView = new HomePageView();
            Main.redirect(homePageView.getHomePageScene());
        });
		
		UserController userController = new UserController(null, null, this, "Change Profile Page"); //inisialisasi controller
		
		changeProfileBtn.setOnAction(Event->{ //set logika Update Btn
			String email = emailTf.getText(); //Dapatkan input dari text field
			String username = nameTf.getText();
			String oldPass = passTf.getText();
			String newPass = newPassTf.getText();
			
			userController.changeProfile(email, username, oldPass, newPass); //panggil logika change profile dari controller
		});
		
		vb.getChildren().addAll(changeProfileLabel, gp, changeProfileBtn, messageLbl, backButton);
		
		borderPane.setCenter(vb);
	}
	
	public Scene getChangeProfileScene() {
		return changeProfileScene;
	}

	public void setValidationMessage(String valid) { //Label jika ada input yang tidak sesuai
		messageLbl.setText(valid);
		messageLbl.setVisible(true);
		messageLbl.setTextFill(Color.RED);
	}
	
	public void showErrorMessage() { //Label jika change profile gagal
		messageLbl.setText("Change Profile failed!");
		messageLbl.setVisible(true);
		messageLbl.setTextFill(Color.RED);
	}
	
	public void showSuccessAlert() { //Alert jika change profile berhasil
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Change Profile Status");
		alert.setHeaderText(null);
		alert.setContentText("Change Profile Successful!");
		alert.showAndWait();
	}
}
