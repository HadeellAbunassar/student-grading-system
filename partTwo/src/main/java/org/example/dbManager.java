package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class dbManager {

    private static dbManager instance;
    private Connection connection;

    private dbManager() {
    }

    public static synchronized dbManager getInstance() {
        if (instance == null) {
            instance = new dbManager();
        }
        return instance;
    }

    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            instance.connection = DriverManager.getConnection
                    ("jdbc:mysql://localhost:3306/gradingsystem?useTimezone=true&serverTimezone=UTC",
                            "hadeel", "hadeel123");

            return instance.connection;
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC driver not found.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Error connecting to the database.");
            e.printStackTrace();
        }
        return null;
    }


}
