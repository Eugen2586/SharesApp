package com.example.sharesapp.REST;

import com.example.sharesapp.FunktionaleKlassen.Handler.AsyncTaskHandler;
import com.example.sharesapp.Model.FromServerClasses.Aktie;

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

/**
 * Klasse, in der Anfragen an den Server gesendet und die Antworten an einen Handler weitergegeben werden.
 */
public class Requests {

    private static final MediaType JSON
            = MediaType.get("application/json; charset=utf-8");
    private static OkHttpClient client = null;
    private final String baseURL = "https://sandbox.iexapis.com/stable/";
    private String token = "token=Tpk_f26c06bf165b426eb0adb59f1f1d9ee4";

    /**
     * Standart Konstruktor, erstellt OkHttpClient
     */
    public Requests() {
        if (client == null) {
            client = new OkHttpClient();
        }
    }

    /**
     * Synchrone Anfrage an den Server mit der gegebenen url.
     * @param url Die url.
     * @return Die Antwort des Servers.
     * @throws IOException Falls etewas schief geht.
     */

    public String run(String url) throws IOException {
        Request request = new Request.Builder()
                .url(baseURL + url + token)
                .addHeader("ruleName", "\"My Rule\",\n" +
                        "            conditions: [\n" +
                        "                ['latestPrice','>',0.01]\n" +
                        "            ],")
                .build();

        try (Response response = client.newCall(request).execute()) {
            return Objects.requireNonNull(response.body()).string();
        }
    }

    /**
     * Schickt synchron den String json an die url und gibt die Antwort zurück
     * @param url Die url.
     * @param json Der String der gesendet wird.
     * @return Die Antwort des Servers
     * @throws IOException Falls etwas schief läuft.
     */

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

    /**
     * Sendet eine asynchrone Anfrage an die gegebene url und gibt die Antwort weiter an den Handler.
     * @param url Die Url.
     * @throws IOException Wirft IOException, wenn etwas schief läuft.
     */

    public void asyncRun(String url) throws IOException {
        Request request = new Request.Builder()
                .url(baseURL + url + token)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                AsyncTaskHandler.handle(response);
            }

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }

        });
    }

    public static void quoteAndPriceRequest(Aktie currentStock) {
        Requests requests = new Requests();
        if (currentStock.isCrypto()) {
            try {
                requests.asyncRun(RequestsBuilder.getCryptoQuoteUrl(currentStock.getSymbol()));
                requests.asyncRun(RequestsBuilder.getCurrencyHistoricalUrl(currentStock.getSymbol()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                requests.asyncRun(RequestsBuilder.getQuote(currentStock.getSymbol()));
                requests.asyncRun(RequestsBuilder.getHistoricalQuotePrices(currentStock.getSymbol(), Range.oneMonth));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void quoteRequest(Aktie currentStock) {
        Requests requests = new Requests();
        if (currentStock.isCrypto()) {
            try {
                requests.asyncRun(RequestsBuilder.getCryptoQuoteUrl(currentStock.getSymbol()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                requests.asyncRun(RequestsBuilder.getQuote(currentStock.getSymbol()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
