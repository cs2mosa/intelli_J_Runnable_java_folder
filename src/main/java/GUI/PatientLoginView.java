package GUI;

import Service_Interfaces.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class PatientLoginView extends VBox {

    private MainApp mainApp;

    public PatientLoginView(MainApp mainApp) {
        this.mainApp = mainApp;
        this.setSpacing(20);
        this.setPadding(new Insets(20));

        // Set background image
        try {
            Image backgroundImage = new Image(getClass().getResourceAsStream("/images/patient_background.jpg"));
            BackgroundImage background = new BackgroundImage(
                    backgroundImage,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundPosition.CENTER,
                    new BackgroundSize(100, 100, true, true, true, true)
            );
            this.setBackground(new Background(background));
        } catch (Exception e) {
            System.err.println("Could not load background image: " + e.getMessage());
        }

        // Title with enhanced visibility against background
        Label titleLabel = new Label("Patient Login");
        titleLabel.setFont(new Font("Arial", 24));
        titleLabel.setTextFill(Color.WHITE);
        titleLabel.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 10, 0, 0, 0);");
        titleLabel.setAlignment(Pos.CENTER);

        // Create a container for the login form with semi-transparent background
        VBox loginFormContainer = new VBox(10);
        loginFormContainer.setPadding(new Insets(20));
        loginFormContainer.setStyle("-fx-background-color: rgba(255, 255, 255, 0.5); -fx-background-radius: 10;");
        loginFormContainer.setMaxWidth(400);
        loginFormContainer.setAlignment(Pos.CENTER);

        // Username Section
        Label usernameLabel = new Label("Username:");
        usernameLabel.setFont(new Font("Arial", 16));
        usernameLabel.setTextFill(Color.DARKBLUE);
        TextField usernameField = new TextField();
        usernameField.setPromptText("Enter your username");
        usernameField.setPrefWidth(300);

        // Password Section
        Label passwordLabel = new Label("Password:");
        passwordLabel.setFont(new Font("Arial", 16));
        passwordLabel.setTextFill(Color.DARKBLUE);
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter your password");
        passwordField.setPrefWidth(300);

        // Buttons container
        HBox buttonBox = new HBox(20);
        buttonBox.setAlignment(Pos.CENTER);

        // Login Button
        Button loginButton = new Button("Login");
        loginButton.setPrefWidth(120);
        loginButton.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white; -fx-font-size: 14px;");
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
        signupButton.setPrefWidth(120);
        signupButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 14px;");
        signupButton.setOnAction(event -> mainApp.loadPage("PatientSignup" , null));

        // Back button at the bottom
        Button backButton = new Button("Back");
        backButton.setStyle("-fx-background-color: #607D8B; -fx-text-fill: white;");
        backButton.setOnAction(event -> mainApp.loadPage("Entry", null));

        // Add buttons to button container
        buttonBox.getChildren().addAll(loginButton, signupButton);

        // Add all elements to the login form container
        loginFormContainer.getChildren().addAll(
                usernameLabel,
                usernameField,
                passwordLabel,
                passwordField,
                buttonBox
        );

        // Center the login form in the screen
        StackPane centerPane = new StackPane();
        centerPane.getChildren().add(loginFormContainer);

        // Organize components into the main VBox
        this.setAlignment(Pos.CENTER);
        this.getChildren().addAll(titleLabel, centerPane, backButton);
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