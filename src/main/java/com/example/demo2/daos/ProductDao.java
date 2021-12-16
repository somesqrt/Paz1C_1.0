package com.example.demo2.daos;




import com.example.demo2.EntityNotFoundException;
import com.example.demo2.classes.Categories;
import com.example.demo2.classes.Position;
import com.example.demo2.classes.Product;

import java.util.List;
import java.util.Map;

public interface ProductDao {
    List<Product> getAll();
    Product save(Product product);
    Product delete(Long id);
    Product getbyId(Long id);
    List<Product> getbyCategory(Categories categories);
    Map<Product,Integer> productOnPosiiton(Position position);

    List<String> getALlNames() throws EntityNotFoundException;

    boolean СapacityСheckProduct(Position position, Product product, int count);

    Map<Product, Integer> productOnPosiiton(Long position);

    void putProductOnPosition(Position position, Product product, int count);
    void transferProductOnAnotherPosition(Position positionfrom,Product product,Position positionfor,int count);

    List<Product> searchProduct(String nameOfProductSearch, String eanOfProductSearch, String categoriOfProductSearch);

    Product getByName(String name);
}
