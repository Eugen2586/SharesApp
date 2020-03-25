package com.example.sharesapp.FunktionaleKlassen.JSON.ToServiceModel;

import com.example.sharesapp.DrawerActivity;
import com.example.sharesapp.Model.FromServerClasses.Aktie;
import com.example.sharesapp.Model.FromServerClasses.Order;
import com.example.sharesapp.Model.ServiceModel;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;
import java.util.Objects;

public class ServiceRequestQuotePrices {

    public ServiceRequestQuotePrices(String s) throws ParseException {
        JSONParser parser = new JSONParser();
        JSONObject jsonar = (JSONObject) parser.parse(s);

        ServiceModel serviceModel = new ServiceModel();
        ArrayList<Aktie> stockList = serviceModel.getData().getStockList();
        for (Aktie stock : stockList) {
            if (Objects.equals(jsonar.get("symbol"), stock.getSymbol())) {
                stock.setPreis(Float.parseFloat(String.valueOf((jsonar.get("latestPrice")))));

                checkChangesWithOrderLists(stock);
                break;
            }
        }
    }

    private void checkChangesWithOrderLists(Aktie changedStock) {
        ServiceModel serviceModel = new ServiceModel();
        // try to buy stock
        ArrayList<Order> buyOrderList = serviceModel.getData().getBuyOrderList();
        for (Order buyOrder : buyOrderList) {
            if (DrawerActivity.buyOrderRequirement(changedStock, buyOrder)) {
                // buy stock to limit of order
                serviceModel.getData().buyStockOfOrder(buyOrder);
                break;
            }
        }
        // try to sell stock
        ArrayList<Order> sellOrderList = serviceModel.getData().getSellOrderList();
        for (Order sellOrder : sellOrderList) {
            if (DrawerActivity.sellOrderRequirement(changedStock, sellOrder)) {
                // buy stock to limit of order
                serviceModel.getData().sellStockOfOrder(sellOrder);
                break;
            }
        }
    }
}
