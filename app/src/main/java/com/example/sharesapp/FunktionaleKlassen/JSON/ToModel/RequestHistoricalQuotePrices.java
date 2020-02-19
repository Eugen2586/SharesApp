package com.example.sharesapp.FunktionaleKlassen.JSON.ToModel;

import com.example.sharesapp.Model.FromServerClasses.Aktie;
import com.example.sharesapp.Model.FromServerClasses.DataPoint;
import com.example.sharesapp.Model.FromServerClasses.SearchedURLS;
import com.example.sharesapp.Model.Model;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;

public class RequestHistoricalQuotePrices {

    ArrayList dbs = null;
    DataPoint db;

    public RequestHistoricalQuotePrices(String s) throws ParseException {
        dbs = new ArrayList();
        JSONParser parser = new JSONParser();
        JSONArray jsonar = (JSONArray) parser.parse(s);
        //TODO pflege hier die Daten, die hier eingelesen werden.
        for (Object t : jsonar) {
            //ToDo zweite Datenebene
            db = new DataPoint();
            JSONObject json = (JSONObject) t;
            db.setDate(json.get("name").toString());
            db.setDate(json.get("date").toString());
            db.setOpen(json.get("open").toString());
            db.setClose(json.get("close").toString());
            db.setHigh(json.get("high").toString());
            db.setLow(json.get("low").toString());
            db.setVolume(json.get("volume").toString());
            db.setUVolume(json.get("uVolume").toString());
            db.setuHigh(json.get("uHigh").toString());
            db.setChange(json.get("change").toString());
            db.setChangePercent(json.get("changePercent").toString());
            db.setLabel(json.get("label").toString());
            db.setChangeOverTime(json.get("changeOverTime").toString());
            dbs.add(db);
        }
        Model m = new Model();
        for (Object f: m.getDaten().getAktienList().getValue()){
            Aktie g = (Aktie) f;
            if (dbs.size()> 0 && g.getName().equals(((Aktie)dbs.get(0)).getName()){
                g.setChart(dbs);
            }
        }
    }
    public ArrayList getDbs() {
        return dbs;
    }

    public void setURLS( ArrayList dbs) {
        this.dbs = dbs;
    }
}
