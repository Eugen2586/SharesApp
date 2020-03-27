package com.example.sharesapp.Model.FromServerClasses;

import androidx.lifecycle.MutableLiveData;

import com.example.sharesapp.Model.Constants;
import com.example.sharesapp.Model.Model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

public class Data {
    private MutableLiveData<ArrayList<Order>> sellOrderList = new MutableLiveData<>();
    private MutableLiveData<ArrayList<Trade>> tradesMutable = new MutableLiveData<>();
    private MutableLiveData<ArrayList<Order>> buyOrderList = new MutableLiveData<>();
    private MutableLiveData<ArrayList<Aktie>> portfolio = new MutableLiveData<>();
    public MutableLiveData<ArrayList<Aktie>> searches = new MutableLiveData<>();
    private MutableLiveData<ArrayList<Aktie>> aktien = new MutableLiveData<>();
    private MutableLiveData<Integer> resetCounter = new MutableLiveData<>();
    private MutableLiveData<Aktie> currentStock = new MutableLiveData<>();

    private ArrayList<Trade> tradelist = new ArrayList<>();
    private ArrayList<Integer> categoryScrollPositions = null;

    private Depot depot = null;
    private AvailType availType = null;
    private String currentSearchString;
    private int previouslySelectedOrderTabIndex = 0;
    private int previouslySelectedDepotTabIndex = 0;
    private int previouslySelectedTabIndex = 0;

    public Data() {

    }

    public ArrayList<Aktie> getSearches() {
        return searches.getValue();
    }

    public MutableLiveData<ArrayList<Aktie>> getMutableSearches() {
        return searches;
    }

    public AvailType getAvailType() {
        if (availType == null) {
            availType = new AvailType();
        }
        return availType;
    }

    public Depot getDepot() {
        if (depot == null) {
            depot = new Depot();
        }
        return depot;
    }

    public void setResetCounter(int resetCounter) {
        this.resetCounter.setValue(resetCounter);
    }

