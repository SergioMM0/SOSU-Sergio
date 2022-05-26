package BE;

public class HealthCondition {

    private int id;
    private int categoryID;
    private int subcategoryID;
    private int status;
    private int expectedScore;
    private String assessment;
    private String goal;
    private String professionalNote;
    private int isEditing;

    public HealthCondition(int categoryID, int subcategoryID, int status, int expectedScore,
                           String assessment, String goal, String professionalNote) {
        this.categoryID = categoryID;
        this.subcategoryID = subcategoryID;
        this.status = status;
        this.expectedScore = expectedScore;
        this.assessment = assessment;
        this.goal = goal;
        this.professionalNote = professionalNote;
    }

    public HealthCondition(int categoryID, int subcategoryID, int status, int expectedScore,
                           String assessment, String goal, String professionalNote, int isEditing) {
        this.categoryID = categoryID;
        this.subcategoryID = subcategoryID;
        this.status = status;
        this.expectedScore = expectedScore;
        this.assessment = assessment;
        this.goal = goal;
        this.professionalNote = professionalNote;
        this.isEditing = isEditing;
    }

    public HealthCondition(int id, int categoryID, int subcategoryID, int status, int expectedScore,
                           String assessment, String goal, String professionalNote, int isEditing) {
        this.id = id;
        this.categoryID = categoryID;
        this.subcategoryID = subcategoryID;
        this.status = status;
        this.expectedScore = expectedScore;
        this.assessment = assessment;
        this.goal = goal;
        this.professionalNote = professionalNote;
        this.isEditing = isEditing;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public int getSubcategoryID() {
        return subcategoryID;
    }

    public void setSubcategoryID(int subcategoryID) {
        this.subcategoryID = subcategoryID;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getExpectedScore() {
        return expectedScore;
    }

    public void setExpectedScore(int expectedScore) {
        this.expectedScore = expectedScore;
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

    public String getProfessionalNote() {
        return professionalNote;
    }

    public void setProfessionalNote(String professionalNote) {
        this.professionalNote = professionalNote;
    }

    public int getIsEditing() {
        return isEditing;
    }

    public void setIsEditing(int isEditing) {
        this.isEditing = isEditing;
    }
}
