package com.example.sharesapp.FunktionaleKlassen.JSON.ToModel;

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

    public RequestHistoricalCryptoPrices(String s) throws ParseException {

        dbs = new ArrayList<>();
        JSONParser parser = new JSONParser();
        JSONArray jsonar = (JSONArray) parser.parse(s);
        JSONArray o = (JSONArray) jsonar.get(0);
        for (Object t : o) {
            JSONObject json = (JSONObject) t;
            if (json != null) {
                DataPoint db = new DataPoint();
                try {
                    db.setDate(Objects.requireNonNull(json.get("date")).toString());
                } catch (Exception ignored) {
                }
                try {
                    db.setSymbol(Objects.requireNonNull(json.get("symbol")).toString());
                } catch (Exception ignored) {
                }
                try {
                    db.setRate(Objects.requireNonNull(json.get("rate")).toString());
                } catch (Exception ignored) {
                }
//                try {
//                    db.setTimestamp(Objects.requireNonNull(json.get("timestamp")).toString());
//                } catch (Exception ignored) {
//                }
                dbs.add(db);
            }
        }
        Model model = new Model();

        Aktie currentStock = model.getData().getCurrentStock();

        currentStock.setChart(dbs);

        model.getData().getMutableCurrentStock().postValue(currentStock);
    }
}
