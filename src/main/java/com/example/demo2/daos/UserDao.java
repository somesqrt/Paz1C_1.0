package com.example.demo2.daos;
import com.example.demo2.EntityNotFoundException;
import com.example.demo2.classes.User;

import java.util.List;

public interface UserDao {
    List<User> getAll();
    User save(User user);
    User delete(Long id);
    User getByid(Long id) throws EntityNotFoundException;

    User getByLogin(String login);

    List<User> searchUser(String name, String surname);

    User getByName(String name);
}
