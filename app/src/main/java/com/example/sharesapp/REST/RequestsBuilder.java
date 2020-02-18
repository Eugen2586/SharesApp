package com.example.sharesapp.REST;

public class RequestsBuilder {

    public String getTimeSeriesURL(String key) {
        return "time-series/REPORTED_FINANCIALS/" + key;
    }

    public String getAllSymbolsURL() {
        return "/ref-data/symbols";
    }
}
