package com.example.sharesapp.Model.FromServerClasses;

import com.example.sharesapp.Model.Constants;

import java.util.ArrayList;
import java.util.GregorianCalendar;

public class ServiceData {

    private float money = 0;
    private ArrayList<Aktie> stockList = new ArrayList<>();
    private ArrayList<Order> buyOrderList = new ArrayList<>();
    private ArrayList<Order> sellOrderList = new ArrayList<>();
    private ArrayList<Aktie> depotList = new ArrayList<>();
    private ArrayList<Trade> tradeList = new ArrayList<>();

    public ServiceData() {
    }

    public ServiceData(float money, ArrayList<Aktie> stockList, ArrayList<Order> buyOrderList,
                       ArrayList<Order> sellOrderList, ArrayList<Aktie> depotList,
                       ArrayList<Trade> tradeList) {
        this.money = money;
        setStockList(stockList);
        setBuyOrderList(buyOrderList);
        setSellOrderList(sellOrderList);
        setDepotList(depotList);
        setTradeList(tradeList);
    }

    public ArrayList<Aktie> getStockList() {
        return stockList;
    }

    public void setStockList(ArrayList<Aktie> stockList) {
        if (stockList == null) {
            this.stockList = new ArrayList<>();
        } else {
            this.stockList = stockList;
        }
    }

    public ArrayList<Order> getBuyOrderList() {
        return buyOrderList;
    }

    public void setBuyOrderList(ArrayList<Order> buyOrderList) {
        if (buyOrderList == null) {
            this.buyOrderList = new ArrayList<>();
        } else {
            this.buyOrderList = buyOrderList;
        }
    }

    public ArrayList<Order> getSellOrderList() {
        return sellOrderList;
    }

    public void setSellOrderList(ArrayList<Order> sellOrderList) {
        if (sellOrderList == null) {
            this.sellOrderList = new ArrayList<>();
        } else {
            this.sellOrderList = sellOrderList;
        }
    }

    public void setDepotList(ArrayList<Aktie> depotList) {
        if (depotList == null) {
            this.depotList = new ArrayList<>();
        } else {
            this.depotList = depotList;
        }
    }

    public void setTradeList(ArrayList<Trade> tradeList) {
        if (tradeList == null) {
            this.tradeList = new ArrayList<>();
        } else {
            this.tradeList = tradeList;
        }
    }

    public void buyStockOfOrder(Order buyOrder) {
        float totalValue = buyOrder.getNumber() * buyOrder.getLimit() * Constants.PROZENT;
        if (money - totalValue >= 0) {
            // change amount of money
            money -= totalValue;

            // remove buyOrder
            buyOrderList.remove(buyOrder);

            // add new stock to depotList
            addOrderToDepot(buyOrder);

            // add respective trade
            Trade trade = new Trade(buyOrder.getStock(), buyOrder.getNumber(), true, totalValue, GregorianCalendar.getInstance().getTime().toString());
            tradeList.add(trade);

            // remove stock from stockList if not needed anymore
            boolean stockNotNeeded = true;
            for (Order sellOrder : sellOrderList) {
                if (sellOrder.getSymbol().equals(buyOrder.getSymbol())) {
                    stockNotNeeded = false;
                }
            }
            removeStockFromStockList(stockNotNeeded, buyOrder.getSymbol());

            // save changes
            saveChangesToPersistence();
        }
    }

    private void addOrderToDepot(Order buyOrder) {
        boolean notFoundInDepot = true;
        for (Aktie depotStock : depotList) {
            if (depotStock.getSymbol().equals(buyOrder.getSymbol())) {
                depotStock.setAnzahl(depotStock.getAnzahl() + buyOrder.getNumber());
                notFoundInDepot = false;
                break;
            }
        }
        if (notFoundInDepot) {
            buyOrder.getStock().setAnzahl(buyOrder.getNumber());
            depotList.add(buyOrder.getStock());
        }
    }

    public void sellStockOfOrder(Order sellOrder) {
        float totalValue = sellOrder.getNumber() * sellOrder.getLimit() * Constants.V_PROZENT;
        for (Aktie depotStock : depotList) {
            if (depotStock.getSymbol().equals(sellOrder.getSymbol()) && depotStock.getAnzahl() >= sellOrder.getNumber()) {
                // change amount of money
                money += totalValue;

                // remove buyOrder
                sellOrderList.remove(sellOrder);

                // add new stock to depotList
                depotStock.setAnzahl(depotStock.getAnzahl() - sellOrder.getNumber());

                // add respective trade
                Trade trade = new Trade(sellOrder.getStock(), sellOrder.getNumber(), false, totalValue, GregorianCalendar.getInstance().getTime().toString());
                tradeList.add(trade);

                // remove stock from stockList if not needed anymore
                boolean stockNotNeeded = true;
                for (Order buyOrder : buyOrderList) {
                    if (sellOrder.getSymbol().equals(buyOrder.getSymbol())) {
                        stockNotNeeded = false;
                    }
                }
                removeStockFromStockList(stockNotNeeded, sellOrder.getSymbol());

                // save changes
                saveChangesToPersistence();

                break;
            }
        }
    }

    private void removeStockFromStockList(boolean stockNotNeeded, String symbol) {
        if (stockNotNeeded) {
            for (Aktie stock : stockList) {
                if (stock.getSymbol().equals(symbol)) {
                    stockList.remove(stock);
                    break;
                }
            }
        }
    }

    private void saveChangesToPersistence() {
        //ToDO persistenz
        // TODO: save money,  buyOrderList, sellOrderList, depotList, tradeList
    }
}
