package com.example.demo2.sqldaos;

import com.example.demo2.DaoFactory;
import com.example.demo2.EntityNotFoundException;
import com.example.demo2.classes.Categories;
import com.example.demo2.classes.Position;
import com.example.demo2.classes.Product;
import com.example.demo2.daos.CategoriesDAO;
import com.example.demo2.daos.PositionDao;
import com.example.demo2.daos.ProductDao;
import com.example.demo2.help.ProduktOnPositionHelp;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class MysqlPositionDaoTest {
     PositionDao positionDao;
    Position testPosition;
    ProductDao productDao;
    CategoriesDAO categoriesDAO;
    boolean deletable = true;

    private Product product = new Product(
            "testName",
            "testManufakture",
            "12354223",
            123.1,
            4532.3,
            23.3,
            543.6,
            59,
            3,
            new Categories("TestCategories")
    );
    private Position position = new Position(
            3,
            6,
            "asd",
            234.5,
            23.8,
            987.3
    );
    private int count = 3142;

    public MysqlPositionDaoTest() {
        DaoFactory.INSTANCE.testing();
        positionDao = DaoFactory.INSTANCE.getPositionDao();
        categoriesDAO = DaoFactory.INSTANCE.getcategoriesDAO();
        productDao = DaoFactory.INSTANCE.getProductDao();
    }

    @BeforeEach
    void setUp() {
        testPosition = new Position(1, 10, "c", 150.25, 130.25, 10, 5000);
    }

    @AfterEach
    void tearDown() {
        if (testPosition != null && deletable == true)
            try {
                positionDao.delete(testPosition.getIdPosiiton());
            }catch (Exception e ){

            }
        deletable = true;
    }

    @Test
    void getAll() {
        List<Position> positionList = positionDao.getAll();
        assertTrue(positionList.size() >= 0);
        assertTrue(positionList != null);
        for (int i = 0; i < positionList.size(); i++) {
            assertNotNull(positionList.get(i));
        }
        deletable = false;
    }

    @Test
    void save() {
        Position savePosition = positionDao.save(testPosition);
        testPosition.setIdPosiiton(savePosition.getIdPosiiton());
        assertEquals(savePosition.getIdPosiiton(), testPosition.getIdPosiiton());

        Long id = savePosition.getIdPosiiton();
        savePosition.setIdPosiiton(-1L);
        assertThrows(EntityNotFoundException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                positionDao.save(savePosition);
            }
        });
        savePosition.setIdPosiiton(id);
        savePosition.setPositionNumber(2);
        Position udpdated = positionDao.save(savePosition);
        assertEquals(savePosition.getIdPosiiton(), udpdated.getIdPosiiton());
    }

    @Test
    void delete() {
        int size = positionDao.getAll().size();
        assertThrows(EntityNotFoundException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                positionDao.delete((long) size + 1);
            }
        });
        assertThrows(EntityNotFoundException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                positionDao.delete(-1L);
            }
        });
        Position saved = positionDao.save(testPosition);
        Position saved2 = positionDao.delete(saved.getIdPosiiton());
        assertEquals(saved.getIdPosiiton(), saved2.getIdPosiiton());
        assertThrows(EntityNotFoundException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                positionDao.getById(saved.getIdPosiiton());
            }

            ;
        });
        deletable = false;
    }

    @Test
    void getById() {
        assertThrows(EntityNotFoundException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                positionDao.getById(-1L);
            }
        });

        Position save = positionDao.save(testPosition);
        testPosition.setIdPosiiton(save.getIdPosiiton());
        assertEquals(save.getIdPosiiton(), positionDao.getById(save.getIdPosiiton()).getIdPosiiton());
    }

    @Test
    void fullnessOfPositionV() {
        Position position = new Position(1, 10, "c", 150.25, 130.25, 10, 5000);
        Product product = new Product("testProduct", "test", "fdsfsd", 130.25, 10, 150.25, 10, 130, 1, categoriesDAO
                .getbyID(1L));
        position = positionDao.save(position);
        Map<Position, Double> map = positionDao.fullnessOfPositionV();
        for (Position position1: map.keySet())
        {
            if(position1.getIdPosiiton().equals(position))
            {
                assertEquals(map.get(position1),100);
            }
        }
        map = positionDao.fullnessOfPositionV();
        for (Position position1: map.keySet())
        {
            if(position1.getIdPosiiton().equals(position))
            {
                assertEquals(map.get(position1),0);
            }
        }
        deletable = false;
    }

    @Test
    void getСapacityOfPositionV() {
        Position save = positionDao.save(testPosition);
        testPosition.setIdPosiiton(save.getIdPosiiton());
        double capacity = save.getHeight() * save.getWeight() * save.getLength();
        double capacityFromMethod = positionDao.getСapacityOfPositionV(save.getIdPosiiton());
        assertEquals(capacity, capacityFromMethod);
    }

    @Test
    void setProductOnPosition(){
        product.setIdProduct(1L);
        position.setIdPosiiton(1L);
        List<ProduktOnPositionHelp> info= positionDao.getAllInfoAboutOrderOnPosition();
        positionDao.setProductOnPosition(product,position,count);
        List<ProduktOnPositionHelp> info2= positionDao.getAllInfoAboutOrderOnPosition();
        assertTrue(info.size()<info2.size());
        positionDao.deleteInfo(new ProduktOnPositionHelp(
                String.valueOf(product.getIdProduct()),
                String.valueOf(position.getIdPosiiton()),
                count
        ));


    }

    @Test
    void getAllInfoAboutOrderOnPosition(){
        List<ProduktOnPositionHelp> allInfoAboutOrderOnPosition = positionDao.getAllInfoAboutOrderOnPosition();
        assertNotNull(allInfoAboutOrderOnPosition);
        assertTrue(allInfoAboutOrderOnPosition.size()>=0);
    }
}