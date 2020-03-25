package com.example.sharesapp.Model.FromServerClasses;

import androidx.lifecycle.MutableLiveData;

import com.example.sharesapp.FunktionaleKlassen.JSON.SaveToJSON;
import com.example.sharesapp.Model.Constants;
import com.example.sharesapp.Model.Model;
import com.google.android.material.tabs.TabLayout;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Set;

public class Data {
    private ArrayList<Trade> tradelist = new ArrayList<>();
    private MutableLiveData<ArrayList<Trade>> tradesMutable = new MutableLiveData<>();
    private Depot depot = null;
    private MutableLiveData<ArrayList<Aktie>> portfolio = new MutableLiveData<>();
    private AvailType availType = null;
    private MutableLiveData<ArrayList<Aktie>> aktien = new MutableLiveData<>();
    private String currentSearchString;
    private int previouslySelectedTabIndex = 0;
    private MutableLiveData<Integer> resetCounter = new MutableLiveData<>();
    private ArrayList<Integer> categoryScrollPositions = null;
    private int searchScrollPosition = 0;
    public MutableLiveData<Aktie> currentStock = new MutableLiveData<>();
    private MutableLiveData<ArrayList<Order>> buyOrderList = new MutableLiveData<>();
    private MutableLiveData<ArrayList<Order>> sellOrderList = new MutableLiveData<>();
    private int previouslySelectedOrderTabIndex = 0;
    private int previouslySelectedDepotTabIndex = 0;

    public MutableLiveData<ArrayList<Aktie>> searches = new MutableLiveData<>();

    public ArrayList<Aktie> getSearches() {
        return searches.getValue();
    }

    public void setSearches(ArrayList<Aktie> searches) {
        this.searches.setValue(searches);
    }


    public AvailType getAvailType() {
        if (availType == null) {
            availType = new AvailType();
        }
        return availType;
    }

    public void setAvailType(AvailType availType) {
        this.availType = availType;
    }


    public Data() {


    }

