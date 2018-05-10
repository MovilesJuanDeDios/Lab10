package com.example.casca.lab05.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.example.casca.lab05.Adapters.CarritoAdapter;
import com.example.casca.lab05.Model.Product;
import com.example.casca.lab05.Model.Usuario;
import com.example.casca.lab05.R;
import com.example.casca.lab05.Utils.Data;

import java.util.ArrayList;
import java.util.List;


public class Carrito extends AppCompatActivity {

    RecyclerView recyclerView;
    CarritoAdapter adapter;

    private Button button;

    private SharedPreferences sharedPref;
    private String username;

    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrito);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewCarrito);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        button = (Button) findViewById(R.id.comprar);

        sharedPref = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        username = sharedPref.getString(getString(R.string.user_pref), getString(R.string.default_user));

        tv = (TextView) findViewById(R.id.textViewMonto);

        if (username.equals("")) {
            adapter = new CarritoAdapter(this, new ArrayList<Product>());
        } else {
            adapter = new CarritoAdapter(this, getUsuario(username).getListaProductos());
            if (getUsuario(username).getListaProductos().size() > 0) {
                tv.setText(String.valueOf(calculaTotal()));
                button.setVisibility(View.VISIBLE);
            }
        }

        recyclerView.setAdapter(adapter);


        final Button button = findViewById(R.id.comprar);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intentf = new Intent(Carrito.this, Payment.class);
                startActivity(intentf);
                finish();
            }
        });
    }

    public Usuario getUsuario(String username) {
        Usuario aux = new Usuario();
        for (Usuario usr : Data.listaUsuarios) {
            if (usr.getUsername().equals(username))
                aux = usr;
        }
        return aux;
    }

    public int calculaTotal() {
        List<Product> lp = getUsuario(username).getListaProductos();
        int suma = 0;
        for (Product prod : lp) {
            suma += prod.getPrice();
        }
        return suma;
    }
}
