package com.example.sharesapp.FunktionaleKlassen.JSON.ToModel;

import com.example.sharesapp.Model.FromServerClasses.Aktie;
import com.example.sharesapp.Model.Model;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;
import java.util.Objects;

public class RequestQuote {
    public RequestQuote(String s) {
        String company = null;
        String open = null;
        String close = null;
        String high = null;
        String highT = null;
        String low = null;
        String lowT = null;
        float latestPrice = 0.0f;
        long latestUpdate = 0;
        int latestVol;
        float prevCl = 0;
        float change = 0;
        float week52Low;
        float week52High;
/*
        float price = 0.0f;
        String latestU = null;
        float chpercent = 0;
        String latestS = null;
        String opent = null;
        String primaryEx = null;
        String calcPrice = null;
        boolean b = false;
        String closet = null;
        String symbol;
        int prevVol = 0;
        int lastTradeT = 0;
        int avgVol = 0;
        String sector = null;
        String bidPrice = null;
        String bidSize = null;
        String askPrice = null;
        String askSize = null;
        boolean isEnabled = false;
        */
        JSONParser parse = new JSONParser();
        JSONObject jsonObject = null;
        try {
            jsonObject = (JSONObject) parse.parse(s);
        } catch (ParseException ignored) {
        }
        try {
            company = (String) jsonObject.get("companyName");
        } catch (Exception ignored) {
        }
        try {
            open = (String) jsonObject.get("open");
        } catch (Exception ignored) {
        }
        try {
            close = (String) jsonObject.get("close");
        } catch (Exception ignored) {
        }
        try {
            high = (String) jsonObject.get("high");
        } catch (Exception ignored) {
        }
        try {
            Object h = jsonObject.get("highTime");
            assert h != null;
            highT = h.toString();
        } catch (Exception ignored) {
        }
        try {
            low = (String) jsonObject.get("low");
        } catch (Exception ignored) {
        }
        try {
            lowT = (String) jsonObject.get("lowTime");
        } catch (Exception ignored) {
        }
        try {
            Object h = jsonObject.get("latestPrice");
            assert h != null;
            latestPrice = Float.parseFloat(h.toString());
        } catch (Exception ignored) {
        }
        try {
            latestUpdate = Long.parseLong(Objects.requireNonNull(jsonObject.get("latestUpdate")).toString());
        } catch (Exception ignored) {
        }
        try {
            Object h = jsonObject.get("latestVolume");
            latestVol = Integer.parseInt(h.toString());
        } catch (Exception e) {
            latestVol = -1;
        }
        try {
            Object h = jsonObject.get("previousClose");
            assert h != null;
            prevCl = Float.parseFloat(h.toString());
        } catch (Exception ignored) {
        }
        try {
            Object h = jsonObject.get("change");
            assert h != null;
            change = Float.parseFloat(h.toString());
        } catch (Exception ignored) {
        }
        try {
            Object h = jsonObject.get("week52High");
            assert h != null;
            week52High = Float.parseFloat(h.toString());
        } catch (Exception e) {
            week52High = -1;
        }
        try {
            Object h = jsonObject.get("week52Low");
            assert h != null;
            week52Low = Float.parseFloat(h.toString());
        } catch (Exception e) {
            week52Low = -1;
        }
        /*
        try {
            latestS = (String) jsonObject.get("latestSource");
        } catch (Exception e) {
        }
        try {
            Object h = jsonObject.get("changePercent");
            chpercent = Float.parseFloat(h.toString());
        } catch (Exception e) {
        }
        try {
            Object h = jsonObject.get("previousVolume");
            prevVol = Integer.parseInt(h.toString());
        } catch (Exception e) {
            prevVol = -1;
        }
        try {
            Object h = jsonObject.get("avgTotalVolume");
            avgVol = Integer.parseInt(h.toString());
        } catch (Exception e) {
            avgVol = -1;
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
            symbol = (String) jsonObject.get("symbol");
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
        try {
            primaryEx = (String) jsonObject.get("primaryExchange");
        } catch (Exception e) {
        }
        try {
            calcPrice = (String) jsonObject.get("calculationPrice");
        } catch (Exception e) {
        }
        try {
            opent = (String) jsonObject.get("openTime");
        } catch (Exception e) {
        }
        try {
            latestS = (String) jsonObject.get("latestSource");
        } catch (Exception e) {
        }
        */

        //order to data sheet
        ArrayList<Aktie> stockList = new Model().getData().getAktienList().getValue();
        if (stockList != null) {
            for (Aktie c : stockList) {
                if (c.getSymbol().equals(jsonObject.get("symbol"))) {
                    c.setAdditionalData(company, open, close, high, highT,
                            low, lowT, latestPrice, latestUpdate, latestVol, prevCl, change,
                            week52High, week52Low);

                    new Model().getData().getAktienList().postValue(stockList);
                    break;
                }
            }
        }

        ArrayList<Aktie> depotList = new Model().getData().getDepot().getAktienImDepot().getValue();
        if (depotList != null) {
            for (Aktie c : depotList) {
                if (c.getSymbol().equals(jsonObject.get("symbol"))) {
                    c.setAdditionalData(company, open, close, high, highT,
                            low, lowT, latestPrice, latestUpdate, latestVol, prevCl, change,
                            week52High, week52Low);

                    new Model().getData().getDepot().getAktienImDepot().postValue(depotList);
                    break;
                }
            }
        }
    }
}
