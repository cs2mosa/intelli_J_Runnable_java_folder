package GUI;

import Service_Interfaces.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class PatientLoginView extends VBox {

    private MainApp mainApp;

    public PatientLoginView(MainApp mainApp) {
        this.mainApp = mainApp;
        this.setSpacing(20);
        this.setPadding(new Insets(20));

        // Title
        Label titleLabel = new Label("Patient Login");
        titleLabel.setFont(new Font("Arial", 24));
        titleLabel.setTextFill(Color.DARKBLUE);
        titleLabel.setAlignment(Pos.CENTER);

        // Username Section
        Label usernameLabel = new Label("Username:");
        usernameLabel.setFont(new Font("Arial", 16));
        usernameLabel.setTextFill(Color.DARKRED);
        TextField usernameField = new TextField();
        usernameField.setPromptText("Enter your username");

        // Password Section
        Label passwordLabel = new Label("Password:");
        passwordLabel.setFont(new Font("Arial", 16));
        passwordLabel.setTextFill(Color.DARKRED);
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter your password");

        // Login Button
        Button loginButton = new Button("Login");
        loginButton.setOnAction(event -> {
            String username = usernameField.getText();
            String password = passwordField.getText();

            if (Patient_Service.getInstance().AuthenticatePatient(username, password)) {
                mainApp.loadPage("PatientProfile" , username);
            } else {
                showAlert("Error", "Invalid username or password.");
            }
        });

        // Signup Button
        Button signupButton = new Button("Sign Up");
        signupButton.setOnAction(event -> mainApp.loadPage("PatientSignup" , null));

        // Organize components into the main VBox
        this.getChildren().addAll(titleLabel, usernameLabel, usernameField, passwordLabel, passwordField, loginButton, signupButton);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(mainApp.getPrimaryStage());
        alert.showAndWait();
    }
}