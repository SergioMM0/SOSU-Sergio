package DAL.Manager1;

import BE.School;
import DAL.DataAccess.DataAccess;
import DAL.DataAccess.JDBCConnectionPool;
import DAL.util.DalException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DAOSchool {

    private final JDBCConnectionPool dataAccess;

    public DAOSchool() {
        dataAccess = new JDBCConnectionPool();
    }


    public List<School> getAllSchhol()throws DalException {
        ArrayList<School> getAllSchools = new ArrayList<>();
        try(Connection con = dataAccess.getConnection()){
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
          throw new DalException("Couldn't retrieve a list of schools " ,e);
        }
    }


    public void createSchool(School school) throws DalException{
        try(Connection con = dataAccess.getConnection()) {
            String sql = "insert  into [dbo].[School] (name ) values  (?)";
            PreparedStatement prs = con.prepareStatement(sql);
            prs.setString(1 , school.getName());
            prs.execute();

        } catch (SQLException e) {
           throw new DalException("Couldnot create a school " , e );
        }

    }


    public void updateSchool(School school )throws DalException {
      try(Connection con = dataAccess.getConnection()) {
        String sql = "UPDATE School SET name = ?  where id = ? ";
        PreparedStatement prs = con.prepareStatement(sql);
        prs.setString(1, school.getName() );
        prs.setInt(2 , school.getId());
        prs.executeUpdate();
      } catch (SQLException e) {
        throw new DalException("couldnot update school at this moment please try again later " , e );
      }
    }


    public void deleteSchool(School school ) throws DalException{
    try(Connection con = dataAccess.getConnection()) {
        String sql ="Delete from School where id = ?";
        PreparedStatement prs = con.prepareStatement(sql);
        prs.setInt(1 , school.getId() );
    } catch (SQLException e) {
      throw new DalException("Couldnot delete this school at this moment " , e);
    }
    }
}
