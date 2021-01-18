package com.dss.p4dss.productos;

public class Producto {
    public int id;
    public String name;
    public String family;
    public double price;

    public Producto(int id, String name, String family, double price) {
        this.id = id;
        this.name = name;
        this.family = family;
        this.price = price;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
