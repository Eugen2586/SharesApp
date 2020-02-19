package com.example.sharesapp.Model.FromServerClasses;

public class DataPoint {


    private String date;
    private String open;
    private String close;
    private String high;
    private String low;
    private String volume;
    private String UVolume;
    private String uHigh;
    private String change;
    private String changePercent;
    private String label;
    private String changeOverTime;

    public void setDate(String date) {

        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setOpen(String open) {
        this.open = open;
    }

    public String getOpen() {
        return open;
    }

    public void setClose(String close) {
        this.close = close;
    }

    public String getClose() {
        return close;
    }

    public void setHigh(String high) {

        this.high = high;
    }

    public String getHigh() {
        return high;
    }

    public void setLow(String low) {
        this.low = low;
    }

    public String getLow() {
        return low;
    }

    public void setVolume(String volume) {

        this.volume = volume;
    }

    public String getVolume() {
        return volume;
    }

    public void setUVolume(String uVolume) {
        this.UVolume = uVolume;
    }

    public String getUVolume() {
        return UVolume;
    }

    public void setuHigh(String uHigh) {
        this.uHigh = uHigh;
    }

    public String getuHigh() {
        return uHigh;
    }

    public void setChange(String change) {
        this.change = change;
    }

    public String getChange() {
        return change;
    }

    public void setChangePercent(String changePercent) {
        this.changePercent = changePercent;
    }

    public String getChangePercent() {
        return changePercent;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public void setChangeOverTime(String changeOverTime) {
        this.changeOverTime = changeOverTime;
    }

    public String getChangeOverTime() {
        return changeOverTime;
    }
}
