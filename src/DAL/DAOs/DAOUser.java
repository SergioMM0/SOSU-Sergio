package DAL.DAOs;

import BE.School;
import BE.User;
import DAL.DataAccess.ConnectionProvider;
import DAL.Exceptions.DALException;
import GUI.Util.StaticData;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DAOUser {

    private final ConnectionProvider connectionProvider;

    public DAOUser() {
        connectionProvider = new ConnectionProvider();
    }


    public User checkCredentials(String useremail, String password) throws DALException {
        try (Connection con = connectionProvider.getConnection()) {
            String sql = " SELECT * FROM [Users] WHERE [Email] = ? AND [Password] = ?";
            PreparedStatement prs = con.prepareStatement(sql);
            prs.setString(1, useremail);
            prs.setString(2, password);
            ResultSet rs = prs.executeQuery();
            while (rs.next()) {
                return new User(
                        rs.getInt("ID"),
                        rs.getInt("Schoolid"),
                        rs.getString("Username"),
                        rs.getString("Email"),
                        rs.getInt("Usertype")
                        );
            }
        } catch (SQLException sqlException) {
            throw new DALException("Not able to verify the user", sqlException);
        }
        return null;
    }

    public List<User> getAllStudents(int schoolId) throws DALException {
        ArrayList<User> allStudents = new ArrayList<>();
        try (Connection con = connectionProvider.getConnection()) {
            String sql = "SELECT * FROM [Users] WHERE [Schoolid] = ? And [Usertype] = ?";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1, schoolId);
            statement.setInt(2, StaticData.getStudentType());
            statement.execute();
            ResultSet rs = statement.getResultSet();
            while (rs.next()) {
                allStudents.add(new User(
                        rs.getInt("ID"),
                        rs.getInt("Schoolid"),
                        rs.getString("username"),
                        rs.getString("email"),
                        rs.getInt("Usertype")
                ));
            }
        } catch (SQLException sqlException) {
            throw new DALException("Not able to retrieve all the students", sqlException);
        }
        return allStudents;
    }

    public void updateUser(User student) throws DALException {
        try (Connection con = connectionProvider.getConnection()) {
            String sql = "UPDATE [Users] SET [Username] = ?, [Password] = ?, [Email] = ? WHERE [ID] = ? ";
            PreparedStatement prs = con.prepareStatement(sql);
            prs.setString(1, student.getName());
            prs.setString(2, student.getName());
            prs.setString(3, student.getEmail());
            prs.setInt(4, student.getId());
            prs.execute();
        } catch (SQLException sqlException) {
            throw new DALException("Not able to update the student", sqlException);
        }
    }

    public void deleteUser(User user) throws DALException {
        try (Connection con = connectionProvider.getConnection()) {
            String sql = "DELETE FROM [Users] WHERE [ID] = ?";
            PreparedStatement prs = con.prepareStatement(sql);
            prs.setInt(1, user.getId());
            prs.executeUpdate();
        } catch (SQLException sqlException) {
            throw new DALException("Not able to delete the user", sqlException);
        }
    }

    public User addUser(User user) throws DALException {
        try (Connection con = connectionProvider.getConnection()) {
            String sql = "INSERT INTO [Users]([Username] , [Password], [Email] , [Usertype] , [Schoolid])" +
                    "VALUES  (?,?,?,?,?)";
            String sql2 = "SELECT [ID] FROM [Users] WHERE [Username] = ? AND [Password] = ? AND [Email] = ? AND [Usertype] = ? AND [schoolid] = ?";
            PreparedStatement prs = con.prepareStatement(sql);
            PreparedStatement prs2 = con.prepareStatement(sql2);
            prs.setString(1, user.getName());
            prs.setString(2, user.getName());
            prs.setString(3, user.getEmail());
            prs.setInt(4, user.getUserType());
            prs.setInt(5, user.getSchoolID());
            prs.executeUpdate();

            prs2.setString(1, user.getName());
            prs2.setString(2, user.getName());
            prs2.setString(3, user.getEmail());
            prs2.setInt(4, user.getUserType());
            prs2.setInt(5, user.getSchoolID());
            prs2.execute();
            ResultSet rs = prs2.getResultSet();
            while(rs.next()){
                user.setId(rs.getInt("ID"));
            }
            return user;
        } catch (SQLException sqlException) {
            throw new DALException("Not able to add the user", sqlException);
        }
    }

    public List<User> getAllUsers(School currentSchool) throws DALException{
        List<User> allUsers = new ArrayList<>();
        try(Connection connection = connectionProvider.getConnection()){
            String sql = "SELECT * FROM [Users] WHERE [Schoolid] = ?";
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1,currentSchool.getId());
            st.execute();
            ResultSet rs = st.getResultSet();
            while(rs.next()){
                allUsers.add(new User (
                        rs.getInt("ID"),
                        rs.getInt("Schoolid"),
                        rs.getString("Username"),
                        rs.getString("Email"),
                        rs.getInt("Usertype")
                ));
            }
        }catch (SQLException sqlException){
            throw new DALException("Not able to retrieve all the users", sqlException);
        }
        return allUsers;
    }
}

