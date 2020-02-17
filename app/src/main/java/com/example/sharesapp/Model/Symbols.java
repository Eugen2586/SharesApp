package com.example.sharesapp.Model;

import java.time.LocalDate;
import java.util.ArrayList;



public class Symbols {
    //Chris
    //Diese Klasse ist gedacht dazu um die vom Server kommenden Mappingdaten von Firmen und Symbolen zu mappen.
    //
    //private final String symbol;
    //private final String exchange;
    //private final String name;
    //private final LocalDate date;
    //private final SymbolType type;
    //private final String iexId;
    //private final String region;
    //private final String currency;
    //private final Boolean isEnabled;
    //Daten in der ExchangeSymbol


    static ArrayList symbols = null;

    Symbols(){
        if (symbols == null){
            symbols = new ArrayList<>();
        }
    }

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


    public void setSymbols(ArrayList sybols){
        this.symbols = symbols;
    }

}
