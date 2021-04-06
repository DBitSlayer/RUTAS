package com.py.rutas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import java.lang.reflect.Array;
import java.util.Arrays;

public class Buscador extends AppCompatActivity {

    Lineas[] lineas;
    Route ruta;
    private ProgressBar pbar_Busqueda;
    EditText origen,destino;
    Button btn_Enviar;
    String direOrigen,direDestino;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscador);

        origen = findViewById(R.id.edt_Inicio);
        destino = findViewById(R.id.edt_Destino);
        btn_Enviar = findViewById(R.id.btn_BRuta);

    }

    public void onResume(){
        super.onResume();

        origen.setText("");
        destino.setText("");
        btn_Enviar.setAlpha(1);
        btn_Enviar.setEnabled(true);

        if (lineas==null){
            Bundle bundle = getIntent().getExtras();
            Parcelable[] datos = bundle.getParcelableArray("LINEAS");
            lineas = Arrays.copyOf(datos,datos.length,Lineas[].class);
        }

        ruta = new Route();
    }

    public void leeDirecciones(View view){
        btn_Enviar.setAlpha(0);
        btn_Enviar.setEnabled(false);

        direOrigen = origen.getText().toString();
        direDestino = destino.getText().toString();

        InputMethodManager introduce = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        introduce.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);

        pbar_Busqueda = new ProgressBar(this);
        EjecutaSegundoPlano tarea = new EjecutaSegundoPlano();
        tarea.execute();
    }

    private class EjecutaSegundoPlano extends AsyncTask<String,String,String>{

        @Override
        protected String doInBackground(String... strings) {
            Location ptoOrigen, ptoDestino;
            Context context = getApplicationContext();

            ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo estadoRed = manager.getActiveNetworkInfo();
            if (estadoRed==null || !estadoRed.isConnected() || !estadoRed.isAvailable()){
                return getString(R.string.error_iconexion);
            }

            try {
                ptoOrigen = OptimizacionBusqueda.busca(direOrigen);
                if (ptoOrigen== null)  return getString(R.string.error_origen);

                ptoDestino = OptimizacionBusqueda.busca(direDestino);
                if (ptoDestino== null)  return getString(R.string.error_destino);
            }catch (Exception e){
                return  getString(R.string.error_red);
            }
            ruta.mejorRuta(ptoOrigen,ptoDestino,lineas);
            return null;
        }

        protected void onPostExecute(String resultado){
            pbar_Busqueda = null;
            btn_Enviar.setEnabled(true);
            btn_Enviar.setAlpha(1);
            muestraRuta();
        }
    }

    public void muestraRuta(){
    }

    public void onBackPressed(){
        if (pbar_Busqueda!=null) pbar_Busqueda= null;
        moveTaskToBack(true);
    }
}