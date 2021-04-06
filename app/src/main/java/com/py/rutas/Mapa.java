package com.py.rutas;

import androidx.fragment.app.FragmentActivity;

import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.os.Parcelable;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.lang.reflect.Array;
import java.util.Arrays;

public class Mapa extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        Bundle bundle = getIntent().getExtras();
        Parcelable[] datos = bundle.getParcelableArray("PARADAS");
        Location[] ruta = Arrays.copyOf(datos,datos.length,Location[].class);
        mMap.clear();

        LatLng posicion,anteriorPosicion;
        float matizcolor = BitmapDescriptorFactory.HUE_GREEN;

        posicion = new LatLng(ruta[0].getLatitude(),ruta[0].getLongitude());
        mMap.addMarker(new MarkerOptions().position(posicion).icon(BitmapDescriptorFactory.defaultMarker(matizcolor)));
        int color = Color.BLUE;

        for (int i = 1; i <ruta.length ; i++) {
            matizcolor = BitmapDescriptorFactory.HUE_RED;
            anteriorPosicion = posicion;
            posicion = new LatLng(ruta[i].getLatitude(),ruta[i].getLongitude());

            if (i == ruta.length-1) matizcolor = BitmapDescriptorFactory.HUE_GREEN;

            mMap.addPolyline(new PolylineOptions().add(anteriorPosicion,posicion).width(7).color(color).geodesic(true));
            mMap.addMarker(new MarkerOptions().position(posicion).icon(BitmapDescriptorFactory.defaultMarker(matizcolor)));
        }

        // Add a marker in Sydney and move the camera
      /*  LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));

        LatLng punta = new LatLng(-12.073249184413754, -77.16528310749901);
        mMap.addMarker(new MarkerOptions().position(punta).title("La Punta"));

        mMap.moveCamera(CameraUpdateFactory.newLatLng(punta));*/
    }
}