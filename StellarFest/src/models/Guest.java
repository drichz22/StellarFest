package models;

public class Guest extends User {
	
	private String accepted_invitations;

	public Guest(String user_id, String user_email, String user_name, String user_password, String user_role, String accepted_invitations) {
		super(user_id, user_email, user_name, user_password, user_role);
		this.accepted_invitations = accepted_invitations;
	}

	public String getAccepted_invitations() {
		return accepted_invitations;
	}

	public void setAccepted_invitations(String accepted_invitations) {
		this.accepted_invitations = accepted_invitations;
	}
	
}