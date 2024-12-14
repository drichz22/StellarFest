package views;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import controllers.EventOrganizerController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import main.Main;
import models.Event;
import models.Vendor;
import models.Guest;

public class ViewOrganizedEventDetails_View {
	private Scene organizedEventDetailsScene;

	public ViewOrganizedEventDetails_View(String eventID) {
		// Ambil detail event berdasarkan eventID
		Event event = EventOrganizerController.viewOrganizedEventDetails(eventID);
		if (event == null) {
			showAlert("Error", "Event not found.");
			return;
		}
		// Ambil daftar vendor dan guest berdasarkan eventID
		HashMap<Vendor, String> vendorMap = EventOrganizerController.getVendorsByTransactionID(eventID);
		HashMap<Guest, String> guestMap = EventOrganizerController.getGuestsByTransactionID(eventID);
		
        VBox mainLayout = new VBox(10);
        mainLayout.setPadding(new Insets(10));
        this.organizedEventDetailsScene = new Scene(mainLayout, 800, 600);
        
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
        TableView<Map.Entry<Vendor, String>> vendorTable = new TableView<>();

        // Kolom Nama Vendor
        TableColumn<Map.Entry<Vendor, String>, String> vendorNameCol = new TableColumn<>("Vendor Name");
        vendorNameCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(
            data.getValue().getKey().getUser_name()
        ));
        
        // Kolom Email Vendor
        TableColumn<Map.Entry<Vendor, String>, String> vendorEmailCol = new TableColumn<>("Vendor Email");
        vendorEmailCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(
            data.getValue().getKey().getUser_email()
        ));

        // Kolom Invitation Status
        TableColumn<Map.Entry<Vendor, String>, String> statusCol = new TableColumn<>("Invitation Status");
        statusCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(
            data.getValue().getValue()
        ));

        vendorTable.getColumns().addAll(vendorNameCol, vendorEmailCol, statusCol);

        // Konversi HashMap ke ObservableList untuk ditampilkan di tabel
        ObservableList<Map.Entry<Vendor, String>> vendorData = FXCollections.observableArrayList(vendorMap.entrySet());
        vendorTable.setItems(vendorData);
        
        
        Separator separator2 = new Separator();
        // Tabel Guest
        TableView<Map.Entry<Guest, String>> guestTable = new TableView<>();

        // Kolom Nama Guest
        TableColumn<Map.Entry<Guest, String>, String> guestNameCol = new TableColumn<>("Guest Name");
        guestNameCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(
            data.getValue().getKey().getUser_name()
        ));
        
        // Kolom Email Guest
        TableColumn<Map.Entry<Guest, String>, String> guestEmailCol = new TableColumn<>("Guest Email");
        guestEmailCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(
            data.getValue().getKey().getUser_email()
        ));

        // Kolom Invitation Status
        TableColumn<Map.Entry<Guest, String>, String> statusGuestCol = new TableColumn<>("Invitation Status");
        statusGuestCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(
            data.getValue().getValue()
        ));

        guestTable.getColumns().addAll(guestNameCol, guestEmailCol, statusGuestCol);

        // Konversi HashMap ke ObservableList untuk ditampilkan di tabel
        ObservableList<Entry<Guest, String>> guestData = FXCollections.observableArrayList(guestMap.entrySet());
        guestTable.setItems(guestData);
        
        
        // Back button
        Button backButton = new Button("Back");
        backButton.setOnAction(e -> {
            // Set the scene back to the previous scene
        	ViewOrganizedEvents_View organizedEventView = new ViewOrganizedEvents_View();
            Main.redirect(organizedEventView.getOrganizedEventScene());
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

	public Scene getOrganizedEventDetailsScene() {
		return organizedEventDetailsScene;
	}	

	private void showAlert(String title, String message) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}
}
