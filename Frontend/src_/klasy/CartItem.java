package klasy;

public class CartItem {

    private String id;
    private String order;
    private String price;
    private String product;
    private String quantity;

    public CartItem(String id, String order, String price, String product, String quantity) {
        this.id = id;
        this.order = order;
        this.price = price;
        this.product = product;
        this.quantity = quantity;
    }

    public CartItem() {
    }

    @Override
    public String toString() {
        return "CartItem{" +
                "id='" + id + '\'' +
                ", order='" + order + '\'' +
                ", price='" + price + '\'' +
                ", product='" + product + '\'' +
                ", quantity='" + quantity + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
