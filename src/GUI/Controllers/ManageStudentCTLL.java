package GUI.Controllers;

import BE.User;
import BLL.Exceptions.BLLException;
import DAL.Exceptions.DALException;
import GUI.Alerts.SoftAlert;
import GUI.Models.ManageStudentMOD;
import GUI.Util.StaticData;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.Locale;

public class ManageStudentCTLL {

    @FXML
    private TextField emailField;

    @FXML
    private TextField nameField;

    private User logedUser;
    private TeacherMainCTLL teacherMainCTLL;
    private int operationType = 0;
    private ManageStudentMOD model;
    private User student;

    public ManageStudentCTLL(){
        model = new ManageStudentMOD();
    }

    @FXML
    private void cancel(ActionEvent event) {
        closeWindow();
    }

    @FXML
    private void save(ActionEvent event) {
        if (operationType == 1) {
            if (fieldsAreFilled()) {
                try {
                    User user = new User(
                            logedUser.getSchoolID(),
                            nameField.getText(),
                            emailField.getText(),
                            StaticData.getStudentType()
                    );
                    teacherMainCTLL.addStudentToTable(model.addNewStudent(user));
                    closeWindow();
                } catch (DALException | BLLException dalException) {
                    SoftAlert.displayAlert(dalException.getMessage());
                }
            }
        }
        if (operationType == 2) {
            if (fieldsAreFilled() && !isTheSame()) {
                try{
                    student.setName(nameField.getText());
                    student.setEmail(emailField.getText());
                    model.updateStudent(student);
                    teacherMainCTLL.updateStudentInTable(student);
                    closeWindow();
                }catch (DALException | BLLException exception){
                    SoftAlert.displayAlert(exception.getMessage());
                }
            }
            if(fieldsAreFilled() && isTheSame()){
                closeWindow();
            }
        }
    }

    private boolean isTheSame(){
        return nameField.getText().toLowerCase(Locale.ROOT).equals(student.getName().toLowerCase(Locale.ROOT)) &&
                emailField.getText().toLowerCase(Locale.ROOT).equals(student.getEmail().toLowerCase(Locale.ROOT));
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

    private void closeWindow() {
        Stage st = (Stage) emailField.getScene().getWindow();
        st.close();
    }

    protected void setUser(User logedUser) {
        this.logedUser = logedUser;
    }

    protected void setOperationType(int operationType) {
        this.operationType = operationType;
    }

    protected void setMainController(TeacherMainCTLL teacherMainCTLL) {
        this.teacherMainCTLL = teacherMainCTLL;
    }

    protected void setStudent(User student) {
        this.student = student;
    }

    public void populateStudentFields() {
        nameField.setText(student.getName());
        emailField.setText(student.getEmail());
    }
}
