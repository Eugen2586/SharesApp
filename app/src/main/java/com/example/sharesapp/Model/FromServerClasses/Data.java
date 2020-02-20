package com.example.sharesapp.Model.FromServerClasses;

import androidx.lifecycle.MutableLiveData;

import com.example.sharesapp.FunktionaleKlassen.JSON.LoadFromJson;
import com.example.sharesapp.FunktionaleKlassen.JSON.SaveToJSON;

import java.util.ArrayList;

public class Data {
    private ArrayList<Trade> tradelist;
    private Depot depot;
    private ArrayList<Aktie> favoriten;
    private AvailType availType;
    private ArrayList<SearchRequest> searches;

    public ArrayList<SearchRequest> getSearches() {
        return searches;
    }

    public void setSearches(ArrayList<SearchRequest> searches) {
        this.searches = searches;
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

    public void setDepot(Depot depot) {
        this.depot = depot;
    }

    private MutableLiveData<ArrayList<Aktie>> aktien = new MutableLiveData<>();

    public void addTrade(Trade trade){
        tradelist.add(trade);
    }

    public void setTradelist(ArrayList<Trade> trades){
        tradelist = trades;
    }
    public ArrayList<Trade> getTrades(){
        return tradelist;
    }

    public float getGewinn(){
        float sum = 0;
        for (Object e: tradelist) {
            Trade t = (Trade) e;
            if(t.isKauf()) {
                sum -= t.getPreis();
            }
            else{
                sum += t.getPreis();
            }
        }
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

    @Override
    public void finalize(){
        //ToDo do Persistenz
        try {
            SaveToJSON stj = new SaveToJSON();
        } catch(Exception e){
            e.printStackTrace();
        }

    }
}
