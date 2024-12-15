package views;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

public class ViewAcceptedEvents_View {
	
	private BorderPane borderPane;
	private Scene viewAcceptedEventScene;
	
	public ViewAcceptedEvents_View() {
		this.borderPane = new BorderPane();
		this.viewAcceptedEventScene = new Scene(borderPane, 800, 600);
	}

	public Scene getViewAcceptedEventScene() {
		return viewAcceptedEventScene;
	}

}
