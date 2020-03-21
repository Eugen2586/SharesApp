package com.example.sharesapp.FunktionaleKlassen.JSON.ToModel;

import com.example.sharesapp.Model.FromServerClasses.Aktie;
import com.example.sharesapp.Model.Model;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;

public class RequestQuotePrices {


    public RequestQuotePrices(String s) throws ParseException {
        JSONParser parser = new JSONParser();
        JSONObject jsonar = (JSONObject) parser.parse(s);

        Model model = new Model();
        if (model.getData().getAktienList().getValue() != null) {
            model.getData().getAktienList().postValue(actualizeArrayList(jsonar, model.getData().getAktienList().getValue()));
        }
        if (model.getData().getDepot().getAktienImDepot().getValue() != null) {
            model.getData().getDepot().getAktienImDepot().postValue(actualizeArrayList(jsonar, model.getData().getDepot().getAktienImDepot().getValue()));
        }
    }

    private ArrayList<Aktie> actualizeArrayList(JSONObject jsonar, ArrayList<Aktie> list) {
        for (Aktie t : list) {
            if (jsonar.get("symbol").equals(t.getSymbol())) {
                System.out.println(t);
                t.setPreis(Float.parseFloat(String.valueOf((jsonar.get("latestPrice")))));
                if (jsonar.get("change") != null) {
                    t.setChange(Float.parseFloat(String.valueOf((jsonar.get("change")))));
                }
                break;
            }
        }
        return list;
    }
}
