package DAL.DAOs;

import BE.School;
import DAL.DataAccess.ConnectionProvider;
import DAL.Exceptions.DALException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DAOSchool {

    private final ConnectionProvider connectionProvider;

    public DAOSchool() {
        connectionProvider = new ConnectionProvider();
    }

    public List<School> getAllSchools() throws DALException {
        List<School> allSchools = new ArrayList<>();
        try(Connection connection = connectionProvider.getConnection()){
            String sql = "SELECT * FROM [School]";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.execute();
            ResultSet rs = statement.getResultSet();
            while(rs.next()){
                allSchools.add(new School(
                        rs.getInt("ID"),
                        rs.getString("Name")
                ));
            }
        }catch (SQLException sqlException){
            throw new DALException("Not able to get all the schools", sqlException);
        }
        return allSchools;
    }

    public School addSchool(School currentSchool) throws DALException{
        try(Connection connection = connectionProvider.getConnection()){
            String sql = "INSERT INTO [School] ([Name]) VALUES (?);";
            String sql2 = "SELECT * FROM [School] WHERE [Name] = ?";

            PreparedStatement st = connection.prepareStatement(sql);
            PreparedStatement st2 = connection.prepareStatement(sql2);
            st.setString(1,currentSchool.getName());
            st.execute();

            st2.setString(1,currentSchool.getName());
            st2.execute();
            ResultSet rs = st2.getResultSet();
            while(rs.next()){
                currentSchool.setId(rs.getInt("ID"));
            }
        }catch (SQLException sqlException){
            throw new DALException("Not able to create the school", sqlException);
        }
        return currentSchool;
    }

    public void deleteSchool(School currentSchool) throws DALException {
        try(Connection connection = connectionProvider.getConnection()){
            String sql = "DELETE FROM [School] WHERE [ID] = ?";
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, currentSchool.getId());
            st.execute();
        }catch (SQLException sqlException){
            throw new DALException("Not able to delete the school", sqlException);
        }
    }

    public School updateSchool(School currenSchool) throws DALException {
        try(Connection connection = connectionProvider.getConnection()){
            String sql = "UPDATE [School] SET [Name] = ? WHERE [ID] = ?";
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1,currenSchool.getName());
            st.setInt(2, currenSchool.getId());
            st.execute();
        }catch (SQLException sqlException){
            throw new DALException("Not able to update the school", sqlException);
        }
        return currenSchool;
    }
}
