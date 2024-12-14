package controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import main.Main;
import models.SessionManager;
import models.User;
import views.HomePageView;
import views.LoginView;
import views.RegisterView;

public class UserController {
	
	private LoginView loginView;
	private RegisterView registerView;
	
	public UserController(LoginView loginView, RegisterView registerView, String page) {
		super();
		this.loginView = loginView;
		this.registerView = registerView;
		
		if (page.equals("Login Page")) {
			showLoginPage();
		}
		else if (page.equals("Register Page")) {
			showRegisterPage();
		}
	}
	
	public void showRegisterPage() {
		Register();
	}
	
	public void showLoginPage() {
		Login();
	}

	public void Register() {
		registerView.setRegisterBtnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				String lbl = "";
				String userId = "User " + (User.getAllUsers().size() + 1);
				String email = registerView.getEmailInput();
				String username = registerView.getUsernameInput();
				String password = registerView.getPasswordInput();
				String role = registerView.getUserRole();
				
				if (email.isEmpty()) {
					lbl = "Email cannot be empty!";
				}
				
				else if (User.getUserByEmail(email) != null) {
					lbl = "Email must be unique!";
				}
				
				else if (username.isEmpty()) {
					lbl = "Username cannot be empty!";
				}
				
				else if (User.getUserByUsername(username) != null) {
					lbl = "Username must be unique!";
				}
				
				else if (password.isEmpty()) {
					lbl = "Password cannot be empty!";
				}
				
				else if (password.length() < 5) {
					lbl = "Password must be more than or equal to 5 characters!";
				}
				
				else if (role == null || !(role.equals("Event Organizer") || !role.equals("Vendor") || !role.equals("Guest") || !role.equals("Admin"))) {
					lbl = "Pick a valid role!";
				}
				
				if(!lbl.isEmpty()) {
					registerView.setValidationMessage(lbl);
					return;
				}
				
				User newUser = new User(userId, email, username, password, role);
				boolean isAdded = User.insertUser(newUser);
				if (isAdded) {
					registerView.showSuccessMessage();
					LoginView loginView = new LoginView();
					Main.redirect(loginView.getLoginScene());
				}
				else {
					registerView.showErrorMessage();
					return;
				}
			}	
		});
	}
	
	public void Login() {
		loginView.setLoginBtnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				String lbl = "";
				String email = loginView.getEmailInput();
				String password = loginView.getPasswordInput();
				
				if(email.isEmpty()) {
					lbl = "Email cannot be empty!";
				}
				
				else if (password.isEmpty()) {
					lbl = "Password cannot be empty!";
				}
				
				if (!lbl.isEmpty()) {
					loginView.setValidationMessage(lbl);
					return;
				}
				
				User validUser = User.getUserByEmailPassword(email, password);
				
				if(validUser != null) {
					if ("Admin".equals(validUser.getUser_role()) && email.equals("admin") && password.equals("admin")) {
						SessionManager.setLoggedInUser(validUser);
						loginView.showSuccessMessage();
						HomePageView homePageView = new HomePageView();
						Main.redirect(homePageView.getHomePageScene());
					}
					else {
						SessionManager.setLoggedInUser(validUser);
						loginView.showSuccessMessage();
						HomePageView homePageView = new HomePageView();
						Main.redirect(homePageView.getHomePageScene());
					}
				}
				
				else {
					loginView.showErrorMessage();
					return;
				}
			}
		});
	}

}
