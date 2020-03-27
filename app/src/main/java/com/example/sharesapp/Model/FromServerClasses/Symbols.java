package com.example.sharesapp.Model.FromServerClasses;

import java.util.ArrayList;

/**
 * Klasse zum speichern der Symbole innerhalb der Aktienliste. Hier sind alle Aktien angezeigt, die es gibt.
 */

public class Symbols {
    //Chris
    //Diese Klasse ist gedacht dazu um die vom Server kommenden Mappingdaten von Firmen und Symbolen zu mappen.
    //
     private final String symbol;
    //private final String exchange;
    private final String name;
    //private final LocalDate date;
    private final String type = null;
    //private final String iexId;
    //private final String region;
    //private final String RequestCurrency;
    //private final Boolean isEnabled;
    //Daten in der ExchangeSymbol


    static ArrayList symbols = null;

    /**
     *Initialisiert die Klasse mit Name und Symbol
     */
    Symbols(String symbol, String name){
        this.symbol = symbol;
        this.name = name;
        if (symbols == null){
            symbols = new ArrayList<>();
        }
    }

    /**
     * Gibt das Symbol der Klasse zurück.
     * @return Symbol der Klasse
     */
    public ArrayList getSymbols(){
        return symbols;
    }

/*    public ExchangeSymbol getCompany(String symbol){
        ExchangeSymbol s = null;
        for (Object t: symbols ) {
            ExchangeSymbol v = (ExchangeSymbol) t;
            if (v.getSymbol().equals(symbol)){
                s = v;
            }
        }
        return s;
    }

    public ExchangeSymbol getSymbol(String company){
        ExchangeSymbol s = null;
        for (Object t: symbols ) {
            ExchangeSymbol v = (ExchangeSymbol) t;
            if (v.getName().equals(company)){
                s = v;
            }
        }
        return s;
    }
    */

    /**
     * Setzt das Symbol der Klasse.
     */
    public void setSymbols(ArrayList sybols){
        this.symbols = symbols;
    }

}
