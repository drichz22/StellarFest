package controllers;

import java.util.ArrayList;

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

	public static ArrayList<Vendor> getVendorsByTransactionID(String eventID){
		return EventOrganizer.getVendorsByTransactionID(eventID);
	}

	public static ArrayList<Guest> getGuestsByTransactionID(String eventID){
		return EventOrganizer.getGuestsByTransactionID(eventID);
	}

	public static void editEventName(String eventID, String eventName) {
		EventOrganizer.editEventName(eventID, eventName);
	}

	public static String checkEditEventName(String eventName) {
		return EventOrganizer.checkEditEventName(eventName);
	}

	public static String checkAddVendorInput(String eventID, String vendorEmail) {
		return EventOrganizer.checkAddVendorInput(eventID, vendorEmail);
	}

	public static String checkAddGuestInput(String eventID, String guestEmail) {
		return EventOrganizer.checkAddGuestInput(eventID, guestEmail);
	}
}
