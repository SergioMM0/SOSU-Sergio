package GUI.Controllers;

import BE.*;
import GUI.Models.StudentQuestionMOD;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class StudentQuestionCTL implements Initializable {
    @FXML
    public Label categoryLabel;
    @FXML
    public Label titleLabel;
    @FXML
    public ImageView image1;
    @FXML
    public ImageView image2;
    @FXML
    public ImageView image3;
    @FXML
    public ImageView image4;
    @FXML
    public ImageView image5;
    @FXML
    public RadioButton state1radio;
    @FXML
    public RadioButton state2radio;
    @FXML
    public RadioButton state3radio;
    @FXML
    public RadioButton state4radio;
    @FXML
    public RadioButton state5radio;
    @FXML
    public RadioButton state6radio;
    @FXML
    public Label questionIdLabel;
    @FXML
    private TextArea textFieldQuestion;
    @FXML
    private AnchorPane titleContainer;
    @FXML
    private TableView<StudentQuestion> questionTable;
    @FXML
    private Tab overviewTab;
    @FXML
    private TabPane tabPane;
    @FXML
    private Tab questionTab;


    private Group currentGroup;
    private Patient currentPatient;
    private Case currentCase;
    StudentQuestionMOD model = new StudentQuestionMOD();    //use model to operation and contact with bll
    StudentQuestion currentQuestion;  //question that is currently showing to user
    private User currentStudent;

    public void saveQuestionAndLoadNext(ActionEvent event) {
        if (currentQuestion == null) {
            fillOverviewList();
            openOverViewTab();
            return;        //questions finished
        }
        //save question then load next question
        int state = getState();   //specify the selected state
        if (questionIdLabel.getText() == "null") return; //end of loading next questions
        int questionId = Integer.parseInt(questionIdLabel.getText());  //read question Id
        StudentQuestionnaireAnswer answer = new StudentQuestionnaireAnswer(0   //automatically will  set in database
                , questionId, state, 0 //questionnaire id will set in StudentMod class
        ); //create answer object

        model.saveStudentQuestionAnswer(answer);            //save answer to database
        currentQuestion = model.getNextQuestion(currentQuestion);       //load next question
        if (currentQuestion == null) {
            fillOverviewList();
            openOverViewTab();
            return;        //questions finished
        }

        setQuestion(currentQuestion);           //set current question to controls
        setAnswer(model.getAnswer(currentQuestion.getId()));   //set selected radio button by data from database
    }

    public void openOverViewTab() {  //open tab that shows overview of questions
        tabPane.getSelectionModel().select(overviewTab);
    }

    public void fillOverviewList() {   //fill table view
        TableColumn<StudentQuestion, String> categoryColumn = new TableColumn<>("Category");
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        TableColumn<StudentQuestion, String> titleColumn = new TableColumn<>("Title");
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        TableColumn<StudentQuestion, String> questionColumn = new TableColumn<>("Question");
        questionColumn.setCellValueFactory(new PropertyValueFactory<>("question"));
        TableColumn<StudentQuestion, String> answerColumn = new TableColumn<>("Answer");
        answerColumn.setCellValueFactory(param -> getStateDescription(param.getValue().getAnswer().getState()));        //answer column will show the answer text
        //  and because it is stored as int number we should convert
        //   it into state description so I used getStateDescription method to do this conversion
        questionTable.getColumns().clear();
        questionTable.getColumns().add(categoryColumn);
        questionTable.getColumns().add(titleColumn);
        questionTable.getColumns().add(questionColumn);
        questionTable.getColumns().add(answerColumn);
        questionTable.getItems().clear();
        questionTable.getItems().setAll(model.getQuestionnaireQuestions());
    }


    @FXML
    void loadPreviousQuestion(ActionEvent event) {
        int currentQuestionId = 0;
        if (questionIdLabel.getText() == "null")       //if we are at end of questions
            currentQuestionId = Integer.MAX_VALUE;        //set currentQuestion with biggest integer value
        else if (questionIdLabel.getText() != "")
            currentQuestionId = Integer.parseInt(questionIdLabel.getText()); ///read question id from label
        StudentQuestion previousQuestion = getPreviousQuestionId(currentQuestionId);
        if (previousQuestion == null) return;           //we are at start of question list
        currentQuestion = previousQuestion;
        setQuestion(currentQuestion);           //set controls with question data
        StudentQuestionnaireAnswer answer = model.getAnswer(currentQuestion.getId());       //get saved answer for question
        setAnswer(answer);          //set radio button to show the saved answer
    }


    private StudentQuestion getPreviousQuestionId(int currentQuestionId) {
        return model.getPreviousQuestion(currentQuestionId);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {            //when view is loaded this method will be called automatically
        insertImage();
        currentQuestion = model.GetFirstQuestion();       //get first question
        setQuestion(currentQuestion);           //set controls with quesiton data

    }

    private void setQuestion(BE.StudentQuestion question) {
        if (question == null) {
            questionIdLabel.setText("null");
            return;
        }

        categoryLabel.setText(question.getCategory());
        titleContainer.setStyle("-fx-background-color: " + question.getColor() + ";");  //set title container color with saved color for category
        titleLabel.setText(question.getTitle());
        questionIdLabel.setText(question.getId() + "");
        textFieldQuestion.setText(question.getQuestion());
        state1radio.setSelected(true);

    }

    private void insertImage() {
        Image imageOne = new Image("res/one.png");
        Image imageTwo = new Image("res/two.png");
        Image imageThree = new Image("res/three.png");
        Image imageFour = new Image("res/four.png");
        Image imageFive = new Image("res/five.png");

        image1.setImage(imageOne);
        image2.setImage(imageTwo);
        image3.setImage(imageThree);
        image4.setImage(imageFour);
        image5.setImage(imageFive);
    }

    private int getState() {
        var state = 1;
        if (state1radio.isSelected()) state = 2;
        if (state2radio.isSelected()) state = 3;
        if (state3radio.isSelected()) state = 4;
        if (state4radio.isSelected()) state = 5;
        if (state5radio.isSelected()) state = 6;

        return state;
    }

    private void setAnswer(StudentQuestionnaireAnswer answer) {
        if (answer == null) return;
        switch (answer.getState()) {
            case 1:
                state6radio.setSelected(true);
                break;
            case 2:
                state1radio.setSelected(true);
                break;
            case 3:
                state2radio.setSelected(true);
                break;
            case 4:
                state3radio.setSelected(true);
                break;
            case 5:
                state4radio.setSelected(true);
                break;
            case 6:
                state5radio.setSelected(true);
                break;
        }
    }

    private ObservableValue<String> getStateDescription(int state) {  //get state and return state description
        String[] descriptions = new String[]{"Not applicable", "None orinsignificantlimitations", "Lette limitations", "Moderate limitations", "Hard limitations", "Total limitations"};
        return new ReadOnlyObjectWrapper<>(descriptions[state - 1]);
    }


    public void setGroup(Group currentGroup) {
        this.currentGroup = currentGroup;
    }

    public void setPatient(Patient currentPatient) {
        this.currentPatient = currentPatient;
        setModelSickPatient(currentPatient, currentCase, currentGroup);
    }

    private void setModelSickPatient(Patient currentPatient, Case currentCase, Group currentGroup) {
        if (currentCase == null || currentGroup == null || currentPatient == null)
            return;
        model.setCurrentSickPatientId(currentPatient, currentCase, currentGroup);
    }

    public void setCase(Case currentCase) {
        this.currentCase = currentCase;
    }

    public void setUser(User currentStudent) {
        this.currentStudent = currentStudent;
    }

    public void disableQuestionTab() {
        questionTab.setDisable(true);

    }

    public void setModelQuestionnaireId() {             //set questionnaire Id in model according to case and group
        int questionnaireId = model.getQuestionnaireId(currentCase.getId(), currentGroup.getId());
        if (questionnaireId > 0)
            model.setQuestionnaireId(questionnaireId);
    }
}
