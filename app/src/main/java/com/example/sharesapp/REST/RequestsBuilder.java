package com.example.sharesapp.REST;

public class RequestsBuilder {

    public static String getTimeSeriesURL(String symbol) {
        return "time-series/REPORTED_FINANCIALS/" + symbol;
    }

    public static String getAllSymbolsURL() {
        return "ref-data/symbols";
    }

    public String getSearchURL(String fragment) {
        return "search/{" + fragment + "}";
    }

    public static String getQuoteData(String symbol) {
        return "data-points/" + symbol;
    }

    public static String getQuote(String symbol) {
        return "stock/" + symbol + "quote/";
    }

    public static String getHistoricalQuotePrices(String symbol, String range) {
        return "stock/" + symbol + "/chart/" + range.toString();
    }
}
