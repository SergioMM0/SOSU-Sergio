package GUI.Util;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class StaticData {

    public static ObservableList<String> getGenders() {
        ObservableList<String> genders = FXCollections.observableArrayList();
        genders.add("Male");
        genders.add("Female");
        genders.add("Not relevant");
        return genders;
    }

    public static int getStudentType(){
        return 3;
    }

    public static int getTeacherType(){
        return 2;
    }
}
