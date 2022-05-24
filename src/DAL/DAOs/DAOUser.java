package DAL.DAOs;

import BE.Group;
import BE.User;
import DAL.DataAccess.ConnectionProvider;
import DAL.Exceptions.DALException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DAOUser {

    private final ConnectionProvider dataAccess;
    private final int studentIdentifier = 3;

    public DAOUser() {
        dataAccess = new ConnectionProvider();
    }


    public User checkCredentials(String useremail, String password) throws DALException {
        try (Connection con = dataAccess.getConnection()) {
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
        try (Connection con = dataAccess.getConnection()) {
            String sql = "SELECT * FROM [Users] WHERE [Schoolid] = ? And [Usertype] = ?";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1, schoolId);
            statement.setInt(2, studentIdentifier);
            statement.execute();
            ResultSet rs = statement.getResultSet();
            while (rs.next()) {
                allStudents.add(new User(
                        rs.getInt("userid"),
                        rs.getInt("schoolId"),
                        rs.getString("username"),
                        rs.getString("email"),
                        rs.getInt("usertype")
                ));
            }
        } catch (SQLException sqlException) {
            throw new DALException("Not able to retrieve all the students", sqlException);
        }
        return allStudents;
    }

    public List<User> getAllUSERS(int schoolId, String utype) throws DALException {
        ArrayList<User> users = new ArrayList<>();
        try (Connection con = dataAccess.getConnection()) {
            String sql = "SELECT * FROM [Users] WHERE [Schoolid] = ? AND [Usertype] = ? ";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1, schoolId);
            statement.setString(2, utype);
            statement.execute();
            ResultSet rs = statement.getResultSet();
            while (rs.next()) {
                int id = rs.getInt("userid");
                String username = rs.getString("username");
                String email = rs.getString("email");
                int usertype = rs.getInt("usertype");
                int schoolid = rs.getInt("schoolId");
                User user = new User(id, schoolid, username, email, usertype);
                users.add(user);
            }
        } catch (SQLException e) {
            throw new DALException("Connection Lost ", e);
        }
        return users;
    }


    public void updateStudent(User student) throws DALException {
        try (Connection con = dataAccess.getConnection()) {
            String sql = "UPDATE [Users] SET [Username] = ?  , [Email] = ? WHERE [ID] = ? ";
            PreparedStatement prs = con.prepareStatement(sql);
            prs.setString(1, student.getName());
            prs.setString(2, student.getEmail());
            prs.setInt(3, student.getId());
            prs.executeUpdate();

        } catch (SQLException sqlException) {
            throw new DALException("Not able to update the student", sqlException);
        }
    }

    public void deleteStudent(User user) throws DALException {
        try (Connection con = dataAccess.getConnection()) {
            String sql = "DELETE FROM [Users] WHERE [ID] = ?";
            PreparedStatement prs = con.prepareStatement(sql);
            prs.setInt(1, user.getId());
            prs.executeUpdate();
        } catch (SQLException sqlException) {
            throw new DALException("Not able to delete the student", sqlException);
        }
    }

    public User addStudent(User user) throws DALException {
        try (Connection con = dataAccess.getConnection()) {
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
            throw new DALException("Not able to add the student", sqlException);
        }
    }

    public List<User> searchForUser(String query) throws DALException { //TODO NOT USED
        List<User> users = new ArrayList<>();

        try (Connection con = dataAccess.getConnection()) {
            String command = " IF (Select COUNT(users.userid) from users where users.username = ? ) = 0 BEGIN Select * from users where users.username = ? END ELSE BEGIN Select * from School where School.name = ? END;";
            PreparedStatement prs = con.prepareStatement(command);
            prs.setString(1, query);
            prs.setString(2, query);
            prs.setString(3, query);
            prs.execute();
            ResultSet rs = prs.getResultSet();
            while (rs.next()) {

                int id = rs.getInt("id");
                String username = rs.getString("username");
                String email = rs.getString("email");
                String usertype = rs.getString("usertype");

                User user = new User(id, 1, username, email, studentIdentifier);
                users.add(user);
            }

        } catch (SQLException e) {
            throw new DALException("Connection Lost ", e);
        }
        return users;
    }
}

