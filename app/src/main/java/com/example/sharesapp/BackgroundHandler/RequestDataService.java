package com.example.sharesapp.BackgroundHandler;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;

import com.example.sharesapp.Model.FromServerClasses.Order;
import com.example.sharesapp.Model.Model;
import com.example.sharesapp.REST.Range;
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
    private Requests requests = new Requests();
    private Model model = new Model();
    final Timer timer = new Timer();

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
        timer.cancel();
        super.onDestroy();
    }

    @Override
    public int onStartCommand(final Intent intent, int flags, int startId) {
        handler = new Handler();
        handler.post(new TimerTask() {
            @Override
            public void run() {
                int timeInterval = 1 * 20 * 1000; // 1 min
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        // get all symbols for requests
                        Set<String> symbolSet = getSymbolSet();

                        // requests for all stocks with symbols in symbolSet
                        asyncRequestsForStocks(symbolSet);

                        System.out.println("...............................................................................Request");
                    }
                }, 0, timeInterval);
            }
        });

        return Service.START_NOT_STICKY;
    }

    private Set<String> getSymbolSet() {
        ArrayList<Order> buyOrderList = model.getData().getBuyOrderList().getValue();
        ArrayList<Order> sellOrderList = model.getData().getSellOrderList().getValue();
        Set<String> symbolSet = new HashSet<>();

        // get all symbols for requests
        if (buyOrderList != null) {
            for (Order buyOrder : buyOrderList) {
                symbolSet.add(buyOrder.getSymbol());
            }
        }
        if (sellOrderList != null) {
            for (Order sellOrder : sellOrderList) {
                symbolSet.add(sellOrder.getSymbol());
            }
        }

        return symbolSet;
    }

    private void asyncRequestsForStocks(Set<String> symbolSet) {
        for (String symbol : symbolSet) {
            Requests requests = new Requests();
            try {
                requests.asyncRun(RequestsBuilder.getQuote(symbol));
//                requests.asyncRun(RequestsBuilder.getHistoricalQuotePrices(symbol, Range.oneMonth));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
