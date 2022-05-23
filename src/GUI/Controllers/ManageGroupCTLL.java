package GUI.Controllers;

import BE.Group;
import BE.User;
import DAL.util.DalException;
import GUI.Alerts.SoftAlert;
import GUI.Models.ManageGroupMOD;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.Locale;

public class ManageGroupCTLL {

    @FXML
    private TextField nameField;

    private User logedUser;
    private Group selectedGroup;
    private TeacherMainCTLL teacherMainCTLL;
    private int operationType = 0;
    private ManageGroupMOD model;
    private static SoftAlert softAlert;

    public ManageGroupCTLL(){
        model = new ManageGroupMOD();
        softAlert = SoftAlert.getInstance();
    }

    @FXML
    void cancel(ActionEvent event) {
        closeWindow();
    }

    @FXML
    void save(ActionEvent event) {
        if(operationType == 1){
            if(fieldsAreFilled()){
                try{
                    Group group = new Group(
                            nameField.getText(),
                            null,
                            logedUser.getSchoolID()
                    );
                    teacherMainCTLL.addGroupToList(model.createNewGroup(group));
                    closeWindow();
                }catch (DalException dalException){
                    dalException.printStackTrace();
                    softAlert.displayAlert(dalException.getMessage());
                }
            }
        }
        else{
            if(fieldsAreFilled() && !isTheSame()){
                try{
                    selectedGroup.setName(nameField.getText());
                    model.updateGroup(selectedGroup);
                    teacherMainCTLL.updateGroupInList(selectedGroup);
                    teacherMainCTLL.setUpGroup(selectedGroup);
                    closeWindow();
                }catch (DalException dalException){
                    softAlert.displayAlert(dalException.getMessage());
                }
            }
        }
    }

    private boolean isTheSame(){
        return nameField.getText().toLowerCase(Locale.ROOT).equals(selectedGroup.getName());
    }

    private boolean fieldsAreFilled(){
        if(nameField.getText().isEmpty()){
            softAlert.displayAlert("Please introduce a name for the group");
            return false;
        }else return true;
    }

    private void closeWindow(){
        Stage st = (Stage) nameField.getScene().getWindow();
        st.close();
    }

    public void setUser(User logedUser) {
        this.logedUser = logedUser;
    }

    public void setGroup(Group group){
        this.selectedGroup = group;
    }

    public void setController(TeacherMainCTLL teacherMainCTLL) {
        this.teacherMainCTLL = teacherMainCTLL;
    }

    public void setOperationType(int operationType) {
        this.operationType = operationType;
    }

    public void populateGroupField() {
        nameField.setText(selectedGroup.getName());
    }
}
