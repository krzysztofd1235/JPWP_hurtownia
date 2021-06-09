package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;



public class DeleteWarninigController {

    @FXML
    private Button okButton;
    @FXML
    private Button cancelButton;
    @FXML
    private Label textLabel;

    private String name;
    private MainController mainController;

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public void initialize(){

        textLabel.setText("You want to delete this " +name);
    }

    public void setName(String name) {
        this.name = name;
        initialize();

    }

    public void okAction(){
        if (name == "product"){
            mainController.deleteProductAction();
        }
        if(name == "category"){
            mainController.deleteCategoryAction();
        }if(name == "client"){
            mainController.deleteClientAction();
        }if(name == "place") {
            mainController.deletePlaceAction();
        }
        Stage stage = (Stage) okButton.getScene().getWindow();
        stage.close();
    }
    public void cancelAction(){
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }


}
