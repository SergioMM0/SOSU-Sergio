package DAL.Manager1;

import BE.Group;
import BE.User;
import DAL.DataAccess.DataAccess;
import DAL.util.DalException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DAOGroup {

    private final DataAccess dataAccess ;

    public DAOGroup(){
        dataAccess = new DataAccess();
    }


    public List<Group> getAllGroups(int schoolId) throws DalException{
        ArrayList<Group> getAllGruops = new ArrayList<>();
        try(Connection con = dataAccess.getConnection()) {
            String sql = "SELECT * FROM Groups where Schoolid = ?";
            PreparedStatement prs = con.prepareStatement(sql);
            prs.setInt(1 , schoolId);
            prs.execute();
            ResultSet rs = prs.getResultSet();
            while(rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int schoolID = rs.getInt("Schoolid");
                Group group = new Group(id , name , getUsersInGroup(id) , schoolID);
                getAllGruops.add(group);
            }

        } catch (SQLException e) {
           throw new DalException("couldn't retrieve the groups " , e );
        }
        return getAllGruops;
    }


    public Group createGroup(Group group)throws DalException {
        try(Connection con = dataAccess.getConnection()) {
            String sql = "INSERT INTO Groups (name , Schoolid  ) VALUES (? , ?)";
            String sql2 = "SELECT [id] FROM Groups WHERE NAME = ?";
            PreparedStatement prs = con.prepareStatement(sql);
            PreparedStatement prs2 = con.prepareStatement(sql2);
            prs.setString(1 , group.getName());
            prs.setInt(2 , group.getSchoolId());
            prs.executeUpdate();
            prs2.setString(1,group.getName());
            prs2.execute();
            ResultSet rs = prs2.getResultSet();
            while(rs.next()){
                group.setId(rs.getInt("id"));
            }
            return group;
        } catch (SQLException e) {
            throw new DalException("couldn't create new group " , e );
        }
    }


    public void updateGroup( Group group)throws DalException {
        try(Connection con = dataAccess.getConnection()){
            String sql = "UPDATE Groups SET name = ?  where id = ?";
            PreparedStatement prs = con.prepareStatement(sql);
            prs.setString(1 , group.getName() );
            prs.setInt(2 , group.getId());
            prs.executeUpdate();
        } catch (SQLException e) {
           throw new DalException("Couldn't create Group" , e);
        }
    }


    public void deleteGroup(Group group) throws DalException{
        try(Connection con = dataAccess.getConnection()) {
            String sql = "Delete from Groups where id = ?";
            PreparedStatement prs =  con.prepareStatement(sql);
            prs.setInt(1 , group.getId());
            prs.executeUpdate();

        } catch (SQLException e) {
            throw new DalException("Couldn't delete a Group" ,e);
        }

    }


    public List<User> getUsersInGroup(int id) throws DalException {
        ArrayList<User> usersinGroup = new ArrayList<>();

        try(Connection con = dataAccess.getConnection()) {
            String sql = "SELECT userid , username , email , usertype , schoolid FROM GroupUsers" +
                    " INNER JOIN users "+
                    "ON GroupUsers.studentid = userid WHERE GroupUsers.Groupid = ? ";
            PreparedStatement prs = con.prepareStatement(sql);
            prs.setInt(1, id);
            prs.execute();
            ResultSet rs = prs.getResultSet();
            while(rs.next()){
                int iD = rs.getInt("userid");
                String name = rs.getString("username");
                String email = rs.getString("email");
                String usertype = rs.getString("usertype");
                int schoolid = rs.getInt("schoolid");

                User user = new User(iD ,schoolid ,name ,email ,usertype);
                usersinGroup.add(user);
            }
            return usersinGroup;
        } catch (SQLException e) {
           throw new DalException("couldn't retrieve users in this group" , e);
        }
    }


    public void addUsertoGroup(Group group, User user) throws DalException {
        try(Connection con = dataAccess.getConnection()) {
            String sql = "INSERT INTO GroupUsers(studentid,Groupid) VALUES (?,?)";
            PreparedStatement prs = con.prepareStatement(sql);
            prs.setInt(1,user.getId());
            prs.setInt(2 , group.getId());
            prs.executeUpdate();
        } catch (SQLException e) {
           throw new DalException("couldn't add user to this groups " , e);
        }
    }


    public void removeUserFromGroup(User user) throws DalException {
        try (Connection con = dataAccess.getConnection()) {
            String sql = "DELETE FROM GroupUsers WHERE studentid = ? ";
            PreparedStatement prs = con.prepareStatement(sql);
            prs.setInt(1, user.getId());
            prs.executeUpdate();
        } catch (SQLException e) {
            throw new DalException("couldn't remove user from this groups " , e);
        }
    }

    public void removeUserAndGroup(User user , Group group)throws DalException{
        try(Connection con = dataAccess.getConnection()) {
            String sql = "DELETE  from GroupUsers where studentid = ? AND Groupid = ?";
            PreparedStatement prs = con.prepareStatement(sql);
            prs.setInt(1,user.getId());
            prs.setInt(2 , group.getId());
            prs.execute();
        } catch (SQLException e) {
           throw new DalException("Couldn't preform this task " , e);
        }
    }

    public Group getGroupOf(User student) throws DalException {
        Group group = null;
        try(Connection con = dataAccess.getConnection()){
            String sql = "SELECT g.id, g.name, g.Schoolid FROM [Groups] g LEFT JOIN GroupUsers gu on g.id = gu.[Groupid] WHERE gu.studentid = ?";
            PreparedStatement st = con.prepareStatement(sql);
            st.setInt(1,student.getId());
            st.execute();
            ResultSet rs = st.getResultSet();
            while(rs.next()){
                group = new Group(rs.getInt("id"), rs.getString("name"), rs.getInt("Schoolid"));
            }
        }catch (SQLException sqlException){
            throw new DalException("Not able to get the group of the student", sqlException);
        }
        return group;
    }
}
