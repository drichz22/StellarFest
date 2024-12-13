package models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EventOrganizer extends User {

	private String events_created;

	public EventOrganizer(String user_id, String user_email, String user_name, String user_password, String user_role, String events_created) {
		super(user_id, user_email, user_name, user_password, user_role);
		this.events_created = events_created;
	}

	public String getEvents_created() {
		return events_created;
	}

	public void setEvents_created(String events_created) {
		this.events_created = events_created;
	}
	
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
}
