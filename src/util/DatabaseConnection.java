package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:sqlserver://192.168.1.27:1433;databaseName=QLy_HopDong;encrypt=false";
    private static final String USERNAME = "sa";
    private static final String PASSWORD = "251405";

    public static Connection getConnection() {
        try {
            // Không cần gọi Class.forName với JDBC 4.0 trở lên
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            // In lỗi ra console, có thể thay bằng log hoặc thông báo người dùng
            System.out.println("Connection failed: " + e.getMessage());
            return null;
        }
    }
}
