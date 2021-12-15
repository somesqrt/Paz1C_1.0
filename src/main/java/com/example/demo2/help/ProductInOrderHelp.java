package com.example.demo2.help;

public class ProductInOrderHelp {
    private String name;
    private String count;
    private String pozicia;

    public ProductInOrderHelp(String name, String count, String pozicia) {
        this.name = name;
        this.count = count;
        this.pozicia = pozicia;
    }

    public String getPozicia() {
        return pozicia;
    }

    public void setPozicia(String pozicia) {
        this.pozicia = pozicia;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public ProductInOrderHelp(String name, String count) {

        this.name = name;
        this.count = count;
    }
}
