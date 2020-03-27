package com.example.sharesapp.FunktionaleKlassen.JSON.ToModel;

import android.widget.ArrayAdapter;

import com.example.sharesapp.Model.FromServerClasses.Aktie;
import com.example.sharesapp.Model.FromServerClasses.DataPoint;
import com.example.sharesapp.Model.Model;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;
import java.util.Objects;

public class RequestHistoricalCryptoPrices {

    private ArrayList<DataPoint> dbs;
    private DataPoint db;

    public RequestHistoricalCryptoPrices(String s) throws ParseException {

        dbs = new ArrayList<>();
        JSONParser parser = new JSONParser();
        JSONArray jsonar = (JSONArray) parser.parse(s);
        JSONArray o = (JSONArray) jsonar.get(0);
        for (Object t : o) {
            db = new DataPoint();
            JSONObject json = (JSONObject) t;
            System.out.println(json.toString());
            db.setDate(Objects.requireNonNull(json.get("date")).toString());
            db.setSymbol(Objects.requireNonNull(json.get("symbol")).toString());
            db.setTimestamp(Objects.requireNonNull(json.get("timestamp")).toString());
            db.setRate(Objects.requireNonNull(json.get("rate")).toString());
            db.setCryptoFlag(true);
            dbs.add(db);
        }
        Model model = new Model();

        Aktie currentStock = model.getData().getCurrentStock();

        currentStock.setChart(dbs);

        model.getData().getMutableCurrentStock().postValue(currentStock);
    }

    public ArrayList getDbs() {
        return dbs;
    }

    public void setURLS(ArrayList dbs) {
        this.dbs = dbs;
    }
}
