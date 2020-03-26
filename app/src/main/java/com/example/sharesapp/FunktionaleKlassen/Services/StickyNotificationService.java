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
import com.example.sharesapp.Model.FromServerClasses.Aktie;
import com.example.sharesapp.Model.Model;
import com.example.sharesapp.R;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class StickyNotificationService extends Service {
    private final LocalBinder mBinder = new LocalBinder();
    protected Handler handler;
    private Timer timer = new Timer();
    Model model = new Model();
    final String channelId = "bbb_channel_id";

    public class LocalBinder extends Binder {
        public StickyNotificationService getService() {
            return StickyNotificationService.this;
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
        timer.cancel();
        // remove Notification channel
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            String id = "my_channel_01";
            assert notificationManager != null;
            notificationManager.deleteNotificationChannel(channelId);
        }
        super.onDestroy();
    }

    @Override
    public int onStartCommand(final Intent intent, int flags, int startId) {
        handler = new Handler();
        handler.post(new TimerTask() {
            @Override
            public void run() {
                final int timeInterval = 1 * 10 * 1000; // TODO: set to 30 min
                timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        // model loading with persistence
//                        model.getPersistanceFBackground();
//                        ArrayList<Aktie> depotList = model.getData().getDepot().getAktienImDepot().getValue();
//                        if (depotList == null) {
//                            showComeBackNotification(0);
//                        } else if (depotList.size() == 0) {
//                            showComeBackNotification(1);
//                        } else {
//                            showComeBackNotification(2);
//                        }

////                        showComeBackNotification(new Random().nextInt() % 4);
//                        System.out.println("...............................................................................Request Sticky");
//                        // get all symbols for requests
//                        Set<String> symbolSet = ServiceRequestFunctionality.getSymbolSet();
//
//                        // serviceRequests for all stocks with symbols in symbolSet
//                        ServiceRequestFunctionality.asyncRequestsForStocks(symbolSet);

                    }
                }, 0, timeInterval);
            }
        });

        return Service.START_STICKY;
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence channel_name = "bbb_channel_name";
            int channel_importance = NotificationManager.IMPORTANCE_DEFAULT;
            String description = "channel for bbb";
            NotificationChannel channel = new NotificationChannel(channelId, channel_name, channel_importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            assert notificationManager != null;
            notificationManager.createNotificationChannel(channel);
        }
        return channelId;
    }
}
