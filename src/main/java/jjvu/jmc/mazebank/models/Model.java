package jjvu.jmc.mazebank.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import jjvu.jmc.mazebank.views.AccountType;
import jjvu.jmc.mazebank.views.ViewFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
    private final ObservableList<Client> clients;

    private Model() {
        this.viewFactory = new ViewFactory();
        this.databaseDriver = new DatabaseDriver();

        // Client Data Section
        this.clientLoginSuccessFlag = false;
        this.client = new Client("", "", "", null, null, null);

        // Admin Data Section
        this.adminLoginSuccessFlag = false;
        this.clients = FXCollections.observableArrayList();
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

                checkingAccount = getCheckingAccount(payeeAddress);
                savingsAccount = getSavingsAccount(payeeAddress);
                this.client.checkingAccountProperty().set(checkingAccount);
                this.client.savingsAccountProperty().set(savingsAccount);

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

    public ObservableList<Client> getClients() {
        return clients;
    }

    public void setClients() {
        CheckingAccount checkingAccount;
        SavingsAccount savingsAccount;

        ResultSet resultSet = databaseDriver.getAllClientsData();

        try {
            while (resultSet.next()) {
                String firstName = resultSet.getString("FirstName");
                String lastName = resultSet.getString("LastName");
                String payeeAddress = resultSet.getString("PayeeAddress");
                String[] dateParts = resultSet.getString("Date").split("-");
                LocalDate date = LocalDate.of(Integer.parseInt(dateParts[0]), Integer.parseInt(dateParts[1]), Integer.parseInt(dateParts[2]));
                checkingAccount = getCheckingAccount(payeeAddress);
                savingsAccount = getSavingsAccount(payeeAddress);

                clients.add(new Client(firstName, lastName, payeeAddress, checkingAccount, savingsAccount, date));
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
    }

    public ObservableList<Client> searchClient(String payeeAddress) {
        ObservableList<Client> searchResults = FXCollections.observableArrayList();
        ResultSet resultSet = databaseDriver.searchClient(payeeAddress);

        try {
            CheckingAccount checkingAccount = getCheckingAccount(payeeAddress);
            SavingsAccount savingsAccount = getSavingsAccount(payeeAddress);
            String firstName = resultSet.getString("FirstName");
            String lastName = resultSet.getString("LastName");
            String[] dateParts = resultSet.getString("Date").split("-");
            LocalDate date = LocalDate.of(Integer.parseInt(dateParts[0]), Integer.parseInt(dateParts[1]), Integer.parseInt(dateParts[2]));

            searchResults.add(new Client(firstName, lastName, payeeAddress, checkingAccount, savingsAccount, date));
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        return searchResults;
    }

    /*
    * Utility Methods
    */
    public CheckingAccount getCheckingAccount(String payeeAddress) {
        CheckingAccount checkingAccount = null;

        ResultSet resultSet = databaseDriver.getCheckingAccountData(payeeAddress);

        try {
            String accountNumber = resultSet.getString("AccountNumber");
            int transactionLimit = (int)resultSet.getDouble("TransactionLimit");
            double balance = resultSet.getDouble("Balance");
            checkingAccount = new CheckingAccount(payeeAddress, accountNumber, balance, transactionLimit);
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        return checkingAccount;
    }

    public SavingsAccount getSavingsAccount(String payeeAddress) {
        SavingsAccount savingsAccount = null;

        ResultSet resultSet = databaseDriver.getSavingsAccountData(payeeAddress);

        try {
            String accountNumber = resultSet.getString("AccountNumber");
            double withdrawalLimit = resultSet.getDouble("WithdrawalLimit");
            double balance = resultSet.getDouble("Balance");
            savingsAccount = new SavingsAccount(payeeAddress, accountNumber, balance, withdrawalLimit);
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        return savingsAccount;
    }
}
