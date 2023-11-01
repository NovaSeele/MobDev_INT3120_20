package com.example.slide_11;

import android.app.Service;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.IBinder;

import java.text.SimpleDateFormat;

public class BoundService extends Service {

    public BinderService binder = new BinderService();
    public BoundService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    public String getSystime() {
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss, dd/mm/yyyy", java.util.Locale.getDefault());
        return sdf.format(new java.util.Date());
    }

    public class BinderService extends Binder {
        BoundService getService() {
            return BoundService.this;
        }
    }
}