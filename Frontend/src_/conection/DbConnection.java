package conection;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import converters.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import klasy.*;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import klasy.Stock;


public class DbConnection {


    public String getString(URL url){
        String result = null;

        try {

            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            InputStream in = new BufferedInputStream(conn.getInputStream());
            result = IOUtils.toString(in, "UTF-8");
        } catch (IOException e) {
            System.out.println(e.toString());
        }

        return result;
    }



    public List<TaxRate> getTaxList(){
        URL url = null;
        try {
            url = new URL("http://hurtownia.mbednarski.pl/taxes");
        } catch (MalformedURLException ignored) {

        }
        String result = new DbConnection().getString(url) ;

        final ObjectMapper objectMapper = new ObjectMapper();
        List<TaxRate> langList = null;
        try {
            langList = objectMapper.readValue(result, new TypeReference<List<TaxRate>>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }
        return langList;
    }

    public Map<Integer, TaxRate> getTaxMap(){
        List<TaxRate> langList = new DbConnection().getTaxList();
        Map<Integer, TaxRate> taxes =new HashMap<>();
        langList.forEach(x -> taxes.put(x.getId(),x));
        return taxes;
    }
    public List<Unit> getUnitList(){
        URL url = null;
        try {
            url = new URL("http://hurtownia.mbednarski.pl/units");
        } catch (MalformedURLException e) {
            System.out.println(e.toString());

        }
        String result = new DbConnection().getString(url) ;

        final ObjectMapper objectMapper = new ObjectMapper();
        List<Unit> langList = null;
        try {
            langList = objectMapper.readValue(result, new TypeReference<List<Unit>>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }

        return langList;
    }

    public Map<Integer, Unit> getUnitMap(){
        List<Unit> langList = new DbConnection().getUnitList();
        Map<Integer, Unit> unit =new HashMap<>();
        langList.forEach(x -> unit.put(x.getId(),x));
        return unit;
    }
    public List<Place> getPlaceList(){
        URL url = null;
        try {
            url = new URL("http://hurtownia.mbednarski.pl/places");
        } catch (MalformedURLException e) {
            System.out.println(e.toString());

        }
        String result = new DbConnection().getString(url) ;

        final ObjectMapper objectMapper = new ObjectMapper();
        List<Place> langList = null;
        try {
            langList = objectMapper.readValue(result, new TypeReference<List<Place>>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }
        return langList;
    }
    public Map<Integer, Place> getPlaceMap(){
        List<Place> langList = new DbConnection().getPlaceList();
        Map<Integer, Place> place =new HashMap<>();
        langList.forEach(x -> place.put(x.getId(),x));
        return place;
    }
    public List<Category> getCategoryList(){
        URL url = null;
        try {
            url = new URL("http://hurtownia.mbednarski.pl/categories");
        } catch (MalformedURLException e) {
            System.out.println(e.toString());
        }
        String result = new DbConnection().getString(url) ;
        final ObjectMapper objectMapper = new ObjectMapper();
        List<Category> langList = null;
        try {
            langList = objectMapper.readValue(result, new TypeReference<List<Category>>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }
        return langList;
    }
    public Map<Integer, Category> getCategoryMap(){
        List<Category> langList = new DbConnection().getCategoryList();
        Map<Integer, Category> category =new HashMap<>();
        langList.forEach(x -> category.put(x.getId(),x));
        return category;
    }
    public List<Product> getProductList(){
        URL url = null;
        try {
            url = new URL("http://hurtownia.mbednarski.pl/products");
        } catch (MalformedURLException e) {
            System.out.println(e.toString());

        }
        String result = new DbConnection().getString(url) ;

        final ObjectMapper objectMapper = new ObjectMapper();
        List<Product> langList = null;
        try {
            langList = objectMapper.readValue(result, new TypeReference<List<Product>>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }

        return langList;

    }
    public Map<Integer, Product> getProductMap(){
        List<Product> langList = new DbConnection().getProductList();
        Map<Integer, Product> product =new HashMap<>();
        langList.forEach(x -> product.put(x.getId(),x));

        return product;
    }
    public List<Stock> getStockList(){
        URL url = null;
        try {
            url = new URL("http://hurtownia.mbednarski.pl/stocks");
        } catch (MalformedURLException e) {
            System.out.println(e.toString());
        }
        String result = new DbConnection().getString(url) ;
        final ObjectMapper objectMapper = new ObjectMapper();
        List<Stock> langList = null;
        try {
            langList = objectMapper.readValue(result, new TypeReference<List<Stock>>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }
        return langList;
    }
    public Map<Integer, Stock> getStockMap(){
        List<Stock> langList = new DbConnection().getStockList();
        Map<Integer, Stock> stock =new HashMap<>();
        langList.forEach(x -> stock.put(x.getId(),x));
        return stock;
    }

    public ObservableList<ProductView> getTableViewList(){

        List<Stock> stockList = getStockList();

        Map<Integer,Product> productMap = getProductMap();
        Map<Integer,Place> placeMap = getPlaceMap();
        Map<Integer,Category> categoryMap = getCategoryMap();
        Map<Integer,TaxRate> taxMap = getTaxMap();
        Map<Integer,Unit> unitMap = getUnitMap();


        ObservableList<ProductView> observableList = FXCollections.observableArrayList();

        for (Stock stock:
                stockList) {
            boolean spr = true;



            for (ProductView productViewinList:
                    observableList) {
                if(Integer.parseInt(stock.getProduct_id()) == productViewinList.getId()){
                    productViewinList.addStockList(stock);
                    spr = false;
                    break;
                }


            }
            if (spr) {
                Product product =  productMap.get(Integer.parseInt(stock.getProduct_id()));
                Place place =  placeMap.get(Integer.parseInt(stock.getPlace_id()));
                Category category =  categoryMap.get(product.getCat_id());
                TaxRate taxRate =  taxMap.get(Integer.parseInt(product.getTax_id()));
                Unit unit =  unitMap.get(Integer.parseInt(product.getUnit_id()));

                ProductView productView = new ProductView(product, stock, place, category, taxRate, unit, categoryMap, product.getId());
                productView.addStockList(stock);
                observableList.add(productView);
            }
        }

        return observableList;
    }

    public String addString(String json, URL url){
        String result = null;
        try {

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod("POST");
            OutputStream os = conn.getOutputStream();
            os.write(json.getBytes("UTF-8"));

            os.close();
            // read the response
            InputStream in = new BufferedInputStream(conn.getInputStream());
            result = IOUtils.toString(in, "UTF-8");
            in.close();
            conn.disconnect();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return result;

    }


    public String addProduct(String name,String cat_id,String description,int sell_price, int buy_price,String tax_id, String unit_id, String barcode){
        String result;
        String json = null;
//        String json = "{\"barcode\":\"1213413345\",\"buy_price\":200000,\"category_id\":5,\"description\":\"Dobra skrętka, nie żadne CCA\",\"name\":\"Skrętka UTP\",\"sell_price\":280000,\"tax_id\":2,\"unit_id\":3}";
        URL url = null;

        AddProductJson addProductJson= new AddProductJson(name,cat_id,description,sell_price,buy_price,tax_id,unit_id,barcode);

        try {
            url = new URL("http://hurtownia.mbednarski.pl/products/");
        } catch (MalformedURLException e) {
            System.out.println(e.toString());
        }
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            json  = objectMapper.writeValueAsString(addProductJson);
        } catch (IOException e) {
            e.printStackTrace();
        }
        result = addString(json, url);
        ResponseId responseId;
        String id = null;

        try {
            responseId = objectMapper.readValue(result, ResponseId.class);

            id =  responseId.getId();

        } catch (IOException e) {
            System.out.println(e.toString());

        }
        return id;

    }
    public void deleteProduct(int product_id){
        String result;
        String json = "{\"id\":\""+product_id+"\"}";

//        String json = "{\"barcode\":\"1213413345\",\"buy_price\":200000,\"category_id\":5,\"description\":\"Dobra skrętka, nie żadne CCA\",\"name\":\"Skrętka UTP\",\"sell_price\":280000,\"tax_id\":2,\"unit_id\":3}";
        URL url = null;

        try {
            url = new URL("http://hurtownia.mbednarski.pl/products/");
        } catch (MalformedURLException e) {
            System.out.println(e.toString());
        }

        deleteMetod(json,url);

    }

    public void modifyProduct(String name, String cat_id, String description, int sell_price,
                              int buy_price, String tax_id, String unit_id, String barcode, String id){
        String json = null;
        URL url = null;


        AddProductJson addProductJson= new AddProductJson(name,cat_id,
                description,sell_price,buy_price,tax_id,unit_id,barcode);
        addProductJson.setId(id);
        try {
            url = new URL("http://hurtownia.mbednarski.pl/products/");
        } catch (MalformedURLException e) {
            System.out.println(e.toString());
        }
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            json  = objectMapper.writeValueAsString(addProductJson);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(json);
        patchMetod(json,url);
    }

    public String addPlace(String name,String description){
        String result ;
        String json = null;
        URL url = null;
        String id = null;
        ResponseId responseId;



        try {
            url = new URL("http://hurtownia.mbednarski.pl/places/");
        } catch (MalformedURLException e) {
            System.out.println(e.toString());
        }

        AddPlace addPlace= new AddPlace(name,description);
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            json  = objectMapper.writeValueAsString(addPlace);
        } catch (IOException e) {
            e.printStackTrace();
        }
        result = addString(json, url);

        try {
            responseId = objectMapper.readValue(result, ResponseId.class);

            id = responseId.getId();

        } catch (IOException e) {
            System.out.println(e.toString());

        }

        return id;
    }
    public void deletPlace(int place_id){

        String result ;
        String json = "{\"id\":\""+(place_id)+"\"}";

//        String json = "{\"barcode\":\"1213413345\",\"buy_price\":200000,\"category_id\":5,\"description\":\"Dobra skrętka, nie żadne CCA\",\"name\":\"Skrętka UTP\",\"sell_price\":280000,\"tax_id\":2,\"unit_id\":3}";
        URL url = null;

        try {
            url = new URL("http://hurtownia.mbednarski.pl/places/");
        } catch (MalformedURLException e) {
            System.out.println(e.toString());
        }
        deleteMetod(json,url);

    }
    public void changeStock(String id, String quantity){

        String json = null;

        URL url = null;
        ChangeStock changeStock = new ChangeStock(id,quantity);

        try {
            url = new URL("http://hurtownia.mbednarski.pl/stocks/");
        } catch (MalformedURLException e) {

        }
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            json  = objectMapper.writeValueAsString(changeStock);
        } catch (IOException e) {
            e.printStackTrace();
        }


        patchMetod(json,url);




    }
    public void patchMetod(String json,URL url){

        HttpClient httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .build();

        HttpRequest.BodyPublisher jsonPayload = HttpRequest.BodyPublishers.ofString(json);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(String.valueOf(url)))
                .method("PATCH", jsonPayload)
                .header("Content-Type", "application/json")
                .build();

        HttpResponse<String> response = null;
        try {
            response= httpClient.send(request,HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(response);

    }
    public void deleteMetod(String json, URL url){
        String result;
        try {

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod("DELETE");
            OutputStream os = conn.getOutputStream();
            os.write(json.getBytes("UTF-8"));

            os.close();
            // read the response
            InputStream in = new BufferedInputStream(conn.getInputStream());
            result = IOUtils.toString(in, "UTF-8");
            in.close();
            conn.disconnect();
            System.out.println(result);
        } catch (Exception e) {
            System.out.println(e.toString());
        }

    }
    public void addCategory(String name, String description, String parent_id){

        String result;
        String json = null;
        URL url = null;

        AddCategoryJson addCategoryJson= new AddCategoryJson(name,description,parent_id);

        try {
            url = new URL("http://hurtownia.mbednarski.pl/categories/");
        } catch (MalformedURLException e) {
            System.out.println(e.toString());
        }
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            json  = objectMapper.writeValueAsString(addCategoryJson);
        } catch (IOException e) {
            e.printStackTrace();
        }
        result = addString(json, url);
        ResponseId responseId;
        String id = null;

        try {
            responseId = objectMapper.readValue(result, ResponseId.class);

            id =  responseId.getId();

        } catch (IOException e) {
            System.out.println(e.toString());

        }

    }
    public void deleteCategory(String category_id){

        String result ;
        String json = "{\"id\":"+(category_id)+"}";
        System.out.println(json);
        URL url = null;

        try {
            url = new URL("http://hurtownia.mbednarski.pl/categories/");
        } catch (MalformedURLException e) {
            System.out.println(e.toString());
        }
        deleteMetod(json,url);

    }
    public void modifyCategory(String name, String description, String id, String parnet_id){

        String json = null;

        URL url = null;

        AddCategoryJson addCategoryJson= new AddCategoryJson(name,description,parnet_id);
        addCategoryJson.setId(id);
        try {
            url = new URL("http://hurtownia.mbednarski.pl/categories/");
        } catch (MalformedURLException e) {
            System.out.println(e.toString());
        }
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            json  = objectMapper.writeValueAsString(addCategoryJson);
        } catch (IOException e) {
            e.printStackTrace();
        }


        patchMetod(json,url);
    }
    public List<Client> getClientList(){
        URL url = null;
        try {
            url = new URL("http://hurtownia.mbednarski.pl/clients");
        } catch (MalformedURLException ignored) {

        }
        String result = new DbConnection().getString(url) ;

        final ObjectMapper objectMapper = new ObjectMapper();
        List<Client> langList = null;
        try {
            langList = objectMapper.readValue(result, new TypeReference<List<Client>>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<Client> langlis1 =  clientOrderMake(langList);
        return langlis1;
    }
    public void addClient(String name, String surname, String Company, String nip, String email){

        String result;
        String json = null;
        URL url = null;

        Client client= new Client(Company,name,surname,nip,email);

        try {
            url = new URL("http://hurtownia.mbednarski.pl/clients/");
        } catch (MalformedURLException e) {
            System.out.println(e.toString());
        }
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            json  = objectMapper.writeValueAsString(client);
        } catch (IOException e) {
            e.printStackTrace();
        }
        result = addString(json, url);
        ResponseId responseId;
        String id = null;

        try {
            responseId = objectMapper.readValue(result, ResponseId.class);

            id =  responseId.getId();

        } catch (IOException e) {
            System.out.println(e.toString());

        }

    }
    public void deleteClient(String id){

        String result ;
        String json = "{\"id\":"+(id)+"}";
        System.out.println(json);
        URL url = null;

        try {
            url = new URL("http://hurtownia.mbednarski.pl/clients/");
        } catch (MalformedURLException e) {
            System.out.println(e.toString());
        }
        deleteMetod(json,url);

    }
    public void modifyClient(String name, String surname, String Company, String nip, String email, int id){

        String json = null;

        URL url = null;

        Client client= new Client(Company,name,surname,nip,email);
        client.setId(id);
        try {
            url = new URL("http://hurtownia.mbednarski.pl/clients/");
        } catch (MalformedURLException e) {
            System.out.println(e.toString());
        }
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            json  = objectMapper.writeValueAsString(client);
        } catch (IOException e) {
            e.printStackTrace();
        }


        patchMetod(json,url);
    }

    public List<Order> getOrderList(){
        URL url = null;
        try {
            url = new URL("http://hurtownia.mbednarski.pl/orders");
        } catch (MalformedURLException ignored) {

        }
        String result = new DbConnection().getString(url) ;

        final ObjectMapper objectMapper = new ObjectMapper();
        List<Order> langList = null;
        try {
            langList = objectMapper.readValue(result, new TypeReference<List<Order>>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }
        return langList;
    }
    public List<CartItem> getCartItemList(){
        URL url = null;
        try {
            url = new URL("http://hurtownia.mbednarski.pl/cartitems");
        } catch (MalformedURLException ignored) {

        }
        String result = new DbConnection().getString(url) ;

        final ObjectMapper objectMapper = new ObjectMapper();
        List<CartItem> langList = null;
        try {
            langList = objectMapper.readValue(result, new TypeReference<List<CartItem>>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }
        return langList;
    }

    public List<Client> clientOrderMake(List<Client> listClient){
        List<Order> orderList =  getOrderList();
        List<CartItem> cartItemList =  getCartItemList();

        for (Client client :
                listClient) {
            for (Order order :
                   orderList) {
                for (CartItem cartitem :
                        cartItemList ) {
                    if(order.getId().equals(cartitem.getOrder())){

                        order.addCartItem(cartitem);
                    }
                    if((String.valueOf(client.getId()).equals(order.getClient()))){
                    client.addOrder(order);
                }
                }
            }

        }
        return listClient;
    }
    public void modifyOrder(String id, String status){

        String json = "{\"id\":"+(id)+",\"status\":"+status +"}";

        URL url = null;


        try {
            url = new URL("http://hurtownia.mbednarski.pl/orders/");
        } catch (MalformedURLException e) {
            System.out.println(e.toString());
        }
        ObjectMapper objectMapper = new ObjectMapper();

        patchMetod(json,url);
    }
    public void deleteOrder(String id){

        String result ;
        String json = "{\"id\":"+(id)+"}";
        System.out.println(json);
        URL url = null;

        try {
            url = new URL("http://hurtownia.mbednarski.pl/orders/");
        } catch (MalformedURLException e) {
            System.out.println(e.toString());
        }
        deleteMetod(json,url);

    }


    public String addOrder(String client) {

        String result;
        String json = "{\"client\":"+(client)+"}";
        URL url = null;



        try {
            url = new URL("http://hurtownia.mbednarski.pl/orders/");
        } catch (MalformedURLException e) {
            System.out.println(e.toString());
        }
        ObjectMapper objectMapper = new ObjectMapper();

        result = addString(json, url);
        ResponseOrders responseOrders;
        String id = null;

        try {
            responseOrders = objectMapper.readValue(result, ResponseOrders.class);

            id = responseOrders.getId();

        } catch (IOException e) {
            System.out.println(e.toString());

        }

        return id;

    }

    public void addCartItem(String product, String stock, String quantity, String price, String order){

        String result;
        String json = "{\"product\":"+ product +", \"stock\":"+stock+", \"quantity\":"+quantity+", \"price\":"+price+", \"order\":"+order+"}";
        URL url = null;



        try {
            url = new URL("http://hurtownia.mbednarski.pl/cartitems/");
        } catch (MalformedURLException e) {
            System.out.println(e.toString());
        }
        ObjectMapper objectMapper = new ObjectMapper();



        result = addString(json, url);


    }
}