    public Depot getDepot() {
        if (depot == null) {
            depot = new Depot();
        }
        return depot;
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

    public void setPreviouslySelectedTabIndex(int tabIndex) {
        previouslySelectedTabIndex = tabIndex;
    }

    public int getPreviouslySelectedTabIndex() {
        return previouslySelectedTabIndex;
    }

    public void resetData() {
        tradelist = new ArrayList<>();
        tradesMutable = new MutableLiveData<>();
        depot = new Depot();
        portfolio = new MutableLiveData<>();
        previouslySelectedTabIndex = 0;
        depot.setGeldwert(Constants.MONEY);
        increaseResetValue();
        categoryScrollPositions = null;
        searchScrollPosition = 0;
        previouslySelectedDepotTabIndex = 0;
        previouslySelectedOrderTabIndex = 0;
        // TODO: aktualisiere Aktienübersicht, dass es normal angezeigt wird (tabs fehlen)
    }

    private void increaseResetValue() {
        if (resetCounter.getValue() == null) {
            resetCounter.setValue(1);
        } else {
            resetCounter.setValue(resetCounter.getValue() + 1);
        }
    }

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

    public int getSearchScrollPosition() {
        return searchScrollPosition;
    }

    public void setSearchScrollPosition(int searchScrollPosition) {
        this.searchScrollPosition = searchScrollPosition;
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

    public ArrayList<Aktie> getBuyOrderStockList() {
        ArrayList<Aktie> stockList = new ArrayList<>();
        if (buyOrderList.getValue() != null) {
            for (Order order: buyOrderList.getValue()) {
                stockList.add(order.getStock());
            }
        }
        return stockList;
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

    public void removeSellOrderList(ArrayList<Order> removeOrderList) {
        ArrayList<Order> orderList = sellOrderList.getValue();
        if (orderList != null && removeOrderList != null) {
            for (Order order: removeOrderList) {
                orderList.remove(order);
            }
            sellOrderList.postValue(orderList);
        }
    }

    public ArrayList<Aktie> getSellOrderStockList() {
        ArrayList<Aktie> stockList = new ArrayList<>();
        if (sellOrderList.getValue() != null) {
            for (Order order: sellOrderList.getValue()) {
                stockList.add(order.getStock());
            }
        }
        return stockList;
    }

    public MutableLiveData<ArrayList<Order>> getSellOrderList() {
        return sellOrderList;
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
        return stock.getSymbol().equals(buyOrder.getSymbol()) && stock.getPreis() < buyOrder.getLimit();
    }

    private boolean sellOrderRequirement(Aktie stock, Order sellOrder) {
        return stock.getSymbol().equals(sellOrder.getSymbol()) && stock.getPreis() > sellOrder.getLimit();
    }

//    public void buyStockOfOrder(Order buyOrder) {
//        float totalValue = buyOrder.getNumber() * buyOrder.getLimit() * Constants.PROZENT;
//        if (depot.getGeldwert() - totalValue >= 0) {
//            // change amount of money
//            depot.setGeldwert(depot.getGeldwert() - totalValue);
//
//            // remove buyOrder
//            removeBuyOrder(buyOrder);
//
//            // add new stock to depotList
//            addOrderToDepot(buyOrder);
//
//            // add respective trade
//            ArrayList<Trade> tradeList = tradesMutable.getValue();
//            Trade trade = new Trade(buyOrder.getStock(), buyOrder.getNumber(), true, totalValue, GregorianCalendar.getInstance().getTime().toString());
//            if (tradeList == null) {
//                tradeList = new ArrayList<>();
//            }
//            tradeList.add(trade);
//
//            // save changes
//
//        }
//    }
//
//    private void addOrderToDepot(Order buyOrder) {
//        boolean notFoundInDepot = true;
//        ArrayList<Aktie> depotList = depot.getAktienImDepot().getValue();
//        if (depotList != null) {
//            for (Aktie depotStock : depotList) {
//                if (depotStock.getSymbol().equals(buyOrder.getSymbol())) {
//                    depotStock.setAnzahl(depotStock.getAnzahl() + buyOrder.getNumber());
//                    notFoundInDepot = false;
//                    break;
//                }
//            }
//        } else {
//            depotList = new ArrayList<>();
//        }
//        if (notFoundInDepot) {
//            buyOrder.getStock().setAnzahl(buyOrder.getNumber());
//            depotList.add(buyOrder.getStock());
//        }
//        depot.setAktienImDepot(depotList);
//    }
//
//    public void sellStockOfOrder(Order sellOrder) {
//        float totalValue = sellOrder.getNumber() * sellOrder.getLimit() * Constants.V_PROZENT;
//        ArrayList<Aktie> depotList = depot.getAktienImDepot().getValue();
//        if (depotList != null) {
//            for (Aktie depotStock : depotList) {
//                if (depotStock.getSymbol().equals(sellOrder.getSymbol()) && depotStock.getAnzahl() >= sellOrder.getNumber()) {
//                    // change amount of money
//                    depot.setGeldwert(depot.getGeldwert() + totalValue);
//
//                    // remove buyOrder
//                    removeSellOrder(sellOrder);
//
//                    // add new stock to depotList
//                    depotStock.setAnzahl(depotStock.getAnzahl() - sellOrder.getNumber());
//                    if (depotStock.getAnzahl() == 0) {
//                        depotList.remove(depotStock);
//                    }
//                    depot.setAktienImDepot(depotList);
//
//                    // add respective trade
//                    ArrayList<Trade> tradeList = tradesMutable.getValue();
//                    Trade trade = new Trade(sellOrder.getStock(), sellOrder.getNumber(), false, totalValue, GregorianCalendar.getInstance().getTime().toString());
//                    if (tradeList == null) {
//                        tradeList = new ArrayList<>();
//                    }
//                    tradeList.add(trade);
//
//                    // save changes
//
//                    break;
//                }
//            }
//        }
//    }
}
