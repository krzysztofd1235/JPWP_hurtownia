package controller;

import conection.DbConnection;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.stage.Stage;
import klasy.Order;

public class OrderStatusController {

    @FXML
    private Button cancelButton;
    @FXML
    private Button okButton;
    @FXML
    private RadioButton status1RadioButton;
    @FXML
    private RadioButton status2RadioButton;
    @FXML
    private RadioButton status3RadioButton;
    @FXML
    private RadioButton status4RadioButton;
    @FXML
    private RadioButton status5RadioButton;

    private Order order;
    private DbConnection dbConnection;

    public void initialize(){
    dbConnection = new DbConnection();
    }

    public  void setStatus(){
        int status = Integer.parseInt(order.getStatus());
        if(status == 1){
            status1RadioButton.setSelected(true);
        }else if(status == 2){
            status2RadioButton.setSelected(true);
        }else if(status == 3) {
            status3RadioButton.setSelected(true);
        }else if(status == 4) {
            status4RadioButton.setSelected(true);
        }else if(status == 5) {
            status5RadioButton.setSelected(true);
        }

    }
    public void setOrder(Order order) {
        this.order = order;
    }

    @FXML
    public void cancelAction(){
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();

    }
    @FXML
    public void okAction(){

        int status;
        if(status1RadioButton.isSelected()){
            status = 1;
        }else if(status2RadioButton.isSelected()){
            status = 2;
        } else if (status3RadioButton.isSelected()) {
            status = 3;
        } else if (status4RadioButton.isSelected()) {
            status = 4;
        } else if (status5RadioButton.isSelected()) {
            status = 5;
        } else {
            status = Integer.parseInt(order.getStatus());
        }
        dbConnection.modifyOrder(order.getId(), String.valueOf(status));


        Stage stage = (Stage) okButton.getScene().getWindow();
        stage.close();


    }
    @FXML
    public void s1Action(){
        status2RadioButton.setSelected(false);
        status3RadioButton.setSelected(false);
        status4RadioButton.setSelected(false);
        status5RadioButton.setSelected(false);



    }
    @FXML
    public void s2Action(){
        status1RadioButton.setSelected(false);
        status3RadioButton.setSelected(false);
        status4RadioButton.setSelected(false);
        status5RadioButton.setSelected(false);
    }
    @FXML
    public void s3Action(){
        status1RadioButton.setSelected(false);
        status2RadioButton.setSelected(false);
        status4RadioButton.setSelected(false);
        status5RadioButton.setSelected(false);
    }
    @FXML
    public void s4Action(){
        status1RadioButton.setSelected(false);
        status2RadioButton.setSelected(false);
        status3RadioButton.setSelected(false);
        status5RadioButton.setSelected(false);
    }
    @FXML
    public void s5Action(){
        status1RadioButton.setSelected(false);
        status2RadioButton.setSelected(false);
        status3RadioButton.setSelected(false);
        status4RadioButton.setSelected(false);

    }
}
