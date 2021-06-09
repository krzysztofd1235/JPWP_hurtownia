package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;



public class SuccesController {

    @FXML
    private Button okButton;

    public void okAction(){
        Stage stage = (Stage) okButton.getScene().getWindow();
        stage.close();
    }

}
