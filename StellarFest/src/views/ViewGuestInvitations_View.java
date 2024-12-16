package views;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

public class ViewGuestInvitations_View {
	
	private BorderPane borderPane;
	private Scene viewInvitationScene;
	
	public ViewGuestInvitations_View() {
		this.borderPane = new BorderPane();
		this.viewInvitationScene = new Scene(borderPane, 800, 600);
	}

	public Scene getViewInvitationScene() {
		return viewInvitationScene;
	}
	
}
