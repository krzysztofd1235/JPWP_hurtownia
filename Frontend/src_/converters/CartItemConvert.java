package converters;

public class CartItemConvert {
    private String product;
    private String stock;
    private String quantity;
    private String price;
    private String order;


    public CartItemConvert(String product, String stock, String quantity, String price, String order) {
        this.product = product;
        this.stock = stock;
        this.quantity = quantity;
        this.price = price;
        this.order = order;
    }

    public CartItemConvert() {
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }
}
