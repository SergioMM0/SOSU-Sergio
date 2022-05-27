package GUI.Controllers;

import BE.School;
import DAL.Exceptions.DALException;
import GUI.Alerts.SoftAlert;
import GUI.Models.AdminMOD;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ManageSchoolCTLL {

    @FXML
    private TextField nameField;

    private int operationType;
    private School currenSchool;
    private AdminCTLL adminCTLL;
    private AdminMOD model;

    public ManageSchoolCTLL(){
        model = AdminMOD.getInstance();
    }

    protected void initializeView(){
        nameField.setText(currenSchool.getName());
    }

    @FXML
    void save(ActionEvent event) {
        switch (operationType){
            case 1:
                if(fieldsAreFilled()){
                    currenSchool = new School(nameField.getText());
                    try{
                        model.addObservableSchool(model.addSchool(currenSchool));
                        adminCTLL.refreshSchoolTable();
                        closeWindow();
                    }catch (DALException dalException){
                        dalException.printStackTrace();
                        SoftAlert.displayAlert(dalException.getMessage());
                    }
                }
                break;
            case 2:
                if(fieldsAreFilled()){
                    currenSchool.setName(nameField.getText());
                    try{
                        model.updateObservableSchool(model.updateSchool(currenSchool));
                        adminCTLL.refreshSchoolTable();
                        closeWindow();
                    }catch (DALException dalException){
                        dalException.printStackTrace();
                        SoftAlert.displayAlert(dalException.getMessage());
                    }
                }
                break;
        }
    }

    @FXML
    void cancel(ActionEvent event) {
        closeWindow();
    }

    private boolean fieldsAreFilled() {
        if(nameField.getText().isEmpty() || nameField.getText().isBlank()){
            SoftAlert.displayAlert("Please introduce a valid name for the School");
            return false;
        }else return true;
    }

    public void setOperationType(int operationType) {
        this.operationType = operationType;
    }

    public void setMainController(AdminCTLL adminCTLL) {
        this.adminCTLL = adminCTLL;
    }

    public void setSchool(School school) {
        this.currenSchool = school;
    }

    private void closeWindow(){
        Stage st = (Stage) nameField.getScene().getWindow();
        st.close();
    }
}
