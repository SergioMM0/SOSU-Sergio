package GUI.Controllers;

import BE.Case;
import BE.Group;
import BE.Patient;
import BE.User;
import DAL.util.DalException;
import GUI.Alerts.SoftAlert;
import GUI.Models.TeacherMainMOD;
import GUI.Util.StaticData;
import GUI.Util.FieldsManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TeacherMainCTLL {

    @FXML
    private ComboBox<String> caseCategoryComboBox;

    @FXML
    private TextField caseNameField;

    @FXML
    private ComboBox<String> caseSubcategoryComboBox;

    @FXML
    private TableView<Case> casesAssignedList;

    @FXML
    private TableColumn<Case, String> nameColCases;

    @FXML
    private TableView<Case> casesGradedList;

    @FXML
    private TableColumn<Case, String> nameColCasesGraded;

    @FXML
    private TableView<Case> casesListGV;

    @FXML
    private TableColumn<Group, String> nameColCasesGV;

    @FXML
    private TextArea descriptionOfConditionText;

    @FXML
    private TextArea newObservationTextArea;

    @FXML
    private TableColumn<Group, String> groupNameCol;

    @FXML
    private TableView<User> participantsTable;

    @FXML
    private TableColumn<User, String> participantsNameCol;

    @FXML
    private Label groupNameLBL;

    @FXML
    private TableView<Group> groupsTable;

    @FXML
    private Label medicalHistoryLBL;

    @FXML
    private TextArea medicalHistoryTextArea;

    @FXML
    private TableView<Patient> patientsListGV;

    @FXML
    private TextField cprField;

    @FXML
    private DatePicker dateOfBirthPicker;

    @FXML
    private TextField familyNameField;

    @FXML
    private ComboBox<String> genderComboBox;

    @FXML
    private TextField heightField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField phoneNumberField;

    @FXML
    private TextField weightField;

    @FXML
    private TableColumn<Patient, String> nameColPatientsGV;

    @FXML
    private TableColumn<Group, String> studentNameCol;

    @FXML
    private Label studentNamesLBL;

    @FXML
    private TableView<User> studentsTable;

    @FXML
    private Tab patientOverviewTab;

    @FXML
    private TabPane tabPane;

    @FXML
    private Tab caseTab;

    @FXML
    private Tab groupTab;

    @FXML
    private Tab studentsGroupsTab;

    private User logedUser;
    private final String generalCSS = "GUI/Views/CSS/GeneralCSS.css";
    private TeacherMainMOD model;
    private final StaticData staticData;
    private boolean patientFromGV;
    private boolean caseFromGV;
    private boolean fromGeneralView;
    private Group currentGroup;
    private Patient currentPatient;
    private Case currentCase;
    private ArrayList<Stage> listOfStages = new ArrayList<>();
    private SoftAlert softAlert;
    private FieldsManager fieldsManager;

    public void setUser(User user) {
        logedUser = user;
    }

    public TeacherMainCTLL() {
        model = TeacherMainMOD.getInstance();
        staticData = StaticData.getInstance();
        softAlert = SoftAlert.getInstance();
        fieldsManager = new FieldsManager();
    }

    protected void initializeView() {
        populateGroupsTable();
        populateCasesTable();
        populatePatientsTable();
        populateStudentsTable();
        setUPContextMenus();
        setUpTabs();
    }

    private void setUpTabs() {
        patientOverviewTab.setDisable(true);
        caseTab.setDisable(true);
        groupTab.setDisable(true);
        medicalHistoryTextArea.setEditable(false);
    }

    private void populateGroupsTable() {
        try {
            groupsTable.getItems().addAll(model.getAllGroups(logedUser.getSchoolID()));
            groupNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        } catch (DalException dalException) {
            dalException.printStackTrace();
            softAlert.displayAlert(dalException.getMessage());
        }
    }

    private void populateCasesTable() {
        try {
            casesListGV.getItems().addAll(model.getAllCases(logedUser.getSchoolID()));
            nameColCasesGV.setCellValueFactory(new PropertyValueFactory<>("name"));
        } catch (DalException dalException) {
            dalException.printStackTrace();
            softAlert.displayAlert(dalException.getMessage());
        }
    }

    private void populatePatientsTable() {
        try {
            patientsListGV.getItems().addAll(model.getAllPatients(logedUser.getSchoolID()));
            nameColPatientsGV.setCellValueFactory(new PropertyValueFactory<>("first_name"));
        } catch (DalException dalException) {
            dalException.printStackTrace();
            softAlert.displayAlert(dalException.getMessage());
        }
    }

    private void populateStudentsTable() {
        try {
            studentsTable.getItems().addAll(model.getAllStudents(logedUser.getSchoolID()));
            studentNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        } catch (DalException dalException) {
            dalException.printStackTrace();
            softAlert.displayAlert(dalException.getMessage());
        }
    }


    protected void addPatientToList(Patient patient) {
        model.addPatientToList(patient);
        refreshPatientsList();
    }

    protected void refreshPatientsList() {
        patientsListGV.getItems().clear();
        patientsListGV.getItems().addAll(model.getObservablePatients());
    }


    protected void addCaseToList(Case newCase) {
        model.addCaseToList(newCase);
        refreshCasesList();
    }

    protected void refreshCasesList() {
        casesListGV.getItems().clear();
        casesListGV.getItems().addAll(model.getObservableCases());
    }

    @FXML
    private void addNewCase(ActionEvent event) {
        openView("GUI/Views/CreateCase.fxml", generalCSS, "Create new case", 860, 700, false, 0);
    }

    @FXML
    private void addNewPatient(ActionEvent event) {
        openView("GUI/Views/CreatePatient.fxml", generalCSS, "Create new patient", 485, 510, false, 0);
    }

    @FXML
    private void caseIsSelected(MouseEvent event) {
        this.currentCase = casesListGV.getSelectionModel().getSelectedItem();
        this.caseFromGV = true;
        if (currentCase != null) {
            fieldsManager.displayCaseInfo(caseTab, currentCase, caseCategoryComboBox,caseSubcategoryComboBox,caseNameField,descriptionOfConditionText);
        }
    }

    @FXML
    private void categorySelected(MouseEvent event) {
        if (caseCategoryComboBox.getValue() != null) {
            caseSubcategoryComboBox.getItems().clear();
            caseSubcategoryComboBox.getItems().addAll(
                    staticData.getSubcategoriesOf(caseCategoryComboBox.getValue()));
        }
    }

    @FXML
    private void patientIsSelected(MouseEvent event) {
        this.currentPatient = patientsListGV.getSelectionModel().getSelectedItem();
        if(currentPatient != null){
            this.patientFromGV = true;
            fieldsManager.displayPatientInfo(patientOverviewTab, currentPatient, nameField,familyNameField,dateOfBirthPicker,
                    genderComboBox,weightField,heightField,cprField,phoneNumberField,medicalHistoryTextArea);
        }
    }

    @FXML
    private void newObservation(ActionEvent event) {
        fieldsManager.handleObservationTeacherView(newObservationTextArea,model,currentPatient,medicalHistoryTextArea);
    }

    @FXML
    private void updatePatient(ActionEvent actionEvent) {
        if (fieldsManager.patientFieldsAreFilled(nameField,familyNameField,dateOfBirthPicker,genderComboBox,weightField,heightField,cprField,phoneNumberField)) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                    "Are you sure you want to update this patient?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.YES) {
                if(!patientFromGV){
                    this.currentPatient = model.getPatientOfCase();
                    try {
                        updatePatientInDB(currentPatient);  //TODO check if debugged
                        fieldsManager.displayPatientInfo(patientOverviewTab, currentPatient,nameField,familyNameField,
                                dateOfBirthPicker,genderComboBox,weightField,heightField,cprField,phoneNumberField,medicalHistoryTextArea);
                    } catch (DalException dalException) {
                        dalException.printStackTrace();
                        softAlert.displayAlert(dalException.getMessage());
                    }
                }else {
                    try {
                        updatePatientInDB(currentPatient); //TODO check if debugged
                        model.updatePatientInTable(currentPatient);
                        refreshPatientsList();
                        fieldsManager.displayPatientInfo(patientOverviewTab, currentPatient,nameField,familyNameField,
                                dateOfBirthPicker,genderComboBox,weightField,heightField,cprField,phoneNumberField,medicalHistoryTextArea);
                    } catch (DalException dalException) {
                        dalException.printStackTrace();
                        softAlert.displayAlert(dalException.getMessage());
                    }
                }
            }
        }
    }

    private void updatePatientInDB(Patient patient) throws DalException{
        fieldsManager.updateVariablesOfPatient(currentPatient, nameField,familyNameField,dateOfBirthPicker,genderComboBox,
                weightField,heightField,cprField,phoneNumberField);
        model.updatePatient(patient);
    }

    @FXML
    private void addStudentToGroup(ActionEvent event) {
        User student = studentsTable.getSelectionModel().getSelectedItem();
        Group group = groupsTable.getSelectionModel().getSelectedItem();
        if (student != null && group != null) {
            if (!group.containsMember(student)) {
                try {
                    model.addStudentToGroup(group,student);
                    model.updateObservableGroup(group.addMember(student));
                    populateParticipantsTable(group);
                    setUpGroup(group);
                } catch (DalException dalException) {
                    softAlert.displayAlert(dalException.getMessage());
                }
            } else softAlert.displayAlert("This student is already in the group");
        } else {
            if (studentsTable.getSelectionModel().getSelectedItem() == null) {
                softAlert.displayAlert("Please select a student");
            } else softAlert.displayAlert("Please select a group");
        }
    }

    @FXML
    private void groupIsSelected(MouseEvent event) {
        if(groupsTable.getSelectionModel().getSelectedItem() != null){
            this.currentGroup = groupsTable.getSelectionModel().getSelectedItem();
            populateParticipantsTable(groupsTable.getSelectionModel().getSelectedItem());
            populateGroupsTab(groupsTable.getSelectionModel().getSelectedItem());
        }
    }

    private void populateParticipantsTable(Group group) {
        if (group.getMembers() != null) {
            participantsTable.getItems().clear();
            participantsTable.getItems().addAll(model.getObservableParticipants(group));
            participantsNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        }
        else{
            groupsTable.getItems().clear();
        }
    }

    protected void setUpGroup(Group group){
        groupTab.setText(group.getName());
        groupNameLBL.setText(group.getName());
        setUpStudentsLBL(group);
    }

    private void populateGroupsTab(Group group) {
        if (groupTab.isDisabled()) {
            groupTab.setDisable(false);
        }
        setUpGroup(group);
        populateCasesAssigned(group);
        populateCasesGraded(group);
    }

    private void populateCasesGraded(Group group) {
        try{
            casesGradedList.getItems().clear();
            casesGradedList.getItems().addAll(model.getCasesGradedOfGroup(group));
            nameColCasesGraded.setCellValueFactory(new PropertyValueFactory<>("name"));
        }catch (DalException dalException){
            softAlert.displayAlert(dalException.getMessage());
        }
    }

    protected void populateCasesAssigned(Group group) {
        try{
            casesAssignedList.getItems().clear();
            casesAssignedList.getItems().addAll(model.getCasesAssignedToGroup(group));
            nameColCases.setCellValueFactory(new PropertyValueFactory<>("name"));
        }catch (DalException dalException){
            dalException.printStackTrace();
            softAlert.displayAlert(dalException.getMessage());
        }
    }

    private void setUpStudentsLBL(Group group) {
        StringBuilder sb = new StringBuilder();
        List<User> participants = model.getObservableParticipants(group);
        for (int i = 0; i < participants.size(); i++) {
            if (i == participants.size() - 1) {
                sb.append(participants.get(i).getName());
            } else sb.append(participants.get(i).getName()).append(", ");

        }
        studentNamesLBL.setText(sb.toString());
    }

    private void setUPContextMenus() {
        ContextMenu contm = new ContextMenu();
        participantsTable.addEventHandler(MouseEvent.MOUSE_CLICKED, t -> {
            if (t.getButton() == MouseButton.SECONDARY) {
                contm.show(participantsTable, t.getScreenX(), t.getScreenY());
            } else contm.hide();
        });
        MenuItem mi1 = new MenuItem("Remove participant");
        mi1.setOnAction(t -> {
            contm.hide();
            populateParticipantsTable(removeParticipant());
        });
        contm.getItems().add(mi1);
    }

    @FXML
    private void removeStudentFromGroup(ActionEvent event) {
        if(participantsTable.getSelectionModel().getSelectedItem() != null){
            removeParticipant();
        }
    }

    private Group removeParticipant() {
        Group group = groupsTable.getSelectionModel().getSelectedItem();
        User participant = participantsTable.getSelectionModel().getSelectedItem();
        try {
            model.removeParticipant(group,participant);
            model.removeObservableParticipant(group,participant);
            populateParticipantsTable(group);
            setUpGroup(group);
        } catch (DalException dalException) {
            softAlert.displayAlert(dalException.getMessage());
        }
        return group;
    }

    @FXML
    private void assignedCaseIsSelected(MouseEvent event) {
        this.caseFromGV = false;
        handleCaseSelected(casesAssignedList);
    }

    @FXML
    private void gradedCaseIsSelected(MouseEvent event) {
        this.caseFromGV = false;
        handleCaseSelected(casesGradedList);
    }

    private void handleCaseSelected(TableView<Case> tableCases){
        this.currentCase = tableCases.getSelectionModel().getSelectedItem();
        if(currentCase != null){
            fieldsManager.displayCaseInfo(caseTab, currentCase, caseCategoryComboBox,caseSubcategoryComboBox,caseNameField,descriptionOfConditionText);
            try{
                this.patientFromGV = false;
                this.currentPatient = model.getPatientOfCaseInGroup(currentCase,currentGroup);
                fieldsManager.displayPatientInfo(patientOverviewTab,currentPatient,nameField,familyNameField,dateOfBirthPicker,
                        genderComboBox,weightField,heightField,cprField,phoneNumberField,medicalHistoryTextArea);
            }catch (DalException dalException){
                softAlert.displayAlert(dalException.getMessage());
            }
        }
    }




    @FXML
    private void addNewStudent(ActionEvent event) {
        openView("GUI/Views/ManageStudent.fxml", generalCSS, "Add new Student", 400, 220, false, 1);
    }

    @FXML
    private void editStudent(ActionEvent event) {
        if (studentsTable.getSelectionModel().getSelectedItem() != null) {
            openView("GUI/Views/ManageStudent.fxml", generalCSS, "Edit student", 400, 220, false, 2);
        } else softAlert.displayAlert("Please select a student");
    }

    protected void addStudentToTable(User user) {
        model.addObservableStudent(user);
        refreshStudentsTable();
    }

    protected void updateStudentInTable(User student) {
        model.updateStudentInLists(student);
        refreshStudentsTable();
        refreshGroupList();
        participantsTable.getItems().clear();
    }

    private void refreshStudentsTable() {
        studentsTable.getItems().clear();
        studentsTable.getItems().addAll(model.getObservableStudents());
    }

    @FXML
    private void assignCaseToGroup(ActionEvent event) {
        if(casesListGV.getSelectionModel().getSelectedItem() != null){
            handleAssignCaseToGroup();
        }else softAlert.displayAlert("Please select a case");
    }

    private void handleAssignCaseToGroup() {
        openView("GUI/Views/AssignNewCaseToGroup.fxml",generalCSS,"Assign case to group",530,400,false,0);
    }

    @FXML
    private void createGroup(ActionEvent event) {
        openView("GUI/Views/ManageGroup.fxml", generalCSS, "Add new group", 400, 180, false, 1);
    }

    @FXML
    private void editGroup(ActionEvent event) {
        if (groupsTable.getSelectionModel().getSelectedItem() != null) {
            openView("GUI/Views/ManageGroup.fxml", generalCSS, "Edit group", 400, 180, false, 2);
        } else softAlert.displayAlert("Please select a group");
    }

    protected void addGroupToList(Group group) {
        model.addObservableGroup(group);
        refreshGroupList();
    }

    protected void updateGroupInList(Group group) {
        model.updateObservableGroup(group);
        refreshGroupList();
    }

    protected void refreshGroupList() {
        groupsTable.getItems().clear();
        groupsTable.getItems().addAll(model.getObservableGroups());
    }

    @FXML
    private void deleteGroup(ActionEvent event) {
        if (groupsTable.getSelectionModel().getSelectedItem() != null) {
            try {
                model.deleteGroup(groupsTable.getSelectionModel().getSelectedItem());
            } catch (DalException dalException) {
                softAlert.displayAlert(dalException.getMessage());
            }
            model.removeObservableGroup(groupsTable.getSelectionModel().getSelectedItem());
            refreshGroupList();
        }
    }

    @FXML
    private void deleteCase(ActionEvent event) {
        handleDeleteCase();
    }

    private void handleDeleteCase() {
        if (casesListGV.getSelectionModel().getSelectedItem() != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                    "Are you sure you want to delete this case?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.YES) {
                try {
                    model.deleteCase(casesListGV.getSelectionModel().getSelectedItem());
                    model.deleteObservableCase(casesListGV.getSelectionModel().getSelectedItem());
                    refreshCasesList();
                    blockCaseTab();
                } catch (DalException dalException) {
                    dalException.printStackTrace();
                    softAlert.displayAlert(dalException.getMessage());
                }
            }
        }
    }

    @FXML
    private void deletePatient(ActionEvent event) {
        handleDeletePatient();
    }

    private void handleDeletePatient() {
        if (patientsListGV.getSelectionModel().getSelectedItem() != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                    "Are you sure you want to delete this patient?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.YES) {
                try {
                    model.deletePatient(patientsListGV.getSelectionModel().getSelectedItem());
                    model.deleteObservablePatient(patientsListGV.getSelectionModel().getSelectedItem());
                    refreshPatientsList();
                    blockPatientTab();
                } catch (DalException dalException) {
                    dalException.printStackTrace();
                    softAlert.displayAlert(dalException.getMessage());
                }
            }
        }
    }

    @FXML
    private void deleteStudent(ActionEvent event) {
        User student = studentsTable.getSelectionModel().getSelectedItem();
        try {
            model.deleteStudent(student);
        } catch (DalException dalException) {
            softAlert.displayAlert(dalException.getMessage());
        }
        model.deleteObservableStudent(student);
        model.deleteStudentFromGroups(student);
        refreshStudentsTable();
        refreshGroupList();
    }

    @FXML
    private void duplicateCase(ActionEvent event) {

    }

    @FXML
    private void duplicatePatient(ActionEvent event) {

    }

    @FXML
    private void logOut(ActionEvent event) {
        closeWindows();
        Parent root1;
        Stage stage = (Stage) studentsTable.getScene().getWindow();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../Views/Login.fxml"));
            root1 = (Parent) fxmlLoader.load();
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void markCaseAsGraded(ActionEvent event) {
        if(casesAssignedList.getSelectionModel().getSelectedItem() != null){
            try{
                handleMarkCaseAsGraded(casesAssignedList.getSelectionModel().getSelectedItem());
            } catch (DalException dalException){
                dalException.printStackTrace();
                softAlert.displayAlert(dalException.getMessage());
            }
            model.moveCaseToGradedList(casesAssignedList.getSelectionModel().getSelectedItem());
            refreshCasesAssigned();
            refreshCasesGraded();
        }
    }

    @FXML
    private void unmarkCaseAsGraded(ActionEvent event) {
        if(casesGradedList.getSelectionModel().getSelectedItem()!= null){
            try{
                model.unmarkCaseAsGraded(casesGradedList.getSelectionModel().getSelectedItem());
            }catch (DalException dalException){
                softAlert.displayAlert(dalException.getMessage());
            }
            model.moveCaseToAssignedList(casesGradedList.getSelectionModel().getSelectedItem());
            refreshCasesGraded();
            refreshCasesAssigned();
        }
    }

    private void handleMarkCaseAsGraded(Case selectedItem) throws DalException {
        model.markCaseAsGraded(selectedItem);
    }

    private void refreshCasesGraded() {
        casesGradedList.getItems().clear();
        casesGradedList.getItems().addAll(model.getObservableCasesGraded());
    }

    @FXML
    private void openGradeCase(ActionEvent event) {
        openView("GUI/Views/StudentQuestion.fxml", "", "Question Overview", 880, 660, false, 0);
    }

    @FXML
    private void openGradedCase(ActionEvent event) {

    }

    @FXML
    private void openManageGroup(ActionEvent event) {
        tabPane.getSelectionModel().select(studentsGroupsTab);
    }

    @FXML
    private void saveChangesOnCase(ActionEvent event) {
        if (fieldsManager.caseFieldsAreFilled(caseNameField,descriptionOfConditionText)) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                    "Are you sure you want to update this case?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.YES) {
                if(!caseFromGV){
                    try {
                        currentCase.setName(caseNameField.getText());
                        currentCase.setConditionDescription(descriptionOfConditionText.getText());
                        currentCase.setCategory(caseCategoryComboBox.getValue());
                        currentCase.setSubCategory(caseSubcategoryComboBox.getValue());
                        model.updateCase(currentCase);
                    } catch (DalException dalException) {
                        dalException.printStackTrace();
                        softAlert.displayAlert(dalException.getMessage());
                    }
                }
                else{
                    try {
                        currentCase.setName(caseNameField.getText());
                        currentCase.setConditionDescription(descriptionOfConditionText.getText());
                        currentCase.setCategory(caseCategoryComboBox.getValue());
                        currentCase.setSubCategory(caseSubcategoryComboBox.getValue());
                        model.updateCase(currentCase);
                        model.updateCaseInTable(currentCase);
                        refreshCasesList();
                    } catch (DalException dalException) {
                        dalException.printStackTrace();
                        softAlert.displayAlert(dalException.getMessage());
                    }
                }
            }
        }
    }

    @FXML
    private void unassignSelectedCase(ActionEvent event) {
        if(casesAssignedList.getSelectionModel().getSelectedItem()!= null){
            try{
                model.unassignCase(casesAssignedList.getSelectionModel().getSelectedItem());
            }catch (DalException dalException){
                softAlert.displayAlert(dalException.getMessage());
            }
            model.deleteAssignedCaseInList(casesAssignedList.getSelectionModel().getSelectedItem());
            refreshCasesAssigned();
            blockPatientTab();
            blockCaseTab();
        }
    }
    private void refreshCasesAssigned(){
        casesAssignedList.getItems().clear();
        casesAssignedList.getItems().addAll(model.getObservableCasesAssigned());
    }

    private void blockPatientTab(){
        patientOverviewTab.setDisable(true);
        patientOverviewTab.setText("Patient info");
    }

    private void blockCaseTab() {
        caseTab.setDisable(true);
        caseTab.setText("Case info");
    }

    private void openView(String resource, String css, String title, int width, int height, boolean resizable, int operationType) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource(resource));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert root != null;
        if (resource.equals("GUI/Views/CreatePatient.fxml")) {
            loader.<NewPatientCTLL>getController().setUser(logedUser);
            loader.<NewPatientCTLL>getController().setController(this);
        }
        if (resource.equals("GUI/Views/CreateCase.fxml")) {
            loader.<NewCaseCTLL>getController().setController(this);
            loader.<NewCaseCTLL>getController().setUser(logedUser);
        }
        if (resource.equals("GUI/Views/ManageStudent.fxml") && operationType == 1) {
            loader.<ManageStudentCTLL>getController().setUser(logedUser);
            loader.<ManageStudentCTLL>getController().setController(this);
            loader.<ManageStudentCTLL>getController().setOperationType(operationType);
        }
        if (resource.equals("GUI/Views/ManageStudent.fxml") && operationType == 2) {
            loader.<ManageStudentCTLL>getController().setUser(logedUser);
            loader.<ManageStudentCTLL>getController().setController(this);
            loader.<ManageStudentCTLL>getController().setOperationType(operationType);
            loader.<ManageStudentCTLL>getController().setStudent(studentsTable.getSelectionModel().getSelectedItem());
            loader.<ManageStudentCTLL>getController().populateStudentFields();
        }
        if (resource.equals("GUI/Views/ManageGroup.fxml") && operationType == 1) {
            loader.<ManageGroupCTLL>getController().setUser(logedUser);
            loader.<ManageGroupCTLL>getController().setController(this);
            loader.<ManageGroupCTLL>getController().setOperationType(operationType);
        }
        if (resource.equals("GUI/Views/ManageGroup.fxml") && operationType == 2) {
            loader.<ManageGroupCTLL>getController().setUser(logedUser);
            loader.<ManageGroupCTLL>getController().setGroup(groupsTable.getSelectionModel().getSelectedItem());
            loader.<ManageGroupCTLL>getController().setController(this);
            loader.<ManageGroupCTLL>getController().setOperationType(operationType);
            loader.<ManageGroupCTLL>getController().populateGroupField();
        }
        if(resource.equals("GUI/Views/AssignNewCaseToGroup.fxml")){
            loader.<AssignCaseCTLL>getController().setCase(casesListGV.getSelectionModel().getSelectedItem());
            loader.<AssignCaseCTLL>getController().setController(this);
            loader.<AssignCaseCTLL>getController().initializeView();
        }
        if (resource.equals("GUI/Views/StudentQuestion.fxml")) {
            StudentQuestionCTL controller = loader.<StudentQuestionCTL>getController();
            Case selectedCase = casesAssignedList.getSelectionModel().getSelectedItem();  //find selected case from case list
            controller.setCase(selectedCase);
            controller.setGroup(groupsTable.getSelectionModel().getSelectedItem());     //find selected group
            controller.disableQuestionTab();            //student question tab will be disabled
            controller.setModelQuestionnaireId();       //set questionnaire Id in application according to selected case and group
            controller.fillOverviewList();      //laod data from database and fill it in overview list
            controller.openOverViewTab();
        }
        root.getStylesheets().add(css);
        Stage stage = new Stage();
        stage.setTitle(title);
        stage.setScene(new Scene(root, width, height));
        listOfStages.add(stage);
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
