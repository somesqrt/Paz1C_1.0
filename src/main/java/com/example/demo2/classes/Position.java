package com.example.demo2.classes;

import java.util.HashMap;
import java.util.Map;

public class Position {
    private Long idPosiiton;
    private int floor;
    private int positionNumber;
    private String shelf;
    private double height;
    private double weight;
    private double length;
    private double BearingCapacity;
    private Map<Product,Integer> products =new HashMap<>();

    public Position(int floor, int positionNumber, String shelf, double height, double weight, double length, double bearingCapacity) {
        this.floor = floor;
        this.positionNumber = positionNumber;
        this.shelf = shelf;
        this.height = height;
        this.weight = weight;
        this.length = length;
        BearingCapacity = bearingCapacity;
    }

    public Position(int floor, int positionNumber, String shelf, double height, double weight, double length) {
        this.floor = floor;
        this.positionNumber = positionNumber;
        this.shelf = shelf;
        this.height = height;
        this.weight = weight;
        this.length = length;
    }

    public Position(Long idPosiiton, int floor, int positionNumber, String shelf, double height, double weight, double length, double bearingCapacity) {
        this.idPosiiton = idPosiiton;
        this.floor = floor;
        this.positionNumber = positionNumber;
        this.shelf = shelf;
        this.height = height;
        this.weight = weight;
        this.length = length;
        BearingCapacity = bearingCapacity;
    }

    public Position(Long idPosiiton, int floor, int positionNumber, String shelf, double height, double weight, double length, double bearingCapacity, Map<Product,Integer> products) {
        this.idPosiiton = idPosiiton;
        this.floor = floor;
        this.positionNumber = positionNumber;
        this.shelf = shelf;
        this.height = height;
        this.weight = weight;
        this.length = length;
        BearingCapacity = bearingCapacity;
        this.products = products;
    }

    public Map<Product,Integer> getProducts() {
        return products;
    }

    public void setProducts(Map<Product,Integer> products) {
        this.products = products;
    }

    public Long getIdPosiiton() {
        return idPosiiton;
    }

    public void setIdPosiiton(Long idPosiiton) {
        this.idPosiiton = idPosiiton;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public int getPositionNumber() {
        return positionNumber;
    }

    public void setPositionNumber(int positionNumber) {
        this.positionNumber = positionNumber;
    }

    public String getShelf() {
        return shelf;
    }

    public void setShelf(String shelf) {
        this.shelf = shelf;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public double getBearingCapacity() {
        return BearingCapacity;
    }

    public void setBearingCapacity(double bearingCapacity) {
        BearingCapacity = bearingCapacity;
    }

    @Override
    public String toString() {
        return "\nPosition{" +
                "idPosiiton=" + idPosiiton +
                ", floor=" + floor +
                ", positionNumber=" + positionNumber +
                ", shelf='" + shelf + '\'' +
                ", height=" + height +
                ", weight=" + weight +
                ", length=" + length +
                ", BearingCapacity=" + BearingCapacity +
                ", products=" + products +
                '}';
    }
}
