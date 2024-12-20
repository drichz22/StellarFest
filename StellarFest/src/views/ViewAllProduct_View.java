package views;

import java.util.ArrayList;

import controllers.VendorController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import main.Main;
import models.Product;

public class ViewAllProduct_View {
	private Scene allProductsScene;
    private BorderPane borderPane;
    private TableView<Product> productTable;
    private ObservableList<Product> productList;

    public ViewAllProduct_View() {
        this.borderPane = new BorderPane();
        this.allProductsScene = new Scene(borderPane, 800, 600);

        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(5);
        gridPane.setVgap(5);

        // Membuat columns
        productTable = new TableView<>();
        TableColumn<Product, String> productIdColumn = new TableColumn<>("Product ID");
        productIdColumn.setCellValueFactory(new PropertyValueFactory<>("product_id"));

        TableColumn<Product, String> vendorIdColumn = new TableColumn<>("Vendor ID");
        vendorIdColumn.setCellValueFactory(new PropertyValueFactory<>("vendor_id"));

        TableColumn<Product, String> nameColumn = new TableColumn<>("Product Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("product_name"));

        TableColumn<Product, String> descriptionColumn = new TableColumn<>("Product Description");
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("product_description"));

        // Menambahkan kolom ke tabel
        productTable.getColumns().addAll(productIdColumn, vendorIdColumn, nameColumn, descriptionColumn);
        productTable.setItems(productList);

        borderPane.setTop(gridPane);
        borderPane.setCenter(productTable);
        	
        // Isi tabel dengan data dari database
        ArrayList<Product> product = VendorController.viewAllProducts();
        productList = FXCollections.observableArrayList(product);
        productTable.setItems(productList);
        
        borderPane.setCenter(productTable);
        
     // Buat tombol untuk Manage Vendor
        Button manageVendorButton = new Button("Manage Vendor");
        manageVendorButton.setOnAction(event -> {
            Product selectedProduct = productTable.getSelectionModel().getSelectedItem(); // Ambil produk yang dipilih
            if (selectedProduct != null) {
                ManageVendorView manageVendorView = new ManageVendorView(selectedProduct.getProduct_id()); // Buat tampilan ManageVendor
                Main.redirect(manageVendorView.getManageVendorScene()); // Alihkan ke tampilan ManageVendor
            } else {
                showAlert("No Product Selected", "Please select a product to manage its vendor.");
            }
        });
        
        Button backButton = new Button("Back"); //Logika back btn untuk navigasi
        backButton.setOnAction(e -> {
        	HomePageView homePageView = new HomePageView();
            Main.redirect(homePageView.getHomePageScene());
        });
        
        gridPane.add(manageVendorButton, 0, 0);
        gridPane.add(backButton, 4, 0);
		borderPane.setCenter(productTable);
    }

    public Scene getAllProductScene() {
        return allProductsScene;
    }
    
    private void showAlert(String title, String message) {
        showAlert(title, message, AlertType.ERROR);
    }

    private void showAlert(String title, String message, AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
