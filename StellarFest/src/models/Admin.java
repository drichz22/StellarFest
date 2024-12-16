package models;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import utils.Connect;

public class Admin extends User {
	protected static Connect connect = Connect.getInstance();

	public Admin(String user_id, String user_email, String user_name, String user_password, String user_role) {
		super(user_id, user_email, user_name, user_password, user_role);
	}
	
	// Method untuk melihat semua event
    public static ArrayList<Event> viewAllEvents() {
    	ArrayList<Event> events = new ArrayList<>();
        String query = "SELECT * FROM event";

        try (PreparedStatement ps = connect.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                String event_id = rs.getString("event_id");
                String event_name = rs.getString("event_name");
                String event_date = rs.getString("event_date");
                String event_location = rs.getString("event_location");
                String event_description = rs.getString("event_description");
                String organizer_id = rs.getString("organizer_id");

                Event event = new Event(event_id, event_name, event_date, event_location, event_description, organizer_id);
                events.add(event);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return events;
    }    


    // Method untuk melihat detail event berdasarkan ID
    public static Event viewEventDetails(String eventID) {
        String query = "SELECT * FROM event WHERE event_id = ?";
        Event event = null;

        try (PreparedStatement ps = connect.prepareStatement(query)) {
            ps.setString(1, eventID);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String event_id = rs.getString("event_id");
                    String event_name = rs.getString("event_name");
                    String event_date = rs.getString("event_date");
                    String event_location = rs.getString("event_location");
                    String event_description = rs.getString("event_description");
                    String organizer_id = rs.getString("organizer_id");

                    event = new Event(event_id, event_name, event_date, event_location, event_description, organizer_id);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return event;
    }
    
    // Method untuk menghapus event berdasarkan ID
    public static boolean deleteEvent(String eventID) {
        String query = "DELETE FROM event WHERE event_id = ?";
        try (PreparedStatement ps = connect.prepareStatement(query)) {
            ps.setString(1, eventID);
            int rowsDeleted = ps.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }   

    // Method untuk menghapus user berdasarkan ID
    public static boolean deleteUser(String userID) {
        String query = "DELETE FROM user WHERE user_id = ?";
        try (PreparedStatement ps = connect.prepareStatement(query)) {
            ps.setString(1, userID);
            int rowsDeleted = ps.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }   

    // Method untuk mengambil semua user
    public static ArrayList<User> getAllUsers() {
    	ArrayList<User> users = new ArrayList<>();
        String query = "SELECT * FROM user";

        try (PreparedStatement ps = connect.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                String user_id = rs.getString("user_id");
                String user_email = rs.getString("user_email");
                String user_name = rs.getString("user_name");
                String user_password = rs.getString("user_password");
                String role = rs.getString("user_role");

                User user = new User(user_id, user_email, user_name, user_password, role);
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }   

  //Untuk mendapatkan list Vendor berdasarkan event
  	public static ArrayList<Vendor> getVendorsByTransactionID(String eventID){
  		ArrayList<Vendor> vendorList = new ArrayList<>();
  		String query = "SELECT DISTINCT u.user_id, user_email, user_name, user_password, user_role " +
  				"FROM user u JOIN invitation i ON u.user_id = i.user_id " +
  				"WHERE i.event_id = ? AND i.invitation_role = 'Vendor' AND i.invitation_status = 'Accepted'";

  		try (PreparedStatement ps = connect.prepareStatement(query)) {
  			ps.setString(1, eventID);
  			try (ResultSet resultSet = ps.executeQuery()) {
  				while (resultSet.next()) {
  					// Ambil data dari ResultSet
  					String vendorId = resultSet.getString("user_id");
  					String vendorName = resultSet.getString("user_name");
  					String vendorEmail = resultSet.getString("user_email");
  					String vendorPassword = resultSet.getString("user_password");
  					String vendorRole = resultSet.getString("user_role");

  					// Buat objek Vendor
  					Vendor vendor = new Vendor(vendorId, vendorEmail, vendorName, vendorPassword, vendorRole);
  					vendorList.add(vendor);
  				}
  			}
  		} catch (SQLException e) {
  			e.printStackTrace();
  		}
  		return vendorList;
  	}
  	
  	//Untuk mendapatkan list Guest berdasarkan event
  	public static ArrayList<Guest> getGuestsByTransactionID(String eventID){
  		ArrayList<Guest> guestList = new ArrayList<>();
  		String query = "SELECT DISTINCT u.user_id, user_email, user_name, user_password, user_role " +
  				"FROM user u JOIN invitation i ON u.user_id = i.user_id " +
  				"WHERE i.event_id = ? AND i.invitation_role = 'Guest' AND i.invitation_status = 'Accepted'";

  		try (PreparedStatement ps = connect.prepareStatement(query)) {
  			ps.setString(1, eventID);
  			try (ResultSet resultSet = ps.executeQuery()) {
  				while (resultSet.next()) {
  					// Ambil data dari ResultSet
  					String guestId = resultSet.getString("user_id");
  					String guestName = resultSet.getString("user_name");
  					String guestEmail = resultSet.getString("user_email");
  					String guestPassword = resultSet.getString("user_password");
  					String guestRole = resultSet.getString("user_role");

  					// Buat objek Guest
  					Guest guest = new Guest(guestId, guestEmail, guestName, guestPassword, guestRole);
  					guestList.add(guest);
  				}
  			}
  		} catch (SQLException e) {
  			e.printStackTrace();
  		}
  		return guestList;
  	}
}
