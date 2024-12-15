package views;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

public class AcceptInvitationView {
	
	private BorderPane borderPane;
	private Scene acceptInvitationScene;
	
	public AcceptInvitationView() {
		this.borderPane = new BorderPane();
		this.acceptInvitationScene = new Scene(borderPane, 800, 600);
	}

	public Scene getAcceptInvitationScene() {
		return acceptInvitationScene;
	}
}
