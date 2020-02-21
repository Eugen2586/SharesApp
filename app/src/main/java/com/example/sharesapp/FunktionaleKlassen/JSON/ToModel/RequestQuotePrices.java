package com.example.sharesapp.FunktionaleKlassen.JSON.ToModel;

import com.example.sharesapp.Model.FromServerClasses.Aktie;
import com.example.sharesapp.Model.Model;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;

public class RequestQuotePrices {


    public RequestQuotePrices(String s) throws ParseException {
        ArrayList<Aktie> m = new Model().getData().getAktienList().getValue();
        JSONParser parser = new JSONParser();
        JSONObject jsonar = (JSONObject) parser.parse(s);
            for (Object t: m) {
                if(jsonar.get("symbol").equals(((Aktie)t).getName())){
                    ((Aktie)t).setPreis(Float.parseFloat((String.valueOf(jsonar.get("latestPrice")))));
                    ((Aktie)t).setChange(Float.parseFloat((String.valueOf(jsonar.get("change")))));
                    ((Aktie)t).setDate((String) jsonar.get("date"));
            }
        }
        new Model().getData().getAktienList().postValue(m);
    }
}
