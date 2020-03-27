package com.example.sharesapp.Model.FromServerClasses;

/**
 * Diese Klasse beschreibt für die Diagramme relevante Datenpunkte
 */
public class DataPoint {

    private double high;
    private double low;
    private String date;
    private String change;
    private String rate;
    private String symbol;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getHigh() {
        return high;
    }

    public void setHigh(double high) {
        this.high = high;
    }

    public double getLow() {
        return low;
    }

    public void setLow(double low) {
        this.low = low;
    }

    public String getChange() {
        return change;
    }

    public void setChange(String change) {
        this.change = change;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    /*
    private String volume;
    private String timestamp;
    private String open;
    private String close;
    private String uVolume;
    private String uHigh;
    private String changePercent;
    private String label;
    private String changeOverTime;
    private boolean cryptoFlag = false;
    private boolean isDerived;

    public String getUVolume() {
        return uVolume;
    }

    public void setUVolume(String uVolume) {
        this.uVolume = uVolume;
    }

    public String getuHigh() {
        return uHigh;
    }

    public void setuHigh(String uHigh) {
        this.uHigh = uHigh;
    }

    public String getChangePercent() {
        return changePercent;
    }

    public void setChangePercent(String changePercent) {
        this.changePercent = changePercent;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getChangeOverTime() {
        return changeOverTime;
    }

    public void setChangeOverTime(String changeOverTime) {
        this.changeOverTime = changeOverTime;
    }

    public String getOpen() {
        return open;
    }

    public void setOpen(String open) {
        this.open = open;
    }

    public String getClose() {
        return close;
    }

    public void setClose(String close) {
        this.close = close;
    }

    public boolean isDerived() {
        return isDerived;
    }

    public void setDerived(boolean derived) {
        isDerived = derived;
    }

    public boolean isCrypto() {
        return cryptoFlag;
    }

    public void setCryptoFlag(boolean cryptoFlag) {
        this.cryptoFlag = cryptoFlag;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {

        this.volume = volume;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
    */
}
