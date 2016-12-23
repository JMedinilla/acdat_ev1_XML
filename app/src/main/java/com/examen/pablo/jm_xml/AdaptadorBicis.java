package com.examen.pablo.jm_xml;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class AdaptadorBicis extends ArrayAdapter<Estacion> {

    LayoutInflater inf;
    Controles cnt;

    public AdaptadorBicis(Context context, int resource, List<Estacion> objects, LayoutInflater inflater) {
        super(context, resource, objects);

        this.inf = inflater;
        this.cnt = new Controles();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Estacion estacionTmp = getItem(position);

        if (convertView == null) {
            View layout = inf.inflate(R.layout.activity_adaptador_bicis, null);

            cnt.titulo = (TextView)layout.findViewById(R.id.txtEj3_Nombre);
            cnt.estado = (TextView)layout.findViewById(R.id.txtEj3_Estado);
            cnt.bicis = (TextView)layout.findViewById(R.id.txtEj3_Bicis);

            cnt.titulo.setText(estacionTmp.titulo);
            cnt.estado.setText(estacionTmp.estado);
            cnt.bicis.setText(estacionTmp.bicisD);

            convertView = layout;
        }
        return convertView;
    }

    public class Controles {
        TextView titulo;
        TextView estado;
        TextView bicis;
    }
}
