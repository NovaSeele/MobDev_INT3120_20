package com.example.slide_12;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class GyroscopeSensor {

    public interface MotionListener{
        void onMotion(float x, float y, float z);
    }

    private MotionListener motionListener;

    public void setMotionListener(MotionListener motionListener){
        this.motionListener = motionListener;
    }

    private SensorManager sensorManager;
    private SensorEventListener sensorEventListener;
    private Sensor sensor;

    GyroscopeSensor(Context context){
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        sensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                if(motionListener != null){
                    motionListener.onMotion(sensorEvent.values[0], sensorEvent.values[1], sensorEvent.values[2]);
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }
        };
    }

    public void registerSensor(){
        sensorManager.registerListener(sensorEventListener, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void unregisterSensor(){
        sensorManager.unregisterListener(sensorEventListener);
    }
}
