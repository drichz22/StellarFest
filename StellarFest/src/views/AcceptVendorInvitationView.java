package views;

import java.util.ArrayList;

import controllers.VendorController;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import main.Main;

public class AcceptVendorInvitationView {
	private BorderPane borderPane;
	private Scene acceptInvitationScene;
	private Button acceptInvitationBtn;
	private TableView<ObservableMap<String, String>> invitationTable;
	private ObservableList<ObservableMap<String, String>> invitationList;
	private ArrayList<ObservableMap<String, String>> invitations;
	
	public AcceptVendorInvitationView() {
		this.borderPane = new BorderPane();
		this.acceptInvitationScene = new Scene(borderPane, 800, 600);
		
		acceptInvitationBtn = new Button("Accept Event");
		
		invitationTable = new TableView<>();

		//Buat kolom2 yang dibutuhkan (memakai Observable agar bisa refresh secara real time)
		TableColumn<ObservableMap<String, String>, String> idColumn = new TableColumn<>("Event ID");
		idColumn.setCellValueFactory(cellData -> 
		    new SimpleStringProperty(cellData.getValue().get("event_id"))); //memakai SimpleStringProperty karena berupa ObservableMap

		TableColumn<ObservableMap<String, String>, String> nameColumn = new TableColumn<>("Event Name");
		nameColumn.setCellValueFactory(cellData -> 
		    new SimpleStringProperty(cellData.getValue().get("event_name")));

		TableColumn<ObservableMap<String, String>, String> roleColumn = new TableColumn<>("Invitation Status");
		roleColumn.setCellValueFactory(cellData -> 
		    new SimpleStringProperty(cellData.getValue().get("invitation_status")));
		
		TableColumn<ObservableMap<String, String>, String> organizerNameColumn = new TableColumn<>("Organizer Name");
		organizerNameColumn.setCellValueFactory(cellData -> 
		    new SimpleStringProperty(cellData.getValue().get("organizer_username")));
		
		TableColumn<ObservableMap<String, String>, String> organizerEmailColumn = new TableColumn<>("Organizer Email");
		organizerEmailColumn.setCellValueFactory(cellData -> 
		    new SimpleStringProperty(cellData.getValue().get("organizer_email")));

		// Nambahin kolom ke tabel
		invitationTable.getColumns().addAll(idColumn, nameColumn, roleColumn, organizerNameColumn, organizerEmailColumn);

		borderPane.setCenter(invitationTable);

		//Ambil data dari db untuk ditambah ke tabel
		invitations = VendorController.getAllVendorInvitations();
		invitationList = FXCollections.observableArrayList(invitations);
		invitationTable.setItems(invitationList);

		//Action Accept Invitation Btn
	    acceptInvitationBtn.setOnAction(Event->{
	    	ObservableMap<String, String> selectedInvitation = invitationTable.getSelectionModel().getSelectedItem();
	    	if (selectedInvitation != null) { //jika ada item yang dipilih, jalankan logika accept invitation
	    		VendorController vendorController = new VendorController(this);
		    	String eventInputId = selectedInvitation.get("event_id");
		    	vendorController.acceptInvitation(eventInputId);
		    	
		    	//Reload tabel setelah update Invitation Status
		    	invitationList.clear();
		    	invitations = VendorController.getAllVendorInvitations();
		    	invitationList.addAll(invitations);
		    	invitationTable.setItems(invitationList);
	    	}
	    	else { //jika tidak ada item dipilih, display error alert
	    		showAlert("Invitation Acceptance Status", "Select an Invitation!");
	    	}
	    });
	    
        Button backButton = new Button("Back"); //Logika back btn untuk navigasi
        backButton.setOnAction(e -> {
        	HomePageView homePageView = new HomePageView();
            Main.redirect(homePageView.getHomePageScene());
        });
        
		VBox mainLayout = new VBox(10); 
		mainLayout.setAlignment(Pos.CENTER);
		mainLayout.getChildren().addAll(acceptInvitationBtn, invitationTable, backButton); 
		borderPane.setCenter(mainLayout);
	}

	public Scene getAcceptInvitationScene() {
		return acceptInvitationScene;
	}

	private void showAlert(String title, String message) { //Alert jika tidak ada item yang dipilih pada tabel
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}
	
	public void showSuccessAlert() { //Alert untuk jika update berhasil
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Update Status");
		alert.setHeaderText(null);
		alert.setContentText("Invitation Accepted Successfuly!");
		alert.getButtonTypes().clear(); //hilangkan semua button (ok dan cancel)
	    alert.getButtonTypes().add(ButtonType.OK); //hanya terapkan button ok
		alert.showAndWait();
	}
	
	public void showFailAlert() { //Alert untuk jika update gagal
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Update Status");
		alert.setHeaderText(null);
		alert.setContentText("Failed to Accept Invitation!");
		alert.getButtonTypes().clear(); //hilangkan semua button (ok dan cancel)
	    alert.getButtonTypes().add(ButtonType.OK); //hanya terapkan button ok
		alert.showAndWait();
	}
}
