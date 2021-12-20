package com.example.demo2;

import com.example.demo2.classes.Roles;
import com.example.demo2.classes.User;
import com.example.demo2.daos.RoleDao;
import com.example.demo2.daos.UserDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.WindowEvent;

import java.text.SimpleDateFormat;
import java.util.Date;

public class UserEditController {

    @FXML
    TextField editName, editSurname, editDate, editLogin;

    @FXML
    ComboBox editRole;

    @FXML
    Button saveEdit, cancelEdit;
    User user = AdminController.user;
    UserDao userDao = DaoFactory.INSTANCE.getUserDao();

    @FXML
    public void initialize() {
        cancelEdit.setOnAction(event -> AdminController.editingWindow.close());
        editName.setText(user.getName());
        editSurname.setText(user.getSurname());
        editDate.setText(user.getDateOfBirth().toString());
        editLogin.setText(user.getLogin());
        ObservableList<String> options =
                FXCollections.observableArrayList(
                        "skladnik",
                        "admin",
                        "veduci",
                        "predajca"
                );
        editRole.setItems(options);
        editRole.getSelectionModel().select(user.getRole().getRole());

    }

    public void reset() throws Exception {
        String hash = userDao.HashPassword("1111");
        user.setPassword(hash);
        userDao.save(user);
    }

    @FXML
    public void doDeleteUser() throws Exception {
        userDao.delete(user.getIdUser());
        AdminController.editingWindow.fireEvent(new WindowEvent(AdminController.editingWindow, WindowEvent.WINDOW_CLOSE_REQUEST));
    }

    @FXML
    public void saveEditUser() throws Exception{
        RoleDao roleDao = DaoFactory.INSTANCE.getRoleDao();
        long numberRoly = roleDao.getByString(String.valueOf(editRole.getValue())).getIdRole();
        String name = editName.getText();
        String surname = editSurname.getText();
        //Date nDate = date.parse(newDate.getText());
        Date nDate = new SimpleDateFormat("yyyy-MM-dd").parse(editDate.getText());
        String login = editLogin.getText();
        String password = user.getPassword();
        Roles nRole = new Roles(1 ,String.valueOf(editRole.getValue()));
        nRole.setIdRole(numberRoly);
        Long id = user.getIdUser();
        User user = new User(
               id,
                name,
                surname,
                nDate,
                login,
                password,
                nRole);

        userDao.save(user);
        AdminController.addingWindow.fireEvent(new WindowEvent(AdminController.addingWindow, WindowEvent.WINDOW_CLOSE_REQUEST));

    }

}
