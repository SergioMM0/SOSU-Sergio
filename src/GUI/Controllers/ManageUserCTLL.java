package GUI.Controllers;

import BE.User;
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
        model = new AdminMOD();
    }

    @FXML
    private TextField emailField;

    @FXML
    private TextField nameField;

    @FXML
    void save(ActionEvent event) {
        switch(operationType){
            case 1:
                if(fieldsAreFilled()){
                    try{
                        User user = new User(schoolId,nameField.getText(),emailField.getText(), StaticData.getStudentType());
                        adminCTLL.addStudentToTable(model.addUser(user));
                        adminCTLL.refreshStudentsTable();
                        closeWindow();
                    }catch (DALException dalException){
                        SoftAlert.displayAlert(dalException.getMessage());
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
                    }catch (DALException dalException){
                        SoftAlert.displayAlert(dalException.getMessage());
                    }
                } break;
            case 3:
                if(fieldsAreFilled()){
                    try{
                        User user = new User(schoolId,nameField.getText(),emailField.getText(),StaticData.getTeacherType());
                        adminCTLL.addTeacherToTable(model.addUser(user));
                        adminCTLL.refreshTeachersTable();
                        closeWindow();
                    }catch (DALException dalException){
                        SoftAlert.displayAlert(dalException.getMessage());
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
                    }catch (DALException dalException){
                        SoftAlert.displayAlert(dalException.getMessage());
                    }
                } break;
        }
    }

    @FXML
    void cancel(ActionEvent event) {
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

    public void populateStudentFields(){
        nameField.setText(student.getName());
        emailField.setText(student.getEmail());
    }

    public void populateTeacherFields(){
        nameField.setText(teacher.getName());
        emailField.setText(teacher.getName());
    }

    public void setTeacher(User teacher) {
        this.teacher = teacher;
    }

    public void setOperationType(int operationType) {
        this.operationType = operationType;
    }

    public void setMainController(AdminCTLL adminCTLL) {
        this.adminCTLL = adminCTLL;
    }

    public void setStudent(User student) {
        this.student = student;
    }

    public void setSchoolId(int id){
        this.schoolId = id;
    }
}
