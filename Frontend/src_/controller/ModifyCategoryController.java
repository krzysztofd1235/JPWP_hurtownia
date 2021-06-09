package controller;

import conection.DbConnection;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import klasy.Category;


public class ModifyCategoryController {
    @FXML
    private Button modifyButton;
    @FXML
    private Button cancelButton;
    @FXML
    private Label warningLabel;
    @FXML
    private TextField nameTextField;
    @FXML
    private TextArea descriptionTextArea;
    private MainController mainController;
    private Category category;
    private DbConnection dbConnection;

    public void initialize(){

        dbConnection = new DbConnection();


    }

    public void setCategory(Category category) {

        this.category = category;
        nameTextField.setText(category.getName());
        descriptionTextArea.setWrapText(true);
        descriptionTextArea.setText(category.getDescription());
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public void modifyAction(){
        if(nameTextField.getText() == ""){
            warningLabel.setText("Invalid name");
        }else {
            warningLabel.setText("");
        }
        dbConnection.modifyCategory(nameTextField.getText(), descriptionTextArea.getText(), String.valueOf(category.getId()), String.valueOf(category.getParent_id()));
        mainController.treeViewRefresh();
        Stage stage = (Stage) modifyButton.getScene().getWindow();
        stage.close();
    }
    public void  cancelAction(){

        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
}
