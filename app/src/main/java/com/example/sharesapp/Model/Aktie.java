package com.example.sharesapp.Model;

import pl.zankowski.iextrading4j.api.stocks.Quote;

public class Aktie{
    //ToDo neue Variablen für die Aktie
    Quote quote;
    int menge;

    public Aktie( ){

    }
    public Aktie( Quote quote ){
       this.quote = quote;
    }
    //ToDo Handelsstuff


}
