package com.example.demo2;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////КОНТРОЛЛЕР ОТВЕЧАЮЩИЙ ТОЛЬКО ЗА ПЕРЕКЛЮЧЕНИЕ//////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

public class MainController {
    @FXML
    TextField editName, editSurname, editDate, editPassword, editLogin;

    @FXML
    ComboBox editRole;



    @FXML
    TextField nameUserSearch, surnameUserSearch;

/*

    public void handlerPredajca() throws Exception {

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("predajca.fxml"));
        Stage window = (Stage) predajca.getScene().getWindow();
        window.setScene(new Scene(fxmlLoader.load(), 1200, 800));

    }

    public void hendlerVeduci() throws Exception {

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Veduci.fxml"));
        Stage window = (Stage) veduci.getScene().getWindow();
        window.setScene(new Scene(fxmlLoader.load(), 1200, 800));

    }

    public void hendlerSkladnik() throws Exception {

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Skladnik.fxml"));
        Stage window = (Stage) skladnik.getScene().getWindow();
        window.setScene(new Scene(fxmlLoader.load(), 1200, 800));

    }

    public void hendlerAdmin() throws Exception {

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Admin.fxml"));
        Stage window = (Stage) Admin.getScene().getWindow();
        window.setScene(new Scene(fxmlLoader.load(), 1200, 800));

    }

*/

}