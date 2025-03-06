package application;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.dao.impl.DepartmentDaoJDBC;
import model.entities.Department;

public class Program2 {
    public static void main(String[] args) {
        DepartmentDao departmentDao = DaoFactory.createDepartmentDao();

        System.out.println("==== TEST 1 - RETRIEVING A DEPARTMENT ====");
        Department retrievedDepartment = departmentDao.findById(3).get();
        System.out.println(retrievedDepartment);


    }
}
