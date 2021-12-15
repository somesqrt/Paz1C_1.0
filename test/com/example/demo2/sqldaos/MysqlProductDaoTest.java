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
            new Categories(4L, "BUQN")
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
                123.4,
                324.56,
                35445.2,
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
        List<Product> all = productDao.getAll();
        Product savedProduct = productDao.save(testProduct);
        testProduct.setIdProduct(savedProduct.getIdProduct());
        assertEquals(testProduct.getIdProduct(), savedProduct.getIdProduct());
        assertEquals(all.size() + 1, productDao.getAll().size());
        savedProduct.setName("testuu");
        Product savedProduct1 = productDao.save(savedProduct);
        assertEquals(savedProduct1.getIdProduct(), savedProduct.getIdProduct());
        productDao.delete(savedProduct.getIdProduct());
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

        Product save = productDao.save(testProduct);
        Product delete = productDao.delete(save.getIdProduct());
        assertEquals(save.getIdProduct(), delete.getIdProduct());

        assertThrows(EntityNotFoundException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                productDao.delete(delete.getIdProduct());
            }
        });
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

        Product savedproduct = productDao.save(testProduct);
        testProduct.setIdProduct(savedproduct.getIdProduct());
        Product getbyID = productDao.getbyId(savedproduct.getIdProduct());
        Product deleteProduct = productDao.delete(savedproduct.getIdProduct());

        assertEquals(getbyID.getIdProduct(), deleteProduct.getIdProduct());
        assertEquals(getbyID.getIdProduct(), savedproduct.getIdProduct());

    }

    @Test
    void getbyCategory() {
        assertThrows(EntityNotFoundException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                productDao.getbyId(null);
            }
        });

        Product saveProduct = productDao.save(testProduct);
        List<Product> products = productDao.getbyCategory(testProduct.getCategories());
        assertNotNull(products);
        for (int i = 0; i < products.size(); i++) {
            assertNotNull(products.get(i));
        }
        assertTrue(products.size()>0);
        productDao.delete(saveProduct.getIdProduct());
    }

    @Test
    void productOnPosiiton() {
        //Categories testCategories = categoriesDAO.save(5L,"TestCategories");

        //product.setIdProduct(1L);
        Product save = productDao.save(product);
        product.setIdProduct(save.getIdProduct());
        position.setIdPosiiton(1L);
        positionDao.setProductOnPosition(product, position, count);
        Map<Product, Integer> productIntegerMap = productDao.productOnPosiiton(position);
        assertNotNull(productIntegerMap);
        positionDao.deleteInfo(new ProduktOnPositionHelp(
                String.valueOf(product.getIdProduct()),
                String.valueOf(position.getIdPosiiton()),
                count
        ));
        productDao.delete(save.getIdProduct());
        //categoriesDAO.delete(testCategories.getIdCategories());

    }

    @Test
    void сapacityСheckProduct() {
    }

    @Test
    void testProductOnPosiiton() {
    }

    @Test
    void putProductOnPosition() {
    }

    @Test
    void transferProductOnAnotherPosition() {
    }

    @Test
    void searchProduct() {
        Product saved = productDao.save(testProduct);
        testProduct.setIdProduct(saved.getIdProduct());
        List<Product> products = productDao.searchProduct("\"" + testProduct.getName() + "\"", "\"" + testProduct.getEAN() + "\"", "\"" + testProduct.getCategories().getCategoria() + "\"");
        assertNotNull(products);
        assertTrue(products.size() >= 0);
        for (int i = 0; i < products.size(); i++) {
            assertNotNull(products.get(i));
        }

        productDao.delete(testProduct.getIdProduct());
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

        Product savedproduct = productDao.save(testProduct);
        testProduct.setIdProduct(savedproduct.getIdProduct());
        Product getByName = productDao.getByName(savedproduct.getName());
        Product deleteProduct = productDao.delete(savedproduct.getIdProduct());

        assertEquals(getByName.getIdProduct(), deleteProduct.getIdProduct());
        assertEquals(getByName.getIdProduct(), savedproduct.getIdProduct());

    }
}