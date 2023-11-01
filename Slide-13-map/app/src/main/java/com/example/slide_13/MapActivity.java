package com.example.slide_13;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        map = googleMap;

        map.setMapType(GoogleMap.MAP_TYPE_TERRAIN);

        map.setBuildingsEnabled(true);

        map.getUiSettings().setZoomControlsEnabled(true);
        map.getUiSettings().setCompassEnabled(true);

        if (getIntent().hasExtra("latitude") && getIntent().hasExtra("longitude")) {
            LatLng currentLocation = new LatLng(getIntent().getDoubleExtra("latitude", 0), getIntent().getDoubleExtra("longitude", 0));

            map.addMarker(new MarkerOptions()
                    .position(currentLocation)
                    .title("Vị trí hiện tại"));
            map.moveCamera(CameraUpdateFactory.newLatLng(currentLocation));
        }
        else {
            // If no latitude and longitude data is provided, default to 0,0
            LatLng currentLocation = new LatLng(0, 0);

            map.addMarker(new MarkerOptions()
                    .position(currentLocation)
                    .title("Default Location"));
            map.moveCamera(CameraUpdateFactory.newLatLng(currentLocation));
        }
    }
}
