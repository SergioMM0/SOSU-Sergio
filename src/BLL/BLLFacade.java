package BLL;

import BE.*;
import BLL.Exceptions.BLLException;
import DAL.Exceptions.DALException;

import java.util.List;

public interface BLLFacade {

    User checkCredentials(String email, String password) throws DALException, BLLException;

    Case createCase(Case newCase) throws DALException;

    Patient createPatient(Patient patient) throws DALException;

    List<Group> getAllGroups(int schoolID) throws DALException;

    List<Case> getAllCases(int schoolID) throws DALException;

    List<Patient> getAllPatients(int schoolID) throws DALException;

    User addNewUser(User user) throws DALException;

    void updateUser(User student) throws DALException;

    void deleteUser(User user) throws DALException;

    List<User> getAllStudent(int schoolID) throws DALException;

    void updatePatient(Patient patient) throws DALException;

    Group createNewGroup(Group group) throws DALException;

    void updateGroup(Group selectedGroup) throws DALException;

    void addStudentToGroup(Group group, User student) throws DALException;

    void deleteGroup(Group group) throws DALException;

    void removeParticipant(Group group, User user) throws DALException;

    void updateCase(Case newCase) throws DALException;

    void deleteCase(Case selectedCase) throws DALException;

    void deletePatient(Patient selectedPatient) throws DALException;

    void assignCaseToGroup(Case selectedCase, Group group, Patient patient) throws DALException;

    List<Case> getCasesAssignedTo(Group group) throws DALException;

    Group getGroupOf(User student) throws DALException;

    Patient getPatientOfCase(Case selectedCase, Group group) throws DALException;

    void unassignCase(Case selectedItem) throws DALException;

    void markCaseAsGraded(Case selectedItem) throws DALException;

    void unmarkCaseAsGraded(Case selectedItem) throws DALException;

    List<Case> getCasesGradedOf(Group group) throws DALException;

    void addObservationToPatient(String text, Patient patient) throws DALException;

    List<School> getAllSchools() throws DALException,BLLException;

    School addSchool(School currentSchool) throws DALException;

    void deleteSchool(School currentSchool) throws DALException;

    List<User> getAllUsers(School currentSchool) throws DALException;

    List<Category> getAllCategoriesHC() throws DALException;

    List<Category> getAllCategoriesFA() throws DALException;

    List<Subcategory> getSubcategoriesFA(Category currentCategory,Patient currentPatient) throws DALException;

    List<Subcategory> getSubcategoriesHC(Category currentCategory,Patient currentPatient) throws DALException;

    FunctionalAbility getFunctionalAbility(Subcategory subcategory, Patient patient) throws DALException;

    HealthCondition getHealthCondition(Subcategory subcategory, Patient patient) throws DALException;

    void updateFunctionalAbility(FunctionalAbility currentFunctionalAbility, Patient currentPatient, Subcategory currentSubcategory) throws DALException;

    void addFunctionalAbility(FunctionalAbility currentFunctionalAbility, Patient currentPatient, Subcategory currentSubcategory) throws DALException;

    void addHealthCondition(HealthCondition currentHealthCondition, Subcategory subcategory, Patient patient) throws DALException;

    void updateHealthCondition(HealthCondition healthCondition, Subcategory subcategory, Patient patient) throws DALException;
}


