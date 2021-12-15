package com.example.demo2.classes;

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
        return "\nProduct{" +
                "idProduct=" + idProduct +
                ", Name='" + Name + '\'' +
                ", Manufacture='" + Manufacture + '\'' +
                ", EAN='" + EAN + '\'' +
                ", weight=" + weight +
                ", taste='" + taste + '\'' +
                ", height=" + height +
                ", length=" + length +
                ", width=" + width +
                ", piecesInPackage=" + piecesInPackage +
                ", Categories=" + Categories +
                '}';
    }
}
