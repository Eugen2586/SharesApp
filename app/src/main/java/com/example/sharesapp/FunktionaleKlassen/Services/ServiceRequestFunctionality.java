package com.example.sharesapp.FunktionaleKlassen.Services;

import com.example.sharesapp.Model.FromServerClasses.Order;
import com.example.sharesapp.Model.Model;

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
}
