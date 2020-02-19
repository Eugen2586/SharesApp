package com.example.sharesapp.FunktionaleKlassen.JSON.ToModel;

import com.example.sharesapp.Model.FromServerClasses.Aktie;
import com.example.sharesapp.Model.Model;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.util.ArrayList;

public class RequestSymbol {

    Aktie ak = new Aktie();
    ArrayList<Aktie> akl = new ArrayList<>();
   public RequestSymbol(String st) throws Exception {
        JSONParser parser = new JSONParser();
        JSONArray jsonar = (JSONArray) parser.parse(st);
        //TODO pflege hier die Daten, die hier eingelesen werden.
        for (Object t : jsonar) {
            //ToDo hier wird die Zerlegung der Nachrichtenvorgenommen.
            ak = new Aktie();
            org.json.simple.JSONObject json = (JSONObject) t;
            ak.setSymbol(json.get("symbol").toString());
            ak.setExchange(json.get("exchange").toString());
            ak.setName(json.get("name").toString());
            ak.setDate(json.get("date").toString());
            ak.setType(json.get("type").toString());
            ak.setRegion(json.get("region").toString());
            ak.setCurrency(json.get("currency").toString());
            ak.setEnabled(json.get("isEnabled").toString());
            akl.add(ak);
        }
       Model m = new Model();
       m.getDaten().addAktienList(akl);
    }


    public ArrayList<Aktie> getAk() {
        Model m = new Model();
        m.getDaten().addAktienList(akl);
        return akl;
    }
}