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
	Scene homeScene;
	
	void homeFunction(Stage primaryStage) {
		GridPane gp = new GridPane();
		homeScene = new Scene (gp, 1600, 800);
		
		Label itemLabel = new Label("Item Label: ");
		TextArea inputItem = new TextArea();
		
		Label quantityLabel = new Label("Quantity: ");
		Spinner<Integer> quantitySpinner = new Spinner<>(1, 20, 1);
		
		Button insertBtn = new Button("Insert Data: ");
		
		ListView<String> dataList = new ListView<>();
		ObservableList<String> items = FXCollections.observableArrayList();
		
		gp.add(itemLabel, 0, 0);
		gp.add(inputItem, 0, 1);
		gp.add(quantityLabel, 0, 2);
		gp.add(quantitySpinner, 0, 3);
		gp.add(insertBtn, 0, 4);
		gp.add(dataList, 0, 5);
		
	}
	
	void registFunction(Stage primaryStage) {
		
		
		BorderPane bp = new BorderPane();
		
		regisScene = new Scene(bp, 1600, 800); //Layout manager, x size, y size
		
		//Text Field
		Label nameLbl = new Label("Username: ");
		TextField nameTf = new TextField();
		
		//Pass Field
		Label passLbl = new Label("Password: ");
		PasswordField passTf = new PasswordField();
		passTf.setMinWidth(300);
		
		//Radio Button
		Label genderLbl = new Label("Gender: ");
		RadioButton maleBtn = new RadioButton("Male");
		RadioButton femaleBtn = new RadioButton("Female");
		
		ToggleGroup genderGroup = new ToggleGroup();
		maleBtn.setToggleGroup(genderGroup);
		femaleBtn.setToggleGroup(genderGroup);
		
		HBox genderContainer = new HBox(10);
		genderContainer.getChildren().addAll(maleBtn, femaleBtn);
		
		//Combo Box
		Label userTypeLbl = new Label("User Type: ");
		ComboBox<String> typeCombo = new ComboBox<>();
		typeCombo.getItems().addAll("Pembeli", "Penjual");
		
		//Date Picker
		Label dateOfBirthLbl = new Label("Date of Birth: ");
		DatePicker DOBPicker = new DatePicker();
		
		GridPane gp = new GridPane();
		gp.setAlignment(Pos.CENTER);
		gp.setVgap(10);
		
		gp.add(nameLbl, 0, 0); //basically (thing, x-axis, y-axis)
		gp.add(nameTf, 1, 0);
		gp.add(passLbl, 0, 1);
		gp.add(passTf, 1, 1);
		gp.add(genderLbl, 0, 2);
		gp.add(genderContainer, 1, 2);
		gp.add(userTypeLbl, 0, 3);
		gp.add(typeCombo, 1, 3);
		gp.add(dateOfBirthLbl, 0, 4);
		gp.add(DOBPicker, 1, 4);
		
		VBox vb = new VBox(20);
		vb.setAlignment(Pos.CENTER);
		
		Label registLabel = new Label("Register");
		
		Label ToSLbl = new Label("Accept Terms and Services");
		CheckBox cbToS = new CheckBox();	
		HBox ToSContainer = new HBox(10);
		ToSContainer.getChildren().addAll(cbToS, ToSLbl);
		ToSContainer.setAlignment(Pos.CENTER);
		
		Button regisBtn = new Button("Sign up!");
		
		vb.getChildren().addAll(registLabel, gp, ToSContainer, regisBtn);
		
		bp.setCenter(vb); //BorderPane nampung apa yang ada
	}
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		registFunction(primaryStage);
		primaryStage.setScene(regisScene);
		primaryStage.show();
	}

}
