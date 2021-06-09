package controller;

import conection.DbConnection;
import converters.ProductView;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import klasy.Client;
import klasy.Place;
import klasy.Stock;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AddOrderController {

    private MainController mainController;
    private OrderController orderController;
    @FXML
    private Button addOrderButton;
    @FXML
    private Button cancelButton;
    @FXML
    private ComboBox productComboBox;
    @FXML
    private ComboBox placeComboBox;
    @FXML
    private TextField quantityTextField;
    @FXML
    private TextField priceTextField;
    @FXML
    private Label priceLabel;
    @FXML
    private Label quantityLabel;


    private Client client;
    private Stock stock;

    private DbConnection dbConnection;
    private ObservableList<ProductView> productViewsList;
    private ListProperty<ProductView> listPropertyProductView;

    ArrayList<Place> listPlaceCombobox = new ArrayList<>();
    private ObservableList<Place> placeObservableListCombobox;
    private ListProperty<Place> listPropertyPlace;

    private List<Stock> stockList;


    private int orderID;

    public void initialize() {
        dbConnection = new DbConnection();


    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void setOrderController(OrderController orderController) {
        this.orderController = orderController;
    }

    public void setView() {
        productViewsList = mainController.getProdouctListView();
        listPropertyProductView = new SimpleListProperty<>();
        listPropertyProductView.set(productViewsList);
        productComboBox.itemsProperty().bindBidirectional(listPropertyProductView);


    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    @FXML
    public void cancelAction() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void selectedProductAction() {
        quantityTextField.setText("");
        priceTextField.setText("");
        listPlaceCombobox = new ArrayList<>();
//
//        setPlaceCombobox(listPlaceCombobox);
        placeComboBox.getItems().removeAll(placeComboBox.getItems());

        ProductView productView = (ProductView) productComboBox.getSelectionModel().getSelectedItem();

        try {
            stockList = productView.getStockList();
        } catch (Exception e) {

        }

        Map<Integer, Place> placeMap = dbConnection.getPlaceMap();
        for (Stock stock :
                stockList) {
            listPlaceCombobox.add(placeMap.get(Integer.parseInt(stock.getPlace_id())));
        }
        setPlaceCombobox(listPlaceCombobox);


    }

    public void setPlaceCombobox(ArrayList<Place> listPlaceCombobox) {


        listPropertyPlace = new SimpleListProperty<>(); //OBSlUGA Comboboxa z listy mieszkan
        placeObservableListCombobox = FXCollections.observableArrayList(listPlaceCombobox);
        listPropertyPlace.set(placeObservableListCombobox);
        placeComboBox.itemsProperty().bindBidirectional(listPropertyPlace);
    }

    public void selectedPlaceAction() {

        Place place = (Place) placeComboBox.getSelectionModel().getSelectedItem();
        int quantity = 0;
        for (Stock stock :
                stockList) {
            try {
                if (Integer.parseInt(stock.getPlace_id()) == place.getId()) {
                    quantity = stock.getQuantity();
                    this.stock = stock;

                }
            } catch (NullPointerException e) {

            }
        }
        quantityTextField.setText(String.valueOf(quantity));
    }

    @FXML
    public void addOrderAction() {
        String quantity = quantityTextField.getText();
        String price = priceTextField.getText();


        try {
            Integer.parseInt(quantity);
            quantityLabel.setText("");
        } catch (NumberFormatException e) {
            quantityLabel.setText("Bad value");
            return;
        }
        try {
            Integer.parseInt(price);
            priceLabel.setText("");
        } catch (NumberFormatException e) {
            priceLabel.setText("Bad value");
            return;
        }
        if (Integer.parseInt(quantity) > stock.getQuantity() || Integer.parseInt(quantity) == 0) {
            quantityLabel.setText("Bad quantity");
            return;
        }

        orderID = Integer.parseInt(dbConnection.addOrder(String.valueOf(client.getId())));
        ProductView productView = (ProductView) productComboBox.getSelectionModel().getSelectedItem();
        dbConnection.addCartItem(String.valueOf(productView.getId()), String.valueOf(stock.getId()), quantity, price, String.valueOf(orderID));
        mainController.refreschAction();
        orderController.show();
        setView();

        try {
            Stage succesStage = new Stage();
            FXMLLoader loader = new FXMLLoader();

            Pane root = loader.load(getClass().getResource("/fxml/SuccesScreen.fxml").openStream());

            SuccesController succesController = loader.getController();


            Scene scene = new Scene(root);
            String css = this.getClass().getResource("/css/aplication.css").toExternalForm();
            scene.getStylesheets().add(css);
            succesStage.setTitle("Succes");
            succesStage.setScene(scene);
            succesStage.setResizable(false);

            succesStage.show();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        Stage stage = (Stage) addOrderButton.getScene().getWindow();
        stage.close();
        orderController.closeStage();


    }

}
