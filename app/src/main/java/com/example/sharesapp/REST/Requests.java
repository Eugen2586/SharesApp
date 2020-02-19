package com.example.sharesapp.REST;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Requests {

    private static final MediaType JSON
            = MediaType.get("application/json; charset=utf-8");
    static OkHttpClient client = null;
    private final String baseURL = "https://sandbox.iexapis.com/stable/";
    private String token = "?token=Tpk_f10f1ddb8a1d4baaa44d427f0ddbea19";

    public Requests() {
        if (client == null) {
            client = new OkHttpClient();
        }
    }


    public String run(String url) throws IOException {
        Request request = new Request.Builder()
                .url(baseURL + url + token)
                .build();

        try (Response response = client.newCall(request).execute()) {
            return Objects.requireNonNull(response.body()).string();
        }
    }


    String post(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(json, JSON);
        Request request = new Request.Builder()
                .url(baseURL + url + token)
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            return Objects.requireNonNull(response.body()).string();
        }
    }

    public void asyncRun(String url) throws IOException {
        Request request = new Request.Builder()
                .url(baseURL + url + token)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                
            }

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }

        });
    }
}
