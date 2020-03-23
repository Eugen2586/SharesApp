package com.example.sharesapp.Model.FromServerClasses;

public class Order {
    private String symbol;
    private int number;
    private float limit;

    public Order(String symbol, int number, float limit) {
        this.symbol = symbol;
        this.number = number;
        this.limit = limit;
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
