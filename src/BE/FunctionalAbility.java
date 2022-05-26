package BE;

public class FunctionalAbility {

    private int id;
    private int categoryID;
    private int subcategoryID;
    private int currentStatus;
    private int expectedStatus;
    private int limitation;
    private String goal;
    private String professionalNote;
    private int isEditing;

    public FunctionalAbility(int categoryID, int subcategoryID,
                             int currentStatus, int expectedStatus, int limitation, String goal, String professionalNote) {
        this.categoryID = categoryID;
        this.subcategoryID = subcategoryID;
        this.currentStatus = currentStatus;
        this.expectedStatus = expectedStatus;
        this.limitation = limitation;
        this.goal = goal;
        this.professionalNote = professionalNote;
    }

    public FunctionalAbility(int categoryID, int subcategoryID, int currentStatus, int expectedStatus,
                             int limitation, String goal, String professionalNote, int isEditing) {
        this.categoryID = categoryID;
        this.subcategoryID = subcategoryID;
        this.currentStatus = currentStatus;
        this.expectedStatus = expectedStatus;
        this.limitation = limitation;
        this.goal = goal;
        this.professionalNote = professionalNote;
        this.isEditing = isEditing;
    }

    public FunctionalAbility(int id, int categoryID, int subcategoryID, int currentStatus, int expectedStatus,
                             int limitation, String goal, String professionalNote, int isEditing) {
        this.id = id;
        this.categoryID = categoryID;
        this.subcategoryID = subcategoryID;
        this.currentStatus = currentStatus;
        this.expectedStatus = expectedStatus;
        this.limitation = limitation;
        this.goal = goal;
        this.professionalNote = professionalNote;
        this.isEditing = isEditing;
    }
}
