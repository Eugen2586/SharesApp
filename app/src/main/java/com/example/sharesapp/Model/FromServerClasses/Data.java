package com.example.sharesapp.Model.FromServerClasses;


import androidx.lifecycle.MutableLiveData;

import com.example.sharesapp.FunktionaleKlassen.JSON.LoadFromJson;
import com.example.sharesapp.FunktionaleKlassen.JSON.SaveToJSON;
import com.example.sharesapp.Model.Depot;

import java.util.ArrayList;

public class Data {
    private ArrayList<Trade> tradelist;
    private ArrayList<Depot> depot;

    public Data(){
        //ToDo initialisation Stuff here!
        LoadFromJson j = new LoadFromJson();
        try {
        j.readJson();
        }
        catch(Exception e){
        }
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

    protected void finalize(){
        //ToDo do Persistenz
        try {
            //SaveToJSON stj = new SaveToJSON(aktien.getValue(), );
        } catch(Exception e){
            e.printStackTrace();
        }

    }
}
