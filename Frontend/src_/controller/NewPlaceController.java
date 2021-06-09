package controller;

import conection.DbConnection;
import converters.ProductView;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import klasy.Place;

import java.io.IOException;
import java.util.ArrayList;

public class NewPlaceController {

    private MainController mainController;
    private DbConnection dbConnection;

    public MainController getMainController() {
        return mainController;
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    @FXML
    private TextField nameTextField;
    @FXML
    private TextArea descriptionTextArea;

    @FXML
    private Button createButton;
    @FXML
    private Button closeButton;
    @FXML
    private Label nameError;
    private ObservableList<ProductView> prodouctListView;


    public void createAction(){
        String name = nameTextField.getText();

        nameError.setText("");
        if(name == ""){
            nameError.setText("Bad name");
            return;
        }

        dbConnection = new DbConnection();
       String id =  dbConnection.addPlace(nameTextField.getText(),descriptionTextArea.getText());

        Place place = new Place(nameTextField.getText(),descriptionTextArea.getText(), Integer.parseInt(id));

        ArrayList<Place> list = mainController.getListPlaceCombobox();
        list.add(place);
        mainController.setPlaceCombobox(list);
        prodouctListView = dbConnection.getTableViewList();
        mainController.viewRefresh(prodouctListView);

        try {
            Stage succesStage = new Stage();
            FXMLLoader loader = new FXMLLoader();

            Pane root =  loader.load(getClass().getResource("/fxml/SuccesScreen.fxml").openStream());

            SuccesController succesController =  loader.getController();

            succesStage.setTitle("Succes");
            Scene scene = new Scene(root);
            String css = this.getClass().getResource("/css/aplication.css").toExternalForm();
            scene.getStylesheets().add(css);
            succesStage.setScene(scene);
            succesStage.setResizable(false);

            succesStage.show();

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        Stage stage = (Stage) createButton.getScene().getWindow();
        stage.close();

    }
    public void closeAction(){
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }




}
