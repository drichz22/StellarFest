package controllers;

import java.util.ArrayList;

import javafx.collections.ObservableMap;
import models.Event;
import models.Guest;
import models.Invitation;
import views.AcceptGuestInvitationView;

public class GuestController {

	private AcceptGuestInvitationView acceptGuestInvitationView;

	public GuestController(AcceptGuestInvitationView acceptGuestInvitationView) {
		super();
		this.acceptGuestInvitationView = acceptGuestInvitationView;
	}

	public static ArrayList<ObservableMap<String, String>> getAllGuestInvitations() {
	    return Guest.getAllGuestInvitations();
	}

	public void acceptInvitation(String eventId) { //Logika untuk update status invitation dari 'Pending' menjadi 'Accepted'
		if (!eventId.isEmpty() && Guest.getInvitationByEventId(eventId) != null) { //Validasi item yang di-select
			Guest.acceptGuestInvitation(eventId);
			acceptGuestInvitationView.showSuccessAlert();
		} else {
			acceptGuestInvitationView.showFailAlert();
			return;
		}
	}
	
	public static ArrayList<Invitation> getAllAcceptedEvents(String email){ //Untuk semua data invitation yang invitation_status = Accepted
		return Guest.getAllAcceptedEvents(email);
	}
	
	public static ArrayList<Event> viewAcceptedEvents(String email){ //Untuk detail event dari invitation di atas
		return Guest.getAllAcceptedEventDetails(email);
	}

}
