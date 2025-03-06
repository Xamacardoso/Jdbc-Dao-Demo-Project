package model.dao;

import model.entities.Seller;

import java.util.List;

public interface SellerDao {
    void insert(Seller seller);
    Seller findById(Integer id);
    List<Seller> findAll();
    void updateById(Seller seller);
    void deleteById(Integer id);
}
