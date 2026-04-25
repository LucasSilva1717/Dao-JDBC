package model.dao.impl;

import java.sql.Connection;
import java.util.List;

import model.entities.Seller;

public class SellerDaoJDBC extends SellerDao {

    private Connection conn;

    public SellerDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    public void insert(Seller obj) {
    }

    public void update(Seller obj) {
    }

    public void deleteById(Integer id) {

    }

    public Seller findById(Integer id) {

        return null;
    }
    public List<Seller> findAll() {

        return List.of();
    }
}
