package BE;

import java.sql.Date;

public class StudentQuestionnaire {
    private int id;
    private java.sql.Date Date;
    private int SickPatientId;

    public StudentQuestionnaire(int id, java.sql.Date date, int sickPatientId) {
        this.id = id;
        Date = date;
        SickPatientId = sickPatientId;
    }

    public StudentQuestionnaire() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public java.sql.Date getDate() {
        return Date;
    }

    public void setDate(java.sql.Date date) {
        Date = date;
    }

    public int getSickPatientId() {
        return SickPatientId;
    }

    public void setSickPatientId(int sickPatientId) {
        SickPatientId = sickPatientId;
    }
}
