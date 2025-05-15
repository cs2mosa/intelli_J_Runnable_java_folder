package GUI;

import Service_Interfaces.*;
import Class_model.*;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.List;

public class PaymentView extends VBox {

    private MainApp mainApp;
    private int patientId; // Patient ID should be fetched dynamically
    private String patientName; // Patient name should be fetched dynamically

    public PaymentView(MainApp mainApp, int patientId, String patientName) {
        this.mainApp = mainApp;
        this.patientId = patientId;
        this.patientName = patientName;
        this.setSpacing(20);
        this.setPadding(new Insets(20));

        // Set up background image
        try {
            // Load the background image - update the path to where your image is stored
            Image backgroundImage = new Image(getClass().getResourceAsStream("/images/payment_background.jpg"));

            // Create ImageView with the background image
            ImageView backgroundImageView = new ImageView(backgroundImage);

            // Make the background image resize with the window
            backgroundImageView.fitWidthProperty().bind(this.widthProperty());
            backgroundImageView.fitHeightProperty().bind(this.heightProperty());
            backgroundImageView.setPreserveRatio(false);

            // Set the background
            this.setBackground(new Background(new BackgroundImage(
                    backgroundImage,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundPosition.CENTER,
                    new BackgroundSize(1.0, 1.0, true, true, false, false)
            )));
        } catch (Exception e) {
            System.err.println("Error loading background image: " + e.getMessage());
        }

        // Create a main container to hold all elements
        VBox contentBox = new VBox();
        contentBox.setSpacing(20);
        contentBox.setPadding(new Insets(20));
        contentBox.setAlignment(Pos.TOP_CENTER);

        // Make the content box transparent to show the background
        contentBox.setBackground(new Background(new BackgroundFill(
                Color.rgb(255, 255, 255, 0.5), // Semi-transparent white background for better readability
                new CornerRadii(10),
                Insets.EMPTY
        )));

        // Title
        Label titleLabel = new Label("Payment");
        titleLabel.setFont(new Font("Arial", 24));
        titleLabel.setTextFill(Color.DARKBLUE);
        titleLabel.setAlignment(Pos.CENTER);

        // Order Details Section
        Label orderDetailsLabel = new Label("Order Details");
        orderDetailsLabel.setFont(new Font("Arial", 16));
        orderDetailsLabel.setTextFill(Color.DARKRED);

        // Fetch the current order
        Order currentOrder = fetchCurrentOrder(patientName);
        if (currentOrder == null) {
            showAlert("Error", "No pending orders found.");
            mainApp.loadPage("PatientProfile" , patientName);
            return;
        }

        Label orderPriceLabel = new Label("Order Price: $" + currentOrder.getTotalPrice());
        Label patientBalanceLabel = new Label("Patient Balance: $" + fetchPatientBalance(patientName));

        // Payment Section
        Label paymentDetailsLabel = new Label("Payment Details");
        paymentDetailsLabel.setFont(new Font("Arial", 16));
        paymentDetailsLabel.setTextFill(Color.DARKRED);

        TextField paymentAmountField = new TextField();
        paymentAmountField.setPromptText("Enter payment amount");

        ComboBox<String> paymentMethodComboBox = new ComboBox<>();
        paymentMethodComboBox.getItems().addAll("Credit Card", "Fawry", "Cash");
        paymentMethodComboBox.setPromptText("Select Payment Method");

        // Back Button
        Button backButton = new Button("Back");
        backButton.setOnAction(event -> mainApp.loadPage("PatientProfile" , patientName));


        Button payButton = new Button("Pay");
        payButton.setOnAction(event -> {
            try {
                double paymentAmount = Double.parseDouble(paymentAmountField.getText());
                String paymentMethod = paymentMethodComboBox.getValue();
                if (paymentMethod != null) {
                    // Process payment
                    if (paymentAmount < currentOrder.getTotalPrice()) throw new IllegalArgumentException("Payment amount is less than the order total.");
                    Patient_Service.getInstance().UpdatePatient(patientName, Patient_Service.getInstance().GetPatient(patientName).getPassword(),"PatientBalance",fetchPatientBalance(patientName) + paymentAmount);
                    Payment payment = new Payment();
                    payment.setAmount(currentOrder.getTotalPrice());
                    payment.setPayday(Date.getCurrentDateAsString()); // Replace with actual date
                    payment.setPaymethod(paymentMethod);
                    payment.setOrder(currentOrder);
                    payment.setStatus("Pending");
                    payment.setID(currentOrder.getOrderId());
                    Payment_service.getInstance().AddPayment(patientId, payment);

                    if(Payment_service.getInstance().ProcessPayment(patientId, payment.getID()) == payment.getID()){
                        showAlert("Success", "Payment successful. Order marked as paid.");
                        mainApp.loadPage("PatientProfile" , patientName);
                    }else{
                        showAlert("Error", "Failed to process payment.Check your Balance");
                    }
                } else {
                    showAlert("Error", "Payment amount is less than the order total or payment method not selected.");
                }
            } catch (NumberFormatException e) {
                showAlert("Error", "Invalid payment amount.");
            }catch (Exception e){
                showAlert("Error", "Failed to process payment");
            }
        });

        // Improve button styling for better visibility on background
        backButton.setStyle("-fx-background-color: #f0f0f0; -fx-text-fill: #333333; -fx-font-weight: bold;");
        payButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold;");

        // Add all controls to the content box
        contentBox.getChildren().addAll(titleLabel, orderDetailsLabel, orderPriceLabel, patientBalanceLabel,
                paymentDetailsLabel, paymentAmountField, paymentMethodComboBox,
                payButton, backButton);

        // Add the content box to the main VBox
        this.getChildren().add(contentBox);
    }

    private Order fetchCurrentOrder(String patientName) {
        // Implement fetching the current order using OrderService
        List<Order> orders = Order_Service.getInstance().GetByCustomer(patientName);
        if(orders == null || orders.isEmpty()) return null;
        //need sorting orders first
        for(Order order : orders){
            if(order.getStatus().equals("Pending")){
                return order;
            }
        }
        return null;
    }

    private double fetchPatientBalance(String patientName) {
        // Implement fetching patient balance using PatientService
        return Patient_Service.getInstance().GetPatientBalance(patientName);
    }

    private void showAlert(String title, String message) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.showAndWait();
        });
    }
}