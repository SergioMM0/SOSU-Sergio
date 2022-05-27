package DAL.DAOs;

import BE.Case;
import BE.Group;
import BE.Patient;
import DAL.DataAccess.ConnectionProvider;
import DAL.Util.CopyChecker;
import DAL.Exceptions.DALException;

import javax.xml.transform.Result;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DAOCase {

    private final ConnectionProvider connectionProvider;
    private final int isFalse = 0;
    private final int isTrue = 1;

    public DAOCase() {
        connectionProvider = new ConnectionProvider();
    }

    public List<Case> getAllCases(int SchoolID) throws DALException {
        ArrayList<Case> allCases = new ArrayList<>();
        try(Connection connection = connectionProvider.getConnection()){
            String sql = "SELECT * FROM [Cases] WHERE [Schoolid]  = ? AND [IsCopy] = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, SchoolID);
            statement.setInt(2, isFalse);
            statement.execute();
            ResultSet rs = statement.getResultSet();
            while(rs.next()){
                allCases.add(new Case(
                        rs.getInt("ID"),
                        rs.getString("Name"),
                        rs.getString("Description"),
                        rs.getInt("Schoolid"),
                        CopyChecker.checkIfCopy(rs.getInt("isCopy")
                )));
            }
            return allCases;
        } catch (SQLException sqlException) {
            throw new DALException("Not able to retrieve all the cases", sqlException);
        }
    }

    public List<Case> getCasesAssignedTo(Group group)throws DALException {
        ArrayList<Case> listOfCases = new ArrayList<>();
        try(Connection con = connectionProvider.getConnection()) {

            String sql = "SELECT a.[ID], a.[Description], a.[Name], a.[schoolid] , b.graded " +
                    "FROM [Cases] AS a INNER JOIN [AssignedCases] AS b ON a.[ID] = b.[Caseid] WHERE b.[Groupid] = ? AND a.[isCopy] = ? AND b.[Graded] = ?";

            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1 , group.getId());
            statement.setInt(2, isTrue);
            statement.setInt(3, isFalse);
            statement.execute();

            ResultSet rs = statement.getResultSet();
            while (rs.next()){
                listOfCases.add(new Case(rs.getInt("ID"),
                        rs.getString("Name"),
                        rs.getString("Description"),
                        rs.getInt("SchoolID")
                        ));
            }
            return listOfCases;
        } catch (SQLException sqlException) {
           throw new DALException("Not able to retrieve the cases assigned to the group", sqlException);
        }
    }

    public void assignCaseToGroup(Patient patient, Case assignedCase, Group group) throws DALException {
        try(Connection con = connectionProvider.getConnection()) {

            String sql = "INSERT INTO [Cases] ([Description],[Name],[schoolid],[isCopy]) VALUES (?,?,?,?)";
            String sql2 = "SELECT [ID] FROM [Cases] WHERE [Name] = ? AND [isCopy] = ?";
            String sql3 = "INSERT INTO [Patient] (FirstName, LastName, DateofBirth, Gender, Schoolid, IsCopy) VALUES (?,?,?,?,?,?)";
            String sql4 = "SELECT [ID] FROM [Patient] WHERE [FirstName] = ? AND [isCopy] = ?";
            String sql5 = "INSERT INTO [AssignedCases] (PatientID, Caseid , Groupid, Graded) VALUES (?,?,?,?)";

            PreparedStatement statement1 = con.prepareStatement(sql);
            PreparedStatement statement2 = con.prepareStatement(sql2);
            PreparedStatement statement3 = con.prepareStatement(sql3);
            PreparedStatement statement4 = con.prepareStatement(sql4);
            PreparedStatement statement5 = con.prepareStatement(sql5);

            statement1.setString(1,assignedCase.getConditionDescription());
            statement1.setString(2,assignedCase.getName());
            statement1.setInt(3, assignedCase.getSchoolID());
            statement1.setInt(4, assignedCase.getIsCopyDB());
            statement1.execute();

            statement2.setString(1,assignedCase.getName());
            statement2.setInt(2,assignedCase.getIsCopyDB());
            statement2.execute();
            ResultSet rs = statement2.getResultSet();
            while(rs.next()){
                assignedCase.setId(rs.getInt("id"));
            }

            statement3.setString(1,patient.getFirst_name());
            statement3.setString(2,patient.getLast_name());
            statement3.setDate(3, Date.valueOf(patient.getDateOfBirth()));
            statement3.setString(4 , patient.getGender());
            statement3.setInt(5,patient.getSchoolId());
            statement3.setInt(6,patient.getIsCopyDB());
            statement3.execute();

            statement4.setString(1, patient.getFirst_name());
            statement4.setInt(2,patient.getIsCopyDB());
            statement4.execute();
            ResultSet rs2 = statement4.getResultSet();
            while(rs2.next()){
                patient.setId(rs2.getInt("id"));
            }

            statement5.setInt(1, patient.getId());
            statement5.setInt(2, assignedCase.getId());
            statement5.setInt(3, group.getId());
            statement5.setInt(4, isFalse);
            statement5.execute();

        } catch (SQLException sqlException) {
            throw new DALException("Not able to assign the case to the group" , sqlException);
        }
    }


    public Case createCase(Case newCase) throws DALException {
        try(Connection con = connectionProvider.getConnection()) {
            String sql = "INSERT INTO [Cases] ( [Name] ,[Description],[schoolid], isCopy) " +
                    "VALUES (?,?,?,?);" ;
            String sql2 = "SELECT [ID] FROM [Cases] WHERE [Name] = ? AND [Description] = ? AND [schoolid] = ?";

            PreparedStatement statement1 = con.prepareStatement(sql);
            PreparedStatement statement2 = con.prepareStatement(sql2);

            statement1.setString(1, newCase.getName());
            statement1.setString(2, newCase.getConditionDescription());
            statement1.setInt(3, newCase.getSchoolID());
            statement1.setInt(4, newCase.getIsCopyDB());
            statement1.execute();

            statement2.setString(1,newCase.getName());
            statement2.setString(2,newCase.getConditionDescription());
            statement2.setInt(3,newCase.getSchoolID());
            statement2.execute();

            ResultSet rs = statement2.getResultSet();
            while(rs.next()){
                newCase.setId(rs.getInt("id"));
            }
            return newCase;
        } catch (SQLException sqlException) {
            throw new DALException("Not able to create the case" , sqlException);
        }
    }

    public void updateCase(Case updatedCase) throws DALException {
        try(Connection con = connectionProvider.getConnection()){
            String sql = "UPDATE [Cases] SET [Name] = ? , [Description] = ? WHERE [ID] = ?";

            PreparedStatement statement = con.prepareStatement(sql);

            statement.setString(1, updatedCase.getName());
            statement.setString(2,updatedCase.getConditionDescription());
            statement.setInt(3, updatedCase.getId());
            statement.executeUpdate();

        } catch (SQLException sqlException) {
            throw new DALException("Not able to update the case", sqlException);
        }
    }


    public void deleteCase(Case c) throws DALException {
        try(Connection con = connectionProvider.getConnection()){
            String sql = " DELETE FROM [Cases] WHERE [ID] = ?";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1 , c.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DALException("Not able to delete the case" , e);
        }
    }

    public void unassignCase(Case selectedItem) throws DALException {
        try(Connection connection = connectionProvider.getConnection()){
            String sql = "DELETE FROM [AssignedCases] WHERE [Caseid] = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1,selectedItem.getId());
            statement.execute();
        }catch (SQLException sqlException){
            throw new DALException("Not able to unassign the case", sqlException);
        }
    }

    public void markCaseAsGraded(Case selectedItem) throws DALException {
        try(Connection connection = connectionProvider.getConnection()){
            String sql = "UPDATE [AssignedCases] SET [Graded] = ? WHERE [Caseid] = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1,isTrue);
            statement.setInt(2,selectedItem.getId());
            statement.execute();
        }catch (SQLException sqlException){
            throw new DALException("Not able to mark the case as graded", sqlException);
        }
    }

    public void unmarkCaseAsGraded(Case selectedItem) throws DALException {
        try(Connection connection = connectionProvider.getConnection()){
            String sql = "UPDATE [AssignedCases] SET [Graded] = ? WHERE [Caseid] = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, isFalse);
            statement.setInt(2,selectedItem.getId());
            statement.execute();
        }catch (SQLException sqlException){
            throw new DALException("Not able to unmark as graded the case", sqlException);
        }
    }

    public List<Case> getCasesGradedOf(Group group) throws DALException {
        ArrayList<Case> listOfGradedCases = new ArrayList<>();
        try(Connection con = connectionProvider.getConnection()) {
            String sql = "SELECT a.[ID], a.[Description], a.[Name], a.[schoolid]" +
                    "FROM [Cases] AS a INNER JOIN [AssignedCases] AS b ON a.[ID] = b.[Caseid] WHERE b.[Groupid] = ? AND a.[isCopy] = ? AND b.[graded] = ?";
            PreparedStatement prs = con.prepareStatement(sql);
            prs.setInt(1 , group.getId());
            prs.setInt(2, isTrue);
            prs.setInt(3, isTrue);
            prs.execute();
            ResultSet rs = prs.getResultSet();
            while (rs.next()){
                listOfGradedCases.add(new Case(
                        rs.getInt("ID"),
                        rs.getString("Name"),
                        rs.getString("Description"),
                        rs.getInt("schoolid")
                        ));
            }
            return listOfGradedCases;
        } catch (SQLException sqlException) {
            throw new DALException("Not able to get the cases graded for the group" , sqlException);
        }
    }

    public Case duplicateCase(Case currentCase) throws DALException{
        try(Connection connection = connectionProvider.getConnection()){
            String checkName = "SELECT [NAME] FROM [Cases] WHERE [Name] = ?";
            String sql = "INSERT INTO [Cases] ([Name], [Description],[schoolid],[isCopy]) VALUES (?,?,?,?)";
            String sql2 = "SELECT [ID] FROM [Cases] WHERE [Name] = ?";

            PreparedStatement safeCopy = connection.prepareStatement(checkName);
            PreparedStatement st = connection.prepareStatement(sql);
            PreparedStatement st2 = connection.prepareStatement(sql2);

            safeCopy.setString(1,currentCase.getName());
            safeCopy.execute();
            ResultSet rs0 = safeCopy.getResultSet();
            if(rs0.next()){
                currentCase = null;
                return currentCase;
            }

            st.setString(1,currentCase.getName());
            st.setString(2,currentCase.getConditionDescription());
            st.setInt(3,currentCase.getSchoolID());
            st.setInt(4,currentCase.getIsCopyDB());
            st.execute();

            st2.setString(1,currentCase.getName());
            st2.execute();
            ResultSet rs = st2.getResultSet();
            while(rs.next()){
                currentCase.setId(rs.getInt("ID"));
            }
        }catch (SQLException sqlException){
            throw new DALException("Not able to duplicate the case", sqlException);
        }
        return currentCase;
    }
}
