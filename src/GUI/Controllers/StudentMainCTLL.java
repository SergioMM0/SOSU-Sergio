package GUI.Controllers;


import BE.Case;
import BE.Group;
import BE.Patient;
import BE.User;
import DAL.util.DalException;
import GUI.Alerts.SoftAlert;
import GUI.Models.StudentMOD;
import GUI.Util.StaticData;
import GUI.Util.FieldsManager;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class StudentMainCTLL {

    @FXML
    private ComboBox<String> caseCategoryComboBox;

    @FXML
    private TextField caseNameField;

    @FXML
    private ComboBox<String> caseSubcategoryComboBox;

    @FXML
    private Tab caseTab;

    @FXML
    private TableView<Case> casesAssignedList;

    @FXML
    private TableView<Case> casesGradedList;

    @FXML
    private TextField cprField;

    @FXML
    private DatePicker dateOfBirthPicker;

    @FXML
    private TextArea descriptionOfConditionText;

    @FXML
    private TextField familyNameField;

    @FXML
    private ComboBox<String> genderComboBox;

    @FXML
    private Label groupLBL;

    @FXML
    private TextField heightField;

    @FXML
    private Label medicalHistoryLBL;

    @FXML
    private TextArea medicalHistoryTextArea;

    @FXML
    private TableColumn<Case, String> nameCOLCasesAssigned;

    @FXML
    private TableColumn<Case, String> nameCOLCasesGraded;

    @FXML
    private TextField nameField;

    @FXML
    private TextArea newObservationTextArea;

    @FXML
    private Tab patientOverviewTab;

    @FXML
    private TextField phoneNumberField;

    @FXML
    private Label studentLBL;

    @FXML
    private TextField weightField;
    @FXML
    private Button evaluateCaseButton;

    private User currentStudent;
    private Group currentGroup;
    private Patient currentPatient;
    private Case currentCase;
    private static SoftAlert softAlert;
    private static StaticData catAndSubC;
    private StudentMOD model;
    private final String generalCSS = "";
    private ArrayList<Stage> listOfStages = new ArrayList<>();
    private FieldsManager fieldsManager;

    public StudentMainCTLL() {
        model = new StudentMOD();
        softAlert = SoftAlert.getInstance();
        catAndSubC = StaticData.getInstance();
        fieldsManager = new FieldsManager();
    }

    protected void initializeView() {
        getLogedGroup();
        setUpTabs();
        populateCasesAssigned();
        populateCasesGraded();
        casesAssignedList.getSelectionModel().getSelectedItems().addListener(new ListChangeListener<Case>() {
            @Override
            public void onChanged(Change<? extends Case> c) {
                    evaluateCaseButton.setDisable(casesAssignedList.getSelectionModel().getSelectedItem()==null);
            }
        });
    }

    private void setUpTabs() {
        patientOverviewTab.setDisable(true);
        caseTab.setDisable(true);
    }

    private void blockPatientTab() {
        patientOverviewTab.setDisable(true);
        patientOverviewTab.setText("Patient info");
    }

    private void blockCaseTab() {
        caseTab.setDisable(true);
        caseTab.setText("Case info");
    }

    private void populateCasesAssigned() {
        try {
            casesAssignedList.getItems().addAll(model.getCasesAssignedTo(currentGroup));
            nameCOLCasesAssigned.setCellValueFactory(new PropertyValueFactory<>("name"));
        } catch (DalException dalException) {
            softAlert.displayAlert(dalException.getMessage());
        }
    }

    private void populateCasesGraded() {
        try {
            casesGradedList.getItems().addAll(model.getCasesGradedOf(currentGroup));
            nameCOLCasesGraded.setCellValueFactory(new PropertyValueFactory<>("name"));
        } catch (DalException dalException) {
            softAlert.displayAlert(dalException.getMessage());
        }
    }

    @FXML
    private void categorySelected(MouseEvent event) {
        if (caseCategoryComboBox.getValue() != null) {
            caseSubcategoryComboBox.getItems().clear();
            caseSubcategoryComboBox.getItems().addAll(
                    StaticData.getInstance().getSubcategoriesOf(caseCategoryComboBox.getValue()));
        }
    }


    @FXML
    private void caseAssignedIsSelected(MouseEvent event) {
        if (casesAssignedList.getSelectionModel().getSelectedItem() != null) {
            this.currentCase = casesAssignedList.getSelectionModel().getSelectedItem();
            try {
                this.currentPatient = model.getPatientOf(currentGroup, currentCase);
            } catch (DalException dalException) {
                softAlert.displayAlert(dalException.getMessage());
            }
            fieldsManager.displayPatientInfo(patientOverviewTab,currentPatient,nameField,familyNameField,dateOfBirthPicker,
                    genderComboBox,weightField,heightField,cprField,phoneNumberField,medicalHistoryTextArea);
            fieldsManager.displayCaseInfo(caseTab, currentCase, caseCategoryComboBox,caseSubcategoryComboBox,caseNameField,descriptionOfConditionText);
        }
    }

    @FXML
    private void caseGradedIsSelected(MouseEvent event) {
        if (casesGradedList.getSelectionModel().getSelectedItem() != null) {
            this.currentCase = casesGradedList.getSelectionModel().getSelectedItem();
            try {
                this.currentPatient = model.getPatientOf(currentGroup, currentCase);
            } catch (DalException dalException) {
                softAlert.displayAlert(dalException.getMessage());
            }
            fieldsManager.displayPatientInfo(patientOverviewTab,currentPatient,nameField,familyNameField,dateOfBirthPicker,
                    genderComboBox,weightField,heightField,cprField,phoneNumberField,medicalHistoryTextArea);
            fieldsManager.displayCaseInfo(caseTab, currentCase, caseCategoryComboBox,caseSubcategoryComboBox,caseNameField,descriptionOfConditionText);
        }
    }

    @FXML
    private void newObservation(ActionEvent event) {
        fieldsManager.handleObservationStudentView(newObservationTextArea,model,currentPatient,medicalHistoryTextArea);
    }

    @FXML
    void saveChangesOnCase(ActionEvent event) {
        if (fieldsManager.caseFieldsAreFilled(caseNameField,descriptionOfConditionText)) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                    "Are you sure you want to update this case?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.YES) {
                try {
                    currentCase.setName(caseNameField.getText());
                    currentCase.setConditionDescription(descriptionOfConditionText.getText());
                    currentCase.setCategory(caseCategoryComboBox.getValue());
                    currentCase.setSubCategory(caseSubcategoryComboBox.getValue());
                    model.updateCase(currentCase);
                    model.updateCaseInTable(currentCase);
                    refreshCasesAssignedList();
                } catch (DalException dalException) {
                    dalException.printStackTrace();
                    softAlert.displayAlert(dalException.getMessage());
                }
            }
        }
    }

    protected void refreshCasesAssignedList() {
        casesAssignedList.getItems().clear();
        casesAssignedList.getItems().addAll(model.getObservableCasesAssigned());
    }

    @FXML
    private void evaluateCase(ActionEvent event) {
        openView("GUI/Views/EvaluateCase.fxml", generalCSS, "Evaluate case", 880, 660, false);
    }

    @FXML
    private void seeGradedCase(ActionEvent event) {
        openView("GUI/Views/EvaluateCase.fxml", generalCSS, "Evaluate case", 880, 660, false);
        //TODO Lock all the elements in questionnaire view to prevent modifications(as it is already graded)
    }

    @FXML
    void updatePatient(ActionEvent event) {
        if (fieldsManager.patientFieldsAreFilled(nameField,familyNameField,dateOfBirthPicker,genderComboBox,weightField,heightField,cprField,phoneNumberField)) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                    "Are you sure you want to update this patient?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.YES) {
                try {
                    fieldsManager.updateVariablesOfPatient(currentPatient, nameField,familyNameField,dateOfBirthPicker,genderComboBox,
                            weightField,heightField,cprField,phoneNumberField);
                    model.updatePatient(currentPatient);
                } catch (DalException dalException) {
                    softAlert.displayAlert(dalException.getMessage());
                }
            }
        }
    }

    public void setUser(User user) {
        this.currentStudent = user;
    }

    public void getLogedGroup() {
        try {
            this.currentGroup = model.getGroupOf(currentStudent);
        } catch (DalException dalException) {
            softAlert.displayAlert(dalException.getMessage());
        }
    }

    private void openView(String resource, String css, String title, int width, int height, boolean resizable) {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource(resource));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert root != null;
        if (resource.equals("GUI/Views/EvaluateCase.fxml")) {

            loader.<EvaluateCaseCTLL>getController().setUser(currentStudent);
            loader.<EvaluateCaseCTLL>getController().setGroup(currentGroup);
            loader.<EvaluateCaseCTLL>getController().setCase(currentCase);
            loader.<EvaluateCaseCTLL>getController().setPatient(currentPatient);
            loader.<EvaluateCaseCTLL>getController().initializeView();

        }
        root.getStylesheets().add(css);
        Stage stage = new Stage();
        stage.setTitle(title);
        listOfStages.add(stage);
        stage.setScene(new Scene(root, width, height));
        stage.setResizable(resizable);
        stage.showAndWait();
    }

    private void closeWindows() {
        for (Stage listOfStage : listOfStages) {
            listOfStage.close();
        }
        listOfStages.clear();
        model.clearLists();
    }
}
