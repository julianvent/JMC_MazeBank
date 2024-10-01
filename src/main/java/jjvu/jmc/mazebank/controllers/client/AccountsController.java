package jjvu.jmc.mazebank.controllers.client;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class AccountsController implements Initializable {
    public Label checkingAccountNumber;
    public Label transactionLimit;
    public Label checkingAccountDate;
    public Label checkingAccountBalance;
    public Label savingsAccountNumber;
    public Label withdrawalLimit;
    public Label savingsAccountDate;
    public Label savingsAccountBalance;
    public TextField amountToSavings;
    public Button transferToSavingsButton;
    public TextField amountToChecking;
    public Button transferToCheckingButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
