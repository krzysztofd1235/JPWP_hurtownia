package controller;

import conection.DbConnection;
import converters.ProductView;
import converters.StockToTableView;
import holder.ProductViewHolder;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import klasy.Place;
import klasy.Stock;

import java.util.List;
import java.util.Map;


public class InformacjeController {



    @FXML
    private Label productName;
    @FXML
    private Label sell;
    @FXML
    private Label buy;
    @FXML
    private Label tax;

    @FXML
    private Label quantity;
    @FXML
    private Label place;
    @FXML
    private Label barcode;
    @FXML
    private Label description;
    @FXML
    private Button close;
    @FXML
    private TableColumn stockColumn;
    @FXML
    private TableView stockTableView;

    private ProductView productView;
    private DbConnection dbConnection;

    public void initialize(){
        ProductViewHolder holder = ProductViewHolder.getINSTANCE();
        productView = holder.getProductView();
        productName.setText(productView.getName());
        sell.setText(productView.getSellPriceSee());
        buy.setText(productView.getBuyPriceSee());
        tax.setText(String.valueOf(productView.getTax().getPercent()/100) + '%');

        quantity.setText(productView.getQuantity()+productView.getUnit().getShortcut());
        place.setText(productView.getPlace());
        barcode.setText(productView.getBarcode());
        description.setWrapText(true);
        description.setText(productView.getDescription());

        dbConnection = new DbConnection();
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

    @FXML
    public void closeAction(){

        Stage stage = (Stage) close.getScene().getWindow();
        stage.close();
    }

    public void chosePlaceTableView(){
        try {
            StockToTableView stockToTableView = (StockToTableView) stockTableView.getSelectionModel().getSelectedItem();
            List<Stock> stockList = dbConnection.getStockList();
            Map placeMap = dbConnection.getPlaceMap();
            for (Stock s :
                    stockList) {
                Place place = (Place) placeMap.get(Integer.parseInt(s.getPlace_id()));

                if(stockToTableView.getPlace().equals(place.getName())){

                    quantity.setText(s.getQuantity()+productView.getUnit().getShortcut());
                    this.place.setText(place.getName());
                }
            }
        } catch (Exception e) {

        }


    }

}
