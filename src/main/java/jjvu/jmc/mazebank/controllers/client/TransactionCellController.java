package jjvu.jmc.mazebank.controllers.client;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class TransactionCellController implements Initializable {
    public FontAwesomeIconView inIcon;
    public FontAwesomeIconView outIcon;
    public Label transactionDateLabel;
    public Label senderNameLabel;
    public Label receiverNameLabel;
    public Label amountLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
