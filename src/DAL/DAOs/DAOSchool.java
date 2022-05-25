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

}
