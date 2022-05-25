package GUI.Models;

import BE.School;
import BE.User;
import BLL.BLLFacade;
import BLL.BLLManager;
import BLL.Exceptions.BLLException;
import DAL.Exceptions.DALException;
import GUI.Util.StaticData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

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

    public void getAllUsers(School currentSchool) throws DALException{
        allTeachers.clear();
        allStudents.clear();
        List<User> allUsers = bllFacade.getAllUsers(currentSchool);
        for(User user : allUsers){
            if(user.getUserType() == StaticData.getTeacherType()){
                allTeachers.add(user);
            }
            else{
                allStudents.add(user);
            }
        }
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

    public User addUser(User currentStudent) throws DALException{
        return bllFacade.addNewUser(currentStudent);
    }

    public void removeUser(User currentStudent) throws DALException{
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

    public void addObservableTeacher(User currentTeacher){
        allTeachers.add(currentTeacher);
    }

    public void removeObservableTeacher(User currentTeacher){
        allTeachers.remove(currentTeacher);
    }

    public ObservableList<User> getObservableTeachers(){
        return allTeachers;
    }

    public void updateUser(User user)throws DALException{
        bllFacade.updateUser(user);
    }

    public void updateObservableStudent(User student) {
        for(User  user : allStudents){
            if(user.getId() == student.getId()){
                user = student;
            }
        }
    }

    public void updateObservableTeacher(User teacher){
        for(User user : allTeachers){
            if(user.getId() == teacher.getId()){
                user = teacher;
            }
        }
    }

    public void clearLists() {
        allTeachers.clear();
        allStudents.clear();
        allSchools.clear();
    }
}
