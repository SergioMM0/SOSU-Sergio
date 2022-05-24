package GUI.Alerts;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class SoftAlert {

    public static void displayAlert(String message){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Something went wrong");
        alert.setHeaderText(message);
        ButtonType okButton = new ButtonType("OK");
        alert.getButtonTypes().setAll(okButton);
        alert.showAndWait();
    }


}
