package com.example.sharesapp.Model;

import com.example.sharesapp.Model.FromServerClasses.Aktie;
import com.example.sharesapp.Model.FromServerClasses.Order;
import com.example.sharesapp.Model.FromServerClasses.ServiceData;
import com.example.sharesapp.Model.FromServerClasses.Trade;

import java.util.ArrayList;

public class ServiceModel {
    static ServiceData data;

    public void setData(float money, ArrayList<Aktie> stockList, ArrayList<Order> buyOrderList,
                        ArrayList<Order> sellOrderList, ArrayList<Aktie> depotList,
                        ArrayList<Trade> tradeList){
        data = new ServiceData(money, stockList, buyOrderList, sellOrderList, depotList, tradeList);
    }

    public ServiceData getData(){
        if (data == null){
            data = new ServiceData();
        }
        return data;
    }
}
