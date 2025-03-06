package application;

import db.DB;
import db.DbException;
import db.DbIntegrityException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class TransactionsDemo {
    public static void main(String[] args) {

        Connection con = null;
        Statement stmt = null;

        try {
            con = DB.getConnection();
            con.setAutoCommit(false); // All operations must be confirmed manually to occur
            stmt = con.createStatement();

            int rows1 = stmt.executeUpdate("""
            UPDATE seller SET BaseSalary = 6969.0 WHERE DepartmentId = 1""");

            if (true) {
                throw new SQLException("Fake error. Testing");
            }

            int rows2 = stmt.executeUpdate("""
            UPDATE seller SET BaseSalary = 3020.0 WHERE DepartmentId = 2""");

            con.commit(); // Confirmation
            System.out.println("Done!!");

        } catch (SQLException e) {
            try {
                // Rolls back the transaction, returning to the previous commit state
                con.rollback();
                throw new DbException("Transaction rolled back! Caused by: " + e.getMessage());

            // Error at rollback operation
            } catch (SQLException ex) {
                throw new DbException("Error trying to rollback! Caused by: " + ex.getMessage());
            }

        } finally {
            DB.closeStatement(stmt);
            DB.closeConnection();
        }
    }
}
