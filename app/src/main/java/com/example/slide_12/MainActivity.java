package com.example.slide_12;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private MotionSensor motionSensor;
    private GyroscopeSensor gyroscopeSensor;
    private EnvironmentSensor environmentSensor;
    private TextView temperatureTextView;
    private TextView humidityTextView;
    private TextView pressureTextView;
    private TextView lightTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //declare the textview
        temperatureTextView = findViewById(R.id.temperature);
        humidityTextView = findViewById(R.id.humidity);
        pressureTextView = findViewById(R.id.pressure);
        lightTextView = findViewById(R.id.light);

        //declare the sensor
        gyroscopeSensor = new GyroscopeSensor(this);
        motionSensor = new MotionSensor(this);
        environmentSensor = new EnvironmentSensor(this);

        //set the gyroscope sensor
        gyroscopeSensor.setMotionListener(new GyroscopeSensor.MotionListener() {
            @Override
            public void onMotion(float tx, float ty, float tz) {
                // Do something with the motion data
                if (tx > 0.5f) {
                    getWindow().getDecorView().setBackgroundColor(Color.RED);
                } else if (tx < -0.5f) {
                    getWindow().getDecorView().setBackgroundColor(Color.BLUE);
                } else if (ty > 0.5f) {

                } else if (ty < -0.5f) {
                    // Do something
                } else if (tz > 0.5f) {
                    // Do something
                } else if (tz < -0.5f) {
                    // Do something
                }
            }
        });


        //set the motion sensor
        motionSensor.setMotionListener(new MotionSensor.MotionListener() {
            @Override
            public void onMotion(float rx, float ry, float rz) {
                // Do something with the motion data
                if (rx > 0.5f) {
                    getWindow().getDecorView().setBackgroundColor(Color.GREEN);
                } else if (rx < -0.5f) {
                    getWindow().getDecorView().setBackgroundColor(Color.YELLOW);
                } else if (ry > 0.5f) {
                    // Do something
                } else if (ry < -0.5f) {
                    // Do something
                } else if (rz > 0.5f) {
                    // Do something
                } else if (rz < -0.5f) {
                    // Do something
                }
            }
        });


        //set the environment sensor
        environmentSensor.setEnvironmentListener(new EnvironmentSensor.EnvironmentListener() {
            @Override
            public void onTemperature(float temperature) {
                if (temperature < -273.2) {
                    temperatureTextView.setText(String.format("Temperature hasn't detected", temperature));
                } else {
                    temperatureTextView.setText(String.format("Temperature: %.2f *C", temperature));
                }
            }

            @Override
            public void onHumidity(float humidity) {
                humidityTextView.setText(String.format("Humid: %.2f Percent", humidity));
            }

            @Override
            public void onPressure(float pressure) {
                pressureTextView.setText(String.format("Pressure: %.2f hPA", pressure));
            }

            @Override
            public void onLight(float light) {
                lightTextView.setText(String.format("Light: %.2f lx", light));
            }
        });
    }



    //register the sensor when the app is running
    @Override
    protected void onResume() {
        super.onResume();
        gyroscopeSensor.registerSensor();
        motionSensor.registerSensor();
        environmentSensor.registerSensor();


    }

    //unregister the sensor when the app is paused
    @Override
    protected void onPause() {
        super.onPause();
        gyroscopeSensor.unregisterSensor();
        motionSensor.unregisterSensor();
        environmentSensor.unregisterSensor();
    }
}