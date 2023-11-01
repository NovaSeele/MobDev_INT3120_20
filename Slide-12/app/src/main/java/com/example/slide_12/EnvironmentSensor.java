package com.example.slide_12;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class EnvironmentSensor {

    public interface EnvironmentListener {
        void onTemperature(float temperature);
        void onHumidity(float humidity);
        void onPressure(float pressure);

        void onLight(float light);
    }

    private EnvironmentListener environmentListener;

    public void setEnvironmentListener(EnvironmentListener environmentListener) {
        this.environmentListener = environmentListener;
    }

    private SensorManager sensorManager;
    private SensorEventListener sensorEventListener;
    private Sensor temperatureSensor;
    private Sensor humiditySensor;
    private Sensor pressureSensor;

    private Sensor lightSensor;

    EnvironmentSensor(Context context) {
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        temperatureSensor = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        humiditySensor = sensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY);
        pressureSensor = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        sensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                if (environmentListener != null) {
                    if (sensorEvent.sensor.getType() == Sensor.TYPE_AMBIENT_TEMPERATURE) {
                        environmentListener.onTemperature(sensorEvent.values[0]);
                    } else if (sensorEvent.sensor.getType() == Sensor.TYPE_RELATIVE_HUMIDITY) {
                        environmentListener.onHumidity(sensorEvent.values[0]);
                    } else if (sensorEvent.sensor.getType() == Sensor.TYPE_PRESSURE) {
                        environmentListener.onPressure(sensorEvent.values[0]);
                    } else if (sensorEvent.sensor.getType() == Sensor.TYPE_LIGHT) {
                        environmentListener.onLight(sensorEvent.values[0]);
                    }
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {
                // Extra function for sensor
            }
        };
    }

    public void registerSensor() {
        sensorManager.registerListener(sensorEventListener, temperatureSensor, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(sensorEventListener, humiditySensor, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(sensorEventListener, pressureSensor, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(sensorEventListener, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void unregisterSensor() {
        sensorManager.unregisterListener(sensorEventListener);
    }
}
