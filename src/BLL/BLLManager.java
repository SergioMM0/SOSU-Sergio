package BLL;

import BE.*;
import BLL.Exceptions.BLLException;
import DAL.DALFacade;
import DAL.Manager;
import DAL.Exceptions.DALException;

import java.security.InvalidParameterException;
import java.util.List;

public class BLLManager implements BLLFacade {

    private DALFacade dalFacade;

    public BLLManager() {
        dalFacade = new Manager();
    }

    @Override
    public User checkCredentials(String email, String password) throws DALException, BLLException {
        User logedUser = dalFacade.checkCredentials(email, password);
        if (logedUser == null) {
            throw new BLLException("Wrong email or password, please try again", new InvalidParameterException());
        }
        return logedUser;
    }

    @Override
    public Case createCase(Case newCase) throws DALException {
        return dalFacade.createCase(newCase);
    }

    @Override
    public Patient createPatient(Patient patient) throws DALException {
        return dalFacade.createPatient(patient);
    }

    @Override
    public List<Group> getAllGroups(int schoolID) throws DALException {
        return dalFacade.getAllGroups(schoolID);
    }

    @Override
    public List<Case> getAllCases(int schoolID) throws DALException {
        return dalFacade.getAllCases(schoolID);
    }

    @Override
    public List<Patient> getAllPatients(int schoolID) throws DALException {
        return dalFacade.getAllPatients(schoolID);
    }

    @Override
    public User addNewUser(User user) throws DALException {
        return dalFacade.addNewUser(user);
    }

    @Override
    public void updateStudent(User student) throws DALException {
        dalFacade.updateStudent(student);
    }

    @Override
    public void deleteUser(User user) throws DALException {
        dalFacade.deleteUser(user);
    }

    @Override
    public List<User> getAllStudent(int schoolID) throws DALException {
        return dalFacade.getAllStudents(schoolID);
    }

    @Override
    public void updatePatient(Patient patient) throws DALException {
        dalFacade.updatePatient(patient);
    }

    @Override
    public Group createNewGroup(Group group) throws DALException {
        return dalFacade.createGroup(group);
    }

    @Override
    public void updateGroup(Group selectedGroup) throws DALException {
        dalFacade.updateGroup(selectedGroup);
    }


    @Override
    public void addStudentToGroup(Group group, User student) throws DALException {
        dalFacade.addUsertoGroup(group, student);
    }

    @Override
    public void deleteGroup(Group group) throws DALException {
        dalFacade.deleteGroup(group);
    }

    @Override
    public void removeParticipant(Group group, User user) throws DALException {
        dalFacade.removeParticipant(user, group);
        //TODO Implement
    }

    @Override
    public void updateCase(Case newCase) throws DALException {
        dalFacade.updateCase(newCase);
        //TODO implement
    }

    @Override
    public void deleteCase(Case selectedCase) throws DALException {
        dalFacade.deleteCase(selectedCase);
    }

    @Override
    public void deletePatient(Patient selectedPatient) throws DALException {
        dalFacade.deletePatient(selectedPatient);
    }

    @Override
    public void assignCaseToGroup(Case selectedCase, Group group, Patient patient) throws DALException {
        String groupDistinctiveCase = selectedCase.getName() + " - " + group.getName();
        selectedCase.setName(groupDistinctiveCase);
        selectedCase.setCopy(true);
        String groupDistinctivePatient = patient.getFirst_name() + " - " + group.getName();
        patient.setFirst_name(groupDistinctivePatient);
        patient.setCopy(true);
        dalFacade.assignCaseToGroup(patient, selectedCase, group);
    }

    @Override
    public List<Case> getCasesAssignedTo(Group group) throws DALException {
        return dalFacade.getCasesAssignedTo(group);
    }


    @Override
    public Group getGroupOf(User student) throws DALException {
        return dalFacade.getGroupOf(student);
    }

    @Override
    public Patient getPatientOfCase(Case selectedCase, Group group) throws DALException {
        return dalFacade.getPatientOfCase(selectedCase, group);
    }

    @Override
    public void unassignCase(Case selectedItem) throws DALException {
        dalFacade.unassignCase(selectedItem);
    }

    @Override
    public void markCaseAsGraded(Case selectedItem) throws DALException {
        dalFacade.markCaseAsGraded(selectedItem);
    }

    @Override
    public void unmarkCaseAsGraded(Case selectedItem) throws DALException {
        dalFacade.unmarkCaseAsGraded(selectedItem);
    }

    @Override
    public List<Case> getCasesGradedOf(Group group) throws DALException {
        return dalFacade.getCasesGradedOf(group);
    }

    @Override
    public void addObservationToPatient(String text, Patient currentPatient) throws DALException {
        dalFacade.addObservationToPatient(text, currentPatient);
    }

    @Override
    public List<School> getAllSchools() throws DALException, BLLException {
        try{
            return dalFacade.getAllSchools();
        }catch(NullPointerException nullPointerException){
            throw new BLLException("Not able to get the schools", nullPointerException);
        }
    }

    @Override
    public School addSchool(School currentSchool) throws DALException {
        return dalFacade.addSchool(currentSchool);
    }

    @Override
    public void deleteSchool(School currentSchool) throws DALException {
        dalFacade.deleteSchool(currentSchool);
    }
}
