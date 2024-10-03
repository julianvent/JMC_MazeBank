package jjvu.jmc.mazebank.models;

import jjvu.jmc.mazebank.views.AccountType;
import jjvu.jmc.mazebank.views.ViewFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class Model {
    private static Model model;
    private final ViewFactory viewFactory;
    private final DatabaseDriver databaseDriver;

    // Client Data Section
    private Client client;
    private boolean clientLoginSuccessFlag;

    // Admin Data Section
    private boolean adminLoginSuccessFlag;

    private Model() {
        this.viewFactory = new ViewFactory();
        this.databaseDriver = new DatabaseDriver();

        // Client Data Section
        this.clientLoginSuccessFlag = false;
        this.client = new Client("", "", "", null, null, null);

        // Admin Data Section
        this.adminLoginSuccessFlag = false;
    }

    // Singleton
    public static synchronized Model getInstance() {
        if (model == null) {
            model = new Model();
        }

        return model;
    }

    public ViewFactory getViewFactory() {
        return viewFactory;
    }

    public DatabaseDriver getDatabaseDriver() {
        return databaseDriver;
    }

    /*
    * Client Method Section
    * */

    public boolean getClientLoginSuccessFlag() {
        return clientLoginSuccessFlag;
    }

    public void setClientLoginSuccessFlag(boolean flag) {
        this.clientLoginSuccessFlag = flag;
    }

    public Client getClient() {
        return client;
    }

    public void evaluateClientCredentials(String payeeAddress, String password) {
        CheckingAccount checkingAccount;
        SavingsAccount savingsAccount;
        ResultSet resultSet = databaseDriver.getClientData(payeeAddress, password);

        try {
            if (resultSet.isBeforeFirst()) {
                this.client.firstNameProperty().set(resultSet.getString("FirstName"));
                this.client.lastNameProperty().set(resultSet.getString("LastName"));
                this.client.payeeAddressProperty().set(resultSet.getString("PayeeAddress"));
                String[] dateParts = resultSet.getString("Date").split("-");
                LocalDate date = LocalDate.of(
                        Integer.parseInt(dateParts[0]), Integer.parseInt(dateParts[1]), Integer.parseInt(dateParts[2])
                );
                this.client.dateCreatedProperty().set(date);

                this.clientLoginSuccessFlag = true;
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
    }

    /*
    * Admin Method Section
    * */

    public boolean getAdminLoginSuccessFlag() {
        return adminLoginSuccessFlag;
    }

    public void setAdminLoginSuccessFlag(boolean flag) {
        this.adminLoginSuccessFlag = flag;
    }

    public void evaluateAdminCredentials(String username, String password) {
        ResultSet resultSet = databaseDriver.getAdminData(username, password);

        try {
            if (resultSet.isBeforeFirst()) {
                this.adminLoginSuccessFlag = true;
            }
        } catch (SQLException ioe) {
            ioe.printStackTrace();
        }
    }
}
