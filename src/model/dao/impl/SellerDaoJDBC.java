package model.dao.impl;

import db.DB;
import db.DbException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class SellerDaoJDBC implements SellerDao {
    private Connection conn;

    public SellerDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Seller seller) {

    }

    @Override
    public Seller findById(Integer id) {
        ResultSet result = null;
        PreparedStatement prepStmt = null;

        try {
            prepStmt = conn.prepareStatement("""
            SELECT s.*, d.Name as DepName FROM seller s
            INNER JOIN department d ON s.DepartmentId = d.Id
            WHERE s.Id = ?
            ORDER BY Name""");

            prepStmt.setInt(1, id);
            result = prepStmt.executeQuery();

            if (result.next()) {
                Integer resultId = result.getInt("Id");
                System.out.println("Retrieved seller with id " + result.getInt("Id"));

                String name = result.getString("Name");
                String email = result.getString("Email");
                Date birthDate = result.getDate("BirthDate");
                Double salary = result.getDouble("BaseSalary");
                Department department = new Department(result.getInt("DepartmentId"), result.getString("DepName"));

                return new Seller(resultId, name, email, salary, birthDate, department);
            }

            return null;

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(prepStmt);
            DB.closeResultSet(result);
            DB.closeConnection();
        }
    }

    @Override
    public List<Seller> findAll() {
        return List.of();
    }

    @Override
    public void updateById(Seller seller) {

    }

    @Override
    public void deleteById(Integer id) {

    }
}
