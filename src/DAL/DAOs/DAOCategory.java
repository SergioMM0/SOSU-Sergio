package DAL.DAOs;

import BE.Category;
import DAL.DataAccess.ConnectionProvider;
import DAL.Exceptions.DALException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DAOCategory {

    private final ConnectionProvider connectionProvider;

    public DAOCategory(){
        connectionProvider = new ConnectionProvider();
    }

    public List<Category> getAllCategoriesHC() throws DALException{
        List<Category> allCategoriesHC = new ArrayList<>();
        try(Connection connection = connectionProvider.getConnection()){
            String sql = "SELECT * FROM [CategoryHC]";
            PreparedStatement st = connection.prepareStatement(sql);
            st.execute();
            ResultSet rs = st.getResultSet();
            while(rs.next()){
                allCategoriesHC.add(new Category(
                   rs.getInt("ID"),
                   rs.getString("Name")
                ));
            }
        }catch (SQLException sqlException){
            throw new DALException("Not able to get all the categories", sqlException);
        }
        return allCategoriesHC;
    }

    public List<Category> getAllCategoriesFA() throws DALException{
        List<Category> allCategories = new ArrayList<>();
        try(Connection connection = connectionProvider.getConnection()){
            String sql = "SELECT * FROM [CategoryFA]";
            PreparedStatement st = connection.prepareStatement(sql);
            st.execute();
            ResultSet rs = st.getResultSet();
            while(rs.next()){
                allCategories.add(new Category(
                        rs.getInt("ID"),
                        rs.getString("Name")
                ));
            }
        }catch (SQLException sqlException){
            throw new DALException("Not able to get all the categories", sqlException);
        }
        return allCategories;
    }

    /*
         try(Connection connection = connectionProvider.getConnection()){
            String sql = "";
            PreparedStatement st = connection.prepareStatement(sql);

            st.execute();
            ResultSet rs = st.getResultSet();
            while(rs.next()){

            }
        }catch (SQLException sqlException){
            throw new DALException("Not able to get all the categories", sqlException);
        }
     */

}
