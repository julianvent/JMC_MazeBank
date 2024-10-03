package jjvu.jmc.mazebank.controllers.admin;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import jjvu.jmc.mazebank.models.Model;

import java.net.URL;
import java.util.Random;
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

    private String payeeAddress;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    private void createCheckingAccount() {
        double balance = Double.parseDouble(checkingAmountField.getText());
        // Generate Account Number
        String firstSection = "3201";
        String lastSection = Integer.toString(new Random().nextInt(9999) + 1000);
        String accountNumber = firstSection + " " + lastSection;

        // Create the checking account
        Model.getInstance().getDatabaseDriver().createCheckingAccount(payeeAddress, accountNumber, 10, balance);
    }
}
