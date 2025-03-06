package application;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Seller;

import java.util.List;


public class Program {
    public static void main(String[] args) {
        SellerDao sellerDao = DaoFactory.createSellerDao();
        Seller newSeller = sellerDao.findById(1).get();

        System.out.println("===== TEST 1 - RETRIEVING ONE SELLER ====");
        System.out.println(newSeller);

        System.out.println("\n===== TEST 2 - RETRIEVING ALL SELLERS ON A DEPARTMENT ====");
        List<Seller> sellers = sellerDao.findByDepartment(1);
        sellers.forEach(System.out::println);

        System.out.println("\n===== TEST 3 - RETRIEVING ALL SELLERS ====");
        List<Seller> allSellers = sellerDao.findAll();
        allSellers.forEach(System.out::println);
    }
}
