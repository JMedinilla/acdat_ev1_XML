package com.examen.pablo.jm_xml;

import android.content.Context;
import android.content.res.XmlResourceParser;
import android.util.Xml;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Analisis {

    public static ArrayList<String> analizarEjercicio_Uno(Context context) throws XmlPullParserException, IOException {

        ArrayList<String> datos = new ArrayList<>();

        double contadorEmpleados = 0;
        double sueldoMinimo = 999999999;
        double sueldoMaximo = 0;
        double edadTotal = 0;
        double edadMedia;

        boolean dentroEmpleado = false;

        XmlResourceParser xrp = context.getResources().getXml(R.xml.empleados);
        int eventType = xrp.getEventType();

        while (eventType != XmlPullParser.END_DOCUMENT ) {

            switch (eventType) {
                case XmlPullParser.START_TAG:
                    if(xrp.getName().equalsIgnoreCase("empleado")) {
                        dentroEmpleado = true;
                        contadorEmpleados += 1;
                    }
                    if (dentroEmpleado && xrp.getName().equalsIgnoreCase("edad")) {
                        double edadTmp = Double.parseDouble(xrp.nextText());
                        edadTotal += edadTmp;
                    }
                    if (dentroEmpleado && xrp.getName().equalsIgnoreCase("sueldo")) {
                        double sueldoTmp = Double.parseDouble(xrp.nextText());
                        if (sueldoTmp < sueldoMinimo) {
                            sueldoMinimo = sueldoTmp;
                        }
                        if (sueldoTmp > sueldoMaximo) {
                            sueldoMaximo = sueldoTmp;
                        }
                    }
                    break;

                case XmlPullParser.END_TAG:
                    if(xrp.getName().equalsIgnoreCase("empleado")) {
                        dentroEmpleado = false;
                    }
                    break;
            }

            eventType = xrp.next();
        }

        if (contadorEmpleados != 0) {
            edadMedia = (edadTotal / contadorEmpleados);

            datos.add(String.valueOf(contadorEmpleados));
            datos.add(String.valueOf(sueldoMaximo));
            datos.add(String.valueOf(sueldoMinimo));
            datos.add(String.valueOf(edadMedia));
        }
        else {
            datos.add("0"); datos.add("0"); datos.add("0"); datos.add("0");
        }

        return datos;
    }

    public static ArrayList<Noticia> analizarEjercicio_Cuatro(File file) throws XmlPullParserException, IOException {
        int eventType;
        ArrayList<Noticia> noticias = null;
        Noticia actual = null;
        XmlPullParser xpp = Xml.newPullParser();
        xpp.setInput(new FileReader(file));
        eventType=xpp.getEventType();

        boolean dentroItem = false;

        while (eventType!=XmlPullParser. END_DOCUMENT ){

            switch (eventType) {
                case XmlPullParser. START_DOCUMENT :
                    noticias = new ArrayList<>();
                    break;
                case XmlPullParser. START_TAG :
                    if (xpp.getName().equalsIgnoreCase("item")) {
                        dentroItem = true;
                        actual = new Noticia();
                    }
                    if (dentroItem && xpp.getName().equalsIgnoreCase("title")) {
                        actual.setTitle(xpp.nextText());
                    }
                    if (dentroItem && xpp.getName().equalsIgnoreCase("link")) {
                        actual.setLink(xpp.nextText());
                    }
                    if (dentroItem && xpp.getName().equalsIgnoreCase("description")) {
                        actual.setDescription(xpp.nextText());
                    }
                    if (dentroItem && xpp.getName().equalsIgnoreCase("pubDate")) {
                        actual.setPubDate(xpp.nextText());
                    }
                    break;
                case XmlPullParser. END_TAG :
                    if (xpp.getName().equalsIgnoreCase("item")) {
                        dentroItem = false;
                        try {
                            noticias.add(actual);
                        }
                        catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                    break;
            }
            eventType = xpp.next();
        }

        return noticias;
    }

    public static ArrayList<Estacion> analizarEjercicio_Tres(File file) throws IOException, XmlPullParserException {
        int eventType;
        ArrayList<Estacion> estaciones = null;
        Estacion actual = null;
        XmlPullParser xpp = Xml.newPullParser();
        xpp.setInput(new FileReader(file));
        eventType = xpp.getEventType();

        boolean dentroEstacion = false;

        while (eventType != XmlPullParser.END_DOCUMENT) {
            switch (eventType) {
                case XmlPullParser. START_DOCUMENT :
                    estaciones = new ArrayList<>();
                    break;
                case XmlPullParser. START_TAG :
                    if (xpp.getName().equalsIgnoreCase("estacion")) {
                        dentroEstacion = true;
                        actual = new Estacion();
                    }
                    if (dentroEstacion && xpp.getName().equalsIgnoreCase("uri")) {
                        actual.url = xpp.nextText();
                    }
                    if (dentroEstacion && xpp.getName().equalsIgnoreCase("title")) {
                        actual.titulo = xpp.nextText();
                    }
                    if (dentroEstacion && xpp.getName().equalsIgnoreCase("estado")) {
                        actual.estado = xpp.nextText();
                    }
                    if (dentroEstacion && xpp.getName().equalsIgnoreCase("bicisDisponibles")) {
                        actual.bicisD = xpp.nextText();
                    }
                    break;
                case XmlPullParser. END_TAG :
                    if (xpp.getName().equalsIgnoreCase("estacion")) {
                        dentroEstacion = false;
                        try {
                            estaciones.add(actual);
                        }
                        catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                    break;
            }
            eventType = xpp.next();
        }

        return estaciones;
    }

    public static ArrayList<String> analizarEjercicio_Dos(File file) throws IOException, XmlPullParserException {

        ArrayList<String> datos = null;

        String minimaHoy = "";
        String maximaHoy = "";
        String minimaManana = "";
        String maximaManana = "";
        String img_Hoy_1 = "";
        String img_Hoy_2 = "";
        String img_Hoy_3 = "";
        String img_Hoy_4 = "";
        String img_Manana_1 = "";
        String img_Manana_2 = "";
        String img_Manana_3 = "";
        String img_Manana_4 = "";

        boolean dentroTemperatura = false;

        int eventType;
        XmlPullParser xrp = Xml.newPullParser();
        xrp.setInput(new FileReader(file));
        eventType = xrp.getEventType();

        int contadorDias = 0;
        while (eventType != XmlPullParser.END_DOCUMENT  && contadorDias < 2) {

            switch (eventType) {
                case XmlPullParser. START_DOCUMENT :
                    datos = new ArrayList<>();
                    break;
                case XmlPullParser.START_TAG:
                    if(xrp.getName().equalsIgnoreCase("estado_cielo")) {
                        String periodo = xrp.getAttributeValue(0);
                        if (periodo.equals("00-06") && contadorDias == 0) {
                            img_Hoy_1 = xrp.nextText();
                        }
                        if (periodo.equals("06-12") && contadorDias == 0) {
                            img_Hoy_2 = xrp.nextText();
                        }
                        if (periodo.equals("12-18") && contadorDias == 0) {
                            img_Hoy_3 = xrp.nextText();
                        }
                        if (periodo.equals("18-24") && contadorDias == 0) {
                            img_Hoy_4 = xrp.nextText();
                        }
                        if (periodo.equals("00-06") && contadorDias == 1) {
                            img_Manana_1 = xrp.nextText();
                        }
                        if (periodo.equals("06-12") && contadorDias == 1) {
                            img_Manana_2 = xrp.nextText();
                        }
                        if (periodo.equals("12-18") && contadorDias == 1) {
                            img_Manana_3 = xrp.nextText();
                        }
                        if (periodo.equals("18-24") && contadorDias == 1) {
                            img_Manana_4 = xrp.nextText();
                        }
                    }
                    if(xrp.getName().equalsIgnoreCase("temperatura")) {
                        dentroTemperatura = true;
                    }
                    if(xrp.getName().equalsIgnoreCase("maxima") && dentroTemperatura) {
                        if (contadorDias == 0) {
                            maximaHoy = xrp.nextText();
                        }
                        else if (contadorDias == 1) {
                            maximaManana = xrp.nextText();
                        }
                    }
                    if(xrp.getName().equalsIgnoreCase("minima") && dentroTemperatura) {
                        if (contadorDias == 0) {
                            minimaHoy = xrp.nextText();
                        }
                        else if (contadorDias == 1) {
                            minimaManana = xrp.nextText();
                        }
                    }
                    break;
                case XmlPullParser.END_TAG:
                    if(xrp.getName().equalsIgnoreCase("dia")) {
                        contadorDias++;
                    }
                    if(xrp.getName().equalsIgnoreCase("temperatura")) {
                        dentroTemperatura = false;
                    }
                    break;
            }

            eventType = xrp.next();
        }

        //Rellenar datos
        datos.add(minimaHoy);
        datos.add(maximaHoy);
        datos.add(minimaManana);
        datos.add(maximaManana);
        datos.add(img_Hoy_1);
        datos.add(img_Hoy_2);
        datos.add(img_Hoy_3);
        datos.add(img_Hoy_4);
        datos.add(img_Manana_1);
        datos.add(img_Manana_2);
        datos.add(img_Manana_3);
        datos.add(img_Manana_4);

        return datos;
    }
}
