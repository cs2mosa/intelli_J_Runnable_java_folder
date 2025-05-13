package GUI;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;

public class EntryView extends VBox {

    private MainApp mainApp;

    public EntryView(MainApp mainApp) {
        this.mainApp = mainApp;
        this.setSpacing(20);
        this.setPadding(new Insets(20));
        this.setAlignment(Pos.CENTER);

        // Set background image
        try {
            Image backgroundImage = new Image(getClass().getResourceAsStream("/images/pharmacy_background.jpg"));
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
        Label titleLabel = new Label("Pharmacy Management System");
        titleLabel.setFont(new Font("Arial", 24));
        titleLabel.setTextFill(Color.WHITE);
        titleLabel.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 10, 0, 0, 0);");
        titleLabel.setAlignment(Pos.CENTER);

        // Patient Button
        Button patientButton = new Button("Patient Login");
        patientButton.setFont(new Font("Arial", 16));
        patientButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        patientButton.setOnAction(event -> mainApp.loadPage("PatientLogin" , null));

        // Pharmacist Button
        Button pharmacistButton = new Button("Pharmacist Login");
        pharmacistButton.setFont(new Font("Arial", 16));
        pharmacistButton.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white;");
        pharmacistButton.setOnAction(event -> mainApp.loadPage("PharmacistLogin" , null));

        this.getChildren().addAll(titleLabel, patientButton, pharmacistButton);
    }
}