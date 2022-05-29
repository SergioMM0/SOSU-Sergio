package GUI.Controllers;

import BE.User;
import BLL.Exceptions.BLLException;
import DAL.Exceptions.DALException;
import GUI.Alerts.SoftAlert;
import GUI.Models.AdminMOD;
import GUI.Util.StaticData;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ManageUserCTLL {

    private AdminMOD model;
    private AdminCTLL adminCTLL;
    private int schoolId;
    private User teacher;
    private User student;
    private int operationType;

    public ManageUserCTLL(){
        model = AdminMOD.getInstance();
    }

    @FXML
    private TextField emailField;

    @FXML
    private TextField nameField;

    @FXML
    private void save(ActionEvent event) {
        switch(operationType){
            case 1:
                if(fieldsAreFilled()){
                    try{
                        User user = new User(schoolId,nameField.getText(),emailField.getText(), StaticData.getStudentType());
                        adminCTLL.addStudentToTable(model.addUser(user));
                        adminCTLL.refreshStudentsTable();
                        closeWindow();
                    }catch (DALException | BLLException exception){
                        SoftAlert.displayAlert(exception.getMessage());
                    }
                } break;
            case 2:
                if(fieldsAreFilled()){
                    try{
                        student.setName(nameField.getText());
                        student.setEmail(emailField.getText());
                        model.updateUser(student);
                        adminCTLL.updateStudentInTable(student);
                        adminCTLL.refreshStudentsTable();
                        closeWindow();
                    }catch (DALException | BLLException exception){
                        SoftAlert.displayAlert(exception.getMessage());
                    }
                } break;
            case 3:
                if(fieldsAreFilled()){
                    try{
                        User user = new User(schoolId,nameField.getText(),emailField.getText(),StaticData.getTeacherType());
                        adminCTLL.addTeacherToTable(model.addUser(user));
                        adminCTLL.refreshTeachersTable();
                        closeWindow();
                    }catch (DALException | BLLException exception){
                        SoftAlert.displayAlert(exception.getMessage());
                    }
                } break;
            case 4:
                if(fieldsAreFilled()){
                    try{
                        teacher.setName(nameField.getText());
                        teacher.setEmail(emailField.getText());
                        model.updateUser(teacher);
                        adminCTLL.updateTeacherInTable(teacher);
                        adminCTLL.refreshTeachersTable();
                        closeWindow();
                    }catch (DALException | BLLException dalException){
                        SoftAlert.displayAlert(dalException.getMessage());
                    }
                } break;
        }
    }

    @FXML
    private void cancel(ActionEvent event) {
        closeWindow();
    }

    private boolean fieldsAreFilled() {
        if (nameField.getText().isEmpty()) {
            SoftAlert.displayAlert("Please introduce a name for the student");
            return false;
        } else if (emailField.getText().isEmpty()) {
            SoftAlert.displayAlert("Please introduce an email for the student");
            return false;
        } else return true;
    }

    private void closeWindow(){
        Stage st = (Stage) emailField.getScene().getWindow();
        st.close();
    }

    protected void populateStudentFields(){
        nameField.setText(student.getName());
        emailField.setText(student.getEmail());
    }

    protected void populateTeacherFields(){
        nameField.setText(teacher.getName());
        emailField.setText(teacher.getName());
    }

    protected void setTeacher(User teacher) {
        this.teacher = teacher;
    }

    protected void setOperationType(int operationType) {
        this.operationType = operationType;
    }

    protected void setMainController(AdminCTLL adminCTLL) {
        this.adminCTLL = adminCTLL;
    }

    protected void setStudent(User student) {
        this.student = student;
    }

    protected void setSchoolId(int id){
        this.schoolId = id;
    }
}
