package com.example.casca.lab05.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.casca.lab05.ConnectionHelper.JsonConnection;
import com.example.casca.lab05.Model.Product;
import com.example.casca.lab05.Model.Usuario;
import com.example.casca.lab05.R;
import com.example.casca.lab05.Utils.Data;

public class AgregarProducto extends AppCompatActivity {

    private EditText id;
    private EditText nombre;
    private EditText descripcion;
    private EditText cantidad;
    private EditText precio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_producto);

        id = (EditText) findViewById(R.id.register_id);
        nombre = (EditText) findViewById(R.id.register_title);
        descripcion =(EditText) findViewById(R.id.register_descripcion);
        cantidad = (EditText) findViewById(R.id.register_cantidad);
        precio = (EditText) findViewById(R.id.register_precio);

        Button registerBtn = (Button) findViewById(R.id.register_button_producto);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptRegister();
            }
        });

        Button mRegisterButton = (Button) findViewById(R.id.cancel_register_producto);
        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelar();
            }
        });
    }

    private void attemptRegister() {

        // Reset errors.
        id.setError(null);
        nombre.setError(null);
        descripcion.setError(null);
        cantidad.setError(null);
        precio.setError(null);

        // Store values at the time of the login attempt.
        String iden = id.getText().toString();
        String nom = nombre.getText().toString();
        String desc = descripcion.getText().toString();
        String cant = cantidad.getText().toString();
        String prec = precio.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // si el id esta vacio
        if (TextUtils.isEmpty(iden)) {
            id.setError(getString(R.string.error_field_required));
            focusView = id;
            cancel = true;
        }

        // si el nombre esta vacio
        if (TextUtils.isEmpty(nom)) {
            nombre.setError(getString(R.string.error_field_required));
            focusView = nombre;
            cancel = true;
        }

        if (TextUtils.isEmpty(desc)) {
            descripcion.setError(getString(R.string.error_field_required));
            focusView = descripcion;
            cancel = true;
        }

        if (TextUtils.isEmpty(cant)) {
            cantidad.setError(getString(R.string.error_field_required));
            focusView = cantidad;
            cancel = true;
        }

        if (TextUtils.isEmpty(prec)) {
            precio.setError(getString(R.string.error_field_required));
            focusView = precio;
            cancel = true;
        }


        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            Product pro = new Product(Integer.parseInt(iden),nom,desc,Integer.parseInt(cant),Integer.parseInt(prec),0);

            JsonConnection conexion=new JsonConnection();
            String url=Data.url+"agregarProducto&id="+iden+"&nombre="+nom+"&shortdesc="+desc+"&cant="+cant+"&precio="+prec;
            conexion.execute(new String[]{url,"POST"});

            Toast.makeText(getApplicationContext(), "Registro exitoso", Toast.LENGTH_SHORT).show();
            Data.listaProductos.add(pro);
            Intent intentf = new Intent(this,Navigation.class);
            startActivity(intentf);
            finish();
        }
    }

    private void cancelar() {
        Intent intentf = new Intent(this,Navigation.class);
        startActivity(intentf);
        finish();
    }
}
