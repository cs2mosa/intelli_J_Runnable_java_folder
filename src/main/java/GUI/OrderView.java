package GUI;

import Service_Interfaces.*;
import Class_model.*;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.StringConverter;

import java.util.*;

public class OrderView extends VBox {

    private MainApp mainApp;

    public OrderView(MainApp mainApp , String patientName) {

        this.mainApp = mainApp;
        this.setSpacing(20);
        this.setPadding(new Insets(20));

        // Title
        Label titleLabel = new Label("Place Order");
        titleLabel.setFont(new Font("Arial", 24));
        titleLabel.setTextFill(Color.DARKBLUE);
        titleLabel.setAlignment(Pos.CENTER);

        // Search Section
        Label searchLabel = new Label("Search Items");
        searchLabel.setFont(new Font("Arial", 16));
        searchLabel.setTextFill(Color.DARKRED);

        ListView<Item> itemsListView = new ListView<>();
        itemsListView.setPrefHeight(200);

        TextField searchField = new TextField();
        searchField.setPromptText("Enter item name");

        Button searchButton = new Button("Search");
        searchButton.setOnAction(event -> {
            String query = searchField.getText();
            if (!query.isEmpty()) {
                List<Item> searchResults = searchItems(query);
                if(searchResults.isEmpty()) showAlert("Error", "No items found.");
                updateItemsListView(itemsListView,searchResults);
            }
        });

        HBox searchBox = new HBox(10, searchField, searchButton);
        searchBox.setAlignment(Pos.CENTER);

        // Available Items Section
        Label itemsLabel = new Label("Available Items");
        itemsLabel.setFont(new Font("Arial", 16));
        itemsLabel.setTextFill(Color.DARKRED);

        // Fetch available items
        List<Item> availableItems = Inventory_service.getInstance().GetAllItems();
        if(availableItems == null || availableItems.isEmpty()) showAlert("Error", "No items found.");
        ObservableList<Item> itemsObservableList = FXCollections.observableArrayList(availableItems);
        itemsListView.setItems(itemsObservableList);

        // Order Details Section
        Label orderDetailsLabel = new Label("Order Details");
        orderDetailsLabel.setFont(new Font("Arial", 16));
        orderDetailsLabel.setTextFill(Color.DARKRED);

        // Map to store item quantities
        Map<String, Integer> orderItems = new HashMap<>();


        // Add Item to Order Button
        Button addItemButton = new Button("Add to Order");
        addItemButton.setOnAction(event -> {
            Item selected = itemsListView.getSelectionModel().getSelectedItem();
            if (selected != null) {
                String selectedItem = selected.getMedicName();
                TextInputDialog dialog = new TextInputDialog("1");
                dialog.setTitle("Add Item to Order");
                dialog.setHeaderText("Enter the quantity for " + selectedItem);
                dialog.setContentText("Quantity:");

                dialog.showAndWait().ifPresent(response -> {
                    try {
                        int quantity = Integer.parseInt(response);
                        if (quantity > 0) {
                            if(orderItems.containsKey(selectedItem))
                                quantity += orderItems.get(selectedItem);
                            orderItems.put(selectedItem, quantity);
                            showAlert("Success", "Item added to order.");
                            updateItemsListView(itemsListView,availableItems);
                        }else if(quantity <= selected.getQuantity()) {
                            showAlert("Error", "no sufficient quantity");
                        } else {
                            showAlert("Error", "Quantity must be greater than 0.");
                        }
                    } catch (NumberFormatException e) {
                        showAlert("Error", "Invalid quantity.");
                    }
                });
            } else {
                showAlert("Error", "Please select an item.");
            }
        });

        // Place Order Button
        Button placeOrderButton = new Button("Place Order");
        placeOrderButton.setOnAction(event -> {
            if (!orderItems.isEmpty()) {
                //temporary until changing
                Pharmacist pharmacist = new Pharmacist();
                pharmacist.setUsername("ahmed");
                pharmacist.setPassword("<PASSWORD>");
                pharmacist.setUserEmail("<EMAIL>");
                pharmacist.setPhoneNumber("01062222222");
                pharmacist.setID(new Random().nextInt(10000));
                User_Service.getInstance().AddUser(pharmacist);
                // Add the order to the repository
                int orderId = Patient_Service.getInstance().PlaceOrder(orderItems,Patient_Service.getInstance().GetPatient(patientName).getID(), pharmacist);
                if (orderId > 0) {
                    showAlert("Success", "Order placed successfully.");
                    mainApp.loadPage("PatientProfile" , patientName);
                } else {
                    showAlert("Error", "Failed to place order.");
                }
            } else {
                showAlert("Error", "Your order is empty.");
            }
        });

        // Organize components into the main VBox
        this.getChildren().addAll(titleLabel, searchLabel, searchBox, itemsLabel, itemsListView, orderDetailsLabel, addItemButton, placeOrderButton);
    }

    private List<Item> searchItems(String query) {
        Inventory_service inventoryService = Inventory_service.getInstance();
        Item itemByName = inventoryService.GetItemByName(query);
        if ((itemByName == null)) {
            showAlert("Error", "No items found.");
            return new ArrayList<>();
        }
        List<Item> searchResults = new ArrayList<>();
        searchResults.add(itemByName);
        return searchResults;
    }

    private void updateItemsListView(ListView<Item> itemsListView,List<Item> items) {
        ObservableList<Item> itemsObservableList = FXCollections.observableArrayList(items);
        //@SuppressWarnings("unchecked")//suppressed that warning because it doesn't matter for now.
        //ListView<Item> itemsListView = (ListView<Item>) this.lookup("#itemsListView");
        if (!itemsObservableList.isEmpty()) {
            itemsListView.setItems(itemsObservableList);
        } else {
            // Handle the case where the ListView was not found
            System.err.println("ListView with id 'itemsListView' not found.");
        }
    }

    private double calculateTotalPrice(Map<Item, Integer> orderItems) {
        double totalPrice = 0.0;
        for (Map.Entry<Item, Integer> entry : orderItems.entrySet()) {
            totalPrice += entry.getKey().getPrice() * entry.getValue();
        }
        return totalPrice;
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
/*private List<Item> fetchAvailableItems() {
        // Implement fetching available items using InventoryService
        return Inventory_service.getInstance().GetAllItems();            //we need to implement GetAllItems in inventory_service
    }*/