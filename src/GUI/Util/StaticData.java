package GUI.Util;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

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

    public static boolean subcategoryAssessed(){
        return true;
    }

    public static boolean subcategoryNotAssessed(){
        return false;
    }

    public static boolean isEditing(){
        return true;
    }
    public static boolean isNotEditing(){
        return false;
    }

    public static ObservableList<String> getRelevancyObservableList(int relevancyInt){
        ObservableList<String> relevancies = FXCollections.observableArrayList();
        relevancies.add("Irrelevant");
        relevancies.add("Potentielt");
        relevancies.add("Akute");
        return relevancies;
    }

    public static int parseRelevancyToInt(String relevancy){
        int integer = 0;
        return switch (relevancy) {
            case "Irrelevant" -> 1;
            case "Potentielt" -> 2;
            case "Akute" -> 3;
            default -> integer;
        };
    }

    public static ObservableList<String> getPerformanceObservableList(int performance){
        ObservableList<String> performances = FXCollections.observableArrayList();
        performances.add("Udfører selv");
        performances.add("Udfører dele selv");
        performances.add("Udfører ikke selv");
        performances.add("Ikke relevant");
        return performances;
    }

    public static int parsePerformanceToInt(String performance){
        int integer = 0;
        return switch (performance) {
            case "Udfører selv" -> 1;
            case "Udfører dele selv" -> 2;
            case "Udfører ikke selv" -> 3;
            case "Ikke relevant" -> 4;
            default -> integer;
        };
    }

    public static ObservableList<String> getMeaningObservableList(int meaning){
        ObservableList<String> meanings = FXCollections.observableArrayList();
        meanings.add("Oplever ikke begransing");
        meanings.add("Oplever begransing");
        return meanings;
    }

    public static int parseMeaningToInt(String meaning){
        int integer = 0;
        return switch (meaning) {
            case "Oplever ikke begransing" -> 1;
            case "Oplever begransing" -> 2;
            default -> integer;
        };
    }
}
