package klasy;

import java.util.ArrayList;

public class Order {
    private String client;
    private String code;
    private String id;
    private String status;
    private String etykietaStatus;
    private ArrayList<CartItem> cartItemsList = new ArrayList<CartItem>();
    private CartItem cartItem;
    public Order() {

    }

    public Order(String client, String code, String id, String status) {
        this.client = client;
        this.code = code;
        this.id = id;
        this.status = status;


        if (status.equals("1")) {
            etykietaStatus = "W trakcie";
        } else if (status.equals("2")) {
            etykietaStatus = "Spakowano";
        } else if (status.equals("3")) {
            etykietaStatus = "Wysłano";
        } else if (status.equals("4")) {
            etykietaStatus = "Zrealizowano";
        } else if (status.equals("5")) {
            etykietaStatus = "Owołano";
        }
        if (status.equals("0")) {
            etykietaStatus = "Stworzono";
        }

    }

    public void setStatuEtykiet() {
        if (status.equals("1")) {
            etykietaStatus = "W trakcie";
        } else if (status.equals("2")) {
            etykietaStatus = "Spakowano";
        } else if (status.equals("3")) {
            etykietaStatus = "Wysłano";
        } else if (status.equals("4")) {
            etykietaStatus = "Zrealizowano";
        } else if (status.equals("5")) {
            etykietaStatus = "Owołano";
        }
        if (status.equals("0")) {
            etykietaStatus = "Stworzono";
        }

    }

    @Override
    public String toString() {
        return "Order{" +
                "code='" + code + '\'' +
                '}';
    }

    public String getClient() {
        return client;
    }
    public void addCartItem(CartItem cartItem){
        cartItemsList.add(cartItem);
        this.cartItem = cartItem;

    }

    public String getEtykietaStatus() {
        return etykietaStatus;
    }

    public void setEtykietaStatus(String etykietaStatus) {
        this.etykietaStatus = etykietaStatus;
    }

    public CartItem getCartItem() {
        return cartItem;
    }

    public void setCartItem(CartItem cartItem) {
        this.cartItem = cartItem;
    }

    public ArrayList<CartItem> getCartItemsList() {
        return cartItemsList;
    }

    public void setCartItemsList(ArrayList<CartItem> cartItemsList) {
        this.cartItemsList = cartItemsList;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
