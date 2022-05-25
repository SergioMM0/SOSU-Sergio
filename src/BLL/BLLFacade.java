package BLL;

import BE.*;
import BLL.Exceptions.BLLException;
import DAL.Exceptions.DALException;

import java.util.List;

public interface BLLFacade {

    User checkCredentials(String email, String password) throws DALException, BLLException;

    Case createCase(Case newCase) throws DALException;

    Patient createPatient(Patient patient) throws DALException;

    void saveStudentQuestionAnswer(StudentQuestionnaireAnswer answer) throws DALException;

    StudentQuestion getFirstQuestion() throws DALException;

    StudentQuestion getNextQuestion(StudentQuestion question) throws DALException, BLLException;

    List<Group> getAllGroups(int schoolID) throws DALException;

    List<Case> getAllCases(int schoolID) throws DALException;

    List<Patient> getAllPatients(int schoolID) throws DALException;

    User addNewStudent(User student) throws DALException;

    void updateStudent(User student) throws DALException;

    void deleteStudent(User student) throws DALException;

    List<User> getAllStudent(int schoolID) throws DALException;

    void updatePatient(Patient patient) throws DALException;

    Group createNewGroup(Group group) throws DALException;

    void updateGroup(Group selectedGroup) throws DALException;


    StudentQuestion getPreviousQuestion(int currentQuestionId) throws BLLException, DALException;

    StudentQuestionnaireAnswer getQuestionaireAnswer(int questionId, int questionaireId) throws DALException;

    void addStudentToGroup(Group group, User student) throws DALException;

    void deleteGroup(Group group) throws DALException;

    void removeParticipant(Group group, User user) throws DALException;

    void updateCase(Case newCase) throws DALException;

    void deleteCase(Case selectedCase) throws DALException;

    void deletePatient(Patient selectedPatient) throws DALException;

    void assignCaseToGroup(Case selectedCase, Group group, Patient patient) throws DALException;

    List<Case> getCasesAssignedTo(Group group) throws DALException;

    Group getGroupOf(User student) throws DALException;

    List<StudentQuestion> getQuestionnaireQuestions(int questionnaireId) throws DALException;

    Patient getPatientOfCase(Case selectedCase, Group group) throws DALException;

    void unassignCase(Case selectedItem) throws DALException;

    void markCaseAsGraded(Case selectedItem) throws DALException;

    void unmarkCaseAsGraded(Case selectedItem) throws DALException;

    List<Case> getCasesGradedOf(Group group) throws DALException;

    void addObservationToPatient(String text, Patient patient) throws DALException;

    void UpdateQuestionnaire(int questionnaireId, Case currentCase, Patient currentPatient, Group currentGroup) throws DALException;

    int getQuestionnaireOf(int caseId, int groupId) throws DALException;

    List<School> getAllSchools() throws DALException,BLLException;
}


