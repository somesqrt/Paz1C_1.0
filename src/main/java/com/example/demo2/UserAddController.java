package com.example.demo2;

import com.example.demo2.classes.Roles;
import com.example.demo2.classes.User;
import com.example.demo2.daos.RoleDao;
import com.example.demo2.daos.UserDao;
import com.example.demo2.sqldaos.MysqlRoleDao;
import com.example.demo2.sqldaos.MysqlUserDao;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.management.relation.Role;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UserAddController {

    @FXML
    TextField newName, newSurname, newDate, newLogin;
    @FXML
    ComboBox newRole;
    @FXML
    Button cancelAdd;


    Stage stage;

    public void initialize() {
        cancelAdd.setOnAction(event -> AdminController.addingWindow.close());
    }

    public void CreateAction() throws Exception {

        MysqlRoleDao mysqlRoleDao = new MysqlRoleDao(new JdbcTemplate());
        RoleDao roleDao = DaoFactory.INSTANCE.getRoleDao();
        long numberRoly = roleDao.getByString(String.valueOf(newRole.getValue())).getIdRole();


        System.out.println(newName.getText());
        String name = newName.getText();
        String surname = newSurname.getText();
        //Date nDate = date.parse(newDate.getText());
        Date nDate = new SimpleDateFormat("dd/MM/yyyy").parse(newDate.getText());
        String login = newLogin.getText();
        String password = DaoFactory.INSTANCE.getUserDao().HashPassword("1111");
        Roles nRole = new Roles(1 ,String.valueOf(newRole.getValue()));
        nRole.setIdRole(numberRoly);



        User user = new User(
                null,
                name,
                surname,
                nDate,
                login,
                password,
                nRole);

        UserDao userDao = DaoFactory.INSTANCE.getUserDao();

        userDao.save(user);

        Stage addingWindow = new Stage();

        AdminController.addingWindow.fireEvent(new WindowEvent(AdminController.addingWindow, WindowEvent.WINDOW_CLOSE_REQUEST));

    }


}
