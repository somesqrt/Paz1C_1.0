package com.example.demo2.classes;

public class ProductInOrder {
    long id;
    Product product;
    long idproduct;
    Double count;

    public ProductInOrder(long id, long idproduct, Double count) {
        this.id = id;
        this.idproduct = idproduct;
        this.count = count;
    }

    public long getIdproduct() {
        return idproduct;
    }

    public void setIdproduct(long idproduct) {
        this.idproduct = idproduct;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Double getCount() {
        return count;
    }

    public void setCount(Double count) {
        this.count = count;
    }

    public ProductInOrder(long id, Product product, Double count) {
        this.id = id;
        this.product = product;
        this.count = count;
    }
}
