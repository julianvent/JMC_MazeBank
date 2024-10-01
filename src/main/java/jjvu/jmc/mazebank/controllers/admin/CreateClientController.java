package jjvu.jmc.mazebank.controllers.admin;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class CreateClientController implements Initializable {
    public TextField firstNameField;
    public TextField lastNameField;
    public TextField passwordField;
    public CheckBox payeeAddressBox;
    public Label payeeAddressLabel;
    public CheckBox addCheckingAccountBox;
    public TextField checkingAmountField;
    public CheckBox addSavingsAccountBox;
    public TextField savingsAmountField;
    public Button createClientButton;
    public Label errorLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
