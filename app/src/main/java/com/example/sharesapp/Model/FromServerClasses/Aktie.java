package com.example.sharesapp.Model.FromServerClasses;

import org.json.JSONObject;



public class Aktie {
    //ToDo neue Variablen für die Aktie


    int menge;
    private String exchange;

    private String symbol;
    private String name;
    private String date;
    private String type;
    private String region;
    private String currency;
    private String enabled;
    private float  preis;
    private int anzahl;
    private float change;

    public Aktie() {

    }

    public Aktie(int menge, String exchange, String symbol, String name, String date, String type, String region, String currency, String enabled, float preis) {
        this.menge = menge;
        this.exchange = exchange;
        this.symbol = symbol;
        this.name = name;
        this.date = date;
        this.type = type;
        this.region = region;
        this.currency = currency;
        this.enabled = enabled;
        this.preis = preis;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public String getExchange() {
        return exchange;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getRegion() {
        return region;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCurrency() {
        return currency;
    }

    public void setEnabled(String enabled) {
        this.enabled = enabled;
    }

    public String getEnabled() {
        return enabled;
    }

    public float getPreis() {
        return preis;
    }

    public void setPreis(float preis) {
        this.preis = preis;
    }

    public int getAnzahl() {
        return anzahl;
    }

    public void setAnzahl(int anzahl) {
        this.anzahl = anzahl;
    }

    public void setChange(float change) {
        this.change = change;
    }

    public Object getChange() {
        return change;
    }
}
