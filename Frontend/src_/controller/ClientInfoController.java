package controller;

import conection.DbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import klasy.Client;
import klasy.Order;

import java.io.IOException;
import java.util.ArrayList;

public class ClientInfoController {

    @FXML
    private Label clientName;
    @FXML
    private Label clientSurname;
    @FXML
    private Label clientCompany;
    @FXML
    private Label clientNip;
    @FXML
    private Label clientEmail;
    @FXML
    private TableView orderTableView;
    @FXML
    private TableColumn orderTableColumn;
    @FXML
    private TableColumn statusTableColumn;
    @FXML
    private Button cancelButton;
    private DbConnection dbConnection;
    private Client client;
    private ArrayList<Order> orderList;
    public void setClient(Client client) {
        this.client = client;
    }

    @FXML
    public void cancelAction(){
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    public void show(){
        dbConnection = new DbConnection();
        clientName.setText(client.getF_name());
        clientSurname.setText(client.getS_name());
        clientCompany.setText(client.getCompany());
        clientNip.setText(client.getNip());
        clientEmail.setText(client.getEmail());

        orderList = client.getOrderList();
        ObservableList<Order> orderTableViewList =  FXCollections.observableArrayList();

        for (Order ord :
                orderList) {
            orderTableViewList.add(ord);

        }
        viewOrders(orderTableViewList);



    }
    public void viewOrders(ObservableList orderList) {

        orderTableView.setItems(orderList);
        orderTableColumn.setCellValueFactory(new PropertyValueFactory<>("code"));
        statusTableColumn.setCellValueFactory(new PropertyValueFactory<>("etykietaStatus"));
        orderTableView.refresh();

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

    }

}
