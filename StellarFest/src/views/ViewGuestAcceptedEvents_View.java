package views;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

public class ViewGuestAcceptedEvents_View {
	
	private BorderPane borderPane;
	private Scene viewAcceptedEventScene;
	
	public ViewGuestAcceptedEvents_View() {
		this.borderPane = new BorderPane();
		this.viewAcceptedEventScene = new Scene(borderPane, 800, 600);
	}

	public Scene getViewAcceptedEventScene() {
		return viewAcceptedEventScene;
	}

}
