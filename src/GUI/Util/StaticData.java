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

    public static ObservableList<String> getRelevancyObservableList(){
        ObservableList<String> relevancies = FXCollections.observableArrayList();
        relevancies.add("Irrelevant");
        relevancies.add("Potentielt");
        relevancies.add("Akute");
        return relevancies;
    }

    public static String getRelevancy(int rel){
        String string = null;
        return switch (rel){
            case 1 -> "Irrelevant";
            case 2 -> "Potentielt";
            case 3 -> "Akute";
            default -> string;
        };
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

    public static ObservableList<String> getPerformanceObservableList(){
        ObservableList<String> performances = FXCollections.observableArrayList();
        performances.add("Udfører selv");
        performances.add("Udfører dele selv");
        performances.add("Udfører ikke selv");
        performances.add("Ikke relevant");
        return performances;
    }

    public static String getPerformance(int perf){
        String string = null;
        return switch (perf){
            case 1 -> "Udfører selv";
            case 2 -> "Udfører dele selv";
            case 3 -> "Udfører ikke selv";
            case 4 -> "Ikke relevant";
            default -> string;
        };
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

    public static ObservableList<String> getMeaningObservableList(){
        ObservableList<String> meanings = FXCollections.observableArrayList();
        meanings.add("Oplever ikke begransing");
        meanings.add("Oplever begransing");
        return meanings;
    }

    public static String getMeaning(int mean){
        String string = null;
        return switch (mean){
            case 1 -> "Oplever ikke begransing";
            case 2 -> "Oplever begransing";
            default -> string;
        };
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
