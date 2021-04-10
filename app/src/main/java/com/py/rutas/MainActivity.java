package com.py.rutas;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

//import java.util.concurrent.ExecutorService;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    private Button btnSitios;
    private Button btnTipos;
    private Button btnUbicacion;
    private ProgressBar pbar_Menu;

    public final String [] LINEA = {"Linea2","Linea3","Linea6"};
    Lineas[] lineas;

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
           /* Intent intent = new Intent(getApplicationContext(),Buscador.class);
            startActivity(intent);*/
            Toast.makeText(this, "LINEAS" + lineas[1].estaciones, Toast.LENGTH_SHORT).show();
        });


        pbar_Menu.setVisibility(View.VISIBLE);
       comenzar();

    }


  /* private class Sincroniza extends AsyncTask<String,Integer,String>{

        protected  String doInBackground(String... strings){
            ManejoBBDD bbdd = new ManejoBBDD(getApplicationContext());

            try {
                bbdd.OpenDatabase(getApplicationContext());
                bbdd.getReadableDatabase();
                lines = bbdd.dameInfoLineas(LINEA);
                bbdd.cerrarBD();
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

   */

   public void comenzar(){
      /*  Intent intent = new Intent(MainActivity.this,Buscador.class);
        Bundle bundle = new Bundle();
        bundle.putParcelableArray("LINEAS", lines);
        intent.putExtras(bundle);
        startActivity(intent);
       */
       ManejoBBDD bbdd = new ManejoBBDD(getApplicationContext());
           bbdd.OpenDatabase(getApplicationContext());
           bbdd.getReadableDatabase();
           lineas = bbdd.dameInfoLineas(LINEA);
           bbdd.cerrarBD();


       Toast.makeText(this, "LINEAS" + lineas[0], Toast.LENGTH_LONG).show();
       Intent intent = new Intent(MainActivity.this,Buscador.class);
       Bundle bundle = new Bundle();
       bundle.putParcelableArray("LINEAS", lineas);
       intent.putExtras(bundle);
       startActivity(intent);

      /* if (!lineas.equals(null)) {
           Toast.makeText(this, "LINEAS" + lineas[0], Toast.LENGTH_SHORT).show();
       }

       */
   }
}
