package com.example.sharesapp.Model.FromServerClasses;

import com.example.sharesapp.FunktionaleKlassen.JSON.LoadFromJson;

import java.util.ArrayList;

public class Data {
    private ArrayList<Aktie> aktien;
    private ArrayList<Trade> tradelist;
    private Depot depot;
    private ArrayList<Aktie> favoriten;

    public Data(){
        //ToDo initialisation Stuff here!
        LoadFromJson j = new LoadFromJson();
        try {
        j.readJson();
        }
        catch(Exception e){
        }
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
            aktien.add(aktie);
        }

    }

    public void addAktienList(ArrayList<Aktie> ar) {
        aktien = ar;
    }

    public ArrayList getAktienList() {
        return aktien;
    }

    protected void finalize(){
        //ToDo do Persistenz
        try {
            // SaveToJSON stj = new SaveToJSON(aktien, );
        }catch(Exception e){

        }

    }
}
