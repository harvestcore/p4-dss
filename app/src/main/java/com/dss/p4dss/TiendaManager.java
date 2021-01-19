package com.dss.p4dss;

import android.content.Context;

import com.dss.p4dss.API.APIConnector;
import com.dss.p4dss.credentials.Credentials;
import com.dss.p4dss.productos.Producto;

import java.util.ArrayList;

public class TiendaManager {
    private static TiendaManager tiendaManager;
    private static String baseURL = "http://192.168.1.110:8080/GomezMartinAngel/api";

    APIConnector apiConnector;
    Credentials credentials;

    ///////////////

    ArrayList<Producto> productos;
    ArrayList<Producto> carrito;

    ///////////////

    private TiendaManager() {
        apiConnector = new APIConnector();

        productos = new ArrayList<>();
        carrito = new ArrayList<>();
    }

    public static TiendaManager getInstance() {
        if (tiendaManager == null) {
            tiendaManager = new TiendaManager();
        }

        return tiendaManager;
    }

    public void setContext(Context context) {
        apiConnector.setContext(context);
    }

    public String getServerURL() { return this.baseURL; }

    public ArrayList<Producto> getProductos () { return this.productos; }

    public void setProductos(ArrayList<Producto> productos) { this.productos = productos; }

    public void fetchProductos() { this.apiConnector.getProductos(); }

    public void fetchCarrito() { this.apiConnector.getCarrito(); }

    public Credentials getCredentials() { return credentials; }

    public void setCredentials(Credentials credentials) { this.credentials = credentials; }

    public ArrayList<Producto> getCarrito () { return this.carrito; }

    public void setCarrito (ArrayList<Producto> carrito) { this.carrito = carrito; }

    public void agregarProductoACarrito(Producto p) {
        this.apiConnector.agregarProductoACarrito(p);
        this.getCarrito().add(p);
    }

    public void borrarProductoDeCarrito(Producto p) {
        this.apiConnector.borrarProductoDeCarrito(p);
        this.getCarrito().remove(p);
    }

    public void login(String username, String password) {
        this.apiConnector.login(username, password);
    }

    public void agregarProducto(Producto p) {
        this.apiConnector.addProducto(p);
    }

    public void agregarUsuario(Credentials c) {
        this.apiConnector.addUsuario(c);
    }
}
