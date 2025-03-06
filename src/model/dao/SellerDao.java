package model.dao;

import model.entities.Seller;

import java.util.List;
import java.util.Optional;

public interface SellerDao {
    void insert(Seller seller);
    Optional<Seller> findById(Integer id);
    List<Seller> findByDepartment(Integer departmentId);
    List<Seller> findAll();
    void updateById(Seller seller);
    void deleteById(Integer id);
}
