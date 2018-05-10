package com.example.casca.widgets.Model;

import java.io.Serializable;

public class Usuario implements Serializable {

    private String userName;
    private String clave;
    private int rol;

    public Usuario() {
    }

    public Usuario(String userName, String clave, int rol) {
        this.userName = userName;
        this.clave = clave;
        this.rol = rol;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String cedula) {
        this.userName = userName;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public int getRol() { return rol; }

    public void setRol(int rol) { this.rol = rol;  }

}