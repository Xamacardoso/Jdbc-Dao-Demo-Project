package model.dao;

import model.entities.Department;

import java.util.List;
import java.util.Optional;

public interface DepartmentDao {
    void insert(Department department);
    Optional<Department> findById(Integer id);
    List<Department> findAll();
    void updateById(Department department);
    void deleteById(Integer id);
}
