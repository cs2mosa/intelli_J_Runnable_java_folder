package GUI;

import Service_Interfaces.User_Service;
import Class_model.Pharmacist;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class PharmacistProfileView extends VBox {

    private MainApp mainApp;
    private Pharmacist pharmacist;

    public PharmacistProfileView(MainApp mainApp) {
        this.mainApp = mainApp;
        this.setSpacing(20);
        this.setPadding(new Insets(20));
        this.setAlignment(Pos.CENTER);

        // Retrieve current pharmacist
        this.pharmacist = (Pharmacist) User_Service.getInstance().GetByUsername(getCurrentPharmacistUsername());

        // Title
        Label titleLabel = new Label("Pharmacist Profile");
        titleLabel.setFont(new Font("Arial", 24));
        titleLabel.setTextFill(Color.DARKBLUE);
        titleLabel.setAlignment(Pos.CENTER);

        // Pharmacist Details Section
        GridPane detailsGrid = new GridPane();
        detailsGrid.setHgap(10);
        detailsGrid.setVgap(10);
        detailsGrid.setAlignment(Pos.CENTER);

        Label nameLabel = new Label("Name:");
        nameLabel.setFont(new Font("Arial", 16));
        Label nameValue = new Label(pharmacist.getUsername());
        nameValue.setFont(new Font("Arial", 16));
        nameValue.setTextFill(Color.DARKRED);

        Label emailLabel = new Label("Email:");
        emailLabel.setFont(new Font("Arial", 16));
        Label emailValue = new Label(pharmacist.getUserEmail());
        emailValue.setFont(new Font("Arial", 16));
        emailValue.setTextFill(Color.DARKRED);

        Label phoneLabel = new Label("Phone:");
        phoneLabel.setFont(new Font("Arial", 16));
        Label phoneValue = new Label(pharmacist.getPhoneNumber());
        phoneValue.setFont(new Font("Arial", 16));
        phoneValue.setTextFill(Color.DARKRED);

        Label salaryLabel = new Label("Salary:");
        salaryLabel.setFont(new Font("Arial", 16));
        Label salaryValue = new Label("$" + String.valueOf(pharmacist.getSalary()));
        salaryValue.setFont(new Font("Arial", 16));
        salaryValue.setTextFill(Color.DARKRED);

        detailsGrid.add(nameLabel, 0, 0);
        detailsGrid.add(nameValue, 1, 0);
        detailsGrid.add(emailLabel, 0, 1);
        detailsGrid.add(emailValue, 1, 1);
        detailsGrid.add(phoneLabel, 0, 2);
        detailsGrid.add(phoneValue, 1, 2);
        detailsGrid.add(salaryLabel, 0, 3);
        detailsGrid.add(salaryValue, 1, 3);

        // Buttons Section
        HBox buttonsBox = new HBox(10);
        buttonsBox.setAlignment(Pos.CENTER);

        Button receivedOrdersButton = new Button("Received Orders");
        receivedOrdersButton.setFont(new Font("Arial", 16));
        receivedOrdersButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        receivedOrdersButton.setOnAction(event -> mainApp.loadPage("ReceivedOrders" , null));

        Button inventoryButton = new Button("Inventory");
        inventoryButton.setFont(new Font("Arial", 16));
        inventoryButton.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white;");
        inventoryButton.setOnAction(event -> mainApp.loadPage("Inventory" , null));

        Button editProfileButton = new Button("Edit Profile");
        editProfileButton.setFont(new Font("Arial", 16));
        editProfileButton.setStyle("-fx-background-color: #FFC107; -fx-text-fill: white;");
        editProfileButton.setOnAction(event -> showEditProfileDialog());

        Button logoutButton = new Button("Logout");
        logoutButton.setFont(new Font("Arial", 16));
        logoutButton.setStyle("-fx-background-color: #F44336; -fx-text-fill: white;");
        logoutButton.setOnAction(event -> mainApp.loadPage("Entry" , null));

        buttonsBox.getChildren().addAll(receivedOrdersButton, inventoryButton, editProfileButton, logoutButton);

        this.getChildren().addAll(titleLabel, detailsGrid, buttonsBox);
    }

    private String getCurrentPharmacistUsername() {
        // Implement logic to retrieve current pharmacist's username
        // This is a placeholder implementation
        return "pharmacist_username";
    }

    private void showEditProfileDialog() {
        // Create a dialog for editing profile information
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Edit Profile");
        dialog.setHeaderText("Update your profile information");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        TextField nameField = new TextField(pharmacist.getUsername());
        TextField emailField = new TextField(pharmacist.getUserEmail());
        TextField phoneField = new TextField(pharmacist.getPhoneNumber());

        grid.add(new Label("Name:"), 0, 0);
        grid.add(nameField, 1, 0);
        grid.add(new Label("Email:"), 0, 1);
        grid.add(emailField, 1, 1);
        grid.add(new Label("Phone:"), 0, 2);
        grid.add(phoneField, 1, 2);

        dialog.getDialogPane().setContent(grid);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.OK) {
                // Update pharmacist information
                pharmacist.setUsername(nameField.getText());
                pharmacist.setUserEmail(emailField.getText());
                pharmacist.setPhoneNumber(phoneField.getText());
                User_Service.getInstance().UpdateUser(pharmacist.getUsername(), "username", nameField.getText());
                User_Service.getInstance().UpdateUser(pharmacist.getUsername(), "email", emailField.getText());
                User_Service.getInstance().UpdateUser(pharmacist.getUsername(), "phone", phoneField.getText());
                showAlert("Success", "Profile updated successfully");
                mainApp.loadPage("PharmacistProfile" , pharmacist.getUsername());
            }
            return dialogButton;
        });

        dialog.showAndWait();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}