package klasy;

public class Unit {
    private String name;
    private String shortcut;
    private int divider;
    private int id;

    public Unit(String name, String shortcut, int divider, int id) {
        this.name = name;
        this.shortcut = shortcut;
        this.divider = divider;
        this.id = id;
    }

    public Unit() {
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

    public String getShortcut() {
        return shortcut;
    }

    public void setShortcut(String shortcut) {
        this.shortcut = shortcut;
    }

    public int getDivider() {
        return divider;
    }

    public void setDivider(int divider) {
        this.divider = divider;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}


