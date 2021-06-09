package controller;


import converters.ProductView;
import holder.NewProductHolder;
import holder.ProductViewHolder;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import klasy.*;


import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import conection.DbConnection;

public class MainController {

    @FXML
    private TableView ProductTabelView;
    @FXML
    private TableColumn<Stock, String> productColumn;
    @FXML
    private TableColumn<Stock, String> quantitycolumn;
    @FXML
    private TableColumn<Stock, String> placeColumn;
    @FXML
    private TableColumn<Stock, String> sellPriceColumn;
    @FXML
    private TableColumn<Stock, String> buyPriceColumn;
    @FXML
    private TableColumn<Stock, String> barcodeColumn;
    @FXML
    private ContextMenu contexMenuItem;
    @FXML
    private TreeView categoryTreeView;

    //////////////////////////////////////////////////////////////
    @FXML
    private TreeView<Category> categoryTreeViewAddScreen;
    @FXML
    private Label choseCategory;
    @FXML
    private ComboBox<Place> comboboxPlaceAddScreen;
    @FXML
    private Label chosePlace;
    @FXML
    private ComboBox<TaxRate> comboboxTaxRateAddScreen;
    @FXML
    private Label choseTax;
    @FXML
    private Label choseUnit;
    @FXML
    private ComboBox<Unit> comboboxUnitAddScreen;
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField barcodeTextField;
    @FXML
    private TextField buyPriceTextField;
    @FXML
    private TextField sellPriceTextField;
    @FXML
    private TextArea descriptionTextField;
    @FXML
    private TextField quantityTextField;
    @FXML
    private Label buyPriceError;
    @FXML
    private Label sellPriceError;
    @FXML
    private Label quantityError;
    @FXML
    private Label choseName;

    @FXML
    private Label choseSellPrice;
    @FXML
    private Label choseBuyPrice;
    @FXML
    private Label choseQuantity;
    @FXML
    private Label choseBarcode;
    @FXML
    private Label choseDescription;

////////////////////////////////////
    @FXML
    private MenuItem deleteMenuItem;
    @FXML
    private Label errorBarcode;
    @FXML
    private Label lackElement;
////////////////////////////

    @FXML
    private  Button newClientButton;
    @FXML
    private MenuItem deleteClinet;
    @FXML
    private TableView ClientTableView;
    @FXML
    private TableColumn clientColumn;
    @FXML
    private TableColumn nipColumn;
    @FXML
    private TableColumn emailColumn;
    @FXML
    private TableColumn surnameClient;


    private DbConnection dbConnection;
    private Category rootName;
    private ObservableList<ProductView> prodouctListView;
    private Category categoryRoot;

    ArrayList<Place> listPlaceCombobox;
    private ObservableList<Place> placeObservableListCombobox;
    private ListProperty<Place> listPropertyPlace;


    ArrayList<TaxRate> listTaxRateCombobox;
    private ObservableList<TaxRate> taxRateObservableListCombobox;
    private ListProperty<TaxRate> listPropertyTaxRate;

    ArrayList<Unit> listUnitCombobox;
    private ObservableList<Unit> unitObservableListCombobox;
    private ListProperty<Unit> listPropertyUnit;

    ArrayList<Client> listClientCombobox;
    private ObservableList<Client> clientObservableListCombobox;
    private ListProperty<Client> listPropertyClient;

    private TreeItem<Category> root1;
    private MainController mainController;

    private  ObservableList<Client> clientLiostView = FXCollections.observableArrayList();
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public void initialize() throws IOException {
        dbConnection = new DbConnection();

        prodouctListView = dbConnection.getTableViewList();
        viewRefresh(prodouctListView);

        listPlaceCombobox = (ArrayList<Place>) dbConnection.getPlaceList();
        listTaxRateCombobox = (ArrayList<TaxRate>) dbConnection.getTaxList();
        listUnitCombobox = (ArrayList<Unit>) dbConnection.getUnitList();
        listClientCombobox = (ArrayList<Client>) dbConnection.getClientList();
        setPlaceCombobox(listPlaceCombobox);
        setTaxRateCombobox(listTaxRateCombobox);
        setUnitCombobox(listUnitCombobox);
        for (Client client :
                listClientCombobox) {

            for (Order order :
                    client.getOrderList()) {
                order.setStatuEtykiet();

            }
            clientLiostView.add(client);

        }

        viewRefreshClient(clientLiostView);
        treeViewRefresh();

    }



