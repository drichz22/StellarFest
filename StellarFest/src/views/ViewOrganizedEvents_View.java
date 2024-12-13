package views;
	
import java.util.ArrayList;

import controllers.EventOrganizerController;
import javafx.collections.FXCollections;
	import javafx.collections.ObservableList;
	import javafx.geometry.Pos;
	import javafx.scene.Scene;
	import javafx.scene.control.TableColumn;
	import javafx.scene.control.TableView;
	import javafx.scene.control.cell.PropertyValueFactory;
	import javafx.scene.layout.BorderPane;
	import javafx.scene.layout.GridPane;
	import models.Event;
	
public class ViewOrganizedEvents_View {
	private Scene organizedEventScene;
	private BorderPane borderPane;
	private TableView<Event> eventTable;
	private ObservableList<Event> eventList;
		
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
	
	       TableColumn<Event, String> descriptionColumn = new TableColumn<>("Description");
	       descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("event_description"));
	        
	       TableColumn<Event, String> organizerIdColumn = new TableColumn<>("Organizer ID");
	       organizerIdColumn.setCellValueFactory(new PropertyValueFactory<>("organizer_id"));
	
	       // Nambahin kolom ke tabel
	       eventTable.getColumns().addAll(idColumn, nameColumn, dateColumn, locationColumn, descriptionColumn, organizerIdColumn);
	       eventTable.setItems(eventList);
	        
	       borderPane.setTop(gridPane);
	       borderPane.setCenter(eventTable);
	        
	       ArrayList<Event> events = EventOrganizerController.viewOrganizedEvents("1");
	       eventList = FXCollections.observableArrayList(events);
	       eventTable.setItems(eventList);
		}
		
		public Scene getOrganizedEventScene() {
			return organizedEventScene;
		}
	}
