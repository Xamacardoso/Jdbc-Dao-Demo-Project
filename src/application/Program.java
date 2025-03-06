package application;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

import java.util.Date;
import java.util.List;


public class Program {
    public static void main(String[] args) {
        SellerDao sellerDao = DaoFactory.createSellerDao();
        Seller seller = sellerDao.findById(1).get();

        System.out.println("===== TEST 1 - RETRIEVING ONE SELLER ====");
        System.out.println(seller);

        System.out.println("\n===== TEST 2 - RETRIEVING ALL SELLERS ON A DEPARTMENT ====");
        List<Seller> sellers = sellerDao.findByDepartment(1);
        sellers.forEach(System.out::println);

        System.out.println("\n===== TEST 3 - RETRIEVING ALL SELLERS ====");
        List<Seller> allSellers = sellerDao.findAll();
        allSellers.forEach(System.out::println);

        System.out.println("\n===== TEST 4 - INSERTING A SELLER ====");
        Seller newSeller = new Seller(null, "Gregory Green", "greg@gmail.com", 2500.00, new Date(), new Department(2, null));
        // sellerDao.insert(newSeller);
        // System.out.println("Inserted! New id: " + newSeller.getId());

        System.out.println("\n===== TEST 5 - UPDATING A SELLER ====");
        seller = sellerDao.findById(11).get();
        seller.setName("Gregorius Greenus");
        sellerDao.update(seller);
        System.out.println("Updated! New seller: " + seller.toString());
    }
}
