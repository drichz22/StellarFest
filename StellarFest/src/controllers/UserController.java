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
		
		if (page.equals("Login Page")) { //jika page merupakan LoginPage, maka method Login() pada controller akan diinisialisasi
			showLoginPage();
		}
		else if (page.equals("Register Page")) { //jika page merupakan RegisterPage, maka method Register() pada controller akan diinisialisasi
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
				
				lbl = checkRegisterInput(email, username, password, role);
				
				if(!lbl.isEmpty()) { //kalau lbl berisi (validasi gagal), maka akan dikeluarkan label error dengan text berisi string lbl
					registerView.setValidationMessage(lbl);
					return;
				}
				
				User newUser = new User(userId, email, username, password, role);
				boolean isAdded = User.insertUser(newUser);
				if (isAdded) { //jika berhasil, maka akan ada alert berhasil & di redirect ke login page
					registerView.showSuccessAlert();
					LoginView loginView = new LoginView();
					Main.redirect(loginView.getLoginScene());
				}
				else {
					registerView.showErrorMessage(); //jika gagal, akan ada alert gagal dan tetap stay di page
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
				
				if (!lbl.isEmpty()) { //kalau lbl berisi (validasi gagal), maka akan dikeluarkan label error dengan text berisi string lbl
					loginView.setValidationMessage(lbl);
					return;
				}
						
				User validUser = User.getUserByEmailPassword(email, password);
				
				//admin dapat login dengan input email dan password 'admin'
				if(email.equals("admin") && password.equals("admin")) {
					validUser = User.getAdmin();
				}
				
				if(validUser != null) {	
					SessionManager.setLoggedInUser(validUser);
					loginView.showSuccessAlert();
					HomePageView homePageView = new HomePageView();
					Main.redirect(homePageView.getHomePageScene());
				}
				
				else {
					loginView.showErrorMessage();
					return;
				}
			}
		});
	}
	
	public User getUserByEmail(String email) {
		return User.getUserByEmail(email);
	}
	
	public User getUserByUsername(String username) {
		return User.getUserByUsername(username);
	}
	
	//untuk validasi input register
	public String checkRegisterInput(String email, String username, String password, String role) {
		String lbl = "";
		if (email.isEmpty()) {
			lbl = "Email cannot be empty!";
		}
		
		else if (getUserByEmail(email) != null) { //untuk mengecek apakah email yang diinput sudah ada pada database
			lbl = "Email must be unique!";
		}
		
		else if (username.isEmpty()) {
			lbl = "Username cannot be empty!";
		}
		
		else if (getUserByUsername(username) != null) { //untuk mengecek apakah username yang diinput sudah ada pada database
			lbl = "Username must be unique!";
		}
		
		else if (password.isEmpty()) {
			lbl = "Password cannot be empty!";
		}
		
		else if (password.length() < 5) {
			lbl = "Password must be more than or equal to 5 characters!";
		}
		
		else if (role == null || !(role.equals("Event Organizer") || !role.equals("Vendor") || !role.equals("Guest"))) { //untuk memastikan role tidak kosong dan valid
			lbl = "Pick a valid role!";
		}
		return lbl;
	}

}
