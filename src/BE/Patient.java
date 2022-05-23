package BE;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Patient {
    private int id;
    private String first_name;
    private String last_name;
    private LocalDate dateOfBirth;
    private String gender;
    private String weight;
    private String height;
    private String cpr;
    private String phoneNumber;
    private ArrayList<String> observationsList;
    private int schoolId;
    private boolean isCopy;

    public Patient(String first_name, String last_name, LocalDate dateOfBirth, String gender, String weight,
                   String height, String cpr, String phoneNumber, ArrayList<String> observationsList, int schoolId) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.weight = weight;
        this.height = height;
        this.cpr = cpr;
        this.phoneNumber = phoneNumber;
        this.observationsList = observationsList;
        this.schoolId = schoolId;
    }

    public Patient(int id, String first_name, String last_name, LocalDate dateOfBirth, String gender,
                   String weight, String height, String cpr, String phoneNumber,
                   ArrayList<String> observationsList, int schoolId) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.weight = weight;
        this.height = height;
        this.cpr = cpr;
        this.phoneNumber = phoneNumber;
        this.observationsList = observationsList;
        this.schoolId = schoolId;
    }

    public Patient(int id, String first_name, String last_name, LocalDate dateOfBirth, String gender,
                   String weight, String height, String cpr, String phoneNumber, ArrayList<String> observationsList,
                   int schoolId, boolean isCopy) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.weight = weight;
        this.height = height;
        this.cpr = cpr;
        this.phoneNumber = phoneNumber;
        this.observationsList = observationsList;
        this.schoolId = schoolId;
        this.isCopy = isCopy;
    }

    public Patient(String first_name, String last_name, LocalDate dateOfBirth, String gender, String weight,
                   String height, String cpr, String phoneNumber, ArrayList<String> observationsList, int schoolId,
                   boolean isCopy) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.weight = weight;
        this.height = height;
        this.cpr = cpr;
        this.phoneNumber = phoneNumber;
        this.observationsList = observationsList;
        this.schoolId = schoolId;
        this.isCopy = isCopy;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getCpr() {
        return cpr;
    }

    public void setCpr(String cpr) {
        this.cpr = cpr;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(int schoolId) {
        this.schoolId = schoolId;
    }

    public ArrayList<String> getObservationsList() {
        return observationsList;
    }

    public void setObservationsList(ArrayList<String> observationsList) {
        this.observationsList = observationsList;
    }

    public boolean getIsCopy() {
        return isCopy;
    }
    public int getIsCopyDB(){
        if(!this.isCopy){
            return 0;
        }else return 1;
    }

    public void setIsCopyDB(int bol) {
        this.isCopy = bol != 0;
    }

    public void setCopy(boolean copy) {
        isCopy = copy;
    }

    public void addObservation(String text) {
        this.observationsList.add(text);
    }
}
