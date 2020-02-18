package com.example.sharesapp.REST;

public class RequestsBuilder {

    //TODO Key entspricht Symbol aus Aktie
    public static String getTimeSeriesURL(String key) {
        return "time-series/REPORTED_FINANCIALS/" + key;
    }


    public static String getAllSymbolsURL() {
        return "/ref-data/symbols";
    }

    //TODO In der Searchbar wurde das Eingegeben unter dem Fragment.
    public String getSearchURL(String fragment) {
        return "/search/{" + fragment + "}";
    }

}
