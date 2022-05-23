package BLL;

import BE.*;
import BLL.Exceptions.BLLException;
import DAL.util.DalException;
import com.sun.security.auth.module.LdapLoginModule;

import java.util.List;

public interface BLLFacade {

    User checkCredentials(String email, String password) throws DalException, BLLException;

    Case createCase(Case newCase) throws DalException;

    Patient createPatient(Patient patient) throws DalException;

    void saveStudentQuestionAnswer(StudentQuestionnaireAnswer answer) throws DalException;

    StudentQuestion getFirstQuestion() throws DalException;

    StudentQuestion getNextQuestion(StudentQuestion question) throws DalException, BLLException;

    List<Group> getAllGroups(int schoolID) throws DalException;

    List<Case> getAllCases(int schoolID) throws DalException;

    List<Patient> getAllPatients(int schoolID) throws DalException;

    User addNewStudent(User user) throws DalException;

    void updateStudent(User student) throws DalException;

    void deleteStudent(User student) throws DalException;

    List<User> getAllStudent(int schoolID) throws DalException;

    void updatePatient(Patient patient) throws DalException;

    Group createNewGroup(Group group) throws DalException;

    void updateGroup(Group selectedGroup) throws DalException;


    StudentQuestion getPreviousQuestion(int currentQuestionId) throws BLLException, DalException;

    StudentQuestionnaireAnswer getQuestionaireAnswer(int questionId, int questionaireId) throws DalException;

    void addStudentToGroup(Group group, User student) throws DalException;

    void deleteGroup(Group group) throws DalException;

    void removeParticipant(Group group, User user) throws DalException;

    void updateCase(Case newCase) throws DalException;

    void deleteCase(Case selectedCase) throws DalException;

    void deletePatient(Patient selectedPatient) throws DalException;

    List<School> getAllSchools() throws DalException;

    void createSchool(School school) throws DalException;

    void updateSchool(School school) throws DalException;

    void deleteSchool(School school) throws DalException;

    void assignCaseToGroup(Case selectedCase, Group group, Patient patient) throws DalException;

    List<Case> getCasesAssignedTo(Group group) throws DalException;

    List<User> getALLUsers(int schoolid, String utype) throws DalException;

    Group getGroupOf(User student) throws DalException;

    StudentQuestionnaire getQuestionnaireOf(Group group) throws DalException;


    List<User> searchForUser(String query) throws DalException;

    List<User> getAllUSERS(int schoolId , String utype) throws DalException;

    List<StudentQuestion> getQuestionnaireQuestions(int questionnaireId) throws DalException;
    Patient getPatientOfCase(Case selectedCase, Group group) throws DalException;

    void unassignCase(Case selectedItem) throws DalException;

    void markCaseAsGraded(Case selectedItem) throws DalException;

    void unmarkCaseAsGraded(Case selectedItem) throws DalException;

    List<Case> getCasesGradedOf(Group group) throws DalException;

    void addObservationToPatient(String text, Patient patient) throws DalException;

    void UpdateQuestionnaire(int questionnaireId, Case currentCase, Patient currentPatient, Group currentGroup) throws DalException;

    int getQuestionnaireOf(int caseId, int groupId) throws DalException;
}


