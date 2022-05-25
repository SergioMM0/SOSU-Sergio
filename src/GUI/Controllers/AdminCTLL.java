package GUI.Controllers;

import BE.School;
import BE.User;
import BLL.Exceptions.BLLException;
import DAL.Exceptions.DALException;
import GUI.Alerts.SoftAlert;
import GUI.Models.AdminMOD;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

public class AdminCTLL {

    @FXML
    private TextField filterField;

    @FXML
    private TableColumn<School, Integer> idColumn;

    @FXML
    private TableColumn<School, String> nameColumnSchool;

    @FXML
    private TableView<School> schoolTableView;

    @FXML
    private TableColumn<User, String> studentEmail;

    @FXML
    private TableColumn<User, String> studentName;

    @FXML
    private TableView<User> studentTableView;

    @FXML
    private TableColumn<User, String> teacherEmail;

    @FXML
    private TableColumn<User, String> teacherName;

    @FXML
    private TableView<User> teacherTableView;

    @FXML
    private Label welcomeLBL;

    private AdminMOD model;
    private User currentUser;
    private School currentSchool;
    private User currentTeacher;
    private User currentStudent;

    public AdminCTLL(){
        model = new AdminMOD();
    }

    public void initializeView(){
        initializeTables();
        populateSchools();
    }

    private void initializeTables() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumnSchool.setCellValueFactory(new PropertyValueFactory<>("name"));
        teacherName.setCellValueFactory(new PropertyValueFactory<>("name"));
        teacherEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        studentName.setCellValueFactory(new PropertyValueFactory<>("name"));
        studentEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
    }

    private void populateSchools(){
        try{
            schoolTableView.getItems().addAll(model.getAllSchools());
        }catch(DALException | BLLException exception){
            exception.printStackTrace();
            SoftAlert.displayAlert(exception.getMessage());
        }
    }

    @FXML
    void schoolIsSelected(MouseEvent event) {
        if(schoolTableView.getSelectionModel().getSelectedItem() != null){
            this.currentSchool = schoolTableView.getSelectionModel().getSelectedItem();
        }
    }

    @FXML
    void studentIsSelected(MouseEvent event) {
        if(studentTableView.getSelectionModel().getSelectedItem() != null){
            this.currentStudent = studentTableView.getSelectionModel().getSelectedItem();
        }
    }

    @FXML
    void teacherIsSelected(MouseEvent event) {
        if(teacherTableView.getSelectionModel().getSelectedItem() != null){
            this.currentTeacher = teacherTableView.getSelectionModel().getSelectedItem();
        }
    }

    @FXML
    void addSchool(ActionEvent event) {

    }

    @FXML
    void addStudent(ActionEvent event) {

    }

    @FXML
    void addTeacher(ActionEvent event) {

    }

    @FXML
    void deleteSchool(ActionEvent event) {

    }

    @FXML
    void deleteStudent(ActionEvent event) {

    }

    @FXML
    void deleteTeacher(ActionEvent event) {

    }

    @FXML
    void logOut(ActionEvent event) {

    }

    public void setUser(User logedUser) {
        this.currentUser = logedUser;
    }
}
