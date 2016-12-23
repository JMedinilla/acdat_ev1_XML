package com.examen.pablo.jm_xml;

import android.app.ProgressDialog;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.FileAsyncHttpResponseHandler;
import com.loopj.android.http.RequestHandle;
import com.squareup.picasso.Picasso;

import org.xmlpull.v1.XmlPullParserException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import cz.msebera.android.httpclient.Header;

public class Ej2_Tiempo extends AppCompatActivity {

    public static final String CANAL = "http://www.aemet.es/xml/municipios/localidad_29067.xml";
    public static final String TEMPORAL = "tiempo.xml";

    ArrayList<String> datos;
    AsyncHttpClient cliente;

    TextView fechaHoy;
    TextView fechaManana;
    ImageView cielo_hoy_1;
    ImageView cielo_hoy_2;
    ImageView cielo_hoy_3;
    ImageView cielo_hoy_4;
    ImageView cielo_manana_1;
    ImageView cielo_manana_2;
    ImageView cielo_manana_3;
    ImageView cielo_manana_4;
    TextView tempHoyMin;
    TextView tempHoyMax;
    TextView tempMananaMin;
    TextView tempMananaMax;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ej2__tiempo);

        fechaHoy = (TextView)findViewById(R.id.txtEj2_FechaHoy);
        fechaManana = (TextView)findViewById(R.id.txtEj2_FechaManana);
        cielo_hoy_1 = (ImageView)findViewById(R.id.img_Ej2_Hoy_1);
        cielo_hoy_2 = (ImageView)findViewById(R.id.img_Ej2_Hoy_2);
        cielo_hoy_3 = (ImageView)findViewById(R.id.img_Ej2_Hoy_3);
        cielo_hoy_4 = (ImageView)findViewById(R.id.img_Ej2_Hoy_4);
        cielo_manana_1 = (ImageView)findViewById(R.id.img_Ej2_Manana_1);
        cielo_manana_2 = (ImageView)findViewById(R.id.img_Ej2_Manana_2);
        cielo_manana_3 = (ImageView)findViewById(R.id.img_Ej2_Manana_3);
        cielo_manana_4 = (ImageView)findViewById(R.id.img_Ej2_Manana_4);
        tempHoyMin = (TextView)findViewById(R.id.txtEJ2_Temperatura_Hoy_Minima);
        tempHoyMax = (TextView)findViewById(R.id.txtEJ2_Temperatura_Hoy_Maxima);
        tempMananaMin = (TextView)findViewById(R.id.txtEJ2_Temperatura_Manana_Minima);
        tempMananaMax = (TextView)findViewById(R.id.txtEJ2_Temperatura_Manana_Maxima);

        Calendar fecha = new GregorianCalendar();
        fechaHoy.setText(fecha.get(Calendar.YEAR) + "-" + (fecha.get(Calendar.MONTH)+1) + "-" + fecha.get(Calendar.DATE));
        fechaManana.setText(fecha.get(Calendar.YEAR) + "-" + (fecha.get(Calendar.MONTH)+1) + "-" + (fecha.get(Calendar.DATE)+1));

        cliente = new AsyncHttpClient();

        descarga(CANAL);
    }

    private void mostrar() {
        String dirImgPartida_1 = "http://www.aemet.es/imagenes/gif/estado_cielo/";
        String dirImgPartida_2 = ".gif";

        tempHoyMin.setText(datos.get(0));
        tempHoyMax.setText(datos.get(1));
        tempMananaMin.setText(datos.get(2));
        tempMananaMax.setText(datos.get(3));
        Picasso.with(this).load(dirImgPartida_1 + datos.get(4) + dirImgPartida_2).error(R.drawable.error).into(cielo_hoy_1);
        Picasso.with(this).load(dirImgPartida_1 + datos.get(5) + dirImgPartida_2).error(R.drawable.error).into(cielo_hoy_2);
        Picasso.with(this).load(dirImgPartida_1 + datos.get(6) + dirImgPartida_2).error(R.drawable.error).into(cielo_hoy_3);
        Picasso.with(this).load(dirImgPartida_1 + datos.get(7) + dirImgPartida_2).error(R.drawable.error).into(cielo_hoy_4);
        Picasso.with(this).load(dirImgPartida_1 + datos.get(8) + dirImgPartida_2).error(R.drawable.error).into(cielo_manana_1);
        Picasso.with(this).load(dirImgPartida_1 + datos.get(9) + dirImgPartida_2).error(R.drawable.error).into(cielo_manana_2);
        Picasso.with(this).load(dirImgPartida_1 + datos.get(10) + dirImgPartida_2).error(R.drawable.error).into(cielo_manana_3);
        Picasso.with(this).load(dirImgPartida_1 + datos.get(11) + dirImgPartida_2).error(R.drawable.error).into(cielo_manana_4);
    }

    private void descarga(String url) {
        final ProgressDialog progreso = new ProgressDialog(this);
        final File miFichero = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), TEMPORAL);
        RequestHandle requestHandle = cliente.get(url, new FileAsyncHttpResponseHandler(miFichero) {

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, File file) {
                progreso.dismiss();
                Toast.makeText(getApplicationContext(), "Fallo: " + throwable.getMessage(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, File file) {
                progreso.dismiss();
                Toast.makeText(getApplicationContext(), "Descarga realizada con éxito: " + file.getPath(), Toast.LENGTH_LONG).show();
                try {
                    datos = Analisis.analizarEjercicio_Dos(file);
                    mostrar();
                } catch (XmlPullParserException | IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onStart() {
                super.onStart();
                progreso.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progreso.setMessage("Conectando . . .");
                progreso.setCancelable(false);
                progreso.show();
            }
        });
    }
}
