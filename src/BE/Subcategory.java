package BE;

public class Subcategory {

    private int id;
    private int name;
    private boolean assessed;

    public Subcategory(int id, int name, boolean assessed) {
        this.id = id;
        this.name = name;
        this.assessed = assessed;
    }

    public Subcategory(int id, int name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getName() {
        return name;
    }

    public void setName(int name) {
        this.name = name;
    }

    public boolean isAssessed() {
        return assessed;
    }

    public void setAssessed(boolean assessed) {
        this.assessed = assessed;
    }
}
