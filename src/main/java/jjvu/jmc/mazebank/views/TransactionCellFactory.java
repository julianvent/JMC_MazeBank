package jjvu.jmc.mazebank.views;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;
import jjvu.jmc.mazebank.controllers.client.TransactionCellController;
import jjvu.jmc.mazebank.models.Transaction;

import java.io.IOException;

public class TransactionCellFactory extends ListCell<Transaction> {
    @Override
    protected void updateItem(Transaction transaction, boolean empty) {
        super.updateItem(transaction, empty);

        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/client/TransactionCell.fxml"));
            TransactionCellController controller = new TransactionCellController(transaction);
            loader.setController(controller);
            setText(null);

            try {
                setGraphic(loader.load());
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
    }
}
