package com.example.sharesapp.Model;

import com.example.sharesapp.Model.FromServerClasses.Aktie;

import java.util.ArrayList;

public class Depot {
    ArrayList<Aktie> aktienImDepot;
    float geldwert;
    boolean in;

    public Depot(ArrayList<Aktie> aktienImDepot, float geldwert, boolean in) {
        this.aktienImDepot = aktienImDepot;
        this.geldwert = geldwert;
        this.in = in;
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
                        ak.setAnzahl( ak.getAnzahl() - a.getAnzahl() );
                        geldwert = geldwert + a.getAnzahl() * a.getPreis();
                        if(ak.getAnzahl() == 0){
                            aktienImDepot.remove(ak);
                        }
                }
            }
            if(in != true){
                aktienImDepot.add(a);
            }
            geldwert = geldwert - a.getPreis();
        }


    }



}
