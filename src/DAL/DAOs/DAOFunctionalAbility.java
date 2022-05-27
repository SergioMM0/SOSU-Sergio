package DAL.DAOs;

import BE.FunctionalAbility;
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
}
