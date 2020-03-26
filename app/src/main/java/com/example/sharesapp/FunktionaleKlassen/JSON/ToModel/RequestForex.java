package com.example.sharesapp.FunktionaleKlassen.JSON.ToModel;

import com.example.sharesapp.Model.FromServerClasses.Aktie;
import com.example.sharesapp.Model.Model;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;

public class RequestForex{
    //deklaration

    public RequestForex(String s){
        // Response from Server!
        //{
        //    "symbol": "USDCAD",
        //        "rate": 1.31,
        //        "timestamp":  1288282222000
        //},
        ArrayList arl = new ArrayList();
        JSONParser parse = new JSONParser();
        JSONArray ar = new JSONArray();
        try {
            ar = (JSONArray) parse.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        for (Object objob: ar ){
            JSONObject obj = (JSONObject) objob;
            Aktie ak = new Aktie();
            try {

                obj = (JSONObject) parse.parse(s);

            } catch (ParseException e) {
                e.printStackTrace();
            }
            try {

                ak.setSymbol(obj.get("symbol").toString());

            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                Object h = obj.get("rate");

                ak.setPreis(Float.parseFloat(h.toString()));

            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {

                ak.setDate(obj.get("timestamp").toString());

            } catch (JSONException e) {
                e.printStackTrace();
            }
            arl.add(ak);
        }
        new Model().getData().setForex(arl);
    }

}
