package com.example.sharesapp.Model.FromServerClasses;

import androidx.lifecycle.MutableLiveData;

import com.example.sharesapp.FunktionaleKlassen.JSON.SaveToJSON;

import java.util.ArrayList;

public class Data {
    private ArrayList<Trade> tradelist = new ArrayList<Trade>();
    private MutableLiveData<ArrayList<Trade>> tradesMutable = new MutableLiveData<>();
    private Depot depot;
    private ArrayList<Aktie> portfolioList = new ArrayList<>();
    private AvailType availType;
    private MutableLiveData<ArrayList<Aktie>> aktien = new MutableLiveData<>();
    private String currentSearchString;

    public MutableLiveData<ArrayList<Aktie>> searches = new MutableLiveData<>();

    public ArrayList<Aktie> getSearches() {
        return searches.getValue();
    }

    public void setSearches(ArrayList<Aktie> searches) {
        this.searches.setValue(searches);
    }


    public AvailType getAvailType() {
        if (availType == null){
            availType = new AvailType();
        }
        return availType;
    }

    public void setAvailType(AvailType availType) {
        this.availType = availType;
    }



    public Data(){


    }
    public Depot getDepot() {
        if (depot == null) {
            depot = new Depot();
        }
        return depot;

    }

    public MutableLiveData<Aktie> currentStock = new MutableLiveData<>();

    public Aktie getCurrentStock() {
        return currentStock.getValue();
    }

    public void setCurrentStock(Aktie currentStock) {
        this.currentStock.setValue(currentStock);
    }

    public void setDepot(Depot depot) {
        this.depot = depot;
    }



    public void addTrade(Trade trade){
        tradelist.add(trade);
        tradesMutable.setValue(tradelist);
    }

    public void setTradelist(ArrayList<Trade> trades){
        tradelist = trades;
        tradesMutable.setValue(tradelist);
    }

    public ArrayList<Trade> getTrades(){
        return tradelist;
    }

    public float getGewinn(){
        float sum = Float.parseFloat("0.0");
        if(tradelist != null) {
            for (Object e : tradelist) {
                Trade t = (Trade) e;
                if (t.isKauf()) {
                    sum -= t.getPreis();
                } else {
                    sum += t.getPreis();
                }
            }
        }
        sum += depot.geldwert;
        return sum;
    }


    public void addAktie(Aktie aktie) {
        if (aktien == null) {
            ArrayList<Aktie> a = null;
            aktien.postValue(a);
            a.add(aktie);
            aktien.setValue(a);
        }

    }

    public void addAktienList(ArrayList<Aktie> ar) {
        aktien.setValue(ar);
    }

    public MutableLiveData<ArrayList<Aktie>> getAktienList() {
        return aktien;
    }

    public MutableLiveData<ArrayList<Trade>> getTradesMutable() {
        return tradesMutable;
    }

    @Override
    public void finalize(){
        //ToDo do Persistenz
        try {
            SaveToJSON stj = new SaveToJSON();
        } catch(Exception e){
            e.printStackTrace();
        }

    }

    public ArrayList<Aktie> getPortfolioList() {
        return portfolioList;
    }

    public void setPortfolioList(ArrayList<Aktie> portfolioList) {
        this.portfolioList = portfolioList;
    }

    public void addToOrRemoveFromPortfolio(Aktie stock) {
        if (portfolioList.contains(stock)) {
            portfolioList.remove(stock);
        } else {
            portfolioList.add(stock);
        }
    }

    public String getCurrentSearchString() {
        return currentSearchString;
    }

    public void setCurrentSearchString(String currentSearchString) {
        this.currentSearchString = currentSearchString;
    }

    public Aktie findStockbySymbol(String symbol) {
        Aktie stock = null;
        for (Aktie s : getAktienList().getValue()) {
            if (s.getSymbol().equals(symbol)) {
                stock = s;
            }
        }
        return stock;
    }
}
