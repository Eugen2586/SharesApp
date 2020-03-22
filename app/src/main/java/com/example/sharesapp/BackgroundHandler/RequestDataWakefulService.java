package com.example.sharesapp.BackgroundHandler;

import android.app.IntentService;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.sharesapp.DrawerActivity;
import com.example.sharesapp.Model.Model;
import com.example.sharesapp.R;

import java.util.Timer;
import java.util.TimerTask;

public class RequestDataWakefulService extends IntentService {

    public RequestDataWakefulService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        // At this point SimpleWakefulReceiver is still holding a wake lock
        // for us.  We can do whatever we need to here and then tell it that
        // it can release the wakelock.  This sample just does some slow work,
        // but more complicated implementations could take their own wake
        // lock here before releasing the receiver's.
        //
        // Note that when using this approach you should be aware that if your
        // service gets killed and restarted while in the middle of such work
        // (so the Intent gets re-delivered to perform the work again), it will
        // at that point no longer be holding a wake lock since we are depending
        // on SimpleWakefulReceiver to that for us.  If this is a concern, you can
        // acquire a separate wake lock here.

        final Model model = new Model();
        Handler handler = new Handler();
        handler.post(new TimerTask() {
            @Override
            public void run() {
                Timer t = new Timer();
                int timeInterval = 1 * 10 * 1000; // 5 min
                t.schedule(new TimerTask(){
                    @Override
                    public void run() {
//                        if (model.getData().getDepot().getAktienImDepot().getValue() != null) {
//                            for (Aktie stock: model.getData().getDepot().getAktienImDepot().getValue()) {
//                                try {
//                                    requests.asyncRun(RequestsBuilder.getQuote(stock.getSymbol()));
//                                } catch (IOException e) {
//                                    e.printStackTrace();
//                                }
//                            }
//
//                            // TODO: Try to sell or buy stock
//                            // TODO: If all bought or sold, stopSelf();
//                        }
                        // TODO: enable Notification if something was bought or sold
                        showComeBackNotification();
//                        showOrderCompleteNotification();
                        // TODO: completeWakefulIntent after notification
//                        BootWakefulReceiver.completeWakefulIntent(intent);
                    }
                }, 0, timeInterval);
            }
        });
    }

    private void showComeBackNotification() {
        showNotificationWithMessage("Komm zurück und werde reich.");
    }

    private void showOrderCompleteNotification() {
        showNotificationWithMessage("Einige deiner Aufträge wurden durchgeführt.");
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
                .setContentIntent(pendingIntent);

        // notify user
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(1, builder.build());
    }

    private String buildNotificationChannel() {
        final String channel_id = "bbb_channel_id";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence channel_name = "bbb_channel_name";
            int channel_importance = NotificationManager.IMPORTANCE_DEFAULT;
            String description = "channel for bbb";
            NotificationChannel channel = new NotificationChannel(channel_id, channel_name, channel_importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            assert notificationManager != null;
            notificationManager.createNotificationChannel(channel);
        }
        return channel_id;
    }
}
