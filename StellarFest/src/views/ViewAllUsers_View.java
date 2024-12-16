package views;

import java.util.ArrayList;

import controllers.AdminController; 
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import main.Main;
import models.User;  

public class ViewAllUsers_View {
    private Scene allUsersScene;
    private BorderPane borderPane;
    private TableView<User> userTable;
    private ObservableList<User> userList;

    public ViewAllUsers_View() {
        this.borderPane = new BorderPane();
        this.allUsersScene = new Scene(borderPane, 800, 600);

        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(5);
        gridPane.setVgap(5);

        // Membuat columns untuk tabel pengguna
        userTable = new TableView<>();
        TableColumn<User, String> idColumn = new TableColumn<>("User ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("user_id"));
        
        TableColumn<User, String> emailColumn = new TableColumn<>("Email");
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("user_email"));
        
        TableColumn<User, String> nameColumn = new TableColumn<>("Username");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("user_name"));
        
        TableColumn<User, String> roleColumn = new TableColumn<>("Role");
        roleColumn.setCellValueFactory(new PropertyValueFactory<>("user_role"));

        // Menambahkan kolom ke tabel
        userTable.getColumns().addAll(idColumn, emailColumn, nameColumn, roleColumn);
        userTable.setItems(userList);

        borderPane.setTop(gridPane);
        borderPane.setCenter(userTable);

        // Isi tabel dengan data dari database
        ArrayList<User> users = AdminController.getAllUsers(); 
        userList = FXCollections.observableArrayList(users);
        userTable.setItems(userList);
        
       
        borderPane.setCenter(userTable);
        
        Button deleteButton = new Button("Delete User");
        deleteButton.setOnAction(e -> {
            User selectedUser = userTable.getSelectionModel().getSelectedItem();
            if (selectedUser != null) {
                // Konfirmasi sebelum menghapus
                boolean confirmed = showDeleteConfirmation(selectedUser);
                if (confirmed) {
                    boolean deleted = AdminController.deleteUser(selectedUser.getUser_id());
                    if (deleted) {
                        userList.remove(selectedUser); // Menghapus user dari tampilan
                        showAlert("Success", "User deleted successfully.");
                    } else {
                        showAlert("Error", "Failed to delete the user.");
                    }
                }
            } else {
                showAlert("Warning", "Please select a user to delete.");
            }
        });
        
        gridPane.add(deleteButton, 1, 0);
        
        // Back button
        Button backButton = new Button("Back");
        backButton.setOnAction(e -> {
            // Set the scene back to the previous scene
        	HomePageView homePageView = new HomePageView();
            Main.redirect(homePageView.getHomePageScene());
        });
        
        gridPane.add(backButton, 0, 0);
    }

    public Scene getAllUsersScene() {
        return allUsersScene;
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    private boolean showDeleteConfirmation(User user) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Delete Confirmation");
        alert.setHeaderText("Are you sure you want to delete this user?");
        alert.setContentText("User: " + user.getUser_name() + " (" + user.getUser_id() + ")");
        
        // Show the confirmation dialog and return true if user confirms
        return alert.showAndWait().filter(response -> response.getButtonData().isDefaultButton()).isPresent();
    }
}

