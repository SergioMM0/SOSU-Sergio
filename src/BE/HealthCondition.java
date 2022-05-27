package BE;

public class HealthCondition {

    private int id;
    private int relevancy;
    private String assessment;
    private String goal;
    private int expectations;
    private String professionalNote;
    private boolean isEditing;

    public HealthCondition(int relevancy, String assessment, String goal, int expectations, String professionalNote) {
        this.relevancy = relevancy;
        this.assessment = assessment;
        this.goal = goal;
        this.expectations = expectations;
        this.professionalNote = professionalNote;
    }

    public HealthCondition(int id, int relevancy, String assessment, String goal, int expectations, String professionalNote, boolean isEditing) {
        this.id = id;
        this.relevancy = relevancy;
        this.assessment = assessment;
        this.goal = goal;
        this.expectations = expectations;
        this.professionalNote = professionalNote;
        this.isEditing = isEditing;
    }

    public HealthCondition(boolean isEditing){
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

    public String getAssessment() {
        return assessment;
    }

    public void setAssessment(String assessment) {
        this.assessment = assessment;
    }

    public String getGoal() {
        return goal;
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }

    public int getExpectations() {
        return expectations;
    }

    public void setExpectations(int expectations) {
        this.expectations = expectations;
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
