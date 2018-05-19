package com.example.casca.lab05.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.casca.lab05.Adapters.ProductAdapter;
import com.example.casca.lab05.ConnectionHelper.JsonConnection;
import com.example.casca.lab05.Model.Product;
import com.example.casca.lab05.Model.Usuario;
import com.example.casca.lab05.R;
import com.example.casca.lab05.Utils.Data;

import java.util.ArrayList;
import java.util.List;

public class Navigation extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    RecyclerView recyclerView;
    ProductAdapter adapter;

    private static boolean added = false;

    TextView userTextView;

    String username;
    int rol;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        userTextView = (TextView) findViewById(R.id.user_logged);
        username = Data.usuario.getUsername();


        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new ProductAdapter(this, Data.listaProductos);
        recyclerView.setAdapter(adapter);

        FloatingActionButton btnAgregar = (FloatingActionButton) findViewById(R.id.button_Agregar);

        if(Data.usuario.getRol() == 1)
            btnAgregar.setVisibility(View.VISIBLE);

        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Navigation.this, AgregarProducto.class);
                startActivity(intent);
            }
        });

    }

    private void addUsuarios(){
        Data.listaUsuarios.clear();
        JsonConnection conexion=new JsonConnection();
        String url=Data.url+"consultarUsuario";
        conexion.execute(new String[]{url,"USER"});
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation, menu);

        invalidateOptionsMenu();
        onPrepareOptionsMenu(menu);

        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        if (!username.equals("")) {
            TextView textView = (TextView) findViewById(R.id.user_logged);
            textView.setText(username);

            menu.findItem(R.id.action_login).setVisible(false);
            menu.findItem(R.id.action_logout).setVisible(true);
        }

        return super.onPrepareOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_login) {
            addUsuarios();
            Intent intentf = new Intent(Navigation.this, Login.class);
            startActivity(intentf);
            finish();
            return true;
        } else {
            if (id == R.id.action_logout) {
                Data.usuario = new Usuario("","","","",0);
                startActivity(getIntent());
                finish();
            }
        }

        return super.onOptionsItemSelected(item);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.carrito) {
            if(Data.usuario.getRol()==0){
                Toast.makeText(this, "Inicie Sesion", Toast.LENGTH_SHORT).show();
            }
            if(Data.usuario.getRol()==1){
                Toast.makeText(this, "Inicie Sesion Como Cliente", Toast.LENGTH_SHORT).show();
            }

            if(Data.usuario.getRol()==2){
                Intent intentf = new Intent(Navigation.this, Carrito.class);
                startActivity(intentf);
            }
        }
        if (id == R.id.perfil) {
            if(Data.usuario.getRol()==0){
                Toast.makeText(this, "Inicie Sesion", Toast.LENGTH_SHORT).show();
            }
            else{
                Intent intentf = new Intent(Navigation.this, Registro.class);
                intentf.putExtra("edit", true);
                startActivity(intentf);
            }
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
