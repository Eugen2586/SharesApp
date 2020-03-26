package com.example.sharesapp.FunktionaleKlassen.JSON.ToModel;

import com.example.sharesapp.Model.FromServerClasses.Aktie;
import com.example.sharesapp.Model.Model;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;

public class RequestQuote {
    public RequestQuote(String s) {
        boolean b =false;
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
        Float latestPrice = null;
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
        JSONParser parse = new JSONParser();
        JSONObject jsonObject = null;
        try {
            jsonObject = (JSONObject) parse.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            symbol = (String) jsonObject.get("symbol");
        }catch(Exception e){
            e.printStackTrace();
        }try{
            company = (String) jsonObject.get("companyName");
        }catch(Exception e){
            e.printStackTrace();
        }try{
            primaryEx = (String) jsonObject.get("primaryExchange");
        }catch(Exception e){
            e.printStackTrace();
        }try{
            calcPrice = (String) jsonObject.get("calculationPrice");
        }catch(Exception e){
            e.printStackTrace();
        }try{
             open = (String) jsonObject.get("open");
        }catch(Exception e){
            e.printStackTrace();
        }try{
             opent = (String) jsonObject.get("openTime");
        }catch(Exception e){
            e.printStackTrace();
        }try{
              close = (String) jsonObject.get("close");
        }catch(Exception e){
            e.printStackTrace();
        }try{ 
            closet = (String) jsonObject.get("closeTime");
        }catch(Exception e){
            e.printStackTrace();
        }try{
            high = (String) jsonObject.get("high");
        }catch(Exception e){
            e.printStackTrace();
        }try{
            Object h = jsonObject.get("highTime");
            highT = h.toString();
        }catch(Exception e){
            e.printStackTrace();
        }try{
             low = (String) jsonObject.get("low");
        }catch(Exception e){
            e.printStackTrace();
        }try{
            lowT = (String) jsonObject.get("lowTime");
        }catch(Exception e){
            e.printStackTrace();
        }try{
            latestPrice = Float.parseFloat((String)jsonObject.get("latestPrice"));
        }catch(Exception e){
            e.printStackTrace();
        }try{
            latestS = (String) jsonObject.get("latestSource");
        }catch(Exception e){
            e.printStackTrace();
        }try{
            latestU = (String) jsonObject.get("latestUpdate");
        }catch(Exception e){
            e.printStackTrace();
        }try{
            Object h = jsonObject.get("latestVolume");
            latestVol = Integer.parseInt(h.toString());
        }catch(Exception e){
            e.printStackTrace();
        }try{
            Object h = jsonObject.get("previousClose");
            prevCl = Float.parseFloat(h.toString());
        }catch(Exception e){
            e.printStackTrace();
        }try{
            Object h = jsonObject.get("previousVolume");
            prevVol = Integer.parseInt(h.toString());
        }catch(Exception e){
            e.printStackTrace();
        }try{
            Object h = jsonObject.get("change");
            change = Float.parseFloat(h.toString());
        }catch(Exception e){
            e.printStackTrace();
        }try{
            Object h = jsonObject.get("changePercent");
            chpercent = Float.parseFloat(h.toString());
        }catch(Exception e){
            e.printStackTrace();
        }try{
            Object h = jsonObject.get("avgTotalVolume");
            avgVol = Integer.parseInt(h.toString());
        }catch(Exception e){
            e.printStackTrace();
        }try{
            Object h = jsonObject.get("week52High");
            week52High = Float.parseFloat(h.toString());
        }catch(Exception e){
            e.printStackTrace();
        }try{
            Object h = jsonObject.get("week52Low");
            week52Low = Float.parseFloat(h.toString());
        }catch(Exception e){
            e.printStackTrace();
        }try{
            Object h = jsonObject.get("lastTradeTime");
            lastTradeT = Integer.parseInt(h.toString());
        }catch(Exception e){
            e.printStackTrace();
        }try {
            Object h = jsonObject.get("isUSMarketOpen");
            b = Boolean.parseBoolean(h.toString());
        }catch(Exception e){
            e.printStackTrace();
        }
        try{
            price = Float.parseFloat((String)jsonObject.get("price"));
        }catch(Exception e){

        }
        //order to data sheet
        ArrayList<Aktie> arrl = new Model().getData().getAktienList().getValue();
        if (arrl != null) {
            for (Aktie c: arrl) {
                if (c.getSymbol().equals(jsonObject.get("symbol"))) {
                    c.setadditionalData(latestPrice, company, primaryEx, calcPrice, open, opent, close, closet, high, highT, low, lowT , latestPrice, latestS, latestU, latestVol, prevCl, prevVol, change, chpercent, avgVol, week52High, week52Low, lastTradeT, b  );
                    break;
                }
			}
		}
        new Model().getData().getAktienList().getValue().clear();
        new Model().getData().getAktienList().postValue(arrl);
    }
}
