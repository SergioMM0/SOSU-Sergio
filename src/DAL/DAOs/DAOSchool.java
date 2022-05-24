package DAL.DAOs;

import BE.School;
import DAL.DataAccess.ConnectionProvider;
import DAL.DataAccess.JDBCConnectionPool;
import DAL.Exceptions.DALException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DAOSchool {

    private final ConnectionProvider connectionProvider;

    public DAOSchool() {
        connectionProvider = new ConnectionProvider();
    }


    public List<School> getAllSchhol()throws DALException {
        ArrayList<School> getAllSchools = new ArrayList<>();
        try(Connection con = connectionProvider.getConnection()){
            String sql = "Select * from School ";
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while(rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("name");
                School school = new School(id , name );
                getAllSchools.add(school);
            }
            return getAllSchools ;
        } catch (SQLException e) {
          throw new DALException("Couldn't retrieve a list of schools " ,e);
        }
    }


    public void createSchool(School school) throws DALException {
        try(Connection con = connectionProvider.getConnection()) {
            String sql = "insert  into [dbo].[School] (name ) values  (?)";
            PreparedStatement prs = con.prepareStatement(sql);
            prs.setString(1 , school.getName());
            prs.execute();

        } catch (SQLException e) {
           throw new DALException("Couldnot create a school " , e );
        }

    }


    public void updateSchool(School school )throws DALException {
      try(Connection con = connectionProvider.getConnection()) {
        String sql = "UPDATE School SET name = ?  where id = ? ";
        PreparedStatement prs = con.prepareStatement(sql);
        prs.setString(1, school.getName() );
        prs.setInt(2 , school.getId());
        prs.executeUpdate();
      } catch (SQLException e) {
        throw new DALException("couldnot update school at this moment please try again later " , e );
      }
    }


    public void deleteSchool(School school ) throws DALException {
    try(Connection con = connectionProvider.getConnection()) {
        String sql ="Delete from School where id = ?";
        PreparedStatement prs = con.prepareStatement(sql);
        prs.setInt(1 , school.getId() );
    } catch (SQLException e) {
      throw new DALException("Couldnot delete this school at this moment " , e);
    }
    }
}
