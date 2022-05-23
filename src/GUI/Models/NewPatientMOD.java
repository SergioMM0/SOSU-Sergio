package GUI.Models;

import BE.Patient;
import BLL.BLLFacade;
import BLL.BLLManager;
import DAL.util.DalException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class NewPatientMOD {

    private BLLFacade bllFacade;

    public NewPatientMOD(){
        bllFacade = new BLLManager();
    }

    public Patient createPatient(Patient patient)throws DalException {
        return bllFacade.createPatient(patient);
    }

    //Combo boxes Hardcoded until we implement danish and english lang, so we will read from a file instead

    public ObservableList<String> getGenders() {
        ObservableList<String> genders = FXCollections.observableArrayList();
        genders.add("Male");
        genders.add("Female");
        genders.add("Lockheed Martin F-16 Fighting Falcon lol");
        return genders;
    }

    public ObservableList<String> getBloodTypes() {
        ObservableList<String> bloodTypes = FXCollections.observableArrayList();
        bloodTypes.add("A");
        bloodTypes.add("B");
        bloodTypes.add("AB");
        bloodTypes.add("0");
        return bloodTypes;
    }

    public ObservableList<String> getExerciseOptions() {
        ObservableList<String> exercise = FXCollections.observableArrayList();
        exercise.add("+5 days a week");
        exercise.add("2-3 days a week");
        exercise.add("1 day a week");
        exercise.add("I'm a lazy a$$ ****");
        return exercise;
    }

    public ObservableList<String> getDietOptions() {
        ObservableList<String> diet = FXCollections.observableArrayList();
        diet.add("Lose diet");
        diet.add("Strict diet");
        diet.add("Doesn't have a diet plan");
        return diet;
    }

    public ObservableList<String> getAlcoholOptions() {
        ObservableList<String> alcohol = FXCollections.observableArrayList();
        alcohol.add("Daily");
        alcohol.add("Weekends");
        alcohol.add("Sporadic");
        alcohol.add("Never");
        return alcohol;
    }

    public ObservableList<String> getTobaccoOptions() {
        ObservableList<String> tobacco = FXCollections.observableArrayList();
        tobacco.add("SnoopDog style");
        tobacco.add("10-20 cigarettes a day");
        tobacco.add("0-10 cigarettes a day");
        tobacco.add("Social smoker");
        tobacco.add("I got a ferrari bcs I don't smoke");
        return tobacco;
    }
}
