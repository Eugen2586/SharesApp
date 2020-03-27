package com.example.sharesapp.Model.FromServerClasses;

import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Comparator;


public class Aktie implements Comparator {
    private int menge;
    private String companyName;
    private String open;
    private String close;
    private String high;
    private String highTime;
    private String low;
    private String lowTime;
    private long latestUpdate;
    private int latestVolume;
    private float previousClose;
    private String exchange;
    private String symbol;
    private String name;
    private String date;
    private String type;
    private String region;
    private String currency;
    private String enabled;
    private float price = 0;
    private int anzahl = 0;
    private ArrayList<DataPoint> chart;

    public Aktie() {

    }

    public Aktie(String symbol, String securityName, String securityType, String region, String exchange) {
        this.exchange = exchange;
        this.symbol = symbol;
        this.name = securityName;
        this.type = securityType;
        this.region = region;
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
        this.price = preis;
    }

    private float change;
    private float week52High;
    private float week52Low;

    public void setAdditionalData(
            String companyName,
            String open,
            String close,
            String high,
            String highTime,
            String low,
            String lowTime,
            float latestPrice,
            long latestUpdate,
            int latestVolume,
            float previousClose,
            float change,
            float week52High,
            float week52Low) {
        this.companyName = companyName;
        this.open = open;
        this.close = close;
        this.high = high;
        this.highTime = highTime;
        this.low = low;
        this.lowTime = lowTime;
        this.latestUpdate = latestUpdate;
        this.latestVolume = latestVolume;
        this.previousClose = previousClose;
        this.change = change;
        this.week52High = week52High;
        this.week52Low = week52Low;
        if (((int) latestPrice * 100) == 0) {
            this.price = 0.01f;
        } else {
            this.price = latestPrice;
        }
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getEnabled() {
        return enabled;
    }

    public void setEnabled(String enabled) {
        this.enabled = enabled;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        if (((int) price * 100) == 0) {
            this.price = 0.01f;
        } else {
            this.price = price;
        }
    }

    public int getAnzahl() {
        return anzahl;
    }

    public void setAnzahl(int anzahl) {
        this.anzahl = anzahl;
        this.menge = anzahl;
    }

    public Object getChange() {
        return change;
    }

    public void setChange(float change) {
        this.change = change;
    }

    public ArrayList<DataPoint> getChart() {
        return chart;
    }

    public void setChart(ArrayList<DataPoint> chart) {
        this.chart = chart;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getOpen() {
        return open;
    }

    public String getClose() {
        return close;
    }

    public String getHigh() {
        return high;
    }

    public String getHighTime() {
        return highTime;
    }

    public String getLow() {
        return low;
    }

    public String getLowTime() {
        return lowTime;
    }

    public long getLatestUpdate() {
        return latestUpdate;
    }

    public int getLatestVolume() {
        return latestVolume;
    }

    public float getPreviousClose() {
        return previousClose;
    }

    public float getWeek52High() {
        return week52High;
    }

    public float getWeek52Low() {
        return week52Low;
    }

    @Override
    public int compare(Object o1, Object o2) {
        Aktie t1, t2;
        t1 = (Aktie) o1;
        t2 = (Aktie) o2;
        if (t1.getSymbol().equals(t2.getSymbol())) {
            return 0;
        }
        return -1;
    }

    public Aktie getClone() {
        return new Aktie(this.menge, this.exchange, this.symbol, this.name, this.date, this.type, this.region, this.currency, this.enabled, this.price);
    }

    public JSONObject getJsonFromAktie() {
        org.json.simple.JSONObject obj = new org.json.simple.JSONObject();
        obj.put("menge", menge);
        obj.put("exchange", exchange);
        obj.put("symbol", symbol);
        obj.put("name", name);
        obj.put("date", date);
        obj.put("type", type);
        obj.put("region", region);
        obj.put("RequestCurrency", currency);
        obj.put("enabled", enabled);
        obj.put("preis", String.valueOf(price));
        obj.put("anzahl", String.valueOf(anzahl));
        obj.put("change", String.valueOf(change));

        return obj;
    }

    public boolean isCrypto() {
        if (type != null && type.equals("crypto")) {
            return true;
        } else {
            return false;
        }
    }

    /*
    private String openTime;
    private String closeTime;
    private float latestPrice;
    private String latestSource;
    private int previousVolume;
    private String primaryExchange;
    private int lastTradeTime;
    private boolean isUSMarketOpen;
    private String sector;
    private String calculationPrice;
    private float changePercent;
    private int avgTotalVolume;
    private String bidPrice = null;
    private String bidSize = null;
    private String askPrice = null;
    private String askSize = null;
    private boolean isEnabled = false;

    public String getPrimaryExchange() {
        return primaryExchange;
    }

    public void setPrimaryExchange(String primaryExchange) {
        this.primaryExchange = primaryExchange;
    }

    public String getCalculationPrice() {
        return calculationPrice;
    }

    public void setCalculationPrice(String calculationPrice) {
        this.calculationPrice = calculationPrice;
    }

    public String getOpenTime() {
        return openTime;
    }

    public void setOpenTime(String openTime) {
        this.openTime = openTime;
    }

    public String getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(String closeTime) {
        this.closeTime = closeTime;
    }

    public float getLatestPrice() {
        return latestPrice;
    }

    public void setLatestPrice(float latestPrice) {
        if (((int) latestPrice * 100) == 0) {
            this.latestPrice = 0.01f;
        } else {
            this.latestPrice = latestPrice;
        }
    }

    public String getLatestSource() {
        return latestSource;
    }

    public void setLatestSource(String latestSource) {
        this.latestSource = latestSource;
    }

    public int getPreviousVolume() {
        return previousVolume;
    }

    public void setPreviousVolume(int previousVolume) {
        this.previousVolume = previousVolume;
    }

    public float getChangePercent() {
        return changePercent;
    }

    public void setChangePercent(float changePercent) {
        this.changePercent = changePercent;
    }

    public int getAvgTotalVolume() {
        return avgTotalVolume;
    }

    public void setAvgTotalVolume(int avgTotalVolume) {
        this.avgTotalVolume = avgTotalVolume;
    }

    public int getLastTradeTime() {
        return lastTradeTime;
    }

    public void setLastTradeTime(int lastTradeTime) {
        this.lastTradeTime = lastTradeTime;
    }

    public boolean isUSMarketOpen() {
        return isUSMarketOpen;
    }

    public void setUSMarketOpen(boolean USMarketOpen) {
        isUSMarketOpen = USMarketOpen;
    }

    public void setClose(String close) {
        this.close = close;
    }

    public void setOpen(String open) {
        this.open = open;
    }

    public void setHigh(String high) {
        this.high = high;
    }

    public void setHighTime(String highTime) {
        this.highTime = highTime;
    }

    public void setLow(String low) {
        this.low = low;
    }

    public void setLowTime(String lowTime) {
        this.lowTime = lowTime;
    }

    public void setLatestUpdate(long latestUpdate) {
        this.latestUpdate = latestUpdate;
    }

    public void setLatestVolume(int latestVolume) {
        this.latestVolume = latestVolume;
    }

    public void setPreviousClose(float previousClose) {
        this.previousClose = previousClose;
    }

    public void setWeek52High(float week52High) {
        this.week52High = week52High;
    }

    public void setWeek52Low(float week52Low) {
        this.week52Low = week52Low;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public void setCryptoData(String sector, String bidPrice, String bidSize, String askPrice, String askSize, boolean isEnabled) {
        this.sector = sector;
        this.bidPrice = bidPrice;
        this.bidSize = bidSize;
        this.askPrice = askPrice;
        this.askSize = askSize;
        this.isEnabled = isEnabled;
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

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }
    */
}
