package DAL.Manager1;

import BE.Case;
import BE.Group;
import BE.Patient;
import DAL.DataAccess.DataAccess;
import DAL.util.CopyChecker;
import DAL.util.DalException;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DAOPatient {
    private final DataAccess dataAccess;
    private CopyChecker copyChecker;
    private final int isTrue = 1;
    private final int isFalse = 0;

    public DAOPatient() {
        dataAccess = new DataAccess();
        copyChecker = CopyChecker.getInstance();
    }

    public List<Patient> getAllPatients(int schoolid) throws DalException {
        ArrayList<Patient> patients = new ArrayList<>();
        try(Connection con = dataAccess.getConnection()) {
            String sql = "SELECT * FROM Patient WHERE schoolid = ? AND isCopy = ?";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1, schoolid);
            statement.setInt(2,isFalse);
            statement.execute();
            ResultSet rs = statement.getResultSet();
            while (rs.next()){
                int id = rs.getInt("id");
                String first_name = rs.getString("first_name");
                String lastname = rs.getString("last_name");
                Date dateofbirth = rs.getDate("dateofBirth");
                String gender = rs.getString("gender");
                String  weight = rs.getString("weight");
                String  height = rs.getString("height");
                String cpr = rs.getString("cpr");
                String phonenumber = rs.getString("phone_number");
                boolean isCopy = copyChecker.checkIfCopy(rs.getInt("isCopy"));
                ArrayList<String> observations = getObservationsOf(id);
                Patient patient = new Patient(id,first_name,lastname,convertToLocalDateViaSqlDate(dateofbirth),gender,weight,height,
                        cpr,phonenumber, observations, schoolid, isCopy);
                patients.add(patient);
            }
            return patients;
        } catch (SQLException e) {
            throw new DalException("Connection Lost" , e);
        }

    }

    public LocalDate convertToLocalDateViaSqlDate(Date dateToConvert) {
        return new java.sql.Date(dateToConvert.getTime()).toLocalDate();
    }

    public Patient createPatient(Patient patient) throws DalException {
        try (Connection con = dataAccess.getConnection()){
            String sql = "INSERT INTO Patient (first_name, last_name, dateofBirth, gender,weight ,height ,cpr ," +
                    " phone_number,schoolid, isCopy) " +
                    "VALUES (?,?,?,?,?,?,?,?,?,?);";

            String sql2 = "SELECT [id] FROM Patient WHERE [first_name] = ? AND [last_name] = ? AND [dateofBirth] = ? AND [gender] = ? AND [weight] = ? AND [height] = ? AND [cpr] = ? AND [phone_number] = ? AND [schoolid] = ?";

            PreparedStatement prs = con.prepareStatement(sql);
            PreparedStatement prs2 = con.prepareStatement(sql2);

            prs.setString(1 , patient.getFirst_name());
            prs.setString(2 , patient.getLast_name());
            prs.setDate(3, Date.valueOf(patient.getDateOfBirth()));
            prs.setString(4 , patient.getGender());
            prs.setString(5 ,patient.getWeight());
            prs.setString(6,patient.getHeight());
            prs.setString(7 ,patient.getCpr());
            prs.setString(8 , patient.getPhoneNumber());
            prs.setInt(9,patient.getSchoolId());
            prs.setInt(10,patient.getIsCopyDB());
            prs.executeUpdate();

            addObservation(patient.getObservationsList().get(0),patient);

            prs2.setString(1 , patient.getFirst_name());
            prs2.setString(2 , patient.getLast_name());
            prs2.setDate(3, Date.valueOf(patient.getDateOfBirth()));
            prs2.setString(4 , patient.getGender());
            prs2.setString(5 ,patient.getWeight());
            prs2.setString(6,patient.getHeight());
            prs2.setString(7 ,patient.getCpr());
            prs2.setString(8 , patient.getPhoneNumber());
            prs2.setInt(9,patient.getSchoolId());
            prs2.execute();
            ResultSet rs = prs2.getResultSet();
            while(rs.next()){
                patient.setId(rs.getInt("id"));
            }
            return patient;
        } catch (SQLException e) {
            throw new DalException("Connection Lost " , e);
        }
    }

    public void addObservation(String observation, Patient currentPatient) throws DalException{
        try(Connection con = dataAccess.getConnection()){
            String sql = "INSERT INTO [observationstable] ([patientid],[content]) VALUES (?,?)";
            PreparedStatement st = con.prepareStatement(sql);
            st.setInt(1,currentPatient.getId());
            st.setString(2,observation);
            st.executeUpdate();
        }catch (SQLException sqlException){
            throw new DalException("Not able to add the observation",sqlException);
        }
    }

    public void updatepatient(Patient patient) throws DalException {

        try (Connection con = dataAccess.getConnection()){
            String sql = "Update Patient set first_name = ? , last_name = ? , dateofBirth = ? , gender = ? " +
                    ", weight = ? , height = ? , cpr = ? , phone_number = ? where id = ? ";
            PreparedStatement prs = con.prepareStatement(sql);
            prs.setString(1 , patient.getFirst_name());
            prs.setString(2 , patient.getLast_name());
            prs.setDate(3,Date.valueOf(patient.getDateOfBirth()));
            prs.setString(4 , patient.getGender());
            prs.setString(5 ,patient.getWeight());
            prs.setString(6,patient.getHeight());
            prs.setString(7 ,patient.getCpr());
            prs.setString(8 , patient.getPhoneNumber());
            prs.setInt(9, patient.getId());
            prs.executeUpdate();
        } catch (SQLException e) {
            throw new DalException("Connection Lost" , e);
        }
    }


    public void deletePatient(Patient patient) throws DalException {
        try(Connection con = dataAccess.getConnection()){
            String sql = "DELETE FROM Patient WHERE id = ?";
            PreparedStatement prs = con.prepareStatement(sql);
            prs.setInt(1 , patient.getId());
            prs.executeUpdate();
        } catch (SQLException e) {
            throw new DalException("Connection Lost" , e);
        }
    }

    private ArrayList<String> getObservationsOf(int patientID) throws DalException{
        ArrayList<String> observations = new ArrayList<>();
        try(Connection connection = dataAccess.getConnection()){
            String sql = "SELECT [content] FROM observationstable WHERE [patientid] = ?";
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1,patientID);
            st.execute();
            ResultSet rs = st.getResultSet();
            while(rs.next()){
                observations.add(rs.getString("content"));
            }
            return observations;
        }catch(SQLException sqlException){
            throw new DalException("Not able to retrieve the Observations",sqlException);
        }
    }


    public Patient getPatientOfCase(Case selectedCase, Group group) throws DalException {
        try(Connection connection = dataAccess.getConnection()){
            String sql = "SELECT a.id, a.first_name, a.last_name, a.dateofBirth, a.gender, a.weight ," +
                    " a.height, a.cpr, a.phone_number, a.schoolid, a.isCopy FROM [Patient] AS a " +
                    "INNER JOIN SickPatient AS b ON a.id = b.patientid WHERE b.Groupid = ? AND b.caseid = ? AND a.[isCopy] = ?";
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1,group.getId());
            st.setInt(2,selectedCase.getId());
            st.setInt(3, isTrue);
            st.execute();
            ResultSet rs = st.getResultSet();
            while(rs.next()){
                int id = rs.getInt("id");
                return new Patient(id,
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        convertToLocalDateViaSqlDate(rs.getDate("dateofBirth")),
                        rs.getString("gender"),
                        rs.getString("weight"),
                        rs.getString("height"),
                        rs.getString("cpr"),
                        rs.getString("phone_number"),
                        getObservationsOf(id),
                        rs.getInt("schoolid"),
                        copyChecker.checkIfCopy(rs.getInt("isCopy"))
                        );
            }

        }catch (SQLException sqlException){
            throw new DalException("Not able to get the patient for the case", sqlException);
        }
        return null;
    }
}
