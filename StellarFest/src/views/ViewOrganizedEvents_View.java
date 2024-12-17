package views;

import java.util.ArrayList;

import controllers.EventOrganizerController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import main.Main;
import models.Event;
import models.SessionManager;

public class ViewOrganizedEvents_View {
	private Scene organizedEventScene;
	private BorderPane borderPane;
	private TableView<Event> eventTable;
	private ObservableList<Event> eventList;
	private Button eventDetailsButton;
	private Button addVendorButton;
	private Button addGuestButton;

	public ViewOrganizedEvents_View() {
		this.borderPane = new BorderPane();
		this.organizedEventScene = new Scene(borderPane, 800, 600);

		GridPane gridPane = new GridPane();
		gridPane.setAlignment(Pos.CENTER);
		gridPane.setHgap(5);
		gridPane.setVgap(5);

		// Membuat columns
		eventTable = new TableView<>();
		TableColumn<Event, String> idColumn = new TableColumn<>("Event ID");
		idColumn.setCellValueFactory(new PropertyValueFactory<>("event_id"));

		TableColumn<Event, String> nameColumn = new TableColumn<>("Name");
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("event_name"));

		TableColumn<Event, String> dateColumn = new TableColumn<>("Date");
		dateColumn.setCellValueFactory(new PropertyValueFactory<>("event_date"));

		TableColumn<Event, String> locationColumn = new TableColumn<>("Location");
		locationColumn.setCellValueFactory(new PropertyValueFactory<>("event_location"));

		// Nambahin kolom ke tabel
		eventTable.getColumns().addAll(idColumn, nameColumn, dateColumn, locationColumn);
		eventTable.setItems(eventList);

		borderPane.setTop(gridPane);
		borderPane.setCenter(eventTable);

		//Isi tabelnya dari data di DB nya
		ArrayList<Event> events = EventOrganizerController.viewOrganizedEvents(SessionManager.getLoggedInUser().getUser_id());
		eventList = FXCollections.observableArrayList(events);
		eventTable.setItems(eventList);

		//Buat ke Event Detail
		eventDetailsButton = new Button("View Event Details");
		eventDetailsButton.setOnAction(event -> {
			Event selectedEvent = eventTable.getSelectionModel().getSelectedItem();
			if (selectedEvent != null) {
				ViewOrganizedEventDetails_View eventDetailView = new ViewOrganizedEventDetails_View(selectedEvent.getEvent_id());
				Main.redirect(eventDetailView.getOrganizedEventDetailsScene());
			} else {
				showAlert("No Event Selected", "Please select an event to view details.");
			}
		});
		
		// Buat ke EditEventName
        Button editBtn = new Button("Edit Event Name");
        editBtn.setOnAction(event -> {
        	Event selectedEvent = eventTable.getSelectionModel().getSelectedItem();
			if (selectedEvent != null) {
				EditEventNameView editEventView = new EditEventNameView(selectedEvent.getEvent_id());
				Main.redirect(editEventView.getEditEventNameScene());
			} else {
				showAlert("No Event Selected", "Please select an event to edit event name.");
			}
        });
        
        // Buat ke addVendor
        addVendorButton = new Button("Add Vendor");
        addVendorButton.setOnAction(event -> {
        	Event selectedEvent = eventTable.getSelectionModel().getSelectedItem();
			if (selectedEvent != null) {
				AddVendorsView addVendorView = new AddVendorsView(selectedEvent.getEvent_id());
				Main.redirect(addVendorView.getAddVendorsView());
			} else {
				showAlert("No Event Selected", "Please select an event to add vendor.");
			}
        });
        
        // Buat ke addGuest
        addGuestButton = new Button("Add Guest");
        addGuestButton.setOnAction(event -> {
        	Event selectedEvent = eventTable.getSelectionModel().getSelectedItem();
			if (selectedEvent != null) {
				AddGuestsView addGuestView = new AddGuestsView(selectedEvent.getEvent_id());
				Main.redirect(addGuestView.getAddGuestsView());
			} else {
				showAlert("No Event Selected", "Please select an event to add guest.");
			}
        });
        
        Button backButton = new Button("Back"); //Logika back btn untuk navigasi
        backButton.setOnAction(e -> {
        	HomePageView homePageView = new HomePageView();
            Main.redirect(homePageView.getHomePageScene());
        });

		// Add button and table to the grid
		gridPane.add(eventDetailsButton, 0, 0);
		gridPane.add(editBtn, 1, 0);
		gridPane.add(addVendorButton, 2, 0);
		gridPane.add(addGuestButton, 3, 0);
		gridPane.add(backButton, 4, 0);
		borderPane.setCenter(eventTable);
	}

	public Scene getOrganizedEventScene() {
		return organizedEventScene;
	}
	private void showAlert(String title, String message) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}
}
