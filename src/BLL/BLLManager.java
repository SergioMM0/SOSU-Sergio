package BLL;

import BE.*;
import BLL.Exceptions.BLLException;
import DAL.DALFacade;
import DAL.DALManager;
import DAL.Exceptions.DALException;
import GUI.Util.StaticData;

import java.security.InvalidParameterException;
import java.util.List;

public class BLLManager implements BLLFacade {

    private DALFacade dalFacade;

    public BLLManager() {
        dalFacade = new DALManager();
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
    public Case createCase(Case newCase) throws DALException, BLLException {
        try{
            return dalFacade.createCase(newCase);
        }catch (InvalidParameterException invalidParameterException){
            throw new BLLException("The case already exists in the system", invalidParameterException);
        }
    }

    @Override
    public Patient createPatient(Patient patient) throws DALException, BLLException {
        try{
            return dalFacade.createPatient(patient);
        }catch (InvalidParameterException invalidParameterException){
            throw new BLLException("The patient already exists in the system", invalidParameterException);
        }
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
    public User addNewUser(User user) throws DALException, BLLException{
        try{
            return dalFacade.addNewUser(user);
        }catch (InvalidParameterException invalidParameterException){
            throw new BLLException("User already exists in the system", invalidParameterException);
        }
    }

    @Override
    public void updateUser(User student) throws DALException, BLLException {
        try{
            dalFacade.updateUser(student);
        }catch(InvalidParameterException invalidParameterException){
            throw new BLLException("User already exists in the system", invalidParameterException);
        }

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
    public void updatePatient(Patient patient) throws DALException, BLLException {
        try{
            dalFacade.updatePatient(patient);
        }catch (InvalidParameterException invalidParameterException){
            throw new BLLException("The patient already exists in the Database", invalidParameterException);
        }
    }

    @Override
    public Group createNewGroup(Group group) throws DALException, BLLException {
        try{
            return dalFacade.createGroup(group);
        }catch (InvalidParameterException invalidParameterException){
            throw new BLLException("The group already exists", invalidParameterException);
        }
    }

    @Override
    public void updateGroup(Group selectedGroup) throws DALException, BLLException {
        try{
            dalFacade.updateGroup(selectedGroup);
        }catch (InvalidParameterException invalidParameterException){
            throw new BLLException("The name of the group already exists in the system", invalidParameterException);
        }
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
    }

    @Override
    public void updateCase(Case newCase) throws DALException, BLLException {
        try{
            dalFacade.updateCase(newCase);
        }catch (InvalidParameterException invalidParameterException){
            throw new BLLException("The name of the case already exists in the system", invalidParameterException);
        }
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

    @Override
    public List<User> getAllUsers(School currentSchool) throws DALException {
        return dalFacade.getAllUsers(currentSchool);
    }

    @Override
    public List<Category> getAllCategoriesHC() throws DALException {
        return dalFacade.getAllCategoriesHC();
    }

    @Override
    public List<Category> getAllCategoriesFA() throws DALException {
        return dalFacade.getAllCategoriesFA();
    }

    @Override
    public List<Subcategory> getSubcategoriesFA(Category currentCategory,Patient currentPatient) throws DALException {
        return dalFacade.getSubcategoriesFA(currentCategory,currentPatient);
    }

    @Override
    public List<Subcategory> getSubcategoriesHC(Category currentCategory,Patient currentPatient) throws DALException {
        return dalFacade.getSubcategoriesHC(currentCategory,currentPatient);
    }

    @Override
    public FunctionalAbility getFunctionalAbility(Subcategory subcategory, Patient patient) throws DALException {
        FunctionalAbility functionalAbility = dalFacade.getFunctionalAbility(subcategory,patient);
        if( functionalAbility == null){
            return new FunctionalAbility(StaticData.isEditing());
        }
        else return functionalAbility;
    }

    @Override
    public HealthCondition getHealthCondition(Subcategory subcategory, Patient patient) throws DALException {
        HealthCondition healthCondition = dalFacade.getHealthCondition(subcategory,patient);
        if(healthCondition == null){
            return new HealthCondition(StaticData.isEditing());
        }
        else return healthCondition;
    }

    @Override
    public void updateFunctionalAbility(FunctionalAbility currentFunctionalAbility, Patient currentPatient, Subcategory currentSubcategory) throws DALException{
        dalFacade.updateFunctionalAbility(currentFunctionalAbility,currentPatient, currentSubcategory);
    }

    @Override
    public void addFunctionalAbility(FunctionalAbility currentFunctionalAbility, Patient currentPatient, Subcategory currentSubcategory) throws DALException {
        dalFacade.addFunctionalAbility(currentFunctionalAbility,currentPatient,currentSubcategory);
    }

    @Override
    public void addHealthCondition(HealthCondition currentHealthCondition, Subcategory subcategory, Patient patient) throws DALException {
        dalFacade.addHealthCondition(currentHealthCondition,subcategory,patient);
    }

    @Override
    public void updateHealthCondition(HealthCondition healthCondition, Subcategory subcategory, Patient patient) throws DALException {
        dalFacade.updateHealthCondition(healthCondition,subcategory,patient);
    }

    @Override
    public Case duplicateCase(Case currentCase) throws DALException, BLLException {
        Case duplicated = new Case(
                currentCase.getName() + " copy",
                currentCase.getConditionDescription(),
                currentCase.getSchoolID(),
                currentCase.isCopy()
        );
        try{
            duplicated = dalFacade.duplicateCase(duplicated);
        }catch (InvalidParameterException invalidParameterException){
            throw new BLLException("The case is already duplicated, it can't be duplicated", new InvalidParameterException());
        }
        return duplicated;
    }

    @Override
    public Patient duplicatePatient(Patient currentPatient) throws DALException, BLLException {
        Patient duplicated = new Patient(
                currentPatient.getFirst_name() + " copy",
                currentPatient.getLast_name(),
                currentPatient.getDateOfBirth(),
                currentPatient.getGender(),
                null,
                currentPatient.getSchoolId()
        );
        try{
            duplicated = dalFacade.duplicatePatient(duplicated);
        }catch (InvalidParameterException invalidParameterException){
            throw new BLLException("The case is already duplicated, it can't be duplicated", new InvalidParameterException());
        }
        return duplicated;
    }

    @Override
    public School updateSchool(School currenSchool) throws DALException {
        return dalFacade.updateSchool(currenSchool);
    }

    @Override
    public List<User> getParticipantsOf(Group group) throws DALException {
        return dalFacade.getParticipantsOf(group);
    }
}
