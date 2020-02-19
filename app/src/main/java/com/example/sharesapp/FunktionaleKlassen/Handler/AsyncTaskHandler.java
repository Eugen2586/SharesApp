package com.example.sharesapp.FunktionaleKlassen.Handler;

import android.os.Handler;
import android.os.Looper;

import com.example.sharesapp.FunktionaleKlassen.JSON.ToModel.RequestSymbol;

import java.io.IOException;
import java.util.Objects;

import okhttp3.Response;

public class AsyncTaskHandler {

    private static Handler mHandler = new Handler(Looper.getMainLooper());

    public static void handle(Response response) throws IOException {

        String url = response.request().url().toString();
        final String s = Objects.requireNonNull(response.body()).string();
        Runnable runnable = null;

        if (url.contains("ref-data")) {
            System.out.println("Data: " + s);

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
        if (runnable != null) {
            mHandler.post(runnable);
        }

    }
}
