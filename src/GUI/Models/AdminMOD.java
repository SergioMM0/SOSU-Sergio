package GUI.Models;

import BE.School;
import BE.User;
import BLL.BLLManager;
import DAL.util.DalException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class AdminMOD {

    private static final AdminMOD AdminSingleton = new AdminMOD();
    private final BLLManager manager;
    private ObservableList<School> schools;
    private ObservableList<User> teachers ;
    private ObservableList<User> students ;
    private ObservableList<User> users;

    public AdminMOD(){
        manager = new BLLManager();
    }

    public static AdminMOD getInstance() {
        if (AdminSingleton == null) {
            return new AdminMOD();
        }
        return AdminSingleton;
    }

    public ObservableList<School> getAllSchools() throws DalException {
        schools = FXCollections.observableArrayList();

            schools.addAll(manager.getAllSchools());

        return schools;
    }

    public ObservableList<User> getAllSudents(int schoolid) throws DalException{
        students = FXCollections.observableArrayList();

       students.addAll(manager.getALLUsers(schoolid , "STUDENT"));

       return students;
    }

    public ObservableList<User> getAllTeachers(int schoolid) throws DalException{
        teachers = FXCollections.observableArrayList();

        teachers.addAll(manager.getALLUsers(schoolid , "TEACHER"));

        return teachers;
    }

    public void createSchools(String name) throws DalException {
         manager.createSchool(new School(1 , name));
         updatethelist();
    }

    public void deleteSchool(School school) throws DalException {
        manager.deleteSchool(school);
        updatethelist();
    }

    public void updateSchool(School school) throws DalException {
        manager.updateSchool(school);
        updatethelist();
    }

    public void updatethelist() throws DalException {
        schools.setAll(manager.getAllSchools());
    }

    public void createUser(User user) throws DalException {
        manager.addNewStudent(user);
        updatethelist();
    }

    public void removeUser(User user ) throws DalException {
        manager.deleteStudent(user);
        updatethelist();
    }

    public ObservableList<User> filter(ObservableList<User> allusers ,String q )throws DalException{
        allusers  = FXCollections.observableArrayList();
        allusers.addAll(manager.searchForUser(q));

       return allusers;
    }
    public ObservableList<User> getallusers(int schoolid , String utype) throws DalException {
      users = FXCollections.observableArrayList();
      //users.addAll(manager.getAllUSERS(schoolid , utype));
      if(utype.equals("TEACHER")){
          return teachers;
      }else {
          return students;
      }
    }

    public ObservableList<User> searchforUser(ObservableList<User> user, String search) {
        return manager.searchUser(user , search);
    }

}
