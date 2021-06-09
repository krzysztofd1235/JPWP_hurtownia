package klasy;

public class TaxRate {
    private int percent;
    private String name;
    private int id;

    public TaxRate(int percent, String name, int id) {
        this.percent = percent;
        this.name = name;
        this.id = id;
    }

    @Override
    public String toString() {
        return  name;

    }

    public TaxRate() {
    }

    public void setPercent(int percent) {
        this.percent = percent;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPercent() {
        return percent;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }
}
