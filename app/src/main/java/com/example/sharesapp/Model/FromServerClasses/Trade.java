package com.example.sharesapp.Model.FromServerClasses;

import java.util.Date;

public class Trade {
    //ToDo Hier werden die Attribute eines Trades gepflegt

    /* Hier sollen die entsprechenden Trades gestored werden */
    //Chris K.

    private Aktie aktie;
    private int anzahl;
    private boolean kauf;
    private float preis;
    private String date;

    public Trade(Aktie aktie, int anzahl, boolean kauf, float preis, String date) {
        this.aktie = aktie;
        this.anzahl = anzahl;
        this.kauf = kauf;
        this.preis = preis;
        this.date = date;
    }

    public Aktie getAktie() {
        return aktie;
    }

    public int getAnzahl() {
        return anzahl;
    }

    public boolean isKauf() {
        return kauf;
    }

    public float getPreis() {
        return preis;
    }

    public String getDate() {
        return date;
    }

}
