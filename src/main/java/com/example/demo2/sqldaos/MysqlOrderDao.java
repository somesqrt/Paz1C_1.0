package com.example.demo2.sqldaos;

import com.example.demo2.DaoFactory;
import com.example.demo2.EntityNotFoundException;
import com.example.demo2.classes.*;
import com.example.demo2.daos.OrderDao;
import com.example.demo2.daos.ProductDao;
import com.example.demo2.daos.UserDao;
import com.example.demo2.help.AllOrderHelp;
import com.example.demo2.help.ProductInOrderHelp;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

public class MysqlOrderDao implements OrderDao {
    private JdbcTemplate jdbcTemplate;

    public MysqlOrderDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void deleteOrder(Long id) {

        String sql = "DELETE from paz1c_project.order where idOrder =" + id;
        jdbcTemplate.update(sql);

    }
    @Override
    public void deleteOrderProduct(Long id) {

        String sql = "DELETE from paz1c_project.productsinorder where idOrder =" + id;
        jdbcTemplate.update(sql);
    }

    @Override
    public List<Order> getAll() {
        return jdbcTemplate.query("SELECT * FROM paz1c_project.order", new RowMapper<Order>() {
            @Override
            public Order mapRow(ResultSet rs, int rowNum) throws SQLException {
                Long id = rs.getLong("idOrder");
                String name = rs.getString("Name");
                Double sum = rs.getDouble("Summ");
                String orderStatus = rs.getString("OrderStatus");
                int idMan = rs.getInt("SalesMan");
                UserDao userDao = DaoFactory.INSTANCE.getUserDao();
                User user = userDao.getByid(Long.valueOf(idMan));
                return new Order(id, name, sum, orderStatus, user);
            }
        });
    }

    @Override
    public List<ProductInOrder> getAllProductsInOrder(long id) {
        return jdbcTemplate.query("SELECT * FROM paz1c_project.productsinorder where idOrder = " + id, new RowMapper<ProductInOrder>() {
            @Override
            public ProductInOrder mapRow(ResultSet rs, int rowNum) throws SQLException {
                Long id = rs.getLong("idOrder");
                Long idProduct = rs.getLong("idProducts");
                Double count = rs.getDouble("count");
                return new ProductInOrder(id, idProduct, count);
            }
        });
    }

    @Override
    public List<ProductInOrder> getAllProductsInOrderWitthLongID(long id) {
        return jdbcTemplate.query("SELECT * FROM paz1c_project.productsinorder where idOrder = " + id, new RowMapper<ProductInOrder>() {
            @Override
            public ProductInOrder mapRow(ResultSet rs, int rowNum) throws SQLException {
                Long id = rs.getLong("idOrder");

                Long idProduct1 = rs.getLong("idProduct");
                ProductDao productDao = DaoFactory.INSTANCE.getProductDao();
                Long idProduct = productDao.getbyId(Long.valueOf(idProduct1)).getIdProduct();
                Double count = rs.getDouble("count");
                return new ProductInOrder(id, idProduct, count);
            }
        });
    }

    @Override
    public void save(Order order, ArrayList<ProductInOrder> productInOrder, Long id){
        String sql1 = "Update paz1c_project.order Set Summ = " + order.getSumm() + " WHERE idOrder like " +order.getIdOrder();
        jdbcTemplate.update(sql1);

        for (int i = 0; i < productInOrder.size(); i++) {
            int need = (int) (1 + productInOrder.get(i).getCount() - 1);
            String sql = "Update paz1c_project.productsinorder Set count = " + need + " WHERE idOrder like " + productInOrder.get(i).getId() + " and idProducts like " + productInOrder.get(i).getProduct().getIdProduct();
            jdbcTemplate.update(sql);
        }
    }

    @Override
    public void update(Long id, String status){
        String sql = "Update paz1c_project.order Set OrderStatus = '" + status + "' WHERE idOrder like " + id;
        jdbcTemplate.update(sql);
    }


