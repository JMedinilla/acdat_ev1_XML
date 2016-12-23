package com.examen.pablo.jm_xml;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.FileAsyncHttpResponseHandler;
import com.loopj.android.http.RequestHandle;
import org.xmlpull.v1.XmlPullParserException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class Ej3_Bicicletas extends ListActivity implements AdapterView.OnItemClickListener {

    public static final String CANAL = "http://www.zaragoza.es/api/recurso/urbanismo-infraestructuras/estacion-bicicleta.xml";
    public static final String TEMPORAL = "estaciones.xml";

    ArrayList<Estacion> listaEstaciones;
    ListView lstEst;
    AdaptadorBicis nAdapter;
    AsyncHttpClient cliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ej3__bicicletas);

        lstEst = getListView();
        lstEst.setOnItemClickListener(this);
        cliente = new AsyncHttpClient();

        descarga(CANAL);
    }

    private void mostrar() {
        if (listaEstaciones != null) {
            nAdapter = new AdaptadorBicis(this, R.layout.activity_adaptador_bicis, listaEstaciones, getLayoutInflater());
            lstEst.setAdapter(nAdapter);
        } else
            Toast.makeText(getApplicationContext(), "No se ha podido crear la lista", Toast.LENGTH_SHORT).show();
    }

    private void descarga(String url) {
        final ProgressDialog progreso = new ProgressDialog(this);
        File miFichero = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), TEMPORAL);
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
                    listaEstaciones = Analisis.analizarEjercicio_Tres(file);
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Uri uri = Uri.parse(listaEstaciones.get(position).url);
        Intent in = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(in);
    }
}
