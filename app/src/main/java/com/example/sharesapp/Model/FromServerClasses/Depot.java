package com.example.sharesapp.Model.FromServerClasses;

import com.example.sharesapp.Model.FromServerClasses.Aktie;
import com.example.sharesapp.Model.FromServerClasses.Trade;
import com.example.sharesapp.Model.Model;

import java.util.ArrayList;
import java.util.GregorianCalendar;

public class Depot {
    ArrayList<Aktie> aktienImDepot;


    float geldwert;
    boolean in;

    public Depot(ArrayList<Aktie> aktienImDepot, float geldwert, boolean in) {
        this.aktienImDepot = aktienImDepot;
        this.geldwert = geldwert;
        this.in = in;
    }

    public Depot() {

    }

    public void kaufeAktie(Aktie a){
        if(geldwert - a.getPreis() > 0){
        in = false;
        for (Object t: aktienImDepot) {
            Aktie ak = (Aktie) t;
            if(ak.getName().equals(a.getName())){
                in = true;
                ak.setAnzahl(a.getAnzahl() + ak.getAnzahl() );
            }
        }
        if(in != true){
            aktienImDepot.add(a);
            geldwert = geldwert - a.getPreis() * a.getAnzahl();
            Model m = new Model();
            Trade trade = new Trade(a, a.getAnzahl(), true,(a.getAnzahl()*a.getPreis()) , GregorianCalendar.getInstance().getTime());
            m.getDaten().addTrade(trade);
        }
        }
    }

    public void verkaufeAktie (Aktie a){
            in = false;
            for (Object t: aktienImDepot) {
                Aktie ak = (Aktie) t;
                if(ak.getName() == a.getName()){
                    in = true;
                    if(a.getName().equals(ak.getName()) && a.getAnzahl() <= ak.getAnzahl()){
                        in = true;
                        ak.setAnzahl( ak.getAnzahl() - a.getAnzahl() );
                        geldwert = geldwert + a.getAnzahl() * a.getPreis();
                        if(ak.getAnzahl() == 0){
                            aktienImDepot.remove(ak);
                        }
                }
            }
            if(in != true){
                aktienImDepot.add(a);
                Model m = new Model();
                Trade trade = new Trade(a, a.getAnzahl(), false,(a.getAnzahl()*a.getPreis()) , GregorianCalendar.getInstance().getTime());
                m.getDaten().addTrade(trade);
            }
            geldwert = geldwert - a.getPreis();
        }


    }

    public float getGeldwert() {
        return geldwert;
    }

    public void setGeldwert(float geldwert) {
        this.geldwert = geldwert;
    }


}
