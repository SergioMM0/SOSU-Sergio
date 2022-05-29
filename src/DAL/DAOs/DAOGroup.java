package DAL.DAOs;

import BE.Group;
import BE.User;
import DAL.DataAccess.ConnectionProvider;
import DAL.Exceptions.DALException;

import java.security.InvalidParameterException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DAOGroup {

    private final ConnectionProvider connectionProvider;

    public DAOGroup(){
        connectionProvider = new ConnectionProvider();
    }


    public List<Group> getAllGroups(int schoolID) throws DALException {
        ArrayList<Group> listOfGroups = new ArrayList<>();

        try(Connection connection = connectionProvider.getConnection()) {
            String sql = "SELECT * FROM [Groups] WHERE [Schoolid] = ?";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1 , schoolID);
            statement.execute();

            ResultSet rs = statement.getResultSet();
            while(rs.next()){
                listOfGroups.add(
                        new Group(
                                rs.getInt("ID"),
                                rs.getString("Name"),
                                new ArrayList<>(),
                                schoolID));
            }
            return listOfGroups;
        } catch (SQLException sqlException) {
           throw new DALException("Not able to retrieve the groups", sqlException);
        }
    }


    public Group createGroup(Group group)throws DALException, InvalidParameterException {
        try(Connection connection = connectionProvider.getConnection()) {
            duplicateCheck(connection,group);

            String sql = "INSERT INTO Groups ([Name],[Schoolid]) VALUES (?,?)";
            String sql2 = "SELECT [ID] FROM [Groups] WHERE [Name] = ?";

            PreparedStatement prs = connection.prepareStatement(sql);
            PreparedStatement prs2 = connection.prepareStatement(sql2);

            prs.setString(1 ,group.getName());
            prs.setInt(2 ,group.getSchoolId());
            prs.executeUpdate();

            prs2.setString(1,group.getName());
            prs2.execute();

            ResultSet rs = prs2.getResultSet();
            while(rs.next()){
                group.setId(rs.getInt("id"));
            }

            return group;
        } catch (SQLException sqlException) {
            throw new DALException("Not able to create the group", sqlException );
        }
    }


    public void updateGroup(Group group) throws DALException, InvalidParameterException {
        try(Connection connection = connectionProvider.getConnection()){
            duplicateCheck(connection,group);
            String sql = "UPDATE [Groups] SET [Name] = ?  WHERE [ID] = ?";
            PreparedStatement prs = connection.prepareStatement(sql);
            prs.setString(1, group.getName());
            prs.setInt(2, group.getId());
            prs.executeUpdate();
        } catch (SQLException sqlException) {
           throw new DALException("Not able to update the group", sqlException);
        }
    }


    public void deleteGroup(Group group) throws DALException {
        try(Connection connection = connectionProvider.getConnection()) {
            String sql = "DELETE FROM [Groups] WHERE [ID] = ?";
            PreparedStatement prs =  connection.prepareStatement(sql);
            prs.setInt(1, group.getId());
            prs.executeUpdate();
        } catch (SQLException sqlException) {
            throw new DALException("Not able to delete the group", sqlException);
        }
    }


    public List<User> getUsersInGroup(int id) throws DALException {
        ArrayList<User> listOfUsersInGroup = new ArrayList<>();
        try(Connection con = connectionProvider.getConnection()) {

            String sql = "SELECT a.[ID] , a.[Username] , a.[Email] , a.[Usertype] , a.[Schoolid] FROM [Users] as a " +
                    "INNER JOIN [UsersInGroup] as b "+
                    "ON a.[ID] = b.[StudentID] " +
                    "WHERE b.[Groupid] = ?";

            PreparedStatement prs = con.prepareStatement(sql);
            prs.setInt(1, id);
            prs.execute();
            ResultSet rs = prs.getResultSet();
            while(rs.next()){
                listOfUsersInGroup.add(new User(
                        rs.getInt("ID"),
                        rs.getInt("Schoolid"),
                        rs.getString("Username"),
                        rs.getString("Email"),
                        rs.getInt("Usertype")
                        ));
            }
            return listOfUsersInGroup;
        } catch (SQLException sqlException) {
           throw new DALException("Not able to retrieve the users in this group" , sqlException);
        }
    }


    public void addUsertoGroup(Group group, User user) throws DALException {
        try(Connection con = connectionProvider.getConnection()) {
            String sql = "INSERT INTO [UsersInGroup] ([Studentid],[Groupid]) VALUES (?,?)";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1, user.getId());
            statement.setInt(2, group.getId());
            statement.executeUpdate();
        } catch (SQLException sqlException) {
           throw new DALException("Not able to add the user to the group" , sqlException);
        }
    }

    public void removeParticipant(User user, Group group)throws DALException {
        try(Connection con = connectionProvider.getConnection()) {
            String sql = "DELETE FROM [UsersInGroup] WHERE [Studentid] = ? AND [Groupid] = ?";
            PreparedStatement prs = con.prepareStatement(sql);
            prs.setInt(1, user.getId());
            prs.setInt(2, group.getId());
            prs.execute();
        } catch (SQLException e) {
           throw new DALException("Not able to remove the user in the group" , e);
        }
    }

    public Group getGroupOf(User student) throws DALException {
        Group group = null;
        try(Connection con = connectionProvider.getConnection()){
            String sql = "SELECT g.[ID], g.[Name], g.[Schoolid] FROM [Groups] g LEFT JOIN [UsersInGroup] gu on g.[ID] = gu.[Groupid] WHERE gu.[Studentid] = ?";
            PreparedStatement st = con.prepareStatement(sql);
            st.setInt(1,student.getId());
            st.execute();
            ResultSet rs = st.getResultSet();
            while(rs.next()){
                group = new Group(rs.getInt("id"), rs.getString("name"), rs.getInt("Schoolid"));
            }
            if (group != null) {
                group.setMembers(getUsersInGroup(group.getId()));
            }
        }catch (SQLException sqlException){
            throw new DALException("Not able to get the group of the student", sqlException);
        }
        return group;
    }

    private void duplicateCheck(Connection connection,Group group) throws SQLException, InvalidParameterException {
        String duplicateCheck = "SELECT [Name] FROM [Groups] WHERE [Name] = ?";
        PreparedStatement safeInsert = connection.prepareStatement(duplicateCheck);
        safeInsert.setString(1,group.getName());
        safeInsert.execute();
        ResultSet rs0 = safeInsert.getResultSet();
        if(rs0.next()){
            throw new InvalidParameterException("The group already exists");
        }
    }
}
