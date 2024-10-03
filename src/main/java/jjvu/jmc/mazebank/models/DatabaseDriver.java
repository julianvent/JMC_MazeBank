package jjvu.jmc.mazebank.models;

import java.sql.*;

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

    /*
     * Utility Methods
     * */
}
