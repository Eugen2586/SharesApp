package com.example.sharesapp.Model.FromServerClasses;


/**
 * Doppelimplementation von SearchRequest.
 * Bitte alle Parameter aus entsprechend der erwähnten Klasse benutzen.
 */
public class SearchedURLS {
    private String symbol;
    private String securityName;
    private String securityType;
    private String region;
    private String exchange;

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSecurityName(String securityName) {

        this.securityName = securityName;
    }

    public String getSecurityName() {
        return securityName;
    }

    public void setSecurityType(String securityType) {

        this.securityType = securityType;
    }

    public String getSecurityType() {
        return securityType;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getRegion() {
        return region;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public String getExchange() {
        return exchange;
    }
}