    public ObservableList<ProductView> getProdouctListView() {
        return prodouctListView;
    }

    public ArrayList<Place> getListPlaceCombobox() {
        return listPlaceCombobox;
    }

    public void setPlaceCombobox(ArrayList<Place> listPlaceCombobox) {
        listPropertyPlace = new SimpleListProperty<>();
        placeObservableListCombobox = FXCollections.observableArrayList(listPlaceCombobox);
        listPropertyPlace.set(placeObservableListCombobox);
        comboboxPlaceAddScreen.itemsProperty().bindBidirectional(listPropertyPlace);
    }
    public void setTaxRateCombobox(ArrayList<TaxRate> listTaxRateCombobox) {
        listPropertyTaxRate = new SimpleListProperty<>();
        taxRateObservableListCombobox = FXCollections.observableArrayList(listTaxRateCombobox);
        listPropertyTaxRate.set(taxRateObservableListCombobox);
        comboboxTaxRateAddScreen.itemsProperty().bindBidirectional(listPropertyTaxRate);
    }

    public void setUnitCombobox(ArrayList<Unit> listUnitCombobox) {
        listPropertyUnit = new SimpleListProperty<>();
        unitObservableListCombobox = FXCollections.observableArrayList(listUnitCombobox);
        listPropertyUnit.set(unitObservableListCombobox);
        comboboxUnitAddScreen.itemsProperty().bindBidirectional(listPropertyUnit);
    }

    public void setRootTreeViewLogin(TreeItem<Category> rootLogin){
        categoryTreeView.setRoot(rootLogin);
        categoryTreeViewAddScreen.setRoot(rootLogin);
    }


    public void treeViewRefresh(){
        List<Category>  categoryList = dbConnection.getCategoryList();
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
                    TreeItem<Category> item = new TreeItem<>(kategoria);
                    mapDoTreeView.get(kategoria.getParent_id()).getChildren().add(item);
                    mapDoTreeView.put(kategoria.getId(), item);
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
        categoryTreeView.setRoot(root);
        categoryTreeViewAddScreen.setRoot(root);


    }



    public void viewRefresh(ObservableList prodouctList) {
        ProductTabelView.setItems(prodouctList);
        productColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        quantitycolumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        placeColumn.setCellValueFactory(new PropertyValueFactory<>("place"));
        sellPriceColumn.setCellValueFactory(new PropertyValueFactory<>("sellPriceSee"));
        buyPriceColumn.setCellValueFactory(new PropertyValueFactory<>("buyPriceSee"));
        barcodeColumn.setCellValueFactory(new PropertyValueFactory<>("barcode"));
        ProductTabelView.refresh();
    }
    public void viewRefreshClient(ObservableList clientList) {
        ClientTableView.setItems(clientList);
        clientColumn.setCellValueFactory(new PropertyValueFactory<>("f_name"));
        surnameClient.setCellValueFactory(new PropertyValueFactory<>("s_name"));
        nipColumn.setCellValueFactory(new PropertyValueFactory<>("nip"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));

        ProductTabelView.refresh();
    }


    @FXML
    public void treeViewCliked() {

        categoryTreeView.setOnMouseClicked(event -> {

                    try {
                        ObservableList<ProductView> prodouctListViewWybranaCategoria = FXCollections.observableArrayList();
                        if (event.getClickCount() == 2) {

                            Category kategoriaZTreeView = null;
                            try {
                                kategoriaZTreeView = (Category) ((TreeItem) categoryTreeView.getSelectionModel().getSelectedItem()).getValue();
                            } catch (Exception e) {
                                System.out.println(e.toString());
                            }

                            for (ProductView produkt :
                                    prodouctListView) {
                                List<Category> kategorieProductList = (List<Category>) produkt.getListCategory();

                                for (Category kategorieProduktu :
                                        kategorieProductList) {
                                    if (kategorieProduktu.toString().equals(kategoriaZTreeView.getName())) {
                                        prodouctListViewWybranaCategoria.add(produkt);
                                    }
                                }
                            }
                            viewRefresh(prodouctListViewWybranaCategoria);
                        }
                    } catch (Exception e) {
                        System.out.println(e.toString());
                    }
                }
        );


    }

