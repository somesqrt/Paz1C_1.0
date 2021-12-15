package com.example.demo2.daos;



import com.example.demo2.EntityNotFoundException;
import com.example.demo2.classes.Roles;

import java.util.List;

public interface RoleDao {
    List<Roles> getAll();
    Roles saveUpdate(Roles roles) throws EntityNotFoundException;
    Roles getByid(Long id)throws EntityNotFoundException;

    Roles getByString(String role) throws EntityNotFoundException;
}
