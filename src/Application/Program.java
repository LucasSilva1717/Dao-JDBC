package Application;

import java.sql.Date;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class Program {

    public static void main(String[] args) {

        Seller seller = new Seller(5, "Lucas", "Lucas@gmail.com", Date.valueOf("2000-07-20"), 3500.0, new Department(1, "Computadores"));

        SellerDao SellerDao = DaoFactory.createSellerDao();

        System.out.println(seller);
        

    }

}
