package GUI.Controllers;

import BE.School;
import BE.User;
import GUI.Models.AdminMOD;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

public class AdminCTLL {

    @FXML
    private TextField filterField;

    @FXML
    private TableColumn<School, Integer> idColumn;

    @FXML
    private TableColumn<School, String> nameColumnSchool;

    @FXML
    private TableView<> schoolTableView;

    @FXML
    private TableColumn<?, ?> studentEmail;

    @FXML
    private TableColumn<?, ?> studentName;

    @FXML
    private TableView<?> studentTableView;

    @FXML
    private TableColumn<?, ?> teacherEmail;

    @FXML
    private TableColumn<?, ?> teacherName;

    @FXML
    private TableView<?> teacherTableView;

    @FXML
    private Label welcomeLBL;

    private AdminMOD adminMOD;
    private User currentUser;

    public AdminCTLL(){
        adminMOD = new AdminMOD();
    }

    @FXML
    void schoolIsSelected(MouseEvent event) {

    }

    @FXML
    void studentIsSelected(MouseEvent event) {

    }

    @FXML
    void teacherIsSelected(MouseEvent event) {

    }

    public void setUser(User logedUser) {
        this.currentUser = logedUser;
    }
}
