package com.example.demo2.sqldaos;

import com.example.demo2.DaoFactory;
import com.example.demo2.EntityNotFoundException;
import com.example.demo2.classes.Roles;
import com.example.demo2.classes.User;
import com.example.demo2.daos.RoleDao;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;


import javax.management.relation.Role;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MysqlRoleDaoTest {
    private RoleDao roleDao;

    public MysqlRoleDaoTest() {
        DaoFactory.INSTANCE.testing();
        roleDao = DaoFactory.INSTANCE.getRoleDao();
    }

    @Test
    void getAll() {
        List<Roles> rolesList = roleDao.getAll();
        assertTrue(rolesList.size() >= 0);
        assertNotNull(rolesList);
        for (int i = 0; i < rolesList.size(); i++) {
            assertNotNull(rolesList.get(i));
        }
    }

    @Test
    void saveUpdate() {
        assertThrows(NullPointerException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                roleDao.saveUpdate(null);
            }
        });

        List<Roles> rolesList = roleDao.getAll();
        Roles role = new Roles(
                rolesList.get(rolesList.size() - 1).getIdRole(),
                rolesList.get(rolesList.size() - 1).getRole()
        );

        Roles updatesRole = roleDao.saveUpdate(role);
        Roles gettingRole = roleDao.getByid(rolesList.get(rolesList.size() - 1).getIdRole());
        assertEquals(updatesRole.getIdRole(), gettingRole.getIdRole());
        assertEquals(updatesRole.getRole(), gettingRole.getRole());


    }

    @Test
    void getByid() {
        assertThrows(EntityNotFoundException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                DaoFactory.INSTANCE.getRoleDao().getByid(-1L);
            }
        });
        List<Roles> rolesList = DaoFactory.INSTANCE.getRoleDao().getAll();
        Roles roles = rolesList.get(2);
        Roles roles1 = DaoFactory.INSTANCE.getRoleDao().getByid(3L);
        assertEquals(roles.getIdRole(), roles1.getIdRole());
    }

    @Test
    void getByString() {
        assertThrows(EntityNotFoundException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                roleDao.getByString(null);
            }
        });
        List<Roles> rolesList = roleDao.getAll();
        Roles getting = roleDao.getByString( rolesList.get(rolesList.size() - 1).getRole());
        assertNotNull(getting.getRole());
        assertNotNull(getting.getIdRole());
    }
}