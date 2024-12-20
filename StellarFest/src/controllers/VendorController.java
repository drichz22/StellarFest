package controllers;

import java.util.ArrayList;

import javafx.collections.ObservableMap;
import models.Event;
import models.Invitation;
import models.Product;
import models.Vendor;
import views.AcceptVendorInvitationView;

public class VendorController {
	
	private AcceptVendorInvitationView acceptVendorInvitationView;

	public VendorController(AcceptVendorInvitationView acceptVendorInvitationView) {
		super();
		this.acceptVendorInvitationView = acceptVendorInvitationView;
	}      
    
	public static ArrayList<ObservableMap<String, String>> getAllVendorInvitations() {
	    return Vendor.getAllVendorInvitations();
	}

	public void acceptInvitation(String eventId) { //Logika untuk update status invitation dari 'Pending' menjadi 'Accepted'
		if (!eventId.isEmpty() && Vendor.getInvitationByEventId(eventId) != null) { //Validasi item yang di-select
			Vendor.acceptVendorInvitation(eventId);
			acceptVendorInvitationView.showSuccessAlert();
		} else {
			acceptVendorInvitationView.showFailAlert();
			return;
		}
	}
	
	public static ArrayList<Invitation> getAllAcceptedEvents(String email){ //Untuk semua data invitation yang invitation_status = Accepted
		return Vendor.getAllAcceptedEvents(email);
	}
	
	public static ArrayList<Event> viewAcceptedEvents(String email){ //Untuk detail event dari invitation di atas
		return Vendor.getAllAcceptedEventDetails(email);
	}
	
	public static void manageVendor(String productID, String productName, String productDescription) {
		Vendor.manageVendor(productID, productName, productDescription);
	}
	
	public static String checkManageVendorInput(String productDescription, String productName) {
		return Vendor.checkManageVendorInput(productDescription, productName);
	}
	
	public static ArrayList<Product> viewAllProducts() {
        return Vendor.viewAllProducts();
    }
}
