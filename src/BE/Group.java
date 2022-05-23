package BE;

import java.util.List;

public class Group {

    private int id;
    private String name;
    private List<User> members;
    private int schoolId;

    public Group(int id, String name, List<User> members , int SchoolID) {
        this.id = id;
        this.name = name;
        this.members = members;
        this.schoolId = SchoolID ;
    }

    public Group(String name, List<User> members, int schoolID) {
        this.name = name;
        this.members = members;
        schoolId = schoolID;
    }

    public Group() {
    }

    public Group(int id, String name, int schoolid) {
        this.id = id;
        this.name = name;
        this.schoolId = schoolid;
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

    public List<User> getMembers() {
        return members;
    }

    public void setMembers(List<User> members) {
        this.members = members;
    }

    public int getParticipants(){
        return this.members.size();
    }

    public int getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(int schoolId) {
        this.schoolId = schoolId;
    }

    public Group addMember(User user){
        this.members.add(user);
        return this;
    }

    public boolean containsMember(User user){
        for(User student : this.members){
            if(student.getId() == user.getId()){
                return true;
            }
        }
        return false;
    }

    public void removeMember(User user){
        this.members.remove(user);
    }

    @Override
    public String toString() {
        return "Group{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", members=" + members +
                ", SchoolID=" + schoolId +
                '}';
    }
}

