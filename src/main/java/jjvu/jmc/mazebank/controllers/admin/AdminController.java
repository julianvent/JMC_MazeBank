package jjvu.jmc.mazebank.controllers.admin;

import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import jjvu.jmc.mazebank.models.Model;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminController implements Initializable {
    public BorderPane adminParent;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Model.getInstance().getViewFactory().getAdminSelectedMenuItemProperty()
                .addListener((observableValue, oldValue, newValue) -> {
                    switch (newValue) {
                        case LIST_CLIENTS -> adminParent.setCenter(Model.getInstance().getViewFactory().getListClientsView());
                        case DEPOSIT -> adminParent.setCenter(Model.getInstance().getViewFactory().getDepositView());
                        default -> adminParent.setCenter(Model.getInstance().getViewFactory().getCreateClientView());
                    }
                });
    }
}
