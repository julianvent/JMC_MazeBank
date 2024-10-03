package jjvu.jmc.mazebank.models;

import java.sql.*;
import java.time.LocalDate;

public class DatabaseDriver {
    private Connection connection;

    public DatabaseDriver() {
        try {
            this.connection = DriverManager.getConnection("jdbc:sqlite:mazebank.db");
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
    }

    /*
    * Client Section
    * */
    public ResultSet getClientData(String payeeAddress, String password) {
        Statement statement;
        ResultSet resultSet = null;
        try {
            statement = this.connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM Clients WHERE PayeeAddress='"+payeeAddress+"' AND Password='"+password+"';");
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        return resultSet;
    }

    /*
     * Admin Section
     * */
    public ResultSet getAdminData(String username, String password) {
        Statement statement;
        ResultSet resultSet = null;

        try {
            statement = this.connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM Admins WHERE Username='"+username+"' AND Password ='"+password+"';");
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        return resultSet;
    }

    public void createClient(String firstName, String lastName, String payeeAddress, String password, LocalDate date) {
        Statement statement;

        try {
            statement = this.connection.createStatement();
            statement.executeUpdate("INSERT INTO " +
                    "Clients(FirstName, LastName, PayeeAddress, Password, Date) " +
                    "VALUES ('"+firstName+"', '"+lastName+"', '"+payeeAddress+"', '"+password+"', '"+date.toString()+"');"
            );
        } catch(SQLException sqle) {
            sqle.printStackTrace();
        }
    }

    public void createCheckingAccount(String owner, String accountNumber, double transactionLimit, double balance) {
        Statement statement;

        try {
            statement = this.connection.createStatement();
            statement.executeUpdate("INSERT INTO " +
                    "CheckingAccounts(Owner, AccountNumber, TransactionLimit, Balance) " +
                    "VALUES ('"+owner+"', '"+accountNumber+"', "+transactionLimit+", "+balance+");"
            );
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
    }

    public void createSavingsAccount(String owner, String accountNumber, double withdrawalLimit, double balance) {
        Statement statement;

        try {
            statement = this.connection.createStatement();
            statement.executeUpdate("INSERT INTO " +
                    "SavingsAccounts(Owner, AccountNumber, WithdrawalLimit, Balance) " +
                    "VALUES ('"+owner+"', '"+accountNumber+"', "+withdrawalLimit+", "+balance+");"
            );
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
    }

    /*
     * Utility Methods
     * */
    public int getLastClientID() {
        Statement statement;
        ResultSet resultSet;
        int id = 0;

        try {
            statement = this.connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM sqlite_sequence WHERE name='Clients';");
            id = resultSet.getInt("seq");
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        return id;
    }
}
