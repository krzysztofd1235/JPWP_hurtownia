package converters;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import klasy.*;

import java.util.List;
import java.util.Map;

public class ProductView {

    private String name;
    private String quantity;
    private String place;
    private String sellPrice;
    private String buyPrice;
    private String barcode;

    private String description;

    private TaxRate tax;
    private Unit unit;
    private Category category;
    private List<Category> listCategory = FXCollections.observableArrayList();
    private int id;
    private ObservableList<Stock> stockList = FXCollections.observableArrayList();
    private String sellPriceSee;
    private String buyPriceSee;

        ///sell.setText(new StringBuffer(String.valueOf(Integer.parseInt(productView.getSellPrice()))).insert(String.valueOf(Integer.parseInt(productView.getSellPrice())).length()-2,".").toString()+ " PLN");


    public ProductView(Product product, Stock stock, Place place, Category category, TaxRate taxRate, Unit unit, Map categoryMap,int id) {
        this.name = product.getName();
        this.quantity = String.valueOf(stock.getQuantity());
        this.place = place.getName();
        this.sellPrice = String.valueOf(product.getSell_price());
        this.buyPrice = String.valueOf(product.getBuy_price());
        this.barcode = product.getBarcode();
        this.tax = taxRate;
        this.unit = unit;
        this.description = product.getDescription();
        this.category = category;

        this.listCategory.add(category);
        int parentID = category.getParent_id();
        while (true){
            if(parentID != 0){
                Category parentParent = (Category) categoryMap.get(parentID);
                this.listCategory.add(parentParent);
                parentID = parentParent.getParent_id();
            }else if(parentID == 0){
                break;
            }
        }
        this.id = id;
        try{
            sellPriceSee = (new StringBuffer(String.valueOf(product.getSell_price())).insert(String.valueOf(product.getSell_price()).length()-2,".").toString()+ " PLN");

        }catch (StringIndexOutOfBoundsException e){
           sellPriceSee = "0."+ product.getSell_price() +" PLN";
        }
        try{
            buyPriceSee = (new StringBuffer(String.valueOf(product.getBuy_price())).insert(String.valueOf(product.getBuy_price()).length()-2,".").toString()+" PLN");

        }catch (StringIndexOutOfBoundsException e){
            buyPriceSee = "0."+ product.getBuy_price() +" PLN";
        }

    }

    public String getSellPriceSee() {
        return sellPriceSee;
    }


    public void setSellPriceSee(String sellPriceSee) {
        String sellPriceSee1;
        try{
            sellPriceSee1 = (new StringBuffer(sellPriceSee).insert(String.valueOf(sellPriceSee).length()-2,"").toString()+ " PLN");

        }catch (StringIndexOutOfBoundsException e){
            sellPriceSee1 = "0."+ sellPriceSee +" PLN";
        }
        this.sellPriceSee = sellPriceSee1;
    }

    public String getBuyPriceSee() {
        return buyPriceSee;
    }

    public void setBuyPriceSee(String buyPriceSee) {
            String buyPriceSee1;
        try{
            buyPriceSee1 = (new StringBuffer(buyPriceSee).insert(String.valueOf(buyPriceSee).length()-2,"").toString()+" PLN");

        }catch (StringIndexOutOfBoundsException e){
            buyPriceSee1 = "0."+ buyPriceSee +" PLN";
        }
        this.buyPriceSee = buyPriceSee1;
    }

    public ObservableList<Stock> getStockList() {
        return stockList;
    }

    public void setStockList(ObservableList<Stock> stockList) {
        this.stockList = stockList;
    }
    public void addStockList(Stock stock){
        stockList.add(stock);
    }
    public void removeStockList(Stock stock){
        stockList.remove(stock);
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List getListCategory() {
        return listCategory;
    }

    public void setListCategory(List listCategory) {
        this.listCategory = listCategory;
    }


    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return name + '\'' +
                ", quantity='" + quantity + '\'' +
                ", place='" + place + '\'';
    }

    public TaxRate getTax() {
        return tax;
    }

    public void setTax(TaxRate tax) {
        this.tax = tax;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(String sellPrice) {
        this.sellPrice = sellPrice;
    }

    public String getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(String buyPrice) {
        this.buyPrice = buyPrice;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }
}
