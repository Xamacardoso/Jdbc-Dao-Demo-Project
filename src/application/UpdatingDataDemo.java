package application;

import db.DB;
import db.DbException;

import java.sql.*;

public class UpdatingDataDemo {
    public static void main(String[] args) {

        Connection con = null;
        PreparedStatement prepStmt = null;

        try {
            con = DB.getConnection();

            prepStmt = con.prepareStatement("""
            UPDATE seller
            SET BaseSalary = BaseSalary + ? WHERE
            (DepartmentId = ?)""");

            prepStmt.setDouble(1, 200.0);
            prepStmt.setInt(2, 2);

            int rowsAffected = prepStmt.executeUpdate();

            System.out.println("Done, rows affected: " + rowsAffected);

        } catch (SQLException e) {
            throw new DbException(e.getMessage());

        } finally {
            DB.closeStatement(prepStmt);
            DB.closeConnection();
        }
    }
}
