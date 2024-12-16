package views;

import java.util.ArrayList;

import controllers.AdminController;
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

public class ViewAllEvents_View {
    private Scene allEventsScene;
    private BorderPane borderPane;
    private TableView<Event> eventTable;
    private ObservableList<Event> eventList;
    private Button eventDetailsButton;

    public ViewAllEvents_View() {
        this.borderPane = new BorderPane();
        this.allEventsScene = new Scene(borderPane, 800, 600);

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

        // Menambahkan kolom ke tabel
        eventTable.getColumns().addAll(idColumn, nameColumn, dateColumn, locationColumn);
        eventTable.setItems(eventList);

        borderPane.setTop(gridPane);
        borderPane.setCenter(eventTable);

        // Isi tabel dengan data dari database
        ArrayList<Event> events = AdminController.viewAllEvents();
        eventList = FXCollections.observableArrayList(events);
        eventTable.setItems(eventList);
        

        // Buat tombol untuk melihat detail event
        eventDetailsButton = new Button("View Event Details");
        eventDetailsButton.setOnAction(event -> {
            Event selectedEvent = eventTable.getSelectionModel().getSelectedItem();
            if (selectedEvent != null) {
                ViewEventDetails_View eventDetailView = new ViewEventDetails_View(selectedEvent.getEvent_id());
                Main.redirect(eventDetailView.getViewEventDetailsScene());
            } else {
                showAlert("No Event Selected", "Please select an event to view details.");
            }
        });

        // Tambahkan tombol untuk melihat event ke grid
        gridPane.add(eventDetailsButton, 0, 0);
        borderPane.setCenter(eventTable);
    }

    public Scene getAllEventsScene() {
        return allEventsScene;
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
