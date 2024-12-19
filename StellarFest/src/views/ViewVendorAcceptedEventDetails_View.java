package views;

import java.util.ArrayList;

import controllers.VendorController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import main.Main;
import models.Event;
import models.SessionManager;

public class ViewVendorAcceptedEventDetails_View {
	private BorderPane borderPane;
	private Scene viewAcceptedEventDetailsScene;
	private TableView<Event> acceptedEventTable;
	private ObservableList<Event> acceptedEventData;
	private ArrayList<Event> acceptedEvents;
	
	public ViewVendorAcceptedEventDetails_View() {
		this.borderPane = new BorderPane();
		this.viewAcceptedEventDetailsScene = new Scene(borderPane, 800, 600);
		
		GridPane gridPane = new GridPane();
		gridPane.setAlignment(Pos.CENTER);
		gridPane.setHgap(5);
		gridPane.setVgap(5);

		//Buat kolom2 yang dibutuhkan 
		acceptedEventTable = new TableView<>();
		TableColumn<Event, String> nameColumn = new TableColumn<>("Event Name");
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("event_name"));
		
		TableColumn<Event, String> dateColumn = new TableColumn<>("Event Date");
		dateColumn.setCellValueFactory(new PropertyValueFactory<>("event_date"));
		
		TableColumn<Event, String> locationColumn = new TableColumn<>("Event Location");
		locationColumn.setCellValueFactory(new PropertyValueFactory<>("event_location"));
		
		TableColumn<Event, String> descColumn = new TableColumn<>("Event Description");
		descColumn.setCellValueFactory(new PropertyValueFactory<>("event_description"));

		// Nambahin kolom ke tabel
		acceptedEventTable.getColumns().addAll(nameColumn, dateColumn, locationColumn, descColumn);

		borderPane.setTop(gridPane);
		borderPane.setCenter(acceptedEventTable);

		//Ambil data dari db untuk ditambah ke tabel
		acceptedEvents = VendorController.viewAcceptedEvents(SessionManager.getLoggedInUser().getUser_email());
		acceptedEventData = FXCollections.observableArrayList(acceptedEvents);
		acceptedEventTable.setItems(acceptedEventData);

        Button backButton = new Button("Back"); //Logika back btn untuk navigasi
        backButton.setOnAction(e -> {
        	HomePageView homePageView = new HomePageView();
            Main.redirect(homePageView.getHomePageScene());
        });
        
        // Add button and table to the grid
     	gridPane.add(backButton, 0, 0);
     	borderPane.setCenter(acceptedEventTable);
	}

	public Scene getViewAcceptedEventDetailsScene() {
		return viewAcceptedEventDetailsScene;
	}
}
