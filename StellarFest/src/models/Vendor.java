package models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;

public class Vendor extends User{
	
	private String accepted_invitations;

	public Vendor(String user_id, String user_email, String user_name, String user_password, String user_role) {
		super(user_id, user_email, user_name, user_password, user_role);
		this.accepted_invitations = "0";
	}	
	
	public String getAccepted_invitations() {
		return accepted_invitations;
	}

	public void setAccepted_invitations(String accepted_invitations) {
		this.accepted_invitations = accepted_invitations;
	}
	
	public static ArrayList<ObservableMap<String, String>> getAllVendorInvitations() {
	    ArrayList<ObservableMap<String, String>> invitations = new ArrayList<>(); //Pakai map agar tidak membuat model baru
	    String query = "SELECT inv.event_id, inv.invitation_status, e.event_name, u.user_name AS organizer_username, u.user_email AS organizer_email FROM invitation inv "
	            + "INNER JOIN event e ON inv.event_id = e.event_id "
	            + "INNER JOIN user u ON e.organizer_id = u.user_id "
	            + "WHERE inv.invitation_role = 'Vendor' AND inv.user_id = ?";
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
	public static boolean acceptVendorInvitation(String eventId) {
		String query = "UPDATE invitation SET invitation_status = 'Accepted' WHERE invitation_role = 'Vendor' AND event_id = ? AND user_id = ?";
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
		String query = "SELECT * FROM invitation WHERE event_id = ? AND invitation_role = 'Vendor' AND user_id = ?";
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
		String query = "SELECT * FROM invitation WHERE invitation_role = 'Vendor' AND user_id = (SELECT user_id FROM user WHERE user_email = ?) AND invitation_role = 'Vendor' AND invitation_status = 'Accepted'";
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
		String query = "SELECT ev.* FROM event ev INNER JOIN invitation inv ON inv.event_id = ev.event_id WHERE inv.invitation_role = 'Vendor' AND inv.invitation_status = 'Accepted' AND inv.user_id = (SELECT user_id FROM user WHERE user_email = ?)";
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
	
	public static void manageVendor(String productID, String productName, String productDescription) {
		String query = "UPDATE product SET product_name = ?, product_description = ? WHERE product_id = ?";
		PreparedStatement ps = connect.prepareStatement(query);
		try {
			ps.setString(1, productName);
			ps.setString(2, productDescription);
			ps.setString(3, productID);
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public static String checkManageVendorInput(String productDescription, String productName) {
		// Validasi namenya harus diisi
		if (productName.isEmpty()) {
			return "Name fields are required.";
		}
		
		if (productDescription.isEmpty()) {
			return "Description fields are required.";
		}
		
		// Validasi panjang teks minimum
		if (productDescription.length() >  200) {
			return "Product description must not exceed 200 characters";
		}

		return null; // validasi lolos
	}
	
	public static ArrayList<Product> viewAllProducts() {
    	ArrayList<Product> products = new ArrayList<>();
        String query = "SELECT * FROM product";

        try (PreparedStatement ps = connect.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                String product_id = rs.getString("product_id");
                String vendor_id = rs.getString("vendor_id");
                String product_name = rs.getString("product_name");
                String product_description = rs.getString("product_description");
  
                Product product = new Product(product_id, vendor_id, product_name, product_description);
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }   
	
	public static Product viewProductDetails(String productID){
		Product product = null;
		String query = "SELECT * FROM product WHERE product_id = ?";

		try (PreparedStatement ps = connect.prepareStatement(query)){	
			ps.setString(1, productID);
			ResultSet resultSet = ps.executeQuery();
			while(resultSet.next()) {
				String product_id = resultSet.getString("product_id");
                String vendor_id = resultSet.getString("vendor_id");
                String product_name = resultSet.getString("product_name");
                String product_description = resultSet.getString("product_description");
  

                product = new Product(product_id, vendor_id, product_name, product_description);
			}
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return product;
	}

}
