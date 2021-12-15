package com.example.demo2.daos;


import com.example.demo2.classes.Order;
import com.example.demo2.classes.Product;
import com.example.demo2.classes.ProductInOrder;
import com.example.demo2.help.AllOrderHelp;
import com.example.demo2.help.ProductInOrderHelp;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface OrderDao {
    //Order save(Order order);

    void deleteOrder(Long id);

    void deleteOrderProduct(Long id);

    List<Order> getAll();


    List<ProductInOrder> getAllProductsInOrder(long id);

    List<ProductInOrder> getAllProductsInOrderWitthLongID(long id);

    void save(Order order, ArrayList<ProductInOrder> productInOrder, Long id);

    void update(Long id, String status);

    Order createOrder(Order order);
    Map<Product, Integer> getProductInOrder(Order order);
    Order update(Order order);
    Order getIdFromOrder(Order order);


    List<AllOrderHelp> getByFilters(String id, String name);

    ProductInOrderHelp getByOrder(String id);
}
