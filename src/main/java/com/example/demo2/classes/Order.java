package com.example.demo2.classes;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Order {
    private Long idOrder;
    private String Name;
    private double Summ;
    private String OrderStatus;
    private User SalesMan;
/*    private Date DateTime;*/
    private Map<Product, Integer> productsInOrder = new HashMap<>();


    public Order(String name, double summ, String orderStatus, User salesMan,/* Date dateTime,*/ Map<Product, Integer> productsInOrder) {
        Name = name;
        Summ = summ;
        OrderStatus = orderStatus;
        SalesMan = salesMan;
/*        DateTime = dateTime;*/
        this.productsInOrder = productsInOrder;
    }

    public Order(Long idOrder, String name, double summ, String orderStatus, User salesMan) {
        this.idOrder = idOrder;
        Name = name;
        Summ = summ;
        OrderStatus = orderStatus;
        SalesMan = salesMan;
/*        DateTime = dateTime;*/
    }

    public Order(String name, double summ, String orderStatus, User salesMan, Date dateTime) {
        Name = name;
        Summ = summ;
        OrderStatus = orderStatus;
        SalesMan = salesMan;
/*        DateTime = dateTime;*/
    }
/*
    public Date getDateTime() {
        return DateTime;
    }

    public void setDateTime(Date dateTime) {
        DateTime = dateTime;
    }*/

    public Order(Long idOrder, String name, double summ, String orderStatus, User salesMan, Map<Product, Integer> productsInOrder) {
        this.idOrder = idOrder;
        Name = name;
        Summ = summ;
        OrderStatus = orderStatus;
        SalesMan = salesMan;
        this.productsInOrder = productsInOrder;
    }

    public Map<Product, Integer> getProductsInOrder() {
        return productsInOrder;
    }
    public void setProductsInOrder(Map<Product, Integer> productsInOrder) {
        this.productsInOrder = productsInOrder;
    }

    public Long getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(Long idOrder) {
        this.idOrder = idOrder;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public double getSumm() {
        return Summ;
    }

    public void setSumm(double summ) {
        Summ = summ;
    }

    public String getOrderStatus() {
        return OrderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        OrderStatus = orderStatus;
    }

    public User getSalesMan() {
        return SalesMan;
    }

    public void setSalesMan(User salesMan) {
        SalesMan = salesMan;
    }

    @Override
    public String toString() {
        return "Order{" +
                "idOrder=" + idOrder +
                ", Name='" + Name + '\'' +
                ", Summ=" + Summ +
                ", OrderStatus='" + OrderStatus + '\'' +
                ", SalesMan=" + SalesMan +
/*                ", DateTime=" + DateTime +*/
                ", productsInOrder=" + productsInOrder +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order)) return false;
        Order order = (Order) o;
        return Double.compare(order.Summ, Summ) == 0 && Objects.equals(idOrder, order.idOrder) && Name.equals(order.Name) && OrderStatus.equals(order.OrderStatus) && SalesMan.equals(order.SalesMan) && productsInOrder.equals(order.productsInOrder);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idOrder, Name, Summ, OrderStatus, SalesMan, productsInOrder);
    }
}
