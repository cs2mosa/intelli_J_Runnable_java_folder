package GUI;

import Service_Interfaces.*;
import Class_model.Patient;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

public class PatientSignupView extends VBox {

    private MainApp mainApp;

    public PatientSignupView(MainApp mainApp) {
        this.mainApp = mainApp;
        this.setSpacing(20);
        this.setPadding(new Insets(20));

        // Set background image
        try {
            // Load the pills image from your resources directory
            // Adjust the path as needed based on your project structure
            Image backgroundImage = new Image(getClass().getResourceAsStream("/images/pack.jpg"));

            // Create a BackgroundImage object
            BackgroundImage background = new BackgroundImage(
                    backgroundImage,
                    BackgroundRepeat.NO_REPEAT,  // Don't repeat the image
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundPosition.CENTER,   // Center the image
                    new BackgroundSize(
                            BackgroundSize.AUTO,  // Auto width
                            BackgroundSize.AUTO,  // Auto height
                            false,               // Don't preserve ratio
                            false,               // Don't preserve ratio
                            true,                // Cover the entire area
                            true                 // Cover the entire area
                    )
            );

            // Apply the background to the VBox
            this.setBackground(new Background(background));
        } catch (Exception e) {
            System.err.println("Failed to load background image: " + e.getMessage());
        }

        // Title
        Label titleLabel = new Label("Patient Sign Up");
        titleLabel.setFont(new Font("Arial", 24));
        titleLabel.setTextFill(Color.WHITE); // Changed to white for better visibility on the image
        titleLabel.setAlignment(Pos.CENTER);

        // Add a small pane behind text elements for better readability
        VBox contentBox = new VBox();
        contentBox.setSpacing(20);
        contentBox.setPadding(new Insets(15));
        contentBox.setStyle("-fx-background-color: rgba(255, 255, 255, 0.5);"); // Semi-transparent white background
        contentBox.setMaxWidth(400);
        contentBox.setAlignment(Pos.CENTER);

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
            try {
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
            }catch (Exception e){
                showAlert("Error", "Sign up failed. Please try again.");
            }
        });

        // Back Button
        Button backButton = new Button("Back");
        backButton.setOnAction(event -> mainApp.loadPage("Entry" , null));

        // Style buttons for better visibility on background
        String buttonStyle = "-fx-background-color: #336699; -fx-text-fill: white; -fx-font-weight: bold;";
        signupButton.setStyle(buttonStyle);
        backButton.setStyle(buttonStyle);

        // Add content to the semi-transparent box
        contentBox.getChildren().addAll(
                usernameLabel, usernameField,
                passwordLabel, passwordField,
                emailLabel, emailField,
                phoneLabel, phoneField,
                ageLabel, ageField,
                addressLabel, addressField,
                signupButton, backButton
        );

        // Organize components into the main VBox (just title and content box)
        this.setAlignment(Pos.CENTER);
        this.getChildren().addAll(titleLabel, contentBox);
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