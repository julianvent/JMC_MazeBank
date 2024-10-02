package jjvu.jmc.mazebank.views;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;
import jjvu.jmc.mazebank.controllers.admin.ClientCellController;
import jjvu.jmc.mazebank.models.Client;

import java.io.IOException;

public class ClientCellFactory extends ListCell<Client> {
    @Override
    protected void updateItem(Client client, boolean empty) {
        super.updateItem(client, empty);

        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/admin/ClientCell.fxml"));
            ClientCellController controller = new ClientCellController(client);
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
