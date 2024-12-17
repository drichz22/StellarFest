package models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;

public class Guest extends User {
	
	private String accepted_invitations;

	public Guest(String user_id, String user_email, String user_name, String user_password, String user_role) {
		super(user_id, user_email, user_name, user_password, user_role);
		this.accepted_invitations = "0";
	}

	public String getAccepted_invitations() {
		return accepted_invitations;
	}

	public void setAccepted_invitations(String accepted_invitations) {
		this.accepted_invitations = accepted_invitations;
	}
	
	//mendapatkan semua event_id, invitation_status, dan event_name fitur accept invitation
	public static ArrayList<ObservableMap<String, String>> getAllGuestInvitations() {
	    ArrayList<ObservableMap<String, String>> invitations = new ArrayList<>(); //Pakai map agar tidak membuat model baru
	    String query = "SELECT inv.event_id, inv.invitation_status, e.event_name, u.user_name AS organizer_username, u.user_email AS organizer_email FROM invitation inv "
	            + "INNER JOIN event e ON inv.event_id = e.event_id "
	            + "INNER JOIN user u ON e.organizer_id = u.user_id "
	            + "WHERE inv.invitation_role = 'Guest' AND inv.user_id = ?";
	    PreparedStatement ps = connect.prepareStatement(query);

	    try {
	        ps.setString(1, SessionManager.getLoggedInUser().getUser_id());
	        ResultSet resultSet = ps.executeQuery();

	        while (resultSet.next()) {
	            ObservableMap<String, String> invitationDetails = FXCollections.observableHashMap();
	            invitationDetails.put("event_id", resultSet.getString("event_id"));
	            invitationDetails.put("event_name", resultSet.getString("event_name"));
	            invitationDetails.put("invitation_status", resultSet.getString("invitation_status"));
	            invitationDetails.put("organizer_username", resultSet.getString("organizer_username"));
	            invitationDetails.put("organizer_email", resultSet.getString("organizer_email"));

	            invitations.add(invitationDetails);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return invitations;
	}

	//Untuk update invitation_status dari 'Pending' menjadi 'Accepted'
	public static boolean acceptGuestInvitation(String eventId) {
		String query = "UPDATE invitation SET invitation_status = 'Accepted' WHERE invitation_role = 'Guest' AND event_id = ? AND user_id = ?";
		PreparedStatement ps = connect.prepareStatement(query);
		int rowsAffected = 0;
		
		try {
			ps.setString(1, eventId);
			ps.setString(2, SessionManager.getLoggedInUser().getUser_id());
			rowsAffected = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return rowsAffected > 0;
	}
	
	//Untuk validasi kalau item yang dipilih pada tabel valid
	public static Invitation getInvitationByEventId(String eventId){
		Invitation inv = null;
		String query = "SELECT * FROM invitation WHERE event_id = ? AND invitation_role = 'Guest' AND user_id = ?";
		PreparedStatement ps = connect.prepareStatement(query);
		
		try {
			ps.setString(1, eventId);
			ps.setString(2, SessionManager.getLoggedInUser().getUser_id());
			ResultSet resultSet = ps.executeQuery();
			if(resultSet.next()) {
				String invitationId = resultSet.getString("invitation_id");
				String eventID = resultSet.getString("event_id");
				String userId = resultSet.getString("user_id");
				String invitationStatus = resultSet.getString("invitation_status");
				String invitationRole = resultSet.getString("invitation_role");
				
				inv = new Invitation(invitationId, eventID, userId, invitationStatus, invitationRole);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return inv;
	}
	
	//Untuk mendapatkan semua invitation yang di accept oleh guest
	public static ArrayList<Invitation> getAllAcceptedEvents(String email){
		ArrayList<Invitation> invitations = new ArrayList<>();
		String query = "SELECT * FROM invitation WHERE invitation_role = 'Guest' AND user_id = (SELECT user_id FROM user WHERE user_email = ?) AND invitation_role = 'Guest' AND invitation_status = 'Accepted'";
		PreparedStatement ps = connect.prepareStatement(query);
		
		try {
			ps.setString(1, email);
			ResultSet resultSet = ps.executeQuery();
			while(resultSet.next()) {
				String invitationId = resultSet.getString("invitation_id");
				String eventId = resultSet.getString("event_id");
				String userId = resultSet.getString("user_id");
				String invitationStatus = resultSet.getString("invitation_status");
				String invitationRole = resultSet.getString("invitation_role");
				
				Invitation inv = new Invitation(invitationId, eventId, userId, invitationStatus, invitationRole);
				invitations.add(inv);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return invitations;
	}
	
	//Untuk mendapatkan detail event dari setiap invitation yang di accept oleh guest
	public static ArrayList<Event> getAllAcceptedEventDetails(String email){
		ArrayList<Event> events = new ArrayList<>();
		String query = "SELECT ev.* FROM event ev INNER JOIN invitation inv ON inv.event_id = ev.event_id WHERE inv.invitation_role = 'Guest' AND inv.invitation_status = 'Accepted' AND inv.user_id = (SELECT user_id FROM user WHERE user_email = ?)";
		PreparedStatement ps = connect.prepareStatement(query);
		
		try {
			ps.setString(1, email);
			ResultSet resultSet = ps.executeQuery();
			while (resultSet.next()) {
				String event_id = resultSet.getString("event_id");
				String event_name = resultSet.getString("event_name");
				String event_date = resultSet.getString("event_date");
				String event_location = resultSet.getString("event_location");
				String event_description = resultSet.getString("event_description");
				String organizer_id = resultSet.getString("organizer_id");
				
				Event event = new Event(event_id, event_name, event_date, event_location, event_description, organizer_id);
				events.add(event);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return events;
	}
	
}
