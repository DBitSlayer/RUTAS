package com.py.rutas;

import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;

public class Lineas implements Parcelable {

    public String nombre;
    public Location[] estaciones;
    public int origenRuta;
    public int finalRuta;
    public double datosParadaOrigen;
    public double datosParadaDestino;

    public void distancia(Location origen, Location destino){
        datosParadaOrigen = origen.distanceTo(estaciones[0]);
        datosParadaDestino = destino.distanceTo(estaciones[0]);

        for (int i=1;i<estaciones.length;i++){
            if (origen.distanceTo(estaciones[i])<datosParadaOrigen){
                origenRuta=i;
                datosParadaOrigen = origen.distanceTo(estaciones[i]);
            }
            if (destino.distanceTo(estaciones[i])<datosParadaDestino){
                finalRuta=i;
                datosParadaDestino = destino.distanceTo(estaciones[i]);
            }
        }
    }

    public double sumaDistMetros(){
        return datosParadaOrigen + datosParadaDestino;
    }

    public Lineas() {
    }

    protected Lineas(Parcel in) {
        nombre = in.readString();
        estaciones = in.createTypedArray(Location.CREATOR);
        origenRuta = in.readInt();
        finalRuta = in.readInt();
        datosParadaOrigen = in.readDouble();
        datosParadaDestino = in.readDouble();
    }

    public static final Creator<Lineas> CREATOR = new Creator<Lineas>() {
        @Override
        public Lineas createFromParcel(Parcel in) {
            return new Lineas(in);
        }

        @Override
        public Lineas[] newArray(int size) {
            return new Lineas[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nombre);
        dest.writeTypedArray(estaciones, 0);
        dest.writeInt(origenRuta);
        dest.writeInt(finalRuta);
        dest.writeDouble(datosParadaOrigen);
        dest.writeDouble(datosParadaDestino);
    }
}
