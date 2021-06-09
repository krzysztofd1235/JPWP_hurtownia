package converters;

public class ChangeStock {


    private String id;
    private String quantity;

    public ChangeStock(String id, String quantity) {
        this.id = id;
        this.quantity = quantity;
    }

    public ChangeStock() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
