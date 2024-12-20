package views;

import controllers.EventOrganizerController;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import main.Main;
import models.Event;

public class EditEventNameView {
	private Scene editEventNameScene;
	
	public EditEventNameView(String eventID) {
		// Ambil detail event berdasarkan eventId
        Event event = EventOrganizerController.viewOrganizedEventDetails(eventID);

        if (event == null) {
            // Jika event tidak ditemukan, tampilkan pesan error
            showAlert("Error", "Event not found.");
            return;
        }

        // Layout
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(10);
        grid.setHgap(10);
        this.editEventNameScene = new Scene(grid, 800, 600);
        
        // Tampilkan detail event
        Label currentNameLabel = new Label("Current Event Name:");
        Label currentNameValue = new Label(event.getEvent_name());

        Label currentDateLabel = new Label("Event Date:");
        Label currentDateValue = new Label(event.getEvent_date());

        Label currentLocationLabel = new Label("Event Location:");
        Label currentLocationValue = new Label(event.getEvent_location());

        Label currentDescriptionLabel = new Label("Event Description:");
        Label currentDescriptionValue = new Label(event.getEvent_description());

        Label newNameLabel = new Label("New Event Name:");
        TextField newNameField = new TextField();
        newNameField.setPromptText("Enter new event name");

        Button submitButton = new Button("Save Changes");
        
        // Tambahkan elemen ke GridPane
        grid.add(currentNameLabel, 0, 0);
        grid.add(currentNameValue, 1, 0);

        grid.add(currentDateLabel, 0, 1);
        grid.add(currentDateValue, 1, 1);

        grid.add(currentLocationLabel, 0, 2);
        grid.add(currentLocationValue, 1, 2);

        grid.add(currentDescriptionLabel, 0, 3);
        grid.add(currentDescriptionValue, 1, 3);

        grid.add(newNameLabel, 0, 4);
        grid.add(newNameField, 1, 4);

        grid.add(submitButton, 1, 5);
        
        submitButton.setOnAction(e -> {
            String newEventName = newNameField.getText();

            // Validasi input
            String validationMessage = EventOrganizerController.checkEditEventName(newEventName);
            if (validationMessage != null) {
                showAlert("Error", validationMessage);
                return;
            }

            // Simpan perubahan
            EventOrganizerController controller = new EventOrganizerController();
            controller.editEventName("1", newEventName);
            showAlert("Success", "Event name updated successfully!");
            
            // Refresh view
            Event updatedEvent = controller.viewOrganizedEventDetails(eventID);

            // Update ulang event name nya
            currentNameValue.setText(updatedEvent.getEvent_name());
        });
        
        // Back button
        Button backButton = new Button("Back");
        backButton.setOnAction(e -> {
            // Set the scene back to the previous scene
        	ViewOrganizedEvents_View organizedEventView = new ViewOrganizedEvents_View();
            Main.redirect(organizedEventView.getOrganizedEventScene());
        });

        HBox buttonBox = new HBox(10);
        buttonBox.getChildren().add(backButton);
        grid.add(buttonBox, 0, 5);
	}

	public Scene getEditEventNameScene() {
		return editEventNameScene;
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
