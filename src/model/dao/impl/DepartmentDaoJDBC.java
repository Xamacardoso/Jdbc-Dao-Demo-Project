package model.dao.impl;

import db.DB;
import model.dao.DepartmentDao;
import model.entities.Department;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public class DepartmentDaoJDBC implements DepartmentDao {
    private Connection conn;

    public DepartmentDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Department department) {

    }

    @Override
    public Optional<Department> findById(Integer id) {
        return Optional.empty();
    }

    @Override
    public List<Department> findAll() {
        return List.of();
    }

    @Override
    public void updateById(Department department) {

    }

    @Override
    public void deleteById(Integer id) {

    }
}
