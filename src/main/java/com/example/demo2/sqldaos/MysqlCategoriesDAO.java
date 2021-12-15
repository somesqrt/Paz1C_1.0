package com.example.demo2.sqldaos;


import com.example.demo2.EntityNotFoundException;
import com.example.demo2.classes.Categories;
import com.example.demo2.daos.CategoriesDAO;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class MysqlCategoriesDAO  implements CategoriesDAO {
    private JdbcTemplate jdbcTemplate;

    public MysqlCategoriesDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Categories> getAll() {
        String sql = "SELECT * FROM categories order by idCategories asc;";
        return jdbcTemplate.query(sql, new RowMapper<Categories>() {
            @Override
            public Categories mapRow(ResultSet rs, int rowNum) throws SQLException {
                Long id = rs.getLong("idCategories");
                String category = rs.getString("categoria");
                return new Categories(id,category);
            }
        });
    }


    @Override
    public Categories save(Categories categories) {
        if (categories.getIdCategories() == null)//INSERT
        {
            SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate);
            insert.withTableName("`categories`");
            insert.usingGeneratedKeyColumns("idCategories");
            insert.usingColumns("categoria");
            MapSqlParameterSource namedParameters = new MapSqlParameterSource();
            namedParameters.addValue("categoria",categories.getCategoria());
            try {
                Long Id = insert.executeAndReturnKey(namedParameters).longValue();
                return new Categories(Id,categories.getCategoria());
            } catch (DuplicateKeyException e) {
                throw new EntityNotFoundException("categoria uz existuje");
            }
        } else {//update
            String sql = "UPDATE categories SET categoria = ? where idCategories = ?";
            int pocet = jdbcTemplate.update(sql, categories.getCategoria(),categories.getIdCategories());
            if (pocet >= 1) {
                return new Categories(categories.getIdCategories(),categories.getCategoria());
            } else {
                throw new EntityNotFoundException("categoria nie existuje");
            }
        }
    }
    /*@Override
    public Categories save(Categories categories) {
        if (categories.getIdCategories() == null)//INSERT
        {
            String sql = "INSERT INTO categories(categoria) VALUE(?)";
            int pocet = jdbcTemplate.update(sql, categories.getCategoria());
            if (pocet == 1) {
                return categories;
            }
        } else {//update
            String sql = "UPDATE categories SET categoria = ? where idCategories = ?";
            int pocet = jdbcTemplate.update(sql, categories.getCategoria(),categories.getIdCategories());
            if (pocet >= 1) {
                return categories;
            } else {
                throw new EntityNotFoundException("positia nie existuje");

            }
        }
        return categories;
    }*/

    @Override
    public Categories delete(Long id) {
        Categories catToDelete = getbyID(id);
        String sql = "DELETE from categories where idCategories =" + id;
        int pocet = jdbcTemplate.update(sql);
        return catToDelete;
    }

    @Override
    public Categories getbyID(Long id) throws EntityNotFoundException {
        try {
            String sql = "SELECT * FROM categories where idCategories = " + id;
            return jdbcTemplate.queryForObject(sql, new RowMapper<Categories>() {
                @Override
                public Categories mapRow(ResultSet rs, int rowNum) throws SQLException {
                    Long id = rs.getLong("idCategories");
                    String category = rs.getString("categoria");
                    return new Categories(id,category);
                }
            });
        } catch (DataAccessException e) {
            throw new EntityNotFoundException("categoria nie existuje");
        }
    }

    /*@Override
    public Categories getbyID(Long id) throws EntityNotFoundException {
        try {
            String sql = "SELECT * FROM categories where idCategories = " + id;
            return jdbcTemplate.queryForObject(sql, new RowMapper<Categories>() {
                @Override
                public Categories mapRow(ResultSet rs, int rowNum) throws SQLException {
                    Long id = rs.getLong("idCategories");
                    String category = rs.getString("categoria");
                    return new Categories(id,category);
                }
            });
        } catch (DataAccessException e) {
            throw new EntityNotFoundException("posiitia nie existuje");
        }
    }*/

    @Override
    public List<String> getALlNames() throws EntityNotFoundException {
        String sql = "SELECT categoria FROM categories order by idCategories asc;";
        return jdbcTemplate.query(sql, new RowMapper<String>() {
            @Override
            public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                String category = rs.getString("categoria");
                return category;
            }
        });
    }

    public Long getByName(String name) throws EntityNotFoundException {
        try {
            String sql = "SELECT * FROM categories where categoria = \"" + name + "\"";
            return jdbcTemplate.queryForObject(sql, new RowMapper<Long>() {
                @Override
                public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
                    Long id = rs.getLong("idCategories");
                    return id;
                }
            });
        } catch (DataAccessException e) {
            throw new EntityNotFoundException("posiitia nie existuje");
        }
    }
}
