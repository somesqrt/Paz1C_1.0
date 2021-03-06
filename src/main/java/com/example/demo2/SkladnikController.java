package com.example.demo2;

import com.example.demo2.classes.Categories;
import com.example.demo2.classes.Position;
import com.example.demo2.classes.Product;
import com.example.demo2.daos.CategoriesDAO;
import com.example.demo2.daos.PositionDao;
import com.example.demo2.daos.ProductDao;
import com.example.demo2.help.ProduktOnPositionHelp;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SkladnikController {

    @FXML
    TextField nameNewProdukt, manufaktureNewProdukt, EANNewProdukt, tasteNewProdukt, weightNewProdukt, lenghtNewProdukt, heightNewProdukt, widthNewProdukt, priceNewProdukt, piecesNewProdukt, nameNewCategoria, floorNewPosition, numberNewPosition, shelfNewPosition, heightNewPosition, weightNewPosition, lenghtNewPosition, countOnNewPosicia,bearingCapacityNewPosition;

    @FXML
    ComboBox categoriaNewProdukt, productOnNewPosition, posiciaOnNewPosition;

    @FXML
    Button predajca, veduci, Admin, searchProduct;

    @FXML
    private TableView<Product> productsSkladnik;

    @FXML
    private TableColumn<Product, Long> IDProductSkladnik;
    @FXML
    private TableColumn<Product, String> nameSkladnik;
    @FXML
    private TableColumn<Product, String> manufactureSkladnik;
    @FXML
    private TableColumn<Product, String> eanSkladnik;
    @FXML
    private TableColumn<Product, Double> weightSkladnik;
    @FXML
    private TableColumn<Product, String> tasteSkladnik;
    @FXML
    private TableColumn<Product, Double> heightSkladnik;
    @FXML
    private TableColumn<Product, Double> lenghtSkladnik;
    @FXML
    private TableColumn<Product, Double> widthSkladnik;
    @FXML
    private TableColumn<Product, Integer> priceSkladnik;
    @FXML
    private TableColumn<Product, Integer> piecesSkladnik;
    @FXML
    private TableColumn<Product, String> categoriaSkladnik;

    @FXML
    private TableView<Categories> categoriesTableSkladnik;

    @FXML
    private TableColumn<Categories, Long> IDCategoriesSkladnik;
    @FXML
    private TableColumn<Categories, String> categoriesNamesSkladnik;

    @FXML
    private TableView<Position> positionTableSlkladnik;

    @FXML
    private TableColumn<Position, Long> IDPositionsSkladnik;
    @FXML
    private TableColumn<Position, Integer> floorPositionsSkladnik;
    @FXML
    private TableColumn<Position, Integer> positionNumberPositionsSkladnik;
    @FXML
    private TableColumn<Position, String> shelfPositionsSkladnik;
    @FXML
    private TableColumn<Position, Double> heightPositionsSkladnik;
    @FXML
    private TableColumn<Position, Double> weightPositionsSkladnik;
    @FXML
    private TableColumn<Position, Double> lenghtPositionsSkladnik;
    @FXML
    private TableColumn<Position, Double> bearningPositionsSkladnik;

    @FXML
    private TableView<ProduktOnPositionHelp> ordersOnPositionTable;

    @FXML
    private TableColumn<ProduktOnPositionHelp, String> productOrderOnPositionSkladnik;
    @FXML
    private TableColumn<ProduktOnPositionHelp, String> poziciaOrderOnPositionSkladnik;
    @FXML
    private TableColumn<ProduktOnPositionHelp, Integer> countOrderOnPositionSkladnik;

    Categories categoriaForDelete;

    CategoriesDAO categoriesDAO = DaoFactory.INSTANCE.getcategoriesDAO();
    ProductDao productDao = DaoFactory.INSTANCE.getProductDao();
    PositionDao positionDao = DaoFactory.INSTANCE.getPositionDao();

    public void changePasswordCurrent() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("UpdatePassController.fxml"));
        Parent root = (Parent) fxmlLoader.load();

        LoginController.changeWindow.setTitle("Add user");
        LoginController.changeWindow.setScene(new Scene(root));
        LoginController.changeWindow.show();
    }

    public void handlerPredajca() throws  Exception{
        if(LoginController.currentUser.getRole().getIdRole() == 2){
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("predajca.fxml"));
            Stage window = (Stage) predajca.getScene().getWindow();
            window.setScene(new Scene(fxmlLoader.load(), 1200, 800));
        }else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Incorrect data");
            alert.setHeaderText(null);
            alert.setContentText("Permision denied");
            alert.show();
        }



    }

    public void hendlerVeduci() throws  Exception{
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

    public void hendlerSkladnik() throws  Exception{

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Skladnik.fxml"));
        LoginController.stage.getScene().getWindow();
        LoginController.stage.setScene(new Scene(fxmlLoader.load(), 1200, 800));

    }

    public void hendlerAdmin() throws  Exception{
        if(LoginController.currentUser.getRole().getIdRole() == 2){
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Admin.fxml"));
        Stage window =(Stage) Admin.getScene().getWindow();
        window.setScene(new Scene(fxmlLoader.load(), 1200,800));
    }else{
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Incorrect data");
        alert.setHeaderText(null);
        alert.setContentText("Permision denied");
        alert.show();
        }
    }

    public void initialize(){
        ObservableList<String> categories = FXCollections.observableList(categoriesDAO.getALlNames());
        categories.add("ALL CATEGORIES");
        if (categoriaNewProdukt != null) {
            categoriaNewProdukt.setItems(categories);
        }

        ObservableList<String> productsNames = FXCollections.observableList(productDao.getALlNames());
        System.out.println(productsNames);
        if (productOnNewPosition != null) {
            productOnNewPosition.setItems(productsNames);
        }

        ObservableList<String> positionNames = FXCollections.observableList(positionDao.getALlNames());
        if (posiciaOnNewPosition != null) {
            posiciaOnNewPosition.setItems(positionNames);
        }



        ProductDao productDao = DaoFactory.INSTANCE.getProductDao();
        CategoriesDAO categoriesDAO = DaoFactory.INSTANCE.getcategoriesDAO();
        PositionDao positionDao = DaoFactory.INSTANCE.getPositionDao();


        List<Product> productsWithoutNormalCategories = productDao.getAll();



        ObservableList<Product> products = FXCollections.observableArrayList(productsWithoutNormalCategories);
        IDProductSkladnik.setCellValueFactory(new PropertyValueFactory<Product, Long>("idProduct"));
        nameSkladnik.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
        manufactureSkladnik.setCellValueFactory(new PropertyValueFactory<Product, String>("manufacture"));
        eanSkladnik.setCellValueFactory(new PropertyValueFactory<Product, String>("EAN"));
        weightSkladnik.setCellValueFactory(new PropertyValueFactory<Product, Double>("weight"));
        tasteSkladnik.setCellValueFactory(new PropertyValueFactory<Product, String>("taste"));
        heightSkladnik.setCellValueFactory(new PropertyValueFactory<Product, Double>("height"));
        lenghtSkladnik.setCellValueFactory(new PropertyValueFactory<Product, Double>("length"));
        widthSkladnik.setCellValueFactory(new PropertyValueFactory<Product, Double>("width"));
        priceSkladnik.setCellValueFactory(new PropertyValueFactory<Product, Integer>("price"));
        piecesSkladnik.setCellValueFactory(new PropertyValueFactory<Product, Integer>("piecesInPackage"));
        categoriaSkladnik.setCellValueFactory(new PropertyValueFactory<Product, String>("Categories"));

        productsSkladnik.setItems(products);


        ObservableList<Categories> categoriesForTable= FXCollections.observableList(categoriesDAO.getAll());

        IDCategoriesSkladnik.setCellValueFactory(new PropertyValueFactory<Categories, Long>("idCategories"));
        categoriesNamesSkladnik.setCellValueFactory(new PropertyValueFactory<Categories, String>("categoria"));

        categoriesTableSkladnik.setItems(categoriesForTable);

        categoriesTableSkladnik.setOnMouseClicked((MouseEvent event) -> {
            if(event.getButton().equals(MouseButton.PRIMARY)){
                categoriaForDelete = categoriesTableSkladnik.getSelectionModel().getSelectedItem();
            }
        });


        ObservableList<Position> positionForTable= FXCollections.observableList(positionDao.getAll());

        IDPositionsSkladnik.setCellValueFactory(new PropertyValueFactory<Position, Long>("idPosiiton"));
        floorPositionsSkladnik.setCellValueFactory(new PropertyValueFactory<Position, Integer>("floor"));
        positionNumberPositionsSkladnik.setCellValueFactory(new PropertyValueFactory<Position, Integer>("positionNumber"));
        shelfPositionsSkladnik.setCellValueFactory(new PropertyValueFactory<Position, String>("shelf"));
        heightPositionsSkladnik.setCellValueFactory(new PropertyValueFactory<Position, Double>("height"));
        weightPositionsSkladnik.setCellValueFactory(new PropertyValueFactory<Position, Double>("weight"));
        lenghtPositionsSkladnik.setCellValueFactory(new PropertyValueFactory<Position, Double>("length"));
        bearningPositionsSkladnik.setCellValueFactory(new PropertyValueFactory<Position, Double>("BearingCapacity"));

        positionTableSlkladnik.setItems(positionForTable);

        List<ProduktOnPositionHelp> BadNames = positionDao.getAllInfoAboutOrderOnPosition();
        ArrayList<ProduktOnPositionHelp> NiceName = new ArrayList<>();

       /* for (int i = 0; i < BadNames.size(); i++) {
            NiceName.add(new ProduktOnPositionHelp(
                    productDao.getbyId(Long.valueOf(BadNames.get(i).getName())).getName(),
                    String.valueOf(positionDao.getById(Long.valueOf(BadNames.get(i).getNumber())).getPositionNumber()),
                    BadNames.get(i).getCount()

            ));
        }*/

        ObservableList<ProduktOnPositionHelp> infoAboutProductOnPosition= FXCollections.observableList(BadNames);

        productOrderOnPositionSkladnik.setCellValueFactory(new PropertyValueFactory<ProduktOnPositionHelp, String>("name"));
        poziciaOrderOnPositionSkladnik.setCellValueFactory(new PropertyValueFactory<ProduktOnPositionHelp, String>("number"));
        countOrderOnPositionSkladnik.setCellValueFactory(new PropertyValueFactory<ProduktOnPositionHelp, Integer>("count"));

        ordersOnPositionTable.setItems(infoAboutProductOnPosition);
    }

    public void addProduct(){
        if(nameNewProdukt.getText().equals("") || manufaktureNewProdukt.getText().equals("") || EANNewProdukt.getText().equals("") || weightNewProdukt.getText().equals("") || lenghtNewProdukt.getText().equals("")|| heightNewProdukt.getText().equals("") || widthNewProdukt.getText().equals("") || priceNewProdukt.getText().equals("") || piecesNewProdukt.getText().equals("") || categoriaNewProdukt.getValue().equals("")){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Incorrect data");
            alert.setHeaderText(null);
            alert.setContentText("Fill in all required fields.");
            alert.show();
        }else{
            CategoriesDAO categoriesDAO = DaoFactory.INSTANCE.getcategoriesDAO();
            Categories categoria = categoriesDAO.getbyID(categoriesDAO.getByName(String.valueOf(categoriaNewProdukt.getValue())));
            Product newProduct = new Product(
                    nameNewProdukt.getText(),
                    manufaktureNewProdukt.getText(),
                    EANNewProdukt.getText(),
                    Double.valueOf(weightNewProdukt.getText()),
                    Double.valueOf(heightNewProdukt.getText()),
                    Double.valueOf(lenghtNewProdukt.getText()),
                    Double.valueOf(widthNewProdukt.getText()),
                    Integer.valueOf(priceNewProdukt.getText()),
                    Integer.valueOf(piecesNewProdukt.getText()),
                    categoria
            );
            ProductDao productDao = DaoFactory.INSTANCE.getProductDao();
            productDao.save(newProduct);
        }
    }

    public void addCategoria(){
        if (nameNewCategoria.getText().equals("")){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Incorrect data");
            alert.setHeaderText(null);
            alert.setContentText("Fill in all required fields.");
            alert.show();
        }else{
            CategoriesDAO categoriesDAO = DaoFactory.INSTANCE.getcategoriesDAO();
            categoriesDAO.save(new Categories(
                    nameNewCategoria.getText()
            ));
        }
    }

    public void addPosition(){
        if(floorNewPosition.getText().equals("") || numberNewPosition.getText().equals("") || shelfNewPosition.getText().equals("") || heightNewPosition.getText().equals("") || weightNewPosition.getText().equals("") || lenghtNewPosition.getText().equals("")){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Incorrect data");
            alert.setHeaderText(null);
            alert.setContentText("Fill in all required fields.");
            alert.show();
        }else{
            Position position = new Position(
                    Integer.valueOf(floorNewPosition.getText()),
                    Integer.valueOf(numberNewPosition.getText()),
                    shelfNewPosition.getText(),
                    Double.valueOf(heightNewPosition.getText()),
                    Double.valueOf(weightNewPosition.getText()),
                    Double.valueOf(lenghtNewPosition.getText()),
                    Double.valueOf(bearingCapacityNewPosition.getText())
            );
            PositionDao positionDao = DaoFactory.INSTANCE.getPositionDao();
            positionDao.save(position);
        }
    }

    public void addOnPosition(){
        if(productOnNewPosition.getValue().equals("") || posiciaOnNewPosition.getValue().equals("") || countOnNewPosicia.getText().equals("")){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Incorrect data");
            alert.setHeaderText(null);
            alert.setContentText("Fill in all required fields.");
            alert.show();
        }else{
            PositionDao positionDao = DaoFactory.INSTANCE.getPositionDao();
            ProductDao productDao = DaoFactory.INSTANCE.getProductDao();
            positionDao.setProductOnPosition(
                    productDao.getByName(String.valueOf(productOnNewPosition.getValue())),
                    positionDao.getByName(String.valueOf(posiciaOnNewPosition.getValue())),
                    Integer.parseInt(countOnNewPosicia.getText())
                    );
        }


    }

    public void deleteCategori(){
        ProductDao productDao = DaoFactory.INSTANCE.getProductDao();
        List<Product> products = productDao.getbyCategory(categoriaForDelete);
        if(products.size() == 0 ){
            CategoriesDAO categoriesDAO = DaoFactory.INSTANCE.getcategoriesDAO();
            categoriesDAO.delete(categoriaForDelete.getIdCategories());
        }else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);

            alert.setTitle("Incorrect data");
            alert.setHeaderText(null);
            alert.setContentText("Some product have this categori");
            alert.show();
        }
    }
}
