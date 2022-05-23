package GUI.Util;

import BE.Case;
import BE.Patient;
import DAL.util.DalException;
import GUI.Alerts.SoftAlert;
import GUI.Models.StudentMOD;
import GUI.Models.TeacherMainMOD;
import javafx.scene.control.*;

import java.time.LocalDate;

public class FieldsManager {

    private static SoftAlert softAlert;
    private static StaticData staticData;

    public FieldsManager() {
        softAlert = SoftAlert.getInstance();
        staticData = StaticData.getInstance();
    }

    public boolean patientFieldsAreFilled(TextField name, TextField familyName, DatePicker dateOfBirth, ComboBox<String> gender, TextField weight,
                                          TextField height, TextField cpr, TextField phoneNumber) {
        if (name.getText().isEmpty()) {
            softAlert.displayAlert("Please introduce a new name for the patient");
            return false;
        } else if (familyName.getText().isEmpty()) {
            softAlert.displayAlert("Please introduce a new family name for the patient");
            return false;
        } else if (dateOfBirth.getValue().isAfter(LocalDate.now()) || dateOfBirth.getValue() == null) {
            softAlert.displayAlert("Please introduce a new date of birth for the patient");
            return false;
        } else if (gender.getSelectionModel().isEmpty() || gender.hasProperties()) {
            softAlert.displayAlert("Please introduce a new gender combo box for the patient");
            return false;
        } else if (weight.getText().isEmpty()) {
            softAlert.displayAlert("Please introduce a new valid weight for the patient");
            return false;
        } else if (height.getText().isEmpty()) {
            softAlert.displayAlert("Please introduce a new valid height for the patient");
            return false;
        } else if (cpr.getText().isEmpty()) {
            softAlert.displayAlert("Please introduce a valid CPR for the patient");
            return false;
        } else if (phoneNumber.getText().isEmpty()) {
            softAlert.displayAlert("Please introduce a valid phone number for the patient");
            return false;
        } else return true;
    }

    public boolean caseFieldsAreFilled(TextField nameCase, TextArea descriptionOfCondition) {
        if (nameCase.getText().isEmpty()) {
            softAlert.displayAlert("Please introduce a valid name for the case");
            return false;
        } else if (descriptionOfCondition.getText().isEmpty()) {
            softAlert.displayAlert("Please introduce a valid description for the case");
            return false;
        } else return true;
    }

    public void handleObservationTeacherView(TextArea newObservation, TeacherMainMOD model, Patient patient, TextArea medicalHistory) {
        if (!newObservation.getText().isEmpty()) {
            String observation = LocalDate.now() + ": " + newObservation.getText();
            try {
                model.addObservationToPatient(observation, patient);
            } catch (DalException dalException) {
                softAlert.displayAlert(dalException.getMessage());
            }
            patient.addObservation(observation);
            medicalHistory.setText(handleObservationsOfPatient(patient));
            newObservation.clear();
        }
    }

    public void handleObservationStudentView(TextArea newObservation, StudentMOD model, Patient patient, TextArea medicalHistory) {
        if (!newObservation.getText().isEmpty()) {
            String observation = LocalDate.now() + ": " + newObservation.getText();
            try {
                model.addObservationToPatient(observation, patient);
            } catch (DalException dalException) {
                softAlert.displayAlert(dalException.getMessage());
            }
            patient.addObservation(observation);
            medicalHistory.setText(handleObservationsOfPatient(patient));
            newObservation.clear();
        }
    }

    private String handleObservationsOfPatient(Patient patient){
        StringBuilder sb = new StringBuilder();
        for(String observation : patient.getObservationsList()){
            sb.append(observation).append("\n");
        }
        return sb.toString();
    }

    public void displayPatientInfo(Tab patientTab, Patient patient, TextField nameField, TextField familyNameField, DatePicker dateOfBirthPicker,
                                   ComboBox<String> genderComboBox, TextField weightField, TextField heightField, TextField cprField,
                                   TextField phoneNumberField, TextArea medicalHistoryTextArea){
        if (patientTab.isDisabled()) {
            patientTab.setDisable(false);
        }
        patientTab.setText(patient.getFirst_name());
        nameField.setText(patient.getFirst_name());
        familyNameField.setText(patient.getLast_name());
        dateOfBirthPicker.setValue(patient.getDateOfBirth());

        genderComboBox.getItems().clear();
        genderComboBox.getItems().addAll(staticData.getGenders());
        genderComboBox.getSelectionModel().select(
                genderComboBox.getItems().indexOf(patient.getGender()));
        //Selects in the gender combo box the gender that matches the gender of the patient
        weightField.setText(patient.getWeight());
        heightField.setText(patient.getHeight());
        cprField.setText(patient.getCpr());
        phoneNumberField.setText(patient.getPhoneNumber());
        medicalHistoryTextArea.setText(handleObservationsOfPatient(patient));
    }

    public void displayCaseInfo(Tab caseTab, Case selectedCase, ComboBox<String> caseCategoryComboBox, ComboBox<String> caseSubcategoryComboBox,
                                 TextField caseNameField, TextArea descriptionOfConditionText){
        if (caseTab.isDisabled()) {
            caseTab.setDisable(false);
        }
        caseTab.setText(selectedCase.getName());
        caseCategoryComboBox.getItems().clear();
        caseSubcategoryComboBox.getItems().clear();

        String[] allCat = staticData.getCategories();
        String[] allSubCat = staticData.getSubcategoriesOf(selectedCase.getCategory());

        caseNameField.setText(selectedCase.getName());
        descriptionOfConditionText.setText(selectedCase.getConditionDescription());

        for (String cat : allCat) {
            caseCategoryComboBox.getItems().add(cat); //population of the categories
        }
        for (String subCat : allSubCat) {
            caseSubcategoryComboBox.getItems().add(subCat); // population of the subcategories of the category
        }
        //population of case in case info when selected in general view

        caseCategoryComboBox.getSelectionModel().select(
                caseCategoryComboBox.getItems().indexOf(selectedCase.getCategory()));
        //selects the category specified for the case

        caseSubcategoryComboBox.getSelectionModel().select(
                caseSubcategoryComboBox.getItems().indexOf(selectedCase.getSubCategory()));
        //selects the subcategory specified for the case
    }

    public void updateVariablesOfPatient(Patient currentPatient, TextField nameField, TextField familyNameField, DatePicker dateOfBirthPicker,
                                         ComboBox<String> genderComboBox, TextField weightField, TextField heightField,
                                         TextField cprField, TextField phoneNumberField){
        currentPatient.setFirst_name(nameField.getText());
        currentPatient.setLast_name(familyNameField.getText());
        currentPatient.setDateOfBirth(dateOfBirthPicker.getValue());
        currentPatient.setGender(genderComboBox.getValue());
        currentPatient.setWeight(weightField.getText());
        currentPatient.setHeight(heightField.getText());
        currentPatient.setCpr(cprField.getText());
        currentPatient.setPhoneNumber(phoneNumberField.getText());
    }
}
