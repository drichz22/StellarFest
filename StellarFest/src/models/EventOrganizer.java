package models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;

import javafx.scene.control.Alert;

public class EventOrganizer extends User {

	private String events_created;

	//Construtor
	public EventOrganizer(String user_id, String user_email, String user_name, String user_password, String user_role, String events_created) {
		super(user_id, user_email, user_name, user_password, user_role);
		this.events_created = events_created;
	}

	//Getter and Setter
	public String getEvents_created() {
		return events_created;
	}

	public void setEvents_created(String events_created) {
		this.events_created = events_created;
	}

	//	Validasi input saat create Event -> Ini saya pindahkan ke Event dan EventController, karena method createnya ada di Event

	//	public static String checkCreateEventInput(String eventName, String eventDate, String eventLocation, String eventDescription) {
	//	    // Validasi: Semua field harus diisi
	//	    if (eventName.isEmpty() || eventDate.isEmpty() || eventLocation.isEmpty() || eventDescription.isEmpty()) {
	//	        return "All fields are required.";
	//	    }
	//
	//	    // Validasi Event Date harus di masa depan
	//	    try {
	//	        LocalDate date = LocalDate.parse(eventDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
	//	        if (!date.isAfter(LocalDate.now())) {
	//	            return "Event date must be in the future.";
	//	        }
	//	    } catch (DateTimeParseException e) {
	//	        return "Invalid date format. Please use yyyy-MM-dd.";
	//	    }
	//
	//	    // Validasi panjang minimal Event Location
	//	    if (eventLocation.length() < 5) {
	//	        return "Event location must be at least 5 characters long.";
	//	    }
	//
	//	    // Validasi panjang maksimum Event Description
	//	    if (eventDescription.length() > 200) {
	//	        return "Event description must be a maximum of 200 characters.";
	//	    }
	//
	//	    return null; // Semua validasi lolos
	//	}

	//Untuk melihat list event yang dibuat oleh EventOrganizer
	public static ArrayList<Event> viewOrganizedEvents(String userID){
		ArrayList<Event> eventList = new ArrayList<>();
		String query = "SELECT * FROM event WHERE organizer_id = ?";

		try (PreparedStatement ps = connect.prepareStatement(query)){	
			ps.setString(1, userID);
			ResultSet resultSet = ps.executeQuery();
			while(resultSet.next()) {
				String eventId = resultSet.getString("event_id");
				String eventName = resultSet.getString("event_name");
				String eventDate = resultSet.getString("event_date");
				String eventLocation = resultSet.getString("event_location");
				String eventDescription = resultSet.getString("event_description");
				String organizerId = resultSet.getString("organizer_id");

				Event event = new Event(eventId, eventName, eventDate, eventLocation, eventDescription, organizerId);

				eventList.add(event);
			}
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return eventList;
	}

	//Untuk melihat event detail dari sebuah event yang dibuat oleh EventOrganizer
	public static Event viewOrganizedEventDetails(String eventID){
		Event event = null;
		String query = "SELECT * FROM event WHERE event_id = ?";

		try (PreparedStatement ps = connect.prepareStatement(query)){	
			ps.setString(1, eventID);
			ResultSet resultSet = ps.executeQuery();
			while(resultSet.next()) {
				String eventId = resultSet.getString("event_id");
				String eventName = resultSet.getString("event_name");
				String eventDate = resultSet.getString("event_date");
				String eventLocation = resultSet.getString("event_location");
				String eventDescription = resultSet.getString("event_description");
				String organizerId = resultSet.getString("organizer_id");

				event = new Event(eventId, eventName, eventDate, eventLocation, eventDescription, organizerId);
			}
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return event;
	}

	//Untuk mendapatkan list vendor
	public static ArrayList<Vendor> getVendors(){
		String query = "SELECT * FROM user WHERE user_role = 'Vendor'";
		ArrayList<Vendor> vendorList = new ArrayList<>();
		PreparedStatement ps = connect.prepareStatement(query);
		try {
			ResultSet resultSet = ps.executeQuery();
			while(resultSet.next()) {
				String vendorId = resultSet.getString("user_id");
				String vendorName = resultSet.getString("user_name");
				String vendorEmail = resultSet.getString("user_email");
				String vendorPassword = resultSet.getString("user_password");
				String vendorRole = resultSet.getString("user_role");

				Vendor vendor = new Vendor(vendorId, vendorEmail, vendorName, vendorPassword, vendorRole);
				vendorList.add(vendor);
			}
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return vendorList;
	}

	//Untuk mendapatkan list guest
	public static ArrayList<Guest> getGuests(){
		String query = "SELECT * FROM user WHERE user_role = 'Guest'";
		ArrayList<Guest> guestList = new ArrayList<>();
		PreparedStatement ps = connect.prepareStatement(query);
		try {
			ResultSet resultSet = ps.executeQuery();
			while(resultSet.next()) {
				String guestId = resultSet.getString("user_id");
				String guestName = resultSet.getString("user_name");
				String guestEmail = resultSet.getString("user_email");
				String guestPassword = resultSet.getString("user_password");
				String guestRole = resultSet.getString("user_role");

				Guest guest = new Guest(guestId, guestEmail, guestName, guestPassword, guestRole);
				guestList.add(guest);
			}
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return guestList;
	}

	//Untuk mendapatkan list Vendor berdasarkan event
	public static HashMap<Vendor, String> getVendorsByTransactionID(String eventID){
		HashMap<Vendor, String> vendorMap = new HashMap<>();
		String query = "SELECT DISTINCT u.user_id, user_email, user_name, user_password, user_role, i.invitation_status " +
				"FROM user u JOIN invitation i ON u.user_id = i.user_id " +
				"WHERE i.event_id = ? AND i.invitation_role = 'Vendor'";

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
					String invitationStatus = resultSet.getString("invitation_status");

					// Buat objek Vendor
					Vendor vendor = new Vendor(vendorId, vendorEmail, vendorName, vendorPassword, vendorRole);

					// Tambahkan Vendor ke HashMap
					vendorMap.put(vendor, invitationStatus);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return vendorMap;
	}
	//Untuk mendapatkan list Guest berdasarkan event
	public static HashMap<Guest, String> getGuestsByTransactionID(String eventID){
		HashMap<Guest, String> guestMap = new HashMap<>();
		String query = "SELECT DISTINCT u.user_id, user_email, user_name, user_password, user_role, i.invitation_status " +
				"FROM user u JOIN invitation i ON u.user_id = i.user_id " +
				"WHERE i.event_id = ? AND i.invitation_role = 'Guest'";

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
					String invitationStatus = resultSet.getString("invitation_status");

					// Buat objek Guest
					Guest guest = new Guest(guestId, guestEmail, guestName, guestPassword, guestRole);

					// Tambahkan Guest ke HashMap
					guestMap.put(guest, invitationStatus);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return guestMap;
	}

	//Untuk mengedit nama event
	public static void editEventName(String eventID, String eventName) {
		String query = "UPDATE event SET event_name = ? WHERE event_id = ?";
		PreparedStatement ps = connect.prepareStatement(query);
		try {
			ps.setString(1, eventName);
			ps.setString(2, eventID);
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	//Validasi nama event saat edit EventName
	public static String checkEditEventName(String eventName) {
		// Validasi namenya harus diisi
		if (eventName.isEmpty()) {
			return "Name fields are required.";
		}

		// Validasi panjang teks minimum
		if (eventName.length() < 5) {
			return "Event name must be at least 5 characters long.";
		}

		return null; // validasi lolos
	}
}
