package com.example.sharesapp.FunktionaleKlassen.JSON.ToModel;

import com.example.sharesapp.Model.FromServerClasses.Aktie;
import com.example.sharesapp.Model.Model;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;

public class RequestCurrency {
    //deklaration

    public RequestCurrency(String s){
           // {
           //     "symbol": "BTCUSDT",
           //     "sector": "cryptocurrency",
           //     "calculationPrice": "realtime",
           //     "latestPrice": "10689.54000000",
           //     "latestSource": "Real time price",
           //     "latestUpdate": 1566249085120,
           //     "latestVolume": null,
           //     "bidPrice": "10691.17000000",
           //     "bidSize": "0.02080400",
           //     "askPrice": "10693.94000000",
           //     "askSize": "0.09739300",
           //     "high": null,
           //     "low": null,
           //     "previousClose": null
           // }
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

                    ak.setSymbol(obj.get("sector").toString());

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {

                    ak.setSymbol(obj.get("calculationPrice").toString());

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {

                    ak.setSymbol(obj.get("latestPrice").toString());

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {

                    ak.setSymbol(obj.get("latestSource").toString());

                } catch (JSONException e){

                }
                try {

                    ak.setSymbol(obj.get("latestUpdate").toString());

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {

                    ak.setSymbol(obj.get("latestVolume").toString());

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {

                    ak.setSymbol(obj.get("bidPrice").toString());

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {

                    ak.setSymbol(obj.get("bidSize").toString());

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {

                    ak.setSymbol(obj.get("askPrice").toString());

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {

                    ak.setSymbol(obj.get("askSize").toString());

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {

                    ak.setSymbol(obj.get("high").toString());

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {

                    ak.setSymbol(obj.get("low").toString());

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {

                    ak.setSymbol(obj.get("previousClose").toString());

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                arl.add(ak);
            }
            new Model().getData().setForex(arl);
    }

}
