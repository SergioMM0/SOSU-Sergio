package BE;

public class Case {

    private int id ;
    private String name ;
    private String conditionDescription;
    private int schoolID;
    private boolean isCopy;

    public Case(int id, String name,String conditionDescription,int schoolID) {
        this.id = id;
        this.name = name;
        this.conditionDescription = conditionDescription;
        this.schoolID = schoolID;
    }

    public Case(String name, String conditionDescription, int schoolID) {
        this.name = name;
        this.conditionDescription = conditionDescription;
        this.schoolID = schoolID;
    }

    public Case(String name, String conditionDescription, int schoolID, boolean isCopy) {
        this.name = name;
        this.conditionDescription = conditionDescription;
        this.schoolID = schoolID;
        this.isCopy = isCopy;
    }

    public Case(int id, String name, String conditionDescription, int schoolID, boolean isCopy) {
        this.id = id;
        this.name = name;
        this.conditionDescription = conditionDescription;
        this.schoolID = schoolID;
        this.isCopy = isCopy;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getConditionDescription() {
        return conditionDescription;
    }

    public void setConditionDescription(String conditionDescription) {
        this.conditionDescription = conditionDescription;
    }

    public int getSchoolID() {
        return schoolID;
    }

    public void setSchoolID(int schoolID) {
        this.schoolID = schoolID;
    }

    public int getIsCopyDB(){
        if(!this.isCopy){
            return 0;
        }else return 1;
    }

    public void setCopy(boolean copy) {
        isCopy = copy;
    }

    public boolean isCopy() {
        return isCopy;
    }
}
