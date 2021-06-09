package klasy;

import java.util.ArrayList;

public class Client {

    private String company;
    private String f_name;
    private String s_name;
    private String nip;
    private String email;
    private int id;
    private ArrayList<Order> orderList = new ArrayList<Order>();



    public Client(String company, String f_name, String s_name, String nip, String email, int id) {
        this.company = company;
        this.f_name = f_name;
        this.s_name = s_name;
        this.nip = nip;
        this.email = email;
        this.id = id;
    }

    public Client(String company, String f_name, String s_name, String nip, String email) {
        this.company = company;
        this.f_name = f_name;
        this.s_name = s_name;
        this.nip = nip;
        this.email = email;
    }

    public Client() {
    }

    public void addOrder(Order order){
        orderList.add(order);
    }


    public ArrayList<Order> getOrderList() {
        return orderList;
    }

    public void setOrderList(ArrayList<Order> orderList) {
        this.orderList = orderList;
    }


    @Override
    public String toString() {
        return  f_name;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getF_name() {
        return f_name;
    }

    public void setF_name(String f_name) {
        this.f_name = f_name;
    }

    public String getS_name() {
        return s_name;
    }

    public void setS_name(String s_name) {
        this.s_name = s_name;
    }

    public String getNip() {
        return nip;
    }

    public void setNip(String nip) {
        this.nip = nip;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
