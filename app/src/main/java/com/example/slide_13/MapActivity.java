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
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        // Get a reference to the SupportMapFragment defined in the XML layout
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        // Check if the mapFragment is not null and set an OnMapReadyCallback
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        // Set the map type to terrain
        mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);

        // Enable 3D building models on the map
        mMap.setBuildingsEnabled(true);

        // Enable zoom controls and compass on the user interface
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);

        // Check if latitude and longitude data was passed as extras from the previous activity
        if (getIntent().hasExtra("latitude") && getIntent().hasExtra("longitude")) {
            LatLng currentLocation = new LatLng(getIntent().getDoubleExtra("latitude", 0), getIntent().getDoubleExtra("longitude", 0));

            // Add a marker at the specified location and move the camera to that location
            mMap.addMarker(new MarkerOptions()
                    .position(currentLocation)
                    .title("Current Location"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(currentLocation));
        }
        else {
            // If no latitude and longitude data is provided, default to Vancouver's coordinates
            LatLng vancouver = new LatLng(49.246292, -123.116226);

            // Add a marker at the default location and move the camera to Vancouver
            mMap.addMarker(new MarkerOptions()
                    .position(vancouver)
                    .title("Marker in Vancouver"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(vancouver));
        }
    }
}
