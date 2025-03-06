package db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

// Object for database connection management
public class DB {

    // Connection object that manages sql database connections
    private static Connection conn;

    public static Connection getConnection() {
        if (conn == null) {
            try {
                // Picks db properties
                Properties props = loadProperties();
                String dbUrl = props.getProperty("dburl");

                // Tries to connect
                System.out.println(dbUrl);
                conn = DriverManager.getConnection(dbUrl, props);
            } catch (SQLException e) {
                throw new DbException(e.getMessage());
            }
        }

        return conn;
    }

    public static void closeConnection() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                throw new DbException(e.getMessage());
            }
        }
    }

    // Loads the db.properties file existing on source folder
    private static Properties loadProperties(){
        try (FileInputStream fs = new FileInputStream("db.properties")){
            Properties props = new Properties();
            props.load(fs);
            return props;

        } catch (IOException e) {
            throw new DbException(e.getMessage());
        }
    }

    public static void closeStatement(Statement stmt) {
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                throw new DbException(e.getMessage());
            }
        }
    }

    public static void closeResultSet(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                throw new DbException(e.getMessage());
            }
        }
    }
}
