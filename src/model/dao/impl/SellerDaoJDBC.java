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
import java.util.*;

public class SellerDaoJDBC implements SellerDao {
    private Connection conn;

    public SellerDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Seller seller) {

    }

    @Override
    public Optional<Seller> findById(Integer id) {
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
                Department dep = instantiateDepartment(result);
                Seller retrievedSeller = instantiateSeller(result, dep);
                return Optional.of(retrievedSeller);
            }

            return Optional.empty();

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(prepStmt);
            DB.closeResultSet(result);
        }
    }

    @Override
    public List<Seller> findByDepartment(Integer departmentId) {
        ResultSet result = null;
        PreparedStatement prepStmt = null;
        List<Seller> sellers = new ArrayList<>();

        try {
            prepStmt = conn.prepareStatement("""
            SELECT s.*, d.Name as DepName FROM seller s
            INNER JOIN department d ON s.DepartmentId = d.Id
            WHERE d.Id = ?
            ORDER BY Name""");

            prepStmt.setInt(1, departmentId);
            result = prepStmt.executeQuery();

            // Map for department instantiation control
            Map<Integer, Department> departmentMap = new HashMap<>();


            while (result.next()) {
                Department dep = departmentMap.get(result.getInt("DepartmentId"));

                // If the department hasn't been instantiated before, instantiate it and puts into the hashmap
                if (dep == null) {
                    dep = instantiateDepartment(result);
                    departmentMap.put(dep.getId(), dep);
                }

                Seller retrievedSeller = instantiateSeller(result, dep);
                sellers.add(retrievedSeller);
            }

            return sellers;

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(prepStmt);
            DB.closeResultSet(result);
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

    private Department instantiateDepartment(ResultSet rs) throws SQLException {
        Integer departmentId = rs.getInt("DepartmentId");
        String departmentName = rs.getString("DepName");

        return new Department(departmentId, departmentName);
    }

    private Seller instantiateSeller(ResultSet result, Department department) throws SQLException {
        Integer resultId = result.getInt("Id");
        String name = result.getString("Name");
        String email = result.getString("Email");
        Date birthDate = result.getDate("BirthDate");
        Double salary = result.getDouble("BaseSalary");

        return new Seller(resultId, name, email, salary, birthDate, department);
    }
}
