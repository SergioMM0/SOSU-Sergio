package GUI.Alerts;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;

public class ConfirmationAlert {

    private static ConfirmationAlert instance;
    private ButtonType answer;

    private ConfirmationAlert(){}

    public static ConfirmationAlert getInstance(){
        if(instance == null){
            instance = new ConfirmationAlert();
        }return instance;
    }

    public void displayConfirmationAlert(String message){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                message, ButtonType.YES, ButtonType.CANCEL);
        alert.showAndWait();
        answer = alert.getResult();
    }

    public ButtonType getAnswer(){
        return this.answer;
    }
}
