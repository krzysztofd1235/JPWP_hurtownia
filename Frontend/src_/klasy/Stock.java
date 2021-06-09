package klasy;

public class Stock  {
    private String product_id;
    private String place_id;
    private int quantity;
    private int id;

    public Stock(String product_id, String place_id, int quantity, int id) {
        this.product_id = product_id;
        this.place_id = place_id;
        this.quantity = quantity;
        this.id = id;
    }

    public Stock() {
    }

    @Override
    public String toString() {
        return "Stock{" +
                "product_id='" + product_id + '\'' +
                ", place_id='" + place_id + '\'' +
                ", quantity=" + quantity +
                ", id=" + id +
                '}';
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getPlace_id() {
        return place_id;
    }

    public void setPlace_id(String place_id) {
        this.place_id = place_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
