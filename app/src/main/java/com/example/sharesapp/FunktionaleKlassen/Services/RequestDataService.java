package com.example.sharesapp.FunktionaleKlassen.Services;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;

import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

public class RequestDataService extends Service {
    private final LocalBinder mBinder = new LocalBinder();
    protected Handler handler;
    Timer timer = new Timer();

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
        // cancel the timer
        timer.cancel();

        super.onDestroy();
    }

    @Override
    public int onStartCommand(final Intent intent, int flags, int startId) {
        // send Requests for all Stocks which have an order every 30 min
        handler = new Handler();
        handler.post(new TimerTask() {
            @Override
            public void run() {
                int timeInterval = 1 * 30 * 1000; // 30sec
                timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        System.out.println("...............................................................................Request Not Sticky");
                        // get all symbols for requests
                        Set<String> symbolSet = ServiceRequestFunctionality.getSymbolSet();

                        // requests for all stocks with symbols in symbolSet
                        ServiceRequestFunctionality.asyncRequestsForStocks(symbolSet);
                    }
                }, 0, timeInterval);
            }
        });

        return Service.START_NOT_STICKY;
    }
}
