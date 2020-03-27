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

public class RequestHistoricalQuotePrices {

    public RequestHistoricalQuotePrices(String s) throws ParseException {

        ArrayList<DataPoint> dbs = new ArrayList<>();
        JSONParser parser = new JSONParser();
        JSONArray jsonar = (JSONArray) parser.parse(s);
        for (Object t : jsonar) {
            DataPoint db = new DataPoint();
            JSONObject json = (JSONObject) t;
            System.out.println(json.toString());
            db.setDate(Objects.requireNonNull(json.get("date")).toString());
            db.setHigh(Double.parseDouble(String.valueOf(json.get("high"))));
            db.setLow(Double.parseDouble(String.valueOf(json.get("low"))));
            db.setChange(Objects.requireNonNull(json.get("change")).toString());
//            db.setVolume(Objects.requireNonNull(json.get("volume")).toString());
//            db.setOpen(Objects.requireNonNull(json.get("open")).toString());
//            db.setClose(Objects.requireNonNull(json.get("close")).toString());
//            db.setUVolume(Objects.requireNonNull(json.get("uVolume")).toString());
//            db.setuHigh(Objects.requireNonNull(json.get("uHigh")).toString());
//            db.setChangePercent(Objects.requireNonNull(json.get("changePercent")).toString());
//            db.setLabel(Objects.requireNonNull(json.get("label")).toString());
//            db.setChangeOverTime(Objects.requireNonNull(json.get("changeOverTime")).toString());
            dbs.add(db);
        }
        Model m = new Model();
        Aktie currentStock = m.getData().getCurrentStock();

        currentStock.setChart(dbs);

        m.getData().getMutableCurrentStock().postValue(currentStock);
    }
}
