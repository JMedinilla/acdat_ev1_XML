package com.examen.pablo.jm_xml;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

public class Ej4_Noticias extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    public static final String CANAL_1 = "http://ep00.epimg.net/rss/tags/ultimas_noticias.xml";
    public static final String CANAL_2 = "http://estaticos.elmundo.es/elmundo/rss/espana.xml";
    public static final String CANAL_3 = "http://www.linux-magazine.com/rss/feed/lmi_news";
    public static final String CANAL_4 = "http://www.pcworld.com/index.rss";
    public static final String TEMPORAL = "noticias.xml";

    Button btnPais;
    Button btnMundo;
    Button btnLinux;
    Button btnPCW;
    ListView lstNoticias;

    ArrayList<Noticia> noticias;
    ArrayAdapter<Noticia> adapter;
    AsyncHttpClient cliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ej4__noticias);

        btnPais = (Button) findViewById(R.id.btnEJ4_ElPais);
        btnPais.setOnClickListener(this);
        btnMundo = (Button) findViewById(R.id.btnEJ4_ElMundo);
        btnMundo.setOnClickListener(this);
        btnLinux = (Button) findViewById(R.id.btnEJ4_LinuxMagazine);
        btnLinux.setOnClickListener(this);
        btnPCW = (Button) findViewById(R.id.btnEJ4_PCWorld);
        btnPCW.setOnClickListener(this);
        lstNoticias = (ListView)findViewById(R.id.listaNoticias);
        lstNoticias.setOnItemClickListener(this);

        cliente = new AsyncHttpClient();
        noticias = new ArrayList<>();
    }

    @Override
    public void onClick(View v) {
        if (v == btnPais) {
            descarga(CANAL_1);
        }
        if (v == btnMundo) {
            descarga(CANAL_2);
        }
        if (v == btnLinux) {
            descarga(CANAL_3);
        }
        if (v == btnPCW) {
            descarga(CANAL_4);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Uri uri = Uri.parse(noticias.get(position).getLink());
        Intent intent = new Intent(Intent. ACTION_VIEW , uri);
        startActivity(intent);
    }

    private void mostrar() {
        if (noticias != null) {
            adapter = new ArrayAdapter<>(this, android.R.layout. simple_list_item_1 , noticias);
            lstNoticias.setAdapter(adapter);
        } else
            Toast.makeText(getApplicationContext(), "Error al crear la lista", Toast.LENGTH_SHORT).show();
    }

    private void descarga(String url) {
        final ProgressDialog progreso = new ProgressDialog(this);
        final File miFichero=new File(Environment.getExternalStorageDirectory().getAbsolutePath(), TEMPORAL);
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
                    noticias = Analisis.analizarEjercicio_Cuatro(file);
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
