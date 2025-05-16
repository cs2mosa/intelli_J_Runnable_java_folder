package GUI;

import Service_Interfaces.*;
import Class_model.*;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
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

        // Set background image
        try {
            Image backgroundImage = new Image(getClass().getResourceAsStream("/images/pack.jpg"));
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
        Label titleLabel = new Label("Pharmacist Sign Up");
        titleLabel.setFont(new Font("Arial", 24));
        titleLabel.setTextFill(Color.WHITE);
        titleLabel.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 10, 0, 0, 0);");

        // Create a container for the signup form with semi-transparent background
        VBox formContainer = new VBox(15);
        formContainer.setPadding(new Insets(20));
        formContainer.setStyle("-fx-background-color: rgba(255, 255, 255, 0.5); -fx-background-radius: 10;");
        formContainer.setMaxWidth(500);
        formContainer.setAlignment(Pos.CENTER);

        // Signup Form
        GridPane signupGrid = new GridPane();
        signupGrid.setHgap(10);
        signupGrid.setVgap(10);
        signupGrid.setAlignment(Pos.CENTER);

        Label usernameLabel = new Label("Username:");
        usernameLabel.setFont(new Font("Arial", 16));
        usernameLabel.setTextFill(Color.DARKBLUE);

        Label passwordLabel = new Label("Password:");
        passwordLabel.setFont(new Font("Arial", 16));
        passwordLabel.setTextFill(Color.DARKBLUE);

        Label emailLabel = new Label("Email:");
        emailLabel.setFont(new Font("Arial", 16));
        emailLabel.setTextFill(Color.DARKBLUE);

        Label phoneLabel = new Label("Phone:");
        phoneLabel.setFont(new Font("Arial", 16));
        phoneLabel.setTextFill(Color.DARKBLUE);

        Label codeLabel = new Label("Validation Code:");
        codeLabel.setFont(new Font("Arial", 16));
        codeLabel.setTextFill(Color.DARKBLUE);

        TextField usernameField = new TextField();
        usernameField.setPromptText("Enter your username");
        usernameField.setPrefWidth(250);

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter your password");
        passwordField.setPrefWidth(250);

        TextField emailField = new TextField();
        emailField.setPromptText("Enter your email");
        emailField.setPrefWidth(250);

        TextField phoneField = new TextField();
        phoneField.setPromptText("Enter your phone number");
        phoneField.setPrefWidth(250);

        TextField codeField = new TextField();
        codeField.setPromptText("Enter validation code");
        codeField.setPrefWidth(250);

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

        // Button container
        HBox buttonBox = new HBox(20);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPadding(new Insets(10, 0, 0, 0));

        // Signup Button
        Button signupButton = new Button("Sign Up");
        signupButton.setFont(new Font("Arial", 16));
        signupButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        signupButton.setPrefWidth(150);
        signupButton.setOnAction(event -> {
            try{
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
            } catch (Exception e) {
                showAlert("Error", "invalid input type. Please try again.");
            }
        });

        // Back Button
        Button backButton = new Button("Back");
        backButton.setFont(new Font("Arial", 16));
        backButton.setStyle("-fx-background-color: #607D8B; -fx-text-fill: white;");
        backButton.setPrefWidth(150);
        backButton.setOnAction(event -> mainApp.loadPage("Entry" , null));

        // Add buttons to container
        buttonBox.getChildren().addAll(signupButton, backButton);

        // Add elements to form container
        formContainer.getChildren().addAll(signupGrid, buttonBox);

        // Center the form in the screen
        StackPane centerPane = new StackPane();
        centerPane.getChildren().add(formContainer);

        this.getChildren().addAll(titleLabel, centerPane);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}