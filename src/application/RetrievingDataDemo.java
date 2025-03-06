package application;

import db.DB;
import db.DbException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class RetrievingDataDemo {
    public static void main(String[] args) {
        Statement statement = null; // Object that represents a sql query
        ResultSet resultSet = null; // Object that represents a TABLE
        Connection conn = null;

        try {
            conn = DB.getConnection(); // Creates db connection

            statement = conn.createStatement(); // Then a statement is prepared

            // Then the statement is executed, producing a ResultSet object as a result of the operation
            resultSet = statement.executeQuery("select * from department");

            while (resultSet.next()) {
                System.out.println(resultSet.getInt("id") + " " + resultSet.getString("name"));
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(statement);
        }
    }
}
