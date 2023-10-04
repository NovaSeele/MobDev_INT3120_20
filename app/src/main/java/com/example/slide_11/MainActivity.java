package com.example.slide_11;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import com.example.slide_11.BoundService.BinderService;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public static final String CHANNEL_ID = "exampleServiceChannel";
    private TextView background_status;
    public EditText editNotiInput;
    public EditText editMessInput;
    public EditText editTimeInput;
    private NotificationChannel channel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        background_status = findViewById(R.id.backgroundStatus);

        editNotiInput = findViewById(R.id.edit_noti_input);
        editMessInput = findViewById(R.id.edit_mess_input);
        editTimeInput = findViewById(R.id.edit_time_input);
        channel = new NotificationChannel(
                CHANNEL_ID,
                "Example Service Channel",
                NotificationManager.IMPORTANCE_DEFAULT
        );

        askForNotifyPermission();
        createNotificationChannel();

        Intent intent = new Intent(this, BoundService.class);
        bindService(intent, connection, BIND_AUTO_CREATE);
    }

    public void onBackGroundServiceClick(View v) {
        Intent intent = new Intent(this, BackGroundService.class);
        startService(intent);

        background_status.setText("Started");
    }

    public void onBackGroundServiceStopClick(View v) {
        Intent intent = new Intent(this, BackGroundService.class);
        stopService(intent);

        background_status.setText("Stopped");
    }

    public void onForeGroundServiceStartClick(View v) {
        Intent serviceIntent = new Intent(this, ForeGroundService.class);
        serviceIntent.putExtra("notiExtra", editNotiInput.getText().toString());
        serviceIntent.putExtra("messExtra", editMessInput.getText().toString());
        serviceIntent.putExtra("timeExtra", editTimeInput.getText().toString());
        ContextCompat.startForegroundService(this, serviceIntent);
    }

    public void onForeGroundServiceStopClick(View v) {
        Intent serviceIntent = new Intent(this, ForeGroundService.class);
        stopService(serviceIntent);
    }

    private void createNotificationChannel() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "Example Service Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
        }
    }


    private void askForNotifyPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (channel.getImportance() == NotificationManager.IMPORTANCE_NONE) {
                Intent intent = new Intent(Settings.ACTION_CHANNEL_NOTIFICATION_SETTINGS);
                intent.putExtra(Settings.EXTRA_APP_PACKAGE, getPackageName());
                intent.putExtra(Settings.EXTRA_CHANNEL_ID,  CHANNEL_ID);
                startActivity(intent);
            }
        }
    }

    private boolean isBound = false;
    BoundService boundService = new BoundService();
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            BinderService binder = (BinderService) service;
            boundService = binder.getService();
            isBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isBound = false;
        }
    };

    public void onBoundServiceClick(View v) {

        TextView bound_service_status = findViewById(R.id.bound_service_status);
        bound_service_status.setText(boundService.getSystime());

        if (isBound) {
            bound_service_status.setText(boundService.getSystime());
        }
    }
}