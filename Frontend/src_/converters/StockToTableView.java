package converters;

public class StockToTableView {
    private String place;

    public StockToTableView(String place) {
        this.place = place;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    @Override
    public String toString() {
        return "StockToTableView{" +
                "place='" + place + '\'' +
                '}';
    }
}
