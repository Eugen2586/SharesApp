package com.example.sharesapp.Model.FromServerClasses;

import com.example.sharesapp.Model.Model;

import java.util.ArrayList;
import java.util.GregorianCalendar;

public class Depot {
    ArrayList<Aktie> aktienImDepot;


    float geldwert;
    boolean in;
    float prozent = 1.01f;

    public Depot(ArrayList<Aktie> aktienImDepot, float geldwert, boolean in) {
        this.aktienImDepot = aktienImDepot;
        this.geldwert = geldwert;
        this.in = in;
    }

    public Depot() {
        this.aktienImDepot = new ArrayList<Aktie>();

    }

    public void kaufeAktie(Aktie a){
        System.out.println("kaufe");
        if(geldwert - a.getPreis()*a.getAnzahl() >= 0){
            in = false;
            Model m = new Model();
            if (!aktienImDepot.isEmpty()) {
                for (Object t : aktienImDepot) {
                    Aktie ak = (Aktie) t;
                    if (ak.getName().equals(a.getName())) {
                        in = true;
                        geldwert = geldwert - a.getPreis() * a.getAnzahl() * prozent;
                        ak.setAnzahl(a.getAnzahl() + ak.getAnzahl());
                        Trade trade = new Trade(a, a.getAnzahl(), true, (a.getAnzahl() * a.getPreis()), GregorianCalendar.getInstance().getTime());
                        m.getData().addTrade(trade);
                    }
                }
            }
            if(!in){
                aktienImDepot.add(a);
                geldwert = geldwert - a.getPreis() * a.getAnzahl() * prozent;
                Trade trade = new Trade(a, a.getAnzahl(), true,(a.getAnzahl()*a.getPreis()) , GregorianCalendar.getInstance().getTime());
                m.getData().addTrade(trade);
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
                m.getData().addTrade(trade);
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

    public float getProzent() {
        return this.prozent;
    }


    public ArrayList<Aktie> getAktien() {
        return aktienImDepot;
    }
}
