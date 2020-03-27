package com.example.sharesapp.Model.FromServerClasses;


/**
 * Verarbeitet die Symbole einer Sucheingabe in dem Suchmenü in das Model.
 */
public class SearchedURLS {
    private String symbol;

    /**
     * befüllt die Klasse mit dem benötigten symbolParameter
     * @param symbol symbol der Aktie
     */
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }

/*
    private String securityName;
    private String securityType;
    private String region;
    private String exchange;

    public String getExchange() {
        return exchange;
    }

    public String getRegion() {
        return region;
    }

    public String getSecurityType() {
        return securityType;
    }

    public String getSecurityName() {
        return securityName;
    }

    public void setSecurityName(String securityName) {
        this.securityName = securityName;
    }

    public void setSecurityType(String securityType) {
        this.securityType = securityType;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }
    */
}
