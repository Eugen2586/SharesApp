package com.example.sharesapp.Model.FromServerClasses;

public class Order {
    private Aktie stock;
    private String symbol;
    private int number;
    private float limit;

    public Order(Aktie stock, String symbol, int number, float limit) {
        this.stock = stock;
        this.symbol = symbol;
        this.number = number;
        this.limit = limit;
    }

    public Aktie getStock() {
        return stock;
    }

    public String getSymbol() {
        return symbol;
    }

    public int getNumber() {
        return number;
    }

    public float getLimit() {
        return limit;
    }
}
