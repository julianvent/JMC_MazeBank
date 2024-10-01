package jjvu.jmc.mazebank.controllers.client;

import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import jjvu.jmc.mazebank.models.Model;

import java.net.URL;
import java.util.ResourceBundle;

public class ClientController implements Initializable {
    public BorderPane clientParent;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Model.getInstance().getViewFactory().getClientSelectedMenuItem()
                .addListener((observableValue, oldValue, newValue) ->  {
            switch (newValue) {
                case "Transactions" -> clientParent.setCenter(Model.getInstance().getViewFactory().getTransactionsView());
                default -> clientParent.setCenter(Model.getInstance().getViewFactory().getDashboardView());
            }
        });
    }
}
