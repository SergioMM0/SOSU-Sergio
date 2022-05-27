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
}
