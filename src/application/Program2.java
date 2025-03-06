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

        System.out.println("\n==== TEST 2 - RETRIEVING A DEPARTMENT ====");
        List<Department> allDepartments = departmentDao.findAll();
        allDepartments.forEach(System.out::println);


    }
}
