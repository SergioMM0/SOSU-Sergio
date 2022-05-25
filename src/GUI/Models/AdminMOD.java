package GUI.Models;

import BE.School;
import BE.User;
import BLL.BLLFacade;
import BLL.BLLManager;
import BLL.Exceptions.BLLException;
import DAL.Exceptions.DALException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class AdminMOD {

    private BLLFacade bllFacade;
    private ObservableList<School> allSchools;
    private ObservableList<User> allStudents;
    private ObservableList<User> allTeachers;

    public AdminMOD(){
        bllFacade = new BLLManager();
        allSchools = FXCollections.observableArrayList();
        allStudents = FXCollections.observableArrayList();
        allTeachers = FXCollections.observableArrayList();
    }

    public ObservableList<School> getAllSchools() throws DALException, BLLException {
        allSchools.addAll(bllFacade.getAllSchools());
        return allSchools;
    }

    public ObservableList<School> getObservableSchools(){
        return allSchools;
    }

    public School addSchool(School currentSchool) throws DALException {
        return bllFacade.addSchool(currentSchool);
    }

    public void deleteSchool(School currentSchool) throws DALException{
        bllFacade.deleteSchool(currentSchool);
    }

    public void addObservableSchool(School currentSchool) {
        allSchools.add(currentSchool);
    }

    public void removeObservableSchool(School currentSchool){
        allSchools.remove(currentSchool);
    }

    public User addStudent(User currentStudent) throws DALException{
        return bllFacade.addNewUser(currentStudent);
    }

    public void removeStudent(User currentStudent) throws DALException{
        bllFacade.deleteUser(currentStudent);
    }

    public void addObservableStudent(User currentStudent) {
        allStudents.add(currentStudent);
    }

    public void removeObservableStudent(User currentStudent){
        allStudents.remove(currentStudent);
    }

    public ObservableList<User> getObservableStudents(){
        return allStudents;
    }


    public User addTeacher(User currentTeacher) throws DALException{
        return bllFacade.addNewUser(currentTeacher);
    }

    public void removeTeacher(User currentTeacher) throws DALException{
        bllFacade.deleteUser(currentTeacher);
    }

    public void addObservableTeacher(User currentTeacher){
        allTeachers.add(currentTeacher);
    }

    public void removeObservableTeacher(User currentTeacher){
        allTeachers.remove(currentTeacher);
    }

    public ObservableList<User> getObservableTeachers(){
        return allTeachers;
    }
}
