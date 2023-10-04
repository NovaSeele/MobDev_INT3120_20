package com.example.slide_11;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class BackGroundService extends Service {

    static final String TAG = "BackGroundService";
    public int counter = 0;

    private boolean isRunning  = false;

    @Override
    public void onCreate() {
        super.onCreate();
        isRunning = true;
        Log.i(TAG, "Start BackGroundService");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isRunning = false;
        Log.i(TAG, "Stop BackGroundService");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(1000);
                        if (isRunning)
                            counter++;
                        else
                            break;
                        Log.i(TAG, "Service has run for " + counter + " seconds.");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        };

        Thread thread = new Thread(runnable);
        thread.start();

        return super.onStartCommand(intent, flags, startId);
    }

    public BackGroundService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}