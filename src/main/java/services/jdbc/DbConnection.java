package services.jdbc;

import services.utilities.ConfigLoader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {


    private Connection conn = null;

    private static DbConnection instance;

    private DbConnection() {
        try {
            String URL = ConfigLoader.get("db.url");
            String USER = ConfigLoader.get("db.user");
            String PASSWORD = ConfigLoader.get("db.password");
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("connection etablie");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static DbConnection getInstance() {
        if (instance == null) {
            instance = new DbConnection();
        }
        return instance;
    }


    public Connection getConn() {
        return conn;
    }
}