    @FXML
    public void treeViewClikedAddScreen(){

        categoryTreeViewAddScreen.setOnMouseClicked(event -> {
                    ObservableList<ProductView> prodouctListViewWybranaCategoria = FXCollections.observableArrayList();
                    if (event.getClickCount() == 2) {
                        Category kategoriaZTreeView = (Category) ((TreeItem) categoryTreeViewAddScreen.getSelectionModel().getSelectedItem()).getValue();

                        NewProductHolder newProductHolder = NewProductHolder.getINSTANCE();
                        newProductHolder.setCategory(kategoriaZTreeView);

                        choseCategory.setText(kategoriaZTreeView.getName());
                    }
                }
        );


    }

    @FXML
    public void placeComboboxAction(){
        try {
            Place place  = (Place) comboboxPlaceAddScreen.getSelectionModel().getSelectedItem();
            chosePlace.setText(place.getName());
            NewProductHolder newProductHolder = NewProductHolder.getINSTANCE();
            newProductHolder.setPlace(place);
        } catch (Exception e) {

        }
    }

    @FXML
    public void taxRateComboboxAction(){
        try {
            TaxRate taxRate = (TaxRate) comboboxTaxRateAddScreen.getSelectionModel().getSelectedItem();
            choseTax.setText(taxRate.getName());
            NewProductHolder newProductHolder = NewProductHolder.getINSTANCE();
            newProductHolder.setTaxRate(taxRate);
        } catch (Exception e) {

        }
    }
    @FXML
    public void unitComboboxAction(){
        try {
            Unit unit = (Unit) comboboxUnitAddScreen.getSelectionModel().getSelectedItem();
            choseUnit.setText(unit.getName());
            NewProductHolder newProductHolder = NewProductHolder.getINSTANCE();
            newProductHolder.setUnit(unit);
        } catch (Exception e) {
        }

    }
    @FXML
    public void addInfoAction(){
        String name = nameTextField.getText();
        String barcode = barcodeTextField.getText();
        String description = descriptionTextField.getText();
        int buyPrice = 0;
        int quantity = 0;
        int selPrice = 0;
        boolean numberError = false;

        try {
             buyPrice = Integer.parseInt(buyPriceTextField.getText());
            buyPriceError.setText("");
        } catch (NumberFormatException e) {
            buyPriceError.setText("Invalid value.");
            numberError = true;
        }


        try {
            selPrice = Integer.parseInt(sellPriceTextField.getText());
            sellPriceError.setText("");
        } catch (NumberFormatException e) {
            sellPriceError.setText("Invalid value.");
            numberError = true;
        }
        for (ProductView pr :
                prodouctListView) {
            if(barcodeTextField.getText().equals(pr.getBarcode())){
                errorBarcode.setText("Bad barcode");
                return;
            }else{
                errorBarcode.setText("");
            }
        }


        try {
            quantity = Integer.parseInt(quantityTextField.getText());
            quantityError.setText("");
        } catch (NumberFormatException e) {
            quantityError.setText("Invalid value.");
            numberError = true;
        }


        if (numberError) return;
        NewProductHolder newProductHolder = NewProductHolder.getINSTANCE();
        newProductHolder.setName(name) ;
        newProductHolder.setBarcode(barcode);
        newProductHolder.setBuyPrice(buyPrice*100);
        newProductHolder.setSellPrice(selPrice*100);
        newProductHolder.setDescription(description);
        newProductHolder.setQuantity(quantity);
        choseName.setText(name);
        choseBuyPrice.setText(String.valueOf(buyPrice));
        choseSellPrice.setText(String.valueOf(selPrice));
        choseBarcode.setText(barcode);
        choseDescription.setText(description);
        choseQuantity.setText(String.valueOf(quantity));

        nameTextField.setText("");
        barcodeTextField.setText("");
        buyPriceTextField.setText("");
        sellPriceTextField.setText("");
        quantityTextField.setText("");
        descriptionTextField.setText("");


    }

