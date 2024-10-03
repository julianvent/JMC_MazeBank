package jjvu.jmc.mazebank.controllers.client;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import jjvu.jmc.mazebank.models.Model;
import jjvu.jmc.mazebank.views.ClientMenuOptions;

import java.net.URL;
import java.util.ResourceBundle;

public class ClientMenuController implements Initializable {
    public Button dashboardButton;
    public Button transactionButton;
    public Button accountButton;
    public Button profileButton;
    public Button logoutButton;
    public Button reportButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addListeners();
    }

    private void addListeners() {
        dashboardButton.setOnAction(actionEvent -> onDashboard());
        transactionButton.setOnAction(actionEvent -> onTransactions());
        accountButton.setOnAction(actionEvent -> onAccounts());
        logoutButton.setOnAction(actionEvent -> onLogout());
    }

    private void onDashboard() {
        Model.getInstance().getViewFactory().getClientSelectedMenuItem().set(ClientMenuOptions.DASHBOARD);
    }

    private void onTransactions() {
        Model.getInstance().getViewFactory().getClientSelectedMenuItem().set(ClientMenuOptions.TRANSACTIONS);
    }

    private void onAccounts() {
        Model.getInstance().getViewFactory().getClientSelectedMenuItem().set(ClientMenuOptions.ACCOUNTS);
    }

    private void onLogout() {
        // Get stage
        Stage stage = (Stage)dashboardButton.getScene().getWindow();
        // Close the client window
        Model.getInstance().getViewFactory().closeStage(stage);
        // Show Login Window
        Model.getInstance().getViewFactory().showLoginWindow();
        // Set Client Login Success Flag to false
        Model.getInstance().setClientLoginSuccessFlag(false);
    }
}
