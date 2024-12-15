package models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import utils.Connect;

public class Invitation {
	private String invitation_id;
	private String event_id;
	private String user_id;
	private String invitation_status;
	private String invitation_role;
	protected static Connect connect = Connect.getInstance();

	//Untuk send invitation ke suatu event, disini saya menambahkan parameter eventID untuk mengetahui event mana yang di invite
	//dan parameter role untuk mengecek role si Usernya baru agar bisa dimasukkan kedalam DB
	public static boolean sendInvitation(String email, String eventID, String role) {
		String query = "INSERT INTO invitation (invitation_id, event_id, user_id, invitation_role, invitation_status) " +
				"VALUES (?, ?, (SELECT user_id FROM user WHERE user_email = ?), ? , 'Pending')";
		String lastId = "SELECT MAX(CAST(invitation_id AS UNSIGNED)) FROM invitation";
		
		//Pertama, cek nilai id tertinggi yang ada buat nanti di increment
		try (PreparedStatement countPs = connect.prepareStatement(lastId)){
			ResultSet rs = countPs.executeQuery();
			int newId = 1; //ID default kalau belum ada invitation
			while(rs.next()) {
				String lastId2 = rs.getString(1);
				if (lastId2 != null) {
					try {
						newId = Integer.parseInt(lastId2) + 1; // Increment invitation_id kalau ga kosong
					} catch (NumberFormatException e) {
						newId = 1; //Kl kosong, jdi 1 idnya
					}
				}
			}
			String invitation_id = Integer.toString(newId);
			
			try (PreparedStatement ps = connect.prepareStatement(query)){
				ps.setString(1, invitation_id);
				ps.setString(2, eventID);
				ps.setString(3, email);
				ps.setString(4, role);
				int rowsInserted = ps.executeUpdate();
				return rowsInserted > 0;
			}
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}

	public Invitation(String invitation_id, String event_id, String user_id, String invitation_status,
			String invitation_role) {
		super();
		this.invitation_id = invitation_id;
		this.event_id = event_id;
		this.user_id = user_id;
		this.invitation_status = invitation_status;
		this.invitation_role = invitation_role;
	}
	public String getInvitation_id() {
		return invitation_id;
	}
	public void setInvitation_id(String invitation_id) {
		this.invitation_id = invitation_id;
	}
	public String getEvent_id() {
		return event_id;
	}
	public void setEvent_id(String event_id) {
		this.event_id = event_id;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getInvitation_status() {
		return invitation_status;
	}
	public void setInvitation_status(String invitation_status) {
		this.invitation_status = invitation_status;
	}
	public String getInvitation_role() {
		return invitation_role;
	}
	public void setInvitation_role(String invitation_role) {
		this.invitation_role = invitation_role;
	}


}
