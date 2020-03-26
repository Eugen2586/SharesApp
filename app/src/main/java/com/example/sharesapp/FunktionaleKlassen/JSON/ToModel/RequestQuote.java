package com.example.sharesapp.FunktionaleKlassen.JSON.ToModel;

import com.example.sharesapp.Model.FromServerClasses.Aktie;
import com.example.sharesapp.Model.Model;

import org.json.JSONException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;

public class RequestQuote {
    public RequestQuote(String s) {
        boolean b = false;
        String closet = null;
        String symbol;
        String company = null;
        String primaryEx = null;
        String calcPrice = null;
        String open = null;
        String opent = null;
        String close = null;
        String high = null;
        String highT = null;
        String low = null;
        String lowT = null;
        String latestS = null;
        float latestPrice = 0.0f;
        String latestU = null;
        float price = 0.0f;
        int latestVol = 0;
        float prevCl = 0;
        float chpercent = 0;
        int prevVol = 0;
        float change = 0;
        int lastTradeT = 0;
        float week52Low = 0;
        int avgVol = 0;
        float week52High = 0;

        String sector = null;
        String bidPrice = null;
        String bidSize = null;
        String askPrice = null;
        String askSize = null;
        boolean isEnabled = false;
        JSONParser parse = new JSONParser();
        JSONObject jsonObject = null;
        try {
            jsonObject = (JSONObject) parse.parse(s);
        } catch (ParseException e) {
        }
        try {
            symbol = (String) jsonObject.get("symbol");
        } catch (Exception e) {
        }
        try {
            company = (String) jsonObject.get("companyName");
        } catch (Exception e) {
        }
        try {
            primaryEx = (String) jsonObject.get("primaryExchange");
        } catch (Exception e) {
        }
        try {
            calcPrice = (String) jsonObject.get("calculationPrice");
        } catch (Exception e) {
        }
        try {
            open = (String) jsonObject.get("open");
        } catch (Exception e) {
        }
        try {
            opent = (String) jsonObject.get("openTime");
        } catch (Exception e) {
        }
        try {
            close = (String) jsonObject.get("close");
        } catch (Exception e) {
        }
        try {
            closet = (String) jsonObject.get("closeTime");
        } catch (Exception e) {
        }
        try {
            high = (String) jsonObject.get("high");
        } catch (Exception e) {
        }
        try {
            Object h = jsonObject.get("highTime");
            highT = h.toString();
        } catch (Exception e) {
        }
        try {
            low = (String) jsonObject.get("low");
        } catch (Exception e) {
        }
        try {
            lowT = (String) jsonObject.get("lowTime");
        } catch (Exception e) {
        }
        try {
            Object h = jsonObject.get("latestPrice");
            latestPrice = Float.parseFloat(h.toString());
        } catch (Exception e) {
        }
        try {
            latestS = (String) jsonObject.get("latestSource");
        } catch (Exception e) {
        }
        try {
            latestU = (String) jsonObject.get("latestUpdate");
        } catch (Exception e) {
        }
        try {
            Object h = jsonObject.get("latestVolume");
            latestVol = Integer.parseInt(h.toString());
        } catch (Exception e) {
            latestVol = -1;
        }
        try {
            Object h = jsonObject.get("previousClose");
            prevCl = Float.parseFloat(h.toString());
        } catch (Exception e) {
        }
        try {
            Object h = jsonObject.get("previousVolume");
            prevVol = Integer.parseInt(h.toString());
        } catch (Exception e) {
            prevVol = -1;
        }
        try {
            Object h = jsonObject.get("change");
            change = Float.parseFloat(h.toString());
        } catch (Exception e) {
        }
        try {
            Object h = jsonObject.get("changePercent");
            chpercent = Float.parseFloat(h.toString());
        } catch (Exception e) {
        }
        try {
            Object h = jsonObject.get("avgTotalVolume");
            avgVol = Integer.parseInt(h.toString());
        } catch (Exception e) {
            avgVol = -1;
        }
        try {
            Object h = jsonObject.get("week52High");
            week52High = Float.parseFloat(h.toString());
        } catch (Exception e) {
            week52High = -1;
        }
        try {
            Object h = jsonObject.get("week52Low");
            week52Low = Float.parseFloat(h.toString());
        } catch (Exception e) {
            week52Low = -1;
        }
        try {
            Object h = jsonObject.get("lastTradeTime");
            lastTradeT = Integer.parseInt(h.toString());
        } catch (Exception e) {
            lastTradeT = -1;
        }
        try {
            Object h = jsonObject.get("isUSMarketOpen");
            b = Boolean.parseBoolean(h.toString());
        } catch (Exception e) {
        }
        try {
            Object h = jsonObject.get("price");
            price = Float.parseFloat(h.toString());
        } catch (Exception e) {
        }
        try {
            sector = jsonObject.get("sector").toString();
        } catch (Exception e) {
        }
        try {
            bidPrice = jsonObject.get("bidPrice").toString();
        } catch (Exception e) {
        }
        try {
            bidSize = jsonObject.get("bidSize").toString();
        } catch (Exception e) {
        }
        try {
            askPrice = jsonObject.get("askPrice").toString();
        } catch (Exception e) {
        }
        try {
            askSize = jsonObject.get("askSize").toString();
        } catch (Exception e) {
        }
        try {
            isEnabled = Boolean.parseBoolean(jsonObject.get("IsEnabled").toString());
        } catch (Exception e) {

        }

        //order to data sheet
        ArrayList<Aktie> arrl = new Model().getData().getAktienList().getValue();
        int i = 0;
        for (Aktie c : arrl) {
            if (c.getSymbol().equals(jsonObject.get("symbol"))) {
                c.setadditionalData(price, latestPrice, company, primaryEx, calcPrice, open, opent, close, closet, high, highT, low, lowT, latestPrice, latestS, latestU, latestVol, prevCl, prevVol, change, chpercent, avgVol, week52High, week52Low, lastTradeT, b);
                c.setCryptoData(sector, bidPrice, bidSize, askPrice, askSize, isEnabled);
                if (new Model().getData().getCurrentStock() != null) {
                    new Model().getData().getCurrentStock().setadditionalData(price, latestPrice, company, primaryEx, calcPrice, open, opent, close, closet, high, highT, low, lowT, latestPrice, latestS, latestU, latestVol, prevCl, prevVol, change, chpercent, avgVol, week52High, week52Low, lastTradeT, b);
                    new Model().getData().getCurrentStock().setCryptoData(sector, bidPrice, bidSize, askPrice, askSize, isEnabled);
                }
                new Model().getData().getAktienList().postValue(arrl);
                break;
            }
        }
        ArrayList<Aktie> depotList = new Model().getData().getDepot().getAktienImDepot().getValue();
        for (Aktie c : depotList) {
            if (c.getSymbol().equals(jsonObject.get("symbol"))) {
                c.setadditionalData(price, latestPrice, company, primaryEx, calcPrice, open, opent, close, closet, high, highT, low, lowT, latestPrice, latestS, latestU, latestVol, prevCl, prevVol, change, chpercent, avgVol, week52High, week52Low, lastTradeT, b);
                new Model().getData().getDepot().getAktienImDepot().postValue(depotList);
                break;
            }
        }
    }
}
