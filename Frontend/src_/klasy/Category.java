package klasy;

public class Category {
    private String name;
    private String description;
    private int parent_id;
    private int id;

    public Category(String name, String description, int parent_id, int id) {
        this.name = name;
        this.description = description;
        this.parent_id = parent_id;
        this.id = id;
    }

    public Category() {
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;

    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getParent_id() {
        return parent_id;
    }

    public void setParent_id(int parent_id) {
        this.parent_id = parent_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

