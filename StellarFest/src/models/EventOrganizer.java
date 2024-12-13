package models;

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
	
}
