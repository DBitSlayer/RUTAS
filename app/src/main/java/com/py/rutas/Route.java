package com.py.rutas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Arrays;

public class Route extends AppCompatActivity {

    public  String linea;
    public Location[] paradas;

    private TextView tv_Lineas;
    private  LinearLayout rutaContenedor;
    private LayoutInflater inflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);

        tv_Lineas = findViewById(R.id.txv_Linea);
        rutaContenedor = findViewById(R.id.pantalla);
        inflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);

        Intent intent = this.getIntent();
        String linea = intent.getStringExtra("LINEAS");
        tv_Lineas.setText(linea);
        Bundle bundle = getIntent().getExtras();
        Parcelable[] datos = bundle.getParcelableArray("PARADAS");
        Location[] ruta = Arrays.copyOf(datos,datos.length,Location[].class);

        String texto = getString(R.string.a_pie)+ " "+ (int)ruta[0].distanceTo(ruta[1]);
        texto+= " " + getString(R.string.metros);

        pintaEtapa(inflater,R.drawable.esfera_inicio_fin,ruta[0].getProvider(),rutaContenedor);
        pintaEtapa(inflater,R.drawable.caminando,texto,rutaContenedor);

        for (int i = 1; i < ruta.length-1 ; i++) {
            pintaEtapa(inflater,R.drawable.esfera_etapa,ruta[i].getProvider(),rutaContenedor);
        }

        if (ruta.length>2){
            texto = getString(R.string.a_pie)+ " " + (int)ruta[ruta.length-2].distanceTo(ruta[ruta.length-1]);
            texto+= " " + getString(R.string.metros);
            pintaEtapa(inflater,R.drawable.caminando,texto,rutaContenedor);
        }

        pintaEtapa(inflater,R.drawable.esfera_inicio_fin,ruta[ruta.length-1].getProvider(),rutaContenedor);
    }

    private void pintaEtapa(LayoutInflater inflater, int imagen,String text, LinearLayout contenedor){
        LinearLayout distanciaEstaciones = (LinearLayout) inflater.inflate(R.layout.distancia_estaciones,null);
        ((ImageView)distanciaEstaciones.findViewById(R.id.icono)).setImageResource(imagen);
        ((TextView)distanciaEstaciones.findViewById(R.id.txvDeta)).setText(text);

        contenedor.addView(distanciaEstaciones);
    }

    public void mapa(View v){
        Bundle b = getIntent().getExtras();
        Intent intent = new Intent(this,Mapa.class);
        intent.putExtras(b);
        startActivity(intent);
    }

    public void mejorRuta(Location origen, Location destino, Lineas[] lineas){
        Lineas mejorLinea = null;

        for (Lineas line: lineas ) {
            line.distancia(origen,destino);
            if (mejorLinea == null || line.sumaDistMetros()<mejorLinea.sumaDistMetros()) {
                mejorLinea = line;
            }
        }

        //a pie
        if (mejorLinea==null || origen.distanceTo(destino)<mejorLinea.sumaDistMetros()){
            linea = null;
            paradas = new Location[2];
            paradas[0] = origen;
            paradas[1] = destino;
            return;
        }

        paradas = new Location[mejorLinea.finalRuta - mejorLinea.origenRuta + 2];
        paradas[0] = origen;
        paradas[paradas.length-1] = destino;

        for (int i = 1; i < (paradas.length - 2); i++) {
            paradas[i] = mejorLinea.estaciones[i + mejorLinea.origenRuta-1];
        }

    }

}