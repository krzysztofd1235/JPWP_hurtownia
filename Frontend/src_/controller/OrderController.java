package controller;


import conection.DbConnection;
import javafx.beans.property.ListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import klasy.Client;
import klasy.Order;

import java.io.IOException;
import java.util.ArrayList;

public class OrderController {
    private MainController mainController;
    private Client client;
    private ArrayList<Order> orderList;
    private OrderController orderController;
    @FXML
    private Button cancelButton;
    @FXML
    private Button addOrder;
    @FXML
    private TableView orderTableView;
    @FXML
    private TableColumn orderTableColumn;
    @FXML
    private TableColumn statusTableColumn;

    ArrayList<Order> listOrderCombobox;
    private ObservableList<Order> orderObservableListCombobox;
    private ListProperty<Order> listPropertyOrder;
    private DbConnection dbConnection;
    public void initialize(){
    dbConnection = new DbConnection();

    }

    public void show(){

        orderList = client.getOrderList();

        ObservableList<Order> orderTableViewList =  FXCollections.observableArrayList();

        for (Order ord :
                orderList) {
            orderTableViewList.add(ord);

        }

        viewOrders(orderTableViewList);
    }

    public void setOrderController(OrderController orderController) {
        this.orderController = orderController;
    }



    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    @FXML
    public void cancelAction(){
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
    @FXML
    public void statusOrderAction(){
        try {
            Stage Stage = new Stage();
            FXMLLoader loader = new FXMLLoader();

            Pane root = loader.load(getClass().getResource("/fxml/OrderStatusScreen.fxml").openStream());

            OrderStatusController orderStatusController = loader.getController();


            orderStatusController.setOrder((Order) orderTableView.getSelectionModel().getSelectedItem());
            orderStatusController.setStatus();
            Scene scene = new Scene(root);
            Stage.setTitle("Order status");
            String css = this.getClass().getResource("/css/aplication.css").toExternalForm();
            scene.getStylesheets().add(css);
            Stage.setScene(scene);
            Stage.setResizable(false);

            Stage.show();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    @FXML
    public void deleteOrderAction(){
        dbConnection.deleteOrder(String.valueOf(((Order)orderTableView.getSelectionModel().getSelectedItem()).getId()));
        orderList.remove((Order)orderTableView.getSelectionModel().getSelectedItem());

        mainController.refreschAction();
    }
    Stage stageAddOrder;
    @FXML
    public void addOrderAction(){
        try {
            stageAddOrder = new Stage();
            FXMLLoader loader = new FXMLLoader();

            Pane root = loader.load(getClass().getResource("/fxml/AddOrderScreen.fxml").openStream());

            AddOrderController addOrderController = loader.getController();
            addOrderController.setMainController(mainController);
            addOrderController.setView();
            addOrderController.setClient(client);
            addOrderController.setOrderController(orderController);
            stageAddOrder.setTitle("Add order");
            Scene scene = new Scene(root);
            String css = this.getClass().getResource("/css/aplication.css").toExternalForm();
            scene.getStylesheets().add(css);
            stageAddOrder.setScene(scene);
            stageAddOrder.setResizable(false);

            stageAddOrder.show();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    public void closeStage(){
        stageAddOrder.close();
    }

    public void viewOrders(ObservableList orderList) {

            orderTableView.setItems(orderList);

            orderTableColumn.setCellValueFactory(new PropertyValueFactory<>("code"));
            statusTableColumn.setCellValueFactory(new PropertyValueFactory<>("etykietaStatus"));

            orderTableView.refresh();

    }

}
