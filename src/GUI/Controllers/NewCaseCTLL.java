package GUI.Controllers;

import BE.Case;
import BE.User;
import BLL.Exceptions.BLLException;
import DAL.Exceptions.DALException;
import GUI.Alerts.SoftAlert;
import GUI.Models.NewCaseMOD;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class NewCaseCTLL implements Initializable {

    @FXML
    private Button cancelBtn;

    @FXML
    private Label caseNameLBL; //labeled in order to have a fancy color and typo :)

    @FXML
    private TextArea descriptionOfConditionText;

    @FXML
    private TextField nameField;

    @FXML
    private Button saveChangesBtn; //Must be green on CSS and different to other buttons

    private NewCaseMOD model;
    private TeacherMainCTLL teacherMainCTLL;
    private User logedUser;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        model = new NewCaseMOD();
    }

    @FXML
    private void cancelCreate(ActionEvent event) {
        closeWindow();
    }

    @FXML
    private void saveNewCase(ActionEvent event) {
        if(fieldsAreFiled()){
            try {
                Case newCase = new Case(nameField.getText(),
                        descriptionOfConditionText.getText(),
                        logedUser.getSchoolID(),
                        false);
                teacherMainCTLL.addCaseToList(model.createCase(newCase));
                closeWindow();
            } catch (DALException | BLLException exception) {
                SoftAlert.displayAlert(exception.getMessage());
            }
        }
    }

    private boolean fieldsAreFiled() {
        if(nameField.getText().isEmpty()){
            SoftAlert.displayAlert("Please introduce a name for the Case");
            return false;
        }
        else if(descriptionOfConditionText.getText().isEmpty()){
            SoftAlert.displayAlert("Please introduce a valid description of the case");
            return false;
        }
        else return true;
    }

    private void closeWindow() {
        Stage st = (Stage) cancelBtn.getScene().getWindow();
        st.close();
    }

    protected void setController(TeacherMainCTLL teacherMainCTLL) {
        this.teacherMainCTLL = teacherMainCTLL;
    }

    protected void setUser(User user){
        this.logedUser = user;
    }
}

