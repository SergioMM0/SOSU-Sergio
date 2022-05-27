package GUI.Util;

import BE.Case;
import BE.Patient;
import DAL.Exceptions.DALException;
import GUI.Alerts.SoftAlert;
import GUI.Models.EvaluateCaseMOD;
import GUI.Models.StudentMOD;
import GUI.Models.TeacherMainMOD;
import javafx.scene.control.*;

import java.time.LocalDate;

public class FieldsManager {

    public static boolean patientFieldsAreFilled(TextField name, TextField familyName, DatePicker dateOfBirth, ComboBox<String> gender) {
        if (name.getText().isEmpty()) {
            SoftAlert.displayAlert("Please introduce a new name for the patient");
            return false;
        } else if (familyName.getText().isEmpty()) {
            SoftAlert.displayAlert("Please introduce a new family name for the patient");
            return false;
        } else if (dateOfBirth.getValue().isAfter(LocalDate.now()) || dateOfBirth.getValue() == null) {
            SoftAlert.displayAlert("Please introduce a new date of birth for the patient");
            return false;
        } else if (gender.getSelectionModel().isEmpty() || gender.hasProperties()) {
            SoftAlert.displayAlert("Please introduce a new gender combo box for the patient");
            return false;
        } else return true;
    }

    public static boolean caseFieldsAreFilled(TextField nameCase, TextArea descriptionOfCondition) {
        if (nameCase.getText().isEmpty()) {
            SoftAlert.displayAlert("Please introduce a valid name for the case");
            return false;
        } else if (descriptionOfCondition.getText().isEmpty()) {
            SoftAlert.displayAlert("Please introduce a valid description for the case");
            return false;
        } else return true;
    }

    public static void handleObservationTeacherView(TextArea newObservation, TeacherMainMOD model, Patient patient, TextArea medicalHistory) {
        if (!newObservation.getText().isEmpty()) {
            String observation = LocalDate.now() + ": " + newObservation.getText();
            try {
                model.addObservationToPatient(observation, patient);
            } catch (DALException dalException) {
                SoftAlert.displayAlert(dalException.getMessage());
            }
            patient.addObservation(observation);
            medicalHistory.setText(handleObservationsOfPatient(patient));
            newObservation.clear();
        }
    }

    public static void handleObservationStudentView(TextArea newObservation, StudentMOD model, Patient patient, TextArea medicalHistory) {
        if (!newObservation.getText().isEmpty()) {
            String observation = LocalDate.now() + ": " + newObservation.getText();
            try {
                model.addObservationToPatient(observation, patient);
            } catch (DALException dalException) {
                SoftAlert.displayAlert(dalException.getMessage());
            }
            patient.addObservation(observation);
            medicalHistory.setText(handleObservationsOfPatient(patient));
            newObservation.clear();
        }
    }

    public static void handleObservationEvaluatingCase(TextArea newObservation, EvaluateCaseMOD model, Patient patient, TextArea medicalHistory) {
        if (!newObservation.getText().isEmpty()) {
            String observation = LocalDate.now() + ": " + newObservation.getText();
            try {
                model.addObservationToPatient(observation, patient);
            } catch (DALException dalException) {
                SoftAlert.displayAlert(dalException.getMessage());
            }
            patient.addObservation(observation);
            medicalHistory.setText(handleObservationsOfPatient(patient));
            newObservation.clear();
        }
    }

    private static String handleObservationsOfPatient(Patient patient){
        StringBuilder sb = new StringBuilder();
        for(String observation : patient.getObservationsList()){
            sb.append(observation).append("\n");
        }
        return sb.toString();
    }

    public static void displayPatientInfo(Tab patientTab, Patient patient, TextField nameField, TextField familyNameField, DatePicker dateOfBirthPicker,
                                   ComboBox<String> genderComboBox, TextArea medicalHistoryTextArea){
        if (patientTab.isDisabled()) {
            patientTab.setDisable(false);
        }
        patientTab.setText(patient.getFirst_name());
        nameField.setText(patient.getFirst_name());
        familyNameField.setText(patient.getLast_name());
        dateOfBirthPicker.setValue(patient.getDateOfBirth());

        genderComboBox.getItems().clear();
        genderComboBox.getItems().addAll(StaticData.getGenders());
        genderComboBox.getSelectionModel().select(
                genderComboBox.getItems().indexOf(patient.getGender()));
        //Selects in the gender combo box the gender that matches the gender of the patient
        medicalHistoryTextArea.setText(handleObservationsOfPatient(patient));
    }

    public static void displayCaseInfo(Tab caseTab, Case selectedCase, TextField caseNameField, TextArea descriptionOfConditionText){
        if (caseTab.isDisabled()) {
            caseTab.setDisable(false);
        }
        caseTab.setText(selectedCase.getName());

        caseNameField.setText(selectedCase.getName());
        descriptionOfConditionText.setText(selectedCase.getConditionDescription());
        //population of case in case info when selected in general view
    }

    public static void updateVariablesOfPatient(Patient currentPatient, TextField nameField, TextField familyNameField, DatePicker dateOfBirthPicker,
                                         ComboBox<String> genderComboBox){
        currentPatient.setFirst_name(nameField.getText());
        currentPatient.setLast_name(familyNameField.getText());
        currentPatient.setDateOfBirth(dateOfBirthPicker.getValue());
        currentPatient.setGender(genderComboBox.getValue());
    }
}
