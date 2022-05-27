package GUI.Models;

import BE.*;
import BLL.BLLFacade;
import BLL.BLLManager;
import BLL.Exceptions.BLLException;
import DAL.Exceptions.DALException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class EvaluateCaseMOD {

    private BLLFacade bllFacade;
    private ObservableList<Category> allCategoriesHC;
    private ObservableList<Category> allCategoriesFA;
    private ObservableList<Subcategory> subcategoriesFA;
    private ObservableList<Subcategory> subcategoriesHC;

    public EvaluateCaseMOD(){
        bllFacade = new BLLManager();
        allCategoriesHC = FXCollections.observableArrayList();
        allCategoriesFA = FXCollections.observableArrayList();
        subcategoriesFA = FXCollections.observableArrayList();
        subcategoriesHC = FXCollections.observableArrayList();
    }

    public ObservableList<Category> getAllCategoriesHC() throws DALException {
        allCategoriesHC.addAll(bllFacade.getAllCategoriesHC());
        return allCategoriesHC;
    }

    public ObservableList<Category> getAllCategoriesFA() throws DALException{
        allCategoriesFA.addAll(bllFacade.getAllCategoriesFA());
        return allCategoriesFA;
    }

    public void addObservationToPatient(String observation, Patient patient) throws DALException {
        bllFacade.addObservationToPatient(observation,patient);
    }

    public void updatePatient(Patient currentPatient) throws DALException, BLLException {
        bllFacade.updatePatient(currentPatient);
    }

    public ObservableList<Subcategory> getSubcategoriesFA(Category currentCategory,Patient currentPatient) throws DALException {
        subcategoriesFA.clear();
        subcategoriesFA.addAll(bllFacade.getSubcategoriesFA(currentCategory,currentPatient));
        return subcategoriesFA;
    }

    public ObservableList<Subcategory> getSubcategoriesHC(Category currentCategory,Patient currentPatient) throws DALException {
        subcategoriesHC.clear();
        subcategoriesHC.addAll(bllFacade.getSubcategoriesHC(currentCategory,currentPatient));
        return subcategoriesHC;
    }

    public FunctionalAbility getCurrentFunctionalAbility(Subcategory currentSubcategory, Patient patient) throws DALException {
        return bllFacade.getFunctionalAbility(currentSubcategory, patient);
    }

    public HealthCondition getCurrentHealthCondition(Subcategory currentSubcategory, Patient currentPatient) throws DALException{
        return bllFacade.getHealthCondition(currentSubcategory,currentPatient);
    }
}
