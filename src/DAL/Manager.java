package DAL;

import BE.*;
import DAL.DAOs.*;
import DAL.Exceptions.DALException;

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
    public List<Case> getAllCases(int schoolid) throws DALException {
        return daoCase.getAllCases(schoolid);
    }

    @Override
    public Case createCase(Case c) throws DALException {
        return daoCase.createCase(c);
    }

    @Override
    public void updateCase(Case c) throws DALException {
        daoCase.updateCase(c);
    }

    @Override
    public void deleteCase(Case c) throws DALException {
        daoCase.deleteCase(c);
    }

    @Override
    public User checkCredentials(String useremail, String password) throws DALException {
        return daoUser.checkCredentials(useremail, password);
    }

    @Override
    public List<User> getAllStudents(int schoolid) throws DALException {
        return daoUser.getAllStudents(schoolid);
    }

    @Override
    public void updateStudent(User student) throws DALException {
        daoUser.updateStudent(student);
    }

    @Override
    public void deleteStudent(User student) throws DALException {
        daoUser.deleteStudent(student);
    }

    @Override
    public User addNewStudent(User student) throws DALException {
        return daoUser.addStudent(student);
    }

    @Override
    public List<User> searchForUser(String query) throws DALException {
        return daoUser.searchForUser(query);
    }

    @Override
    public List<Patient> getAllPatients(int schoolid) throws DALException {
        return daoPatient.getAllPatients(schoolid);
    }

    @Override
    public Patient createPatient(Patient patient) throws DALException {
        return daoPatient.createPatient(patient);
    }

    @Override
    public void updatePatient(Patient patient) throws DALException {
        daoPatient.updatepatient(patient);
    }

    @Override
    public void deletePatient(Patient patient) throws DALException {
        daoPatient.deletePatient(patient);
    }

    @Override
    public List<School> getAllSchhol() throws DALException {
        return daoSchool.getAllSchhol();
    }

    @Override
    public void createSchool(School school) throws DALException {
        daoSchool.createSchool(school);
    }

    @Override
    public void updateSchool(School school) throws DALException {
        daoSchool.updateSchool(school);
    }

    @Override
    public void deleteSchool(School school) throws DALException {
        daoSchool.deleteSchool(school);
    }

    @Override
    public List<Group> getAllGroups(int schoolID) throws DALException {
        return daoGroup.getAllGroups(schoolID);
    }

    @Override
    public Group createGroup(Group group) throws DALException {
        return daoGroup.createGroup(group);
    }

    @Override
    public void updateGroup(Group group) throws DALException {
        daoGroup.updateGroup(group);
    }

    @Override
    public void deleteGroup(Group group) throws DALException {
        daoGroup.deleteGroup(group);
    }

    @Override
    public List<User> getUsersInGroup(int id) throws DALException {
        return daoGroup.getUsersInGroup(id);
    }

    @Override
    public void addUsertoGroup(Group group, User user) throws DALException {
        daoGroup.addUsertoGroup(group, user);
    }

    @Override
    public void removeUserFromGroup(User user) throws DALException {
        daoGroup.removeUserFromGroup(user);
    }

    @Override
    public void assignCaseToGroup(Patient patient, Case assignedCase, Group group) throws DALException {
        daoCase.assignCaseToGroup(patient,assignedCase,group);
    }

    @Override
    public void addStudentQuestionAnswer(StudentQuestionnaireAnswer answer) throws DALException {
        daoStudentQuestion.addStudentQuestionAnswer(answer);
    }

    @Override
    public StudentQuestion getFirstStudentQuestion() throws DALException {
        var questionaireId=daoStudentQuestion.addQuestionaire();
        StudentQuestion question = daoStudentQuestion.getAllQuestions().get(0);
        question.setQuestionaireId(questionaireId);
        return question;
    }

    @Override
    public StudentQuestion getNextStudentQuestion(int id) throws DALException {
        List<StudentQuestion> questions = daoStudentQuestion.getAllQuestions();
        Optional<StudentQuestion> nextQuestion =
                questions.stream().filter(question -> question.getId() > id).min(Comparator.comparingInt(StudentQuestion::getId));
        if (nextQuestion.isPresent()) return nextQuestion.get();
        return null;
    }

    @Override
    public StudentQuestion getPreviousQuestion(int currentQuestionId) throws DALException {
        List<StudentQuestion> questions = daoStudentQuestion.getAllQuestions();
        Optional<StudentQuestion> previousQuestion =
                questions.stream().filter(question -> question.getId() < currentQuestionId).max(Comparator.comparingInt(StudentQuestion::getId));
        if (previousQuestion.isPresent()) return previousQuestion.get();
        return null;
    }

    @Override
    public StudentQuestionnaireAnswer getQuestionaireAnswer(int questionId, int questionaireId) throws DALException {
        return daoStudentQuestion.getQuestionaireAnswer(questionId,questionaireId);
    }

    @Override
    public void removeParticipant(User user, Group group) throws DALException {
        daoGroup.removeParticipant(user , group);
    }

    @Override
    public List<Case> getCasesAssignedTo(Group group) throws DALException {
        return daoCase.getCasesAssignedTo(group);
    }

    @Override
    public List<User> getAllUSERS(int schoolId ,String utype) throws DALException {
        return daoUser.getAllUSERS(schoolId , utype);
    }

    @Override
    public List<User> getALLUsers(int schoolid, String utype) throws DALException {
        return null;
    }


    @Override
    public Group getGroupOf(User student) throws DALException {
        return daoGroup.getGroupOf(student);
    }

    @Override
    public StudentQuestionnaire getQuestionnaireOf(Group group) throws DALException {
        return daoStudentQuestion.getQuestionnaireOf(group);
    }

    @Override
    public List<StudentQuestion> getQuestionnaireQuestions(int questionnaireId) throws DALException {
        return daoStudentQuestion.getQuestionnaireQuestions(questionnaireId);}
    public Patient getPatientOfCase(Case selectedCase, Group group) throws DALException {
        return daoPatient.getPatientOfCase(selectedCase, group);
    }

    @Override
    public void unassignCase(Case selectedItem) throws DALException {
        daoCase.unassignCase(selectedItem);
    }

    @Override
    public void markCaseAsGraded(Case selectedItem) throws DALException {
        daoCase.markCaseAsGraded(selectedItem);
    }

    @Override
    public void unmarkCaseAsGraded(Case selectedItem) throws DALException {
        daoCase.unmarkCaseAsGraded(selectedItem);
    }

    @Override
    public List<Case> getCasesGradedOf(Group group) throws DALException {
        return daoCase.getCasesGradedOf(group);
    }

    @Override
    public void addObservationToPatient(String text, Patient currentPatient) throws DALException {
        daoPatient.addObservation(text,currentPatient);
    }

    @Override
    public StudentQuestionnaire getQuestionnaire(int questionnaireId) throws DALException {
        return  daoStudentQuestion.getQuestionnaire(questionnaireId);
    }

    @Override
    public int getSickPatientId(Patient currentPatient, Case currentCase, Group currentGroup) throws DALException {
        return daoStudentQuestion.getSickPatientId(currentPatient,currentCase,currentGroup);
    }

    @Override
    public void updateQuestionnaire(StudentQuestionnaire questionnaire) throws DALException {
        daoStudentQuestion.updateQuestionnaireSickPatient(questionnaire);
    }

    @Override
    public int getQuestionnaireOf(int caseId, int groupId) throws DALException {
       return daoStudentQuestion.getQuestionnaireOf(caseId,groupId);
    }

}
