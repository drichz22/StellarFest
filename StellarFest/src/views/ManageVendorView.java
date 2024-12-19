package views;

import controllers.VendorController;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import main.Main;
import models.Product;
import models.Vendor;

public class ManageVendorView {
	private Scene manageVendorScene;

    public ManageVendorView(String productID) {
    	// Ambil detail event berdasarkan eventId
        Product product = Vendor.viewProductDetails(productID);

        if (product == null) {
            // Jika event tidak ditemukan, tampilkan pesan error
            showAlert("Error", "Product not found.");
            return;
        }
        
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(10);
        grid.setHgap(10);
        this.manageVendorScene = new Scene(grid, 800, 600);

        // Form Fields
        Label productNameLabel = new Label("Product Name:");
        TextField productNameField = new TextField();
        productNameField.setPromptText("Enter product name");

        Label productDescriptionLabel = new Label("Product Description:");
        TextField productDescriptionField = new TextField();
        productDescriptionField.setPromptText("Enter product description");

        Button submitButton = new Button("Save Product");
        Button backButton = new Button("Back");

        // Adding elements to the GridPane
        grid.add(productNameLabel, 0, 0);
        grid.add(productNameField, 1, 0);

        grid.add(productDescriptionLabel, 0, 1);
        grid.add(productDescriptionField, 1, 1);

        HBox buttonBox = new HBox(10);
        buttonBox.getChildren().addAll(submitButton, backButton);
        grid.add(buttonBox, 1, 2);

        // Button Actions
        submitButton.setOnAction(e -> {
            String productName = productNameField.getText();
            String productDescription = productDescriptionField.getText();

            // Validate input
            String validationMessage = VendorController.checkManageVendorInput(productDescription, productName);
            if (validationMessage != null) {
                showAlert("Validation Error", validationMessage);
                return;
            }


            // Save product details
            VendorController.manageVendor(productID, productName, productDescription);

            // Success alert
            showAlert("Success", "Product saved successfully!");
        });

        backButton.setOnAction(e -> {
            // Set the scene back to the previous scene
        	ViewAllProduct_View productView = new ViewAllProduct_View();
            Main.redirect(productView.getAllProductScene());
        });
    }

    public Scene getManageVendorScene() {
        return manageVendorScene;
    }

    // Helper method to display alerts
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
