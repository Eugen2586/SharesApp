package com.example.sharesapp.FunktionaleKlassen.Services;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.sharesapp.DrawerActivity;
import com.example.sharesapp.R;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class NotificationOnlyStickyService extends Service {
    private final LocalBinder mBinder = new LocalBinder();
    protected Handler handler;
    private Timer timer = new Timer();
    final String channelId = "bbb_channel_id";

    public class LocalBinder extends Binder {
        public NotificationOnlyStickyService getService() {
            return NotificationOnlyStickyService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        // cancel the timer
        timer.cancel();

        // remove Notification channel if possible
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            assert notificationManager != null;
            notificationManager.deleteNotificationChannel(channelId);
        }

        super.onDestroy();
    }

    @Override
    public int onStartCommand(final Intent intent, int flags, int startId) {
        // send Notification with random Message every 30 min
        handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                final int timeInterval = 30 * 60 * 1000;
                timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        showComeBackNotification(new Random().nextInt() % 4);
                    }
                }, timeInterval, timeInterval);
            }
        });

        return Service.START_STICKY;
    }

    private void showComeBackNotification(int messageIndex) {
        // defines random message from messageIndex
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
        // https://developer.android.com/training/notify-user/build-notification#kotlin
        // build notification channel
        String channel_id = buildNotificationChannel();

        // build pendingIntent for opening activity on click on notification
        Intent intent = new Intent(this, DrawerActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        // build notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channel_id)
                .setSmallIcon(R.drawable.logo)
                .setContentTitle("Broken Broke Broker")
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        // notify user
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(1, builder.build());
    }

    private String buildNotificationChannel() {
        // if version compatible -> build Notification Channel
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence channel_name = "bbb_channel_name";
            int channel_importance = NotificationManager.IMPORTANCE_DEFAULT;
            String description = "channel for bbb";
            NotificationChannel channel = new NotificationChannel(channelId, channel_name, channel_importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            assert notificationManager != null;
            notificationManager.createNotificationChannel(channel);
        }

        return channelId;
    }
}
