package com.example.demo2;

import com.example.demo2.classes.Order;
import com.example.demo2.classes.Product;
import com.example.demo2.classes.ProductInOrder;
import com.example.demo2.daos.CategoriesDAO;
import com.example.demo2.daos.OrderDao;
import com.example.demo2.help.AllOrderHelp;
import com.example.demo2.help.ProductInOrderHelp;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class VeducyController {

    @FXML
    private TextField idForVeduci, nameForVeduci;

    @FXML
    private ComboBox categoriesForVeduci;

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


    public static Stage dumpingWindow = new Stage();

    @FXML
    Button predajca, veduci, Admin, searchProduct;

    ObservableList<Order> tableOrderHelpsFromDb;

    OrderDao orderDao = DaoFactory.INSTANCE.getorderDao();


    public static AllOrderHelp order = null;

    public void handlerPredajca() throws  Exception{

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("predajca.fxml"));
        Stage window = (Stage) predajca.getScene().getWindow();
        window.setScene(new Scene(fxmlLoader.load(), 1200, 800));


    }

    public void hendlerVeduci() throws  Exception{

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Veduci.fxml"));
        LoginController.stage.getScene().getWindow();
        LoginController.stage.setScene(new Scene(fxmlLoader.load(), 1200, 800));

    }

    public void hendlerSkladnik() throws  Exception{

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Skladnik.fxml"));
        LoginController.stage.getScene().getWindow();
        LoginController.stage.setScene(new Scene(fxmlLoader.load(), 1200, 800));

    }

    public void hendlerAdmin() throws  Exception{

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Admin.fxml"));
        Stage window =(Stage) Admin.getScene().getWindow();
        window.setScene(new Scene(fxmlLoader.load(), 1200,800));

    }

    public void initialize() throws Exception{
        tableOrderHelpsFromDb = FXCollections.observableList(orderDao.getAll());
        ArrayList<AllOrderHelp> OrderHelps = new ArrayList<>();

        for (int i = 0; i < tableOrderHelpsFromDb.size(); i++) {

            OrderHelps.add( new AllOrderHelp(
                    tableOrderHelpsFromDb.get(i).getIdOrder(),
                    tableOrderHelpsFromDb.get(i).getName(),
                    tableOrderHelpsFromDb.get(i).getSumm(),
                    tableOrderHelpsFromDb.get(i).getOrderStatus(),
                    tableOrderHelpsFromDb.get(i).getSalesMan().getName()
            ));
        }

        ObservableList<AllOrderHelp> tableOrderHelps = FXCollections.observableList(OrderHelps);
        idOrderTable.setCellValueFactory(new PropertyValueFactory<Order, Long>("idOrder"));
        nameOrderTable.setCellValueFactory(new PropertyValueFactory<Order, String>("Name"));
        sumOrderTable.setCellValueFactory(new PropertyValueFactory<Order, Double>("Summ"));
        orderStatusOrderTable.setCellValueFactory(new PropertyValueFactory<Order, String>("OrderStatus"));
        salesManOrderTable.setCellValueFactory(new PropertyValueFactory<Order, String>("SalesMan"));


        allOrder.setItems(tableOrderHelps);
        try {
            doubleClickListener();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void doubleClickListener() {
        allOrder.setRowFactory( tv -> {
            TableRow<AllOrderHelp> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    order = row.getItem();
                    try {
                        dumpingOrder();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            return row ;
        });
    }

    public void dumpingOrder() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("VeduciPackOrder.fxml"));
        Parent root = (Parent) fxmlLoader.load();


        dumpingWindow.setTitle("Edit user");
        dumpingWindow.setScene(new Scene(root));

        dumpingWindow.show();
    }

    public void searchOrderVeduci(){
        ObservableList<AllOrderHelp> orders = null;


        String idForSearchOrderVeduci = "";
        String nameForSearchOrderVeduci = "";


        idForSearchOrderVeduci = "\'" + idForVeduci.getText() + "\'";
        nameForSearchOrderVeduci = "\'" + nameForVeduci.getText() + "\'";



        if(!idForSearchOrderVeduci.equals("") || !nameForSearchOrderVeduci.equals("")) {
            if(idForSearchOrderVeduci.equals("''")){
                idForSearchOrderVeduci = "idOrder";
            }
            if(nameForSearchOrderVeduci.equals("''")){
                nameForSearchOrderVeduci = "Name";
            }
            orders = FXCollections.observableList(orderDao.getByFilters(idForSearchOrderVeduci, nameForSearchOrderVeduci));
        }/*else{
            orders = FXCollections.observableList(orderDao.getAll());
        }*/

        idOrderTable.setCellValueFactory(new PropertyValueFactory<Order, Long>("idOrder"));
        nameOrderTable.setCellValueFactory(new PropertyValueFactory<Order, String>("Name"));
        sumOrderTable.setCellValueFactory(new PropertyValueFactory<Order, Double>("Summ"));
        orderStatusOrderTable.setCellValueFactory(new PropertyValueFactory<Order, String>("OrderStatus"));
        salesManOrderTable.setCellValueFactory(new PropertyValueFactory<Order, String>("SalesMan"));

        allOrder.setItems(orders);
        try {
            doubleClickListener();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
