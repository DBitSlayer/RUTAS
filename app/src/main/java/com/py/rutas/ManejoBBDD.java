package com.py.rutas;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.location.Location;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Objects;

import androidx.annotation.Nullable;

public  class ManejoBBDD extends SQLiteOpenHelper {

    String rutaAlmacenamiento;
    SQLiteDatabase database;
    private Context mContext;

    public ManejoBBDD(Context context) {

        super(context, "paradasMetro.db",null,3);
        mContext = context;
        rutaAlmacenamiento = mContext.getFilesDir().getParentFile().getPath() + "/databases/paradasMetro.db";
    }

    public void OpenDatabase(Context context){
        try {
            //no es la primera vez que se accede a la BD
            this.getReadableDatabase();
            database = SQLiteDatabase.openDatabase(rutaAlmacenamiento,null,SQLiteDatabase.OPEN_READONLY);
        }catch (Exception e){
            copiarBD(context);
            database = SQLiteDatabase.openDatabase(rutaAlmacenamiento,null,SQLiteDatabase.OPEN_READONLY);
        }
    }

    private void copiarBD(Context context){
        try {
            InputStream datosEntrada = context.getAssets().open("paradasMetro.db");
            OutputStream datosSalidad = new FileOutputStream(rutaAlmacenamiento);
            byte[] bufferDB = new byte[1024];

            int longitud;
            while ((longitud = datosEntrada.read(bufferDB))>0){
                datosSalidad.flush();
                datosSalidad.close();
                datosEntrada.close();
            }
        }catch (Exception e){

        }
    }

    public Location datosEstacion(int id){
        Location estacion;
        Cursor cursor;
        cursor = database.rawQuery("SELECT * FROM paradas WHERE id ="+id,null);
        cursor.moveToFirst();

        estacion = new Location(cursor.getString(1));
        estacion.setLatitude(Double.parseDouble(cursor.getString(2)));
        estacion.setLongitude(Double.parseDouble(cursor.getString(3)));

        cursor.close();

        return estacion;
    }

    public Lineas[] dameInfoLineas(String[] nombresDeLineas){ // recibe array con las lineas de Metro
        Lineas[] lineas = new Lineas[nombresDeLineas.length]; //crea un arraay ipo Lineas con la longitud del parametro
        Cursor cursor = null;

        for (int i = 0; i<nombresDeLineas.length;i++){
            lineas[i] = new Lineas();
            lineas[i].nombre = nombresDeLineas[i]; // alamcenando el nombre de cada linea

            cursor = database.rawQuery("SELECT id FROM "+ nombresDeLineas[i],null); // consulta a la tabla para obtener los id de las estaciones
            lineas[i].estaciones = new Location[cursor.getCount()];

            int contador = 0;
            cursor.moveToFirst();

            while (!cursor.isAfterLast()){
                int estacion = Integer.parseInt(cursor.getString(0)); //almacen del id de cada estacion
                lineas[i].estaciones[contador] = datosEstacion(estacion);
                contador++;

                cursor.moveToNext();
            }

        }

        if (cursor!=null && !cursor.isClosed()){
            cursor.close();
        }

        return lineas;
    }

    public void cerrarBD(){
        database.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


}
