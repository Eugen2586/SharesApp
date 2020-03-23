package com.example.sharesapp.BackgroundHandler;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.sharesapp.DrawerActivity;
import com.example.sharesapp.Model.FromServerClasses.Aktie;
import com.example.sharesapp.Model.FromServerClasses.Order;
import com.example.sharesapp.Model.Model;
import com.example.sharesapp.R;
import com.example.sharesapp.REST.Requests;
import com.example.sharesapp.REST.RequestsBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

public class RequestDataService extends Service {
    private final LocalBinder mBinder = new LocalBinder();
    protected Handler handler;
    protected Toast mToast;
    private Requests requests = new Requests();

    public class LocalBinder extends Binder {
        public RequestDataService getService() {
            return RequestDataService.this;
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
    public int onStartCommand(final Intent intent, int flags, int startId) {
        final Model model = new Model();
        handler = new Handler();
        handler.post(new TimerTask() {
            @Override
            public void run() {
                final Timer t = new Timer();
                int timeInterval = 1 * 10 * 1000; // TODO: set to 15 min
                t.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        ArrayList<Aktie> stockList = model.getData().getAktienList().getValue();
                        if (stockList != null) {
                            ArrayList<Order> buyOrderList = model.getData().getBuyOrderList();
                            ArrayList<Order> sellOrderList = model.getData().getSellOrderList();
                            Set<String> symbolSet = new HashSet<>();

                            // get all symbols for requests
                            for (Order buyOrder : buyOrderList) {
                                symbolSet.add(buyOrder.getSymbol());
                            }
                            for (Order sellOrder : sellOrderList) {
                                symbolSet.add(sellOrder.getSymbol());
                            }

                            // requests for all symbols
                            for (String symbol : symbolSet) {
                                Requests requests = new Requests();
                                try {
                                    requests.asyncRun(RequestsBuilder.getQuote(symbol));
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }

                            boolean allOrderCompleted = buyOrderList.size() == 0 && sellOrderList.size() == 0;
                            boolean oneOrderCompleted = false;
                            // in Observer
                            // try to buy stock
                            for (Order buyOrder : buyOrderList) {
                                for (Aktie stock : stockList) {
                                    if (stock.getSymbol().equals(buyOrder.getSymbol()) && stock.getPreis() < buyOrder.getLimit()) {
                                        // buy stock to currentPrice
                                        Aktie stockClone = stock.getClone();
                                        stockClone.setAnzahl(buyOrder.getNumber());
                                        model.getData().getDepot().kaufeAktie(stockClone);
                                        oneOrderCompleted = true;
                                    }
                                }
                            }
                            // try to sell stock
                            for (Order sellOrder : sellOrderList) {
                                for (Aktie stock : stockList) {
                                    if (stock.getSymbol().equals(sellOrder.getSymbol()) && stock.getPreis() > sellOrder.getLimit()) {
                                        // buy stock to currentPrice
                                        Aktie stockClone = stock.getClone();
                                        stockClone.setAnzahl(sellOrder.getNumber());
                                        model.getData().getDepot().verkaufeAktie(stockClone);
                                        oneOrderCompleted = true;
                                    }
                                }
                            }

                            // show notification depending on number of completed orders
                            if (allOrderCompleted) {
                                showAllOrderCompleteNotification();
                            } else if (oneOrderCompleted) {
                                showOneOrderCompleteNotification();
                            } else {
                                showComeBackNotification();
                            }
                        }
                    }
                }, timeInterval, timeInterval);
            }
        });
        return Service.START_STICKY;
    }

    private void showComeBackNotification() {
        showNotificationWithMessage("Komm zurück und werde reich.");
    }

    private void showOneOrderCompleteNotification() {
        showNotificationWithMessage("Einige deiner Aufträge wurden durchgeführt.");
    }

    private void showAllOrderCompleteNotification() {
        showNotificationWithMessage("Alle Aufträge wurden durchgeführt.");
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
