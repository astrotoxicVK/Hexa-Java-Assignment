package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DButil {
    private static final String URL = "jdbc:mysql://localhost:3306/ticketbookingsystem";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    public static Connection getDBConn() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;	
        }
    }
}
