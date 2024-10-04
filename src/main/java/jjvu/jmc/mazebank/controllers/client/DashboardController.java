package jjvu.jmc.mazebank.controllers.client;

import javafx.beans.binding.Bindings;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import jjvu.jmc.mazebank.models.Model;
import jjvu.jmc.mazebank.views.TransactionCellFactory;

import java.net.URL;
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
}
