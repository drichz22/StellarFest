package controllers;

import models.Admin;
import models.Event;
import models.User;
import models.Guest;
import models.Vendor;

import java.util.ArrayList;

public class AdminController {
	
    public static ArrayList<Event> viewAllEvents() {
        return Admin.viewAllEvents();
    }
    
    public static Event viewEventDetails(String eventID) {
        return Admin.viewEventDetails(eventID);
    }

    public static boolean deleteEvent(String eventID) {
        return Admin.deleteEvent(eventID);
    }

    public static boolean deleteUser(String userID) {
        return Admin.deleteUser(userID);
    }

    public static ArrayList<User> getAllUsers() {
        return Admin.getAllUsers();
    }

    public static ArrayList<Vendor> getVendorsByTransactionID(String eventID){
		return Admin.getVendorsByTransactionID(eventID);
	}

	public static ArrayList<Guest> getGuestsByTransactionID(String eventID){
		return Admin.getGuestsByTransactionID(eventID);
	}
}
