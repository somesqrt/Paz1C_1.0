package com.example.demo2;

import com.example.demo2.classes.*;
import com.example.demo2.daos.CategoriesDAO;
import com.example.demo2.daos.OrderDao;
import com.example.demo2.daos.ProductDao;
import com.example.demo2.daos.UserDao;
import com.example.demo2.help.AllOrderHelp;
import com.example.demo2.help.ProductInOrderHelp;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class PredajcaController {

    public static Product product = null;
    public static AllOrderHelp productInOrder = null;
    public static NowOrder nowOrder = null;
    ProductDao productDao = DaoFactory.INSTANCE.getProductDao();
    @FXML
    Button predajca, veduci, Admin, searchProduct;

    @FXML
    TextField producntName, productEAN, prodactSKU, PersonName;

    @FXML
    Text textID;

    @FXML
    ChoiceBox producntCategory;

    @FXML
    private TableView<AllOrderHelp> allOrder;

    @FXML
    private TableColumn<Order, Long> idOrderTable;
    @FXML
    private TableColumn<Order, String> nameOrderTable;
    @FXML
    private TableColumn<Order, Double> sumOrderTable;
    @FXML
    private TableColumn<Order, String> orderStatusOrderTable;
    @FXML
    private TableColumn<Order, String> salesManOrderTable;

    @FXML
    private TableView<ProductInOrderHelp> editingOrder;

    @FXML
    private TableColumn<ProductInOrderHelp, String> nameProductOrderTable;
    @FXML
    private TableColumn<ProductInOrderHelp, String> countOrderTable;

    @FXML
    private TableView<Product> tableProducts;

    @FXML
    private TableColumn<Product, String> nameOfProducnt;

    @FXML
    private TableColumn<Product, String> EANOfProduct;

    @FXML
    private TableColumn<Product, Date> tasteOfProduct;

    @FXML
    private TableColumn<Product, String> manufactureOfProduct;

    @FXML
    private TableColumn<Product, String> countProductPieces;

    @FXML
    private TableColumn<Product, String> priceOfProduct;

    @FXML
    private TableView<NowOrder> nowOrderTable;

    @FXML
    private TableColumn<NowOrder, String> nameOfProducntInOrder;

    @FXML
    private TableColumn<NowOrder, String> countOfProducntInOrder;

    @FXML
    private TableColumn<NowOrder, Integer> priceOfProducntInOrder;

    OrderDao orderDao = DaoFactory.INSTANCE.getorderDao();

    CategoriesDAO categoriesDAO = DaoFactory.INSTANCE.getcategoriesDAO();
    ArrayList<NowOrder> ordersDetail = new ArrayList<NowOrder>();

    ObservableList<NowOrder> orders;
    ObservableList<ProductInOrderHelp> tableProductsInOrder;
    ObservableList<ProductInOrder> productsInOrder;
    public static int count = 0;
    public static Stage windowCount = new Stage();



    public void changePasswordCurrent() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("UpdatePassController.fxml"));
        Parent root = (Parent) fxmlLoader.load();

        LoginController.changeWindow.setTitle("Add user");
        LoginController.changeWindow.setScene(new Scene(root));
        LoginController.changeWindow.show();
    }

    public void handlerPredajca() throws Exception {

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("predajca.fxml"));
        Stage window = (Stage) predajca.getScene().getWindow();
        window.setScene(new Scene(fxmlLoader.load(), 1200, 800));


    }

    public void hendlerVeduci() throws Exception {
        if(LoginController.currentUser.getRole().getIdRole() == 2){
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Veduci.fxml"));
        LoginController.stage.getScene().getWindow();
        LoginController.stage.setScene(new Scene(fxmlLoader.load(), 1200, 800));
        }else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Incorrect data");
            alert.setHeaderText(null);
            alert.setContentText("Permision denied");
            alert.show();
        }
    }

    public void hendlerSkladnik() throws Exception {
            if(LoginController.currentUser.getRole().getIdRole() == 2){
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Skladnik.fxml"));
        LoginController.stage.getScene().getWindow();
        LoginController.stage.setScene(new Scene(fxmlLoader.load(), 1200, 800));
            }else{
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Incorrect data");
                alert.setHeaderText(null);
                alert.setContentText("Permision denied");
                alert.show();
            }
    }

    public void hendlerAdmin() throws Exception {
                if(LoginController.currentUser.getRole().getIdRole() == 2){
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Admin.fxml"));
        Stage window = (Stage) Admin.getScene().getWindow();
        window.setScene(new Scene(fxmlLoader.load(), 1200, 800));
                }else{
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Incorrect data");
                    alert.setHeaderText(null);
                    alert.setContentText("Permision denied");
                    alert.show();
                }
    }

    public void initialize() {

        ObservableList<String> categories = FXCollections.observableList(categoriesDAO.getALlNames());
        categories.add("ALL CATEGORIES");
        if (producntCategory != null) {
            producntCategory.setItems(categories);
        }


        ObservableList<Product> products = FXCollections.observableList(productDao.getAll());

        nameOfProducnt.setCellValueFactory(new PropertyValueFactory<Product, String>("Name"));
        EANOfProduct.setCellValueFactory(new PropertyValueFactory<Product, String>("EAN"));
        tasteOfProduct.setCellValueFactory(new PropertyValueFactory<Product, Date>("taste"));
        manufactureOfProduct.setCellValueFactory(new PropertyValueFactory<Product, String>("Manufacture"));
        countProductPieces.setCellValueFactory(new PropertyValueFactory<Product, String>("piecesInPackage"));
        priceOfProduct.setCellValueFactory(new PropertyValueFactory<Product, String>("Categories"));

        tableProducts.setItems(products);

        try {
            doubleClickListener();
        } catch (Exception e) {
            e.printStackTrace();
        }

        ObservableList<Order> orders = FXCollections.observableList(orderDao.getAll());
        ArrayList<AllOrderHelp> orderHelps = new ArrayList<>();
        for (int i = 0; i < orders.size(); i++) {
            orderHelps.add(new AllOrderHelp(
                    orders.get(i).getIdOrder(),
                    orders.get(i).getName(),
                    orders.get(i).getSumm(),
                    orders.get(i).getOrderStatus(),
                    orders.get(i).getSalesMan().getName()
            ));
        }
        ObservableList<AllOrderHelp> tableOrderHelps = FXCollections.observableList(orderHelps);

        idOrderTable.setCellValueFactory(new PropertyValueFactory<Order, Long>("idOrder"));
        nameOrderTable.setCellValueFactory(new PropertyValueFactory<Order, String>("Name"));
        sumOrderTable.setCellValueFactory(new PropertyValueFactory<Order, Double>("Summ"));
        orderStatusOrderTable.setCellValueFactory(new PropertyValueFactory<Order, String>("OrderStatus"));
        salesManOrderTable.setCellValueFactory(new PropertyValueFactory<Order, String>("SalesMan"));


        allOrder.setItems(tableOrderHelps);
        try {
            doubleClickListener2();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void doubleClickListener2() {
        allOrder.setRowFactory(tv -> {
            TableRow<AllOrderHelp> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    productInOrder = row.getItem();
                    try {
                        fullInfo();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            return row;
        });
    }

    public void fullInfo() {

        textID.setText(String.valueOf(productInOrder.getIdOrder()));

        System.out.println(productInOrder.getIdOrder());
        productsInOrder = FXCollections.observableList(orderDao.getAllProductsInOrder(productInOrder.getIdOrder()));

        ArrayList<ProductInOrderHelp> orderHelps = new ArrayList<>();
        for (int i = 0; i < productsInOrder.size(); i++) {
            orderHelps.add(new ProductInOrderHelp(
                    productDao.getbyId(productsInOrder.get(i).getIdproduct()).getName(),
                    String.valueOf(productsInOrder.get(i).getCount())
            ));
        }

        tableProductsInOrder = FXCollections.observableList(orderHelps);

        nameProductOrderTable.setCellValueFactory(new PropertyValueFactory<ProductInOrderHelp, String>("name"));


        countOrderTable.setCellValueFactory(
                new PropertyValueFactory<ProductInOrderHelp, String>("count"));
        countOrderTable.setCellFactory(TextFieldTableCell.forTableColumn());

        countOrderTable.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<ProductInOrderHelp, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<ProductInOrderHelp, String> t) {
                        ((ProductInOrderHelp) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setCount(t.getNewValue());
                    }
                }
        );


        editingOrder.setItems(tableProductsInOrder);

    }


    public void doubleClickListener() throws Exception {
        tableProducts.setRowFactory(tv -> {
            TableRow<Product> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    product = row.getItem();
                    try {
                        addNowOrder();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            return row;
        });
    }

    public void addNowOrder() throws Exception {

        NowOrder newOrder = new NowOrder(product.getName(), "1", product.getPrice());
        NowOrder fordeleted = newOrder;
        if (orders != null) {
            while (orders.contains(newOrder)) {
                newOrder.setCount(String.valueOf(Integer.valueOf(newOrder.getCount()) + 1));
            }
            if (orders.contains(fordeleted)) {
                orders.remove(fordeleted);
            }
        }


        ordersDetail.add(newOrder);
        orders = FXCollections.observableList(ordersDetail);
        nameOfProducntInOrder.setCellValueFactory(new PropertyValueFactory<NowOrder, String>("Name"));

        countOfProducntInOrder.setCellValueFactory(
                new PropertyValueFactory<NowOrder, String>("count"));
        countOfProducntInOrder.setCellFactory(TextFieldTableCell.forTableColumn());

        countOfProducntInOrder.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<NowOrder, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<NowOrder, String> t) {
                        ((NowOrder) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setCount(t.getNewValue());
                    }
                }
        );


        priceOfProducntInOrder.setCellValueFactory(new PropertyValueFactory<NowOrder, Integer>("price"));

        nowOrderTable.setItems(orders);
        /*try {
            doubleClickListenerForOrder();
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }

    public void cancelOrder() {

        orders.removeAll();
        nowOrderTable.getItems().clear();

    }

    ;

    public void saveOrder() {
        double sum = 0;
        HashMap<Product, Integer> productsInOrderNow = new HashMap<Product, Integer>();
        for (int i = 0; i < orders.size(); i++) {
            sum += orders.get(i).getPrice();
            productsInOrderNow.put(productDao.getByName(orders.get(i).getName()), Integer.valueOf(orders.get(i).getCount()));

        }


        String timeStamp = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(Calendar.getInstance().getTime());
        String s = PersonName.getText();
        Order order = new Order(
                PersonName.getText(),
                sum,
                "Created",
                LoginController.currentUser,
                productsInOrderNow
        );
        orderDao.createOrder(order);
        orders.removeAll();
        nowOrderTable.getItems().clear();
        initialize();

    }

    ;

    public void searchProduct() throws Exception {
        String nameOfProductSearch = "";
        String EANOfProductSearch = "";
        //String SKUOfProductSearch = "";
        String categoriOfProductSearch = "";

        ObservableList<Product> products;

        nameOfProductSearch = "\'" + producntName.getText() + "\'";
        EANOfProductSearch = "\'" + productEAN.getText() + "\'";
        categoriOfProductSearch = "\"" + producntCategory.getValue() + "\"";

        if (!categoriOfProductSearch.equals("\"ALL CATEGORIES\"")) {
            categoriOfProductSearch = String.valueOf(producntCategory.getValue());
            categoriOfProductSearch = String.valueOf(categoriesDAO.getByName(categoriOfProductSearch));
        }


        if (!nameOfProductSearch.equals("") || !EANOfProductSearch.equals("") || !categoriOfProductSearch.equals("")) {
            if (nameOfProductSearch.equals("''")) {
                nameOfProductSearch = "name";
            }
            if (EANOfProductSearch.equals("''")) {
                EANOfProductSearch = "EAN";
            }
            if (categoriOfProductSearch.equals("\"null\"")) {
                categoriOfProductSearch = "Categories";
            }
            if (categoriOfProductSearch.equals("\"ALL CATEGORIES\"")) {
                categoriOfProductSearch = "Categories";
            }
            products = FXCollections.observableList(productDao.searchProduct(nameOfProductSearch, EANOfProductSearch, categoriOfProductSearch));
        } else {
            products = FXCollections.observableList(productDao.getAll());
        }

        nameOfProducnt.setCellValueFactory(new PropertyValueFactory<Product, String>("Name"));
        EANOfProduct.setCellValueFactory(new PropertyValueFactory<Product, String>("EAN"));
        tasteOfProduct.setCellValueFactory(new PropertyValueFactory<Product, Date>("taste"));
        manufactureOfProduct.setCellValueFactory(new PropertyValueFactory<Product, String>("Manufacture"));
        countProductPieces.setCellValueFactory(new PropertyValueFactory<Product, String>("piecesInPackage"));
        priceOfProduct.setCellValueFactory(new PropertyValueFactory<Product, String>("Categories"));

        tableProducts.setItems(products);

        doubleClickListener();

    }

    public void deleteOrder() throws Exception {
        orderDao.deleteOrderProduct(productInOrder.getIdOrder());
        orderDao.deleteOrder(productInOrder.getIdOrder());
        tableProductsInOrder.removeAll();
        editingOrder.getItems().clear();
        initialize();
    }


    public void saveEditingOrder() throws Exception {
        UserDao userDao = DaoFactory.INSTANCE.getUserDao();
        ArrayList<ProductInOrder> productInOrdersToUpdateArray = new ArrayList<>();
        double suma = 0;
        for (int i = 0; i < productsInOrder.size(); i++) {

            ProductInOrder toUpdate = new ProductInOrder(
                    productsInOrder.get(i).getId(),
                    productDao.getbyId(productsInOrder.get(i).getIdproduct()),
                    Double.valueOf(tableProductsInOrder.get(i).getCount())

            );
            productInOrdersToUpdateArray.add(toUpdate);
            double c = Double.valueOf(tableProductsInOrder.get(i).getCount());
            double p = Double.valueOf(productDao.getbyId(productsInOrder.get(i).getIdproduct()).getPrice());

            suma += Double.valueOf(tableProductsInOrder.get(i).getCount()) * Double.valueOf(productDao.getbyId(productsInOrder.get(i).getIdproduct()).getPrice());

        }
        Order order = new Order(
                productInOrder.getIdOrder(),
                productInOrder.getName(),
                suma,
                productInOrder.getOrderStatus(),
                userDao.getByName(productInOrder.getSalesMan())

        );

        orderDao.save(order, productInOrdersToUpdateArray, productInOrder.getIdOrder());

        tableProductsInOrder.removeAll();
        editingOrder.getItems().clear();
    }
}
