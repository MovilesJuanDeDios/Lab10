package com.example.casca.lab05.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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
import android.widget.TextView;
import android.widget.Toast;

import com.example.casca.lab05.Adapters.ProductAdapter;
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

    SharedPreferences sharedPref;
    String username;


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
        sharedPref = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        username = sharedPref.getString(getString(R.string.user_pref), getString(R.string.default_user));

        if (!added) {
            addUsusarios();
            addProductos();
            added = true;
        }

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new ProductAdapter(this, Data.listaProductos);
        recyclerView.setAdapter(adapter);

    }

    private void addUsusarios(){
        /* ************************ ROL 1 ES ROL DE USUARIO,
         * ES EN CASO QUE DESPUES SE PIDAN OTROS ROLES
         * COMO ADMINISTRADOR ETC
         * ***************************************************/
        Usuario user = new Usuario("andres","ancas@algo.com","andres","12345",1);
        Usuario user2 = new Usuario("jose","joseslon@gmail.com","jose","12345",1);
        Usuario user3 = new Usuario("giancarlo","juank@hotmail.com","giancarlo","12345",1);
        Data.listaUsuarios.add(user);
        Data.listaUsuarios.add(user2);
        Data.listaUsuarios.add(user3);
    }

    private void addProductos() {
        Data.listaProductos.add(
                new Product(
                        1,
                        "Apple MacBook Air Core i5 5th Gen - (8 GB/128 GB SSD/Mac OS Sierra)",
                        "13.3 inch, Silver, 1.35 kg",
                        24,
                        525330,
                        R.drawable.macbook));

        Data.listaProductos.add(
                new Product(
                        2,
                        "Dell Inspiron 7000 Core i5 7th Gen - (8 GB/1 TB HDD/Windows 10 Home)",
                        "14 inch, Gray, 1.659 kg",
                        13,
                        381330,
                        R.drawable.dellinspiron));

        Data.listaProductos.add(
                new Product(
                        3,
                        "Microsoft Surface Pro 4 Core m3 6th Gen - (4 GB/128 GB SSD/Windows 10)",
                        "13.3 inch, Silver, 1.35 kg",
                        10,
                        342000,
                        R.drawable.surface));
        Data.listaProductos.add(
                new Product(
                        4,
                        "Apple iPhone X",
                        "Fully Unlocked 5.8\", 64GB - Silver",
                        27,
                        525000,
                        R.drawable.iphone));
        Data.listaProductos.add(
                new Product(
                        5,
                        "Samsung Galaxy S9",
                        "Unlocked Midnight Black 64GB",
                        72,
                        409000,
                        R.drawable.samsung));
        Data.listaProductos.add(
                new Product(
                        6,
                        "Play Station 4",
                        "Slim 500GB Console",
                        37,
                        153000,
                        R.drawable.ps4));
        Data.listaProductos.add(
                new Product(
                        7,
                        "TCL 55S405",
                        "55-Inch 4K Ultra HD Roku Smart LED TV (2017 Model)",
                        18,
                        228000,
                        R.drawable.tcl));
        Data.listaProductos.add(
                new Product(
                        8,
                        "Sony MHC-V90W",
                        "High Power One Box Party Music System with Built-In Wi-Fi (2017 model)",
                        30,
                        740500,
                        R.drawable.sony));
        Data.listaProductos.add(
                new Product(
                        9,
                        "Stacy Talented Superhero Ninja Name Pride Funny Gift",
                        "Phone Case Fits Iphone 6, 6s, 7, 8",
                        69,
                        7500,
                        R.drawable.stacy));
        Data.listaProductos.add(
                new Product(
                        10,
                        "LG Electronics OLED65C8PUA",
                        "65-Inch 4K Ultra HD Smart OLED TV (2018 Model)",
                        100,
                        2000000,
                        R.drawable.lg));
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
            Intent intentf = new Intent(Navigation.this, Login.class);
            startActivity(intentf);
            finish();
            return true;
        } else {
            if (id == R.id.action_logout) {
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.clear();
                editor.commit();
                finish();
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
            Intent intentf = new Intent(Navigation.this, Carrito.class);
            startActivity(intentf);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
