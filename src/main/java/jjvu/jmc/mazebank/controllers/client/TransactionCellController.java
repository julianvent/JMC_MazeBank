package jjvu.jmc.mazebank.controllers.client;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import jjvu.jmc.mazebank.models.Model;
import jjvu.jmc.mazebank.models.Transaction;

import java.net.URL;
import java.util.ResourceBundle;

public class TransactionCellController implements Initializable {
    public FontAwesomeIconView inIcon;
    public FontAwesomeIconView outIcon;
    public Label transactionDateLabel;
    public Label senderNameLabel;
    public Label receiverNameLabel;
    public Button messageButton;
    public Label amountLabel;

    private final Transaction transaction;

    public TransactionCellController(Transaction transaction) {
        this.transaction = transaction;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        senderNameLabel.textProperty().bind(transaction.senderProperty());
        receiverNameLabel.textProperty().bind(transaction.receiverProperty());
        amountLabel.textProperty().bind(transaction.amountProperty().asString());
        transactionDateLabel.textProperty().bind(transaction.dateProperty().asString());
        messageButton.setOnAction(actionEvent -> Model.getInstance().getViewFactory()
                .showMessageWindow(transaction.senderProperty().get(), transaction.messageProperty().get())
        );

        transactionIcons();
    }

    private void transactionIcons() {
        if (transaction.senderProperty().get().equals(Model.getInstance().getClient().payeeAddressProperty().get())) {
            inIcon.setFill(Color.rgb(240, 240, 240));
            outIcon.setFill(Color.RED);
        } else {
            inIcon.setFill(Color.GREEN);
            outIcon.setFill(Color.rgb(240, 240, 240));
        }
    }
}
