package converters;

public class AddCategoryJson {
    private String name;
    private String description;
    private String parent_id;
    private String Id;

    public AddCategoryJson(String name, String description, String parent_id) {
        this.name = name;
        this.description = description;
        this.parent_id = parent_id;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public AddCategoryJson() {
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getParent_id() {
        return parent_id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setParent_id(String parent_id) {
        this.parent_id = parent_id;
    }
}
