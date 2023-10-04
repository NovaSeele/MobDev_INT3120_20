package com.example.slide_11;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

public class ForeGroundService extends Service {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String input = intent.getStringExtra("inputExtra");

        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, notificationIntent, 0);

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(ForeGroundService.this, MainActivity.CHANNEL_ID);

        builder.setContentTitle(intent.getStringExtra("notiExtra"));
        builder.setContentText(intent.getStringExtra("messExtra"));
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        builder.setSmallIcon(R.drawable.ic_launcher_foreground);
        builder.setProgress(100, 0, false);

        notificationManager.notify(1, builder.build());

        startForeground(1, builder.build());

        // Create a Handler to update the progress
        Handler handler = new Handler();
        int totalProgress = 100;
        int totalTime = Integer.parseInt(intent.getStringExtra("timeExtra"));
        final int[] currentProgress = {0};
        int increment = totalProgress/totalTime; // Increase by 10% with each update

        // Runnable to update the progress
        Runnable updateProgressRunnable = new Runnable() {
            @Override
            public void run() {
                currentProgress[0] += increment;
                builder.setProgress(totalProgress, currentProgress[0], false);
                notificationManager.notify(1, builder.build());

                if (currentProgress[0] < totalProgress) {

                    handler.postDelayed(this, 1000);
                } else {
                    // Progress has reached 100%, remove the notification
                    notificationManager.cancel(1);
                    stopForeground(true);
                }
            }
        };
        // Start updating the progress
        handler.post(updateProgressRunnable);
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}