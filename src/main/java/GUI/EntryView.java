package GUI;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class EntryView extends VBox {

    private MainApp mainApp;

    public EntryView(MainApp mainApp) {
        this.mainApp = mainApp;
        this.setSpacing(20);
        this.setPadding(new Insets(20));
        this.setAlignment(Pos.CENTER);

        // Title
        Label titleLabel = new Label("Pharmacy Management System");
        titleLabel.setFont(new Font("Arial", 24));
        titleLabel.setTextFill(Color.DARKBLUE);
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