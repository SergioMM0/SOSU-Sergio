package DAL.DAOs;

import BE.FunctionalAbility;
import BE.HealthCondition;
import BE.Patient;
import BE.Subcategory;
import DAL.DataAccess.ConnectionProvider;
import DAL.Exceptions.DALException;
import GUI.Util.StaticData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DAOFunctionalAbility {

    private final ConnectionProvider connectionProvider;

    public DAOFunctionalAbility(){
        connectionProvider = new ConnectionProvider();
    }

    public FunctionalAbility getFunctionalAbility(Subcategory subcategory, Patient patient) throws DALException{
        FunctionalAbility functionalAbility = null;
        try(Connection connection = connectionProvider.getConnection()){
            String sql = "SELECT * FROM [FunctionalAbility] WHERE [ID] IN " +
                    "(SELECT [FunctionalAbilityID] FROM [PatientFunctionalAbilities] WHERE SubcategoryID = ? AND PatientID = ?)";
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1,subcategory.getId());
            st.setInt(2,patient.getId());
            st.execute();
            ResultSet rs = st.getResultSet();
            while(rs.next()){
                functionalAbility = new FunctionalAbility(
                        rs.getInt("ID"),
                        rs.getInt("Relevancy"),
                        rs.getInt("CurrentLevel"),
                        rs.getInt("ExpectedLevel"),
                        rs.getInt("Performance"),
                        rs.getInt("Meaning"),
                        rs.getString("CitizenGoal"),
                        rs.getString("ProfessionalNote"),
                        StaticData.isNotEditing()
                );
            }
        }catch (SQLException sqlException){
            throw new DALException("Not able to get all the categories", sqlException);
        }
        return functionalAbility;
    }

    public void addFunctionalAbility(FunctionalAbility functionalAbility, Subcategory subcategory, Patient patient) throws DALException{
        try(Connection connection = connectionProvider.getConnection()){
            String sql = "INSERT INTO [FunctionalAbility] ([PatientID],[SubcategoryID],[Relevancy]," +
                    "[CurrentLevel],[ExpectedLevel],[Performance],[Meaning],[CitizenGoal],[ProfessionalNote]) VALUES (?,?,?,?,?,?,?,?,?)";
            String sql2 = "SELECT [ID] FROM [FunctionalAbility] WHERE [PatientID] = ? AND [SubcategoryID] = ?";
            String sql3 = "INSERT INTO [PatientFunctionalAbilities] ([PatientID],[FunctionalAbilityID],[SubcategoryIDFA]) VALUES (?,?,?)";

            PreparedStatement st = connection.prepareStatement(sql);
            PreparedStatement st2 = connection.prepareStatement(sql2);
            PreparedStatement st3 = connection.prepareStatement(sql3);

            st.setInt(1,patient.getId());
            st.setInt(2,subcategory.getId());
            st.setInt(3,functionalAbility.getRelevancy());
            st.setInt(4,functionalAbility.getCurrentLevel());
            st.setInt(5,functionalAbility.getExpectedLevel());
            st.setInt(6,functionalAbility.getPerformance());
            st.setInt(7,functionalAbility.getMeaning());
            st.setString(8,functionalAbility.getCitizenGoal());
            st.setString(9,functionalAbility.getProfessionalNote());
            st.execute();

            st2.setInt(1,patient.getId());
            st2.setInt(2,subcategory.getId());
            st2.execute();
            ResultSet rs = st2.getResultSet();
            while(rs.next()){
                functionalAbility.setId(rs.getInt("ID"));
            }

            st3.setInt(1,patient.getId());
            st3.setInt(2,functionalAbility.getId());
            st3.setInt(3,subcategory.getId());
            st3.execute();

        }catch (SQLException sqlException){
            throw new DALException("Not able to add the functional ability to the patient", sqlException);
        }
    }

    public void updateFunctionalAbility(FunctionalAbility functionalAbility, Subcategory subcategory, Patient patient) throws DALException{
        try(Connection connection = connectionProvider.getConnection()){
            String sql = "UPDATE FunctionalAbility SET [Relevancy] = ?, [CurrentLevel] = ?," +
                    " [ExpectedLevel] = ?, [Performance] = ?, [Meaning] = ?, [CitizenGoal] = ?, [ProfessionalNote] = ? " +
                    "WHERE [SubcategoryID] = ? AND [PatientID] = ?";
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1,functionalAbility.getRelevancy());
            st.setInt(2,functionalAbility.getCurrentLevel());
            st.setInt(3,functionalAbility.getExpectedLevel());
            st.setInt(4,functionalAbility.getPerformance());
            st.setInt(5,functionalAbility.getMeaning());
            st.setString(6,functionalAbility.getCitizenGoal());
            st.setString(7,functionalAbility.getProfessionalNote());
            st.setInt(8,subcategory.getId());
            st.setInt(9,patient.getId());
            st.execute();
        }catch (SQLException sqlException){
            throw new DALException("Not able to update the functional ability of the patient", sqlException);
        }
    }
}
