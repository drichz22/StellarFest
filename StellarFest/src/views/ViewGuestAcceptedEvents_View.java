package views;

import java.util.ArrayList;

import controllers.GuestController;
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
import models.Invitation;
import models.SessionManager;

public class ViewGuestAcceptedEvents_View {
	
	private BorderPane borderPane;
	private Scene viewAcceptedEventScene;
	private Button viewEventDetailsBtn;
	private TableView<Invitation> acceptedInvTable;
	private ObservableList<Invitation> acceptedInvData;
	private ArrayList<Invitation> acceptedInvs;
	
	public ViewGuestAcceptedEvents_View() {
		this.borderPane = new BorderPane();
		this.viewAcceptedEventScene = new Scene(borderPane, 800, 600);
		
		viewEventDetailsBtn = new Button("View Event Details");
		
		GridPane gridPane = new GridPane();
		gridPane.setAlignment(Pos.CENTER);
		gridPane.setHgap(5);
		gridPane.setVgap(5);

		//Buat kolom2 yang dibutuhkan 
		acceptedInvTable = new TableView<>();
		TableColumn<Invitation, String> idColumn = new TableColumn<>("Invitation ID");
		idColumn.setCellValueFactory(new PropertyValueFactory<>("invitation_id"));
		
		TableColumn<Invitation, String> statusColumn = new TableColumn<>("Invitation Status");
		statusColumn.setCellValueFactory(new PropertyValueFactory<>("invitation_status"));
		
		TableColumn<Invitation, String> eventIdColumn = new TableColumn<>("Event ID");
		eventIdColumn.setCellValueFactory(new PropertyValueFactory<>("event_id"));

		// Nambahin kolom ke tabel
		acceptedInvTable.getColumns().addAll(idColumn, statusColumn, eventIdColumn);

		borderPane.setTop(gridPane); //set button ke atas
		borderPane.setCenter(acceptedInvTable);

		//Ambil data dari db untuk ditambah ke tabel
		acceptedInvs = GuestController.getAllAcceptedEvents(SessionManager.getLoggedInUser().getUser_email());
		acceptedInvData = FXCollections.observableArrayList(acceptedInvs);
		acceptedInvTable.setItems(acceptedInvData);

		//Redirect ke view accepted event details
	    viewEventDetailsBtn.setOnAction(Event->{
	    	ViewGuestAcceptedEventDetails_View viewGuestAcceptedEventDetails = new ViewGuestAcceptedEventDetails_View();
	    	Main.redirect(viewGuestAcceptedEventDetails.getViewAcceptedEventDetailsScene());
	    });
	    
        Button backButton = new Button("Back"); //Logika back btn untuk navigasi
        backButton.setOnAction(e -> {
        	HomePageView homePageView = new HomePageView();
            Main.redirect(homePageView.getHomePageScene());
        });
        
        // Tambah button ke grid
     	gridPane.add(viewEventDetailsBtn, 0, 0);
     	gridPane.add(backButton, 1, 0);
     	borderPane.setCenter(acceptedInvTable);
	}

	public Scene getViewAcceptedEventScene() {
		return viewAcceptedEventScene;
	}

}
