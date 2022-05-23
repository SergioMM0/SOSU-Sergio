import DAL.Manager1.DAOCase;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.w3c.dom.ls.LSOutput;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("GUI/Views/Login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 500, 450);
        scene.getStylesheets().add("GUI/Views/CSS/GeneralCSS.css");
        stage.setResizable(false);
        stage.setTitle("Simulation platform - Log in");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}