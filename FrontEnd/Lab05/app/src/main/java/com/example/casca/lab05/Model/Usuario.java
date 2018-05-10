package com.example.casca.lab05.Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Usuario implements Serializable {

    private String nombre;
    private String email;
    private String username;
    private String clave;
    private int rol;
    List<Product> listaProductos;

    public Usuario() {
    }

    public Usuario(String nombre, String email, String username, String clave, int rol) {
        this.nombre = nombre;
        this.email = email;
        this.username = username;
        this.clave = clave;
        this.rol = rol;
        listaProductos = new ArrayList<>();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public int getRol() {
        return rol;
    }

    public void setRol(int rol) {
        this.rol = rol;
    }

    public List<Product> getListaProductos() {
        return listaProductos;
    }

}