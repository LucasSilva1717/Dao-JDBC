package Application;

import model.dao.DaoFactory;
import model.dao.impl.SellerDaoJDBC;
import model.entities.Seller;

public class Program {

    public static void main(String[] args) {

        SellerDaoJDBC sellerDao = DaoFactory.createSellerDao();

        Seller seller = sellerDao.findById(3);
        
        if (seller != null) {
            System.out.println("Seller found: " + seller.getName());
        } else {
            System.out.println("Seller not found");
        }
        
        System.out.println(seller);
        

    }

}
