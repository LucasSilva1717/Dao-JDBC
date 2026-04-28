package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public void insert(Seller obj) {//método insert() para inserir um novo vendedor no banco de dados, usando um PreparedStatement para executar o comando SQL com os parâmetros do objeto Seller
        PreparedStatement st = null;

        try {
            st = conn.prepareStatement(//
                "INSERT INTO seller "
                + "(Name, Email, BirthDate, BaseSalary, DepartmentId) "
                + "VALUES "
                + "(?, ?, ?, ?, ?)",
                PreparedStatement.RETURN_GENERATED_KEYS);//opção para retornar as chaves geradas automaticamente pelo banco de dados, como o id do novo vendedor);
                
                st.setString(1, obj.getName());
                st.setString(2, obj.getEmail());
                st.setDate(3, new java.sql.Date(obj.getBirthDate().getTime()));//conversão de java.util.Date para java.sql.Date, necessária para armazenar a data no banco de dados
                st.setDouble(4, obj.getBaseSalary());
                st.setInt(5, obj.getDepartment().getId());

                int rowsAffected = st.executeUpdate();
                if (rowsAffected > 0) {//verificação se a inserção foi bem-sucedida, verificando o número de linhas afetadas pelo comando SQL
                    ResultSet rs = st.getGeneratedKeys();//obtenção das chaves geradas automaticamente pelo banco de dados, usando o método getGeneratedKeys() do PreparedStatement
                    if (rs.next()) {//verificação se há chaves geradas, usando o método next() do ResultSet
                        int id = rs.getInt(1);//obtenção do id gerado, usando o método getInt() do ResultSet, passando o índice da coluna (1 para a primeira coluna)
                        obj.setId(id);//definição do id do objeto Seller com o valor gerado pelo banco de dados, usando o método setId() do objeto Seller
                    }
                    DB.closeResultSet(rs);
                } else {
                    throw new DbException("Unexpected error! No rows affected!");//lançamento de uma exceção personalizada, caso a inserção não tenha afetado nenhuma linha no banco de dados
                }
        } catch (SQLException e)  {
            throw new DbException(e.getMessage());//lançamento de uma exceção personalizada, caso ocorra um erro de SQL durante a execução do comando
        } finally {
            DB.closeStatement(st);
        }
    }  
    

    @Override
    public void update(Seller obj) {
        PreparedStatement st = null;

        try {
            st = conn.prepareStatement(//
                "UPDATE seller "
                + "SET Name = ?, Email = ?, BirthDate = ?, BaseSalary = ?, DepartmentId = ? "
                + "WHERE Id = ?",       
                PreparedStatement.RETURN_GENERATED_KEYS);//opção para retornar as chaves geradas automaticamente pelo banco de dados, como o id do novo vendedor);
                
                st.setString(1, obj.getName());
                st.setString(2, obj.getEmail());
                st.setDate(3, new java.sql.Date(obj.getBirthDate().getTime()));//conversão de java.util.Date para java.sql.Date, necessária para armazenar a data no banco de dados
                st.setDouble(4, obj.getBaseSalary());
                st.setInt(5, obj.getDepartment().getId());
                st.setInt(6,obj.getId());

                st.executeUpdate();
                
            } catch (SQLException e)  {
            throw new DbException(e.getMessage());//lançamento de uma exceção personalizada, caso ocorra um erro de SQL durante a execução do comando
            } finally {
            DB.closeStatement(st);
            }
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
        dep.setName(rs.getString("DepName"));
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
    public List<Seller> findAll() throws SQLException {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                "SELECT seller.*, department.Name as DepName "
                + "FROM seller INNER JOIN department "
                + "ON seller.DepartmentId = department.Id "
                + "ORDER BY Name"
            );
            
            rs = st.executeQuery();

            List<Seller> list = new ArrayList<>();

            Map<Integer, Department> map = new HashMap<>();

            while (rs.next()){
                Department dep = map.get(rs.getInt("DepartmentId"));
                if (dep == null) {
                    dep = instantiateDepartment(rs);
                    map.put(rs.getInt("DepartmentId"), dep);
                }
                Seller obj = instantiateSeller(rs, dep);
                list.add(obj);
                
            } return list;
        } finally {
            DB.closeResultSet(rs);
            DB.closeStatement(st);
        }
    }

    @Override
    public List<Seller> findByDepartment(Department department) throws SQLException{
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                "SELECT seller.*, department.Name as DepName "
                + "FROM seller INNER JOIN department "
                + "ON seller.DepartmentId = department.Id "
                + "WHERE DepartmentId = ? "
                + "ORDER BY Name"
            );
            st.setInt(1, department.getId());
            rs = st.executeQuery();

            List<Seller> list = new ArrayList<>();

            Map<Integer, Department> map = new HashMap<>();

            while (rs.next()){
                Department dep = map.get(rs.getInt("DepartmentId"));
                if (dep == null) {
                    dep = instantiateDepartment(rs);
                    map.put(rs.getInt("DepartmentId"), dep);
                }
                Seller obj = instantiateSeller(rs, dep);
                list.add(obj);
                
            }return list;
        }finally {
            DB.closeResultSet(rs);
            DB.closeStatement(st);
        }    
    }
}

