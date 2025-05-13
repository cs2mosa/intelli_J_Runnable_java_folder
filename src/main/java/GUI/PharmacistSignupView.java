package GUI;

import Service_Interfaces.*;
import Class_model.*;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Pair;

import java.util.HashSet;
import java.util.Optional;
import java.util.Random;

public class PharmacistSignupView extends VBox {

    private MainApp mainApp;

    public PharmacistSignupView(MainApp mainApp) {
        this.mainApp = mainApp;
        this.setSpacing(15);
        this.setPadding(new Insets(20));
        this.setAlignment(Pos.CENTER);

        // Title
        Label titleLabel = new Label("Pharmacist Sign Up");
        titleLabel.setFont(new Font("Arial", 24));
        titleLabel.setTextFill(Color.DARKBLUE);

        // Signup Form
        GridPane signupGrid = new GridPane();
        signupGrid.setHgap(10);
        signupGrid.setVgap(10);
        signupGrid.setAlignment(Pos.CENTER);

        Label usernameLabel = new Label("Username:");
        usernameLabel.setFont(new Font("Arial", 16));
        Label passwordLabel = new Label("Password:");
        passwordLabel.setFont(new Font("Arial", 16));
        Label emailLabel = new Label("Email:");
        emailLabel.setFont(new Font("Arial", 16));
        Label phoneLabel = new Label("Phone:");
        phoneLabel.setFont(new Font("Arial", 16));
        Label codeLabel = new Label("Validation Code:");
        codeLabel.setFont(new Font("Arial", 16));

        TextField usernameField = new TextField();
        usernameField.setPromptText("Enter your username");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter your password");
        TextField emailField = new TextField();
        emailField.setPromptText("Enter your email");
        TextField phoneField = new TextField();
        phoneField.setPromptText("Enter your phone number");
        TextField codeField = new TextField();
        codeField.setPromptText("Enter validation code");

        signupGrid.add(usernameLabel, 0, 0);
        signupGrid.add(usernameField, 1, 0);
        signupGrid.add(passwordLabel, 0, 1);
        signupGrid.add(passwordField, 1, 1);
        signupGrid.add(emailLabel, 0, 2);
        signupGrid.add(emailField, 1, 2);
        signupGrid.add(phoneLabel, 0, 3);
        signupGrid.add(phoneField, 1, 3);
        signupGrid.add(codeLabel, 0, 4);
        signupGrid.add(codeField, 1, 4);

        // Signup Button
        Button signupButton = new Button("Sign Up");
        signupButton.setFont(new Font("Arial", 16));
        signupButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        signupButton.setOnAction(event -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            String email = emailField.getText();
            String phone = phoneField.getText();
            String code = codeField.getText();
            //validation code
            if (code.equals("PHARMACY123")) {
                Pharmacist pharmacist = new Pharmacist();
                pharmacist.setUsername(username);
                pharmacist.setPassword(password);
                pharmacist.setUserEmail(email);
                pharmacist.setPhoneNumber(phone);
                pharmacist.setRoles(new HashSet<>());

                Random random = new Random();
                pharmacist.setID(random.nextInt(10000));

                // Additional Pharmacist-specific fields can be set here

                if (User_Service.getInstance().AddUser(pharmacist) > 0) {
                    showAlert("Success", "Sign up successful.");
                    mainApp.loadPage("PharmacistProfile" ,username);
                } else {
                    showAlert("Error", "Sign up failed. Please try again.");
                }
            } else {
                showAlert("Error", "Invalid validation code.");
            }
        });

        // Back Button
        Button backButton = new Button("Back");
        backButton.setFont(new Font("Arial", 16));
        backButton.setOnAction(event -> mainApp.loadPage("Entry" , null));

        this.getChildren().addAll(titleLabel, signupGrid, signupButton, backButton);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}