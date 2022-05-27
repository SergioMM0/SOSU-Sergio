package DAL.DAOs;

import BE.*;
import DAL.DataAccess.ConnectionProvider;
import DAL.Exceptions.DALException;
import GUI.Util.StaticData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DAOHealthCondition {

    private final ConnectionProvider connectionProvider;

    public DAOHealthCondition(){
        connectionProvider = new ConnectionProvider();
    }

    public HealthCondition getHealthCondition(Subcategory subcategory, Patient patient) throws DALException {
        HealthCondition healthCondition = null;
        try(Connection connection = connectionProvider.getConnection()){
            String sql = "SELECT * FROM [HealthCondition] WHERE [ID] IN " +
                    "(SELECT [HealthConditionID] FROM [PatientHealthConditions] WHERE SubcategoryID = ? AND PatientID = ?)";

            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1,subcategory.getId());
            st.setInt(2,patient.getId());
            st.execute();
            ResultSet rs = st.getResultSet();
            while(rs.next()){
                healthCondition = new HealthCondition(
                        rs.getInt("ID"),
                        rs.getInt("Relevancy"),
                        rs.getString("Assessment"),
                        rs.getString("Goal"),
                        rs.getInt("Expectations"),
                        rs.getString("ProfessionalNote"),
                        StaticData.isNotEditing()
                );
            }
        }catch (SQLException sqlException){
            throw new DALException("Not able to get all the categories", sqlException);
        }
        return healthCondition;
    }

    public void addHealthCondition(Subcategory subcategory, HealthCondition healthCondition, Patient patient) throws DALException{
        try(Connection connection = connectionProvider.getConnection()){
            String sql = "INSERT INTO [HealthCondition] ([PatientID],[SubcategoryID]," +
                    "[Relevancy],[Assessment],[Goal],[Expectations],[ProfessionalNote]) VALUES (?,?,?,?,?,?,?)";
            String sql2 = "SELECT [ID] FROM [HealthCondition] WHERE [PatientID] = ? AND [SubcategoryID] = ?";
            String sql3 = "INSERT INTO [PatientHealthConditions] ([PatientID],[HealthConditionID],[SubcategoryIDHC]) VALUES (?,?,?)";

            PreparedStatement st = connection.prepareStatement(sql);
            PreparedStatement st2 = connection.prepareStatement(sql2);
            PreparedStatement st3 = connection.prepareStatement(sql3);

            st.setInt(1,patient.getId());
            st.setInt(2,subcategory.getId());
            st.setInt(3,healthCondition.getRelevancy());
            st.setString(4,healthCondition.getAssessment());
            st.setString(5,healthCondition.getGoal());
            st.setInt(6,healthCondition.getExpectations());
            st.setString(7,healthCondition.getProfessionalNote());
            st.execute();

            st2.setInt(1,patient.getId());
            st2.setInt(2,subcategory.getId());
            st2.execute();
            ResultSet rs = st2.getResultSet();
            while(rs.next()){
                healthCondition.setId(rs.getInt("ID"));
            }

            st3.setInt(1,patient.getId());
            st3.setInt(2,healthCondition.getId());
            st3.setInt(3,subcategory.getId());
            st3.execute();

        }catch (SQLException sqlException){
            throw new DALException("Not able to add the health condition to the patient", sqlException);
        }
    }

    public void updateHealthCondition(HealthCondition healthCondition, Subcategory subcategory, Patient patient) throws DALException{
        try(Connection connection = connectionProvider.getConnection()){
            String sql = "UPDATE [HealthCondition] SET [Relevancy] = ?, [Assessment] = ?," +
                    " [Goal] = ?, [Expectations] = ?, [ProfessionalNote] = ? WHERE PatientID = ? AND SubcategoryID = ?";
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1,healthCondition.getRelevancy());
            st.setString(2,healthCondition.getAssessment());
            st.setString(3,healthCondition.getGoal());
            st.setInt(4,healthCondition.getExpectations());
            st.setString(5,healthCondition.getProfessionalNote());
            st.setInt(6,patient.getId());
            st.setInt(7,subcategory.getId());
            st.execute();
        }catch (SQLException sqlException){
            throw new DALException("Not able to update the health condition for the patient", sqlException);
        }
    }
}
