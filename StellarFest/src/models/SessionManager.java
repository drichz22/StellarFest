package models;

public class SessionManager {
	
	private static User loggedInUser;

	public static User getLoggedInUser() {
		return loggedInUser;
	}

	public static void setLoggedInUser(User loggedInUser) {
		SessionManager.loggedInUser = loggedInUser;
	}
	
}
