package com.example.slide_13;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class LocationActivity extends AppCompatActivity implements LocationListener {
    private FusedLocationProviderClient cilent_location;
    private LocationManager manager_location;
    private String provider_location;
    private Button button_location;
    private Button update_button_location;
    private TextView details_location;
    private final static int REQUEST_CODE = 100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        details_location = findViewById(R.id.location_details);
        button_location = findViewById(R.id.button_get_location);
        update_button_location = findViewById(R.id.button_update_location);

        cilent_location = LocationServices.getFusedLocationProviderClient(this);
        provider_location = LocationManager.GPS_PROVIDER;
        manager_location = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        button_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(LocationActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(LocationActivity.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(LocationActivity.this, new String[] {android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_CODE);
                }
                cilent_location.getLastLocation().addOnSuccessListener(LocationActivity.this, new OnSuccessListener<android.location.Location>() {
                    @Override
                    public void onSuccess(android.location.Location location) {
                        if (location != null) {
                            Geocoder geocoder = new Geocoder(LocationActivity.this, Locale.getDefault());
                            try {
                                List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);

                                details_location.setText("Vĩ độ: " + location.getLatitude() + "\n" + "Kinh độ: " + location.getLongitude() + "\n" + addresses.get(0).getAddressLine(0));

                                Intent intent = new Intent(LocationActivity.this, MapActivity.class);
                                intent.putExtra("latitude", location.getLatitude());
                                intent.putExtra("longitude", location.getLongitude());
                                intent.putExtra("address", addresses.get(0).getAddressLine(0));
                                startActivity(intent);

                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
                });
            }
        });

        update_button_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(LocationActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(LocationActivity.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(LocationActivity.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_CODE);
                }
                manager_location.requestLocationUpdates(provider_location, 2000L, 1F, LocationActivity.this);
            }
        });
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        Geocoder geocoder = new Geocoder(LocationActivity.this, Locale.getDefault());
        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            details_location.setText("Vĩ độ: " + location.getLatitude() + "\n" + "Kinh độ: " + location.getLongitude() + "\n" + addresses.get(0).getAddressLine(0));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}