package com.example.demo2.classes;

public class Categories {
    private Long idCategories;
    private String Categoria;

    public Categories(String categoria) {
        Categoria = categoria;
    }

    public Categories(Long idCategories, String categoria) {
        this.idCategories = idCategories;
        Categoria = categoria;
    }

    public Long getIdCategories() {
        return idCategories;
    }

    public void setIdCategories(Long idCategories) {
        this.idCategories = idCategories;
    }

    public String getCategoria() {
        return Categoria;
    }

    public void setCategoria(String categoria) {
        Categoria = categoria;
    }

    @Override
    public String toString() {
        return Categoria;

    }
}
