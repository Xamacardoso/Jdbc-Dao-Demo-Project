package application;

import db.DB;
import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Seller;

import java.sql.Connection;

public class Program {
    public static void main(String[] args) {
        SellerDao sellerDao = DaoFactory.createSellerDao();
        Seller newSeller = sellerDao.findById(1);
        System.out.println(newSeller);
    }
}
