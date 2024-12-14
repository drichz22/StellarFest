package views;

import controllers.EventController;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import models.SessionManager;

public class CreateEventView {
	private Scene createEventScene;
	
	public CreateEventView() {
	    GridPane grid = new GridPane();
	    grid.setPadding(new Insets(10, 10, 10, 10));
	    grid.setVgap(10);
	    grid.setHgap(10);
	    this.createEventScene = new Scene(grid, 800, 600);

	    Label nameLabel = new Label("Event Name:");
	    TextField nameField = new TextField();

	    Label dateLabel = new Label("Event Date (YYYY-MM-DD):");
	    TextField dateField = new TextField();

	    Label locationLabel = new Label("Event Location:");
	    TextField locationField = new TextField();

	    Label descriptionLabel = new Label("Event Description:");
	    TextArea descriptionField = new TextArea();
	    descriptionField.setPrefHeight(100);

	    Button submitButton = new Button("Create Event");

	    // Tambahkan ke grid
	    grid.add(nameLabel, 0, 0);
	    grid.add(nameField, 1, 0);

	    grid.add(dateLabel, 0, 1);
	    grid.add(dateField, 1, 1);

	    grid.add(locationLabel, 0, 2);
	    grid.add(locationField, 1, 2);

	    grid.add(descriptionLabel, 0, 3);
	    grid.add(descriptionField, 1, 3);

	    grid.add(submitButton, 1, 4);

	    submitButton.setOnAction(e -> {
	        String eventName = nameField.getText();
	        String eventDate = dateField.getText();
	        String eventLocation = locationField.getText();
	        String eventDescription = descriptionField.getText();
	        
	        String validationMessage = EventController.checkCreateEventInput(eventName, eventDate, eventLocation, eventDescription);

	        if (validationMessage != null) {
                // Tampilkan pesan kesalahan jika validasi gagal
                showAlert("Error", validationMessage);
            } 
	        else {
                // Jika validasi berhasil, kirim data ke EventController
                EventController controller = new EventController();
                boolean success = controller.createEvent(eventName, eventDate, eventLocation, eventDescription, SessionManager.getLoggedInUser().getUser_id());

                if (success) {
                    showAlert("Success", "Event created successfully!");
                    nameField.clear();
                    dateField.clear();
                    locationField.clear();
                    descriptionField.clear();
                } 
                else {
                    showAlert("Error", "Failed to create event. Please try again.");
                }
            }
	    });
	}
        
	public Scene getCreateEventScene() {
        return createEventScene;
    }
	
	// Menampilkan Alert
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
