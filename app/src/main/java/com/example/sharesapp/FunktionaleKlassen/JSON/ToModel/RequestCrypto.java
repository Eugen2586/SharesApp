package com.example.sharesapp.FunktionaleKlassen.JSON.ToModel;

import com.example.sharesapp.Model.FromServerClasses.Crypto;
import com.example.sharesapp.Model.Model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;

public class RequestCrypto {
    //deklaration

    public RequestCrypto(String s) throws JSONException {
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
        ArrayList<Crypto> cryptoList = new ArrayList<>();
        JSONParser parser = new JSONParser();
        if (s != null && !s.isEmpty()) {
            org.json.JSONArray jsonay;
            try {
                jsonay = (org.json.JSONArray) parser.parse(s);
            } catch (Exception e) {
                jsonay = new JSONArray(s);
            }
            ArrayList jsonar = new ArrayList();
            for (int i = 0; i < jsonay.length(); i++) {
                jsonar.add(jsonay.get(i));
            }
            for (Object objob : jsonar) {
                Crypto crypto = new Crypto();
                org.json.JSONObject obj = null;
                try {
                    obj = (org.json.JSONObject) objob;
                }catch(Exception e){
                }
                try {
                    crypto.setSymbol(obj.get("symbol").toString());
                } catch (JSONException e) {
                }
                try {
                    crypto.setSector(obj.get("sector").toString());
                } catch (JSONException e) {
                }
                try {
                    crypto.setCalculationPrice(obj.get("calculationPrice").toString());
                } catch (JSONException e) {
                }
                try {
                    crypto.setLatestPrice(obj.get("latestPrice").toString());
                } catch (JSONException e) {
                }
                try {
                    crypto.setLatestSource(obj.get("latestSource").toString());
                } catch (JSONException e) {
                }
                try {
                    crypto.setLatestUpdate(Long.parseLong(obj.get("latestUpdate").toString()));
                } catch (JSONException e) {
                }
                try {
                    crypto.setLatestVolume(obj.get("latestVolume").toString());
                } catch (JSONException e) {
                }
                try {
                    crypto.setBidPrice(obj.get("bidPrice").toString());
                } catch (JSONException e) {
                }
                try {
                    crypto.setBidSize(obj.get("bidSize").toString());
                } catch (JSONException e) {
                }
                try {
                    crypto.setAskPrice(obj.get("askPrice").toString());
                } catch (JSONException e) {
                }
                try {
                    crypto.setAskSize(obj.get("askSize").toString());
                } catch (JSONException e) {
                }
                try {
                    crypto.setHigh(obj.get("high").toString());
                } catch (JSONException e) {
                }
                try {
                    crypto.setLow(obj.get("low").toString());
                } catch (JSONException e) {
                }
                try {
                    crypto.setPreviousClose(obj.get("previousClose").toString());
                } catch (JSONException e) {
                }
                cryptoList.add(crypto);
            }
            new Model().getData().getMutableCryptoList().postValue(cryptoList);
        }
    }
}