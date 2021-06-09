package converters;

public class AddProductJson {

    private String name;
    private String category_id;
    private String description;
    private int sell_price;
    private int buy_price;
    private String tax_id;
    private String unit_id;
    private String barcode;
    private String id;

    public AddProductJson(String name, String category_id, String description, int sell_price, int buy_price, String tax_id, String unit_id, String barcode) {
        this.name = name;
        this.category_id = category_id;
        this.description = description;
        this.sell_price = sell_price;
        this.buy_price = buy_price;
        this.tax_id = tax_id;
        this.unit_id = unit_id;
        this.barcode = barcode;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
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

    public int getBuy_price() {
        return buy_price;
    }

    public void setBuy_price(int buy_price) {
        this.buy_price = buy_price;
    }

    public String getTax_id() {
        return tax_id;
    }

    public void setTax_id(String tax_id) {
        this.tax_id = tax_id;
    }

    public String getUnit_id() {
        return unit_id;
    }

    public void setUnit_id(String unit_id) {
        this.unit_id = unit_id;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }
}
