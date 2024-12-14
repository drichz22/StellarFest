package controllers;

import models.Event;

public class EventController {
	public static boolean createEvent(String event_name, String event_date, String event_location, String event_description,
			String organizer_id) {
		return Event.createEvent(event_name, event_date, event_location, event_description, organizer_id);
	}
	public static String checkCreateEventInput(String eventName, String eventDate, String eventLocation, String eventDescription) {
		return Event.checkCreateEventInput(eventName, eventDate, eventLocation, eventDescription);
	}
}
