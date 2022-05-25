package GUI.Controllers;

import BE.School;
import BE.User;
import BLL.Exceptions.BLLException;
import DAL.Exceptions.DALException;
import GUI.Alerts.SoftAlert;
import GUI.Models.AdminMOD;
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
    private ArrayList<Stage> listOfStages = new ArrayList<>();

    public AdminCTLL(){
        model = new AdminMOD();
    }

    public void initializeView(){
        initializeTables();
        populateSchools();
        setUPContextMenus();
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
            try{

            }catch (DALException dalException){
                SoftAlert.displayAlert(dalException.getMessage());
            }
            refreshSchoolTable();
        });

        updateTeacher.setOnAction(t -> {
            menu2.hide();
            try{

            }catch (DALException dalException){
                SoftAlert.displayAlert(dalException.getMessage());
            }
        });

        updateStudent.setOnAction(t -> {
            menu3.hide();
            try{

            }catch (DALException dalException){
                SoftAlert.displayAlert(dalException.getMessage());
            }
        });
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
        try{
            model.addObservableSchool(model.addSchool(currentSchool));
            refreshSchoolTable();
        }catch (DALException dalException){
            SoftAlert.displayAlert(dalException.getMessage());
        }
    }

    @FXML
    void addStudent(ActionEvent event) {
        try {
            model.addObservableStudent(model.addStudent(currentStudent));
            refreshStudentsTable();
        } catch (DALException dalException) {
            SoftAlert.displayAlert(dalException.getMessage());
        }
    }

    @FXML
    void addTeacher(ActionEvent event) {
        try{
            model.addObservableTeacher(model.addTeacher(currentTeacher));
            refreshTeachersTable();
        }catch (DALException dalException){
            SoftAlert.displayAlert(dalException.getMessage());
        }
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
            model.removeStudent(currentStudent);
            model.removeObservableStudent(currentStudent);
            refreshStudentsTable();
        }catch (DALException dalException){
            SoftAlert.displayAlert(dalException.getMessage());
        }
    }

    @FXML
    void deleteTeacher(ActionEvent event) {
        try{
            model.removeTeacher(currentTeacher);
            model.removeObservableTeacher(currentTeacher);
            refreshTeachersTable();
        }catch (DALException dalException){
            SoftAlert.displayAlert(dalException.getMessage());
        }
    }

    private void refreshSchoolTable() {
        schoolTableView.getItems().clear();
        schoolTableView.getItems().addAll(model.getObservableSchools());
    }

    private void refreshStudentsTable() {
        studentTableView.getItems().clear();
        studentTableView.getItems().addAll(model.getObservableStudents());
    }

    private void refreshTeachersTable(){
        teacherTableView.getItems().clear();
        teacherTableView.getItems().addAll(model.getObservableTeachers());
    }

    private void openView(String resource, String title, int width, int height, boolean resizable, int operationType) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource(resource));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert root != null;
        if (resource.equals("GUI/Views/CreatePatient.fxml")) {
            loader.<NewPatientCTLL>getController().setUser(logedUser);
            loader.<NewPatientCTLL>getController().setController(this);
        }
        root.getStylesheets().add("GUI/Views/CSS/GeneralCSS.css");
        Stage stage = new Stage();
        stage.setTitle(title);
        stage.setScene(new Scene(root, width, height));
        listOfStages.add(stage);
        stage.setResizable(resizable);
        stage.showAndWait();
    }

    @FXML
    void logOut(ActionEvent event) {
        closeWindows();
    }

    private void closeWindows() {
        for (Stage listOfStage : listOfStages) {
            listOfStage.close();
        }
        listOfStages.clear();
    }

    public void setUser(User logedUser) {
        this.currentUser = logedUser;
    }
}
