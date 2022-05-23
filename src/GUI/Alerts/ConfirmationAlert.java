package GUI.Alerts;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class ConfirmationAlert {

    private static final String confirmationTitle = "Please confirm";

    public ConfirmationAlert(String message){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(confirmationTitle);
        alert.setHeaderText(message);
        ButtonType okButton = new ButtonType("Confirm");
        ButtonType cancelButton = new ButtonType("Cancel");
        alert.getButtonTypes().setAll(okButton,cancelButton);
        alert.showAndWait();
    }
}
