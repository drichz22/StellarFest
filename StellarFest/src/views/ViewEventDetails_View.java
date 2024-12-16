package views;

import java.util.ArrayList;

import controllers.AdminController;
import controllers.EventOrganizerController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import main.Main;
import models.Admin;
import models.Event;
import models.Guest;
import models.Vendor;

public class ViewEventDetails_View {
	private Scene ViewEventDetailsScene;

	public ViewEventDetails_View(String eventID) {
		// Ambil detail event berdasarkan eventID
		Event event = Admin.viewEventDetails(eventID);
		if (event == null) {
			showAlert("Error", "Event not found.");
			return;
		}
		// Ambil daftar vendor dan guest berdasarkan eventID
		ArrayList<Vendor> vendorList = AdminController.getVendorsByTransactionID(eventID);
		ArrayList<Guest> guestList = AdminController.getGuestsByTransactionID(eventID);
		
        VBox mainLayout = new VBox(10);
        mainLayout.setPadding(new Insets(10));
        this.ViewEventDetailsScene = new Scene(mainLayout, 800, 600);
        
        //Tampilkan Detail Event
        Label titleLabel = new Label("Event Details");
        titleLabel.setStyle("-fx-font-size: 18; -fx-font-weight: bold;");

        GridPane detailsGrid = new GridPane();
        detailsGrid.setHgap(10);
        detailsGrid.setVgap(10);

        detailsGrid.add(new Label("Event Name:"), 0, 0);
        detailsGrid.add(new Label(event.getEvent_name()), 1, 0);

        detailsGrid.add(new Label("Event Date:"), 0, 1);
        detailsGrid.add(new Label(event.getEvent_date()), 1, 1);

        detailsGrid.add(new Label("Event Location:"), 0, 2);
        detailsGrid.add(new Label(event.getEvent_location()), 1, 2);

        detailsGrid.add(new Label("Event Description:"), 0, 3);
        detailsGrid.add(new Label(event.getEvent_description()), 1, 3);
        
        
        Separator separator1 = new Separator();
        // Tabel Vendor
        TableView<Vendor> vendorTable = new TableView<>();
        
        // Kolom ID Vendor
        TableColumn<Vendor, String> vendorIdCol = new TableColumn<>("Vendor ID");
        vendorIdCol.setCellValueFactory(new PropertyValueFactory<>("user_id"));

        // Kolom Nama Vendor
        TableColumn<Vendor, String> vendorNameCol = new TableColumn<>("Vendor Name");
        vendorNameCol.setCellValueFactory(new PropertyValueFactory<>("user_name"));

        // Kolom Email Vendor
        TableColumn<Vendor, String> vendorEmailCol = new TableColumn<>("Vendor Email");
        vendorEmailCol.setCellValueFactory(new PropertyValueFactory<>("user_email"));

        vendorTable.getColumns().addAll(vendorIdCol, vendorNameCol, vendorEmailCol);

        // Konversi ArrayList ke ObservableList untuk ditampilkan di tabel
        ObservableList<Vendor> vendorData = FXCollections.observableArrayList(vendorList);
        vendorTable.setItems(vendorData);        
        
        Separator separator2 = new Separator();
        // Tabel Guest
        TableView<Guest> guestTable = new TableView<>();
        
        // Kolom ID Guest
        TableColumn<Guest, String> guestIdCol = new TableColumn<>("Guest ID");
        guestIdCol.setCellValueFactory(new PropertyValueFactory<>("user_id"));

        // Kolom Nama Guest
        TableColumn<Guest, String> guestNameCol = new TableColumn<>("Guest Name");
        guestNameCol.setCellValueFactory(new PropertyValueFactory<>("user_name"));

        // Kolom Email Guest
        TableColumn<Guest, String> guestEmailCol = new TableColumn<>("Guest Email");
        guestEmailCol.setCellValueFactory(new PropertyValueFactory<>("user_email"));

        guestTable.getColumns().addAll(guestIdCol, guestNameCol, guestEmailCol);

        // Konversi ArrayList ke ObservableList untuk ditampilkan di tabel
        ObservableList<Guest> guestData = FXCollections.observableArrayList(guestList);
        guestTable.setItems(guestData);
        
        
        // Back button
        Button backButton = new Button("Back");
        backButton.setOnAction(e -> {
            // Set the scene back to the previous scene
        	ViewAllEvents_View EventView = new ViewAllEvents_View();
            Main.redirect(EventView.getAllEventsScene());
        });

        HBox buttonBox = new HBox(10);
        buttonBox.getChildren().add(backButton);
        
        // Tambahkan elemen ke layout
        mainLayout.getChildren().addAll(
                detailsGrid,
                separator1,
                new Label("Vendors:"), vendorTable,
                separator2,
                new Label("Guests:"), guestTable,
                backButton
        );
	}

	public Scene getViewEventDetailsScene() {
		return ViewEventDetailsScene;
	}	

	private void showAlert(String title, String message) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}
}
