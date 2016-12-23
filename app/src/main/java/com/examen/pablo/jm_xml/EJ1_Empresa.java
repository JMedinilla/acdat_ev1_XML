package com.examen.pablo.jm_xml;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;

public class EJ1_Empresa extends AppCompatActivity {

    TextView txtEmpleados;
    TextView txtMaximo;
    TextView txtMinimo;
    TextView txtEdad;

    ArrayList<String> datos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ej1__empresa);

        try {
            datos = Analisis.analizarEjercicio_Uno(getApplicationContext());
        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
        }

        txtEmpleados = (TextView)findViewById(R.id.txtEj1_Empleados);
        txtEmpleados.setText(datos.get(0));
        txtMaximo = (TextView)findViewById(R.id.txtEj1_Maximo);
        txtMaximo.setText(datos.get(1));
        txtMinimo = (TextView)findViewById(R.id.txtEj1_Minimo);
        txtMinimo.setText(datos.get(2));
        txtEdad = (TextView)findViewById(R.id.txtEj1_Edad);
        txtEdad.setText(datos.get(3));
    }
}
