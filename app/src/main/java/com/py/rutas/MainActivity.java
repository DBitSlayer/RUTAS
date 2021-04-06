package com.py.rutas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {

    private Button btnSitios;
    private Button btnTipos;
    private Button btnUbicacion;
    private ProgressBar pbar_Menu;

    public final String [] LINEAS = {"Linea2","Linea3","Linea6"};
    Lineas[] lines;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSitios = findViewById(R.id.btn_sitios);
        btnTipos = findViewById(R.id.btn_Tipos);
        btnUbicacion = findViewById(R.id.btn_Ubicacion);
        pbar_Menu = findViewById(R.id.pbar_Menu);

        btnSitios.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(),Mapa.class);
            startActivity(intent);
        });

        btnTipos.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(),Buscador.class);
            startActivity(intent);
        });

        pbar_Menu.setVisibility(View.VISIBLE);
        Sincroniza sincroniza = new Sincroniza();
        sincroniza.execute();
    }

   private class Sincroniza extends AsyncTask<String,Integer,String>{

        protected  String doInBackground(String... strings){
            ManejoBBDD bbdd = new ManejoBBDD(getApplicationContext());

            try {
                bbdd.OpenDatabase(getApplicationContext());
                lines = bbdd.dameInfoLineas(LINEAS);
                bbdd.close();
            }catch (Exception e){
                finish();
            }

            return null;
        }

        protected void onProgressUpdate(Integer... valores){
            pbar_Menu.setProgress(valores[0],true);
        }

        protected  void onPostExecute(String resultado){
            comenzar();
        }
   }

   public void comenzar(){
        Bundle bundle = new Bundle();
        bundle.putParcelableArray("LINEAS", lines);

        Intent intent = new Intent(this,Buscador.class);

   }


}
