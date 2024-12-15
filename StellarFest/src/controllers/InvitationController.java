package controllers;

import models.Invitation;

public class InvitationController {
	public static boolean sendInvitation(String email, String eventID, String role) {
		return Invitation.sendInvitation(email, eventID, role);
	}
}
