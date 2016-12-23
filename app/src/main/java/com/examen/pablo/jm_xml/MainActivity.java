package com.examen.pablo.jm_xml;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnEj_Uno;
    Button btnEj_Dos;
    Button btnEj_Tres;
    Button btnEj_Cuatro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnEj_Uno = (Button)findViewById(R.id.btnEjercicio_1);
        btnEj_Uno.setOnClickListener(this);
        btnEj_Dos = (Button)findViewById(R.id.btnEjercicio_2);
        btnEj_Dos.setOnClickListener(this);
        btnEj_Tres = (Button)findViewById(R.id.btnEjercicio_3);
        btnEj_Tres.setOnClickListener(this);
        btnEj_Cuatro = (Button)findViewById(R.id.btnEjercicio_4);
        btnEj_Cuatro.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent in = null;
        if (v == btnEj_Uno) {
            in = new Intent(MainActivity.this, EJ1_Empresa.class);
        }
        if (v == btnEj_Dos) {
            in = new Intent(MainActivity.this, Ej2_Tiempo.class);
        }
        if (v == btnEj_Tres) {
            in = new Intent(MainActivity.this, Ej3_Bicicletas.class);
        }
        if (v == btnEj_Cuatro) {
            in = new Intent(MainActivity.this, Ej4_Noticias.class);
        }
        startActivity(in);
    }
}
