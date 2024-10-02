package jjvu.jmc.mazebank.controllers.admin;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import jjvu.jmc.mazebank.models.Model;
import jjvu.jmc.mazebank.views.AdminMenuOptions;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminMenuController implements Initializable {
    public Button createClientButton;
    public Button listClientsButton;
    public Button depositButton;
    public Button logoutButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addListeners();
    }

    private void addListeners() {
        createClientButton.setOnAction(actionEvent -> onCreateClient());
        listClientsButton.setOnAction(actionEvent -> onListClients());
        depositButton.setOnAction(actionEvent -> onDeposit());
    }

    private void onCreateClient() {
        Model.getInstance().getViewFactory().getAdminSelectedMenuItemProperty().set(AdminMenuOptions.CREATE_CLIENT);
    }

    private void onListClients() {
        Model.getInstance().getViewFactory().getAdminSelectedMenuItemProperty().set(AdminMenuOptions.LIST_CLIENTS);
    }

    private void onDeposit() {
        Model.getInstance().getViewFactory().getAdminSelectedMenuItemProperty().set(AdminMenuOptions.DEPOSIT);
    }
}
