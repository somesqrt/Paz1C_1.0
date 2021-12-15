package com.example.demo2.help;

import com.example.demo2.classes.Product;
import com.example.demo2.classes.User;

import java.util.HashMap;
import java.util.Map;

public class AllOrderHelp {
    private Long idOrder;
    private String Name;
    private double Summ;
    private String OrderStatus;
    private String SalesMan;

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

    public String getSalesMan() {
        return SalesMan;
    }

    public void setSalesMan(String salesMan) {
        SalesMan = salesMan;
    }

    public AllOrderHelp(Long idOrder, String name, double summ, String orderStatus, String salesMan) {
        this.idOrder = idOrder;
        Name = name;
        Summ = summ;
        OrderStatus = orderStatus;
        SalesMan = salesMan;
    }
}
