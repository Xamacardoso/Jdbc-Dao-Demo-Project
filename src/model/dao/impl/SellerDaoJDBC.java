package model.dao.impl;

import model.dao.SellerDao;
import model.entities.Seller;

import java.sql.Connection;
import java.util.List;

public class SellerDaoJDBC implements SellerDao {
    private Connection conn;

    public SellerDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Seller seller) {

    }

    @Override
    public Seller findById(Integer id) {
        return null;
    }

    @Override
    public List<Seller> findAll() {
        return List.of();
    }

    @Override
    public void updateById(Seller seller) {

    }

    @Override
    public void deleteById(Integer id) {

    }
}
