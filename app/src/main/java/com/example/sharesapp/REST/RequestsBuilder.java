package com.example.sharesapp.REST;

import java.util.List;

/**
 * Klase, in der die Urls gebaut werden.
 */

public class RequestsBuilder {

    /**
     * Baut aus Symbol die url für Time series Anfragen.
     * @param symbol Das Symbol.
     * @return Die Time Series URL.
     */
    public static String getTimeSeriesURL(String symbol) {
        return "time-series/REPORTED_FINANCIALS/" + symbol + "?";
    }

    /**
     * Gibt die Url, um alle Symbole zu bekommen, zurück.
     * @return Url um alle Symbole zu bekommen.
     */

    public static String getAllSymbolsURL() {
        return "ref-data/symbols" + "?";
    }

    /**
     * Baut aus dem gegeben String die Url für Suchanfragen
     * @param fragment Der gesuchte String.
     * @return Die Url für die Suchanfrage.
     */

    public static String getSearchURL(String fragment) {
            return "search/{" + fragment + "}" + "?";
    }

    /**
     * Baut mit gegeben Symbol die Url um mehr Details über die Aktie zu bekommen.
     * @param symbol Das Symbol der Aktie.
     * @return Die Url um mehr Informationen über die Aktie zu bekommen.
     */

    public static String getQuoteData(String symbol) {
        return "data-points/" + symbol + "?";
    }

    /**
     * Baut aus gegeben Symbol die Url um Imformationen über die Aktie zu bekommen.
     * @param symbol Das Symbol der Aktie.
     * @return Die Url um Informationen über die Aktie zu bekommen.
     */

    public static String getQuote(String symbol) {
        return "stock/" + symbol + "/quote" + "?";
    }

    /**
     * Baut aus gegeben Symbol die Url um alte Imformationen innerhalb des Zeitraums der range über die Aktie zu bekommen.
     * @param symbol Das Symbol der Aktie.
     * @param range Der Zeitraum in dem man Informationen erhalten möchte.
     * @return Die Url um Informationen über die Aktie zu bekommen.
     */

    public static String getHistoricalQuotePrices(String symbol, String range) {
        return "stock/" + symbol + "/chart/" + range + "?";
    }

    public static String getCryptoSymbolsUrl(){
        return "ref-data/crypto/symbols" + "?";
    }

    public static String getCryptoQuoteUrl(String symbol){
        return "crypto/" + symbol + "/quote" + "?";
    }

    public static String getCurrencyRatesUrl(List<String> symbols) {
        StringBuilder s = new StringBuilder();
        for (String symbol : symbols) {
            s.append(symbol).append(",");
        }
        return "fx/latest?symbols=" + s + "&&";
    }

    public static String getCurrencyConversionsUrl(String symbol) {
        return "fx/convert?symbols=" + symbol + "&&";
    }

    public static String getCurrencyHistoricalUrl(String symbol) {
        return "fx/historical?symbols=" + symbol + "&last=30&";
    }
}
