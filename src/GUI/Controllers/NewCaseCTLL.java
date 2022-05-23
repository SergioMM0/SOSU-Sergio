package GUI.Controllers;

import BE.Case;
import BE.User;
import DAL.util.DalException;
import GUI.Alerts.SoftAlert;
import GUI.Models.NewCaseMOD;
import GUI.Util.StaticData;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class NewCaseCTLL implements Initializable {

    @FXML
    private Button cancelBtn;

    @FXML
    private Label caseNameLBL; //labeled in order to have a fancy color and typo :)

    @FXML
    private ComboBox<String> categoryComboBox;

    @FXML
    private TextArea descriptionOfConditionText;

    @FXML
    private TextField nameField;

    @FXML
    private Button saveChangesBtn; //Must be green on CSS and different to other buttons

    @FXML
    private ComboBox<String> subcategoryComboBox;

    private NewCaseMOD model;
    private TeacherMainCTLL teacherMainCTLL;
    private StaticData catAndSubC;
    private User logedUser;
    private static SoftAlert softAlert;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        model = new NewCaseMOD();
        catAndSubC = StaticData.getInstance();
        softAlert = SoftAlert.getInstance();
        populateCategories();
    }

    public void populateCategories(){
        String[] allCat = catAndSubC.getCategories();
        for (int i = 0; i < allCat.length-1; i++) {
            categoryComboBox.getItems().add(allCat[i]);
        }
    }

    @FXML
    void catSelected(ActionEvent event) {
        if(!categoryComboBox.getValue().isEmpty()){
                subcategoryComboBox.getItems().clear();
                subcategoryComboBox.getItems().addAll(catAndSubC.getSubcategoriesOf(categoryComboBox.getValue()));
        }
    }

    @FXML
    void cancelCreate(ActionEvent event) {
        closeWindow();
    }

    @FXML
    void saveNewCase(ActionEvent event) {
        if(fieldsAreFiled()){
            try {
                Case newCase = new Case(nameField.getText(),
                        descriptionOfConditionText.getText(),
                        categoryComboBox.getValue(),
                        subcategoryComboBox.getValue(),
                        logedUser.getSchoolID(),
                        false);
                closeWindow();
                teacherMainCTLL.addCaseToList(model.createCase(newCase));
            } catch (DalException dalException) {
                softAlert.displayAlert(dalException.getMessage());
            }
        }
    }

    private boolean fieldsAreFiled() {
        if(nameField.getText().isEmpty()){
            softAlert.displayAlert("Please introduce a name for the Case");
            return false;
        }
        else if(categoryComboBox.getValue().isEmpty()){
            softAlert.displayAlert("Please select a category for the Case");
            return false;
        }
        else if(subcategoryComboBox.getValue().isEmpty()){
            softAlert.displayAlert("Please select a subcategory for the Case");
            return false;
        }
        else if(descriptionOfConditionText.getText().isEmpty()){
            softAlert.displayAlert("Please introduce a valid description of the case");
            return false;
        }
        else return true;
    }

    private void closeWindow() {
        Stage st = (Stage) cancelBtn.getScene().getWindow();
        st.close();
    }

    public void setController(TeacherMainCTLL teacherMainCTLL) {
        this.teacherMainCTLL = teacherMainCTLL;
    }

    public void setUser(User user){
        this.logedUser = user;
    }
}

