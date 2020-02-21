package com.example.sharesapp.Model.FromServerClasses;

import androidx.lifecycle.MutableLiveData;

import com.example.sharesapp.Model.Model;

import java.util.ArrayList;
import java.util.GregorianCalendar;

public class Depot {
    public MutableLiveData<ArrayList<Aktie>> aktienImDepot = new MutableLiveData<>();

    float geldwert;
    boolean in;
    float prozent = 1.01f;

    public Depot(ArrayList<Aktie> aktienImDepot, float geldwert, boolean in) {
        this.aktienImDepot.postValue(aktienImDepot);
        this.geldwert = geldwert;
        this.in = in;
    }

    public Depot() {
        this.aktienImDepot.postValue(new ArrayList<Aktie>());
    }

    public void kaufeAktie(Aktie a) {
        System.out.println("kaufe");
        if (geldwert - a.getPreis() * a.getAnzahl() >= 0) {
            in = false;
            Model m = new Model();
            ArrayList<Aktie> stocks = aktienImDepot.getValue();

            for (Object t : stocks) {
                Aktie ak = (Aktie) t;
                if (ak.getName().equals(a.getName())) {
                    in = true;
                    geldwert = geldwert - a.getPreis() * a.getAnzahl() * prozent;
                    ak.setAnzahl(a.getAnzahl() + ak.getAnzahl());
                    Trade trade = new Trade(a, a.getAnzahl(), true, (a.getAnzahl() * a.getPreis()), GregorianCalendar.getInstance().getTime());
                    m.getData().addTrade(trade);
                }
            }

            if (!in) {
                stocks.add(a);
                geldwert = geldwert - a.getPreis() * a.getAnzahl() * prozent;
                Trade trade = new Trade(a, a.getAnzahl(), true, (a.getAnzahl() * a.getPreis()), GregorianCalendar.getInstance().getTime());
                m.getData().addTrade(trade);
            }
            aktienImDepot.postValue(stocks);
        }
    }

    public void verkaufeAktie(Aktie a) {
        in = false;
        ArrayList<Aktie> stocks = aktienImDepot.getValue();
        for (Object t : stocks) {
            Aktie ak = (Aktie) t;
            if (ak.getName().equals(a.getName())) {
                in = true;
                if (a.getName().equals(ak.getName()) && a.getAnzahl() <= ak.getAnzahl()) {
                    in = true;
                    ak.setAnzahl(ak.getAnzahl() - a.getAnzahl());
                    geldwert = geldwert + a.getAnzahl() * a.getPreis();
                    if (ak.getAnzahl() == 0) {
                        stocks.remove(ak);
                    }
                }
            }
            if (!in) {
                stocks.add(a);
                Model m = new Model();
                Trade trade = new Trade(a, a.getAnzahl(), false, (a.getAnzahl() * a.getPreis()), GregorianCalendar.getInstance().getTime());
                m.getData().addTrade(trade);
            }
            geldwert = geldwert - a.getPreis();
            aktienImDepot.postValue(stocks);
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

    public ArrayList<Aktie> getAktienImDepot() {
        return aktienImDepot.getValue();
    }

    public void setAktienImDepot(ArrayList<Aktie> aktienImDepot) {
        this.aktienImDepot.setValue(aktienImDepot);
    }

    public ArrayList<Aktie> getAktien() {
        return aktienImDepot;
	}
    public Aktie findStockbySymbol(String symbol) {
        Aktie stock = null;
        for (Aktie s : getAktienImDepot()) {
            if (s.getSymbol().equals(symbol)) {
                stock = s;
            }
        }
        return stock;

    }
}
