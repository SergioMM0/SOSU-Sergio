package GUI.Alerts;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class SoftAlert {

    private static final String errTitle = "Something went wrong";
    private static SoftAlert instance;

    private SoftAlert(){
    }

    public static SoftAlert getInstance(){
        if(instance == null){
            instance = new SoftAlert();
        }
        return instance;
    }

    public void displayAlert(String message){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(errTitle);
        alert.setHeaderText(message);
        ButtonType okButton = new ButtonType("OK");
        alert.getButtonTypes().setAll(okButton);
        alert.showAndWait();
    }


}
