package model.dao.impl;

import db.DB;
import db.DbException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

import java.sql.*;
import java.util.*;
import java.util.Date;

public class SellerDaoJDBC implements SellerDao {
    private Connection conn;

    public SellerDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Seller seller) {
        PreparedStatement prepStmt = null;
        try {
            prepStmt = conn.prepareStatement("""
            INSERT INTO seller (Name, Email, BirthDate, BaseSalary, DepartmentId)
            VALUES (?, ?, ?, ?, ?)
            """, Statement.RETURN_GENERATED_KEYS);

            prepStmt.setString(1, seller.getName());
            prepStmt.setString(2, seller.getEmail());
            prepStmt.setDate(3, new java.sql.Date(seller.getBirthDate().getTime()));
            prepStmt.setDouble(4, seller.getBaseSalary());
            prepStmt.setInt(5, seller.getDepartment().getId());
            int rowsAffected = prepStmt.executeUpdate();

            ResultSet rs = prepStmt.getGeneratedKeys();
            if (rowsAffected > 0) {
                if (rs.next()) {
                    seller.setId(rs.getInt(1));
                }

                DB.closeResultSet(rs);
            } else {
                throw new DbException("Unexpected error, no rows affected");
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(prepStmt);
        }

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
        ResultSet result = null;
        Statement stmt = null;
        List<Seller> sellers = new ArrayList<>();

        try {
            stmt = conn.createStatement();

            result = stmt.executeQuery("""
            SELECT s.*, d.Name as DepName FROM seller s
            INNER JOIN department d ON s.DepartmentId = d.Id
            ORDER BY Name""");

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
            DB.closeStatement(stmt);
            DB.closeResultSet(result);
        }
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
