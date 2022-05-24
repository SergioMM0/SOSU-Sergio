package DAL;

import BE.*;
import DAL.Exceptions.DALException;

import java.util.List;

public interface DALFacade {

    List<Case> getAllCases(int schoolid) throws DALException;

    Case createCase(Case c)throws DALException;

    void updateCase(Case c)throws DALException;

    void deleteCase(Case c) throws DALException;

    User checkCredentials(String useremail, String password) throws DALException;

    List<User> getAllStudents(int schoolid) throws DALException;

    void updateStudent(User user) throws DALException;  //

    void deleteStudent(User user) throws DALException;

    User addNewStudent(User user ) throws DALException; //

    List<User> searchForUser (String query) throws DALException;

    List<Patient> getAllPatients(int schoolid) throws DALException;

    Patient createPatient(Patient patient ) throws DALException;
    //
    void updatePatient(Patient patient) throws DALException;
        //
    void deletePatient(Patient patient)throws DALException;

    List<School> getAllSchhol() throws DALException;

    void createSchool(School school)throws DALException;
        //
    void updateSchool(School school)throws DALException;
        //
    void deleteSchool(School school)throws DALException;

    List<Group> getAllGroups(int schoolID)throws DALException;

    Group createGroup(Group group)throws DALException;
        //
    void updateGroup(Group group)throws DALException;
        //
    void deleteGroup(Group group)throws DALException;

    List<User> getUsersInGroup(int id)throws DALException;

    void addUsertoGroup(Group group , User user)throws DALException;

    void removeUserFromGroup(User user)throws DALException;

    void assignCaseToGroup(Patient p , Case c , Group g) throws DALException;


    void addStudentQuestionAnswer(StudentQuestionnaireAnswer answer) throws DALException;

    StudentQuestion getFirstStudentQuestion() throws DALException;

    StudentQuestion getNextStudentQuestion(int id) throws DALException;

    StudentQuestion getPreviousQuestion(int currentQuestionId) throws DALException;

    StudentQuestionnaireAnswer getQuestionaireAnswer(int questionId, int questionaireId) throws DALException;

     void removeParticipant(User user , Group group)throws DALException;

     List<Case> getCasesAssignedTo(Group group)throws DALException;


    List<User> getAllUSERS(int schoolId  ,String utype) throws DALException;

     List<User> getALLUsers(int schoolid , String utype) throws DALException;

    Group getGroupOf(User student) throws DALException;

    StudentQuestionnaire getQuestionnaireOf(Group group) throws DALException;

    List<StudentQuestion> getQuestionnaireQuestions(int questionnaireId) throws DALException;
    Patient getPatientOfCase(Case selectedCase, Group group) throws DALException;

    void unassignCase(Case selectedItem) throws DALException;

    void markCaseAsGraded(Case selectedItem) throws DALException;

    void unmarkCaseAsGraded(Case selectedItem) throws DALException;

    List<Case> getCasesGradedOf(Group group) throws DALException;

    void addObservationToPatient(String text, Patient currentPatient) throws DALException;

    StudentQuestionnaire getQuestionnaire(int questionnaireId) throws DALException;

    int getSickPatientId(Patient currentPatient, Case currentCase, Group currentGroup) throws DALException;

    void updateQuestionnaire(StudentQuestionnaire questionnaire) throws DALException;

    int getQuestionnaireOf(int caseId, int groupId) throws DALException;
}
