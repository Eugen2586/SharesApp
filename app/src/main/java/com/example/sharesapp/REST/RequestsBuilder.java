package com.example.sharesapp.REST;

public class RequestsBuilder {

    public static String getTimeSeriesURL(String key) {
        return "time-series/REPORTED_FINANCIALS/" + key;
    }

    public static String getAllSymbolsURL() {
        return "/ref-data/symbols";
    }

    public String getSearchURL(String fragment) {
        return "/search/{" + fragment + "}";
    }

    
}
