package controllers;

import views.LoginView;
import views.RegisterView;

public class UserController {
	
	private LoginView loginView;
	private RegisterView registerView;
	
	public UserController() {
		this.loginView = new LoginView();
		this.registerView = new RegisterView();
	}
	
	public void Register(String email, String name, String password, String role) {
		
	}

}
