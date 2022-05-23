package GUI.Controllers;

import BE.Case;
import BE.Group;
import BE.Patient;
import BE.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.TouchPoint;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class EvaluateCaseCTLL {
    private ArrayList<Stage> listOfStages = new ArrayList<>();
    private String generalCSS = "";

    @FXML
    private Label caseIdLabel;

    @FXML
    private Label caseNameLabel;

    @FXML
    private Label categoryNameLabel;

    @FXML
    private Label causeLabel;

    @FXML
    private Label conditionLabel;

    @FXML
    private Label cprLabel;

    @FXML
    private Label dataOfBirthLabel;

    @FXML
    private Label descriptionLabel;

    @FXML
    private Label diagonseLabel;

    @FXML
    private Label firstNameLabel;

    @FXML
    private Label goalLabel;

    @FXML
    private Label lastNameLabel;

    @FXML
    private Label phoneLabel;

    @FXML
    private Label subCategoryLabel;

    public void initializeView() {
        if (currentCase == null || currentPatient == null) return;
        caseIdLabel.setText(currentCase.getId() + "");
        caseNameLabel.setText(currentCase.getName());
        categoryNameLabel.setText(currentCase.getCategory());
        subCategoryLabel.setText(currentCase.getSubCategory());
        conditionLabel.setText(currentCase.getConditionDescription());
        firstNameLabel.setText(currentPatient.getFirst_name());
        lastNameLabel.setText(currentPatient.getLast_name());
        cprLabel.setText(currentPatient.getCpr());
        dataOfBirthLabel.setText(currentPatient.getDateOfBirth().toString());
        phoneLabel.setText(currentPatient.getPhoneNumber());
    }

    @FXML
    void close(ActionEvent event) {
        Stage stage = (Stage) firstNameLabel.getScene().getWindow();
        stage.close();
    }

    public void setGroup(Group currentGroup) {
        this.currentGroup = currentGroup;
    }

    public void setPatient(Patient currentPatient) {
        this.currentPatient = currentPatient;
    }

    public void setCase(Case currentCase) {
        this.currentCase = currentCase;
    }

    private User currentStudent;
    private Group currentGroup;
    private Patient currentPatient;
    private Case currentCase;

    @FXML
    void openQuestionnaire(ActionEvent event) {
        openView("GUI/Views/StudentQuestion.fxml", generalCSS, "Student Question", 880, 660, false);
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
        StudentQuestionCTL controller = loader.getController();
        controller.setUser(currentStudent);
        controller.setGroup(currentGroup);
        controller.setCase(currentCase);
        controller.setPatient(currentPatient);
        root.getStylesheets().add(css);
        Stage stage = new Stage();
        stage.setTitle(title);
        listOfStages.add(stage);
        stage.setScene(new Scene(root, width, height));
        stage.setResizable(resizable);
        stage.showAndWait();
    }

    public void setUser(User currentStudent) {
        this.currentStudent = currentStudent;
    }
}
