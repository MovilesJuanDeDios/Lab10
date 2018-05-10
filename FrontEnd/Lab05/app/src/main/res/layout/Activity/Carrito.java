package com.example.casca.widgets.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.casca.widgets.R;

public class Carrito extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrito);

        final Button button = findViewById(R.id.comprar);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intentf = new Intent(com.example.casca.widgets.Activity.Carrito.this, Payment.class);
                startActivity(intentf);
            }
        });
    }
}
