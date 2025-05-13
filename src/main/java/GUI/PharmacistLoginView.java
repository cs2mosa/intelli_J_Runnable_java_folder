package GUI;

import Service_Interfaces.User_Service;
import Class_model.Pharmacist;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.Optional;

public class PharmacistLoginView extends VBox {

    private MainApp mainApp;

    public PharmacistLoginView(MainApp mainApp) {
        this.mainApp = mainApp;
        this.setSpacing(15);
        this.setPadding(new Insets(20));
        this.setAlignment(Pos.CENTER);

        // Title
        Label titleLabel = new Label("Pharmacist Login");
        titleLabel.setFont(new Font("Arial", 24));
        titleLabel.setTextFill(Color.DARKBLUE);

        // Login Form
        GridPane loginGrid = new GridPane();
        loginGrid.setHgap(10);
        loginGrid.setVgap(10);
        loginGrid.setAlignment(Pos.CENTER);

        Label usernameLabel = new Label("Username:");
        usernameLabel.setFont(new Font("Arial", 16));
        Label passwordLabel = new Label("Password:");
        passwordLabel.setFont(new Font("Arial", 16));

        TextField usernameField = new TextField();
        usernameField.setPromptText("Enter your username");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter your password");

        loginGrid.add(usernameLabel, 0, 0);
        loginGrid.add(usernameField, 1, 0);
        loginGrid.add(passwordLabel, 0, 1);
        loginGrid.add(passwordField, 1, 1);

        // Login Button
        Button loginButton = new Button("Login");
        loginButton.setFont(new Font("Arial", 16));
        loginButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        loginButton.setOnAction(event -> {
            String username = usernameField.getText();
            String password = passwordField.getText();

            Pharmacist pharmacist = (Pharmacist) User_Service.getInstance().GetByUsername(username);
            /*pharmacist != null && pharmacist.getPassword().equals(password) && pharmacist.getPassword() != null*/
            if (User_Service.getInstance().AuthenticateUser(pharmacist)) {
                showAlert("Success", "Login successful!");
                mainApp.loadPage("PharmacistProfile" , username);
            } else {
                showAlert("Error", "Invalid username or password");
            }
        });

        // Signup Button
        Button signupButton = new Button("Sign Up");
        signupButton.setFont(new Font("Arial", 16));
        signupButton.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white;");
        signupButton.setOnAction(event -> mainApp.loadPage("PharmacistSignup" , null));

        // Back Button
        Button backButton = new Button("Back");
        backButton.setFont(new Font("Arial", 16));
        backButton.setOnAction(event -> mainApp.loadPage("Entry" , null));

        this.getChildren().addAll(titleLabel, loginGrid, loginButton, signupButton, backButton);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}