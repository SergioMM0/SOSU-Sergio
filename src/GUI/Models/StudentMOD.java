package GUI.Models;

import BE.Case;
import BE.Group;
import BE.Patient;
import BE.User;
import BLL.BLLFacade;
import BLL.BLLManager;
import DAL.util.DalException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class StudentMOD {

    private BLLFacade bllFacade;
    private ObservableList<Case> casesAssigned;
    private ObservableList<Case> casesGraded;

    public StudentMOD() {
        bllFacade = new BLLManager();
        casesAssigned = FXCollections.observableArrayList();
        casesGraded = FXCollections.observableArrayList();
    }

    //TODO DELETE THE FOLLOWING WHEN IMPLEMENTED TO READ FROM FILE

    public ObservableList<String> getGenders() {
        ObservableList<String> genders = FXCollections.observableArrayList();
        genders.add("Male");
        genders.add("Female");
        genders.add("Lockheed Martin F-16 Fighting Falcon lol");
        return genders;
    }

    public Group getGroupOf(User user) throws DalException{
        return bllFacade.getGroupOf(user);
    }


    public ObservableList<Case> getCasesAssignedTo(Group logedGroup) throws DalException {
        casesAssigned.addAll(bllFacade.getCasesAssignedTo(logedGroup));
        return casesAssigned;
    }

    public ObservableList<Case> getCasesGradedOf(Group logedGroup) throws DalException {
        casesGraded.addAll(bllFacade.getCasesGradedOf(logedGroup));
        return casesGraded;
    }

    public Patient getPatientOf(Group currentGroup, Case currentCase) throws DalException {
        return bllFacade.getPatientOfCase(currentCase, currentGroup);
    }

    public void addObservationToPatient(String observation, Patient currentPatient) throws DalException{
        bllFacade.addObservationToPatient(observation, currentPatient);
    }

    public void clearLists() {
        casesGraded.clear();
        casesAssigned.clear();
    }

    public ObservableList<Case> getObservableCasesAssigned() {
        return casesAssigned;
    }

    public void updateCase(Case currentCase) throws DalException{
        bllFacade.updateCase(currentCase);
    }

    public void updateCaseInTable(Case currentCase) {
        for (Case c : casesAssigned) {
            if (c.getName().equals(currentCase.getName())) {
                c.setName(currentCase.getName());
                c.setConditionDescription(currentCase.getConditionDescription());
                c.setCategory(currentCase.getCategory());
                c.setSubCategory(currentCase.getSubCategory());
            }
        }
    }

    public void updatePatient(Patient currentPatient) throws DalException{
        bllFacade.updatePatient(currentPatient);
    }
}
