package views;

import java.util.ArrayList;

import controllers.EventOrganizerController;
import controllers.InvitationController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import main.Main;
import models.Guest;
import models.Vendor;

public class AddGuestsView {
	private Scene addGuestsView;
	private TableView<Guest> guestTable;
	private ObservableList<Guest> guestData;
	private ArrayList<Guest> selectedGuests = new ArrayList<>();
	
	public AddGuestsView(String eventID) {
		VBox mainLayout = new VBox(10);
		mainLayout.setPadding(new Insets(10));
		this.addGuestsView = new Scene(mainLayout, 800, 600);

		// Tabel list guest
		guestTable = new TableView<>();
		TableColumn<Guest, String> nameColumn = new TableColumn<>("Name");
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("user_name"));

		TableColumn<Guest, String> emailColumn = new TableColumn<>("Email");
		emailColumn.setCellValueFactory(new PropertyValueFactory<>("user_email"));

		TableColumn<Guest, Boolean> selectColumn = new TableColumn<>("Select");
		selectColumn.setCellFactory(tc -> new TableCell<>() {
			CheckBox checkBox = new CheckBox();

			@Override
			protected void updateItem(Boolean selected, boolean empty) {
				super.updateItem(selected, empty);
				if (empty) {
					setGraphic(null);
				} else {
					Guest guest = getTableView().getItems().get(getIndex());
		            // Set checkbox action
		            checkBox.setOnAction(e -> {
		                if (checkBox.isSelected()) {
		                    // Tambahkan vendor ke ArrayList jika dicentang
		                    if (!selectedGuests.contains(guest)) {
		                    	selectedGuests.add(guest);
		                    }
		                } else {
		                    // Hapus vendor dari ArrayList jika tidak dicentang
		                	selectedGuests.remove(guest);
		                }
		            });

		            setGraphic(checkBox);
				}
			}
		});

		guestTable.getColumns().addAll(nameColumn, emailColumn, selectColumn);

		// Load vendor data
		ArrayList<Guest> guests = EventOrganizerController.getGuests();
		guestData = FXCollections.observableArrayList(guests);
		guestTable.setItems(guestData);

		// Submit button
		Button inviteButton = new Button("Send Invitations");
		inviteButton.setOnAction(e -> {
			if (selectedGuests.isEmpty()) {
				showAlert("Error", "Please select at least one vendor to send invitations.");
			} else {
				for (Guest guest : selectedGuests) {
					String validationMessage = EventOrganizerController.checkAddGuestInput(eventID, guest.getUser_email());
					if(validationMessage == null) {
						Boolean success = InvitationController.sendInvitation(guest.getUser_email(), eventID, guest.getUser_role());
						if (success) {
							showAlert("Success", "Invitation sent to: " + guest.getUser_email());
		                } 
		                else {
		                	showAlert("Error", "Failed to send invitation to: " + guest.getUser_email());
		                }
					}
					else {
						showAlert("Error", validationMessage);
					}
				}
			}
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
        
		mainLayout.getChildren().addAll(guestTable, inviteButton, backButton);
	}

	public Scene getAddGuestsView() {
		return addGuestsView;
	}
	
	private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
