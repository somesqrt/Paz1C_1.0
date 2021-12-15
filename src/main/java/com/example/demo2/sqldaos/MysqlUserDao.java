package com.example.demo2.sqldaos;

import com.example.demo2.DaoFactory;
import com.example.demo2.EntityNotFoundException;
import com.example.demo2.classes.User;
import com.example.demo2.daos.RoleDao;
import com.example.demo2.daos.UserDao;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MysqlUserDao implements UserDao {
    private JdbcTemplate jdbcTemplate;

    public MysqlUserDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<User> getAll() {
        return jdbcTemplate.query("select * from user", new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                Long id = rs.getLong("idUser");
                String name = rs.getString("name");
                String surname = rs.getString("surname");
                Date dateofbirth = rs.getDate("dateOfBirth");
                String login = rs.getString("login");
                String password = rs.getString("password");
                RoleDao roleDao = DaoFactory.INSTANCE.getRoleDao();
                return new User(id, name, surname, dateofbirth, login, password, roleDao.getByid(rs.getLong("role")));
            }
        });
    }


    @Override
    public User save(User user) {
        if (user.getIdUser() == null)//insert
        {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate);
            insert.withTableName("user");
            insert.usingGeneratedKeyColumns("idUser");
            insert.usingColumns("`name`","surname","dateOfBirth","login","password","role");
            MapSqlParameterSource namedParameters = new MapSqlParameterSource();
            namedParameters.addValue("`name`",user.getName());
            namedParameters.addValue("surname",user.getSurname());
            namedParameters.addValue("dateOfBirth",formatter.format(user.getDateOfBirth()));
            namedParameters.addValue("login",user.getLogin());
            namedParameters.addValue("password",user.getPassword());
            namedParameters.addValue("role",user.getRole().getIdRole());

            Long id = null;
            try {
                id = insert.executeAndReturnKey(namedParameters).longValue();
            } catch (DuplicateKeyException e) {
                throw new EntityNotFoundException("user with login "+user.getLogin()+" exist");
            }
            return new User(id,user.getName(),user.getSurname(),user.getDateOfBirth(),user.getLogin(),user.getPassword(),user.getRole());
        } else {
            String sql = "Update user Set name = ?,surname= ?,dateOfBirth= ?,login= ?,password= ?,role= ? where idUser = ?";
            int pocet = jdbcTemplate.update(sql, user.getName(), user.getSurname(), user.getDateOfBirth(), user.getLogin(), user.getPassword(), user.getRole().getIdRole(), user.getIdUser());
            if (pocet >= 1) {
                return new User(user.getIdUser(),user.getName(),user.getSurname(),user.getDateOfBirth(),user.getLogin(),user.getPassword(),user.getRole());
            } else {
                throw new EntityNotFoundException("user nie existuje");

            }
        }

    }
    /*@Override
    public User save(User user) {
        if (user.getIdUser() == null)//insert
        {
            String sql = "Insert into user(name,surname,dateOfBirth,login,password,role) value(?,?,?,?,?,?)";
            int pocet = jdbcTemplate.update(sql, user.getName(), user.getSurname(), user.getDateOfBirth(), user.getLogin(), user.getPassword(), user.getRole().getIdRole());
            if (pocet == 1) {
                return user;
            }
        } else {
            String sql = "Update user Set name = ?,surname= ?,dateOfBirth= ?,login= ?,password= ?,role= ? where idUser = ?";
            int pocet = jdbcTemplate.update(sql, user.getName(), user.getSurname(), user.getDateOfBirth(), user.getLogin(), user.getPassword(), user.getRole().getIdRole(), user.getIdUser());
            if (pocet >= 1) {
                return user;
            } else {
                throw new EntityNotFoundException("user nie existuje");

            }
        }
        return user;
    }*/
    @Override
    public User delete(Long id) {
        User deleted = getByid(id);
        try {
            String sql = "DELETE from user where idUser =" + id;
            jdbcTemplate.update(sql);
            return deleted;
        } catch (DataIntegrityViolationException e) {
            throw new EntityNotFoundException("nie je mozne zmazat categoriu");
        }
    }
    /*@Override
    public User delete(Long id) {
        User deleted = getByid(id);
        String sql = "DELETE from user where idUser =" + id;
        int pocet = jdbcTemplate.update(sql);
        return deleted;
    }*/

    @Override
    public User getByid(Long id) {
        try {
            String sql = "SELECT * FROM user where idUser = " + id;
            return jdbcTemplate.queryForObject(sql, new RowMapper<User>() {
                @Override
                public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                    Long id = rs.getLong("idUser");
                    String name = rs.getString("name");
                    String surname = rs.getString("surname");
                    Date dateofbirth = rs.getDate("dateOfBirth");
                    String login = rs.getString("login");
                    String password = rs.getString("password");
                    RoleDao roleDao = DaoFactory.INSTANCE.getRoleDao();
                    return new User(id, name, surname, dateofbirth, login, password, roleDao.getByid(rs.getLong("role")));
                }
            });
        } catch (Exception e) {
            throw new EntityNotFoundException("User nie existuje");
        }

    }

    @Override
    public User getByLogin(String login) {
        try {
            String sql = "SELECT * FROM user where login like \"" + login + "\"";
            return jdbcTemplate.queryForObject(sql, new RowMapper<User>() {
                @Override
                public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                    Long id = rs.getLong("idUser");
                    String name = rs.getString("name");
                    String surname = rs.getString("surname");
                    Date dateofbirth = rs.getDate("dateOfBirth");
                    String login = rs.getString("login");
                    String password = rs.getString("password");
                    RoleDao roleDao = DaoFactory.INSTANCE.getRoleDao();
                    return new User(id, name, surname, dateofbirth, login, password, roleDao.getByid(rs.getLong("role")));
                }
            });
        } catch (Exception e) {
            throw new EntityNotFoundException("User nie existuje");
        }

    }

    @Override
    public List<User> searchUser(String name, String surname) {
        return jdbcTemplate.query("select * from user where name like \"" + name + "\" and surname like \"" + surname + "\"", new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                Long id = rs.getLong("idUser");
                String name = rs.getString("name");
                String surname = rs.getString("surname");
                Date dateofbirth = rs.getDate("dateOfBirth");
                String login = rs.getString("login");
                String password = rs.getString("password");
                RoleDao roleDao = DaoFactory.INSTANCE.getRoleDao();
                return new User(id, name, surname, dateofbirth, login, password, roleDao.getByid(rs.getLong("role")));
            }
        });
    }

    @Override
    public User getByName(String name) {
        try {
            String sql = "SELECT * FROM user where name = \"" + name + "\"";
            return jdbcTemplate.queryForObject(sql, new RowMapper<User>() {
                @Override
                public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                    Long id = rs.getLong("idUser");
                    String name = rs.getString("name");
                    String surname = rs.getString("surname");
                    Date dateofbirth = rs.getDate("dateOfBirth");
                    String login = rs.getString("login");
                    String password = rs.getString("password");
                    RoleDao roleDao = DaoFactory.INSTANCE.getRoleDao();
                    return new User(id, name, surname, dateofbirth, login, password, roleDao.getByid(rs.getLong("role")));
                }
            });
        } catch (Exception e) {
            throw new EntityNotFoundException("User nie existuje");
        }

    }


}
