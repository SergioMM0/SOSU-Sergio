package GUI.Controllers;

import BE.*;
import DAL.Exceptions.DALException;
import GUI.Alerts.SoftAlert;
import GUI.Models.EvaluateCaseMOD;
import GUI.Util.FieldsManager;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

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
    private TableView<Subcategory> subcategoryFATableView;

    @FXML
    private TableView<Subcategory> subcategoryHCTableView;

    private EvaluateCaseMOD model;
    private Category currentCategory;
    private Subcategory currentSubcategory;
    private Case currentCase;
    private Patient currentPatient;
    private Group currentGroup;

    public EvaluateCaseCTLL(){
        model = new EvaluateCaseMOD();
    }

    public void initializeView() {
        FieldsManager.displayPatientInfo(patientOverviewTab,currentPatient,nameField,familyNameField,dateOfBirthPicker,genderComboBox,medicalHistoryTextArea);
        FieldsManager.displayCaseInfo(caseTab,currentCase,caseNameField,descriptionOfConditionText);
        populateCategoriesFA();
        populateCategoriesHC();
    }

    @FXML
    void categoryFAIsSelected(MouseEvent event) {
        if(categoryFATableView.getSelectionModel().getSelectedItem() != null){
            this.currentCategory = categoryFATableView.getSelectionModel().getSelectedItem();
        }
    }

    @FXML
    void categoryHCIsSelected(MouseEvent event) {
        if(categoryHCTableView.getSelectionModel().getSelectedItem() != null){
            this.currentCategory = categoryHCTableView.getSelectionModel().getSelectedItem();
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
            this.currentSubcategory = subcategoryFATableView.getSelectionModel().getSelectedItem();
        }
    }

    @FXML
    void assessFA(ActionEvent event) {

    }

    @FXML
    void assessHC(ActionEvent event) {

    }

    @FXML
    void newObservation(ActionEvent event) {

    }

    @FXML
    void saveChangesOnCase(ActionEvent event) {

    }

    @FXML
    void updatePatient(ActionEvent event) {

    }

    private void populateCategoriesHC() {
        try{
            categoryHCTableView.getItems().addAll(model.getAllCategoriesHC());
        }catch (DALException dalException){
            SoftAlert.displayAlert(dalException.getMessage());
        }
    }

    private void populateCategoriesFA() {

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

    private void openView(String resource, String css, String title, int width, int height) {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource(resource));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert root != null;
        if (resource.equals("GUI/Views/EvaluateCase.fxml")) {

        }
        root.getStylesheets().add(css);
        Stage stage = new Stage();
        stage.setTitle(title);
        stage.setScene(new Scene(root, width, height));
        stage.setResizable(false);
        stage.showAndWait();
    }
}
