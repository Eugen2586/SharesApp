package com.example.sharesapp.Model.FromServerClasses;


/**
 * Verarbeitet die Symbole einer Suchteingabe in dem Suchmenü ins das Model.
 */
public class SearchRequest {
    String symbol;
    String securityName;
    String securityType;
    String region;
    String exchange;

    /**
     * Gibt das Symbol der Aktie zurück.
     * @return Symbol der gesuchten Aktie.
     */
    public String getSymbol() {
        return symbol;
    }

    /**
     * Gibt den SecurityNamen der gesuchten Aktie zurück.
     * @return Security Name der Aktie
     */
    public String getSecurityName() {
        return securityName;
    }
    /**
     * Gibt den SecurityType der gesuchten Aktie zurück.
     * @return Security Type der Aktie
     */
    public String getSecurityType() {
        return securityType;
    }
    /**
     * Gibt die Region der gesuchten Aktie zurück.
     * @return Region der Aktie
     */
    public String getRegion() {
        return region;
    }
    /**
     * Gibt die Handelbörse der gesuchten Aktie zurück.
     * @return Börsenhandelsplatz der Aktie
     */
    public String getExchange() {
        return exchange;
    }

    /**
     * Befüllt die Klasse mit allen Daten.
     * @param symbol Symbol der gesuchten Aktie
     * @param securityName SecurityName der gesuchten Aktie.
     * @param securityType SecurityType der gesuchten Aktie.
     * @param region Region der gesuchten Aktie.
     * @param exchange Handelsplatz der gesuchten Aktie.
     */
    public SearchRequest(String symbol, String securityName, String securityType, String region, String exchange) {
        this.symbol = symbol;
        this.securityName = securityName;
        this.securityType = securityType;
        this.region = region;
        this.exchange = exchange;
    }

}
