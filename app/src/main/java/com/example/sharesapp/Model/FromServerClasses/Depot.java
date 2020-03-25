package com.example.sharesapp.Model.FromServerClasses;

import androidx.lifecycle.MutableLiveData;

import com.example.sharesapp.Model.Constants;
import com.example.sharesapp.Model.Model;
import com.example.sharesapp.REST.Requests;
import com.example.sharesapp.REST.RequestsBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Set;

public class Depot {
    private MutableLiveData<ArrayList<Aktie>> aktienImDepot = new MutableLiveData<>();

    private float geldwert;
    private boolean in;
    private float prozent = 1.01f;
    private boolean schwierigkeitsgrad;

    public Depot(ArrayList<Aktie> aktienImDepot, float geldwert, boolean in) {
        this.aktienImDepot.postValue(aktienImDepot);
        this.geldwert = geldwert;
        this.in = in;
    }

    Depot() {
        this.aktienImDepot.postValue(new ArrayList<Aktie>());
    }


    public void kaufeAktie(Aktie a) {
        if (geldwert - a.getPreis() * a.getAnzahl() >= 0) {
            in = false;
            Model m = new Model();
            ArrayList<Aktie> stocks = aktienImDepot.getValue();

            if (stocks != null) {
                for (Aktie ak : stocks) {
                    if (ak.getSymbol().equals(a.getSymbol())) {
                        in = true;
                        geldwert = geldwert - a.getPreis() * a.getAnzahl() * prozent;
                        ak.setAnzahl(a.getAnzahl() + ak.getAnzahl());
                        Trade trade = new Trade(a, a.getAnzahl(), true, (a.getAnzahl() * a.getPreis()), GregorianCalendar.getInstance().getTime().toString());
                        m.getData().addTrade(trade);
                    }
                }

                if (!in) {
                    stocks.add(a);
                    geldwert = geldwert - a.getPreis() * a.getAnzahl() * prozent;
                    Trade trade = new Trade(a, a.getAnzahl(), true, (a.getAnzahl() * a.getPreis()), GregorianCalendar.getInstance().getTime().toString());
                    m.getData().addTrade(trade);
                }

                aktienImDepot.postValue(stocks);
            }
        }
    }

    public void verkaufeAktie(Aktie a) {
        in = false;
        ArrayList<Aktie> stocks = aktienImDepot.getValue();
        Aktie toRemove = null;

        if (stocks != null) {
            for (Aktie ak : stocks) {
                if (ak.getSymbol().equals(a.getSymbol())) {
                    in = true;
                    if (a.getSymbol().equals(ak.getSymbol()) && a.getAnzahl() <= ak.getAnzahl()) {
                        in = false;
                        ak.setAnzahl(ak.getAnzahl() - a.getAnzahl());
                        geldwert = geldwert + a.getAnzahl() * a.getPreis() * (2f - this.prozent);
                        if (ak.getAnzahl() == 0) {
                            toRemove = ak;
                        }
                    }
                }
            }

            if (!in) {
//                    stocks.add(a);
                Model m = new Model();
                Trade trade = new Trade(a, a.getAnzahl(), false, (a.getAnzahl() * a.getPreis()), GregorianCalendar.getInstance().getTime().toString());
                m.getData().addTrade(trade);
            }
            geldwert = geldwert - a.getPreis();
            aktienImDepot.postValue(stocks);

            stocks.remove(toRemove);
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

    public MutableLiveData<ArrayList<Aktie>> getAktienImDepot() {
        return aktienImDepot;
    }

    public void setAktienImDepot(ArrayList<Aktie> aktienImDepot) {
        this.aktienImDepot.postValue(aktienImDepot);
    }

    public MutableLiveData<ArrayList<Aktie>> getAktien() {
        return aktienImDepot;
    }

    public Aktie findStockbySymbol(String symbol) {
        Aktie stock = null;
        if (getAktienImDepot().getValue() != null) {
            for (Aktie s : getAktienImDepot().getValue()) {
                if (s.getSymbol().equals(symbol)) {
                    stock = s;
                }
            }
        }
        return stock;
    }

    public float calculateStockValue() {
        if (aktienImDepot == null || aktienImDepot.getValue() == null) {
            return 0.0f;
        } else {
            float sum = 0.0f;
            for (Aktie a : aktienImDepot.getValue()) {
                sum += a.getPreis() * a.getAnzahl();
            }
            return sum;
        }
    }

    public boolean getSchwierigkeitsgrad() {
        return this.schwierigkeitsgrad;
    }

    public void setSchwierigkeitsgrad() {
        this.schwierigkeitsgrad = true;
    }
}
