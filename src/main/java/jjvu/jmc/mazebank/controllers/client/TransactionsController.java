package jjvu.jmc.mazebank.controllers.client;

import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import jjvu.jmc.mazebank.models.Model;
import jjvu.jmc.mazebank.models.Transaction;
import jjvu.jmc.mazebank.views.TransactionCellFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class TransactionsController implements Initializable {
    public ListView<Transaction> transactionsListView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeAllTransactionsList();
        transactionsListView.setItems(Model.getInstance().getAllTransactions());
        transactionsListView.setCellFactory(e -> new TransactionCellFactory());
    }

    private void initializeAllTransactionsList() {
        if (Model.getInstance().getAllTransactions().isEmpty()) {
            Model.getInstance().setAllTransactions();
        }
    }
}
