package BE;

public class User {
    private int id;
    private int schoolID;
    private String name;
    private String email;
    private String userType;

    public User(int id,int schoolID, String name, String email, String userType) {
        this.id = id;
        this.schoolID = schoolID;
        this.name = name;
        this.email = email;
        this.userType = userType;
    }

    public User(int schoolID, String name, String email, String userType) {
        this.schoolID = schoolID;
        this.name = name;
        this.email = email;
        this.userType = userType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSchoolID() {
        return schoolID;
    }

    public void setSchoolID(int schoolID) {
        this.schoolID = schoolID;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", userType='" + userType + '\'' +
                '}';
    }
}
