package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class SellerDaoJDBC implements SellerDao {

    private Connection conn;

    public SellerDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Seller obj) {
    }

    @Override
    public void update(Seller obj) {
    }

    @Override
    public void deleteById(Integer id) {

    }

    @Override
    public Seller findById(Integer id) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(//PreparedStatement é usado para executar comandos SQL com parâmetros
                    "SELECT seller.*,department.Name as DepName "
                    + "FROM seller INNER JOIN department "
                    + "ON seller.DepartmentId = department.Id "
                    + "WHERE seller.Id = ?");

                    st.setInt(1, id);//setInt() para int, setString() para string, setDate() para date, etc
                    rs = st.executeQuery();//executeQuery() para consultas, executeUpdate() para insert, update e delete
                    if(rs.next()) {
                        Department dep = instantiateDepartment(rs);//instanciar um objeto Department usando os dados do ResultSet
                        Seller obj = instantiateSeller(rs, dep);//instanciar um objeto Seller usando os dados do ResultSet e o objeto Department criado anteriormente
                        return obj;
                    }
                    return null;
                } catch (SQLException e) {
                    throw new DbException(e.getMessage());//DbException é uma classe personalizada que estende RuntimeException, usada para tratar exceções relacionadas ao banco de dados
                
                } finally {
                    DB.closeResultSet(rs);
                    DB.closeStatement(st);
                }
    }
    private Department instantiateDepartment(ResultSet rs) throws SQLException {//instanciar um objeto Department usando os dados do ResultSet
        Department dep = new Department();
        dep.setId(rs.getInt("DepartmentId"));
        dep.setName(rs.getString("Name"));
        return dep;
    }

    private Seller instantiateSeller(ResultSet rs, Department dep) throws SQLException {//instanciar um objeto Seller usando os dados do ResultSet e o objeto Department criado anteriormente
        Seller obj = new Seller();
        obj.setId(rs.getInt("Id"));
        obj.setName(rs.getString("Name"));
        obj.setEmail(rs.getString("Email"));
        obj.setBaseSalary(rs.getDouble("BaseSalary"));
        obj.setBirthDate(rs.getDate("Birthdate"));
        obj.setDepartment(dep);
        return obj;
    }

    @Override
    public List<Seller> findAll() {//List é uma interface que representa uma coleção de elementos, e List.of() é um método estático que retorna uma lista imutável contendo os elementos fornecidos como argumentos. No caso, List.of() sem argumentos retorna uma lista vazia.

        return List.of();
    }
}
