package GUI.Controllers;

import BE.FunctionalAbility;
import BE.Patient;
import BE.Subcategory;
import DAL.Exceptions.DALException;
import GUI.Alerts.SoftAlert;
import GUI.Models.AssessFunctionalAbilityMOD;
import GUI.Util.FieldsManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class AssessFunctionalAbilityCTLL {

    @FXML
    private Button dependenceButton1;

    @FXML
    private Button dependenceButton2;

    @FXML
    private Button dependenceButton3;

    @FXML
    private Button dependenceButton4;

    @FXML
    private Button dependenceButton5;

    @FXML
    private Button dependenceButton6;

    @FXML
    private Button expectedButton1;

    @FXML
    private Button expectedButton2;

    @FXML
    private Button expectedButton3;

    @FXML
    private Button expectedButton4;

    @FXML
    private Button expectedButton5;

    @FXML
    private Button expectedButton6;

    @FXML
    private ComboBox<String> meaningComboBox;

    @FXML
    private TextArea goalTextArea;

    @FXML
    private TextArea observationsTextArea;

    @FXML
    private ComboBox<String> performanceComboBox;

    @FXML
    private ComboBox<String> relevancyComboBox;

    @FXML
    private ImageView selectionIndicator1;

    @FXML
    private ImageView selectionIndicator10;

    @FXML
    private ImageView selectionIndicator11;

    @FXML
    private ImageView selectionIndicator12;

    @FXML
    private ImageView selectionIndicator2;

    @FXML
    private ImageView selectionIndicator3;

    @FXML
    private ImageView selectionIndicator4;

    @FXML
    private ImageView selectionIndicator5;

    @FXML
    private ImageView selectionIndicator6;

    @FXML
    private ImageView selectionIndicator7;

    @FXML
    private ImageView selectionIndicator8;

    @FXML
    private ImageView selectionIndicator9;

    private AssessFunctionalAbilityMOD model;
    private Subcategory currentSubcategory;
    private Patient currentPatient;
    private FunctionalAbility currentFunctionalAbility;
    private List<ImageView> indicatorsCurrent;
    private List<ImageView> indicatorsExpected;
    private int currentLevel = 6;
    private int expectedLevel = 6;

    public AssessFunctionalAbilityCTLL() {
        model = new AssessFunctionalAbilityMOD();
        indicatorsCurrent = new ArrayList<>();
        indicatorsExpected = new ArrayList<>();
    }

    public void initializeView() {
        setUpFields();
        setUpSelectionIndicator();
    }

    @FXML
    void dependenceButton1(ActionEvent event) {
        this.currentLevel = 1;
        FieldsManager.changeIndicatorCurrent(indicatorsCurrent, currentLevel);
    }

    @FXML
    void dependenceButton2(ActionEvent event) {
        this.currentLevel = 2;
        FieldsManager.changeIndicatorCurrent(indicatorsCurrent, currentLevel);
    }

    @FXML
    void dependenceButton3(ActionEvent event) {
        this.currentLevel = 3;
        FieldsManager.changeIndicatorCurrent(indicatorsCurrent, currentLevel);
    }

    @FXML
    void dependenceButton4(ActionEvent event) {
        this.currentLevel = 4;
        FieldsManager.changeIndicatorCurrent(indicatorsCurrent, currentLevel);
    }

    @FXML
    void dependenceButton5(ActionEvent event) {
        this.currentLevel = 5;
        FieldsManager.changeIndicatorCurrent(indicatorsCurrent, currentLevel);
    }

    @FXML
    void dependenceButton6(ActionEvent event) {
        this.currentLevel = 6;
        FieldsManager.changeIndicatorCurrent(indicatorsCurrent, currentLevel);
    }

    @FXML
    void expectedButton1(ActionEvent event) {
        this.expectedLevel = 1;
        FieldsManager.changeIndicatorExpected(indicatorsExpected, expectedLevel);
    }

    @FXML
    void expectedButton2(ActionEvent event) {
        this.expectedLevel = 2;
        FieldsManager.changeIndicatorExpected(indicatorsExpected, expectedLevel);
    }

    @FXML
    void expectedButton3(ActionEvent event) {
        this.expectedLevel = 3;
        FieldsManager.changeIndicatorExpected(indicatorsExpected, expectedLevel);
    }

    @FXML
    void expectedButton4(ActionEvent event) {
        this.expectedLevel = 4;
        FieldsManager.changeIndicatorExpected(indicatorsExpected, expectedLevel);
    }

    @FXML
    void expectedButton5(ActionEvent event) {
        this.expectedLevel = 5;
        FieldsManager.changeIndicatorExpected(indicatorsExpected, expectedLevel);
    }

    @FXML
    void expectedButton6(ActionEvent event) {
        this.expectedLevel = 6;
        FieldsManager.changeIndicatorExpected(indicatorsExpected, expectedLevel);
    }

    @FXML
    void cancel(ActionEvent event) {
        closeWindow();
    }

    @FXML
    void save(ActionEvent event) {
        if (FieldsManager.FunctionalAbilityFieldsAreFilled(
                relevancyComboBox, performanceComboBox, meaningComboBox, goalTextArea, observationsTextArea)) {
            if (currentSubcategory.isAssessed()) {
                try {
                    FieldsManager.updateVariablesOfFunctionalAbility(currentFunctionalAbility,currentLevel, expectedLevel,
                            relevancyComboBox,performanceComboBox,meaningComboBox,goalTextArea,observationsTextArea);
                    model.updateFunctionalAbility(currentFunctionalAbility,currentPatient,currentSubcategory);
                    closeWindow();
                } catch (DALException dalException) {
                    SoftAlert.displayAlert(dalException.getMessage());
                }
            } else {
                try {
                    FieldsManager.updateVariablesOfFunctionalAbility(currentFunctionalAbility,currentLevel, expectedLevel,
                            relevancyComboBox,performanceComboBox,meaningComboBox,goalTextArea,observationsTextArea);
                    model.addFunctionalAbility(currentFunctionalAbility,currentPatient,currentSubcategory);
                    closeWindow();
                } catch (DALException dalException) {
                    SoftAlert.displayAlert(dalException.getMessage());
                }
            }
        }
    }

    public void setUpFields() {
        if (currentSubcategory.isAssessed()) {
            FieldsManager.fillFunctionalAbilityAssessed(relevancyComboBox, performanceComboBox,
                    meaningComboBox, currentFunctionalAbility,goalTextArea,observationsTextArea);
        } else FieldsManager.fillFunctionalAbilityNotAssessed(relevancyComboBox, performanceComboBox,
                meaningComboBox);
    }

    public void setUpSelectionIndicator() {
        indicatorsCurrent.add(selectionIndicator1);
        indicatorsCurrent.add(selectionIndicator2);
        indicatorsCurrent.add(selectionIndicator3);
        indicatorsCurrent.add(selectionIndicator4);
        indicatorsCurrent.add(selectionIndicator5);
        indicatorsCurrent.add(selectionIndicator6);
        indicatorsExpected.add(selectionIndicator7);
        indicatorsExpected.add(selectionIndicator8);
        indicatorsExpected.add(selectionIndicator9);
        indicatorsExpected.add(selectionIndicator10);
        indicatorsExpected.add(selectionIndicator11);
        indicatorsExpected.add(selectionIndicator12);

        if (currentSubcategory.isAssessed()) {
            FieldsManager.setUpSelectionIndicatorsAssessed(indicatorsCurrent, indicatorsExpected, currentFunctionalAbility);
        } else {
            FieldsManager.setUpSelectionIndicatorsNotAssessed(indicatorsCurrent, indicatorsExpected);
        }
    }

    public void setPatient(Patient patient) {
        this.currentPatient = patient;
    }

    public void setSubcategory(Subcategory subcategory) {
        this.currentSubcategory = subcategory;
    }

    public void setFunctionalAbility(FunctionalAbility functionalAbility) {
        this.currentFunctionalAbility = functionalAbility;
    }

    private void closeWindow() {
        Stage st = (Stage) dependenceButton1.getScene().getWindow();
        st.close();
    }
}
