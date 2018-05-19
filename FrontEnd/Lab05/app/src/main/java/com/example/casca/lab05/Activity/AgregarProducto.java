package com.example.casca.lab05.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.example.casca.lab05.ConnectionHelper.JsonConnection;
import com.example.casca.lab05.Model.Product;
import com.example.casca.lab05.Model.Usuario;
import com.example.casca.lab05.R;
import com.example.casca.lab05.Utils.Data;

import java.util.ArrayList;
import java.util.List;

public class AgregarProducto extends AppCompatActivity {

    private EditText id;
    private EditText nombre;
    private EditText descripcion;
    private EditText cantidad;
    private EditText precio;
    ArrayAdapter<String> adapterTipo;
    Spinner spinner;
    List<String> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_producto);

        id = (EditText) findViewById(R.id.register_id);
        nombre = (EditText) findViewById(R.id.register_title);
        descripcion =(EditText) findViewById(R.id.register_descripcion);
        cantidad = (EditText) findViewById(R.id.register_cantidad);
        precio = (EditText) findViewById(R.id.register_precio);
        spinner = (Spinner) findViewById(R.id.imagen);

        list.add("Laptop Dell");
        list.add("Iphone");
        list.add("LCD-TV LG");
        list.add("Laptop MacBook");
        list.add("Consola de Video Juegos");
        list.add("Torre Sonido SONY");
        list.add("Celular Samsung");
        list.add("Torre Sonido SONY");
        list.add("Tablet Surface");
        list.add("Pantalla LED TCL");

        adapterTipo = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, list);
        adapterTipo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapterTipo);

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

        if(getIntent().getBooleanExtra("edit",false)==true)
            editData();
    }

    private void editData() {
        int pos=getIntent().getIntExtra("position",0);
        ((EditText) findViewById(R.id.register_id)).setText(Integer.toString(Data.listaProductos.get(pos).getId()));
        (findViewById(R.id.register_id)).setEnabled(false);
        ((EditText) findViewById(R.id.register_title)).setText(Data.listaProductos.get(pos).getTitle());
        ((EditText) findViewById(R.id.register_descripcion)).setText(Data.listaProductos.get(pos).getShortdesc());
        ((EditText) findViewById(R.id.register_cantidad)).setText(Integer.toString(Data.listaProductos.get(pos).getCantidad()));
        ((EditText) findViewById(R.id.register_precio)).setText(Integer.toString(Data.listaProductos.get(pos).getPrice()));
        ((Spinner)findViewById(R.id.imagen)).setEnabled(false);
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
        int image = imageChosen();

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
            Product pro = new Product(Integer.parseInt(iden),nom,desc,Integer.parseInt(cant),Integer.parseInt(prec),image);
            String url;
            JsonConnection conexion=new JsonConnection();

            if(getIntent().getBooleanExtra("edit",false)==true){
                int pos=getIntent().getIntExtra("position",0);
                Data.listaProductos.get(pos).setId(Integer.parseInt(iden));
                Data.listaProductos.get(pos).setTitle(nom);
                Data.listaProductos.get(pos).setShortdesc(desc);
                Data.listaProductos.get(pos).setCantidad(Integer.parseInt(cant));
                Data.listaProductos.get(pos).setPrice(Integer.parseInt(prec));
                //Data.listaProductos.get(pos).setImage(image);
                url=Data.url+"setProducto&id="+iden+"&nombre="+nom+"&shortdesc="+desc+"&cant="+cant+"&precio="+prec;
            }
            else {
                Data.listaProductos.add(pro);
                url = Data.url + "agregarProducto&id=" + iden + "&nombre=" + nom + "&shortdesc=" + desc + "&cant=" + cant + "&precio=" + prec + "&imagen=" + image;
            }

            conexion.execute(new String[]{url,"POST"});

            Toast.makeText(getApplicationContext(), "Registro exitoso", Toast.LENGTH_SHORT).show();

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

    private int imageChosen(){
        int image=0;
        String imageChosen;
        imageChosen = spinner.getSelectedItem().toString();
        switch(imageChosen){
            case "Laptop Dell":
                image=R.drawable.dellinspiron;
                break;
            case "Iphone":
                image=R.drawable.iphone;
                break;
            case "LCD-TV LG":
                image=R.drawable.lg;
                break;
            case "Laptop MacBook":
                image=R.drawable.macbook;
                break;
            case "Consola de Video Juegos":
                image=R.drawable.ps4;
                break;
            case "Celular Samsung":
                image=R.drawable.samsung;
                break;
            case "Torre Sonido SONY":
                image=R.drawable.sony;
                break;
            case "Tablet Surface":
                image=R.drawable.surface;
                break;
            case "Pantalla LED TCL":
                image=R.drawable.tcl;
                break;
        }
        return image;
    }
}
