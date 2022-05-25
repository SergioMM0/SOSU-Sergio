package DAL.DAOs;

import BE.Case;
import BE.Group;
import BE.Patient;
import DAL.DataAccess.ConnectionProvider;
import DAL.Util.CopyChecker;
import DAL.Exceptions.DALException;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DAOPatient {

    private final ConnectionProvider dataAccess;
    private final int isTrue = 1;
    private final int isFalse = 0;

    public DAOPatient() {
        dataAccess = new ConnectionProvider();
    }

    public List<Patient> getAllPatients(int schoolid) throws DALException {
        ArrayList<Patient> allPatients = new ArrayList<>();
        try(Connection con = dataAccess.getConnection()) {
            String sql = "SELECT * FROM [Patient] WHERE [Schoolid] = ? AND [isCopy] = ?";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1, schoolid);
            statement.setInt(2, isFalse);
            statement.execute();

            ResultSet rs = statement.getResultSet();
            while (rs.next()){
                int id = rs.getInt("ID");
                String first_name = rs.getString("FirstName");
                String lastname = rs.getString("LastName");
                Date dateofbirth = rs.getDate("DateofBirth");
                String gender = rs.getString("Gender");
                boolean isCopy = CopyChecker.checkIfCopy(rs.getInt("IsCopy"));
                ArrayList<String> observations = getObservationsOf(id);
                allPatients.add(new Patient(id,first_name,lastname,
                        convertToLocalDateViaSqlDate(dateofbirth),gender,observations, schoolid, isCopy));
            }
            return allPatients;
        } catch (SQLException sqlException) {
            throw new DALException("Not able to retrieve all the patients", sqlException);
        }

    }

    public LocalDate convertToLocalDateViaSqlDate(Date dateToConvert) {
        return new java.sql.Date(dateToConvert.getTime()).toLocalDate();
    }

    public Patient createPatient(Patient patient) throws DALException {
        try (Connection con = dataAccess.getConnection()){
            String sql = "INSERT INTO [Patient] ([FirstName], [LastName], [DateofBirth], [Gender], [Schoolid], [isCopy]) " +
                    "VALUES (?,?,?,?,?,?)";

            String sql2 = "SELECT [ID] FROM [Patient] WHERE [FirstName] = ? AND [LastName] = ? AND [DateOfBirth] = ? AND [Gender] = ? AND [schoolid] = ?";

            PreparedStatement prs = con.prepareStatement(sql);
            PreparedStatement prs2 = con.prepareStatement(sql2);

            prs.setString(1 , patient.getFirst_name());
            prs.setString(2 , patient.getLast_name());
            prs.setDate(3, Date.valueOf(patient.getDateOfBirth()));
            prs.setString(4 , patient.getGender());
            prs.setInt(5, patient.getSchoolId());
            prs.setInt(6, patient.getIsCopyDB());
            prs.executeUpdate();

            prs2.setString(1 , patient.getFirst_name());
            prs2.setString(2 , patient.getLast_name());
            prs2.setDate(3, Date.valueOf(patient.getDateOfBirth()));
            prs2.setString(4 , patient.getGender());
            prs2.setInt(5,patient.getSchoolId());
            prs2.execute();

            ResultSet rs = prs2.getResultSet();
            while(rs.next()){
                patient.setId(rs.getInt("ID"));
            }

            addObservation(patient.getObservationsList().get(0),patient);
            return patient;
        } catch (SQLException sqlException) {
            throw new DALException("Not able to create the patient" , sqlException);
        }
    }

    public void addObservation(String observation, Patient currentPatient) throws DALException {
        try(Connection con = dataAccess.getConnection()){
            String sql = "INSERT INTO [Observations] ([Patientid],[Content]) VALUES (?,?)";
            PreparedStatement st = con.prepareStatement(sql);
            st.setInt(1,currentPatient.getId());
            st.setString(2,observation);
            st.executeUpdate();
        }catch (SQLException sqlException){
            throw new DALException("Not able to add the observation", sqlException);
        }
    }

    public void updatepatient(Patient patient) throws DALException {
        try (Connection con = dataAccess.getConnection()){
            String sql = "UPDATE Patient SET [FirstName] = ? , [LastName] = ? , [DateofBirth] = ? ,[Gender] = ? WHERE id = ? ";
            PreparedStatement prs = con.prepareStatement(sql);
            prs.setString(1 , patient.getFirst_name());
            prs.setString(2 , patient.getLast_name());
            prs.setDate(3,Date.valueOf(patient.getDateOfBirth()));
            prs.setString(4 , patient.getGender());
            prs.setInt(5, patient.getId());
            prs.executeUpdate();
        } catch (SQLException sqlException) {
            throw new DALException("Not able to update the patient" , sqlException);
        }
    }


    public void deletePatient(Patient patient) throws DALException {
        try(Connection con = dataAccess.getConnection()){
            String sql = "DELETE FROM [Patient] WHERE [ID] = ?";
            PreparedStatement prs = con.prepareStatement(sql);
            prs.setInt(1 , patient.getId());
            prs.executeUpdate();
        } catch (SQLException sqlException) {
            throw new DALException("Not able to delete the patient" , sqlException);
        }
    }

    private ArrayList<String> getObservationsOf(int patientID) throws DALException {
        ArrayList<String> listOfObservations = new ArrayList<>();
        try(Connection connection = dataAccess.getConnection()){
            String sql = "SELECT [Content] FROM [Observations] WHERE [Patientid] = ?";
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1,patientID);
            st.execute();
            ResultSet rs = st.getResultSet();
            while(rs.next()){
                listOfObservations.add(rs.getString("Content"));
            }
            return listOfObservations;
        }catch(SQLException sqlException){
            throw new DALException("Not able to retrieve the Observations", sqlException);
        }
    }


    public Patient getPatientOfCase(Case selectedCase, Group group) throws DALException {
        try(Connection connection = dataAccess.getConnection()){
            String sql = "SELECT a.[ID], a.[FirstName], a.[LastName], a.[DateofBirth], a.[Gender]," +
                    " a.[Schoolid], a.[isCopy] FROM [Patient] AS a " +
                    "INNER JOIN [AssignedCases] AS b ON a.[ID] = b.[Patientid] WHERE b.[Groupid] = ? AND b.[Caseid] = ? AND a.[isCopy] = ?";
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1,group.getId());
            st.setInt(2,selectedCase.getId());
            st.setInt(3, isTrue);
            st.execute();
            ResultSet rs = st.getResultSet();
            while(rs.next()){
                int id = rs.getInt("ID");
                return new Patient(id,
                        rs.getString("FirstName"),
                        rs.getString("LastName"),
                        convertToLocalDateViaSqlDate(rs.getDate("DateOfBirth")),
                        rs.getString("Gender"),
                        getObservationsOf(id),
                        rs.getInt("Schoolid"),
                        CopyChecker.checkIfCopy(rs.getInt("isCopy"))
                        );
            }
        }catch (SQLException sqlException){
            throw new DALException("Not able to get the patient for the case", sqlException);
        }
        return null;
    }
}
