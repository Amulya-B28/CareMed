package database;

import java.sql.*;

public class DatabaseConnection {
    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/caremed",
                "root", "your_password"
            );
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
