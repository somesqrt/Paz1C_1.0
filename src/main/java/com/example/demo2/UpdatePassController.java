package com.example.demo2;

import com.example.demo2.daos.UserDao;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.WindowEvent;

import java.awt.*;

public class UpdatePassController {

    @FXML
    TextField PassForChange;

    UserDao userDao = DaoFactory.INSTANCE.getUserDao();
    public void updatePasswrodCurrentUser(){
        UserDao userDao = DaoFactory.INSTANCE.getUserDao();
        LoginController.currentUser.setPassword(userDao.HashPassword(PassForChange.getText()));
        userDao.save(LoginController.currentUser);
        AdminController.addingWindow.fireEvent(new WindowEvent(AdminController.addingWindow, WindowEvent.WINDOW_CLOSE_REQUEST));
    }
}
