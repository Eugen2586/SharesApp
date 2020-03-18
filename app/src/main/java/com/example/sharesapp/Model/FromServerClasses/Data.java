package com.example.sharesapp.Model.FromServerClasses;

import androidx.lifecycle.MutableLiveData;

import com.example.sharesapp.FunktionaleKlassen.JSON.SaveToJSON;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

public class Data {
    private ArrayList<Trade> tradelist = new ArrayList<Trade>();
    private MutableLiveData<ArrayList<Trade>> tradesMutable = new MutableLiveData<>();
    private Depot depot;
    private MutableLiveData<ArrayList<Aktie>> portfolio = new MutableLiveData<>();
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

    public void addAktienList(ArrayList<Aktie> stockList) {
        Set<Aktie> stockSet;
        if (aktien.getValue() == null) {
            stockSet = new HashSet<>();
        } else {
            stockSet = new HashSet<>(aktien.getValue());
        }
        stockSet.addAll(stockList);
        ArrayList<Aktie> newStockList = new ArrayList<>(stockSet);
        sortStockList(newStockList);
        aktien.setValue(newStockList);
    }

    public MutableLiveData<ArrayList<Aktie>> getAktienList() {
        return aktien;
    }

    public MutableLiveData<ArrayList<Trade>> getTradesMutable() {
        return tradesMutable;
    }

    public MutableLiveData<ArrayList<Aktie>> getPortfolioList() {
        return portfolio;
    }

    public void setPortfolioList(ArrayList<Aktie> portfolioList) {
        portfolio.setValue(portfolioList);
    }

    public void addToPortfolio(Aktie stock, String symbol) {
        ArrayList<Aktie> portfolioList = portfolio.getValue();
        if (portfolioList != null) {
            for (Aktie portfolioStock : portfolioList) {
                if (portfolioStock.getSymbol().equals(symbol)) {
                    // already in portfolio
                    return;
                }
            }
        } else {
            portfolioList = new ArrayList<>();
        }
        portfolioList.add(stock);
        sortStockList(portfolioList);
        portfolio.setValue(portfolioList);
    }

    public void removeFromPortfolio(String symbol) {
        ArrayList<Aktie> portfolioList = portfolio.getValue();
        if (portfolioList != null) {
            Aktie stockToRemove = null;
            for (Aktie portfolioStock : portfolioList) {
                if (portfolioStock.getSymbol().equals(symbol)) {
                    stockToRemove = portfolioStock;
                    break;
                }
            }
            if (stockToRemove != null) {
                portfolioList.remove(stockToRemove);
            }
        }
        portfolio.setValue(portfolioList);
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

    private void sortStockList(ArrayList<Aktie> stockList) {
        Collections.sort(stockList, new Comparator<Aktie>() {
            @Override
            public int compare(Aktie stock1, Aktie stock2) {
                return stock1.getSymbol().compareTo(stock2.getSymbol());
            }
        });
    }

}
