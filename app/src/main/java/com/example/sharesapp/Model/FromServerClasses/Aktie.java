package com.example.sharesapp.Model.FromServerClasses;

import com.example.sharesapp.Model.Model;

import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Comparator;


public class Aktie implements Comparator {
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
    private float preis = 0.0f;
    private int anzahl = 0;
    private float change;
    private ArrayList chart;

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

    public Aktie(String symbol, String securityName, String securityType, String region, String exchange) {
        this.exchange = exchange;
        this.symbol = symbol;
        this.name = securityName;
        this.type = securityType;
        this.region = region;
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
        this.menge = anzahl;
    }

    public void setChange(float change) {
        this.change = change;
    }

    public Object getChange() {
        return change;
    }

    public void setChart(ArrayList chart) {
        this.chart = chart;
    }

    public ArrayList getChart() {

        Aktie aktie  = new Aktie();
        aktie.setSymbol("AAPL");
        int ind = new Model().getData().getAktienList().getValue().indexOf(aktie);
        new Model().getData().getAktienList().getValue().get(ind);


        return chart;
    }

    @Override
    public int compare(Object o1, Object o2) {
        Aktie t1 , t2;
        t1 = (Aktie) o1;
        t2 = (Aktie) o2;
        if(t1.getSymbol().equals(t2.getSymbol())){
            return 0;
        }
        return -1;
    }

    public Aktie getClone() {
        return new Aktie( this.menge, this.exchange, this.symbol, this.name, this.date, this.type, this.region, this.currency, this.enabled, this.preis);
    }

    public JSONObject getJsonFromAktie() {
        org.json.simple.JSONObject obj = new org.json.simple.JSONObject();
        obj.put("menge", menge);
        obj.put("exchange",exchange);
        obj.put("symbol",symbol);
        obj.put("name",name);
        obj.put("date",date);
        obj.put("type", type);
        obj.put("region",region);
        obj.put("currency",currency);
        obj.put("enabled", enabled);
        obj.put("preis", String.valueOf(preis));
        obj.put("anzahl", String.valueOf(anzahl));
        obj.put("change", String.valueOf(change));

        return obj;
    }
}
