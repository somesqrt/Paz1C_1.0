package com.example.demo2.daos;



import com.example.demo2.EntityNotFoundException;
import com.example.demo2.classes.Categories;

import java.util.List;

public interface CategoriesDAO {
    List<Categories> getAll();
    Categories save(Categories categories)  throws EntityNotFoundException;
    Categories delete(Long id)  throws EntityNotFoundException;
    Categories getbyID(Long id)  throws EntityNotFoundException;
    List<String> getALlNames() throws EntityNotFoundException;
    Long getByName(String name) throws EntityNotFoundException;
}
