package klasy;

public class Product  {
    private String name;
    private Integer cat_id;
    private String description;
    private int sell_price;
    private String tax_id;
    private int buy_price;
    private String unit_id;
    private String Barcode;
    private int id;


    public Product(String name, Integer cat_id, String description, int sell_price, String tax_id, int buy_price, String unit_id, String barcode, int id) {
        this.name = name;
        this.cat_id = cat_id;
        this.description = description;
        this.sell_price = sell_price;
        this.tax_id = tax_id;
        this.buy_price = buy_price;
        this.unit_id = unit_id;
        Barcode = barcode;
        this.id = id;
    }

    public Product() {
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", cat_id=" + cat_id +
                ", description='" + description + '\'' +
                ", sell_price=" + sell_price +
                ", tax_id='" + tax_id + '\'' +
                ", buy_price=" + buy_price +
                ", unit_id='" + unit_id + '\'' +
                ", Barcode='" + Barcode + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCat_id() {
        return cat_id;
    }

    public void setCat_id(Integer cat_id) {
        this.cat_id = cat_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getSell_price() {
        return sell_price;
    }

    public void setSell_price(int sell_price) {
        this.sell_price = sell_price;
    }

    public String getTax_id() {
        return tax_id;
    }

    public void setTax_id(String tax_id) {
        this.tax_id = tax_id;
    }

    public int getBuy_price() {
        return buy_price;
    }

    public void setBuy_price(int buy_price) {
        this.buy_price = buy_price;
    }

    public String getUnit_id() {
        return unit_id;
    }

    public void setUnit_id(String unit_id) {
        this.unit_id = unit_id;
    }

    public String getBarcode() {
        return Barcode;
    }

    public void setBarcode(String barcode) {
        Barcode = barcode;
    }
}
