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

    private  Categories categoria = new Categories(
            "asdasdas"
    );

    private Product product = new Product(
            "testName",
            "testManufakture",
            "12354223",
            1.1,
            4.3,
            2.3,
            5.6,
            59,
            3,
            new Categories(4L, "BUQN")
    );
    private Position position = new Position(
            3,
            6,
            "asd",
            23412.5,
            2322.8,
            98722.3,
            1000221
    );
    private int count = 3;

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
        Categories cat =  new Categories("test1111111111111");
        cat =  categoriesDAO.save(cat);
        Product product = new Product("testProduct", "test", "fdsfsd", 130.25, 10, 150.25, 10, 130, 1,cat);
        position = positionDao.save(position);
        product = productDao.save(product);
        Map<Position,Double> full = positionDao.fullnessOfPositionV();
        double fullnest = 0;
        for(Position position1:full.keySet())
        {
            if(position1.getIdPosiiton() == position.getIdPosiiton()){
                fullnest = full.get(position1);
                break;
            }
        }
        product.setCategories(cat);
        product = productDao.save(product);
        positionDao.setProductOnPosition(product,position,4);

        full = positionDao.fullnessOfPositionV();
        double fullnest1 = 0;
        for(Position position1:full.keySet())
        {
            if(position1.getIdPosiiton() == position.getIdPosiiton()){
                fullnest1 = full.get(position1);
                break;
            }
        }
       assertNotEquals(fullnest,fullnest1);
        positionDao.deleteInfo(new ProduktOnPositionHelp(String.valueOf(product.getIdProduct()),String.valueOf(position.getIdPosiiton()),4));
        full = positionDao.fullnessOfPositionV();
        double fullnest3 = 0;
        for(Position position1:full.keySet())
        {
            if(position1.getIdPosiiton() == position.getIdPosiiton()){
                fullnest3 = full.get(position1);
                break;
            }
        }
        assertEquals(fullnest3,fullnest);
        Product deleta =  productDao.delete(product.getIdProduct());
       assertEquals(deleta,product);
       Categories catdelete = categoriesDAO.delete(cat.getIdCategories());
       assertEquals(catdelete,cat);
       Position posdelete = positionDao.delete(position.getIdPosiiton());
       assertEquals(posdelete,position);
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
    void getAllInfoAboutOrderOnPosition(){
        List<ProduktOnPositionHelp> allInfoAboutOrderOnPosition = positionDao.getAllInfoAboutOrderOnPosition();
        assertNotNull(allInfoAboutOrderOnPosition);
        assertTrue(allInfoAboutOrderOnPosition.size()>=0);
    }
    @Test
    void setProductOnPosition(){
        categoria = categoriesDAO.save(categoria);
        product.setCategories(categoria);
        product = productDao.save(product);
        position = positionDao.save(position);
        List<ProduktOnPositionHelp> info= positionDao.getAllInfoAboutOrderOnPosition();
        positionDao.setProductOnPosition(product,position,count);
        List<ProduktOnPositionHelp> info2= positionDao.getAllInfoAboutOrderOnPosition();
        assertTrue(info.size()<info2.size());
        positionDao.deleteInfo(new ProduktOnPositionHelp(
                String.valueOf(product.getIdProduct()),
                String.valueOf(position.getIdPosiiton()),
                count
        ));
        position = positionDao.delete(position.getIdPosiiton());
        product = productDao.delete(product.getIdProduct());
        categoria = categoriesDAO.delete(categoria.getIdCategories());
    }
}