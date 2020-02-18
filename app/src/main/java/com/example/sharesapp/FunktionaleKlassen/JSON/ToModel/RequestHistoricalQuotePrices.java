package com.example.sharesapp.FunktionaleKlassen.JSON.ToModel;

import com.example.sharesapp.Model.FromServerClasses.SearchedURLS;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.util.ArrayList;

public class RequestHistoricalQuotePrices {

    ArrayList URLS = null;

   /* public RequestHistoricalQuotePrices(String s){
        urls = new ArrayList();
        JSONParser parser = new JSONParser();
        JSONArray jsonar = (JSONArray) parser.parse(s);
        //TODO pflege hier die Daten, die hier eingelesen werden.
        for (Object t : jsonar) {
            //ToDo zweite Datenebene
            url = new SearchedURLS();
            org.json.simple.JSONObject json = (JSONObject) t;
            url.setSymbol(json.get("symbol").toString());
            url.setSecurityName(json.get("securityName").toString());
            url.setSecurityType(json.get("securityType").toString());
            url.setRegion(json.get("region").toString());
            url.setExchange(json.get("exchange").toString());
            urls.add(url);
        }
    }
    
    */


    public ArrayList getURLS() {
        return URLS;
    }

    public void setURLS(ArrayList urls) {
        this.URLS = urls;
    }
}
