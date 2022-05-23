package DAL;

import BE.*;
import DAL.Manager1.*;
import DAL.util.DalException;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class Manager implements DALFacade {

    private final DAOCase daoCase;
    private final DAOUser daoUser;
    private final DAOPatient daoPatient;
    private final DAOSchool daoSchool;
    private final DAOGroup daoGroup;
    private final DAOStudentQuestion daoStudentQuestion;

    public Manager() {
        daoCase = new DAOCase();
        daoUser = new DAOUser();
        daoPatient = new DAOPatient();
        daoSchool = new DAOSchool();
        daoGroup = new DAOGroup();
        daoStudentQuestion = new DAOStudentQuestion();
    }


    @Override
    public List<Case> getAllCases(int schoolid) throws DalException {
        return daoCase.getAllCases(schoolid);
    }

    @Override
    public Case createCase(Case c) throws DalException {
        return daoCase.createCase(c);
    }

    @Override
    public void updateCase(Case c) throws DalException {
        daoCase.updateCase(c);
    }

    @Override
    public void deleteCase(Case c) throws DalException {
        daoCase.deleteCase(c);
    }

    @Override
    public User verifyUsers(String useremail, String password) throws DalException {
        return daoUser.verifyUsers(useremail, password);
    }

    @Override
    public List<User> getAllUsers(int schoolid) throws DalException {
        return daoUser.getAllUsers(schoolid);
    }

    @Override
    public void updateuser(User user ) throws DalException {
        daoUser.updateuser(user );
    }

    @Override
    public void deleteuser(User user) throws DalException {
        daoUser.deleteuser(user);
    }

    @Override
    public User addUser(User user) throws DalException {
        return daoUser.addUser(user);
    }

    @Override
    public List<User> searchForUser(String query) throws DalException {
        return daoUser.searchForUser(query);
    }

    @Override
    public List<Patient> getAllPatients(int schoolid) throws DalException {
        return daoPatient.getAllPatients(schoolid);
    }

    @Override
    public Patient createPatient(Patient patient) throws DalException {
        return daoPatient.createPatient(patient);
    }

    @Override
    public void updatePatient(Patient patient) throws DalException {
        daoPatient.updatepatient(patient);
    }

    @Override
    public void deletePatient(Patient patient) throws DalException {
        daoPatient.deletePatient(patient);
    }

    @Override
    public List<School> getAllSchhol() throws DalException {
        return daoSchool.getAllSchhol();
    }

    @Override
    public void createSchool(School school) throws DalException {
        daoSchool.createSchool(school);
    }

    @Override
    public void updateSchool(School school) throws DalException {
        daoSchool.updateSchool(school);
    }

    @Override
    public void deleteSchool(School school) throws DalException {
        daoSchool.deleteSchool(school);
    }

    @Override
    public List<Group> getAllGroups(int schoolID) throws DalException {
        return daoGroup.getAllGroups(schoolID);
    }

    @Override
    public Group createGroup(Group group) throws DalException {
        return daoGroup.createGroup(group);
    }

    @Override
    public void updateGroup(Group group) throws DalException {
        daoGroup.updateGroup(group);
    }

    @Override
    public void deleteGroup(Group group) throws DalException {
        daoGroup.deleteGroup(group);
    }

    @Override
    public List<User> getUsersInGroup(int id) throws DalException {
        return daoGroup.getUsersInGroup(id);
    }

    @Override
    public void addUsertoGroup(Group group, User user) throws DalException {
        daoGroup.addUsertoGroup(group, user);
    }

    @Override
    public void removeUserFromGroup(User user) throws DalException {
        daoGroup.removeUserFromGroup(user);
    }

    @Override
    public void assignCaseToGroup(Patient patient, Case assignedCase, Group group) throws DalException {
        daoCase.assignCaseToGroup(patient,assignedCase,group);
    }

    @Override
    public void addStudentQuestionAnswer(StudentQuestionnaireAnswer answer) throws DalException {
        daoStudentQuestion.addStudentQuestionAnswer(answer);
    }

    @Override
    public StudentQuestion getFirstStudentQuestion() throws DalException {
        var questionaireId=daoStudentQuestion.addQuestionaire();
        StudentQuestion question = daoStudentQuestion.getAllQuestions().get(0);
        question.setQuestionaireId(questionaireId);
        return question;
    }

    @Override
    public StudentQuestion getNextStudentQuestion(int id) throws DalException {
        List<StudentQuestion> questions = daoStudentQuestion.getAllQuestions();
        Optional<StudentQuestion> nextQuestion =
                questions.stream().filter(question -> question.getId() > id).min(Comparator.comparingInt(StudentQuestion::getId));
        if (nextQuestion.isPresent()) return nextQuestion.get();
        return null;
    }

    @Override
    public StudentQuestion getPreviousQuestion(int currentQuestionId) throws DalException {
        List<StudentQuestion> questions = daoStudentQuestion.getAllQuestions();
        Optional<StudentQuestion> previousQuestion =
                questions.stream().filter(question -> question.getId() < currentQuestionId).max(Comparator.comparingInt(StudentQuestion::getId));
        if (previousQuestion.isPresent()) return previousQuestion.get();
        return null;
    }

    @Override
    public StudentQuestionnaireAnswer getQuestionaireAnswer(int questionId, int questionaireId) throws DalException {
        return daoStudentQuestion.getQuestionaireAnswer(questionId,questionaireId);
    }

    @Override
    public void removeUserAndGroup(User user, Group group) throws DalException {
        daoGroup.removeUserAndGroup(user , group);
    }

    @Override
    public List<Case> getCasesAssignedTo(Group group) throws DalException {
        return daoCase.getCasesAssignedTo(group);
    }

    @Override
    public List<User> getAllUSERS(int schoolId ,String utype) throws DalException {
        return daoUser.getAllUSERS(schoolId , utype);
    }

    @Override
    public List<User> getALLUsers(int schoolid, String utype) throws DalException {
        return null;
    }


    @Override
    public Group getGroupOf(User student) throws DalException {
        return daoGroup.getGroupOf(student);
    }

    @Override
    public StudentQuestionnaire getQuestionnaireOf(Group group) throws DalException {
        return daoStudentQuestion.getQuestionnaireOf(group);
    }

    @Override
    public List<StudentQuestion> getQuestionnaireQuestions(int questionnaireId) throws DalException {
        return daoStudentQuestion.getQuestionnaireQuestions(questionnaireId);}
    public Patient getPatientOfCase(Case selectedCase, Group group) throws DalException {
        return daoPatient.getPatientOfCase(selectedCase, group);
    }

    @Override
    public void unassignCase(Case selectedItem) throws DalException {
        daoCase.unassignCase(selectedItem);
    }

    @Override
    public void markCaseAsGraded(Case selectedItem) throws DalException {
        daoCase.markCaseAsGraded(selectedItem);
    }

    @Override
    public void unmarkCaseAsGraded(Case selectedItem) throws DalException {
        daoCase.unmarkCaseAsGraded(selectedItem);
    }

    @Override
    public List<Case> getCasesGradedOf(Group group) throws DalException {
        return daoCase.getCasesGradedOf(group);
    }

    @Override
    public void addObservationToPatient(String text, Patient currentPatient) throws DalException {
        daoPatient.addObservation(text,currentPatient);
    }

    @Override
    public StudentQuestionnaire getQuestionnaire(int questionnaireId) throws DalException {
        return  daoStudentQuestion.getQuestionnaire(questionnaireId);
    }

    @Override
    public int getSickPatientId(Patient currentPatient, Case currentCase, Group currentGroup) throws DalException {
        return daoStudentQuestion.getSickPatientId(currentPatient,currentCase,currentGroup);
    }

    @Override
    public void updateQuestionnaire(StudentQuestionnaire questionnaire) throws DalException {
        daoStudentQuestion.updateQuestionnaireSickPatient(questionnaire);
    }

    @Override
    public int getQuestionnaireOf(int caseId, int groupId) throws DalException {
       return daoStudentQuestion.getQuestionnaireOf(caseId,groupId);
    }

}
