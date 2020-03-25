package com.example.sharesapp.FunktionaleKlassen.Services;

import com.example.sharesapp.Model.FromServerClasses.Order;
import com.example.sharesapp.Model.Model;
import com.example.sharesapp.REST.Requests;
import com.example.sharesapp.REST.RequestsBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

class ServiceRequestFunctionality {
    static Set<String> getSymbolSet() {
        Model model = new Model();
        ArrayList<Order> buyOrderList = model.getData().getBuyOrderList().getValue();
        ArrayList<Order> sellOrderList = model.getData().getSellOrderList().getValue();
        Set<String> symbolSet = new HashSet<>();

        // get all symbols for requests
        if (buyOrderList != null) {
            for (Order buyOrder : buyOrderList) {
                symbolSet.add(buyOrder.getSymbol());
            }
        }
        if (sellOrderList != null) {
            for (Order sellOrder : sellOrderList) {
                symbolSet.add(sellOrder.getSymbol());
            }
        }

        return symbolSet;
    }

    static void asyncRequestsForStocks(Set<String> symbolSet) {
        Requests requests = new Requests();
        for (String symbol : symbolSet) {
            try {
                requests.asyncRun(RequestsBuilder.getQuote(symbol));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
