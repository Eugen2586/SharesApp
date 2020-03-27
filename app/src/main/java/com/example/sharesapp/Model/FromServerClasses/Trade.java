package com.example.sharesapp.Model.FromServerClasses;

import java.util.Date;

public class Trade {
    /**
     * Stellt die Klasse dar in der Handelsbewegungen abgebildet werden.
     */

    /* Hier sollen die entsprechenden Trades gestored werden */
    //Chris K.

    private Aktie aktie;
    private int anzahl;
    private boolean kauf;
    private float preis;
    private long millis;
    private String symbol;

    /**
     * Diese Klasse wird immer komplett initialisiert.
     * @param aktie gehandelte Aktie
     * @param anzahl Anzahl der gehandelten Aktien
     * @param kauf Flag ob es sich um einen Kauf handelt
     * @param preis Preis der Aktie beim Verkauf.
     * @param millis Zeit des Verkaufs.
     * @param symbol Symbol der Aktie.
     */
    public Trade(Aktie aktie, int anzahl, boolean kauf, float preis, long millis, String symbol) {
        this.aktie = aktie;
        this.anzahl = anzahl;
        this.kauf = kauf;
        this.preis = preis;
        this.millis = millis;
        this.symbol = symbol;
    }

    /**
     * Getter des Attributes Aktie.
     * @return Aktie des Trades.
     */

    public Aktie getAktie() {
        return aktie;
    }

    /**
     * Anzahl der gehandelten Ware im Trade
     * @return Anzahl der gehandelten Waren.
     */

    public int getAnzahl() {
        return anzahl;
    }

    /**
     * Gibt zurück ob es sich um einen Kauf oder Verkauf handelt.
     * @return Bei True ist es ein Kauf bei false ein Verkauf.
     */

    public boolean isKauf() {
        return kauf;
    }

    /**
     * Gibt den zum Zeitpunkt des verkaufens/kaufens anliegenden Preis wieder.
     * @return Preis zum Zeitpunkt des Handels
     */

    public float getPreis() {
        return preis;
    }

    /**
     * Zeitpunkt zu dem die Aktie gehandelt wurde.
     * @return Zeitpunt zu dem die Aktie gehandelt wurde.
     */

    public long getMillis() {
        return millis;
    }

    /**
     * Gibt das Symbol der Aktie wieder die gehandelt wird.
     * @return Symbol der Aktie die gehandelt wurde.
     */
    public String getSymbol() {
        return symbol;
    }

}
