package models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import utils.Connect;

public class Event {
	private String event_id;
	private String event_name;
	private String event_date;
	private String event_location;
	private String event_description;
	private String organizer_id;
	protected static Connect connect = Connect.getInstance();

	public static boolean createEvent(String event_name, String event_date, String event_location, String event_description,
			String organizer_id) {
		String query = "INSERT INTO event (event_id, event_name, event_date, event_location, event_description, organizer_id) VALUES (?, ?, ?, ?, ?, ?)";
		String lastId = "SELECT MAX(CAST(event_id AS UNSIGNED)) FROM event";

		//Pertama, cek nilai id tertinggi yang ada buat nanti di increment
		try (PreparedStatement countPs = connect.prepareStatement(lastId)) {
			ResultSet rs = countPs.executeQuery();
			int newId = 1; //ID default kalau belum ada event

			while(rs.next()) {
				String lastId2 = rs.getString(1);
				if (lastId2 != null) {
					try {
						newId = Integer.parseInt(lastId2) + 1; // Increment event_id kalau ga kosong
					} catch (NumberFormatException e) {
						newId = 1; //Kl kosong, jdi 1 idnya
					}
				}
			}
			String event_id = Integer.toString(newId);

			try (PreparedStatement ps = connect.prepareStatement(query)){
				ps.setString(1, event_id);
				ps.setString(2, event_name);
				ps.setString(3, event_date);
				ps.setString(4, event_location);
				ps.setString(5, event_description);
				ps.setString(6, organizer_id);
				int rowsInserted = ps.executeUpdate();
				return rowsInserted > 0;
			}
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}	
	}

	//Validasi input saat create Event
	public static String checkCreateEventInput(String eventName, String eventDate, String eventLocation, String eventDescription) {
		// Validasi: Semua field harus diisi
		if (eventName.isEmpty() || eventDate.isEmpty() || eventLocation.isEmpty() || eventDescription.isEmpty()) {
			return "All fields are required.";
		}

		// Validasi Event Date harus di masa depan
		try {
			LocalDate date = LocalDate.parse(eventDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
			if (!date.isAfter(LocalDate.now())) {
				return "Event date must be in the future.";
			}
		} catch (DateTimeParseException e) {
			return "Invalid date format. Please use yyyy-MM-dd.";
		}

		// Validasi panjang minimal Event Location
		if (eventLocation.length() < 5) {
			return "Event location must be at least 5 characters long.";
		}

		// Validasi panjang maksimum Event Description
		if (eventDescription.length() > 200) {
			return "Event description must be a maximum of 200 characters.";
		}

		return null; // Semua validasi lolos
	}

	public Event(String event_id, String event_name, String event_date, String event_location, String event_description,
			String organizer_id) {
		super();
		this.event_id = event_id;
		this.event_name = event_name;
		this.event_date = event_date;
		this.event_location = event_location;
		this.event_description = event_description;
		this.organizer_id = organizer_id;
	}
	public String getEvent_id() {
		return event_id;
	}
	public void setEvent_id(String event_id) {
		this.event_id = event_id;
	}
	public String getEvent_name() {
		return event_name;
	}
	public void setEvent_name(String event_name) {
		this.event_name = event_name;
	}
	public String getEvent_date() {
		return event_date;
	}
	public void setEvent_date(String event_date) {
		this.event_date = event_date;
	}
	public String getEvent_location() {
		return event_location;
	}
	public void setEvent_location(String event_location) {
		this.event_location = event_location;
	}
	public String getEvent_description() {
		return event_description;
	}
	public void setEvent_description(String event_description) {
		this.event_description = event_description;
	}
	public String getOrganizer_id() {
		return organizer_id;
	}
	public void setOrganizer_id(String organizer_id) {
		this.organizer_id = organizer_id;
	}
}
