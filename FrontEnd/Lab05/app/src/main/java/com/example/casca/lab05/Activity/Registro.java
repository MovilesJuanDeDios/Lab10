package com.example.casca.lab05.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.casca.lab05.ConnectionHelper.JsonConnection;
import com.example.casca.lab05.Model.Usuario;
import com.example.casca.lab05.R;
import com.example.casca.lab05.Utils.Data;

public class Registro extends AppCompatActivity {

    private EditText nombre;
    private EditText email;
    private EditText usuario;
    private EditText pass;
    private EditText passConf;

    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private Login.UserLoginTask mAuthTask = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        usuario =(EditText) findViewById(R.id.register_username);
        nombre = (EditText) findViewById(R.id.register_nombre);
        email = (EditText) findViewById(R.id.register_email);
        pass = (EditText) findViewById(R.id.register_password);
        passConf = (EditText) findViewById(R.id.register_password_confirm);

        Button registerBtn = (Button) findViewById(R.id.register_button);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptRegister();
            }
        });

        Button mRegisterButton = (Button) findViewById(R.id.cancel_register);
        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelar();
            }
        });

        if(getIntent().getBooleanExtra("edit",false)==true)
            editData();

    }

    private void attemptRegister() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        usuario.setError(null);
        nombre.setError(null);
        email.setError(null);
        pass.setError(null);
        passConf.setError(null);

        // Store values at the time of the login attempt.
        String usu = usuario.getText().toString();
        String nom = nombre.getText().toString();
        String mail = email.getText().toString();
        String passw = pass.getText().toString();
        String passwConf = passConf.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // si el nombre esta vacio
        if (TextUtils.isEmpty(nom)) {
            nombre.setError(getString(R.string.error_field_required));
            focusView = nombre;
            cancel = true;
        }

        // si el correo esta vacio
        if (TextUtils.isEmpty(mail)) {
            email.setError(getString(R.string.error_field_required));
            focusView = email;
            cancel = true;
        }

        if (!isUsernameValid(usu)) {
            usuario.setError(getString(R.string.error_invalid_username));
            focusView = usuario;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            Usuario us = new Usuario(usu,nom,mail,passw,1);
            String url;
            JsonConnection conexion = new JsonConnection();

            if(getIntent().getBooleanExtra("edit",false)==true){
                Data.usuario.setUsername(usu);
                Data.usuario.setNombre(nom);
                Data.usuario.setEmail(mail);
                Data.usuario.setClave(passw);
                url=Data.url+"setUsuario&username="+usu+"&nombre="+nom+"&email="+mail+"&password="+passw;
            }
            else{
                Data.listaUsuarios.add(us);
                url=Data.url+"agregarUsuario&username="+usu+"&nombre="+nom+"&email="+mail+"&password="+passw+"&rol="+2;
            }

            conexion.execute(new String[]{url,"POST"});

            Toast.makeText(getApplicationContext(), "Registro exitoso", Toast.LENGTH_SHORT).show();

            Intent intentf = new Intent(Registro.this,Navigation.class);
            startActivity(intentf);
            finish();
        }
    }

    // revisa que el email no este repetido
    private boolean isEmailValid(String email) {
        for (Usuario element : Data.listaUsuarios) {
            if (element.getEmail().equals(email))
                return false;
        }
        return true;
    }

    // revisa que el usuario no este repetido
    private boolean isUsernameValid(String username) {
        for (Usuario element : Data.listaUsuarios) {
            if (element.getUsername().equals(username))
                return false;
        }
        return true;
    }


    private void cancelar() {
        Intent intentf = new Intent(Registro.this,Navigation.class);
        startActivity(intentf);
        finish();
    }

    public void editData() {

        ((EditText) findViewById(R.id.register_nombre)).setText(Data.usuario.getNombre());
        ((EditText) findViewById(R.id.register_email)).setText(Data.usuario.getEmail());
        ((EditText) findViewById(R.id.register_username)).setText(Data.usuario.getUsername());
        (findViewById(R.id.register_username)).setEnabled(false);
        ((EditText) findViewById(R.id.register_password)).setText(Data.usuario.getClave());
        ((EditText) findViewById(R.id.register_password_confirm)).setText(Data.usuario.getClave());

    }
}
