package model.dao.impl;

import java.util.List;

import Model.dao.SellerDao;
import Model.entities.Seller;

public class SellerDaoJDBC implements SellerDao{

    private connection conn;    

    public SellerDaoJDBC(connection conn) {
        this.conn = conn;
    }

    public void insert(Seller obj) {

    }

    public void update(Seller obj) {

    }

    public void deleteById(Integer id) {

    }

    public Seller findById(Integer id) {
        PrepareStatement st = null;
        ResultSet rs= null;
        try {
            st = conn.prepareStatement(
                "SELECT seller.*,department.Name as DepName "
                + "FROM seller INNER JOIN department "
                + "ON seller.DepartmentId = department.Id "
                + "WHERE seller.Id = ?"
            )
        }

    }

    public List<Seller> findAll() {
        
    }
}
