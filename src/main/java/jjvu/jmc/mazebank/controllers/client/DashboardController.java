package jjvu.jmc.mazebank.controllers.client;

import javafx.beans.binding.Bindings;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import jjvu.jmc.mazebank.models.Model;
import jjvu.jmc.mazebank.models.Transaction;
import jjvu.jmc.mazebank.views.TransactionCellFactory;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
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
        bindData();
        initializeLatestTransactions();
        transactionListView.setItems(Model.getInstance().getLatestTransactions());
        transactionListView.setCellFactory(e -> new TransactionCellFactory());
        sendButton.setOnAction(actionEvent -> onSendMoney());
        setAccountSummary();
    }

    private void bindData() {
        userName.textProperty().bind(Bindings.concat("Hi, ").concat(Model.getInstance().getClient().firstNameProperty()));
        loginDate.setText("Today: " + LocalDate.now());
        checkingBalance.textProperty().bind(Model.getInstance().getClient().checkingAccountProperty().get().balanceProperty().asString());
        checkingAccountNumber.textProperty().bind(Model.getInstance().getClient().checkingAccountProperty().get().accountNumberProperty());
        savingsBalance.textProperty().bind(Model.getInstance().getClient().savingsAccountProperty().get().balanceProperty().asString());
        savingsAccountNumber.textProperty().bind(Model.getInstance().getClient().savingsAccountProperty().get().accountNumberProperty());
    }

    private void initializeLatestTransactions() {
        if (Model.getInstance().getLatestTransactions().isEmpty()) {
            Model.getInstance().setLatestTransactions();
        }
    }

    private void onSendMoney() {
        String receiver = payeeField.getText();
        double amount = Double.parseDouble(amountField.getText());
        String message = messageField.getText();
        String sender = Model.getInstance().getClient().payeeAddressProperty().get();
        ResultSet resultSet = Model.getInstance().getDatabaseDriver().searchClient(receiver);

        try {
            if (resultSet.isBeforeFirst()) {
                Model.getInstance().getDatabaseDriver().updateBalance(receiver, amount, "ADD");
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        // Subtract from sender's savings account
        Model.getInstance().getDatabaseDriver().updateBalance(sender, amount, "SUBTRACT");

        // Update the savings account balance in the client object
        Model.getInstance().getClient().savingsAccountProperty().get().setBalance(
                Model.getInstance().getDatabaseDriver().getSavingsAccountBalance(sender)
        );

        // Record new transaction
        Model.getInstance().getDatabaseDriver().newTransaction(sender, receiver, amount, message);

        // Clear fields
        payeeField.setText("");
        amountField.setText("");
        messageField.setText("");
    }

    // Method calculates all expenses and income
    private void setAccountSummary() {
        double income = 0;
        double expenses = 0;

        if (Model.getInstance().getAllTransactions().isEmpty()) {
            Model.getInstance().setAllTransactions();
        }

        for (Transaction transaction: Model.getInstance().getAllTransactions()) {
            if (transaction.senderProperty().get().equals(Model.getInstance().getClient().payeeAddressProperty().get())) {
                expenses = expenses + transaction.amountProperty().get();
            } else {
                income = income + transaction.amountProperty().get();
            }
        }

        incomeLabel.setText("+ $"+ income);
        expenseLabel.setText("- $" + expenses);
    }
}
