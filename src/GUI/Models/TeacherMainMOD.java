package GUI.Models;

import BE.Case;
import BE.Group;
import BE.Patient;
import BE.User;
import BLL.BLLFacade;
import BLL.BLLManager;
import DAL.util.DalException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TeacherMainMOD {

    private BLLFacade bllFacade;
    private ObservableList<Group> allGroups;
    private ObservableList<Case> allCases;
    private ObservableList<Case> casesAssigned;
    private ObservableList<Patient> allPatients;
    private ObservableList<User> allStudents;
    private ObservableList<User> groupParticipants;
    private ObservableList<Case> casesGraded;
    private static TeacherMainMOD instance;
    private Patient patientOfCase;

    public static TeacherMainMOD getInstance() {
        if (instance == null)
            instance = new TeacherMainMOD();
        return instance;
    }

    private TeacherMainMOD() {
        bllFacade = new BLLManager();
        allGroups = FXCollections.observableArrayList();
        allCases = FXCollections.observableArrayList();
        casesAssigned = FXCollections.observableArrayList();
        casesGraded = FXCollections.observableArrayList();
        allPatients = FXCollections.observableArrayList();
        allStudents = FXCollections.observableArrayList();
        groupParticipants = FXCollections.observableArrayList();
    }

    public ObservableList<Group> getAllGroups(int schoolID) throws DalException {
        allGroups.addAll(bllFacade.getAllGroups(schoolID));
        return allGroups;
    }

    public ObservableList<Case> getAllCases(int schoolID) throws DalException {
        allCases.addAll(bllFacade.getAllCases(schoolID));
        return allCases;
    }

    public ObservableList<Patient> getAllPatients(int schoolID) throws DalException {
        allPatients.addAll(bllFacade.getAllPatients(schoolID));
        return allPatients;
    }

    public void addPatientToList(Patient patient) {
        allPatients.add(patient);
    }

    public ObservableList<Patient> getObservablePatients() {
        return allPatients;
    }

    public void addCaseToList(Case newCase) {
        allCases.add(newCase);
    }

    public ObservableList<Case> getObservableCases() {
        return allCases;
    }

    public void updateCase(Case newCase) throws DalException {
        bllFacade.updateCase(newCase);
    }

    public void updateCaseInTable(Case oldCase) {
        for (Case c : allCases) {
            if (c.getName().equals(oldCase.getName())) {
                c.setName(oldCase.getName());
                c.setConditionDescription(oldCase.getConditionDescription());
                c.setCategory(oldCase.getCategory());
                c.setSubCategory(oldCase.getSubCategory());
            }
        }
    }

    public void addObservableStudent(User user) {
        allStudents.add(user);
    }

    public ObservableList<User> getObservableStudents() {
        return allStudents;
    }

    public void updateStudentInLists(User student) {
        for (User user : allStudents) {
            if (user.getId() == student.getId()) {
                user.setName(student.getName());
                user.setEmail(student.getEmail());
            }
        }
        for(Group g : allGroups){
            for(User user : g.getMembers()){
                if(user.getId() == student.getId()){
                    user.setName(student.getName());
                    user.setEmail(student.getEmail());
                }
            }
        }
    }

    public void deleteStudent(User student) throws DalException {
        bllFacade.deleteStudent(student);
    }

    public void deleteObservableStudent(User student) {
        allStudents.remove(student);
    }

    public ObservableList<User> getAllStudents(int schoolID) throws DalException {
        allStudents.addAll(bllFacade.getAllStudent(schoolID));
        return allStudents;
    }

    public void updatePatient(Patient patient) throws DalException {
        bllFacade.updatePatient(patient);
    }

    public void updatePatientInTable(Patient patient) {
        for (Patient p : allPatients) {
            if (p.getId() == patient.getId()) {
                p = patient;
            }
        }
    }

    public void addObservableGroup(Group group) {
        allGroups.add(group);
    }

    public ObservableList<Group> getObservableGroups() {
        return allGroups;
    }

    public void updateObservableGroup(Group group) {
        for (Group g : allGroups) {
            if (g.getId() == group.getId()) {
                g = group;
            }
        }
    }

    public ObservableList<User> getObservableParticipants(Group group) {
        groupParticipants.clear();
        groupParticipants.addAll(group.getMembers());
        return groupParticipants;
    }

    public void addStudentToGroup(Group group, User student) throws DalException {
        bllFacade.addStudentToGroup(group, student);
    }


    public void removeObservableParticipant(Group group, User user) {
        group.removeMember(user);
        updateObservableGroup(group);
    }

    public void removeParticipant(Group group, User user) throws DalException {
        bllFacade.removeParticipant(group, user);
    }

    public void deleteGroup(Group group) throws DalException {
        bllFacade.deleteGroup(group);
    }

    public void removeObservableGroup(Group group) {
        allGroups.remove(group);
    }

    public void deleteStudentFromGroups(User student) {
        for (Group group : allGroups) {
            group.removeMember(student);
        }
    }

    public void deleteCase(Case selectedCase) throws DalException {
        bllFacade.deleteCase(selectedCase);
    }

    public void deleteObservableCase(Case selectedItem) {
        allCases.remove(selectedItem);
    }

    public void deletePatient(Patient selectedPatient) throws DalException {
        bllFacade.deletePatient(selectedPatient);
    }

    public void deleteObservablePatient(Patient selectedItem) {
        allPatients.remove(selectedItem);
    }

    public void assignCaseToGroup(Case selectedCase, Group group, Patient patient) throws DalException {
        bllFacade.assignCaseToGroup(selectedCase, group, patient);
    }

    public ObservableList<Case> getCasesAssignedToGroup(Group group) throws DalException {
        casesAssigned.clear();
        casesAssigned.addAll(bllFacade.getCasesAssignedTo(group));
        return casesAssigned;
    }

    public ObservableList<Case> getObservableCasesAssigned() {
        return casesAssigned;
    }

    public Patient getPatientOfCaseInGroup(Case selectedCase, Group group) throws DalException {
        this.patientOfCase = bllFacade.getPatientOfCase(selectedCase, group);
        return patientOfCase;
    }
    public Patient getPatientOfCase(){
        return this.patientOfCase;
    }

    public void unassignCase(Case selectedItem) throws DalException{
        bllFacade.unassignCase(selectedItem);
    }

    public void deleteAssignedCaseInList(Case caseAssigned) {
        casesAssigned.remove(caseAssigned);
    }

    public void markCaseAsGraded(Case selectedItem) throws DalException {
        bllFacade.markCaseAsGraded(selectedItem);
    }

    public void unmarkCaseAsGraded(Case selectedItem) throws DalException {
        bllFacade.unmarkCaseAsGraded(selectedItem);
    }

    public void moveCaseToGradedList(Case selectedItem){
        casesAssigned.remove(selectedItem);
        casesGraded.add(selectedItem);
    }

    public ObservableList<Case> getObservableCasesGraded(){
        return casesGraded;
    }

    public void moveCaseToAssignedList(Case selectedItem) {
        casesGraded.remove(selectedItem);
        casesAssigned.add(selectedItem);
    }

    public ObservableList<Case> getCasesGradedOfGroup(Group group) throws DalException {
        casesGraded.clear();
        casesGraded.addAll(bllFacade.getCasesGradedOf(group));
        return casesGraded;
    }

    public void addObservationToPatient(String text, Patient currentPatient) throws DalException {
        bllFacade.addObservationToPatient(text,currentPatient);
    }

    public void clearLists(){
        allGroups.clear();
        allCases.clear();
        casesAssigned.clear();
        casesGraded.clear();
        allPatients.clear();
        allStudents.clear();
        groupParticipants.clear();
    }
}
