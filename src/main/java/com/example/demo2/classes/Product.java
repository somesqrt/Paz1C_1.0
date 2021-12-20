package com.example.demo2.classes;

import java.util.Objects;

public class Product {
    private Long idProduct;
    private String Name;
    private String Manufacture;
    private String EAN;
    private double weight;
    private String taste;
    private double height;
    private double length;
    private double width;
    private int price;
    private int piecesInPackage;
    private Categories Categories;

    public Product(String name, String manufacture, String EAN, double weight, String taste, double height, double length, double width, int price, int piecesInPackage, com.example.demo2.classes.Categories categories) {
        Name = name;
        Manufacture = manufacture;
        this.EAN = EAN;
        this.weight = weight;
        this.taste = taste;
        this.height = height;
        this.length = length;
        this.width = width;
        this.price = price;
        this.piecesInPackage = piecesInPackage;
        Categories = categories;
    }

    public Product(String name, String manufacture, String EAN, double weight, double height, double length, double width, int price, int piecesInPackage, com.example.demo2.classes.Categories categories) {
        Name = name;
        Manufacture = manufacture;
        this.EAN = EAN;
        this.weight = weight;
        this.height = height;
        this.length = length;
        this.width = width;
        this.price = price;
        this.piecesInPackage = piecesInPackage;
        Categories = categories;
    }

    public Product(Long idProduct, String name, String manufacture, String EAN, double weight, String taste, double height, double length, double width, int price, int piecesInPackage, Categories categories) {
        this.idProduct = idProduct;
        Name = name;
        Manufacture = manufacture;
        this.EAN = EAN;
        this.weight = weight;
        this.taste = taste;
        this.height = height;
        this.length = length;
        this.width = width;
        this.price = price;
        this.piecesInPackage = piecesInPackage;
        Categories = categories;
    }


    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Long getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(Long idProduct) {
        this.idProduct = idProduct;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getManufacture() {
        return Manufacture;
    }

    public void setManufacture(String manufacture) {
        Manufacture = manufacture;
    }

    public String getEAN() {
        return EAN;
    }

    public void setEAN(String EAN) {
        this.EAN = EAN;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getTaste() {
        return taste;
    }

    public void setTaste(String taste) {
        this.taste = taste;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public int getPiecesInPackage() {
        return piecesInPackage;
    }

    public void setPiecesInPackage(int piecesInPackage) {
        this.piecesInPackage = piecesInPackage;
    }

    public Categories getCategories() {
        return Categories;
    }

    public void setCategories(Categories categories) {
        Categories = categories;
    }

    @Override
    public String toString() {
        return "Product{" +
                "idProduct=" + idProduct +
                ", Name='" + Name + '\'' +
                ", Manufacture='" + Manufacture + '\'' +
                ", EAN='" + EAN + '\'' +
                ", weight=" + weight +
                ", taste='" + taste + '\'' +
                ", height=" + height +
                ", length=" + length +
                ", width=" + width +
                ", price=" + price +
                ", piecesInPackage=" + piecesInPackage +
                ", Categories=" + Categories +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;
        Product product = (Product) o;
        return Double.compare(product.weight, weight) == 0 && Double.compare(product.height, height) == 0 && Double.compare(product.length, length) == 0 && Double.compare(product.width, width) == 0 && price == product.price && piecesInPackage == product.piecesInPackage && Objects.equals(idProduct, product.idProduct) && Name.equals(product.Name) && Manufacture.equals(product.Manufacture) && EAN.equals(product.EAN) && Objects.equals(taste, product.taste) && Categories.equals(product.Categories);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idProduct, Name, Manufacture, EAN, weight, taste, height, length, width, price, piecesInPackage, Categories);
    }
}
