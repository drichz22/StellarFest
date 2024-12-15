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
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import main.Main;
import models.Vendor;

public class AddVendorsView {
	private Scene addVendorsView;
	private TableView<Vendor> vendorTable;
	private ObservableList<Vendor> vendorData;
	private ArrayList<Vendor> selectedVendors = new ArrayList<>();

	public AddVendorsView(String eventID) {
		VBox mainLayout = new VBox(10);
		mainLayout.setPadding(new Insets(10));
		this.addVendorsView = new Scene(mainLayout, 800, 600);

		// Tabel list vendor
		vendorTable = new TableView<>();
		TableColumn<Vendor, String> nameColumn = new TableColumn<>("Name");
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("user_name"));

		TableColumn<Vendor, String> emailColumn = new TableColumn<>("Email");
		emailColumn.setCellValueFactory(new PropertyValueFactory<>("user_email"));

		TableColumn<Vendor, Boolean> selectColumn = new TableColumn<>("Select");
		selectColumn.setCellFactory(tc -> new TableCell<>() {
			CheckBox checkBox = new CheckBox();

			@Override
			protected void updateItem(Boolean selected, boolean empty) {
				super.updateItem(selected, empty);
				if (empty) {
					setGraphic(null);
				} else {
					Vendor vendor = getTableView().getItems().get(getIndex());
		            // Set checkbox action
		            checkBox.setOnAction(e -> {
		                if (checkBox.isSelected()) {
		                    // Tambahkan vendor ke ArrayList jika dicentang
		                    if (!selectedVendors.contains(vendor)) {
		                        selectedVendors.add(vendor);
		                    }
		                } else {
		                    // Hapus vendor dari ArrayList jika tidak dicentang
		                    selectedVendors.remove(vendor);
		                }
		            });

		            setGraphic(checkBox);
				}
			}
		});

		vendorTable.getColumns().addAll(nameColumn, emailColumn, selectColumn);

		// Load vendor data
		ArrayList<Vendor> vendors = EventOrganizerController.getVendors();
		vendorData = FXCollections.observableArrayList(vendors);
		vendorTable.setItems(vendorData);

		// Submit button
		Button inviteButton = new Button("Send Invitations");
		inviteButton.setOnAction(e -> {
			if (selectedVendors.isEmpty()) {
				showAlert("Error", "Please select at least one vendor to send invitations.");
			} else {
				for (Vendor vendor : selectedVendors) {
					String validationMessage = EventOrganizerController.checkAddVendorInput(eventID, vendor.getUser_email());

					if(validationMessage == null) {
						Boolean success = InvitationController.sendInvitation(vendor.getUser_email(), eventID, vendor.getUser_role());
						if (success) {
							showAlert("Success", "Invitation sent to: " + vendor.getUser_email());
		                } 
		                else {
		                	showAlert("Error", "Failed to send invitation to: " + vendor.getUser_email());
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
        
		mainLayout.getChildren().addAll(vendorTable, inviteButton, backButton);
	}

	public Scene getAddVendorsView() {
		return addVendorsView;
	}
	
	private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
