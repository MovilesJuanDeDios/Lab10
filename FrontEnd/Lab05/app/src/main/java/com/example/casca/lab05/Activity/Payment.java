package com.example.casca.lab05.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageButton;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.casca.lab05.ConnectionHelper.JsonConnection;
import com.example.casca.lab05.Model.Product;
import com.example.casca.lab05.Model.Usuario;
import com.example.casca.lab05.R;
import com.example.casca.lab05.Utils.Data;

import java.util.ArrayList;
import java.util.List;

public class Payment extends AppCompatActivity {

    private Button button;
    String username;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        RadioButton paypal = findViewById(R.id.radioButton6);
        RadioButton cards = findViewById(R.id.rdbOne);

        paypal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText name = findViewById(R.id.editText);
                EditText card = findViewById(R.id.editText3);
                Spinner mes = findViewById(R.id.mes);
                Spinner ano = findViewById(R.id.anio);
                EditText security = findViewById(R.id.security);

                name.setEnabled(false);
                card.setEnabled(false);
                mes.setEnabled(false);
                ano.setEnabled(false);
                security.setEnabled(false);
            }
        });

        cards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText name = findViewById(R.id.editText);
                EditText card = findViewById(R.id.editText3);
                Spinner mes = findViewById(R.id.mes);
                Spinner ano = findViewById(R.id.anio);
                EditText security = findViewById(R.id.security);

                name.setEnabled(true);
                card.setEnabled(true);
                mes.setEnabled(true);
                ano.setEnabled(true);
                security.setEnabled(true);
            }
        });

        button = (Button) findViewById(R.id.finalizar_compra);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                disminuyeInventario();
                finalizarCompra();
            }
        });


        username = Data.usuario.getUsername();

        TextView monto = findViewById(R.id.textViewMontoP);
        monto.setText(Integer.toString(calculaTotal()));
    }

    public void finalizarCompra() {
        getUsuario(username).getListaProductos().clear();

        for(Product p : Data.listaProductos) {
            JsonConnection conexion= new JsonConnection();
            String url;
            url=Data.url+"setProducto&id="+p.getId()+"&nombre="+p.getTitle()+"&shortdesc="+p.getShortdesc()+"&cant="+p.getCantidad()+"&precio="+p.getPrice();
            conexion.execute(new String[]{url,"POST"});
        }

        Intent intentf = new Intent(Payment.this,Navigation.class);
        startActivity(intentf);
        finish();
    }

    public Usuario getUsuario(String username) {
        Usuario aux = new Usuario();
        for (Usuario usr : Data.listaUsuarios) {
            if (usr.getUsername().equals(username))
                aux = usr;
        }
        return Data.usuario;
    }

    public void disminuyeInventario() {
        List<Product> lp =  getUsuario(username).getListaProductos();
        for (Product p : lp) {
            for(Product pr : Data.listaProductos) {
                if (p.getId() == pr.getId()) {
                    int cant = pr.getCantidad();
                    cant--;
                    pr.setCantidad(cant);
                }
            }
        }
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
