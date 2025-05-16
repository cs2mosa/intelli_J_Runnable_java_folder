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
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;

import java.util.Optional;

public class PharmacistLoginView extends VBox {

    private MainApp mainApp;

    public PharmacistLoginView(MainApp mainApp) {
        this.mainApp = mainApp;
        this.setSpacing(15);
        this.setPadding(new Insets(20));
        this.setAlignment(Pos.CENTER);

        // Set background image
        try {
            // Load the pills image from your resources directory
            Image backgroundImage = new Image(getClass().getResourceAsStream("/images/patient_background.jpg"));

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

        // Title with better visibility against background
        Label titleLabel = new Label("Pharmacist Login");
        titleLabel.setFont(new Font("Arial", 24));
        titleLabel.setTextFill(Color.WHITE);
        titleLabel.setStyle("-fx-background-color: rgba(0, 0, 128, 0.7); -fx-padding: 10px 20px; -fx-background-radius: 5px;");

        // Create semi-transparent container for login form
        VBox formContainer = new VBox();
        formContainer.setSpacing(15);
        formContainer.setPadding(new Insets(20));
        formContainer.setStyle("-fx-background-color: rgba(255, 255, 255, 0.5); -fx-background-radius: 10px;");
        formContainer.setMaxWidth(350);
        formContainer.setAlignment(Pos.CENTER);

        // Login Form
        GridPane loginGrid = new GridPane();
        loginGrid.setHgap(10);
        loginGrid.setVgap(10);
        loginGrid.setAlignment(Pos.CENTER);

        Label usernameLabel = new Label("Username:");
        usernameLabel.setFont(new Font("Arial", 16));
        usernameLabel.setTextFill(Color.DARKBLUE);

        Label passwordLabel = new Label("Password:");
        passwordLabel.setFont(new Font("Arial", 16));
        passwordLabel.setTextFill(Color.DARKBLUE);

        TextField usernameField = new TextField();
        usernameField.setPromptText("Enter your username");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter your password");

        loginGrid.add(usernameLabel, 0, 0);
        loginGrid.add(usernameField, 1, 0);
        loginGrid.add(passwordLabel, 0, 1);
        loginGrid.add(passwordField, 1, 1);

        // Login Button - preserving original functionality
        Button loginButton = new Button("Login");
        loginButton.setFont(new Font("Arial", 16));
        loginButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        loginButton.setOnAction(event -> {
            try{
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
            } catch (Exception e) {
                showAlert("Error", "Invalid username or password");
            }
        });

        // Signup Button - preserving original functionality
        Button signupButton = new Button("Sign Up");
        signupButton.setFont(new Font("Arial", 16));
        signupButton.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white;");
        signupButton.setOnAction(event -> mainApp.loadPage("PharmacistSignup" , null));

        // Back Button - preserving original functionality
        Button backButton = new Button("Back");
        backButton.setFont(new Font("Arial", 16));
        backButton.setStyle("-fx-background-color: #f44336; -fx-text-fill: white;");
        backButton.setOnAction(event -> mainApp.loadPage("Entry" , null));

        // Add buttons to form container
        formContainer.getChildren().addAll(loginGrid, loginButton, signupButton, backButton);

        // Add spacing to push the form down slightly from the title
        this.setSpacing(30);

        // Add only the title and form container to the main VBox
        this.getChildren().addAll(titleLabel, formContainer);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}