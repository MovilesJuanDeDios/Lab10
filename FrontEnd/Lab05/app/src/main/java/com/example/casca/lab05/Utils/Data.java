package com.example.casca.lab05.Utils;


import com.example.casca.lab05.Model.Product;
import com.example.casca.lab05.Model.Usuario;

import java.util.ArrayList;
import java.util.List;

public class Data {
    public static String url="http://192.168.1.52:8083/Servlet/Servlet?accion=";
    public static Usuario usuario = new Usuario();
    public static final List<Usuario> listaUsuarios = new ArrayList<>();
    public static final List<Product> listaProductos = new ArrayList<>();
}
