package GUI.Controllers;

import BE.*;
import BLL.Exceptions.BLLException;
import DAL.Exceptions.DALException;
import GUI.Alerts.SoftAlert;
import GUI.Models.EvaluateCaseMOD;
import GUI.Util.FieldsManager;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class EvaluateCaseCTLL {

    @FXML
    private TabPane tabPane;

    @FXML
    private TextField caseNameField;

    @FXML
    private Tab caseTab;

    @FXML
    private TableView<Category> categoryFATableView;

    @FXML
    private TableView<Category> categoryHCTableView;

    @FXML
    private TableColumn<Category, String> categoryNameFA;

    @FXML
    private TableColumn<Category, String> categoryNameHC;

    @FXML
    private DatePicker dateOfBirthPicker;

    @FXML
    private TextArea descriptionOfConditionText;

    @FXML
    private TextField familyNameField;

    @FXML
    private ComboBox<String> genderComboBox;

    @FXML
    private Label medicalHistoryLBL;

    @FXML
    private TextArea medicalHistoryTextArea;

    @FXML
    private TextField nameField;

    @FXML
    private TextArea newObservationTextArea;

    @FXML
    private Tab patientOverviewTab;

    @FXML
    private TableColumn<Subcategory, String> subCategoryNameFA;

    @FXML
    private TableColumn<Subcategory, String> subCategoryNameHC;

    @FXML
    private TableColumn<Subcategory, String> assessedCOLHC;

    @FXML
    private TableColumn<Subcategory, String> assessedColFA;

    @FXML
    private TableView<Subcategory> subcategoryFATableView;

    @FXML
    private TableView<Subcategory> subcategoryHCTableView;

    private EvaluateCaseMOD model;
    private Category currentCategory;
    private Subcategory currentSubcategory;
    private Case currentCase;
    private Patient currentPatient;
    private Group currentGroup;
    private FunctionalAbility currentFunctionalAbility;
    private HealthCondition currentHealthCondition;

    public EvaluateCaseCTLL(){
        model = new EvaluateCaseMOD();
    }

    public void initializeView() {
        FieldsManager.displayPatientInfo(patientOverviewTab,currentPatient,nameField,familyNameField,dateOfBirthPicker,genderComboBox,medicalHistoryTextArea);
        FieldsManager.displayCaseInfo(caseTab,currentCase,caseNameField,descriptionOfConditionText);
        initializeTables();
        populateCategoriesFA();
        populateCategoriesHC();
    }

    @FXML
    void categoryFAIsSelected(MouseEvent event) {
        if(categoryFATableView.getSelectionModel().getSelectedItem() != null){
            this.currentCategory = categoryFATableView.getSelectionModel().getSelectedItem();
            populateSubcategoriesFA();
        }
    }

    @FXML
    void categoryHCIsSelected(MouseEvent event) {
        if(categoryHCTableView.getSelectionModel().getSelectedItem() != null){
            this.currentCategory = categoryHCTableView.getSelectionModel().getSelectedItem();
            populateSubcategoriesHC();
        }
    }

    @FXML
    void subcategoryFAIsSelected(MouseEvent event) {
        if(subcategoryFATableView.getSelectionModel().getSelectedItem() != null){
            this.currentSubcategory = subcategoryFATableView.getSelectionModel().getSelectedItem();
        }
    }

    @FXML
    void subcategoryHCIsSelected(MouseEvent event) {
        if(subcategoryHCTableView.getSelectionModel().getSelectedItem() != null){
            this.currentSubcategory = subcategoryHCTableView.getSelectionModel().getSelectedItem();
        }
    }

    @FXML
    void assessFA(ActionEvent event) {
        try{
            this.currentFunctionalAbility = model.getCurrentFunctionalAbility(currentSubcategory, currentPatient);
            openView("GUI/Views/AssessFunctionalAbility.fxml","Functional Ability", 710,680);
        }catch(DALException dalException){
            SoftAlert.displayAlert(dalException.getMessage());
        }
    }

    @FXML
    void assessHC(ActionEvent event) {
        try{
            this.currentHealthCondition = model.getCurrentHealthCondition(currentSubcategory, currentPatient);
            openView("GUI/Views/AssesHealthCondition.fxml","Health Condition", 680,450);
        }catch (DALException dalException){
            SoftAlert.displayAlert(dalException.getMessage());
        }
    }

    @FXML
    void newObservation(ActionEvent event) {
        FieldsManager.handleObservationEvaluatingCase(newObservationTextArea,model,currentPatient,medicalHistoryTextArea);
    }

    @FXML
    void updatePatient(ActionEvent event) {
        if (FieldsManager.patientFieldsAreFilled(nameField,familyNameField,dateOfBirthPicker,genderComboBox)) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                    "Are you sure you want to update this patient?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.YES) {
                try {
                    FieldsManager.updateVariablesOfPatient(currentPatient, nameField,familyNameField,dateOfBirthPicker,genderComboBox);
                    model.updatePatient(currentPatient);
                } catch (DALException | BLLException exception) {
                    SoftAlert.displayAlert(exception.getMessage());
                }
            }
        }
    }

    @FXML
    void close(ActionEvent event) {
        closeWindow();
    }

    private void initializeTables() {
        categoryNameHC.setCellValueFactory(new PropertyValueFactory<>("name"));
        categoryNameFA.setCellValueFactory(new PropertyValueFactory<>("name"));
        subCategoryNameFA.setCellValueFactory(new PropertyValueFactory<>("name"));
        subCategoryNameHC.setCellValueFactory(new PropertyValueFactory<>("name"));
        assessedColFA.setCellValueFactory(subcategory -> {
                    boolean assessed = subcategory.getValue().isAssessed();
                    String isAssessed;
                    if (assessed) {
                        isAssessed = "Yes";
                    } else {
                        isAssessed = "No";
                    }
                    return new ReadOnlyStringWrapper(isAssessed);
                });
        assessedCOLHC.setCellValueFactory(subcategory -> {
            boolean assessed = subcategory.getValue().isAssessed();
            String isAssessed;
            if (assessed) {
                isAssessed = "Yes";
            } else {
                isAssessed = "No";
            }
            return new ReadOnlyStringWrapper(isAssessed);
        });
    }

    protected void populateSubcategoriesFA() {
        try{
            subcategoryFATableView.getItems().clear();
            subcategoryFATableView.getItems().addAll(model.getSubcategoriesFA(currentCategory,currentPatient));
        }catch (DALException dalException){
            dalException.printStackTrace();
            SoftAlert.displayAlert(dalException.getMessage());
        }
    }

    protected void populateSubcategoriesHC() {
        try{
            subcategoryHCTableView.getItems().clear();
            subcategoryHCTableView.getItems().addAll(model.getSubcategoriesHC(currentCategory,currentPatient));
        }catch (DALException dalException){
            dalException.printStackTrace();
            SoftAlert.displayAlert(dalException.getMessage());
        }
    }

    private void populateCategoriesHC() {
        try{
            categoryHCTableView.getItems().addAll(model.getAllCategoriesHC());
        }catch (DALException dalException){
            SoftAlert.displayAlert(dalException.getMessage());
        }
    }

    private void populateCategoriesFA() {
        try{
            categoryFATableView.getItems().addAll(model.getAllCategoriesFA());
        }catch (DALException dalException){
            SoftAlert.displayAlert(dalException.getMessage());
        }
    }

    public void setGroup(Group group){
        this.currentGroup = group;
    }

    public void setCase(Case currentCase){
        this.currentCase = currentCase;
    }

    public void setPatient(Patient patient){
        this.currentPatient = patient;
    }

    private void openView(String resource, String title, int width, int height) {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource(resource));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert root != null;
        if (resource.equals("GUI/Views/AssessFunctionalAbility.fxml")) {
            loader.<AssessFunctionalAbilityCTLL>getController().setPatient(currentPatient);
            loader.<AssessFunctionalAbilityCTLL>getController().setSubcategory(currentSubcategory);
            loader.<AssessFunctionalAbilityCTLL>getController().setFunctionalAbility(currentFunctionalAbility);
            loader.<AssessFunctionalAbilityCTLL>getController().initializeView();
            loader.<AssessFunctionalAbilityCTLL>getController().setEvaluateCaseCTLL(this);
        }
        if (resource.equals("GUI/Views/AssesHealthCondition.fxml")) {
            loader.<AssessHealthConditionCTLL>getController().setPatient(currentPatient);
            loader.<AssessHealthConditionCTLL>getController().setSubCategory(currentSubcategory);
            loader.<AssessHealthConditionCTLL>getController().setHealthCondition(currentHealthCondition);
            loader.<AssessHealthConditionCTLL>getController().initializeView();
            loader.<AssessHealthConditionCTLL>getController().setEvaluateCaseCTLL(this);
        }
        root.getStylesheets().add("GUI/Views/CSS/GeneralCSS.css");
        Stage stage = new Stage();
        stage.setTitle(title);
        stage.setScene(new Scene(root, width, height));
        stage.setResizable(false);
        stage.showAndWait();
    }

    private void closeWindow(){
        Stage st = (Stage) tabPane.getScene().getWindow();
        st.close();
    }
}
