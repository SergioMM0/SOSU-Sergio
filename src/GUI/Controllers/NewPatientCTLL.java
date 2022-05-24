package GUI.Controllers;

import BE.Patient;
import BE.User;
import DAL.Exceptions.DALException;
import GUI.Alerts.SoftAlert;
import GUI.Models.NewPatientMOD;
import GUI.Util.StaticData;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class NewPatientCTLL implements Initializable {

    @FXML
    private Button cancelButton;

    @FXML
    private Button createButton;

    @FXML
    private DatePicker dateOfBirthPicker;

    @FXML
    private TextField familyNameField;

    @FXML
    private ComboBox<String> genderComboBox;

    @FXML
    private TextField nameField;

    @FXML
    private TextField observationsField;

    private NewPatientMOD model;
    private User user;
    private TeacherMainCTLL teacherMainCTLL;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        model = new NewPatientMOD();
        populateComboBoxes();
    }

    private void populateComboBoxes() {
        genderComboBox.getItems().addAll(StaticData.getGenders());
    }

    @FXML
    void cancel(ActionEvent event) {
        closeWindow();
    }

    @FXML
    void createPatient(ActionEvent event) {
        if(fieldsAreFiled()){
            ArrayList<String> observations = new ArrayList<>();
            try{
                observations.add(LocalDate.now() + ": " + observationsField.getText());
                Patient patient = new Patient(
                        nameField.getText(),
                        familyNameField.getText(),
                        dateOfBirthPicker.getValue(),
                        genderComboBox.getValue(),
                        observations,
                        user.getSchoolID(),
                        false
                );
                teacherMainCTLL.addPatientToList(model.createPatient(patient));
                closeWindow();
            } catch(DALException dalException){
                dalException.printStackTrace();
                SoftAlert.displayAlert(dalException.getMessage());
            }
        }
    }

    private boolean fieldsAreFiled() {
        if (nameField.getText().isEmpty()) {
            SoftAlert.displayAlert("Please introduce the name of the patient");
            return false;
        } else if (familyNameField.getText().isEmpty()) {
            SoftAlert.displayAlert("Please introduce the family name of the patient");
            return false;
        } else if (dateOfBirthPicker.getValue().isAfter(LocalDate.now()) || dateOfBirthPicker.getValue() == null) {
            SoftAlert.displayAlert("Please select a correct date of birth for the patient");
            return false;
        } else if (genderComboBox.getSelectionModel().isEmpty() || genderComboBox.hasProperties()) {
            SoftAlert.displayAlert("Please introduce the gender of the patient");
            return false;
        }
        else if (observationsField.getText().isEmpty()) { //including an observation when creation a patient can be optional
            observationsField.setText("Patient included in the system with no observations");
            return false;
        }
        else return true;
    }

    private void closeWindow(){
        Stage st = (Stage) cancelButton.getScene().getWindow();
        st.close();
    }

    public void setController(TeacherMainCTLL teacherMainCTLL) {
        this.teacherMainCTLL = teacherMainCTLL;
    }

    public void setUser(User logedUser) {
        this.user = logedUser;
    }
}

