package com.example.demo2.sqldaos;

import com.example.demo2.DaoFactory;
import com.example.demo2.EntityNotFoundException;
import com.example.demo2.classes.Categories;
import com.example.demo2.classes.Position;
import com.example.demo2.classes.Product;
import com.example.demo2.classes.User;
import com.example.demo2.daos.CategoriesDAO;
import com.example.demo2.daos.PositionDao;
import com.example.demo2.daos.ProductDao;
import com.example.demo2.help.ProduktOnPositionHelp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class MysqlProductDaoTest {
    ProductDao productDao;
    PositionDao positionDao;

    CategoriesDAO categoriesDAO;

    private  Categories categoria = new Categories(
            "asdasdas"
    );

    private Product product = new Product(
            "testName",
            "testManufakture",
            "12354223",
            1.1,
            11.3,
            1.3,
            1.6,
            59,
            3,
            new Categories(4L, "BUQN")
    );
    private Position position = new Position(
            3,
            6,
            "asd",
            2341.5,
            2311.8,
            9871.3,
            10001
    );
    private int count = 3;

    public MysqlProductDaoTest() {
        DaoFactory.INSTANCE.testing();
        categoriesDAO = DaoFactory.INSTANCE.getcategoriesDAO();
        productDao = DaoFactory.INSTANCE.getProductDao();
        positionDao = DaoFactory.INSTANCE.getPositionDao();
    }

    Product testProduct;

    @BeforeEach
    void setUp() {
        testProduct = new Product(
                "name",
                "manufacture",
                "23432432",
                233.5,
                "testTaste",
                1.4,
                1.56,
                1.2,
                4,
                2,
                new Categories(1L, "AAAJQDXMRVHYII")
        );
    }

    @Test
    void getAll() {
        List<Product> all = productDao.getAll();
        assertTrue(all.size() >= 0);
        assertNotNull(all);
        for (int i = 0; i < all.size(); i++) {
            assertNotNull(all.get(i));
        }
    }

    @Test
    void save() {
        categoria = categoriesDAO.save(categoria);
        product.setCategories(categoria);
        List<Product> all = productDao.getAll();
        product = productDao.save(product);
        assertEquals(product.getIdProduct(), product.getIdProduct());
        assertEquals(all.size() + 1, productDao.getAll().size());
        product.setName("testuu");
        Product savedProduct1 = productDao.save(product);
        assertEquals(savedProduct1.getIdProduct(), product.getIdProduct());
        productDao.delete(product.getIdProduct());
        categoria = categoriesDAO.delete(categoria.getIdCategories());
    }

    @Test
    void delete() {
        List<Product> all = productDao.getAll();
        assertThrows(EntityNotFoundException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                productDao.delete((long) (all.size() + 1));
            }
        });
        assertThrows(EntityNotFoundException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                productDao.delete(-1L);
            }
        });
        categoria = categoriesDAO.save(categoria);
        product.setCategories(categoria);
        product = productDao.save(product);
        Product delete = productDao.delete(product.getIdProduct());
        assertEquals(product.getIdProduct(), delete.getIdProduct());

        assertThrows(EntityNotFoundException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                productDao.delete(delete.getIdProduct());
            }
        });
        categoria = categoriesDAO.delete(categoria.getIdCategories());
    }

    @Test
    void getbyId() {
        assertThrows(EntityNotFoundException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                productDao.getbyId(-1L);
            }
        });
        List<Product> all = productDao.getAll();
        assertThrows(EntityNotFoundException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                productDao.getbyId((long) (all.size() + 1));
            }
        });
        categoria = categoriesDAO.save(categoria);
        product.setCategories(categoria);
        product = productDao.save(product);
        Product getbyID = productDao.getbyId(product.getIdProduct());
        Product deleteProduct = productDao.delete(product.getIdProduct());

        assertEquals(getbyID.getIdProduct(), deleteProduct.getIdProduct());
        assertEquals(getbyID.getIdProduct(), product.getIdProduct());
        categoria = categoriesDAO.delete(categoria.getIdCategories());
    }

    @Test
    void getbyCategory() {
        assertThrows(EntityNotFoundException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                productDao.getbyId(null);
            }
        });
        categoria = categoriesDAO.save(categoria);
        product.setCategories(categoria);
        product = productDao.save(product);
        List<Product> products = productDao.getbyCategory(product.getCategories());
        assertNotNull(products);
        for (int i = 0; i < products.size(); i++) {
            assertNotNull(products.get(i));
        }
        assertTrue(products.size()>0);
        productDao.delete(product.getIdProduct());
        categoria = categoriesDAO.delete(categoria.getIdCategories());
    }

    @Test
    void productOnPosiiton() {
        //Categories testCategories = categoriesDAO.save(5L,"TestCategories");
        categoria = categoriesDAO.save(categoria);
        product.setCategories(categoria);
        //product.setIdProduct(1L);
        product = productDao.save(product);
        position = positionDao.save(position);
        positionDao.setProductOnPosition(product, position, count);
        Map<Product, Integer> productIntegerMap = productDao.productOnPosiiton(position);
        assertNotNull(productIntegerMap);
        positionDao.deleteInfo(new ProduktOnPositionHelp(
                String.valueOf(product.getIdProduct()),
                String.valueOf(position.getIdPosiiton()),
                count
        ));
        position = positionDao.delete(position.getIdPosiiton());
        productDao.delete(product.getIdProduct());
        categoria = categoriesDAO.delete(categoria.getIdCategories());
        //categoriesDAO.delete(testCategories.getIdCategories());

    }
    @Test
    void searchProduct() {
        categoria = categoriesDAO.save(categoria);
        product.setCategories(categoria);
        product = productDao.save(product);
        testProduct.setIdProduct(product.getIdProduct());
        List<Product> products = productDao.searchProduct("\"" + testProduct.getName() + "\"", "\"" + testProduct.getEAN() + "\"", "\"" + testProduct.getCategories().getCategoria() + "\"");
        assertNotNull(products);
        assertTrue(products.size() >= 0);
        for (int i = 0; i < products.size(); i++) {
            assertNotNull(products.get(i));
        }

        productDao.delete(testProduct.getIdProduct());
        categoria = categoriesDAO.delete(categoria.getIdCategories());
    }

    @Test
    void getByName() {
        assertThrows(EntityNotFoundException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                productDao.getByName("");
            }
        });
        assertThrows(EntityNotFoundException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                productDao.getByName(null);
            }
        });
        categoria = categoriesDAO.save(categoria);
        product.setCategories(categoria);
        product = productDao.save(product);

        String names = product.getName();
        Product getByName = productDao.getByName(product.getName());
        Product deleteProduct = productDao.delete(product.getIdProduct());

        assertEquals(getByName.getIdProduct(), deleteProduct.getIdProduct());
        assertEquals(getByName.getIdProduct(), product.getIdProduct());
        categoria = categoriesDAO.delete(categoria.getIdCategories());

    }
}