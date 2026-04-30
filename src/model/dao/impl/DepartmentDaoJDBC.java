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
import model.entities.Department;

public class DepartmentDaoJDBC {
    private Connection conn;

    public DepartmentDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    public void insert(Department obj) {//método insert() para inserir um novo departamento no banco de dados, usando um PreparedStatement para executar o comando SQL com os parâmetros do objeto Department
        PreparedStatement st = null;

        try {
            st = conn.prepareStatement("INSERT INTO department (Name) VALUES (?)", PreparedStatement.RETURN_GENERATED_KEYS);
                
            st.setString(1, obj.getName());
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
    

    
    public void update(Department obj) {
        PreparedStatement st = null;

        try {
            st = conn.prepareStatement("UPDATE department SET Name = ? WHERE Id = ? ");//opção para retornar as chaves geradas automaticamente pelo banco de dados, como o id do novo vendedor);
                
                st.setString(1, obj.getName());
                st.setInt(2, obj.getId());

                st.executeUpdate();
                
            } catch (SQLException e)  {
            throw new DbException(e.getMessage());//lançamento de uma exceção personalizada, caso ocorra um erro de SQL durante a execução do comando
            } finally {
            DB.closeStatement(st);
            }
    }

    public void deleteById(Integer id) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement("DELETE FROM department WHERE Id = ?");
            st.setInt(1, id);
            st.executeUpdate();
            }catch (SQLException e) {
                throw new DbException(e.getMessage());
            }finally {
                DB.closeStatement(st);}

    }

    
    public Department findById(Integer id) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement("SELECT * FROM department WHERE Id = ? ");//PreparedStatement é usado para executar comandos SQL com parâmetros
            st.setInt(1, id);//setInt() para int, setString() para string, setDate() para date, etc
                    rs = st.executeQuery();//executeQuery() para consultas, executeUpdate() para insert, update e delete
                    if(rs.next()) {
                        Department dep = instantiateDepartment(rs);//instanciar um objeto Department usando os dados do ResultSet
                        return dep;
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
        dep.setId(rs.getInt("Id"));
        dep.setName(rs.getString("Name"));
        return dep;
    }

    public List<Department> findAll() throws SQLException {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement("SELECT * FROM department ORDER BY Name");
            rs = st.executeQuery();

            List<Department> list = new ArrayList<>();

            Map<String, Department> map = new HashMap<>();

            while (rs.next()){
                Department dep = map.get(rs.getString("Name"));
                if (dep == null) {
                    dep = instantiateDepartment(rs);
                    map.put(rs.getString("Name"), dep);
                }
                Department obj = instantiateDepartment(rs);
                list.add(obj);
                
            } return list;
        } finally {
            DB.closeResultSet(rs);
            DB.closeStatement(st);
        }
    }

    public List<Department> findByName(Department name) throws SQLException{
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement("SELECT * FROM department WHERE Name = ?");
            st.setString(1, name.getName());
            rs = st.executeQuery();

            List<Department> list = new ArrayList<>();

            Map<String, Department> map = new HashMap<>();

            while (rs.next()){
                Department dep = map.get(rs.getString("Name"));
                if (dep == null) {
                    dep = instantiateDepartment(rs);
                    map.put(rs.getString("Name"), dep);
                }
                Department obj = instantiateDepartment(rs);
                list.add(obj);
                
            }return list;
        }finally {
            DB.closeResultSet(rs);
            DB.closeStatement(st);
        }    
    }
}


