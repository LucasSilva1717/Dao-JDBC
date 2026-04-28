package Application;

import java.sql.SQLException;
import java.util.List;
import model.dao.DaoFactory;
import model.dao.impl.SellerDaoJDBC;
import model.entities.Department;
import model.entities.Seller;

public class Program {

    public static void main(String[] args) throws SQLException {

        SellerDaoJDBC sellerDao = DaoFactory.createSellerDao();

        System.out.println("=====FyndById=====");

        Seller seller = sellerDao.findById(3);
        
        System.out.println(seller);

        System.out.println("\n=====FyndByDepartment=====");
        Department department = new Department(2, null);
        List<Seller> list = sellerDao.findByDepartment(department);
        for (Seller obj : list) {
            System.out.println(obj);
        }
    }
}
