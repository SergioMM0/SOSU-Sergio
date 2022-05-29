package GUI.Controllers;

import BE.HealthCondition;
import BE.Patient;
import BE.Subcategory;
import DAL.Exceptions.DALException;
import GUI.Alerts.SoftAlert;
import GUI.Models.AssessHealthConditionMOD;
import GUI.Util.FieldsManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class AssessHealthConditionCTLL {

    @FXML
    private TextArea assessmentText;

    @FXML
    private ComboBox<String> expectationsComboBox;

    @FXML
    private TextArea goalText;

    @FXML
    private TextArea observationsText;

    @FXML
    private ComboBox<String> relevancyComboBox;

    private AssessHealthConditionMOD model;
    private Patient currentPatient;
    private Subcategory currentSubcategory;
    private HealthCondition currentHealthCondition;
    private EvaluateCaseCTLL evaluateCaseCTLL;

    public AssessHealthConditionCTLL(){
        model = new AssessHealthConditionMOD();
    }

    protected void initializeView() {
        setUpFields();
    }

    @FXML
    private void save(ActionEvent event) {
        if(FieldsManager.healthConditionFieldsArefilled(relevancyComboBox,expectationsComboBox,
                assessmentText,goalText,observationsText)){
            if(currentSubcategory.isAssessed()){
                try{
                    FieldsManager.updateVariablesOfHealthCondition(currentHealthCondition,relevancyComboBox, expectationsComboBox,
                            assessmentText,goalText,observationsText);
                    model.updateHealthCondition(currentHealthCondition,currentSubcategory,currentPatient);
                    evaluateCaseCTLL.populateSubcategoriesHC();
                    closeWindow();
                }catch (DALException dalException){
                    SoftAlert.displayAlert(dalException.getMessage());
                }
            }
            else{
                try{
                    FieldsManager.updateVariablesOfHealthCondition(currentHealthCondition,relevancyComboBox, expectationsComboBox,
                            assessmentText,goalText,observationsText);
                    model.addHealthCondition(currentHealthCondition,currentSubcategory,currentPatient);
                    evaluateCaseCTLL.populateSubcategoriesHC();
                    closeWindow();
                }catch (DALException dalException){
                    SoftAlert.displayAlert(dalException.getMessage());
                }
            }
        }
    }

    @FXML
    private void cancel(ActionEvent event) {
        closeWindow();
    }


    private void setUpFields() {
        if(currentSubcategory.isAssessed()){
            FieldsManager.fillHealthConditionAssessed(currentHealthCondition,relevancyComboBox,
                    expectationsComboBox,assessmentText,goalText,observationsText);
        }else FieldsManager.fillHealthConditionNotAssessed(relevancyComboBox,expectationsComboBox);
    }

    private void closeWindow() {
        Stage st = (Stage) goalText.getScene().getWindow();
        st.close();
    }

    protected void setPatient(Patient patient) {
        this.currentPatient = patient;
    }

    protected void setSubCategory(Subcategory subcategory) {
        this.currentSubcategory = subcategory;
    }

    protected void setHealthCondition(HealthCondition healthCondition) {
        this.currentHealthCondition = healthCondition;
    }

    protected void setEvaluateCaseCTLL(EvaluateCaseCTLL evaluateCaseCTLL){
        this.evaluateCaseCTLL = evaluateCaseCTLL;
    }
}
