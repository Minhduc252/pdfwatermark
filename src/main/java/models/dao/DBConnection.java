package models.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/pdfwatermark";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    private static Connection connection;
    
    private DBConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            System.out.println("Kết nối cơ sở dữ liệu thành công!");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Lỗi kết nối cơ sở dữ liệu: " + e.getMessage());
        }
    }

    public static Connection getConnection() {
        if (connection == null) {
            synchronized (DBConnection.class) {
                if (connection == null) {
                    new DBConnection(); 
                }
            }
        }
        return connection;
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Kết nối cơ sở dữ liệu đã được đóng.");
            } catch (SQLException e) {
                e.printStackTrace();
                System.err.println("Lỗi khi đóng kết nối cơ sở dữ liệu.");
            }
        }
    }
}
