package application;

import db.DB;
import db.DbException;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class InsertingDataDemo {
    public static void main(String[] args) throws ParseException {
        Connection conn = null;

        // Statement object that represents a parametrized sql query
        PreparedStatement prepSmt = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        try {
            conn = DB.getConnection();

            // SQL statement with placeholders (parameter indications)
            prepSmt = conn.prepareStatement("""
            INSERT INTO seller
            (Name, Email, BirthDate, BaseSalary, DepartmentId)
            VALUES
            (?, ?, ?, ?, ?)""",
            PreparedStatement.RETURN_GENERATED_KEYS);

            // Putting parameters
            prepSmt.setString(1, "Joana D'Arc");
            prepSmt.setString(2, "joanadarc@hotmail.com");
            prepSmt.setDate(3, new java.sql.Date(dateFormat.parse("22/04/1985").getTime()));
            prepSmt.setDouble(4, 3000.0);
            prepSmt.setInt(5, 4);

            // prepSmt = conn.prepareStatement("""
            // INSERT INTO department (Name)
            // VALUES ('D1'), ('D2')""", Statement.RETURN_GENERATED_KEYS);

            // Method that is used when a query changes rows in database. Returns how many rows affected.
            int rowsAffected = prepSmt.executeUpdate();
            System.out.println("Done! Rows affected: " + rowsAffected);

            // Getting generated key value for new insertion
            if (rowsAffected > 0) {

                // Printing every insertion id
                ResultSet rs = prepSmt.getGeneratedKeys();
                while (rs.next()) {
                    int id = rs.getInt(1);
                    System.out.println("ID: " + id);
                }
            }


        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(prepSmt);
            DB.closeConnection();
        }
    }
}
