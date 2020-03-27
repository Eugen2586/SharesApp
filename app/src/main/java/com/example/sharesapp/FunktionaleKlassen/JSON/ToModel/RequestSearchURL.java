package com.example.sharesapp.FunktionaleKlassen.JSON.ToModel;

import com.example.sharesapp.Model.FromServerClasses.SearchedURLS;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;
import java.util.Objects;

public class RequestSearchURL {
    private ArrayList<SearchedURLS> urls;

    public RequestSearchURL(String s) throws ParseException {
        urls = new ArrayList<>();
        JSONParser parser = new JSONParser();
        JSONArray jsonar = (JSONArray) parser.parse(s);
        //TODO pflege hier die Daten, die hier eingelesen werden.
        for (Object t : jsonar) {
            //ToDo zweite Datenebene
            SearchedURLS url = new SearchedURLS();
            org.json.simple.JSONObject json = (JSONObject) t;
            url.setSymbol(Objects.requireNonNull(json.get("symbol")).toString());
            /*
            url.setSecurityName(Objects.requireNonNull(json.get("securityName")).toString());
            url.setSecurityType(Objects.requireNonNull(json.get("securityType")).toString());
            url.setRegion(Objects.requireNonNull(json.get("region")).toString());
            url.setExchange(Objects.requireNonNull(json.get("exchange")).toString());
            */
            urls.add(url);
        }
    }

    public ArrayList getURLS() {
        return urls;
    }
}
