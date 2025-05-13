package GUI;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class ReceivedOrdersView extends VBox {

    private MainApp mainApp;

    public ReceivedOrdersView(MainApp mainApp,String patientName) {
        this.mainApp = mainApp;

        Label ordersLabel = new Label("Received Orders");


        Button backButton = new Button("Back");
        backButton.setOnAction(event -> mainApp.loadPage("PharmacistProfile" ,patientName));

        getChildren().addAll(ordersLabel, backButton);


    }
}
