package com.example.demo2;


import com.example.demo2.classes.User;
import com.example.demo2.daos.UserDao;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {

    @FXML
    TextField loginUserToLogin;

    @FXML
    Button signup;

    @FXML
    PasswordField passwordToLogin;

    public static User currentUser;
    public static Stage stage = new Stage();
    private String loginForLogin, passwordForLogin;
    public static Stage changeWindow = new Stage();
    @FXML
    public void toSignup() throws Exception {   // логин юзера (переделать ДБ)
        UserDao userDao = DaoFactory.INSTANCE.getUserDao();
        loginForLogin = loginUserToLogin.getText();


        passwordForLogin = passwordToLogin.getText();

        if (loginForLogin.equals("") || passwordForLogin.equals("")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);

            alert.setTitle("Incorrect data");
            alert.setHeaderText(null);
            alert.setContentText("Polia pre prihlasovacie meno a heslo nemôžu zostať prázdne.");

            alert.showAndWait();
        } else {
            try{
                currentUser = userDao.getByLogin(loginForLogin);
            }catch (Exception e){

            }
              //тут происходит поиск но по старой ДБ надо переделать(переподключить метод)
            if (currentUser == null) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);

                alert.setTitle("Login failed.");
                alert.setHeaderText(null);
                alert.setContentText("Prihlasovacie meno alebo heslo je nesprávne");

                alert.showAndWait();
            } else {
                String hash = userDao.HashPassword(passwordForLogin);
                if(hash.equals(currentUser.getPassword())){
                    FXMLLoader fxmlLoader = null;
                    if (currentUser.getRole().getIdRole() == 1) {                           //разобраться как он сделал роли
                        fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("SkladnikNonAdmin.fxml"));
                    } else if (currentUser.getRole().getIdRole() == 2) {
                        fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Admin.fxml"));
                    } else if (currentUser.getRole().getIdRole() == 3) {
                        fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("VeduciNonAdmin.fxml"));
                    } else if (currentUser.getRole().getIdRole() == 4) {
                        fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("predajcaNonAdmin.fxml"));
                    }

                    Stage stage1 = new Stage();
                    stage1 = (Stage) signup.getScene().getWindow();
                    stage1.close();

                    Scene scene = new Scene(fxmlLoader.load(), 1200, 800);
                    stage.setScene(scene);
                    stage.show();
                }else{
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);

                    alert.setTitle("Incorrect data");
                    alert.setHeaderText(null);
                    alert.setContentText("rihlasovacie meno alebo heslo je nesprávne");
                }

            }

        }

    }
}