    @FXML
    public void deletePlaceWarningScreen(){
        try {
            Stage newWarning = new Stage();
            FXMLLoader loader = new FXMLLoader();

            Pane root = (Pane) loader.load(getClass().getResource("/fxml/DeleteWarningScreen.fxml").openStream());

            DeleteWarninigController deleteWarninigController =  loader.getController();
            deleteWarninigController.setMainController(mainController);
            deleteWarninigController.setName("place");

            Scene scene = new Scene(root);
            String css = this.getClass().getResource("/css/aplication.css").toExternalForm();
            newWarning.setTitle("Warning");
            scene.getStylesheets().add(css);
            newWarning.setScene(scene);
            newWarning.setResizable(false);

            newWarning.show();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void deletePlaceAction(){
        try {
            Place place  = (Place) comboboxPlaceAddScreen.getSelectionModel().getSelectedItem();
            dbConnection.deletPlace(place.getId());
            listPlaceCombobox.remove(place);
            setPlaceCombobox(listPlaceCombobox);
            NewProductHolder newProductHolder = NewProductHolder.getINSTANCE();
            newProductHolder.setPlace(null);
            chosePlace.setText("");
            prodouctListView = dbConnection.getTableViewList();
            viewRefresh(prodouctListView);
        } catch (Exception e) {

        }

    }




    @FXML
    public void createProductButton(){
        NewProductHolder newProductHolder = NewProductHolder.getINSTANCE();

        if(newProductHolder.getName() == null){
            lackElement.setText("Lack of \nname!!!");
            return;
        }
        if(newProductHolder.getCategory()  == null){
            lackElement.setText("Lack of \ncategory!!!");
            return;
        }

        if(newProductHolder.getSellPrice()  == 0){
            lackElement.setText("Lack of \nsell price!!!");
            return;
        }
        if(newProductHolder.getBuyPrice()  == 0){
            lackElement.setText("Lack of \nbuy price!!!");
            return;
        }
        if(newProductHolder.getTaxRate()  == null){
            lackElement.setText("Lack of \ntax!!!");
            return;
        }
        if(newProductHolder.getQuantity()  == 0){
            lackElement.setText("Lack of \nquantity!!!");
            return;
        }
        if(newProductHolder.getPlace()  == null){
            lackElement.setText("Lack of \nplace!!!");
            return;
        }
        if(newProductHolder.getBarcode()  == null){
            lackElement.setText("Lack of \nbarcode!!!");
            return;
        }
        if(newProductHolder.getUnit()  == null){
            lackElement.setText("Lack of \nunit!!!");
            return;
        }
        if(newProductHolder.getDescription()  == null){
            lackElement.setText("Lack of \ndescription!!!");
            return;
        }
        lackElement.setText("");


        String productid = dbConnection.addProduct(newProductHolder.getName(),String.valueOf(newProductHolder.getCategory().getId()),newProductHolder.getDescription(),
                newProductHolder.getSellPrice(), newProductHolder.getBuyPrice(), String.valueOf(newProductHolder.getTaxRate().getId()),
                String.valueOf(newProductHolder.getUnit().getId()),newProductHolder.getBarcode());
        int place_id = newProductHolder.getPlace().getId();

        List<Stock> stockList = dbConnection.getStockList();

        for (Stock sto :
                stockList) {
            if(sto.getPlace_id().equals(String.valueOf(place_id))){
                if(sto.getProduct_id().equals(productid)) {
                    dbConnection.changeStock(String.valueOf(sto.getId()), String.valueOf(newProductHolder.getQuantity()));

                }
            }

        }
        prodouctListView = dbConnection.getTableViewList();
        viewRefresh(prodouctListView);
        choseName.setText("");
        choseCategory.setText("");
        choseCategory.setText("");
        choseBuyPrice.setText("");
        choseSellPrice.setText("");
        choseTax.setText("");
        choseQuantity.setText("");
        chosePlace.setText("");
        choseBarcode.setText("");
        choseUnit.setText("");
        choseDescription.setText("");



        try {
            Stage succesStage = new Stage();
            FXMLLoader loader = new FXMLLoader();

            Pane root =  loader.load(getClass().getResource("/fxml/SuccesScreen.fxml").openStream());

            SuccesController succesController =  loader.getController();


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

    }




    @FXML
    public void newCategoryAction(){

        try {
            Stage InformacjeStage = new Stage();
            FXMLLoader loader = new FXMLLoader();

            Pane root =  loader.load(getClass().getResource("/fxml/NewCategoryScreen.fxml").openStream());

            NewCategoryController newCategoryController =  loader.getController();
            newCategoryController.setMainController(mainController);

            Scene scene = new Scene(root);
            String css = this.getClass().getResource("/css/aplication.css").toExternalForm();
            scene.getStylesheets().add(css);
            InformacjeStage.setTitle("New category");
            InformacjeStage.setScene(scene);
            InformacjeStage.setResizable(false);

            InformacjeStage.show();

        } catch (IOException ex) {
            ex.printStackTrace();
        }


    }

    public TreeItem<Category> getRoot1() {
        return root1;
    }

    @FXML
    public void menuItemClick() {
        ProductViewHolder holder = ProductViewHolder.getINSTANCE();
        holder.setProductView((ProductView) ProductTabelView.getSelectionModel().getSelectedItem());
        if(ProductTabelView.getSelectionModel().getSelectedItem() == null){
            return;
        }
        try {
            Stage InformacjeStage = new Stage();
            FXMLLoader loader = new FXMLLoader();

            Pane root =  loader.load(getClass().getResource("/fxml/InformacjeScreen.fxml").openStream());

            InformacjeController informacjeController =  loader.getController();

            Scene scene = new Scene(root);
            String css = this.getClass().getResource("/css/aplication.css").toExternalForm();
            scene.getStylesheets().add(css);
            InformacjeStage.setTitle("Info");
            InformacjeStage.setScene(scene);
            InformacjeStage.setResizable(false);
            InformacjeStage.show();

        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }


    @FXML
    public void newPlaceAction(){


        try {
            Stage newPlaceStage = new Stage();
            FXMLLoader loader = new FXMLLoader();

            Pane root =  loader.load(getClass().getResource("/fxml/NewPlaceScreen.fxml").openStream());

            NewPlaceController newPlaceController = (NewPlaceController) loader.getController();
            newPlaceController.setMainController(mainController);

            Scene scene = new Scene(root);
            String css = this.getClass().getResource("/css/aplication.css").toExternalForm();
            scene.getStylesheets().add(css);
            newPlaceStage.setTitle("New place");
            newPlaceStage.setScene(scene);
            newPlaceStage.setResizable(false);

            newPlaceStage.show();


        } catch (IOException ex) {
            ex.printStackTrace();
        }


    }



    @FXML
    public void openWarningScreen(){
        try {
            Stage newWarning = new Stage();
            FXMLLoader loader = new FXMLLoader();

            Pane root = (Pane) loader.load(getClass().getResource("/fxml/DeleteWarningScreen.fxml").openStream());

            DeleteWarninigController deleteWarninigController =  loader.getController();
            deleteWarninigController.setMainController(mainController);
            deleteWarninigController.setName("product");

            Scene scene = new Scene(root);
            String css = this.getClass().getResource("/css/aplication.css").toExternalForm();
            scene.getStylesheets().add(css);
            newWarning.setTitle("Warning");
            newWarning.setScene(scene);
            newWarning.setResizable(false);

            newWarning.show();

        } catch (IOException ex) {
            ex.printStackTrace();
        }


    }
    public void deleteProductAction(){
        ProductView productView = (ProductView) ProductTabelView.getSelectionModel().getSelectedItem();
        dbConnection.deleteProduct(productView.getId());
        prodouctListView.remove(productView);
        viewRefresh(prodouctListView);
    }
    public void openModyfiScreen() {
        ProductViewHolder holder = ProductViewHolder.getINSTANCE();
        holder.setProductView((ProductView) ProductTabelView.getSelectionModel().getSelectedItem());
        try {
            Stage newModyfi = new Stage();
            FXMLLoader loader = new FXMLLoader();

            Pane root = (Pane) loader.load(getClass().getResource("/fxml/ProductModyfiScreen.fxml").openStream());

            ProductModyfiController productModyfiController = (ProductModyfiController) loader.getController();
            productModyfiController.setMainController(mainController);

            Scene scene = new Scene(root);
            String css = this.getClass().getResource("/css/aplication.css").toExternalForm();
            scene.getStylesheets().add(css);
            newModyfi.setTitle("Product modyfi");
            newModyfi.setScene(scene);
            newModyfi.setResizable(false);

            newModyfi.show();

        } catch (IOException ex) {

        }

    }

    @FXML
    public void tableViewClicked(){

        ProductTabelView.setOnMouseClicked(event -> {

                    if (event.getClickCount() == 2) {
                        menuItemClick();
                    }
                }
        );


    }
    @FXML
    public void clientTableViewClicked(){

        ClientTableView.setOnMouseClicked(event -> {

                    if (event.getClickCount() == 2) {


                        if(ClientTableView.getSelectionModel().getSelectedItem() == null){
                            return;
                        }
                        try {
                            Stage InformacjeStage = new Stage();
                            FXMLLoader loader = new FXMLLoader();

                            Pane root =  loader.load(getClass().getResource("/fxml/ClientInfoScreen.fxml").openStream());

                            ClientInfoController clientInfoController =  loader.getController();
                            Client client = (Client) ClientTableView.getSelectionModel().getSelectedItem();
                            for (Order order :
                                    client.getOrderList()) {
                                order.setStatuEtykiet();

                            }
                            clientInfoController.setClient(client);
                            clientInfoController.show();
                            Scene scene = new Scene(root);
                            String css = this.getClass().getResource("/css/aplication.css").toExternalForm();
                            scene.getStylesheets().add(css);
                            InformacjeStage.setTitle("Info");
                            InformacjeStage.setScene(scene);
                            InformacjeStage.setResizable(false);
                            InformacjeStage.show();

                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }

                    }
                }
        );


    }
    @FXML
    public void refreschAction(){

            prodouctListView = dbConnection.getTableViewList();
            viewRefresh(prodouctListView);

            listPlaceCombobox = (ArrayList<Place>) dbConnection.getPlaceList();
            listTaxRateCombobox = (ArrayList<TaxRate>) dbConnection.getTaxList();
            listUnitCombobox = (ArrayList<Unit>) dbConnection.getUnitList();


            ArrayList<Client>     listClientCombobox1 = (ArrayList<Client>) dbConnection.getClientList();
            setPlaceCombobox(listPlaceCombobox);
            setTaxRateCombobox(listTaxRateCombobox);
            setUnitCombobox(listUnitCombobox);
            ObservableList<Client> clientLiostView1 = FXCollections.observableArrayList();
            for (Client client :
                    listClientCombobox1) {
                 clientLiostView1.add(client);

        }

            viewRefreshClient(clientLiostView1);
            treeViewRefresh();


        treeViewRefresh();

    }

    public void deleteCategoryAction(){
        Category category = (Category) ((TreeItem) categoryTreeView.getSelectionModel().getSelectedItem()).getValue();
        dbConnection.deleteCategory(String.valueOf(category.getId()));
        treeViewRefresh();
    }
    @FXML
    public void modifyCategoryAction(){
        try {
            Stage modifyCategoryStage = new Stage();
            FXMLLoader loader = new FXMLLoader();

            Pane root = (Pane) loader.load(getClass().getResource("/fxml/ModifyCategoryScreen.fxml").openStream());

            ModifyCategoryController modifyCategoryController =  loader.getController();
            modifyCategoryController.setMainController(mainController);
            Category category = (Category) ((TreeItem) categoryTreeView.getSelectionModel().getSelectedItem()).getValue();
            modifyCategoryController.setCategory(category);
            modifyCategoryStage.setTitle("Modyfi category");
            Scene scene = new Scene(root);
            String css = this.getClass().getResource("/css/aplication.css").toExternalForm();
            scene.getStylesheets().add(css);
            modifyCategoryStage.setScene(scene);
            modifyCategoryStage.setResizable(false);

            modifyCategoryStage.show();

        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }
    @FXML
    public void openWarnigScreenCategory(){
        try {
            Stage newWarning = new Stage();
            FXMLLoader loader = new FXMLLoader();

            Pane root = (Pane) loader.load(getClass().getResource("/fxml/DeleteWarningScreen.fxml").openStream());

            DeleteWarninigController deleteWarninigController =  loader.getController();
            deleteWarninigController.setMainController(mainController);
            deleteWarninigController.setName("category");
            newWarning.setTitle("Warning");

            Scene scene = new Scene(root);
            String css = this.getClass().getResource("/css/aplication.css").toExternalForm();
            scene.getStylesheets().add(css);
            newWarning.setScene(scene);
            newWarning.setResizable(false);

            newWarning.show();

        } catch (IOException ex) {
            ex.printStackTrace();
        }


    }
    @FXML
    public void clinetCmoboboxAction(){

    }
    @FXML
    public void newClientAction(){

        try {
            Stage AddClientStage = new Stage();
            FXMLLoader loader = new FXMLLoader();

            Pane root =  loader.load(getClass().getResource("/fxml/ClientAddScreen.fxml").openStream());

            ClientAddController  clientAddController =  loader.getController();
            clientAddController.setMainController(mainController);
            AddClientStage.setTitle("Add client");
            Scene scene = new Scene(root);
            String css = this.getClass().getResource("/css/aplication.css").toExternalForm();
            scene.getStylesheets().add(css);
            AddClientStage.setScene(scene);
            AddClientStage.setResizable(false);

            AddClientStage.show();

        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }
    @FXML
    public void openModyfiScreenClient(){

        try {
            Stage AddClientStage = new Stage();
            FXMLLoader loader = new FXMLLoader();

            Pane root =  loader.load(getClass().getResource("/fxml/ClientAddScreen.fxml").openStream());

            ClientAddController  clientAddController =  loader.getController();
            clientAddController.setMainController(mainController);
            clientAddController.setClinetModyfi((Client) ClientTableView.getSelectionModel().getSelectedItem());
            clientAddController.setModyfi();
            AddClientStage.setTitle("Modyfi client");
            Scene scene = new Scene(root);
            String css = this.getClass().getResource("/css/aplication.css").toExternalForm();
            scene.getStylesheets().add(css);
            AddClientStage.setScene(scene);
            AddClientStage.setResizable(false);

            AddClientStage.show();

        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }
    @FXML
    public void openWarningScreenClient(){
        try {
            Stage newWarning = new Stage();
            FXMLLoader loader = new FXMLLoader();

            Pane root = (Pane) loader.load(getClass().getResource("/fxml/DeleteWarningScreen.fxml").openStream());

            DeleteWarninigController deleteWarninigController =  loader.getController();
            deleteWarninigController.setMainController(mainController);
            deleteWarninigController.setName("client");
            newWarning.setTitle("Warning");

            Scene scene = new Scene(root);
            String css = this.getClass().getResource("/css/aplication.css").toExternalForm();
            scene.getStylesheets().add(css);
            newWarning.setScene(scene);
            newWarning.setResizable(false);

            newWarning.show();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    public void deleteClientAction(){

        dbConnection.deleteClient(String.valueOf(((Client)ClientTableView.getSelectionModel().getSelectedItem()).getId()));
        refreschAction();
    }
    public void addOrderAction(){
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader();

            Pane root = (Pane) loader.load(getClass().getResource("/fxml/OrderScreen.fxml").openStream());

            OrderController orderController =  loader.getController();
            orderController.setMainController(mainController);
            orderController.setClient((Client) ClientTableView.getSelectionModel().getSelectedItem());
            orderController.setOrderController(orderController);
            orderController.show();
            stage.setTitle("Order");
            Scene scene = new Scene(root);
            String css = this.getClass().getResource("/css/aplication.css").toExternalForm();
            scene.getStylesheets().add(css);
            stage.setScene(scene);
            stage.setResizable(false);

            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }



    }

}