package application;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;

import java.util.List;

public class Program2 {
    public static void main(String[] args) {
        DepartmentDao departmentDao = DaoFactory.createDepartmentDao();

        System.out.println("==== TEST 1 - RETRIEVING A DEPARTMENT ====");
        Department retrievedDepartment = departmentDao.findById(3).get();
        System.out.println(retrievedDepartment);

        System.out.println("\n==== TEST 2 - RETRIEVING ALL DEPARTMENTS ====");
        List<Department> allDepartments = departmentDao.findAll();
        allDepartments.forEach(System.out::println);

        System.out.println("\n==== TEST 3 - INSERTING A DEPARTMENT ====");
        Department newDepartment = new Department(null, "D1");
        departmentDao.insert(newDepartment);
        System.out.println("Insertion done! New department: " + newDepartment);

        System.out.println("\n==== TEST 4 - DELETING A DEPARTMENT ====");
        // departmentDao.deleteById(7);
        // System.out.println("Deleted!");

        System.out.println("\n==== TEST 5 - UPDATING A DEPARTMENT ====");
        Department updatedDepartment = departmentDao.findById(8).get();
        updatedDepartment.setName("Magic");
        departmentDao.updateById(updatedDepartment);
        System.out.println("Updated department: " + updatedDepartment);
    }
}
