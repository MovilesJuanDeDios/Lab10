package com.example.casca.lab05.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.casca.lab05.ConnectionHelper.JsonConnection;
import com.example.casca.lab05.R;
import com.example.casca.lab05.Utils.Data;

import java.util.Timer;
import java.util.TimerTask;

public class Load extends AppCompatActivity {
    private Timer timer;
    private ProgressBar progressBar;
    private int i=0;
    TextView textView;
    public static final String url="http://10.0.2.2:8080/Servlet/Servlet?accion=consultarProductos";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load);
        progressBar=(ProgressBar)findViewById(R.id.progressBar);
        progressBar.setProgress(0);
        progressBar.getProgressDrawable().setColorFilter(
                Color.RED, android.graphics.PorterDuff.Mode.SRC_IN);
        textView=(TextView)findViewById(R.id.textView);
        textView.setText("");

        Data.listaProductos.clear();

        JsonConnection jconexion = new JsonConnection();
        jconexion.execute(new String[]{url,"GET"});

        final long period = 50;
        timer=new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                //this repeats every 100 ms
                if (i<100){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                        }
                    });
                    progressBar.setProgress(i);
                    i++;
                }else{
                    //closing the timer
                    timer.cancel();
                    Intent intent =new Intent(Load.this,Navigation.class);
                    startActivity(intent);
                    // close this activity
                    finish();
                }
            }
        }, 0, period);
    }

}
