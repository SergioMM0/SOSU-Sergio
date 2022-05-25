package GUI.Controllers;


import BE.Case;
import BE.Group;
import BE.Patient;
import BE.User;
import DAL.Exceptions.DALException;
import GUI.Alerts.SoftAlert;
import GUI.Models.StudentMOD;
import GUI.Util.FieldsManager;
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
import java.util.List;

public class StudentMainCTLL {

    @FXML
    private TextField caseNameField;

    @FXML
    private Tab caseTab;

    @FXML
    private TableView<Case> casesAssignedList;

    @FXML
    private TableView<Case> casesGradedList;

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
    private Label studentLBL;

    private User currentStudent;
    private Group currentGroup;
    private Patient currentPatient;
    private Case currentCase;
    private StudentMOD model;
    private final String generalCSS = "";
    private ArrayList<Stage> listOfStages = new ArrayList<>();

    public StudentMainCTLL() {
        model = new StudentMOD();
    }

    protected void initializeView() {
        getLogedGroup();
        setUpTabs();
        populateCasesAssigned();
        populateCasesGraded();
        displayLabels();
    }

    private void displayLabels() {
        groupLBL.setText(currentGroup.getName());
        setUpStudentsLBL(currentGroup);
    }

    private void setUpStudentsLBL(Group group) {
        StringBuilder sb = new StringBuilder();
        List<User> participants = group.getMembers();
        for (int i = 0; i < participants.size(); i++) {
            if (i == participants.size() - 1) {
                sb.append(participants.get(i).getName());
            } else sb.append(participants.get(i).getName()).append(", ");

        }
        studentLBL.setText(sb.toString());
    }

    private void setUpTabs() {
        patientOverviewTab.setDisable(true);
        caseTab.setDisable(true);
    }

    private void populateCasesAssigned() {
        try {
            casesAssignedList.getItems().addAll(model.getCasesAssignedTo(currentGroup));
            nameCOLCasesAssigned.setCellValueFactory(new PropertyValueFactory<>("name"));
        } catch (DALException dalException) {
            SoftAlert.displayAlert(dalException.getMessage());
        }
    }

    private void populateCasesGraded() {
        try {
            casesGradedList.getItems().addAll(model.getCasesGradedOf(currentGroup));
            nameCOLCasesGraded.setCellValueFactory(new PropertyValueFactory<>("name"));
        } catch (DALException dalException) {
            SoftAlert.displayAlert(dalException.getMessage());
        }
    }


    @FXML
    private void caseAssignedIsSelected(MouseEvent event) {
        if (casesAssignedList.getSelectionModel().getSelectedItem() != null) {
            this.currentCase = casesAssignedList.getSelectionModel().getSelectedItem();
            try {
                this.currentPatient = model.getPatientOf(currentGroup, currentCase);
            } catch (DALException dalException) {
                SoftAlert.displayAlert(dalException.getMessage());
            }
            FieldsManager.displayPatientInfo(patientOverviewTab,currentPatient,nameField,familyNameField,dateOfBirthPicker,
                    genderComboBox,medicalHistoryTextArea);
            FieldsManager.displayCaseInfo(caseTab, currentCase,caseNameField,descriptionOfConditionText);
        }
    }

    @FXML
    private void caseGradedIsSelected(MouseEvent event) {
        if (casesGradedList.getSelectionModel().getSelectedItem() != null) {
            this.currentCase = casesGradedList.getSelectionModel().getSelectedItem();
            try {
                this.currentPatient = model.getPatientOf(currentGroup, currentCase);
            } catch (DALException dalException) {
                SoftAlert.displayAlert(dalException.getMessage());
            }
            FieldsManager.displayPatientInfo(patientOverviewTab,currentPatient,nameField,familyNameField,dateOfBirthPicker,
                    genderComboBox,medicalHistoryTextArea);
            FieldsManager.displayCaseInfo(caseTab, currentCase,caseNameField,descriptionOfConditionText);
        }
    }

    @FXML
    private void newObservation(ActionEvent event) {
        FieldsManager.handleObservationStudentView(newObservationTextArea,model,currentPatient,medicalHistoryTextArea);
    }

    @FXML
    void saveChangesOnCase(ActionEvent event) {
        if (FieldsManager.caseFieldsAreFilled(caseNameField,descriptionOfConditionText)) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                    "Are you sure you want to update this case?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.YES) {
                try {
                    currentCase.setName(caseNameField.getText());
                    currentCase.setConditionDescription(descriptionOfConditionText.getText());
                    model.updateCase(currentCase);
                    model.updateCaseInTable(currentCase);
                    refreshCasesAssignedList();
                } catch (DALException dalException) {
                    dalException.printStackTrace();
                    SoftAlert.displayAlert(dalException.getMessage());
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
        if (FieldsManager.patientFieldsAreFilled(nameField,familyNameField,dateOfBirthPicker,genderComboBox)) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                    "Are you sure you want to update this patient?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.YES) {
                try {
                    FieldsManager.updateVariablesOfPatient(currentPatient, nameField,familyNameField,dateOfBirthPicker,genderComboBox);
                    model.updatePatient(currentPatient);
                } catch (DALException dalException) {
                    SoftAlert.displayAlert(dalException.getMessage());
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
        } catch (DALException dalException) {
            SoftAlert.displayAlert(dalException.getMessage());
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

    @FXML
    void logOut(ActionEvent event) {
        closeWindows();
        Parent root1;
        Stage stage = (Stage) casesAssignedList.getScene().getWindow();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../Views/Login.fxml"));
            root1 = (Parent) fxmlLoader.load();
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void closeWindows() { //TODO TO be implemented
        for (Stage listOfStage : listOfStages) {
            listOfStage.close();
        }
        listOfStages.clear();
        model.clearLists();
    }
}
