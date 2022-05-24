package GUI.Models;

import BE.Case;
import BE.Group;
import BE.Patient;
import BE.User;
import BLL.BLLFacade;
import BLL.BLLManager;
import DAL.Exceptions.DALException;
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

    public Group getGroupOf(User user) throws DALException {
        return bllFacade.getGroupOf(user);
    }


    public ObservableList<Case> getCasesAssignedTo(Group logedGroup) throws DALException {
        casesAssigned.addAll(bllFacade.getCasesAssignedTo(logedGroup));
        return casesAssigned;
    }

    public ObservableList<Case> getCasesGradedOf(Group logedGroup) throws DALException {
        casesGraded.addAll(bllFacade.getCasesGradedOf(logedGroup));
        return casesGraded;
    }

    public Patient getPatientOf(Group currentGroup, Case currentCase) throws DALException {
        return bllFacade.getPatientOfCase(currentCase, currentGroup);
    }

    public void addObservationToPatient(String observation, Patient currentPatient) throws DALException {
        bllFacade.addObservationToPatient(observation, currentPatient);
    }

    public void clearLists() {
        casesGraded.clear();
        casesAssigned.clear();
    }

    public ObservableList<Case> getObservableCasesAssigned() {
        return casesAssigned;
    }

    public void updateCase(Case currentCase) throws DALException {
        bllFacade.updateCase(currentCase);
    }

    public void updateCaseInTable(Case currentCase) {
        for (Case c : casesAssigned) {
            if (c.getName().equals(currentCase.getName())) {
                c.setName(currentCase.getName());
                c.setConditionDescription(currentCase.getConditionDescription());
            }
        }
    }

    public void updatePatient(Patient currentPatient) throws DALException {
        bllFacade.updatePatient(currentPatient);
    }
}
