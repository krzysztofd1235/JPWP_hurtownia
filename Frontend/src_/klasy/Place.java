package klasy;

public class Place {
    private String name;
    private String Description;
    private int id;

    public Place(String name, String description, int id) {
        this.name = name;
        Description = description;
        this.id = id;
    }

    public Place() {
    }

    @Override
    public String toString() {
        return  name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

