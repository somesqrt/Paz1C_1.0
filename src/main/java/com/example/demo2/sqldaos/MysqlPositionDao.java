package com.example.demo2.sqldaos;

import com.example.demo2.DaoFactory;
import com.example.demo2.EntityNotFoundException;
import com.example.demo2.classes.Position;
import com.example.demo2.classes.Product;
import com.example.demo2.daos.PositionDao;
import com.example.demo2.help.ProduktOnPositionHelp;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class MysqlPositionDao implements PositionDao {
    private JdbcTemplate jdbcTemplate;

    public MysqlPositionDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Position> getAll() {
        String sql = "select positions.id,positions.floor,positions.positionNumber,positions.shelf,positions.height,positions.weight,positions.lenght,positions.BearingCapacity,prosuctonposition.count,products.idProduct,products.name from positions\n" +
                "left outer join prosuctonposition on positions.id = prosuctonposition.idPosition\n" +
                "left outer join products on prosuctonposition.idProduct = products.idProduct;";
        return jdbcTemplate.query(sql, new ResultSetExtractor<List<Position>>() {
            @Override
            public List<Position> extractData(ResultSet rs) throws SQLException, DataAccessException {
                List<Position> positionList = new ArrayList<>();
                Position position = null;
                while (rs.next()) {
                    Long id = rs.getLong("id");
                    if (position == null || position.getIdPosiiton() != id) {
                        int floor = rs.getInt("floor");
                        int positonNumber = rs.getInt("positionNumber");
                        String shelf = rs.getString("shelf");
                        double height = rs.getDouble("height");
                        double weight = rs.getDouble("weight");
                        double lenght = rs.getDouble("lenght");
                        double BearingCapacity = rs.getDouble("BearingCapacity");
                        position = new Position(id, floor, positonNumber, shelf, height, weight, lenght, BearingCapacity);
                        positionList.add(position);
                    }
                    if (rs.getString("name") == null) {
                        continue;
                    }
                    Long idProduct = rs.getLong("idProduct");
                    int count = rs.getInt("count");

                     position.getProducts().put(DaoFactory.INSTANCE.getProductDao().getbyId(idProduct), count);
                }
                return positionList;
            }
        });
    }
    /*@Override
    public List<Position> getAll() {
        String sql = "select positions.id,positions.floor,positions.positionNumber,positions.shelf,positions.height,positions.weight,positions.lenght,positions.BearingCapacity,prosuctonposition.count,products.idProduct,products.name from positions\n" +
                "left outer join prosuctonposition on positions.id = prosuctonposition.idPosition\n" +
                "left outer join products on prosuctonposition.idProduct = products.idProduct;";
        return jdbcTemplate.query(sql, new ResultSetExtractor<List<Position>>() {
            @Override
            public List<Position> extractData(ResultSet rs) throws SQLException, DataAccessException {
                List<Position> positionList = new ArrayList<>();
                Position position = null;
                while (rs.next()) {
                    Long id = rs.getLong("id");
                    if (position == null || position.getIdPosiiton() != id) {
                        int floor = rs.getInt("floor");
                        int positonNumber = rs.getInt("positionNumber");
                        String shelf = rs.getString("shelf");
                        double height = rs.getDouble("height");
                        double weight = rs.getDouble("weight");
                        double lenght = rs.getDouble("lenght");
                        double BearingCapacity = rs.getDouble("BearingCapacity");
                        position = new Position(id, floor, positonNumber, shelf, height, weight, lenght, BearingCapacity);
                        positionList.add(position);
                    }
                    if (rs.getString("name") == null) {
                        continue;
                    }
                    Long idProduct = rs.getLong("idProduct");
                    int count = rs.getInt("count");
                    position.getProducts().put(DaoFactory.INSTANCE.getProductDao().getbyId(id), count);
                }
                return positionList;
            }
        });
    }*/

    @Override
    public Position save(Position position) throws EntityNotFoundException {
        Long posiiton1 = position.getIdPosiiton();
        if (posiiton1 == null)//INSERT
        {
            SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate);
            insert.withTableName("positions");
            insert.usingGeneratedKeyColumns("id");
            insert.usingColumns("floor", "positionNumber", "shelf", "height", "weight", "lenght", "BearingCapacity");
            MapSqlParameterSource namedParameters = new MapSqlParameterSource();
            namedParameters.addValue("floor", position.getFloor());
            namedParameters.addValue("positionNumber", position.getPositionNumber());
            namedParameters.addValue("shelf", position.getShelf());
            namedParameters.addValue("height", position.getHeight());
            namedParameters.addValue("weight", position.getWeight());
            namedParameters.addValue("lenght", position.getLength());
            namedParameters.addValue("BearingCapacity", position.getBearingCapacity());
            Long id = insert.executeAndReturnKey(namedParameters).longValue();
            return new Position(id, position.getFloor(), position.getPositionNumber(), position.getShelf(), position.getHeight(), position.getWeight(), position.getLength(), position.getBearingCapacity());
        } else {//update
            String sql = "UPDATE positions SET floor = ?,positionNumber = ?,shelf = ?,height = ?,weight = ?,lenght = ?,BearingCapacity = ? where id = ?";
            int pocet = jdbcTemplate.update(sql, position.getFloor(), position.getPositionNumber(), position.getShelf(), position.getHeight(), position.getWeight(), position.getLength(), position.getBearingCapacity(), position.getIdPosiiton());
            if (pocet >= 1) {
                return new Position(position.getIdPosiiton(), position.getFloor(), position.getPositionNumber(), position.getShelf(), position.getHeight(), position.getWeight(), position.getLength(), position.getBearingCapacity());
            } else {
                throw new EntityNotFoundException("positia nie existuje");

            }
        }
    }

    @Override
    public Position delete(Long id) throws EntityNotFoundException {
        Position posToDelete = getById(id);
        try {
            String sql = "DELETE from positions where id =" + id;
            jdbcTemplate.update(sql);
            return posToDelete;
        } catch (DataAccessException e) {
            throw new EntityNotFoundException("nie je mozne zmazat Poziciu");
        }
    }

    @Override
    public Position getById(Long id) throws EntityNotFoundException {
        try {
            String sql = "SELECT * FROM positions where id = " + id;
            return jdbcTemplate.queryForObject(sql, new RowMapper<Position>() {
                @Override
                public Position mapRow(ResultSet rs, int rowNum) throws SQLException {
                    long id = rs.getLong("id");
                    int floor = rs.getInt("floor");
                    int positonNumber = rs.getInt("positionNumber");
                    String shelf = rs.getString("shelf");
                    double height = rs.getDouble("height");
                    double weight = rs.getDouble("weight");
                    double lenght = rs.getDouble("lenght");
                    double BearingCapacity = rs.getDouble("BearingCapacity");
                    Map<Product, Integer> productIntegerMap = DaoFactory.INSTANCE.getProductDao().productOnPosiiton(id);
                    return new Position(id, floor, positonNumber, shelf, height, weight, lenght, BearingCapacity, productIntegerMap);
                }
            });
        } catch (DataAccessException e) {
            throw new EntityNotFoundException("posiitia nie existuje");
        }
    }
   /* @Override
    public Position getById(Long id) throws EntityNotFoundException {
        try {
            String sql = "SELECT * FROM positions where id like " + id;
            return jdbcTemplate.queryForObject(sql, new RowMapper<Position>() {
                @Override
                public Position mapRow(ResultSet rs, int rowNum) throws SQLException {
                    long id = rs.getLong("idPosition");
                    int floor = rs.getInt("floor");
                    int positonNumber = rs.getInt("positionNumber");
                    String shelf = rs.getString("shelf");
                    double height = rs.getDouble("height");
                    double weight = rs.getDouble("weight");
                    double lenght = rs.getDouble("length");
                    double BearingCapacity = rs.getDouble("BearingCapacity");
                    return new Position(id, floor, positonNumber, shelf, height, weight, lenght, BearingCapacity);
                }
            });
        } catch (DataAccessException e) {
            throw new EntityNotFoundException("posiitia nie existuje");
        }
    }*/

    @Override
    public Map<Position, Double> fullnessOfPositionV() {
        List<Position> positionList = getAll();
        Map<Position, Double> positionDoubleMap = new HashMap<>();
        for (int i = 0; i < getAll().size(); i++) {
            double getСapacityOfPosition = getСapacityOfPositionV(positionList.get(i).getIdPosiiton());
            double VofProducts = 0;
            Map<Product, Integer> productIntegerMap = DaoFactory.INSTANCE.getProductDao().productOnPosiiton(positionList.get(i));
            for (Product product : productIntegerMap.keySet()) {
                double VofProducts1 = product.getHeight() * product.getLength() * product.getWidth();
                int count = productIntegerMap.get(product);
                VofProducts += VofProducts1 * count;
            }
            positionDoubleMap.put(positionList.get(i), 100 - ((VofProducts / getСapacityOfPosition) * 100));

        }
        return positionDoubleMap;
    }


    @Override
    public Double getСapacityOfPositionV(Long positionId) {
        Position position = DaoFactory.INSTANCE.getPositionDao().getById(positionId);
        return position.getHeight() * position.getWeight() * position.getLength();
    }

    @Override
    public Position getByName(String name) {
        String sql = "SELECT * FROM positions where positionNUmber like " + name;
        return jdbcTemplate.queryForObject(sql, new RowMapper<Position>() {
            @Override
            public Position mapRow(ResultSet rs, int rowNum) throws SQLException {
                long id = rs.getLong("id");
                int floor = rs.getInt("floor");
                int positonNumber = rs.getInt("positionNumber");
                String shelf = rs.getString("shelf");
                double height = rs.getDouble("height");
                double weight = rs.getDouble("weight");
                double lenght = rs.getDouble("lenght");
                double BearingCapacity = rs.getDouble("BearingCapacity");
                return new Position(id, floor, positonNumber, shelf, height, weight, lenght, BearingCapacity);
            }
        });
    }
    public boolean СapacityСheckProduct(Position position, Product product, int count) {
        double productCapacity = product.getHeight() * product.getWidth() * product.getLength() * count;
        double productCapacityM = product.getWeight() * count;
        double positionCapacity = DaoFactory.INSTANCE.getPositionDao().getСapacityOfPositionV(position.getIdPosiiton());
        double positionCapacityM = DaoFactory.INSTANCE.getPositionDao().getById(position.getIdPosiiton()).getBearingCapacity();
        List<Position> positionList = DaoFactory.INSTANCE.getPositionDao().getAll();
        Map<Product, Integer> allProductOnPosition = new HashMap<>();
        for (int i = 0; i < positionList.size(); i++) {
            if (positionList.get(i).getIdPosiiton() == position.getIdPosiiton()) {
                allProductOnPosition = positionList.get(i).getProducts();
                break;
            }
        }
        double fullnestOfPosition = 0;
        double fullnestOfPositionM = 0;
        for (Product product1 : allProductOnPosition.keySet()) {
            fullnestOfPosition += (product1.getHeight() * product1.getWidth() * product1.getLength()) * allProductOnPosition.get(product1);
            fullnestOfPositionM += product1.getWeight() * allProductOnPosition.get(product1);
        }
        if (positionCapacity - fullnestOfPosition >= productCapacity && positionCapacityM - fullnestOfPositionM >= productCapacityM) {
            return true;
        } else {
            return false;
        }
    }
    @Override
    public void setProductOnPosition(Product product, Position position, int count) {
        if (СapacityСheckProduct(position, product, count)) {
            List<Position> positionList = DaoFactory.INSTANCE.getPositionDao().getAll();
            Map<Product, Integer> allProductOnPosition = new HashMap<>();
            for (int i = 0; i < positionList.size(); i++) {
                if (positionList.get(i).getIdPosiiton() == position.getIdPosiiton()) {
                    allProductOnPosition = positionList.get(i).getProducts();
                    break;
                }
            }
            int countOnPosition = Integer.MIN_VALUE;
            for (Product product1 : allProductOnPosition.keySet()) {
                if (product1.getIdProduct() == product.getIdProduct()) {
                    countOnPosition = allProductOnPosition.get(product1);
                    break;
                }
            }
            if (countOnPosition == Integer.MIN_VALUE) {
                String sql = "INSERT INTO prosuctonposition (`idProduct`, `idPosition`, `count`) VALUES (?,?,?);";
                jdbcTemplate.update(sql, product.getIdProduct(), position.getIdPosiiton(), count);
            } else {
                String sql = "UPDATE prosuctonposition SET count = ? WHERE idProduct = ? ;";
                jdbcTemplate.update(sql, countOnPosition + count, product.getIdProduct());
            }
        } else {
            throw new EntityNotFoundException("produkt nie je mozne vllozit na poziciu");
        }
    }

    @Override
    public List<ProduktOnPositionHelp> getAllInfoAboutOrderOnPosition() {
        return jdbcTemplate.query("SELECT idProduct,idPosition,count,floor,positionNumber,shelf FROM prosuctonposition join positions on positions.id = prosuctonposition.idPosition;", new RowMapper<ProduktOnPositionHelp>() {
            @Override
            public ProduktOnPositionHelp mapRow(ResultSet rs, int rowNum) throws SQLException {
                String name = rs.getString("idProduct");
                int count = rs.getInt("count");
                String floor = rs.getString("floor");
                String positionNumber =rs.getString("positionNumber");
                String shelf = rs.getString("shelf");

                return new ProduktOnPositionHelp(name, floor+""+shelf+""+positionNumber, count);
            }
        });
    }
    @Override
    public List<String> getALlNames() throws EntityNotFoundException {
        String sql = "SELECT positionNumber FROM positions order by id asc;";
        return jdbcTemplate.query(sql, new RowMapper<String>() {
            @Override
            public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                String category = rs.getString("positionNumber");
                return category;
            }
        });
    }

    @Override
    public ProduktOnPositionHelp deleteInfo(ProduktOnPositionHelp produktOnPositionHelp) {
        try {
            String sql = "DELETE from prosuctonposition where idProduct =" + produktOnPositionHelp.getName() + " and idPosition = " + produktOnPositionHelp.getNumber();
            jdbcTemplate.update(sql);
        } catch (DataAccessException e) {
            throw new EntityNotFoundException("nie je mozne zmazat Poziciu");
        }
        return produktOnPositionHelp;
    }
    public Long getIdPositionByProduct(Product product, int pocet) {
        String sql = "SELECT * FROM prosuctonposition where idProduct = ? and count>=? limit 1";
        return jdbcTemplate.queryForObject(sql, new RowMapper<Long>() {
            @Override
            public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
                Long id = rs.getLong("idPosition");
                return id;
            }
        },product.getIdProduct(),pocet);
    }
}
