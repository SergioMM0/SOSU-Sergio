package DAL;

import BE.*;
import DAL.DAOs.*;
import DAL.Exceptions.DALException;
import java.util.List;

public class DALManager implements DALFacade {

    private final DAOCase daoCase;
    private final DAOUser daoUser;
    private final DAOPatient daoPatient;
    private final DAOSchool daoSchool;
    private final DAOGroup daoGroup;
    private final DAOCategory daoCategories;
    private final DAOSubcategory daoSubcategory;
    private final DAOFunctionalAbility daoFunctionalAbility;
    private final DAOHealthCondition daoHealthCondition;

    public DALManager() {
        daoCase = new DAOCase();
        daoUser = new DAOUser();
        daoPatient = new DAOPatient();
        daoSchool = new DAOSchool();
        daoGroup = new DAOGroup();
        daoCategories = new DAOCategory();
        daoSubcategory = new DAOSubcategory();
        daoFunctionalAbility = new DAOFunctionalAbility();
        daoHealthCondition = new DAOHealthCondition();
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
    public void updateUser(User student) throws DALException {
        daoUser.updateUser(student);
    }

    @Override
    public void deleteUser(User user) throws DALException {
        daoUser.deleteUser(user);
    }

    @Override
    public User addNewUser(User student) throws DALException {
        return daoUser.addUser(student);
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
    public void addUsertoGroup(Group group, User user) throws DALException {
        daoGroup.addUsertoGroup(group, user);
    }

    @Override
    public void assignCaseToGroup(Patient patient, Case assignedCase, Group group) throws DALException {
        daoCase.assignCaseToGroup(patient,assignedCase,group);
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
    public Group getGroupOf(User student) throws DALException {
        return daoGroup.getGroupOf(student);
    }

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
    public List<School> getAllSchools() throws DALException {
        return daoSchool.getAllSchools();
    }

    @Override
    public School addSchool(School currentSchool) throws DALException {
        return daoSchool.addSchool(currentSchool);
    }

    @Override
    public void deleteSchool(School currentSchool) throws DALException {
        daoSchool.deleteSchool(currentSchool);
    }

    @Override
    public List<User> getAllUsers(School currentSchool) throws DALException {
        return daoUser.getAllUsers(currentSchool);
    }

    @Override
    public List<Category> getAllCategoriesHC() throws DALException {
        return daoCategories.getAllCategoriesHC();
    }

    @Override
    public List<Category> getAllCategoriesFA() throws DALException {
        return daoCategories.getAllCategoriesFA();
    }

    @Override
    public List<Subcategory> getSubcategoriesFA(Category currentCategory,Patient currentPatient) throws DALException {
        return daoSubcategory.getSubcategoriesFA(currentCategory,currentPatient);
    }

    @Override
    public List<Subcategory> getSubcategoriesHC(Category currentCategory,Patient currentPatient) throws DALException {
        return daoSubcategory.getSubcategoriesHC(currentCategory,currentPatient);
    }

    @Override
    public FunctionalAbility getFunctionalAbility(Subcategory subcategory, Patient patient) throws DALException {
        return daoFunctionalAbility.getFunctionalAbility(subcategory,patient);
    }

    @Override
    public HealthCondition getHealthCondition(Subcategory subcategory, Patient patient) throws DALException {
        return daoHealthCondition.getHealthCondition(subcategory,patient);
    }

    @Override
    public void updateFunctionalAbility(FunctionalAbility currentFunctionalAbility, Patient currentPatient,
                                        Subcategory currentSubcategory) throws DALException {
        daoFunctionalAbility.updateFunctionalAbility(currentFunctionalAbility,currentSubcategory,currentPatient);
    }

    @Override
    public void addFunctionalAbility(FunctionalAbility currentFunctionalAbility, Patient currentPatient, Subcategory currentSubcategory) throws DALException {
        daoFunctionalAbility.addFunctionalAbility(currentFunctionalAbility,currentSubcategory,currentPatient);
    }

    @Override
    public void addHealthCondition(HealthCondition currentHealthCondition, Subcategory subcategory, Patient patient) throws DALException {
        daoHealthCondition.addHealthCondition(subcategory,currentHealthCondition,patient);
    }

    @Override
    public void updateHealthCondition(HealthCondition healthCondition, Subcategory subcategory, Patient patient) throws DALException {
        daoHealthCondition.updateHealthCondition(healthCondition,subcategory,patient);
    }

    @Override
    public Case duplicateCase(Case currentCase) throws DALException {
        return daoCase.duplicateCase(currentCase);
    }

    @Override
    public Patient duplicatePatient(Patient currentPatient) throws DALException {
        return daoPatient.duplicatePatient(currentPatient);
    }
}
