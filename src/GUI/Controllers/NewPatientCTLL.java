package GUI.Controllers;

import BE.Patient;
import BE.User;
import DAL.util.DalException;
import GUI.Alerts.SoftAlert;
import GUI.Models.NewPatientMOD;
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
import java.util.List;
import java.util.ResourceBundle;

public class NewPatientCTLL implements Initializable {

    @FXML
    private ComboBox<String> alcoholComboBox;

    @FXML
    private ComboBox<String> bloodTypeComboBox;

    @FXML
    private Button cancelButton;

    @FXML
    private TextField cprField;

    @FXML
    private Button createButton;

    @FXML
    private DatePicker dateOfBirthPicker;

    @FXML
    private ComboBox<String> dietComboBox;

    @FXML
    private ComboBox<String> exerciseComboBox;

    @FXML
    private TextField familyNameField;

    @FXML
    private ComboBox<String> genderComboBox;

    @FXML
    private TextField heightField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField observationsField;

    @FXML
    private TextField phoneNumberField;

    @FXML
    private ComboBox<String> tobaccoComboBox;

    @FXML
    private TextField weightField;

    private NewPatientMOD model;
    private User user;
    private TeacherMainCTLL teacherMainCTLL;
    private static SoftAlert softAlert;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        model = new NewPatientMOD();
        populateComboBoxes();
        softAlert = SoftAlert.getInstance();
    }

    private void populateComboBoxes() {
        genderComboBox.getItems().addAll(model.getGenders());
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
                observations.add(observationsField.getText());
                Patient patient = new Patient(
                        0,
                        nameField.getText(),
                        familyNameField.getText(),
                        dateOfBirthPicker.getValue(),
                        genderComboBox.getValue(),
                        weightField.getText(),
                        heightField.getText(),
                        cprField.getText(),
                        phoneNumberField.getText(),
                        observations,
                        user.getSchoolID(),
                        false
                );
                teacherMainCTLL.addPatientToList(model.createPatient(patient));
                closeWindow();
            } catch(DalException dalException){
                dalException.printStackTrace();
                softAlert.displayAlert(dalException.getMessage());
            }
        }
    }

    private boolean fieldsAreFiled() {
        if (nameField.getText().isEmpty()) {
            softAlert.displayAlert("Please introduce the name of the patient");
            return false;
        } else if (familyNameField.getText().isEmpty()) {
            softAlert.displayAlert("Please introduce the family name of the patient");
            return false;
        } else if (dateOfBirthPicker.getValue().isAfter(LocalDate.now()) || dateOfBirthPicker.getValue() == null) {
            softAlert.displayAlert("Please select a correct date of birth for the patient");
            return false;
        } else if (genderComboBox.getSelectionModel().isEmpty() || genderComboBox.hasProperties()) {
            softAlert.displayAlert("Please introduce the gender of the patient");
            return false;
        } else if (weightField.getText().isEmpty()) {
            softAlert.displayAlert("Please introduce the weight of the patient");
            return false;
        } else if (heightField.getText().isEmpty()) {
            softAlert.displayAlert("Please introduce the height of the patient");
            return false;
        } else if (cprField.getText().isEmpty()) {
            softAlert.displayAlert("Please introduce the CPR of the patient");
            return false;
        } else if (phoneNumberField.getText().isEmpty()) { //Phone number is optional
            phoneNumberField.setText("No phone number");
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

