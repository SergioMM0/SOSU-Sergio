package GUI.Controllers;

import BE.School;
import BE.User;
import BLL.Exceptions.BLLException;
import DAL.Exceptions.DALException;
import GUI.Alerts.SoftAlert;
import GUI.Models.AdminMOD;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class AdminCTLL {

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
    private ArrayList<Stage> listOfStages = new ArrayList<>();

    public AdminCTLL(){
        model = AdminMOD.getInstance();
    }

    public void initializeView(){
        initializeTables();
        populateSchools();
        setUPContextMenus();
        setUPLabel();
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

    private void setUPContextMenus() {
        ContextMenu menu1 = new ContextMenu();
        ContextMenu menu2 = new ContextMenu();
        ContextMenu menu3 = new ContextMenu();

        MenuItem updateSchool = new MenuItem("Update school");
        MenuItem updateTeacher = new MenuItem("Update teacher");
        MenuItem updateStudent= new MenuItem("Update student");

        menu1.getItems().add(updateSchool);
        menu2.getItems().add(updateTeacher);
        menu3.getItems().add(updateStudent);

        schoolTableView.addEventHandler(MouseEvent.MOUSE_CLICKED, t -> {
            if (t.getButton() == MouseButton.SECONDARY) {
                menu1.show(schoolTableView, t.getScreenX(), t.getScreenY());
            } else menu1.hide();
        });

        teacherTableView.addEventHandler(MouseEvent.MOUSE_CLICKED, t -> {
            if (t.getButton() == MouseButton.SECONDARY) {
                menu2.show(teacherTableView, t.getScreenX(), t.getScreenY());
            } else menu2.hide();
        });

        studentTableView.addEventHandler(MouseEvent.MOUSE_CLICKED, t -> {
            if (t.getButton() == MouseButton.SECONDARY) {
                menu3.show(studentTableView, t.getScreenX(), t.getScreenY());
            } else menu3.hide();
        });

        updateSchool.setOnAction(t -> {
            menu1.hide();
            if(schoolTableView.getSelectionModel().getSelectedItem() != null){
                openView("GUI/Views/ManageSchool.fxml","Add new school", 400,220,2);
            }
        });

        updateTeacher.setOnAction(t -> {
            menu2.hide();
            if(currentSchool != null){
                openView("GUI/Views/ManageUser.fxml","Add new teacher", 400,220,4);
            }else SoftAlert.displayAlert("Please select a School");

        });

        updateStudent.setOnAction(t -> {
            menu3.hide();
            if(currentSchool != null){
                openView("GUI/Views/ManageUser.fxml","Add new student", 400,220,2);
            }else SoftAlert.displayAlert("Please select a School");
        });
    }

    @FXML
    void schoolIsSelected(MouseEvent event) {
        if(schoolTableView.getSelectionModel().getSelectedItem() != null){
            this.currentSchool = schoolTableView.getSelectionModel().getSelectedItem();
            try{
                model.getAllUsers(currentSchool);
                    refreshTeachersTable();
                    refreshStudentsTable();

            }catch (DALException dalException){
                SoftAlert.displayAlert(dalException.getMessage());
            }
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
        openView("GUI/Views/ManageSchool.fxml","Add new school", 400,220,1);
    }

    @FXML
    void addStudent(ActionEvent event) {
        if(currentSchool!=null){
            openView("GUI/Views/ManageUser.fxml","Add new student", 400,220,1);
        }else SoftAlert.displayAlert("Please select a school");
    }

    @FXML
    void addTeacher(ActionEvent event) {
        if(currentSchool!=null){
            openView("GUI/Views/ManageUser.fxml","Add new teacher", 400,220,3);
        }else SoftAlert.displayAlert("PLease select a school");
    }

    @FXML
    void deleteSchool(ActionEvent event) {
        try{
            model.deleteSchool(currentSchool);
            model.removeObservableSchool(currentSchool);
            refreshSchoolTable();
        }catch (DALException dalException){
            SoftAlert.displayAlert(dalException.getMessage());
        }
    }

    @FXML
    void deleteStudent(ActionEvent event) {
        try{
            model.removeUser(currentStudent);
            model.removeObservableStudent(currentStudent);
            refreshStudentsTable();
        }catch (DALException dalException){
            SoftAlert.displayAlert(dalException.getMessage());
        }
    }

    @FXML
    void deleteTeacher(ActionEvent event) {
        try{
            model.removeUser(currentTeacher);
            model.removeObservableTeacher(currentTeacher);
            refreshTeachersTable();
        }catch (DALException dalException){
            SoftAlert.displayAlert(dalException.getMessage());
        }
    }

    protected void refreshSchoolTable() {
        schoolTableView.getItems().clear();
        schoolTableView.getItems().addAll(model.getObservableSchools());
    }

    protected void refreshStudentsTable() {
        studentTableView.getItems().clear();
        studentTableView.getItems().addAll(model.getObservableStudents());
    }

    protected void refreshTeachersTable(){
        teacherTableView.getItems().clear();
        teacherTableView.getItems().addAll(model.getObservableTeachers());
    }

    private void setUPLabel() {
        welcomeLBL.setText("Welcome back " + currentUser.getName());
    }

    public void addStudentToTable(User addUser) {
        model.addObservableStudent(addUser);
    }

    public void updateStudentInTable(User student) {
        model.updateObservableStudent(student);
    }

    public void addTeacherToTable(User addUser) {
        model.addObservableTeacher(addUser);
    }

    public void updateTeacherInTable(User teacher) {
        model.updateObservableTeacher(teacher);
    }

    private void openView(String resource, String title, int width, int height,int operationType) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource(resource));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert root != null;
        root.getStylesheets().add("GUI/Views/CSS/GeneralCSS.css");
        if (resource.equals("GUI/Views/ManageUser.fxml") && operationType == 1) {
            loader.<ManageUserCTLL>getController().setSchoolId(currentSchool.getId());
            loader.<ManageUserCTLL>getController().setMainController(this);
            loader.<ManageUserCTLL>getController().setOperationType(operationType);
        }
        if (resource.equals("GUI/Views/ManageUser.fxml") && operationType == 2) {
            loader.<ManageUserCTLL>getController().setStudent(currentStudent);
            loader.<ManageUserCTLL>getController().setMainController(this);
            loader.<ManageUserCTLL>getController().setOperationType(operationType);
            loader.<ManageUserCTLL>getController().populateStudentFields();
        }
        if (resource.equals("GUI/Views/ManageUser.fxml") && operationType == 3) {
            loader.<ManageUserCTLL>getController().setSchoolId(currentSchool.getId());
            loader.<ManageUserCTLL>getController().setMainController(this);
            loader.<ManageUserCTLL>getController().setOperationType(operationType);
        }
        if (resource.equals("GUI/Views/ManageUser.fxml") && operationType == 4) {
            loader.<ManageUserCTLL>getController().setTeacher(currentTeacher);
            loader.<ManageUserCTLL>getController().setMainController(this);
            loader.<ManageUserCTLL>getController().setOperationType(operationType);
            loader.<ManageUserCTLL>getController().populateTeacherFields();
        }
        if (resource.equals("GUI/Views/ManageSchool.fxml") && operationType == 1) {
            loader.<ManageSchoolCTLL>getController().setOperationType(operationType);
            loader.<ManageSchoolCTLL>getController().setMainController(this);
        }
        if (resource.equals("GUI/Views/ManageSchool.fxml") && operationType == 2) {
            loader.<ManageSchoolCTLL>getController().setSchool(currentSchool);
            loader.<ManageSchoolCTLL>getController().setMainController(this);
            loader.<ManageSchoolCTLL>getController().setOperationType(operationType);
            loader.<ManageSchoolCTLL>getController().initializeView();
        }
        Stage stage = new Stage();
        stage.setTitle(title);
        stage.setScene(new Scene(root, width, height));
        listOfStages.add(stage);
        stage.setResizable(false);
        stage.showAndWait();
    }

    @FXML
    void logOut(ActionEvent event) {
        closeWindows();
        Parent root1;
        Stage stage = (Stage) schoolTableView.getScene().getWindow();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../Views/Login.fxml"));
            root1 = (Parent) fxmlLoader.load();
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void closeWindows() {
        for (Stage listOfStage : listOfStages) {
            listOfStage.close();
        }
        listOfStages.clear();
        model.clearLists();
    }

    public void setUser(User user) {this.currentUser = user;
    }
}
