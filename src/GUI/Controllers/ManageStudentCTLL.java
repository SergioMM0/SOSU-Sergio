package GUI.Controllers;

import BE.User;
import DAL.util.DalException;
import GUI.Alerts.SoftAlert;
import GUI.Models.ManageStudentMOD;
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
    private static SoftAlert softAlert;

    public ManageStudentCTLL(){
        model = new ManageStudentMOD();
        softAlert = SoftAlert.getInstance();
    }

    @FXML
    void cancel(ActionEvent event) {
        closeWindow();
    }

    @FXML
    void save(ActionEvent event) {
        if (operationType == 1) {
            if (fieldsAreFilled()) {
                try {
                    User user = new User(
                            logedUser.getSchoolID(),
                            nameField.getText(),
                            emailField.getText(),
                            "STUDENT"
                    );
                    teacherMainCTLL.addStudentToTable(model.addNewStudent(user));
                    closeWindow();
                } catch (DalException dalException) {
                    softAlert.displayAlert(dalException.getMessage());
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
                }catch (DalException dalException){
                    softAlert.displayAlert(dalException.getMessage());
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
            softAlert.displayAlert("Please introduce a name for the student");
            return false;
        } else if (emailField.getText().isEmpty()) {
            softAlert.displayAlert("Please introduce an email for the student");
            return false;
        } else return true;
    }

    private void closeWindow() {
        Stage st = (Stage) emailField.getScene().getWindow();
        st.close();
    }

    public void setUser(User logedUser) {
        this.logedUser = logedUser;
    }

    public void setOperationType(int operationType) {
        this.operationType = operationType;
    }

    public void setController(TeacherMainCTLL teacherMainCTLL) {
        this.teacherMainCTLL = teacherMainCTLL;
    }

    public void setStudent(User student) {
        this.student = student;
    }

    public void populateStudentFields() {
        nameField.setText(student.getName());
        emailField.setText(student.getEmail());
    }
}
