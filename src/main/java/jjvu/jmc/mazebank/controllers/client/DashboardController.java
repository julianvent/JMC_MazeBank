package jjvu.jmc.mazebank.controllers.client;

import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {

    public Text userName;
    public Label loginDate;
    public Label checkingBalance;
    public Label checkingAccountNumber;
    public Label savingsBalance;
    public Label savingsAccountNumber;
    public Text incomeLabel;
    public Label expenseLabel;
    public ListView transactionListView;
    public TextField payeeField;
    public TextField amountField;
    public TextArea messageField;
    public Button sendButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
