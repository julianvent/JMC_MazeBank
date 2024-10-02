package jjvu.jmc.mazebank.controllers.admin;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import jjvu.jmc.mazebank.models.Client;

import java.net.URL;
import java.util.ResourceBundle;

public class ClientCellController implements Initializable {
    public Label firstNameLabel;
    public Label lastNameLabel;
    public Label payeeAddressLabel;
    public Label checkingAccountLabel;
    public Label savingsAccountLabel;
    public Label dateCreatedLabel;
    public Button deleteButton;

    private final Client client;

    public ClientCellController(Client client) {
        this.client = client;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
