package application;

import db.DB;
import db.DbException;
import db.DbIntegrityException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeletingDataDemo {
    public static void main(String[] args) {

        Connection con = null;
        PreparedStatement prepStmt = null;

        try {
            con = DB.getConnection();

            prepStmt = con.prepareStatement("""
            DELETE FROM department
            WHERE (Id = ?)""");

            prepStmt.setInt(1, 6);

            int rowsAffected = prepStmt.executeUpdate();

            System.out.println("Done, rows affected: " + rowsAffected);

        } catch (SQLException e) {
            throw new DbIntegrityException(e.getMessage());

        } finally {
            DB.closeStatement(prepStmt);
            DB.closeConnection();
        }
    }
}
