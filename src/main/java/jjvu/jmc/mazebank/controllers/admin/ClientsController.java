package jjvu.jmc.mazebank.controllers.admin;

import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import jjvu.jmc.mazebank.models.Client;
import jjvu.jmc.mazebank.models.Model;
import jjvu.jmc.mazebank.views.ClientCellFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class ClientsController implements Initializable {
    public ListView<Client> clientsListView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeData();
        clientsListView.setItems(Model.getInstance().getClients());
        clientsListView.setCellFactory(e -> new ClientCellFactory());
    }

    private void initializeData() {
        if (Model.getInstance().getClients().isEmpty()) {
            Model.getInstance().setClients();
        }
    }
}
