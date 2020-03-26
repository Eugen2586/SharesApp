package com.example.sharesapp.FunktionaleKlassen.Worker;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.work.WorkManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.sharesapp.R;

public class NotificationWorker extends Worker {

    private final String NOTIFICATION_CHANNEL_ID = "bbb_channel_id";

    /**
     * @param appContext   The application {@link Context}
     * @param workerParams Parameters to setup the internal state of this worker
     */
    public NotificationWorker(@NonNull Context appContext, @NonNull WorkerParameters workerParams) {
        super(appContext, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        try {
            showComeBackNotification(0);
            return Result.success();
        } catch (Exception e) {
            e.printStackTrace();
            WorkManager.getInstance(this.getApplicationContext()).cancelAllWorkByTag("Stock_Market");
            return Result.failure();
        }
    }

    private void showComeBackNotification(int messageIndex) {
        String message;
        switch (messageIndex) {
            case 0:
                message = "Komm zurück und werde reich!";
                break;
            case 1:
                message = "Wir vermissen dich!";
                break;
            case 2:
                message = "Willst du Geld verdienen?";
                break;
            case 3:
                message = "Geld! Geld! Geld!";
                break;
            default:
                message = "Geld verdient sich nicht von alleine!";
        }
        showNotificationWithMessage(message);
    }

    private void showNotificationWithMessage(String message) {
        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "Stock Market", NotificationManager.IMPORTANCE_HIGH);

            // Configure the notification channel.
            notificationChannel.setDescription("Channel description");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.GREEN);
            notificationChannel.setVibrationPattern(new long[]{0, 1000, 500, 1000});
            notificationChannel.enableVibration(true);
            notificationChannel.setSound(null, null);
            notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
            assert notificationManager != null;
            notificationManager.createNotificationChannel(notificationChannel);
        }


        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(getApplicationContext(), NOTIFICATION_CHANNEL_ID);

        notificationBuilder.setAutoCancel(false)
                .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE | Notification.DEFAULT_LIGHTS)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setPriority(Notification.PRIORITY_MAX)
                .setContentTitle("BBB")
                .setContentText(message);

        assert notificationManager != null;
        notificationManager.notify(1, notificationBuilder.build());
    }
}
