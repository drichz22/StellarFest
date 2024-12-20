package controllers;

import models.Invitation;

public class InvitationController {
	public static boolean sendInvitation(String email, String eventID, String role) {
		return Invitation.sendInvitation(email, eventID, role);
	}
	
	//Untuk accept invitation akan ada di GuestController dan VendorController karena query condition berbeda dan parameter pada diagram tidak mencukupi
}
