package com.example.demo2;

import com.example.demo2.classes.User;
import com.example.demo2.daos.UserDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import javax.management.relation.Role;
import java.util.Date;

public class AdminController {
    @FXML
    Button predajca, veduci, skladnik, Admin, adding;
   /* @FXML
    Button predajca, veduci, skladnik, Admin, adding, cancelEdit;*/

    @FXML
    TextField nameUserSearch, surnameUserSearch;

    @FXML
    Button cancelAdd = new Button();;

    @FXML
    private TableView<User> tableUsers;

    @FXML
    private TableColumn<User, Long> idOfUsers;

    @FXML
    private TableColumn<User, String> nameOfUsers;

    @FXML
    private TableColumn<User, String> surnameOfUsers;

    @FXML
    private TableColumn<User, Date> dataOfUsers;

    @FXML
    private TableColumn<User, String> roleOfUsers;

    public static User user = null;

    public static Stage addingWindow = new Stage();
    public static Stage editingWindow = new Stage();
    UserDao userDao = DaoFactory.INSTANCE.getUserDao();

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

    @FXML
    public void initialize(){
        ObservableList<User> users = FXCollections.observableList(userDao.getAll());

        idOfUsers.setCellValueFactory(new PropertyValueFactory<User, Long>("idUser"));
        nameOfUsers.setCellValueFactory(new PropertyValueFactory<User, String>("Name"));
        surnameOfUsers.setCellValueFactory(new PropertyValueFactory<User, String>("Surname"));
        dataOfUsers.setCellValueFactory(new PropertyValueFactory<User, Date>("dateOfBirth"));
        roleOfUsers.setCellValueFactory(new PropertyValueFactory<User, String>("role"));

        tableUsers.setItems(users);

        try {
            doubleClickListener();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public void addUser() throws  Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Pridanie.fxml"));
        Parent root = (Parent) fxmlLoader.load();

        addingWindow.setTitle("Add user");
        addingWindow.setScene(new Scene(root));
        addingWindow.show();



        /*addingWindow.setScene(secondScene);

        addingWindow.initOwner(window.getOwner());
        addingWindow.setOnCloseRequest(E ->{
            initialize();
        });
        addingWindow.show();*/
    }


    public void searchUser() throws Exception{   // поиск пользователя (переделать датабазу)
        String nameForSearchUser = "";
        String surnameForSearchUser = "";
        ObservableList<User> users;
        if(!nameUserSearch.getText().equals("") && !surnameUserSearch.getText().equals("")) {
            nameForSearchUser = nameUserSearch.getText();
            surnameForSearchUser = surnameUserSearch.getText();
             users = FXCollections.observableArrayList(userDao.searchUser(nameForSearchUser, surnameForSearchUser));
        }else {
             users = FXCollections.observableList(userDao.getAll());
        }

        idOfUsers.setCellValueFactory(new PropertyValueFactory<User, Long>("idUser"));
        nameOfUsers.setCellValueFactory(new PropertyValueFactory<User, String>("Name"));
        surnameOfUsers.setCellValueFactory(new PropertyValueFactory<User, String>("Surname"));
        dataOfUsers.setCellValueFactory(new PropertyValueFactory<User, Date>("dateOfBirth"));
        roleOfUsers.setCellValueFactory(new PropertyValueFactory<User, String>("role"));

        tableUsers.setItems(users);

        doubleClickListener();
    }

    public void doubleClickListener() throws Exception{
        tableUsers.setRowFactory( tv -> {
            TableRow<User> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    user = row.getItem();
                    try {
                        editingUser(user);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            return row ;
        });
    }

    public void editingUser(User user) throws  Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Uprava.fxml"));
        Parent root = (Parent) fxmlLoader.load();


        editingWindow.setTitle("Edit user");
        editingWindow.setScene(new Scene(root));
        editingWindow.show();
    }
}
