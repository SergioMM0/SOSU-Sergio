package BLL;

import BE.*;
import BLL.Exceptions.BLLException;
import DAL.DALFacade;
import DAL.Manager;
import DAL.util.DalException;
import javafx.collections.ObservableList;

import java.security.InvalidParameterException;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class BLLManager implements BLLFacade {

    private DALFacade dalFacade;

    public BLLManager() {
        dalFacade = new Manager();
    }

    @Override
    public User checkCredentials(String email, String password) throws DalException, BLLException {
        User logedUser = dalFacade.verifyUsers(email, password);
        if (logedUser == null) {
            throw new BLLException("Wrong email or password, please try again", new InvalidParameterException());
        }
        return logedUser;
    }

    @Override
    public Case createCase(Case newCase) throws DalException {
        return dalFacade.createCase(newCase);
    }

    @Override
    public Patient createPatient(Patient patient) throws DalException {
        return dalFacade.createPatient(patient);
    }

    @Override
    public List<Group> getAllGroups(int schoolID) throws DalException {
        return dalFacade.getAllGroups(schoolID);
    }

    @Override
    public List<Case> getAllCases(int schoolID) throws DalException {
        return dalFacade.getAllCases(schoolID);
    }

    @Override
    public List<Patient> getAllPatients(int schoolID) throws DalException {
        return dalFacade.getAllPatients(schoolID);
    }

    @Override
    public User addNewStudent(User user) throws DalException {
        return dalFacade.addUser(user);
    }

    @Override
    public void updateStudent(User student) throws DalException {
        dalFacade.updateuser(student);
    }

    @Override
    public void deleteStudent(User student) throws DalException {
        dalFacade.deleteuser(student);
    }

    @Override
    public List<User> getAllStudent(int schoolID) throws DalException {
        return dalFacade.getAllUsers(schoolID);
    }

    @Override
    public void updatePatient(Patient patient) throws DalException {
        dalFacade.updatePatient(patient);
    }

    @Override
    public Group createNewGroup(Group group) throws DalException {
        return dalFacade.createGroup(group);
    }

    @Override
    public void updateGroup(Group selectedGroup) throws DalException {
        dalFacade.updateGroup(selectedGroup);
    }


    @Override
    public void addStudentToGroup(Group group, User student) throws DalException {
        dalFacade.addUsertoGroup(group, student);
    }

    @Override
    public void deleteGroup(Group group) throws DalException {
        dalFacade.deleteGroup(group);
    }

    @Override
    public void removeParticipant(Group group, User user) throws DalException {
        dalFacade.removeUserAndGroup(user, group);
        //TODO Implement
    }

    @Override
    public void updateCase(Case newCase) throws DalException {
        dalFacade.updateCase(newCase);
        //TODO implement
    }

    @Override
    public void deleteCase(Case selectedCase) throws DalException {
        dalFacade.deleteCase(selectedCase);
    }

    @Override
    public void deletePatient(Patient selectedPatient) throws DalException {
        dalFacade.deletePatient(selectedPatient);
    }

    @Override
    public List<School> getAllSchools() throws DalException {
        return dalFacade.getAllSchhol();
    }

    @Override
    public void createSchool(School school) throws DalException {
        dalFacade.createSchool(school);
    }

    @Override
    public void updateSchool(School school) throws DalException {
        dalFacade.updateSchool(school);
    }

    @Override
    public void deleteSchool(School school) throws DalException {
        dalFacade.deleteSchool(school);
    }

    @Override
    public void assignCaseToGroup(Case selectedCase, Group group, Patient patient) throws DalException {
        String groupDistinctiveCase = selectedCase.getName() + " - " + group.getName();
        selectedCase.setName(groupDistinctiveCase);
        selectedCase.setCopy(true);
        String groupDistinctivePatient = patient.getFirst_name() + " - " + group.getName();
        patient.setFirst_name(groupDistinctivePatient);
        patient.setCopy(true);
        dalFacade.assignCaseToGroup(patient, selectedCase, group);
    }

    @Override
    public List<Case> getCasesAssignedTo(Group group) throws DalException {
        return dalFacade.getCasesAssignedTo(group);
        //TODO IMPLEMENT

    }

    @Override
    public List<User> getALLUsers(int schoolid, String utype) throws DalException {
        List<User> sortedusers = dalFacade.getAllUSERS(schoolid , utype);
      sortedusers.sort((o1, o2) -> o2.getName().compareTo(o1.getName()));

     return sortedusers ;

    }

    public ObservableList<User> searchUser(ObservableList<User> users, String text) {
        return users.filtered((t) -> t.getName().toLowerCase().startsWith(text.toLowerCase()) ||  t.getEmail().toLowerCase().startsWith(text.toLowerCase()));

    }


    @Override
    public Group getGroupOf(User student) throws DalException {
        return dalFacade.getGroupOf(student);
    }

    @Override
    public StudentQuestionnaire getQuestionnaireOf(Group group) throws DalException {
        return dalFacade.getQuestionnaireOf(group);
    }

    public List<User> searchForUser(String query) throws DalException {
        return dalFacade.searchForUser(query);
    }

    @Override
    public List<User> getAllUSERS(int schoolId , String utype) throws DalException {
        return dalFacade.getAllUSERS(schoolId , utype);
    }

    @Override
    public List<StudentQuestion> getQuestionnaireQuestions(int questionnaireId) throws DalException {
        return dalFacade.getQuestionnaireQuestions(questionnaireId);
    }
    public Patient getPatientOfCase(Case selectedCase, Group group) throws DalException {
        return dalFacade.getPatientOfCase(selectedCase, group);
    }

    @Override
    public void unassignCase(Case selectedItem) throws DalException {
        dalFacade.unassignCase(selectedItem);
    }

    @Override
    public void markCaseAsGraded(Case selectedItem) throws DalException {
        dalFacade.markCaseAsGraded(selectedItem);
    }

    @Override
    public void unmarkCaseAsGraded(Case selectedItem) throws DalException {
        dalFacade.unmarkCaseAsGraded(selectedItem);
    }

    @Override
    public List<Case> getCasesGradedOf(Group group) throws DalException {
        return dalFacade.getCasesGradedOf(group);
    }

    @Override
    public void addObservationToPatient(String text, Patient currentPatient) throws DalException {
        dalFacade.addObservationToPatient(text, currentPatient);
    }

    @Override
    public void UpdateQuestionnaire(int questionnaireId, Case currentCase, Patient currentPatient, Group currentGroup) throws DalException {
        StudentQuestionnaire questionnaire=dalFacade.getQuestionnaire(questionnaireId);
        int sickPatientId = dalFacade.getSickPatientId(currentPatient, currentCase, currentGroup);
        if(sickPatientId<0)return;
        questionnaire.setSickPatientId(sickPatientId);
        dalFacade.updateQuestionnaire(questionnaire);
    }

    @Override
    public int getQuestionnaireOf(int caseId, int groupId) throws DalException {
        return dalFacade.getQuestionnaireOf(caseId,groupId);
    }

    @Override
    public void saveStudentQuestionAnswer(StudentQuestionnaireAnswer answer) throws DalException {
        dalFacade.addStudentQuestionAnswer(answer);
    }

    @Override
    public StudentQuestion getFirstQuestion() throws DalException {
        return dalFacade.getFirstStudentQuestion();
    }

    @Override
    public StudentQuestion getNextQuestion(StudentQuestion question) throws DalException, BLLException {
        StudentQuestion s = dalFacade.getNextStudentQuestion(question.getId());

        return s;
    }

    @Override
    public StudentQuestion getPreviousQuestion(int currentQuestionId) throws BLLException, DalException {
        StudentQuestion s = dalFacade.getPreviousQuestion(currentQuestionId);

        return s;
    }

    @Override
    public StudentQuestionnaireAnswer getQuestionaireAnswer(int questionId, int questionaireId) throws DalException {
        return dalFacade.getQuestionaireAnswer(questionId, questionaireId);
    }


}
