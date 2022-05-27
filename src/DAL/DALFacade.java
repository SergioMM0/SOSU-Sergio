package DAL;

import BE.*;
import DAL.Exceptions.DALException;

import java.util.List;

public interface DALFacade {

    List<Case> getAllCases(int schoolid) throws DALException;

    Case createCase(Case c) throws DALException;

    void updateCase(Case c) throws DALException;

    void deleteCase(Case c) throws DALException;

    User checkCredentials(String useremail, String password) throws DALException;

    List<User> getAllStudents(int schoolid) throws DALException;

    void updateUser(User user) throws DALException;

    void deleteUser(User user) throws DALException;

    User addNewUser(User user) throws DALException;

    List<Patient> getAllPatients(int schoolid) throws DALException;

    Patient createPatient(Patient patient) throws DALException;

    void updatePatient(Patient patient) throws DALException;

    void deletePatient(Patient patient) throws DALException;

    List<Group> getAllGroups(int schoolID) throws DALException;

    Group createGroup(Group group) throws DALException;

    void updateGroup(Group group) throws DALException;

    void deleteGroup(Group group) throws DALException;

    void addUsertoGroup(Group group, User user) throws DALException;

    void assignCaseToGroup(Patient p, Case c, Group g) throws DALException;

    void removeParticipant(User user, Group group) throws DALException;

    List<Case> getCasesAssignedTo(Group group) throws DALException;

    Group getGroupOf(User student) throws DALException;

    Patient getPatientOfCase(Case selectedCase, Group group) throws DALException;

    void unassignCase(Case selectedItem) throws DALException;

    void markCaseAsGraded(Case selectedItem) throws DALException;

    void unmarkCaseAsGraded(Case selectedItem) throws DALException;

    List<Case> getCasesGradedOf(Group group) throws DALException;

    void addObservationToPatient(String text, Patient currentPatient) throws DALException;

    List<School> getAllSchools() throws DALException;

    School addSchool(School currentSchool) throws DALException;

    void deleteSchool(School currentSchool) throws DALException;

    List<User> getAllUsers(School currentSchool) throws DALException;

    List<Category> getAllCategoriesHC() throws DALException;

    List<Category> getAllCategoriesFA() throws DALException;

    List<Subcategory> getSubcategoriesFA(Category currentCategory,Patient currentPatient) throws DALException;

    List<Subcategory> getSubcategoriesHC(Category currentCategory,Patient currentPatient) throws DALException;

    FunctionalAbility getFunctionalAbility(Subcategory subcategory, Patient patient) throws DALException;

    HealthCondition getHealthCondition(Subcategory subcategory, Patient patient) throws DALException;
}
