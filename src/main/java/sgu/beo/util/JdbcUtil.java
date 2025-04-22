package sgu.beo.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JdbcUtil {
    private static JdbcUtil instance;
    private Connection connection;

    private static final String JDBC_URL = "jdbc:mysql://localhost:3307/shoe_store";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASS = "root";

    private JdbcUtil() {
    }

    public static JdbcUtil getInstance() {
        if (instance == null) {
            synchronized (JdbcUtil.class) {
                if (instance == null) {
                    instance = new JdbcUtil();
                }
            }
        }
        return instance;
    }

    public Connection getConnection() {
        try {
            this.connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Database connection failed", e);
        }
        return connection;
    }

    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}