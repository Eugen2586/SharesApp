package com.example.sharesapp.Model.FromServerClasses;

/**
 * Eine Kauf- oder Verkaufsorder mit entsprechendem Limit wird in dieser Klasse abgebildet.
 */
public class Order {

    private Aktie stock;
    private String symbol;
    private int number;
    private float limit;

    /**
     * Der Konstruktor initialisiert alle Parameter der Klasse.
     * @param stock Aktie, die gekauft oder verkauft werden soll.
     * @param symbol Symbol der zu kaufenden oder verkaufenden Aktie.
     * @param number Anzahl der Handelsware.
     * @param limit Preis der als Limit
     */
    public Order(Aktie stock, String symbol, int number, float limit) {
        this.stock = stock;
        this.symbol = symbol;
        this.number = number;
        this.limit = limit;
    }

    /**
     * Getter der zu handelnden Aktie.
     * @return Aktie die gehandelt wird.
     */
    public Aktie getStock() {
        return stock;
    }

    /**
     * Getter des Sybols zu handelnden Aktie.
     * @return Symbol der Aktie.
     */
    public String getSymbol() {
        return symbol;
    }

    /**
     * Getter der Anzahl der zu handelnden Aktien.
     * @return Anzahl der zu handelnden Aktien.
     */
    public int getNumber() {
        return number;
    }

    /**
     * Getter des Limits zum Handeln der Aktie.
     * @return Limit.
     */
    public float getLimit() {
        return limit;
    }
}
