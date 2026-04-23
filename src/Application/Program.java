package Application;

import java.text.SimpleDateFormat;
import java.sql.Date;

import model.entities.Department;
import model.entities.Seller;

public class Program {

    private SimpleDateFormat SDF = new SimpleDateFormat("dd/MM/yyyy");

    public static void main(String[] args) {

        Seller seller = new Seller(5, "Lucas", "Lucas@gmail.com", Date.valueOf("2000-07-20"), 3500.0, new Department(1, "Computadores"));

        System.out.println(seller);



    }

}
