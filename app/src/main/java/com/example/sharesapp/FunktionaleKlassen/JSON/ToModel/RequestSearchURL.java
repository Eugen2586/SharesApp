package com.example.sharesapp.FunktionaleKlassen.JSON.ToModel;

import com.example.sharesapp.Model.Aktie;
import com.example.sharesapp.Model.SearchedURLS;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;

public class RequestSearchURL {
    SearchedURLS url;
    ArrayList urls;

    /*
    [{"symbol":"AAPL","securityName":"pA .cnlpIe","securityType":"cs","region":"US","exchange":"ANS"},{"symbol":"APLE","securityName":"laIAtT Epy oHiil IscneppRt","securityType":"sc","region":"US","exchange":"YNS"},{"symbol":"APRU","securityName":"cymu.nRl phC pnI oea,sAp","securityType":"sc","region":"US","exchange":"CTO"},{"symbol":"AGPL","securityName":"lnno.rl, deigcA  pIepnHeG","securityType":"cs","region":"US","exchange":"TCO"},{"symbol":"APGN-ID","securityName":"n eelplepgrcpA","securityType":"sc","region":"IE","exchange":"DBU"},{"symbol":"APGN-LN","securityName":"Apgnpeplecelr ","securityType":"cs","region":"GB","exchange":"LON"},{"symbol":"AAPL-MM","securityName":"Ie nc.Alpp","securityType":"sc","region":"MX","exchange":"EXM"},{"symbol":"APC-GY","securityName":"lA.ncppe I","securityType":"sc","region":"DE","exchange":"TER"},{"symbol":"500014-IB","securityName":"i tFAipLiedc mneepanl","securityType":"sc","region":"IN","exchange":"OMB"},{"symbol":"511339-IB","securityName":"Loi.edpCp t el CdrtrAp.","securityType":"cs","region":"IN","exchange":"OMB"}]
    */
    public RequestSearchURL(String s) throws ParseException {
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
    public ArrayList getURLS(){
        return urls;
    }
}
