package jjvu.jmc.mazebank.controllers.admin;

import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import jjvu.jmc.mazebank.models.Client;
import jjvu.jmc.mazebank.models.Model;
import jjvu.jmc.mazebank.views.ClientCellFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class DepositController implements Initializable {
    public TextField payeeAddressSearchField;
    public Button searchButton;
    public ListView<Client> resultsListView;
    public TextField depositAmountField;
    public Button depositButton;

    private Client client;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        searchButton.setOnAction(actionEvent -> onClientSearch());
        depositButton.setOnAction(actionEvent -> onDeposit());
    }

    private void onClientSearch() {
        ObservableList<Client> searchResults = Model.getInstance().searchClient(payeeAddressSearchField.getText());
        resultsListView.setItems(searchResults);
        resultsListView.setCellFactory(e -> new ClientCellFactory());
        client = searchResults.get(0);
    }

    private void onDeposit() {
        double amount = Double.parseDouble(depositAmountField.getText());
        double newBalance = amount + client.savingsAccountProperty().get().balanceProperty().get();

        if (depositAmountField.getText() != null) {
            Model.getInstance().getDatabaseDriver().depositSavings(client.payeeAddressProperty().get(), newBalance);
        }
        emptyFields();
    }

    private void emptyFields() {
        payeeAddressSearchField.setText("");
        depositAmountField.setText("");
    }
}
