package BE;

public class Subcategory {

    private int id;
    private String name;
    private boolean assessed;

    public Subcategory(int id, String name, boolean assessed) {
        this.id = id;
        this.name = name;
        this.assessed = assessed;
    }

    public Subcategory(int id, String name) {
        this.id = id;
        this.name = name;
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

    public boolean isAssessed() {
        return assessed;
    }

    public void setAssessed(boolean assessed) {
        this.assessed = assessed;
    }
}
