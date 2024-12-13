package controllers;

import java.util.ArrayList;

import models.Event;
import models.EventOrganizer;

public class EventOrganizerController {
	public static ArrayList<Event> viewOrganizedEvents(String userID){
		return EventOrganizer.viewOrganizedEvents(userID);
	}
}
