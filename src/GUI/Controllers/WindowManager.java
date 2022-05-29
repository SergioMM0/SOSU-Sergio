package GUI.Controllers;

import BE.Case;
import BE.Group;
import BE.Patient;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WindowManager {
    /**
     * Implementation of this class was not finished, it was a try of making a class that would manage the windows in the app
     * in order to delete de duplication of code and make it more dynamic but the time was not helping when "the group issue"
     * popped off
     */

    private static WindowManager instance;
    private List<Stage> allStages;

    private WindowManager() {
        allStages = new ArrayList<>();
    }

    private static WindowManager getInstance() {
        if (instance == null) {
            instance = new WindowManager();
        }
        return instance;
    }

    public void openEvaluateCaseView(String resource, int width, int height, Case currentCase, Group currentGroup, Patient currentPatient) {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource(resource));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert root != null;
        loader.<EvaluateCaseCTLL>getController().setCase(currentCase);
        loader.<EvaluateCaseCTLL>getController().setGroup(currentGroup);
        loader.<EvaluateCaseCTLL>getController().setPatient(currentPatient);
        loader.<EvaluateCaseCTLL>getController().initializeView();
        root.getStylesheets().add("GUI/Views/CSS/GeneralCSS.css");
        Stage stage = new Stage();
        stage.setTitle("Evaluate case");
        allStages.add(stage);
        stage.setScene(new Scene(root, width, height));
        stage.setResizable(false);
        stage.showAndWait();
    }
}
