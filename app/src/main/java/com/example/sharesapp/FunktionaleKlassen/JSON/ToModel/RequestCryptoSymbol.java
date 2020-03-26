package com.example.sharesapp.FunktionaleKlassen.JSON.ToModel;

import com.example.sharesapp.Model.FromServerClasses.Aktie;
import com.example.sharesapp.Model.FromServerClasses.Crypto;
import com.example.sharesapp.Model.Model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;

import java.util.ArrayList;

public class RequestCryptoSymbol {
    //deklaration

    public RequestCryptoSymbol(String s) throws JSONException {
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
////        JSONParser parse = new JSONParser();
////        JSONArray ar = new JSONArray();
////        try {
////            ar = (JSONArray) parse.parse(s);
////        } catch (ParseException e) {
////            e.printStackTrace();
////        }
        ArrayList<Aktie> cryptoList = new ArrayList<>();
        JSONParser parser = new JSONParser();
        if (s != null && !s.isEmpty()) {
            JSONArray jsonay;
            try {
                jsonay = (JSONArray) parser.parse(s);
            } catch (Exception e) {
                jsonay = new JSONArray(s);
            }
            ArrayList jsonar = new ArrayList();
            for (int i = 0; i < jsonay.length(); i++) {
                jsonar.add(jsonay.get(i));
            }
            for (Object objob : jsonar) {
                Aktie crypto = new Aktie();
                JSONObject obj = null;
                try {
                    obj = (JSONObject) objob;
                }catch(Exception e){
                    e.printStackTrace();
                }
                try {
                    crypto.setSymbol(obj.get("symbol").toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    crypto.setName(obj.get("name").toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    crypto.setExchange(obj.get("exchange").toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    crypto.setDate(obj.get("date").toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    crypto.setType(obj.get("type").toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    crypto.setRegion(obj.get("region").toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    crypto.setCurrency(obj.get("currency").toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    crypto.setEnabled(Boolean.parseBoolean(obj.get("isEnabled").toString()));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                cryptoList.add(crypto);
            }

            Model model = new Model();
            ArrayList<Aktie> stockList = model.getData().getAktienList().getValue();
            if (stockList != null && stockList.size() != 0) {
                stockList.addAll(cryptoList);
                model.getData().getAktienList().setValue(stockList);
            } else {
                model.getData().getAktienList().setValue(cryptoList);
            }
        }
    }
}
