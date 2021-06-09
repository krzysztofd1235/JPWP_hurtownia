package controller;

import conection.DbConnection;
import converters.ProductView;
import converters.StockToTableView;
import holder.ProductViewHolder;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import klasy.Place;
import klasy.Stock;

import java.util.List;
import java.util.Map;


public class ProductModyfiController {

    @FXML
    private Button modyfiButton;
    @FXML
    private Button closeButton;
    @FXML
    private TextArea descriptionTextArea;
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField sellPriceTextField;
    @FXML
    private TextField buyPriceTextField;
    @FXML
    private TextField barcodeTextField;
    @FXML
    private Label sellPriceError;
    @FXML
    private Label buyPriceError;
    @FXML
    private Label barcodeError;
    @FXML
    private Label choseplaceLabel;
    @FXML
    private Button quantityModyfiBitton;
    @FXML
    private TextField quantityTextField;
    @FXML
    private TableColumn stockColumn;
    @FXML
    private TableView stockTableView;

    private MainController mainController;
    private DbConnection dbConnection;
    ProductView productView;
    public void initialize(){
        dbConnection = new DbConnection();
        ProductViewHolder holder = ProductViewHolder.getINSTANCE();
        productView= holder.getProductView();

        nameTextField.setText(productView.getName());


        sellPriceTextField.setText(productView.getSellPrice());
        buyPriceTextField.setText(productView.getBuyPrice());

        try{
           sellPriceTextField.setText(new StringBuffer(String.valueOf(productView.getSellPrice())).insert(String.valueOf(productView.getSellPrice()).length()-2,".").toString());

        }catch (StringIndexOutOfBoundsException e){
           String sellPriceSee = "0."+ productView.getSellPrice();
            sellPriceTextField.setText(sellPriceSee);
        }
        try{
            buyPriceTextField.setText(new StringBuffer(String.valueOf(productView.getBuyPrice())).insert(String.valueOf(productView.getBuyPrice()).length()-2,".").toString());

        }catch (StringIndexOutOfBoundsException e){
           String buyPriceSee = "0."+ productView.getSellPrice();
           buyPriceTextField.setText(buyPriceSee);
        }

        barcodeTextField.setText(productView.getBarcode());
        descriptionTextArea.setText(productView.getDescription());
//        quantityTextField.setText(productView.getQuantity());
        quantityTextField.setDisable(true);
        ObservableList<StockToTableView> stockToTableViewsList = FXCollections.observableArrayList();
        Map placeMap = dbConnection.getPlaceMap();


        for (Stock stock:
                productView.getStockList()) {
            Place place = (Place) placeMap.get(Integer.parseInt(stock.getPlace_id()));
            StockToTableView stockToTableView = new StockToTableView(place.getName());
            stockToTableViewsList.add(stockToTableView);

        }
        stockTableView.setItems(stockToTableViewsList);
        stockColumn.setCellValueFactory(new PropertyValueFactory<>("place"));
    }


    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }


    @FXML
    public void closeAction(){

        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
    @FXML
    public void modifyAction(){
        ObservableList<ProductView> prodouctListView  = mainController.getProdouctListView();
        int buyPrice;
        int sellPrice = 0;
        boolean numberError = false;

        try {
            buyPrice = (int)((Float.parseFloat(buyPriceTextField.getText())*100));


            buyPriceError.setText("");
        } catch (NumberFormatException e) {
            buyPriceError.setText("Invalid value.");
            numberError = true;
            System.out.println("1");
        }
        try {

            sellPrice =(int)((Float.parseFloat(sellPriceTextField.getText())*100));
            sellPriceError.setText("");
        } catch (NumberFormatException e) {
            sellPriceError.setText("Invalid value.");
            numberError = true;
            System.out.println("2");
            System.out.println(sellPrice);
        }
        for (ProductView pr :
                prodouctListView) {
            if(barcodeTextField.getText().equals(productView.getBarcode())){


            }else {
                if (barcodeTextField.getText().equals(pr.getBarcode())) {
                    barcodeError.setText("Bad barcode");
                    numberError = true;
                    break;
                } else {

                    barcodeError.setText("");
                }
            }
        }
        System.out.println(numberError);
        if(numberError)return;


        productView.setName(nameTextField.getText());
        productView.setSellPrice(sellPriceTextField.getText());
        productView.setBarcode(barcodeTextField.getText());
        productView.setBuyPrice(buyPriceTextField.getText());
        productView.setDescription(descriptionTextArea.getText());


        for (ProductView pr:
             prodouctListView) {
            if(pr == productView){
                pr.setName(nameTextField.getText());
                pr.setSellPriceSee(sellPriceTextField.getText());
                pr.setBuyPriceSee(buyPriceTextField.getText());
                pr.setBarcode(barcodeTextField.getText());
                pr.setDescription(descriptionTextArea.getText());
            }

        }
        System.out.println(prodouctListView);
        mainController.viewRefresh(prodouctListView);

        dbConnection.modifyProduct(nameTextField.getText(),String.valueOf(productView.getCategory().getId()),descriptionTextArea.getText(),(int)((Float.parseFloat(sellPriceTextField.getText())*100)),
                (int)((Float.parseFloat(buyPriceTextField.getText())*100)),String.valueOf(productView.getTax().getId()),String.valueOf(productView.getUnit().getId()),barcodeTextField.getText(),
                String.valueOf(productView.getId()));
        Stage stage = (Stage) modyfiButton.getScene().getWindow();
        stage.close();

    }
    @FXML
    public void madyfiQuantytAction(){

        String quantity = quantityTextField.getText();

        try {
            StockToTableView stockToTableView = (StockToTableView) stockTableView.getSelectionModel().getSelectedItem();
            List<Stock> stockList = dbConnection.getStockList();
            Map placeMap = dbConnection.getPlaceMap();
            for (Stock s :
                    stockList) {
                Place place = (Place) placeMap.get(Integer.parseInt(s.getPlace_id()));

                if(stockToTableView.getPlace().equals(place.getName())){
                    dbConnection.changeStock(String.valueOf(s.getId()), quantity);
                    ProductViewHolder holder = ProductViewHolder.getINSTANCE();
                    ProductView productView = holder.getProductView();
                    productView.setQuantity(quantity);
                    holder.setProductView(productView);

                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }





    }
    @FXML
    public void chosePlaceTableView(){
        quantityTextField.setDisable(false);
        try {
            StockToTableView stockToTableView = (StockToTableView) stockTableView.getSelectionModel().getSelectedItem();
            List<Stock> stockList = dbConnection.getStockList();
            Map placeMap = dbConnection.getPlaceMap();
            for (Stock s :
                    stockList) {
                Place place = (Place) placeMap.get(Integer.parseInt(s.getPlace_id()));

                if(stockToTableView.getPlace().equals(place.getName())){

                    choseplaceLabel.setText(place.getName());
                    quantityTextField.setText(String.valueOf(s.getQuantity()));
                }
            }
        } catch (Exception e) {

        }


    }

}
