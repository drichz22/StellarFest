package controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import main.Main;
import models.SessionManager;
import models.User;
import views.ChangeProfileView;
import views.HomePageView;
import views.LoginView;
import views.RegisterView;

public class UserController {
	
	private LoginView loginView;
	private RegisterView registerView;
	private ChangeProfileView changeProfileView;
	
	public UserController(LoginView loginView, RegisterView registerView, ChangeProfileView changeProfileView, String page) {
		super();
		this.loginView = loginView;
		this.registerView = registerView;
		this.changeProfileView = changeProfileView;
		
		if (page.equals("Login Page")) { //jika page merupakan LoginPage, maka method Login() pada controller akan diinisialisasi
			showLoginPage();
		}
		else if (page.equals("Register Page")) { //jika page merupakan RegisterPage, maka method Register() pada controller akan diinisialisasi
			showRegisterPage();
		}
		else if (page.equals("Change Profile Page")) {
			showChangeProfilePage();
		}
	}
	
	public void showRegisterPage() {
		Register();
	}
	
	public void showLoginPage() {
		Login();
	}
	
	public void showChangeProfilePage() {
		ChangeProfile();
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
	
	public void ChangeProfile() {
		changeProfileView.setChangeProfileButtonAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				String lbl = "";
				String email = changeProfileView.getEmailInput();
				String username = changeProfileView.getUsernameInput();
				String oldPass = changeProfileView.getPasswordInput();
				String newPass = changeProfileView.getNewPassInput();
				String id = SessionManager.getLoggedInUser().getUser_id(); //dapatkan id dari current user
				String role = SessionManager.getLoggedInUser().getUser_role(); //dapatkan role dari current user
				
				lbl = checkChangeProfileInput(email, username, oldPass, newPass);
				
				if (!lbl.isEmpty()) { 
					changeProfileView.setValidationMessage(lbl);
					return;
				}
				
				//fungsionalitas kalau update 1 saja, tidak menimbulkan error di database
				if (email.isEmpty()) {
					email = SessionManager.getLoggedInUser().getUser_email();
				}
				if (username.isEmpty()) {
					username = SessionManager.getLoggedInUser().getUser_name();
				}
				if (newPass.isEmpty()) {
					newPass = SessionManager.getLoggedInUser().getUser_password();
				}
				
				User updatedUser = new User(id, email, username, newPass, role);
				boolean isUpdated = User.updateUser(updatedUser);
				if (isUpdated) {
					SessionManager.setLoggedInUser(updatedUser); //simpan updated data ke current user data
					changeProfileView.showSuccessAlert();
					HomePageView homePageView = new HomePageView();
					Main.redirect(homePageView.getHomePageScene());
				}
				else {
					changeProfileView.showErrorMessage();
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
	
	//Untuk validasi input change profile
	public String checkChangeProfileInput(String email, String name, String oldPassword, String newPassword) {
		String lbl = "";
		if (getUserByEmail(email) != null) {
			lbl = "Email must be unique!";
		}
		else if (email.equals(SessionManager.getLoggedInUser().getUser_email())) { //validasi bila email yang diinput sama seperti yang ada pada session
			lbl = "Email must be different from the current email!";
		}
		else if (getUserByUsername(name) != null) {
			lbl = "Username must be unique!";
		}
		else if (name.equals(SessionManager.getLoggedInUser().getUser_name())) { //validasi bila username yang diinput sama seperti yang ada pada session
			lbl = "Username must be different from the current username!";
		}
		else if (!oldPassword.isEmpty() && !oldPassword.equals(SessionManager.getLoggedInUser().getUser_password())) { //jika old password tidak kosong, baru dicek apakah sesuai dengan curr pass
			lbl = "Old Password must be the same as the current password!";
		}
		else if (!newPassword.isEmpty() && newPassword.length() < 5) { //jika new password tidak kosong, baru dicek lengthnya kurang dari 5 atau tidak
			lbl = "New Password must be more than or equal to 5 characters!";
		}
		return lbl;
	}

}
