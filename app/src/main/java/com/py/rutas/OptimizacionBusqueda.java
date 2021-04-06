package com.py.rutas;

import android.location.Location;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class OptimizacionBusqueda {

    public static Location busca(String direccion){
        Location centroCiudad = new Location(""); //Centro de la ciudad donde se trabajja
        centroCiudad.setLatitude(40.4381311);
        centroCiudad.setLongitude(-3.8196205);

        direccion = direccion + " , Madrid"; // " , poner la ciudad que se trabaja
        Location location;

        try {
            location = consultaLocalizacion("calle " + direccion,centroCiudad); // " " restringido a calles

            return  location;
        }catch (Exception e){
            return null;
        }
    }

    private static Location consultaLocalizacion(String direccion, Location centroCiudad) throws  Exception{
        Location location;
        InputStream inData;
        URL url = new URL("http://maps.google.com/maps/api/geocode/json?address=" + URLEncoder.encode(direccion,"UTF-8"));
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.connect();
        try {
           inData = new BufferedInputStream(urlConnection.getInputStream());
            readStream(inData);
        }finally {
            urlConnection.disconnect();
        }

        StringBuilder cadena = new StringBuilder();
        int caracter;
        //convertir la informacion en objeto json
        while ((caracter = inData.read())!=-1){
            cadena.append((char)caracter);
        }

        //comprobar status
        JSONObject object = new JSONObject(cadena.toString());
        if (!(object.getString("status").equals("OK"))) return null;
        //comprobar results
        JSONArray dire = object.getJSONArray("results");
        if (dire==null || dire.length()==0) return null;

        location = getLocalizacion(dire.getJSONObject(0));

        return location;
    }

    private static String readStream(InputStream in){
        return null;
    }

    private  static Location getLocalizacion(JSONObject direccion) throws Exception{
        String dire = direccion.getString("formatted_address"); // formato de direccion
        dire = new String(dire.getBytes("ISO-8859-1"),"UTF-8");// convertir la direccion

        Location loca = new Location(dire);
        double lalitud = direccion.getJSONObject("geometry").getJSONObject("location").getDouble("lat");
        double longitud = direccion.getJSONObject("geometry").getJSONObject("location").getDouble("lng");

        loca.setLatitude(lalitud);
        loca.setLongitude(longitud);

        return loca;
    }
}
