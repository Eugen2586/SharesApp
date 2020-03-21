package com.example.sharesapp.BackgroundHandler;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.sharesapp.Model.FromServerClasses.Aktie;
import com.example.sharesapp.Model.Model;
import com.example.sharesapp.R;
import com.example.sharesapp.REST.Requests;
import com.example.sharesapp.REST.RequestsBuilder;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class RequestDataService extends Service {
    private final LocalBinder mBinder = new LocalBinder();
    protected Handler handler;
    protected Toast mToast;
    private Requests requests = new Requests();

    public class LocalBinder extends Binder {
        public RequestDataService getService() {
            return RequestDataService .this;
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
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        final Model model = new Model();
        handler = new Handler();
        handler.post(new TimerTask() {
            @Override
            public void run() {
                Timer t = new Timer();
                t.schedule(new TimerTask(){
                    @Override
                    public void run() {
                        if (model.getData().getDepot().getAktienImDepot().getValue() != null) {
                            for (Aktie stock: model.getData().getDepot().getAktienImDepot().getValue()) {
                                try {
                                    requests.asyncRun(RequestsBuilder.getQuote(stock.getSymbol()));
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }

                            // TODO: Try to sell or buy stock
                            // TODO: If all bought or sold, stopSelf();
                        }
                        // TODO: enable Notification if something was bought or sold
//                        showNotification();
                    }
                }, 0, 5 * 60 * 1000); // 5 min
            }
        });
        return Service.START_STICKY;
    }

    private void showNotification() {
        // https://developer.android.com/training/notify-user/build-notification#kotlin
        // build notification channel
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "channel_name";
            String description = "channel_desciption";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("channel_id", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            assert notificationManager != null;
            notificationManager.createNotificationChannel(channel);
        }

        // build notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "channel_id")
                .setSmallIcon(R.drawable.logo)
                .setContentTitle("BBB")
                .setContentText("Content Content")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        // notify user
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(1, builder.build());
    }
}
