package model.dao.impl;

import db.DB;
import db.DbException;
import model.dao.DepartmentDao;
import model.entities.Department;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DepartmentDaoJDBC implements DepartmentDao {
    private Connection conn;

    public DepartmentDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Department department) {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conn.prepareStatement("""
            INSERT INTO department (Name)
            VALUES (?)""", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, department.getName());

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    department.setId(rs.getInt(1));
                }

            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }finally {
            DB.closeStatement(ps);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public Optional<Department> findById(Integer id) {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conn.prepareStatement("SELECT * FROM department WHERE Id = ?");
            ps.setInt(1, id);

            rs = ps.executeQuery();
            if (rs.next()) {
                Department retrievedDepartment = instantiateDepartment(rs);
                return Optional.of(retrievedDepartment);
            }
        } catch (SQLException e){
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(ps);
            DB.closeResultSet(rs);
        }

        return Optional.empty();
    }

    @Override
    public List<Department> findAll() {
        Statement stmt = null;
        ResultSet rs = null;
        List<Department> departments = new ArrayList<>();

        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM department");

            while (rs.next()) {
                Department department = instantiateDepartment(rs);
                departments.add(department);
            }

            return departments;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(stmt);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public void updateById(Department department) {
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement("""
            UPDATE department SET Name = ?
            WHERE Id = ?""", Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, department.getName());
            ps.setInt(2, department.getId());

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected == 0) {
                throw new DbException("No rows affected");
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(ps);
        }
    }

    @Override
    public void deleteById(Integer id) {
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement("DELETE FROM department WHERE Id = ?");
            ps.setInt(1, id);

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(ps);
        }
    }

    private static Department instantiateDepartment(ResultSet rs) throws SQLException {
        Integer id = rs.getInt("Id");
        String name = rs.getString("Name");
        return new Department(id, name);
    }
}
