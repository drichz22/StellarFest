package controllers;

import java.util.ArrayList;
import java.util.HashMap;

import models.Event;
import models.EventOrganizer;
import models.Guest;
import models.Vendor;

public class EventOrganizerController {
	public static ArrayList<Event> viewOrganizedEvents(String userID){
		return EventOrganizer.viewOrganizedEvents(userID);
	}

	public static Event viewOrganizedEventDetails(String eventID) {
		return EventOrganizer.viewOrganizedEventDetails(eventID);
	}

	public static ArrayList<Vendor> getVendors(){
		return EventOrganizer.getVendors();
	}

	public static ArrayList<Guest> getGuests(){
		return EventOrganizer.getGuests();
	}

	public static HashMap<Vendor, String> getVendorsByTransactionID(String eventID){
		return EventOrganizer.getVendorsByTransactionID(eventID);
	}
	
	public static HashMap<Guest, String> getGuestsByTransactionID(String eventID){
		return EventOrganizer.getGuestsByTransactionID(eventID);
	}

	public static void editEventName(String eventID, String eventName) {
		EventOrganizer.editEventName(eventID, eventName);
	}

	public static String checkEditEventName(String eventName) {
		return EventOrganizer.checkEditEventName(eventName);
	}
}
