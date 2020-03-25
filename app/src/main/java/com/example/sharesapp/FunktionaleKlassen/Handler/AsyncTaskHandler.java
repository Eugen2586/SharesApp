package com.example.sharesapp.FunktionaleKlassen.Handler;

import com.example.sharesapp.FunktionaleKlassen.JSON.ToModel.RequestHistoricalQuotePrices;
import com.example.sharesapp.FunktionaleKlassen.JSON.ToModel.RequestQuote;
import com.example.sharesapp.FunktionaleKlassen.JSON.ToModel.RequestQuotePrices;
import com.example.sharesapp.FunktionaleKlassen.JSON.ToModel.RequestSearch;
import com.example.sharesapp.FunktionaleKlassen.JSON.ToModel.RequestSymbol;

import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.Objects;

import okhttp3.Response;

public class AsyncTaskHandler {

    public static void handle(Response response) throws IOException {

        String url = response.request().url().toString();
        final String s = Objects.requireNonNull(response.body()).string();

        if (url.contains("ref-data")) {

            try {
                new RequestSymbol(s);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }else if (url.contains("quote")) {
            try {
                new RequestQuotePrices(s);
                new RequestQuote(s);
            }catch(Exception e){
                e.printStackTrace();
            }
        }else if (url.contains("chart")) {
            try {
                new RequestHistoricalQuotePrices(s);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else if (url.contains("stock")) {

            try {
                new RequestQuotePrices(s);
                new RequestQuote(s);
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (url.contains("search")) {
            try {
                new RequestSearch(s);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

    }
}
