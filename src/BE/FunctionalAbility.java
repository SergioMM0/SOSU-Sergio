package BE;

public class FunctionalAbility {

    private int id;
    private int relevancy;
    private int currentLevel;
    private int expectedLevel;
    private int performance;
    private int meaning;
    private String citizenGoal;
    private String professionalNote;
    private boolean isEditing;

    public FunctionalAbility(int relevancy, int currentLevel, int expectedLevel, int performance, int meaning, String citizenGoal, String professionalNote) {
        this.relevancy = relevancy;
        this.currentLevel = currentLevel;
        this.expectedLevel = expectedLevel;
        this.performance = performance;
        this.meaning = meaning;
        this.citizenGoal = citizenGoal;
        this.professionalNote = professionalNote;
    }

    public FunctionalAbility(int id, int relevancy, int currentLevel, int expectedLevel, int performance, int meaning, String citizenGoal, String professionalNote, boolean isEditing) {
        this.id = id;
        this.relevancy = relevancy;
        this.currentLevel = currentLevel;
        this.expectedLevel = expectedLevel;
        this.performance = performance;
        this.meaning = meaning;
        this.citizenGoal = citizenGoal;
        this.professionalNote = professionalNote;
        this.isEditing = isEditing;
    }

    public FunctionalAbility(boolean isEditing) {
        this.isEditing = isEditing;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRelevancy() {
        return relevancy;
    }

    public void setRelevancy(int relevancy) {
        this.relevancy = relevancy;
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(int currentLevel) {
        this.currentLevel = currentLevel;
    }

    public int getExpectedLevel() {
        return expectedLevel;
    }

    public void setExpectedLevel(int expectedLevel) {
        this.expectedLevel = expectedLevel;
    }

    public int getPerformance() {
        return performance;
    }

    public void setPerformance(int performance) {
        this.performance = performance;
    }

    public int getMeaning() {
        return meaning;
    }

    public void setMeaning(int meaning) {
        this.meaning = meaning;
    }

    public String getCitizenGoal() {
        return citizenGoal;
    }

    public void setCitizenGoal(String citizenGoal) {
        this.citizenGoal = citizenGoal;
    }

    public String getProfessionalNote() {
        return professionalNote;
    }

    public void setProfessionalNote(String professionalNote) {
        this.professionalNote = professionalNote;
    }

    public boolean isEditing() {
        return isEditing;
    }

    public void setEditing(boolean editing) {
        isEditing = editing;
    }
}