    @Override
    public Order createOrder(Order order) {
        Long idOfOrder;
        if (OrderControl(order) == true) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate);
            insert.withTableName("`order`");
            insert.usingGeneratedKeyColumns("idOrder");
            insert.usingColumns("Name", "Summ", "OrderStatus", "SalesMan");
            MapSqlParameterSource namedParameters = new MapSqlParameterSource();
            namedParameters.addValue("Name", order.getName());
            namedParameters.addValue("Summ", order.getSumm());
            namedParameters.addValue("OrderStatus", order.getOrderStatus());
            namedParameters.addValue("SalesMan", order.getSalesMan().getIdUser());
            idOfOrder = insert.executeAndReturnKey(namedParameters).longValue();
            insert = new SimpleJdbcInsert(jdbcTemplate);
            for (Product product : order.getProductsInOrder().keySet()) {
                Long position = DaoFactory.INSTANCE.getPositionDao().getIdPositionByProduct(product, order.getProductsInOrder().get(product));
                insert.withTableName("`productsinorder`");
                insert.usingColumns("idOrder", "idProducts", "count", "IdPosition");
                namedParameters = new MapSqlParameterSource();
                namedParameters.addValue("idOrder", idOfOrder);
                namedParameters.addValue("idProducts", product.getIdProduct());
                namedParameters.addValue("count", order.getProductsInOrder().get(product));
                namedParameters.addValue("IdPosition", position);
                insert.execute(namedParameters);
                String sqlcountInPosition = "select count from prosuctonposition where idProduct = ? and idPosition = ?";
                int countInPosition =jdbcTemplate.queryForObject(sqlcountInPosition, new RowMapper<Integer>() {
                    @Override
                    public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
                        int pocet = rs.getInt("count");
                        return pocet;
                    }
                },product.getIdProduct(),position);
                sqlcountInPosition = "UPDATE `prosuctonposition` SET  `count` = ? WHERE idProduct = ? and idPosition = ? ";
                jdbcTemplate.update(sqlcountInPosition,countInPosition-order.getProductsInOrder().get(product),product.getIdProduct(),position);
            }
        } else {
            throw new EntityNotFoundException("Order creation error\n"+"Order:"+order+"\n"+"contact the warehouse manager");
        }
        return new Order(idOfOrder,order.getName(),order.getSumm(),order.getOrderStatus(),order.getSalesMan(),order.getProductsInOrder());
    }



    public Boolean OrderControl(Order order) {
        Boolean controla = true;
        Map<Product, Integer> orderProduct = order.getProductsInOrder();
        for (Product product : orderProduct.keySet()) {
            Product product1 = product;
            int pocet = orderProduct.get(product1);
            String sql = "Select count(*) as controla from prosuctonposition where idProduct = ? and count>=?";
            boolean resultfromdb = jdbcTemplate.queryForObject(sql, new RowMapper<Boolean>() {
                @Override
                public Boolean mapRow(ResultSet rs, int rowNum) throws SQLException {
                    int controlaCount = rs.getInt("controla");
                    if (controlaCount >= 1) {
                        return true;
                    } else {
                        return false;
                    }
                }
            }, product1.getIdProduct(), pocet);
            if (resultfromdb == false) {
                controla = false;
                break;
            }
        }
        return controla;
    }
    @Override
    public Map<Product,Integer> getProductInOrder(Order order) {
        String sql = "SELECT * FROM paz1c_project.productsinorder where idOrder ="+order.getIdOrder();
        Map<Product,Integer> map = new HashMap<>();
        return jdbcTemplate.queryForObject(sql, new RowMapper<Map<Product,Integer>>() {
            @Override
            public Map<Product,Integer> mapRow(ResultSet rs, int rowNum) throws SQLException {
               Long idProducts = rs.getLong("idProducts");
               int count = rs.getInt("count");
              map.put(DaoFactory.INSTANCE.getProductDao().getbyId(idProducts),count);
              return map;
            }
        });
    }

    @Override
    public Order update(Order order) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd H:mm:ss", Locale.ENGLISH);
        String sql = "UPDATE `paz1c_project`.`order` SET `Name` = ?, `Summ` = ?, `OrderStatus` = ?, `SalesMan` = ?, `DateTime` = ?> WHERE `idOrder` = ?;";
        int pocet = jdbcTemplate.update(sql,order.getName(),order.getSumm(),order.getOrderStatus(),order.getSalesMan().getIdUser(),/*formatter.format(order.getDateTime()),*/order.getIdOrder());
        if(pocet == 1)
        {
            return order;
        }
        else {
            throw new EntityNotFoundException("chyba updatumu");
        }
    }

    @Override
    public Order getIdFromOrder(Order order) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd H:mm:ss", Locale.ENGLISH);
        String sql = "SELECT idOrder FROM paz1c_project.order WHERE Name ='" + order.getName() + "'  and Summ = " + order.getSumm()
                + " and OrderStatus = '" + order.getOrderStatus() + "' and SalesMan = " + order.getSalesMan().getIdUser() /*+ " and DateTime ='" + formatter.format(order.getDateTime())+"'"*/ ;
        try {
            return jdbcTemplate.queryForObject(sql, new RowMapper<Order>() {
                @Override
                public Order mapRow(ResultSet rs, int rowNum) throws SQLException {
                    Long id = rs.getLong("idOrder");
/*                    String name = rs.getString("Name");
                    double Summ = rs.getDouble("Summ");
                    String OrderStatus = rs.getString("OrderStatus");
                    Long SalesMan = rs.getLong("SalesMan");
*//*                    Date DateTime = rs.getTimestamp("DateTime");*/
                    return new Order(id,null,0,null,null);
                }
            });
        } catch (DataAccessException e) {
            throw new EntityNotFoundException("order nie existuje");
        }
    }

    @Override
    public List<AllOrderHelp> getByFilters(String id, String name){
        /*return jdbcTemplate.query("select * from paz1c_project.products where name like " + nameOfProductSearch +
                " and EAN like " + eanOfProductSearch + " and Categories like " + categoriOfProductSearch + ";"*/

        return jdbcTemplate.query("SELECT * FROM paz1c_project.order where idOrder like " + id +
                " and Name like " + name + ";", new RowMapper<AllOrderHelp>() {
            @Override
            public AllOrderHelp mapRow(ResultSet rs, int rowNum) throws SQLException {
                Long id = rs.getLong("idOrder");
                String name = rs.getString("Name");
                Double sum = rs.getDouble("Summ");
                String orderStatus = rs.getString("OrderStatus");
                int idMan = rs.getInt("SalesMan");
                UserDao userDao = DaoFactory.INSTANCE.getUserDao();
                String user = userDao.getByid(Long.valueOf(idMan)).getName();
                return new AllOrderHelp(id, name, sum, orderStatus, user);
            }
        });
    }

    @Override
    public ProductInOrderHelp getByOrder(String id){
        return jdbcTemplate.queryForObject("SELECT * FROM paz1c_project.prosuctonposition where idProduct like " + id, new RowMapper<ProductInOrderHelp>() {
            @Override
            public ProductInOrderHelp mapRow(ResultSet rs, int rowNum) throws SQLException {
                int idProduct = rs.getInt("idProduct");
                int idPozition = rs.getInt("idPosition");
                int count = rs.getInt("count");
                return new ProductInOrderHelp(String.valueOf(idProduct), String.valueOf(count), String.valueOf(idPozition));
            }
        });
    }
}
