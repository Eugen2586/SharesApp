package com.example.sharesapp.FunktionaleKlassen.Handler;

import com.example.sharesapp.FunktionaleKlassen.JSON.ToModel.RequestForex;
import com.example.sharesapp.FunktionaleKlassen.JSON.ToModel.RequestHistoricalQuotePrices;
import com.example.sharesapp.FunktionaleKlassen.JSON.ToModel.RequestQuote;
import com.example.sharesapp.FunktionaleKlassen.JSON.ToModel.RequestQuotePrices;
import com.example.sharesapp.FunktionaleKlassen.JSON.ToModel.RequestSearch;
import com.example.sharesapp.FunktionaleKlassen.JSON.ToModel.RequestSymbol;

import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.Objects;

import okhttp3.Response;

/**
 * Klasse, in der die Antworten des Servers auf asynchrone Anfragen behandelt werden.
 */

public class AsyncTaskHandler {

    /**
     * Verarbeitet die Antwort des Servers.
     *
     * @param response Die Antwort des Servers.
     * @throws IOException Wirft Exception, falls etwas schief läuft.
     */

    public static void handle(Response response) throws IOException {

        String url = response.request().url().toString();
        final String s = Objects.requireNonNull(response.body()).string();

        if (url.contains("ref-data")) {
            try {
                new RequestSymbol(s);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (url.contains("quote")) {
            try {
                new RequestQuote(s);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (url.contains("chart") || url.contains("fx/historical")) {
            try {
                new RequestHistoricalQuotePrices(s);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else if (url.contains("stock")) {
            try {
                new RequestQuotePrices(s);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (url.contains("search")) {
            try {
                new RequestSearch(s);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else if (url.contains("crypto/symbols")) {
            // TODO: "fx"
            try {
                new RequestForex(s);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
