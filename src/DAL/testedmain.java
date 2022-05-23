package DAL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class testedmain extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../GUI/Views/Admin.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        scene.getStylesheets().add("GUI/Views/CSS/styles.css");
        stage.setResizable(false);
        stage.setTitle("Simulation platform - Log in");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    //TODO Test addNewCase and "case info" in teacherView



}

