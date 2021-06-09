package controller;

import conection.DbConnection;
import converters.ProductView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import klasy.Category;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NewCategoryController{


    @FXML
    private TreeView categoryTreeViewAddCategory;
    @FXML
    private Label chosePraentCategory;
    @FXML
    private TextField descriptionTextField;
    @FXML
    private TextField nameTextField;
    @FXML
    private Button createButton;
    @FXML
    private Label warningLabel;
    @FXML
    private Label closeLabel;
    @FXML
    private Button closeButton;

    private MainController mainController;


    public MainController getMainController() {
        return mainController;
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    private DbConnection dbConnection;
    private Category rootName;
    private Category categoryRoot;
    private Category choseCategoryTreeView;


    public void initialize(){

        dbConnection = new DbConnection();
        List<Category> categoryList = dbConnection.getCategoryList();
        List<Category> listaDoTreeView = categoryList;
        Map<Integer, TreeItem> mapDoTreeView = new HashMap<>();

        categoryList.forEach(category -> {

            if (category.getParent_id() == 0) {
                rootName = category;
                categoryRoot = category;
            }
        });

        categoryList.remove(categoryRoot);

        TreeItem<Category> root = new TreeItem<>(rootName);
        mapDoTreeView.put(categoryRoot.getId(), root);
        while (true) {

            for (Category kategoria :
                    listaDoTreeView) {

                if (kategoria.getParent_id() != 0 && mapDoTreeView.get(kategoria.getParent_id()) != null) {
                    TreeItem<Category> xd = new TreeItem<>(kategoria);
                    mapDoTreeView.get(kategoria.getParent_id()).getChildren().add(xd);
                    mapDoTreeView.put(kategoria.getId(), xd);
                    listaDoTreeView.remove(kategoria);

                } else {
                    continue;
                }
                break;
            }
            if (listaDoTreeView.size() == 0) {
                break;
            }

        }


        categoryTreeViewAddCategory.setRoot(root);

    }



    @FXML
    public void treeViewClikedAddCategory(){
        categoryTreeViewAddCategory.setOnMouseClicked(event -> {
                    try {
                        ObservableList<ProductView> prodouctListViewWybranaCategoria = FXCollections.observableArrayList();
                        if (event.getClickCount() == 2) {
                             choseCategoryTreeView = (Category) ((TreeItem) categoryTreeViewAddCategory.getSelectionModel().getSelectedItem()).getValue();



                            chosePraentCategory.setText(choseCategoryTreeView.getName());
                        }
                    } catch (Exception e) {

                    }
                }
        );

    }


    @FXML
    public void createCategoryAction(){


        if(nameTextField.getText() == ""){
            warningLabel.setText("Invalid name");
            return;
        }else{
            warningLabel.setText("");
        }
        if(choseCategoryTreeView == null){
            warningLabel.setText("Chose parent category");
            return;
        }else {
            warningLabel.setText("");
        }

        dbConnection.addCategory(nameTextField.getText(), descriptionTextField.getText(), String.valueOf(choseCategoryTreeView.getId()));
        mainController.treeViewRefresh();
        initialize();
        nameTextField.setText("");
        descriptionTextField.setText("");
        chosePraentCategory.setText("");
        choseCategoryTreeView = null;

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


    }
    @FXML
    public void closeAction(){
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }


}
