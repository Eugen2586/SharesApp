package com.example.sharesapp.Model.FromServerClasses;

import androidx.lifecycle.MutableLiveData;

import com.example.sharesapp.Model.Model;

import java.util.ArrayList;
import java.util.GregorianCalendar;

public class Depot {
    private MutableLiveData<ArrayList<Aktie>> aktienImDepot = new MutableLiveData<>();

    private float geldwert;
    private boolean in;
    private float prozent = 1.01f;
    private int schwierigkeitsgrad;
    private float startMoney = 0;
    private int kaufCounter;
    private int verkaufCounter;

    Depot() {
        this.aktienImDepot.postValue(new ArrayList<Aktie>());
    }

    public void kaufeAktie(Aktie a) {
        if (geldwert - a.getPrice() * a.getAnzahl() >= 0) {
            in = false;
            Model m = new Model();
            ArrayList<Aktie> stocks = aktienImDepot.getValue();

            if (stocks != null) {
                for (Aktie ak : stocks) {
                    if (ak.getSymbol().equals(a.getSymbol())) {
                        in = true;
                        geldwert = geldwert - a.getPrice() * a.getAnzahl() * prozent;
                        ak.setAnzahl(a.getAnzahl() + ak.getAnzahl());
                        Trade trade = new Trade(a, a.getAnzahl(), true, (a.getAnzahl() * a.getPrice() * prozent), GregorianCalendar.getInstance().getTime().getTime(), a.getSymbol());
                        m.getData().addTrade(trade);
                        this.increaseKaufCounter();
                    }
                }

                if (!in) {
                    stocks.add(a);
                    geldwert = geldwert - a.getPrice() * a.getAnzahl() * prozent;
                    Trade trade = new Trade(a, a.getAnzahl(), true, (a.getAnzahl() * a.getPrice() * prozent), GregorianCalendar.getInstance().getTime().getTime(), a.getSymbol());
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
                        geldwert = geldwert + a.getAnzahl() * a.getPrice() * (2f - this.prozent);
                        this.increaseVerkaufCounter();
                        if (ak.getAnzahl() == 0) {
                            toRemove = ak;
                        }
                    }
                }
            }

            if (!in) {
                Model m = new Model();
                Trade trade = new Trade(a, a.getAnzahl(), false, (a.getAnzahl() * a.getPrice() * (2f - this.prozent)), GregorianCalendar.getInstance().getTime().getTime(), a.getSymbol());
                m.getData().addTrade(trade);
            }
            geldwert = geldwert - a.getPrice();
            aktienImDepot.postValue(stocks);

            stocks.remove(toRemove);
        }
    }

    public float getGeldwert() {
        return geldwert;
    }

    public void setGeldwert(float geldwert) {
        this.startMoney = geldwert;
        this.geldwert = geldwert;
    }

    private void setProzent(float q) {
        /** Setzt das Prozent auf q
         * @params float q
         */
        this.prozent = q;
    }

    public float getProzent() {
        /**
         * @return prozent
         */
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

    public float calculateStockValue() {
        if (aktienImDepot == null || aktienImDepot.getValue() == null) {
            return 0.0f;
        } else {
            float sum = 0.0f;
            for (Aktie a : aktienImDepot.getValue()) {
                sum += a.getPrice() * a.getAnzahl();
            }
            return sum;
        }
    }

    private void increaseKaufCounter() {
        /**
         * erhöht Kaufcounter um 1
         */
        this.kaufCounter += 1;
    }

    public int getKaufCounter() {
        /**
         * @return kaufCounter
         */
        return this.kaufCounter;
    }

    public void setKaufCounter(int x) {
        /**
         * Setzt kaufCounter auf x
         * @param int x
         */
        this.kaufCounter = x;
    }

    private void increaseVerkaufCounter() {
        /**
         * erhöht Verkaufcounter um 1
         */
        this.verkaufCounter += 1;
    }

    public int getVerkaufCounter() {
        /**
         * @return verkaufCounter
         */
        return this.verkaufCounter;
    }

    public void setVerkaufCounter(int x) {
        /**
         * Setzt verkaufCounter auf x
         * @param int x
         */
        this.verkaufCounter = x;
    }

    public String[] getSchwierigkeitsgrad(int i) {
        /**
        Diese Methode gibt textuelle Beschreibung für Schwierigkeitsgraden
        @param int i
        @return wenn i == 0 gibt die Methode die Beschreibung für aktuelles Schwierigkeitsgrad zurück,
        wenn i != 0 gibt die Methode die Beschreibung für ein ausgewähltes Schwierigkeitsgrad zurück
         */
        if (i == 0) {
            i = this.schwierigkeitsgrad;
        }
        String[] s = new String[2];
        switch(i) {
            case 1:
                s[0] = "Einfach";
                s[1] = "150000€ auf dem Konto und 0,035% Gebühren für ein Kauf und Verkauf. Ziemlich einfach, oder? ";
                return s;
            case 2:
                s[0] = "Normal";
                s[1] = "50000€ auf dem Konto und 0,125% Gebühren für ein Kauf und Verkauf. Hier hat man einen sicheren Start.";
                return s;
            case 3:
                s[0] = "Schwer";
                s[1] = "50000€ auf dem Konto und 0,5% Gebühren für ein Kauf und Verkauf. Was erlauben sich diese Banken?!";
                return s;
            case 4:
                s[0] = "Herausforderung";
                s[1] = "1000€ auf dem Konto und 0,5% Gebühren für ein Kauf und ein Verkauf. Kann man damit reich werden?";
                return s;
            default:
                return s;
        }
    }

    public int getSchwierigkeitsgrad() {
        /**
        @return Schwierigkeitsgrad oder unbestimmtes Schwierigkeitsgrad
         */
        if (this.schwierigkeitsgrad != 1 && this.schwierigkeitsgrad != 2 && this.schwierigkeitsgrad != 3 && this.schwierigkeitsgrad != 4 && this.schwierigkeitsgrad != -1) {
            return 0;
        }
        return this.schwierigkeitsgrad;
    }

    public boolean applySchwierigkeitsgrad(boolean neu) {
        /**
        Diese Methode wendet das Schwierigkeitsgrad an. Wenn das Spiel neu gestartet wird (neu == true)
        wird das Geldwert auch angepasst. Wenn das Spiel aber geladen wird, dann wird nur Prozent angepasst.
        @params boolean neu
         */
        // wenn das Spiel geladen wird, muss das Geldwert nicht neu gesetzt werden, sonst wird es
        //überschrieben sein. Deswegen wird das Geldwert nur dann neu gesetzt, wenn das Spiel
        //neu gestartet wird.
        switch(this.schwierigkeitsgrad) {
            case 1:
                if (neu)
                    {setGeldwert(150000f);}
                setProzent(1.00035f);
                return true;
            case 2:
                if (neu)
                    {setGeldwert(50000f);}
                setProzent(1.00125f);
                return true;
            case 3:
                if (neu)
                    {setGeldwert(50000f);}
                setProzent(1.005f);
                return true;
            case 4:
                if (neu)
                    {setGeldwert(1000f);}
                setProzent(1.05f);
                return true;
        }
        return false;
    }

    public void setSchwierigkeitsgrad(int i) {
        /**
         * Setzt Schwierigkeitsgrad
         * @param int i
         *            1 - einfach
         *            2 - normal
         *            3 - schwierig
         *            4 - herausforderung
         */
        this.schwierigkeitsgrad = i;
    }

    public float getStartMoney() {
        return startMoney;
    }

    /*
    public Depot(ArrayList<Aktie> aktienImDepot, float geldwert, boolean in) {
        this.aktienImDepot.postValue(aktienImDepot);
        this.geldwert = geldwert;
        this.in = in;
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
    */
}
