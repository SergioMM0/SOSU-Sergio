package DAL.DAOs;

import BE.Category;
import BE.Patient;
import BE.Subcategory;
import DAL.DataAccess.ConnectionProvider;
import DAL.Exceptions.DALException;
import GUI.Util.StaticData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DAOSubcategory {

    private final ConnectionProvider connectionProvider;

    public DAOSubcategory() {
        connectionProvider = new ConnectionProvider();
    }

    //TODO Maybe make only one method
    public List<Subcategory> getSubcategoriesFA(Category currentCategory, Patient currentPatient) throws DALException {
        List<Subcategory> allSubcategoriesFA = new ArrayList<>();
        try (Connection connection = connectionProvider.getConnection()) {
            String sql = "SELECT * FROM [SubcategoryFA] WHERE [CategoryID] = ? AND [ID] IN (SELECT [SubcategoryIDFA] FROM [PatientFunctionalAbilities] WHERE [PatientID] = ?)";
            String sql2 = "SELECT * FROM [SubcategoryFA] WHERE [CategoryID] = ? AND [ID] NOT IN (SELECT [SubcategoryIDFA] FROM [PatientFunctionalAbilities] WHERE [PatientID] = ?)";

            PreparedStatement st = connection.prepareStatement(sql);
            PreparedStatement st2 = connection.prepareStatement(sql2);

            st.setInt(1, currentCategory.getId());
            st.setInt(2, currentPatient.getId());
            st.execute();
            ResultSet rs = st.getResultSet();
            while (rs.next()) {
                allSubcategoriesFA.add(new Subcategory(
                        rs.getInt("ID"),
                        rs.getString("Name"),
                        StaticData.subcategoryAssessed()
                ));
            }

            st2.setInt(1, currentCategory.getId());
            st2.setInt(2, currentPatient.getId());
            st2.execute();
            ResultSet rs2 = st2.getResultSet();
            while (rs2.next()) {
                allSubcategoriesFA.add(new Subcategory(
                        rs2.getInt("ID"),
                        rs2.getString("Name"),
                        StaticData.subcategoryNotAssessed()
                ));
            }
        } catch (SQLException sqlException) {
            throw new DALException("Not able to get all the subcategories", sqlException);
        }
        return allSubcategoriesFA;
    }

    public List<Subcategory> getSubcategoriesHC(Category currentCategory, Patient currentPatient) throws DALException {
        List<Subcategory> allSubcategoriesHC = new ArrayList<>();
        try (Connection connection = connectionProvider.getConnection()) {
            String sql = "SELECT * FROM [SubcategoryHC] WHERE [CategoryID] = ? AND [ID] IN (SELECT [SubcategoryIDHC] FROM [PatientHealthConditions] WHERE [PatientID] = ?)";
            String sql2 = "SELECT * FROM [SubcategoryHC] WHERE [CategoryID] = ? AND [ID] NOT IN (SELECT [SubcategoryIDHC] FROM [PatientHealthConditions] WHERE [PatientID] = ?)";

            PreparedStatement st = connection.prepareStatement(sql);
            PreparedStatement st2 = connection.prepareStatement(sql2);
            st.setInt(1, currentCategory.getId());
            st.setInt(2, currentPatient.getId());
            st.execute();
            ResultSet rs = st.getResultSet();

            while (rs.next()) {
                allSubcategoriesHC.add(new Subcategory(
                        rs.getInt("ID"),
                        rs.getString("Name"),
                        StaticData.subcategoryAssessed()
                ));
            }

            st2.setInt(1, currentCategory.getId());
            st2.setInt(2, currentPatient.getId());
            st2.execute();
            ResultSet rs2 = st2.getResultSet();

            while (rs2.next()) {
                allSubcategoriesHC.add(new Subcategory(
                        rs2.getInt("ID"),
                        rs2.getString("Name"),
                        StaticData.subcategoryNotAssessed()
                ));
            }

        } catch (SQLException sqlException) {
            throw new DALException("Not able to get all the subcategories", sqlException);
        }
        return allSubcategoriesHC;
    }
}
