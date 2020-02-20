package com.example.sharesapp.FunktionaleKlassen.Handler;

import android.os.Handler;
import android.os.Looper;

import com.example.sharesapp.FunktionaleKlassen.JSON.ToModel.RequestHistoricalQuotePrices;
import com.example.sharesapp.FunktionaleKlassen.JSON.ToModel.RequestQuotePrices;
import com.example.sharesapp.FunktionaleKlassen.JSON.ToModel.RequestSymbol;
import com.example.sharesapp.Model.FromServerClasses.Aktie;
import com.example.sharesapp.Model.Model;
import com.example.sharesapp.REST.Requests;
import com.example.sharesapp.REST.RequestsBuilder;

import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import okhttp3.Response;

public class AsyncTaskHandler {

    private static Handler mHandler = new Handler(Looper.getMainLooper());

    public static void handle(Response response) throws IOException {

        String url = response.request().url().toString();
        final String s = Objects.requireNonNull(response.body()).string();
        Runnable runnable = null;

        if (url.contains("ref-data")) {
            runnable = new Runnable() {
                @Override
                public void run() {
                    try {
                        new RequestSymbol(s);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
        }
        else if (url.contains("stock")){
            runnable = new Runnable() {
                @Override
                public void run() {
                    try {
                        new RequestQuotePrices(s);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
        } else if(url.contains("chart") ){
            try {
                System.out.print(s);
                new RequestHistoricalQuotePrices(s);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        if (runnable != null) {
            mHandler.post(runnable);
        }

    }
}