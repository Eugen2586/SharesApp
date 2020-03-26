package com.example.sharesapp.Model.FromServerClasses;

public class Crypto {

    //
    //
    private String symbol = null;
    private String sector = null; // not needed (will return cryptocurrency)
    private String calculationPrice = null;
    private String high = null;
    private String low = null;
    private String latestPrice = null;
    private String latestSource = null; // not needed (This will always be Real time price)
    private String latestVolume = null;
    private String previousClose = null;
    private String bidPrice = null;
    private String bidSize = null;
    private String askPrice = null;
    private String askSize = null;
    private long latestUpdate = 0;

    private String name = null;
    private String exchange = null;
    private String date = null;
    private String type = null;
    private String region = null;
    private String currency = null;
    private boolean isEnabled = false;

    public Crypto(String symbol,
            String sector,
            String calculationPrice,
            String high,
            String low,
            String latestPrice,
            String latestSource,
            String latestVolume,
            String previousClose,
            String bidPrice,
            String bidSize,
            String askPrice,
            String askSize,
            long latestUpdate) {
        this.symbol = symbol;
        this.sector = sector;
        this.calculationPrice = calculationPrice;
        this.high = high;
        this.low = low;
        this.latestPrice = latestPrice;
        this.latestSource = latestSource;
        this.latestVolume = latestVolume;
        this.previousClose = previousClose;
        this.bidPrice = bidPrice;
        this.bidSize = bidSize;
        this.askPrice = askPrice;
        this.askSize = askSize;
        this.latestUpdate = latestUpdate;
    }

    public Crypto() {

    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public String getCalculationPrice() {
        return calculationPrice;
    }

    public void setCalculationPrice(String calculationPrice) {
        this.calculationPrice = calculationPrice;
    }

    public String getHigh() {
        return high;
    }

    public void setHigh(String high) {
        this.high = high;
    }

    public String getLow() {
        return low;
    }

    public void setLow(String low) {
        this.low = low;
    }

    public String getLatestPrice() {
        return latestPrice;
    }

    public void setLatestPrice(String latestPrice) {
        this.latestPrice = latestPrice;
    }

    public String getLatestSource() {
        return latestSource;
    }

    public void setLatestSource(String latestSource) {
        this.latestSource = latestSource;
    }

    public String getLatestVolume() {
        return latestVolume;
    }

    public void setLatestVolume(String latestVolume) {
        this.latestVolume = latestVolume;
    }

    public String getPreviousClose() {
        return previousClose;
    }

    public void setPreviousClose(String previousClose) {
        this.previousClose = previousClose;
    }

    public String getBidPrice() {
        return bidPrice;
    }

    public void setBidPrice(String bidPrice) {
        this.bidPrice = bidPrice;
    }

    public String getBidSize() {
        return bidSize;
    }

    public void setBidSize(String bidSize) {
        this.bidSize = bidSize;
    }

    public String getAskPrice() {
        return askPrice;
    }

    public void setAskPrice(String askPrice) {
        this.askPrice = askPrice;
    }

    public String getAskSize() {
        return askSize;
    }

    public void setAskSize(String askSize) {
        this.askSize = askSize;
    }

    public long getLatestUpdate() {
        return latestUpdate;
    }

    public void setLatestUpdate(long latestUpdate) {
        this.latestUpdate = latestUpdate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }
}
