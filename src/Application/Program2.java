package Application;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import model.dao.DaoFactory;
import model.dao.impl.DepartmentDaoJDBC;
import model.entities.Department;

public class Program2 {

    public static void main(String[] args) throws SQLException {

        Scanner sc = new Scanner(System.in);

        DepartmentDaoJDBC departmentDao = DaoFactory.createDepartmentDao();//instanciação do DepartmentDaoJDBC usando a fábrica de DAOs

        System.out.println("=====FyndById=====");
        Department department = departmentDao.findById(1);
        System.out.println(department);


        System.out.println("\n=====FindAll=====");//chamada do método findAll() para obter uma lista de todos os departamentos e impressão de cada um deles
        List<Department> list = departmentDao.findAll();
        for (int i = 0; i < list.size(); i++){
            System.out.println((i+1) + ": " + list.get(i));
        }


        System.out.println("\n=====FindByName=====");
        System.out.println("Enter department name: ");
        String name = sc.nextLine();
        Department nameToSearch = new Department(null, name);
        List<Department> listByName = departmentDao.findByName(nameToSearch);
        if (listByName.isEmpty()) {
            System.out.println("Department not found!");
        } else {
        for (Department obj : listByName){
            System.out.println(obj);   
            }
        }

        System.out.println("\n=====Insert=====");
        System.out.println("Enter department name: ");
        String nameDep = sc.nextLine();
        Department newDepartment = new Department(null, nameDep);
        departmentDao.insert(newDepartment);
        System.out.println("Inserted! New id = " + newDepartment.getName());//impressão do id do novo departamento, que foi gerado automaticamente pelo banco de dados e definido no objeto Department após a inserção
        if (newDepartment.getId() != null) {
            //System.out.println("Inserted! New id = " + newDepartment.getId());
        } else {
            //System.out.println("Insertion failed!");
        }

        System.out.println("\n=====Update=====");
        System.out.println("Enter department id for update test: ");
        int id = sc.nextInt();
        sc.nextLine();
        department = departmentDao.findById(id);
        if (department != null) {
        System.out.print("Enter new department name: ");
        String newName = sc.nextLine();
        department.setName(newName);
        departmentDao.update(department);
        System.out.println("Update completed!");
        } else {
        System.out.println("Department not found!");
        

        System.out.println("\n=====Delete=====");
        System.out.println("Enter department id for delete test: ");
        int idToDelete = sc.nextInt();
        departmentDao.deleteById(idToDelete);
        if (departmentDao.findById(idToDelete) == null) {
            System.out.println("Delete completed!");
        } else {
            System.out.println("Delete failed! Department still exists.");
        }
        sc.close();  
        }
    }
}

