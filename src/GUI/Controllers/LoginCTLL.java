package GUI.Controllers;

import BE.User;
import BLL.Exceptions.BLLException;
import DAL.Exceptions.DALException;
import GUI.Alerts.SoftAlert;
import GUI.Models.LoginMOD;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginCTLL implements Initializable {

    @FXML
    private AnchorPane loginPane;

    @FXML
    private ImageView loginIMG;

    @FXML
    private TextField emailField;

    @FXML
    private Label emailLbl;

    @FXML
    private Button loginBtn;

    @FXML
    private TextField passwordField;

    @FXML
    private Label passwordLBL;

    private User logedUser;
    private LoginMOD loginMOD;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loginMOD = new LoginMOD();
    }

    @FXML
    void loginAct(ActionEvent event) {
        if(!emailField.getText().isEmpty() && !passwordField.getText().isEmpty()){
            try{
                logedUser = loginMOD.checkCredentials(emailField.getText(),passwordField.getText());
                switch (logedUser.getUserType()) {
                    case 3:
                        openView("GUI/Views/StudentMain.fxml", "FS3 for Students", 880, logedUser);
                        break;
                    case 2:
                        openView("GUI/Views/TeacherMain.fxml", "FS3 for Students", 880, logedUser);
                        break;
                    case 1:
                        openView("GUI/Views/Admin.fxml","Admin FS3", 1000, logedUser);
                }
            }catch (DALException | BLLException exception){
                SoftAlert.displayAlert(exception.getMessage());
            }
        }
    }

    private void closeWindow(){
        Stage st = (Stage) loginPane.getScene().getWindow();
        st.close();
    }

    private void openView(String resource, String title, int width, User logedUser){
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource(resource));
        Parent root = null;
        try{root = loader.load();}
        catch (IOException e){
            e.printStackTrace();
        }
        assert root != null;
        root.getStylesheets().add("GUI/Views/CSS/GeneralCSS.css");
        switch (logedUser.getUserType()) {
            case 3 -> {
                loader.<StudentMainCTLL>getController().setUser(logedUser);
                loader.<StudentMainCTLL>getController().initializeView();
            }
            case 2 -> {
                loader.<TeacherMainCTLL>getController().setUser(logedUser);
                loader.<TeacherMainCTLL>getController().initializeView();
            }
            case 1 -> {
                loader.<AdminCTLL>getController().setUser(logedUser);
                loader.<AdminCTLL>getController().initializeView();
            }
        }
        Stage stage = new Stage();
        stage.setTitle(title);
        stage.setScene(new Scene(root,width,660));
        stage.setResizable(false);
        stage.show();
        closeWindow();
    }
}