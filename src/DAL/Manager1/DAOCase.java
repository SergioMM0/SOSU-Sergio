package DAL.Manager1;

import BE.Case;
import BE.Group;
import BE.Patient;
import DAL.DataAccess.DataAccess;
import DAL.DataAccess.JDBCConnectionPool;
import DAL.util.CopyChecker;
import DAL.util.DalException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DAOCase {

    private final JDBCConnectionPool dataAccess;
    private CopyChecker copyChecker;
    private final int isFalse = 0;
    private final int isTrue = 1;

    public DAOCase() {
        dataAccess = new JDBCConnectionPool();
        copyChecker = CopyChecker.getInstance();
    }

    public List<Case> getAllCases(int schoolid) throws DalException {
        ArrayList<Case> cases = new ArrayList<>();
        try(Connection connection = dataAccess.getConnection()){
            String sql = "SELECT * FROM [Case] WHERE [schoolid]  = ? AND [isCopy] = ?";
            PreparedStatement prs = connection.prepareStatement(sql);
            prs.setInt(1 , schoolid);
            prs.setInt(2,isFalse);
            prs.execute();
            ResultSet rs = prs.getResultSet();
            while(rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String description_of_the_condition = rs.getString("Description_of_the_condition");
                String category = rs.getString("CategoryName");
                String subcategory = rs.getString("SubCategoryName");
                int schoolID = rs.getInt("schoolid");
                boolean isCopy = copyChecker.checkIfCopy(rs.getInt("isCopy"));

                Case c = new Case(id,name,description_of_the_condition,category,subcategory,schoolID,isCopy);
                cases.add(c);
            }
            return cases;
        } catch (SQLException e) {
            throw new DalException("Connection Lost" , e);
        }
    }

    public List<Case> getCasesAssignedTo(Group group)throws DalException{
        ArrayList<Case> cases = new ArrayList<>();
        try(Connection con = dataAccess.getConnection()) {
            String sql = "SELECT a.id, a.Description_of_the_condition, a.CategoryName, a.SubCategoryName, a.name, a.schoolid , b.graded " +
                    "FROM [Case] AS a INNER JOIN SickPatient AS b ON a.id = b.caseid WHERE b.Groupid = ? AND a.[isCopy] = ? AND b.[graded] = ?";
            PreparedStatement prs = con.prepareStatement(sql);
            prs.setInt(1 , group.getId());
            prs.setInt(2, isTrue);
            prs.setInt(3,isFalse);
            prs.execute();
            ResultSet rs = prs.getResultSet();
            while (rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String condition = rs.getString("Description_of_the_condition");
                String cat = rs.getString("CategoryName");
                String subcat = rs.getString("SubCategoryName");
                int schoolid = rs.getInt("schoolid");
                Case c = new Case(id ,name ,condition , cat ,subcat , schoolid);
                cases.add(c);
            }
            return cases;
        } catch (SQLException e) {
           throw new DalException("Couldnot retrive list of cases from the database " , e);
        }
    }

    public void assignCaseToGroup(Patient patient, Case assignedCase, Group group) throws DalException {
        try(Connection con = dataAccess.getConnection()) {
            String sql = "INSERT INTO [Case] (Description_of_the_condition,CategoryName,SubCategoryName,[name],schoolid,isCopy) VALUES (?,?,?,?,?,?)";
            String sql2 = "SELECT [id] FROM [Case] WHERE [name] = ? AND [isCopy] = ?";
            String sql3 = "INSERT INTO [Patient] (first_name, last_name, dateofBirth, gender,weight ,height ,cpr , phone_number, schoolid, isCopy) VALUES (?,?,?,?,?,?,?,?,?,?)";
            String sql4 = "SELECT [id] FROM [Patient] WHERE [first_name] = ? AND [isCopy] = ?";
            String sql5 = "INSERT INTO SickPatient (patientid , caseid , Groupid, graded) VALUES (?,?,?,?)";
            PreparedStatement prs = con.prepareStatement(sql);
            PreparedStatement prs2 = con.prepareStatement(sql2);
            PreparedStatement prs3 = con.prepareStatement(sql3);
            PreparedStatement prs4 = con.prepareStatement(sql4);
            PreparedStatement prs5 = con.prepareStatement(sql5);
            prs.setString(1,assignedCase.getConditionDescription());
            prs.setString(2,assignedCase.getCategory());
            prs.setString(3,assignedCase.getSubCategory());
            prs.setString(4,assignedCase.getName());
            prs.setInt(5, assignedCase.getSchoolID());
            prs.setInt(6, assignedCase.getIsCopyDB());
            prs.execute();

            prs2.setString(1,assignedCase.getName());
            prs2.setInt(2,assignedCase.getIsCopyDB());
            prs2.execute();
            ResultSet rs = prs2.getResultSet();
            while(rs.next()){
                assignedCase.setId(rs.getInt("id"));
            }

            prs3.setString(1,patient.getFirst_name());
            prs3.setString(2,patient.getLast_name());
            prs3.setDate(3, Date.valueOf(patient.getDateOfBirth()));
            prs3.setString(4 , patient.getGender());
            prs3.setString(5 ,patient.getWeight());
            prs3.setString(6,patient.getHeight());
            prs3.setString(7 ,patient.getCpr());
            prs3.setString(8 , patient.getPhoneNumber());
            prs3.setInt(9,patient.getSchoolId());
            prs3.setInt(10,patient.getIsCopyDB());
            prs3.execute();

            prs4.setString(1, patient.getFirst_name());
            prs4.setInt(2,patient.getIsCopyDB());
            prs4.execute();
            ResultSet rs2 = prs4.getResultSet();
            while(rs2.next()){
                patient.setId(rs2.getInt("id"));
            }

            prs5.setInt(1, patient.getId());
            prs5.setInt(2, assignedCase.getId());
            prs5.setInt(3, group.getId());
            prs5.setInt(4, isFalse);
            prs5.execute();

        } catch (SQLException sqlException) {
            throw new DalException("Not able to assign the case to the group" , sqlException);
        }
    }


    public Case createCase(Case newCase) throws DalException {
        try(Connection con = dataAccess.getConnection()) {
            String sql = "INSERT INTO [dbo].[Case] ( name ,Description_of_the_condition,CategoryName , SubCategoryName ,schoolid, isCopy) " +
                    "VALUES (?,?,?,?,?,?);" ;
            String sql2 = "SELECT [id] FROM [dbo].[Case] WHERE [name] = ? AND [Description_of_the_condition] = ? AND [CategoryName] = ? AND [SubCategoryName] = ? AND [schoolid] = ?";
            PreparedStatement prs = con.prepareStatement(sql);
            PreparedStatement prs2 = con.prepareStatement(sql2);
            prs.setString(1 , newCase.getName());
            prs.setString(2 ,newCase.getConditionDescription());
            prs.setString(3,newCase.getCategory());
            prs.setString(4 , newCase.getSubCategory());
            prs.setInt(5 ,newCase.getSchoolID());
            prs.setInt(6,newCase.getIsCopyDB());
            prs.executeUpdate();
            prs2.setString(1,newCase.getName());
            prs2.setString(2,newCase.getConditionDescription());
            prs2.setString(3,newCase.getCategory());
            prs2.setString(4,newCase.getSubCategory());
            prs2.setInt(5,newCase.getSchoolID());
            prs2.execute();
            ResultSet rs = prs2.getResultSet();
            while(rs.next()){
                newCase.setId(rs.getInt("id"));
            }
            return newCase;
        } catch (SQLException e) {
            throw new DalException("Connection Lost" , e);
        }

    }


    public void updateCase(Case c) throws DalException {
        try(Connection con = dataAccess.getConnection()){
            String sql = "Update [Case] set name = ? , description_of_the_condition = ? , CategoryName = ? , SubCategoryName = ? " +
                    " where id = ? ";
            PreparedStatement prs = con.prepareStatement(sql);
            prs.setString(1 , c.getName());
            prs.setString(2 ,c.getConditionDescription());
            prs.setString(3 , c.getCategory());
            prs.setString(4 , c.getSubCategory());
            prs.setInt(5,c.getId());
            prs.executeUpdate();

        } catch (SQLException e) {
            throw new DalException("Connection Lost" , e);
        }
    }


    public void deleteCase(Case c) throws DalException {
        try(Connection con = dataAccess.getConnection()){
            String sql = " DELETE FROM [Case] WHERE id = ?";
            PreparedStatement prs = con.prepareStatement(sql);
            prs.setInt(1 , c.getId());
            prs.executeUpdate();
        } catch (SQLException e) {
            throw new DalException("Connection Lost" , e);

        }
    }

    public void unassignCase(Case selectedItem) throws DalException{
        try(Connection connection = dataAccess.getConnection()){
            String sql = "DELETE FROM [SickPatient] WHERE [caseid] = ?";
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1,selectedItem.getId());
            st.execute();
        }catch (SQLException sqlException){
            throw new DalException("Not able to unassign the case", sqlException);
        }
    }

    public void markCaseAsGraded(Case selectedItem) throws DalException {
        try(Connection connection = dataAccess.getConnection()){
            String sql = "UPDATE [SickPatient] SET [graded] = ? WHERE [caseid] = ?";
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1,isTrue);
            st.setInt(2,selectedItem.getId());
            st.execute();
        }catch (SQLException sqlException){
            throw new DalException("Not able to mark the case as graded", sqlException);
        }
    }

    public void unmarkCaseAsGraded(Case selectedItem) throws DalException{
        try(Connection connection = dataAccess.getConnection()){
            String sql = "UPDATE [SickPatient] SET [graded] = ? WHERE [caseid] = ?";
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, isFalse);
            st.setInt(2,selectedItem.getId());
            st.execute();
        }catch (SQLException sqlException){
            throw new DalException("Not able to unmark as graded the case", sqlException);
        }
    }

    public List<Case> getCasesGradedOf(Group group) throws DalException{
        ArrayList<Case> cases = new ArrayList<>();
        try(Connection con = dataAccess.getConnection()) {
            String sql = "SELECT a.id, a.Description_of_the_condition, a.CategoryName, a.SubCategoryName, a.name, a.schoolid , b.graded " +
                    "FROM [Case] AS a INNER JOIN SickPatient AS b ON a.id = b.caseid WHERE b.Groupid = ? AND a.[isCopy] = ? AND b.[graded] = ?";
            PreparedStatement prs = con.prepareStatement(sql);
            prs.setInt(1 , group.getId());
            prs.setInt(2, isTrue);
            prs.setInt(3, isTrue);
            prs.execute();
            ResultSet rs = prs.getResultSet();
            while (rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String condition = rs.getString("Description_of_the_condition");
                String cat = rs.getString("CategoryName");
                String subcat = rs.getString("SubCategoryName");
                int schoolid = rs.getInt("schoolid");
                Case c = new Case(id ,name ,condition , cat ,subcat , schoolid);
                cases.add(c);
            }
            return cases;
        } catch (SQLException e) {
            throw new DalException("Not able to get the cases graded for the group" , e);
        }
    }
}
