package com.example.sharesapp.FunktionaleKlassen.Services;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;

import com.example.sharesapp.Model.FromServerClasses.Aktie;
import com.example.sharesapp.Model.FromServerClasses.Order;
import com.example.sharesapp.Model.Model;
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
                        // get all stocks for requests
                        Set<Aktie> stockSet = getStockSet();

                        // requests for all stocks with symbols in symbolSet
                        asyncRequestsForStocks(stockSet);
                    }
                }, 0, timeInterval);
            }
        });

        return Service.START_NOT_STICKY;
    }

    private Set<Aktie> getStockSet() {
        Model model = new Model();
        ArrayList<Order> buyOrderList = model.getData().getBuyOrderList().getValue();
        ArrayList<Order> sellOrderList = model.getData().getSellOrderList().getValue();
        Set<Aktie> stockSet = new HashSet<>();

        // get all symbols for requests
        if (buyOrderList != null) {
            for (Order buyOrder : buyOrderList) {
                stockSet.add(buyOrder.getStock());
            }
        }
        if (sellOrderList != null) {
            for (Order sellOrder : sellOrderList) {
                stockSet.add(sellOrder.getStock());
            }
        }

        return stockSet;
    }

    private void asyncRequestsForStocks(Set<Aktie> stockSet) {
        for (Aktie stock : stockSet) {
            Requests.quoteRequest(stock);
        }
    }
}
