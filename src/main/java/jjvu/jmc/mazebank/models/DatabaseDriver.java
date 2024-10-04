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

    public ResultSet getTransactions(String payeeAddress, int limit) {
        Statement statement;
        ResultSet resultSet = null;

        try {
            statement = this.connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM Transactions WHERE Sender='"+payeeAddress+"' OR Receiver='"+payeeAddress+"' LIMIT "+limit +";");
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        return resultSet;
    }

    // Method returns savings account balance
    public double getSavingsAccountBalance(String payeeAddress) {
        Statement statement;
        ResultSet resultSet;
        double balance = 0;

        try {
            statement = this.connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM SavingsAccounts WHERE Owner='"+payeeAddress+"';");
            balance = resultSet.getDouble("balance");
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        return balance;
    }

    // Method to either add or subtract from balance given operation
    public void updateBalance(String payeeAddress, double amount, String operation) {
        Statement statement;
        ResultSet resultSet;

        try {
            statement = this.connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM SavingsAccounts WHERE Owner='"+payeeAddress+"';");
            double newBalance;
            if (operation.equals("ADD")) {
                 newBalance = resultSet.getDouble("Balance") + amount;
                statement.executeUpdate("UPDATE SavingsAccounts SET Balance="+newBalance+" WHERE Owner='"+payeeAddress+"';");
            } else {
                if (resultSet.getDouble("Balance") >= amount) {
                    newBalance = resultSet.getDouble("Balance") - amount;
                    statement.executeUpdate("UPDATE SavingsAccounts SET Balance="+newBalance+" WHERE Owner='"+payeeAddress+"';");
                }
            }

        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
    }

    // Creates and records new Transaction
    public void newTransaction(String sender, String receiver, double amount, String message) {
        Statement statement;

        try {
            statement = this.connection.createStatement();
            LocalDate date = LocalDate.now();

            statement.executeUpdate("INSERT INTO " +
                    "Transactions(Sender, Receiver, Amount, Date, Message) " +
                    " VALUES ('"+ sender + "', '"+receiver+"', "+amount+", '"+date+"', '"+message+"');"
            );
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
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

    public ResultSet getAllClientsData() {
        Statement statement;
        ResultSet resultSet = null;

        try {
            statement = this.connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM Clients;");
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        return resultSet;
    }

    public void depositSavings(String payeeAddress, double amount) {
        Statement statement;

        try {
            statement = this.connection.createStatement();
            statement.executeUpdate("UPDATE SavingsAccounts SET Balance="+amount+" WHERE Owner='"+payeeAddress+"';");
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

    public ResultSet getCheckingAccountData(String payeeAddress) {
        Statement statement;
        ResultSet resultSet = null;

        try {
            statement = this.connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM CheckingAccounts WHERE Owner='"+payeeAddress+"';");
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        return resultSet;
    }

    public ResultSet getSavingsAccountData(String payeeAddress) {
        Statement statement;
        ResultSet resultSet = null;

        try {
            statement = this.connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM SavingsAccounts WHERE Owner='"+payeeAddress+"';");
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        return resultSet;
    }

    public ResultSet searchClient(String payeeAddress) {
        Statement statement;
        ResultSet resultSet = null;

        try {
            statement = this.connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM Clients WHERE PayeeAddress='"+payeeAddress+"';");
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        return resultSet;
    }
}
