package com.dss.p4dss.productos;

public class Producto {
    public String id;
    public String tag;
    public String uuid;

    public Producto(String id, String tag, String uuid) {
        this.id = id;
        this.tag = tag;
        this.uuid = uuid;
    }

    @Override
    public String toString() {
        return this.tag;
    }
}
