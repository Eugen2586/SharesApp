package com.example.sharesapp.FunktionaleKlassen.JSON.ToModel;

import com.example.sharesapp.Model.FromServerClasses.SearchRequest;
import com.example.sharesapp.Model.FromServerClasses.SearchedURLS;
import com.example.sharesapp.Model.Model;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;

public class RequestSearch {

    ArrayList urls = new ArrayList();

    public RequestSearch(String st) throws ParseException {
        urls = new ArrayList();
        JSONParser parser = new JSONParser();
        JSONArray jsonar = (JSONArray) parser.parse(st);
        //TODO pflege hier die Daten, die hier eingelesen werden.
        for (Object t : jsonar) {
            //ToDo zweite Datenebene
            org.json.simple.JSONObject json = (JSONObject) t;
            SearchRequest url = new SearchRequest(json.get("symbol").toString(),json.get("securityName").toString(), (String) json.get("securityType"),json.get("region").toString() , json.get("exchange").toString() );
            urls.add(url);
        }
        new Model().getData().setSearches(urls);

    }
}
