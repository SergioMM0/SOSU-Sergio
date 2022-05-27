package GUI.Util;

import BE.Case;
import BE.FunctionalAbility;
import BE.Patient;
import BE.Subcategory;
import DAL.Exceptions.DALException;
import GUI.Alerts.SoftAlert;
import GUI.Models.EvaluateCaseMOD;
import GUI.Models.StudentMOD;
import GUI.Models.TeacherMainMOD;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;

import java.time.LocalDate;
import java.util.List;

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

    public static void fillFunctionalAbilityAssessed(ComboBox<String> relevancyComboBox, ComboBox<String> performanceComboBox,
                                                     ComboBox<String> meaningComboBox, FunctionalAbility currentFunctionalAbility,
                                                     TextArea goalTextArea, TextArea observationsArea) {
        relevancyComboBox.getItems().clear(); //just in case
        relevancyComboBox.getItems().addAll(StaticData.getRelevancyObservableList());
        relevancyComboBox.getSelectionModel().select(
                relevancyComboBox.getItems().indexOf(StaticData.getRelevancy(currentFunctionalAbility.getRelevancy())));

        performanceComboBox.getItems().clear();
        performanceComboBox.getItems().addAll(StaticData.getPerformanceObservableList());
        performanceComboBox.getSelectionModel().select(
                performanceComboBox.getItems().indexOf(StaticData.getPerformance(currentFunctionalAbility.getPerformance())));

        meaningComboBox.getItems().clear();
        meaningComboBox.getItems().addAll(StaticData.getMeaningObservableList());
        meaningComboBox.getSelectionModel().select(
                meaningComboBox.getItems().indexOf(StaticData.getMeaning(currentFunctionalAbility.getMeaning())));

        goalTextArea.setText(currentFunctionalAbility.getCitizenGoal());
        observationsArea.setText(currentFunctionalAbility.getProfessionalNote());
    }

    public static void fillFunctionalAbilityNotAssessed(ComboBox<String> relevancyComboBox, ComboBox<String> performanceComboBox,
                                                        ComboBox<String> meaningComboBox) {
        relevancyComboBox.getItems().clear();
        relevancyComboBox.getItems().addAll(StaticData.getRelevancyObservableList());

        performanceComboBox.getItems().clear();
        performanceComboBox.getItems().addAll(StaticData.getPerformanceObservableList());

        meaningComboBox.getItems().clear();
        meaningComboBox.getItems().addAll(StaticData.getMeaningObservableList());
    }


    public static void setUpSelectionIndicatorsAssessed(List<ImageView> indicatorsCurrent, List<ImageView> indicatorsExpected, FunctionalAbility currentFunctionalAbility) {
        for(ImageView indicator : indicatorsCurrent){
            if(currentFunctionalAbility.getCurrentLevel() == indicatorsCurrent.indexOf(indicator)+1){
                indicator.setVisible(true);
            }else indicator.setVisible(false);
        }

        for(ImageView indicator : indicatorsExpected){
            if(currentFunctionalAbility.getExpectedLevel() == indicatorsExpected.indexOf(indicator)+1){
                indicator.setVisible(true);
            }else indicator.setVisible(false);
        }
    }

    public static void setUpSelectionIndicatorsNotAssessed(List<ImageView> indicatorsCurrent, List<ImageView> indicatorsExpected) {
        for(ImageView indicator : indicatorsCurrent){
            indicator.setVisible(false);
        }
        for(ImageView indicator : indicatorsExpected){
            indicator.setVisible(false);
        }
    }

    public static void changeIndicatorCurrent(List<ImageView> indicatorsCurrent, int currentLevel){
        for(ImageView indicator : indicatorsCurrent){
            if(currentLevel == indicatorsCurrent.indexOf(indicator)+1){
                indicator.setVisible(true);
            }else indicator.setVisible(false);
        }
    }

    public static void changeIndicatorExpected(List<ImageView> indicatorsExpected, int expectedLevel){
        for(ImageView indicator : indicatorsExpected){
            if(expectedLevel == indicatorsExpected.indexOf(indicator)+1){
                indicator.setVisible(true);
            }else indicator.setVisible(false);
        }
    }

    public static boolean FunctionalAbilityFieldsAreFilled(ComboBox<String> relevancyComboBox, ComboBox<String> performanceComboBox,
                                                                   ComboBox<String> meaningComboBox, TextArea goalTextArea,
                                                                   TextArea observationsTextArea) {

        if (relevancyComboBox.getSelectionModel().isEmpty() || relevancyComboBox.hasProperties()) {
            SoftAlert.displayAlert("Please introduce a level of relevancy for the Functional ability disorder");
            return false;
        }
        else if (performanceComboBox.getSelectionModel().isEmpty() || performanceComboBox.hasProperties()) {
            SoftAlert.displayAlert("Please introduce a level of performance for the functional ability disorder");
            return false;
        }else if (meaningComboBox.getSelectionModel().isEmpty() || meaningComboBox.hasProperties()) {
            SoftAlert.displayAlert("Please introduce a level of meaning for the functional ability disorder");
            return false;
        }else if (goalTextArea.getText().isEmpty()) {
            SoftAlert.displayAlert("Please introduce a valid goal");
            return false;
        }else if (observationsTextArea.getText().isEmpty()) {
            SoftAlert.displayAlert("Please introduce a valid observation");
            return false;
        }else return true;
    }


    public static void updateVariablesOfFunctionalAbility(FunctionalAbility currentFunctionalAbility, int currentLevel,
                                                          int expectedLevel, ComboBox<String> relevancyComboBox,
                                                          ComboBox<String> performanceComboBox,
                                                          ComboBox<String> meaningComboBox, TextArea goalTextArea,
                                                          TextArea observationsTextArea) {
        currentFunctionalAbility.setCurrentLevel(currentLevel);
        currentFunctionalAbility.setExpectedLevel(expectedLevel);
        currentFunctionalAbility.setMeaning(StaticData.parseMeaningToInt(meaningComboBox.getValue()));
        currentFunctionalAbility.setPerformance(StaticData.parsePerformanceToInt(performanceComboBox.getValue()));
        currentFunctionalAbility.setRelevancy(StaticData.parseRelevancyToInt(relevancyComboBox.getValue()));
        currentFunctionalAbility.setCitizenGoal(goalTextArea.getText());
        currentFunctionalAbility.setProfessionalNote(observationsTextArea.getText());
    }
}
