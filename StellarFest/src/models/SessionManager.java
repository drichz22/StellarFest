package models;

public class SessionManager { //class ini untuk meng-capture data current User yang log in di aplikasi
	
	private static User loggedInUser;

	public static User getLoggedInUser() {
		return loggedInUser;
	}

	public static void setLoggedInUser(User loggedInUser) {
		SessionManager.loggedInUser = loggedInUser;
	}
	
}
