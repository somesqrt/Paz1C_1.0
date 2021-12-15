package com.example.demo2;

import com.example.demo2.classes.Product;
import com.example.demo2.classes.ProductInOrder;
import com.example.demo2.daos.OrderDao;
import com.example.demo2.daos.PositionDao;
import com.example.demo2.daos.ProductDao;
import com.example.demo2.help.ProductInOrderHelp;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.stage.WindowEvent;

import java.io.ObjectInputFilter;
import java.util.ArrayList;
import java.util.List;

public class VeduciPackOrderController {
    @FXML
    private TableView<ProductInOrderHelp> ProductAndPosition;

    @FXML
    private TableColumn<ProductInOrderHelp, String> ProductInTableForSearch;
    @FXML
    private TableColumn<ProductInOrderHelp, String> countInTableForSearch;
    @FXML
    private TableColumn<ProductInOrderHelp, String> poziciaInTableForSearch;

    OrderDao orderDao = DaoFactory.INSTANCE.getorderDao();

    public static ProductInOrderHelp productInOrderHelp = null;

    private ArrayList<ProductInOrderHelp> justTransformation = new ArrayList<>();
    private ArrayList<ProductInOrderHelp> added = new ArrayList<>();


    public void initialize() {
        List<ProductInOrder> productInOrders = orderDao.getAllProductsInOrder(VeducyController.order.getIdOrder());
        ArrayList<ProductInOrderHelp> productInOrderHelps = new ArrayList<>();
        for (int i = 0; i < productInOrders.size(); i++) {
            productInOrderHelps.add(orderDao.getByOrder(String.valueOf(productInOrders.get(i).getIdproduct())));
        }


        ProductDao productDao = DaoFactory.INSTANCE.getProductDao();
        PositionDao positionDao = DaoFactory.INSTANCE.getPositionDao();

        for (int i = 0; i < productInOrderHelps.size(); i++) {
            justTransformation.add(new ProductInOrderHelp(
                    productDao.getbyId(Long.valueOf(productInOrderHelps.get(i).getName())).getName(),
                    productInOrderHelps.get(i).getCount(),
                    String.valueOf(positionDao.getById(Long.valueOf(productInOrderHelps.get(i).getPozicia())).getPositionNumber())
            ));
        }
        
        ObservableList<ProductInOrderHelp> productAndPosition = FXCollections.observableList(justTransformation);

        ProductInTableForSearch.setCellValueFactory(new PropertyValueFactory<ProductInOrderHelp, String>("name"));
        poziciaInTableForSearch.setCellValueFactory(new PropertyValueFactory<ProductInOrderHelp, String>("pozicia"));
        countInTableForSearch.setCellValueFactory(new PropertyValueFactory<ProductInOrderHelp, String>("count"));

        ProductAndPosition.setItems(productAndPosition);

        try {
            doubleClickListener();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    ObjectProperty<ProductInOrderHelp> criticalPerson = new SimpleObjectProperty<>();
    private void doubleClickListener() {
        ProductAndPosition.setRowFactory( tv -> {
            TableRow<ProductInOrderHelp> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {

                    BooleanBinding critical = row.itemProperty().isNotEqualTo(criticalPerson);
                    row.styleProperty().bind(Bindings.when(critical)
                        .then("-fx-background-color: #81c483 ;")
                        .otherwise(""));
                    added.add(row.getItem());

                }
            });
            return row ;
        });
    }

    public void backFromDump() {
        VeducyController.dumpingWindow.fireEvent(new WindowEvent(AdminController.editingWindow, WindowEvent.WINDOW_CLOSE_REQUEST));
    }

    public void nedotatokFromDump() {
        orderDao.update(VeducyController.order.getIdOrder(),"Nedostatok");
        VeducyController.dumpingWindow.fireEvent(new WindowEvent(AdminController.editingWindow, WindowEvent.WINDOW_CLOSE_REQUEST));
    }

    public void doneFromDump() {
        if(added.size() == justTransformation.size()){
            orderDao.update(VeducyController.order.getIdOrder(),"Collect");
            VeducyController.dumpingWindow.fireEvent(new WindowEvent(AdminController.editingWindow, WindowEvent.WINDOW_CLOSE_REQUEST));
        }else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);

            alert.setTitle("Incorrect data");
            alert.setHeaderText(null);
            alert.setContentText("Not all products in the order have been collected.");
            alert.show();
        }
    }


}
