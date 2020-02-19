package com.example.sharesapp.Model.FromServerClasses;

import java.util.Date;

class Trade {
    //ToDo Hier werden die Attribute eines Trades gepflegt

    /* Hier sollen die entsprechenden Trades gestored werden */
    //Chris K.

    private Aktie aktie;
    private int anzahl;
    private boolean kauf;
    private float preis;
    private Date date;

    public Trade(Aktie aktie, int anzahl, boolean kauf, float preis, Date date) {
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

    public Date getDate() {
        return date;
    }

}
