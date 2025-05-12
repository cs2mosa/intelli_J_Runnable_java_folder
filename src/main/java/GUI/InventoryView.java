package GUI;

import Service_Interfaces.*;
import Class_model.Item;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.HashSet;
import java.util.List;

public class InventoryView extends VBox {



    private MainApp mainApp;

    public InventoryView(MainApp mainApp , String PatientName) {
        this.mainApp = mainApp;
        this.setSpacing(10);
        this.setPadding(new Insets(10));
/*---------------------------------------------------------------------------------------*/
        /* example for adding items just for testing*/
        //adding some items
        Item item1 = new Item.builder()
                .setMedicName("Medic1")
                .setPrice(10.0)
                .setExpireDate("31/12/2025")
                .setQuantity(10)
                .setUsage("Take 1 tablet every 6 hours")
                .setSideEffects(new HashSet<>())
                .setHealingEffects(new HashSet<>())
                .setCategory("cats")
                .build();

        Item item2 = new Item.builder()
                .setMedicName("Medic2")
                .setPrice(10.0)
                .setExpireDate("31/12/2024")
                .setQuantity(10)
                .setUsage("Take 1 tablet every 12 hours")
                .setSideEffects(new HashSet<>())
                .setHealingEffects(new HashSet<>())
                .setCategory("cats")
                .build();

        Item item3 = new Item.builder()
                .setMedicName("Medic3")
                .setPrice(10.0)
                .setExpireDate("31/12/2025")
                .setQuantity(10)
                .setUsage("Take 1 tablet every 6 hours")
                .setSideEffects(new HashSet<>())
                .setHealingEffects(new HashSet<>())
                .setCategory("cats")
                .build();

        Inventory_service.getInstance().AddNewItem(item1);
        Inventory_service.getInstance().AddNewItem(item2);
        Inventory_service.getInstance().AddNewItem(item3);
        //end of adding items
/*---------------------------------------------------------------------------------------*/
        Label titleLabel = new Label("Inventory Management");
        titleLabel.setFont(new Font("Arial", 20));
        titleLabel.setTextFill(Color.DARKBLUE);

        // Add new item section
        GridPane addItemGrid = new GridPane();
        addItemGrid.setHgap(10);
        addItemGrid.setVgap(10);
        addItemGrid.setAlignment(Pos.CENTER);

        Label addItemLabel = new Label("Add New Item");
        addItemLabel.setFont(new Font("Arial", 16));
        addItemLabel.setTextFill(Color.DARKRED);

        Label itemNameLabel = new Label("Item Name:");
        TextField itemNameField = new TextField();
        itemNameField.setPromptText("Enter item name");
        itemNameField.setTooltip(new Tooltip("Enter the name of the item"));

        Label itemCategoryLabel = new Label("Category:");
        TextField itemCategoryField = new TextField();
        itemCategoryField.setPromptText("Enter category");
        itemCategoryField.setTooltip(new Tooltip("Enter the category of the item"));

        Label itemPriceLabel = new Label("Price:");
        TextField itemPriceField = new TextField();
        itemPriceField.setPromptText("Enter price");
        itemPriceField.setTooltip(new Tooltip("Enter the price of the item"));

        Label itemQuantityLabel = new Label("Quantity:");
        TextField itemQuantityField = new TextField();
        itemQuantityField.setPromptText("Enter quantity");
        itemQuantityField.setTooltip(new Tooltip("Enter the quantity of the item"));

        Button addItemButton = new Button("Add Item");
        addItemButton.setOnAction(event -> {
            try {
                String name = itemNameField.getText();
                String category = itemCategoryField.getText();
                double price = Double.parseDouble(itemPriceField.getText());
                int quantity = Integer.parseInt(itemQuantityField.getText());
                Item newItem = new Item.builder()
                                .setMedicName(name)
                                .setCategory(category)
                                .setPrice(price)
                                .setQuantity(quantity)
                                .build();
                if (Inventory_service.getInstance().AddNewItem(newItem) == 0) {
                    showAlert("Success", "Item added successfully!");
                } else {
                    showAlert("Error", "Failed to add item.");
                }
            } catch (NumberFormatException e) {
                showAlert("Error", "Invalid input. Please check your inputs.");
            }
        });

        addItemGrid.add(itemNameLabel, 0, 0);
        addItemGrid.add(itemNameField, 1, 0);
        addItemGrid.add(itemCategoryLabel, 0, 1);
        addItemGrid.add(itemCategoryField, 1, 1);
        addItemGrid.add(itemPriceLabel, 0, 2);
        addItemGrid.add(itemPriceField, 1, 2);
        addItemGrid.add(itemQuantityLabel, 0, 3);
        addItemGrid.add(itemQuantityField, 1, 3);
        addItemGrid.add(addItemButton, 1, 4);

        // Remove item section
        HBox removeItemBox = new HBox(10);
        removeItemBox.setAlignment(Pos.CENTER);

        Label removeItemLabel = new Label("Remove Item");
        removeItemLabel.setFont(new Font("Arial", 16));
        removeItemLabel.setTextFill(Color.DARKRED);

        Label removeItemNameLabel = new Label("Item Name:");
        TextField removeItemNameField = new TextField();
        removeItemNameField.setPromptText("Enter item name");
        removeItemNameField.setTooltip(new Tooltip("Enter the name of the item to remove"));

        Button removeItemButton = new Button("Remove Item");
        removeItemButton.setOnAction(event -> {
            String name = removeItemNameField.getText();
            if (Inventory_service.getInstance().RemoveItemByName(name) == 0) {
                showAlert("Success", "Item removed successfully!");
            } else {
                showAlert("Error", "Failed to remove item.");
            }
        });

        removeItemBox.getChildren().addAll(removeItemNameLabel, removeItemNameField, removeItemButton);

        // Update item price section
        HBox updatePriceBox = new HBox(10);
        updatePriceBox.setAlignment(Pos.CENTER);

        Label updatePriceLabel = new Label("Update Item Price");
        updatePriceLabel.setFont(new Font("Arial", 16));
        updatePriceLabel.setTextFill(Color.DARKRED);

        Label updateItemNameLabel = new Label("Item Name:");
        TextField updateItemNameField = new TextField();
        updateItemNameField.setPromptText("Enter item name");
        updateItemNameField.setTooltip(new Tooltip("Enter the name of the item to update"));

        Label updateItemPriceLabel = new Label("New Price:");
        TextField updateItemPriceField = new TextField();
        updateItemPriceField.setPromptText("Enter new price");
        updateItemPriceField.setTooltip(new Tooltip("Enter the new price of the item"));

        Button updatePriceButton = new Button("Update Price");
        updatePriceButton.setOnAction(event -> {
            try {
                String name = updateItemNameField.getText();
                float price = Float.parseFloat(updateItemPriceField.getText());
                if (Inventory_service.getInstance().UpdateItemPrice(name, price) == 0) {
                    showAlert("Success", "Item price updated successfully!");
                } else {
                    showAlert("Error", "Failed to update item price.");
                }
            } catch (NumberFormatException e) {
                showAlert("Error", "Invalid input. Please check your inputs.");
            }
        });

        updatePriceBox.getChildren().addAll(updateItemNameLabel, updateItemNameField, updateItemPriceLabel, updateItemPriceField, updatePriceButton);

        // View low stock items section
        VBox lowStockSection = new VBox(10);
        lowStockSection.setAlignment(Pos.CENTER);

        Label lowStockLabel = new Label("Low Stock Items");
        lowStockLabel.setFont(new Font("Arial", 16));
        lowStockLabel.setTextFill(Color.DARKRED);

        ListView<String> lowStockListView = new ListView<>();
        lowStockListView.setPrefHeight(100);

        Button viewLowStockButton = new Button("View Low Stock");
        viewLowStockButton.setOnAction(event -> {
            List<String> lowStockItems = Inventory_service.getInstance().getLowStockItems();
            if(lowStockItems == null || lowStockItems.isEmpty()) showAlert("Error", "No low stock items found.");
            ObservableList<String> lowStockObservableList = FXCollections.observableArrayList(lowStockItems);
            lowStockListView.setItems(lowStockObservableList);
        });

        lowStockSection.getChildren().addAll(lowStockLabel, lowStockListView, viewLowStockButton);

        // Back button
        Button backButton = new Button("Back");
        backButton.setOnAction(event -> mainApp.loadPage("PharmacistProfile" , PatientName));

        // Organize components into the main VBox
        this.getChildren().addAll(titleLabel, addItemLabel, addItemGrid, removeItemLabel, removeItemBox, updatePriceLabel, updatePriceBox, lowStockSection, backButton);
    }

    private void showAlert(String title, String message) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        });
    }
}