package GUI;

import Service_Interfaces.*;
import Class_model.Patient;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

public class PatientSignupView extends VBox {

    private MainApp mainApp;

    public PatientSignupView(MainApp mainApp) {
        this.mainApp = mainApp;
        this.setSpacing(20);
        this.setPadding(new Insets(20));

        // Title
        Label titleLabel = new Label("Patient Sign Up");
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

        // Email Section
        Label emailLabel = new Label("Email:");
        emailLabel.setFont(new Font("Arial", 16));
        emailLabel.setTextFill(Color.DARKRED);
        TextField emailField = new TextField();
        emailField.setPromptText("Enter your email");

        // Phone Section
        Label phoneLabel = new Label("Phone Number:");
        phoneLabel.setFont(new Font("Arial", 16));
        phoneLabel.setTextFill(Color.DARKRED);
        TextField phoneField = new TextField();
        phoneField.setPromptText("Enter your phone number");

        // Age Section
        Label ageLabel = new Label("Age:");
        ageLabel.setFont(new Font("Arial", 16));
        ageLabel.setTextFill(Color.DARKRED);
        TextField ageField = new TextField();
        ageField.setPromptText("Enter your age");

        // Address Section
        Label addressLabel = new Label("Address:");
        addressLabel.setFont(new Font("Arial", 16));
        addressLabel.setTextFill(Color.DARKRED);
        TextField addressField = new TextField();
        addressField.setPromptText("Enter your address");

        // Signup Button
        Button signupButton = new Button("Sign Up");
        signupButton.setOnAction(event -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            String email = emailField.getText();
            String phone = phoneField.getText();
            float age = Float.parseFloat(ageField.getText());
            String address = addressField.getText();
            /*signUpPatient(username, password, email, phone, age, address)*/
            if (signUpPatient(username, password, email, phone, age, address) > 0) {
                showAlert("Success", "Sign up successful. Please log in.");
                mainApp.loadPage("PatientProfile" , username);
            } else {
                showAlert("Error", "Sign up failed. Please try again.");
            }
        });

        // Back Button
        Button backButton = new Button("Back");
        backButton.setOnAction(event -> mainApp.loadPage("Entry" , null));

        // Organize components into the main VBox
        this.getChildren().addAll(titleLabel, usernameLabel, usernameField, passwordLabel, passwordField, emailLabel, emailField, phoneLabel, phoneField, ageLabel, ageField, addressLabel, addressField, signupButton, backButton);
    }

    private int signUpPatient(String username, String password, String email, String phone, float age, String address) {
        // Generate a random patientId
        Random random = new Random();
        int patientId = random.nextInt(10000);

        // Create a new Patient object using the constructor
        Patient patient = new Patient(patientId, username, password, email, phone, age, address, new HashSet<>(), new ArrayList<>(), new HashSet<>());

        // Add the patient using PatientService
        return Patient_Service.getInstance().AddPatient(patient);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(mainApp.getPrimaryStage());
        alert.showAndWait();
    }
}