    public MutableLiveData<Aktie> getMutableCurrentStock() {
        return currentStock;
    }

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
        if( tradelist == null){
            tradelist = new ArrayList<>();
        }
        if(tradesMutable == null){
            tradesMutable  = new MutableLiveData<>();
        }
        tradelist.add(trade);
        tradesMutable.setValue(tradelist);
    }

    public void setTradelist(ArrayList<Trade> trades) {
        tradelist = trades;
        tradesMutable.setValue(tradelist);
    }

    public ArrayList<Trade> getTrades() {
        return tradesMutable.getValue();
    }

    public void addAktienList(ArrayList<Aktie> stockList) {
        // TODO: add Depot and Portfolio to stocklist on start of app and loading
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

    public void setPreviouslySelectedTabIndex(int tabIndex) {
        previouslySelectedTabIndex = tabIndex;
    }

    public int getPreviouslySelectedTabIndex() {
        return previouslySelectedTabIndex;
    }

    private void increaseResetValue() {
        /**
         * Erhöht ResetCounter um 1
         */
        if (resetCounter.getValue() == null) {
            resetCounter.setValue(1);
        } else {
            resetCounter.setValue(resetCounter.getValue() + 1);
        }
    }

    /**
     * @return resetCounter
     */
    public MutableLiveData<Integer> getResetCounter() {
        return resetCounter;
    }

    public ArrayList<Integer> getCategoryScrollPositions() {
        return categoryScrollPositions;
    }

    public void createCategoryScrollPositions(int tabCount) {
        categoryScrollPositions = new ArrayList<>();
        for (int i = 0; i < tabCount; i++) {
            categoryScrollPositions.add(0);
        }
    }

    public int getPreviouslySelectedOrderTabIndex() {
        return previouslySelectedOrderTabIndex;
    }

    public void setPreviouslySelectedOrderTabIndex(int previouslySelectedOrderTabIndex) {
        this.previouslySelectedOrderTabIndex = previouslySelectedOrderTabIndex;
    }

    public int getPreviouslySelectedDepotTabIndex() {
        return previouslySelectedDepotTabIndex;
    }

    public void setPreviouslySelectedDepotTabIndex(int previouslySelectedDepotTabIndex) {
        this.previouslySelectedDepotTabIndex = previouslySelectedDepotTabIndex;
    }

    public void addBuyOrder(Order buyOrder) {
        ArrayList<Order> orderList = buyOrderList.getValue();
        if (orderList == null) {
            orderList = new ArrayList<>();
        }
        orderList.add(buyOrder);
        buyOrderList.postValue(orderList);
    }

    public void removeBuyOrder(Order buyOrder) {
        ArrayList<Order> orderList = buyOrderList.getValue();
        if (orderList != null) {
            orderList.remove(buyOrder);
            buyOrderList.postValue(orderList);
        }
    }

    public void removeBuyOrderList(ArrayList<Order> removeOrderList) {
        ArrayList<Order> orderList = buyOrderList.getValue();
        if (orderList != null) {
            for (Order order: removeOrderList) {
                orderList.remove(order);
            }
            buyOrderList.postValue(orderList);
        }
    }

    public MutableLiveData<ArrayList<Order>> getBuyOrderList() {
        return buyOrderList;
    }

    public void addSellOrder(Order sellOrder) {
        ArrayList<Order> orderList = sellOrderList.getValue();
        if (orderList == null) {
            orderList = new ArrayList<>();
        }
        orderList.add(sellOrder);
        sellOrderList.postValue(orderList);
    }

    public void removeSellOrder(Order sellOrder) {
        ArrayList<Order> orderList = sellOrderList.getValue();
        if (orderList != null) {
            orderList.remove(sellOrder);
            sellOrderList.postValue(orderList);
        }
    }

    private void removeSellOrderList(ArrayList<Order> removeOrderList) {
        ArrayList<Order> orderList = sellOrderList.getValue();
        if (orderList != null && removeOrderList != null) {
            for (Order order: removeOrderList) {
                orderList.remove(order);
            }
            sellOrderList.postValue(orderList);
        }
    }

    public MutableLiveData<ArrayList<Order>> getSellOrderList() {
        return sellOrderList;
    }

    public void resetData() {
        tradelist = new ArrayList<>();
        tradesMutable = new MutableLiveData<>();
        int schwierigkeitsgrad = depot.getSchwierigkeitsgrad();
        int k = depot.getKaufCounter();
        int v = depot.getVerkaufCounter();
        depot = new Depot();
        depot.setKaufCounter(k);
        depot.setVerkaufCounter(v);
        portfolio = new MutableLiveData<>();
        previouslySelectedTabIndex = 0;
        depot.setGeldwert(Constants.MONEY);
        increaseResetValue();
        categoryScrollPositions = null;
        previouslySelectedDepotTabIndex = 0;
        previouslySelectedOrderTabIndex = 0;
        depot.setSchwierigkeitsgrad(schwierigkeitsgrad);
        depot.applySchwierigkeitsgrad(true);
    }

    private void sortStockList(ArrayList<Aktie> stockList) {
        Collections.sort(stockList, new Comparator<Aktie>() {
            @Override
            public int compare(Aktie stock1, Aktie stock2) {
                return stock1.getSymbol().compareTo(stock2.getSymbol());
            }
        });
    }

    public void checkOrderListsForBuyingSelling(ArrayList<Aktie> stockList) {
        Model model = new Model();
        System.out.println("...............................................................................Handle Stocklist Change");
        if (stockList != null) {
            // try to buy stock
            ArrayList<Order> buyOrderList = model.getData().getBuyOrderList().getValue();
            if (buyOrderList != null) {
                ArrayList<Order> buyOrderListToRemove = new ArrayList<>();
                for (Order buyOrder : buyOrderList) {
                    for (Aktie stock : stockList) {
                        if (buyOrderRequirement(stock, buyOrder)) {
                            // buy stock to currentPrice
                            Aktie stockClone = buyOrder.getStock().getClone();
                            stockClone.setAnzahl(buyOrder.getNumber());
                            stockClone.setCompanyName(buyOrder.getStock().getCompanyName());
                            model.getData().getDepot().kaufeAktie(stockClone);
                            buyOrderListToRemove.add(buyOrder);
                            System.out.println("...............................................................................Kaufe Aktie " + stockClone.getSymbol());
                            break;
                        }
                    }
                }
                model.getData().removeBuyOrderList(buyOrderListToRemove);
            }
            // try to sell stock
            ArrayList<Order> sellOrderList = model.getData().getSellOrderList().getValue();
            if (sellOrderList != null) {
                ArrayList<Order> sellOrderListToRemove = new ArrayList<>();
                for (Order sellOrder : sellOrderList) {
                    for (Aktie stock : stockList) {
                        if (sellOrderRequirement(stock, sellOrder)) {
                            // buy stock to currentPrice
                            Aktie stockClone = sellOrder.getStock().getClone();
                            stockClone.setAnzahl(sellOrder.getNumber());
                            model.getData().getDepot().verkaufeAktie(stockClone);
                            sellOrderListToRemove.add(sellOrder);
                            System.out.println("...............................................................................Verkaufe Aktie " + stockClone.getSymbol());
                            break;
                        }
                    }
                }
                model.getData().removeSellOrderList(sellOrderListToRemove);
            }
        }
    }

    private boolean buyOrderRequirement(Aktie stock, Order buyOrder) {
        return stock.getSymbol().equals(buyOrder.getSymbol()) && stock.getPrice() < buyOrder.getLimit();
    }

    private boolean sellOrderRequirement(Aktie stock, Order sellOrder) {
        return stock.getSymbol().equals(sellOrder.getSymbol()) && stock.getPrice() > sellOrder.getLimit();
    }

    public String findTypeOfSymbol(String symbol) {
        String type = "";
        ArrayList<Aktie> stockList = aktien.getValue();
        if (stockList != null) {
            for (Aktie stock : stockList) {
                if (stock.getSymbol().equals(symbol)) {
                    type = stock.getType();
                }
            }
        }
        return type;
    }

    public String findCompanyNameBySymbol(String symbol) {
        String company = "";
        ArrayList<Aktie> stockList = aktien.getValue();
        if (stockList != null) {
            for (Aktie stock : stockList) {
                if (stock.getSymbol().equals(symbol)) {
                    company = stock.getCompanyName();
                }
            }
        }
        return company;
    }

    /*
    private MutableLiveData forexList = new MutableLiveData();
    private MutableLiveData<ArrayList<DataPoint>> personalChart = new MutableLiveData<>();

        public ArrayList<Aktie> getSellOrderStockList() {
            ArrayList<Aktie> stockList = new ArrayList<>();
            if (sellOrderList.getValue() != null) {
                for (Order order : sellOrderList.getValue()) {
                    stockList.add(order.getStock());
                }
            }
            return stockList;
        }

    public ArrayList<Aktie> getBuyOrderStockList() {
        ArrayList<Aktie> stockList = new ArrayList<>();
        if (buyOrderList.getValue() != null) {
            for (Order order : buyOrderList.getValue()) {
                stockList.add(order.getStock());
            }
        }
        return stockList;
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

    public float getGewinn() {
        float sum = Float.parseFloat("0.0");
        if (tradelist != null) {
            for (Object e : tradelist) {
                Trade t = (Trade) e;
                if (t.isKauf()) {
                    sum -= t.getPreis();
                } else {
                    sum += t.getPreis();
                }
            }
        }
        sum += depot.getGeldwert();
        return sum;
    }

    public void setAvailType(AvailType availType) {
        this.availType = availType;
    }

    public void setSearches(ArrayList<Aktie> searches) {
        this.searches.setValue(searches);
    }
     */
}
