package views;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

public class ViewGuestAcceptedEventDetails_View {
	
	private BorderPane borderPane;
	private Scene viewAcceptedEventDetailsScene;
	
	public ViewGuestAcceptedEventDetails_View() {
		this.borderPane = new BorderPane();
		this.viewAcceptedEventDetailsScene = new Scene(borderPane, 800, 600);
	}

	public Scene getViewAcceptedEventDetailsScene() {
		return viewAcceptedEventDetailsScene;
	}

}
