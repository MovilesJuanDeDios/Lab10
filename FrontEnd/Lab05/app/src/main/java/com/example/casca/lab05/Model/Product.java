package com.example.casca.lab05.Model;

/**
 * Created by casca on 16/04/2018.
 */

public class Product {
    private int id;
    private String title;
    private String shortdesc;
    private int cantidad;
    private int price;
    private int image;

    public Product(int id, String title, String shortdesc, int cantidad, int price, int image) {
        this.id = id;
        this.title = title;
        this.shortdesc = shortdesc;
        this.cantidad = cantidad;
        this.price = price;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getShortdesc() {
        return shortdesc;
    }

    public int getCantidad() {
        return cantidad;
    }

    public int getPrice() {
        return price;
    }

    public int getImage() {
        return image;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
