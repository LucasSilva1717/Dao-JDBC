package Application;

import java.sql.SQLException;
import java.util.List;
import model.dao.DaoFactory;
import model.dao.impl.SellerDaoJDBC;
import model.entities.Department;
import model.entities.Seller;

public class Program {

    public static void main(String[] args) throws SQLException {

        SellerDaoJDBC sellerDao = DaoFactory.createSellerDao();//instanciação do SellerDaoJDBC usando a fábrica de DAOs

        System.out.println("=====FyndById=====");
        Seller seller = sellerDao.findById(3);
        System.out.println(seller);

        System.out.println("\n=====FyndByDepartment=====");//instanciação de um objeto Department com id 2 e nome null, para ser usado como parâmetro no método findByDepartment
        Department department = new Department(2, null);
        List<Seller> list = sellerDao.findByDepartment(department);
        for (Seller obj : list) {
            System.out.println(obj);
        }

        System.out.println("\n=====FyndAll=====");//chamada do método findAll() para obter uma lista de todos os vendedores e impressão de cada um deles
        list = sellerDao.findAll();
        for (Seller obj : list) {
            System.out.println(obj);
        }

        System.out.println("\n=====Insert=====");//criação de um novo objeto Seller com os dados do novo vendedor e chamada do método insert() para inserir o novo vendedor no banco de dados
        Seller newSeller = new Seller(null, "Julio", "julio@gmail.com", new java.util.Date(), 4000.0, department);
        sellerDao.insert(newSeller);
        System.out.println("Inserted! New id = " + newSeller.getId());//impressão do id do novo vendedor, que foi gerado automaticamente pelo banco de dados e definido no objeto Seller após a inserção
        
        System.out.println("\n=====Update=====");//atualização do nome do vendedor e chamada do método update() para atualizar os dados do vendedor no banco de dados
        seller = sellerDao.findById(1);
        seller.setName("Matha Crane");//atualização do nome do vendedor com id 1 para "Matha Crane"
        seller.setBaseSalary(2000.0);
        sellerDao.update(seller);
        System.out.println("Update completed!");
    }
}
