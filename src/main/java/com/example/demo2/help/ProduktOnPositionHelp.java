package com.example.demo2.help;

public class ProduktOnPositionHelp {
    private String name;
    private String number;
    private int count;

    public ProduktOnPositionHelp(String name, String number, int count) {
        this.name = name;
        this.number = number;
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
