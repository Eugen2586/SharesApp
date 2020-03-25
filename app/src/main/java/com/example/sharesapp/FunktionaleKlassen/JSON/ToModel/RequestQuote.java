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
            
        }try{
            company = (String) jsonObject.get("companyName");
        }catch(Exception e){

        }try{
            primaryEx = (String) jsonObject.get("primaryExchange");
        }catch(Exception e){

        }try{
            calcPrice = (String) jsonObject.get("calculationPrice");
        }catch(Exception e){

        }try{
             open = (String) jsonObject.get("open");
        }catch(Exception e){

        }try{
             opent = (String) jsonObject.get("openTime");
        }catch(Exception e){

        }try{
              close = (String) jsonObject.get("close");
        }catch(Exception e){

        }try{ 
            closet = (String) jsonObject.get("closeTime");
        }catch(Exception e){

        }try{
            high = (String) jsonObject.get("high");
        }catch(Exception e){

        }try{
             highT = (String) jsonObject.get("highTime");
        }catch(Exception e){

        }try{
             low = (String) jsonObject.get("low");
        }catch(Exception e){

        }try{
            lowT = (String) jsonObject.get("lowTime");
        }catch(Exception e){

        }try{
            latestPrice = Float.parseFloat((String)jsonObject.get("latestPrice"));
        }catch(Exception e){

        }try{
            latestS = (String) jsonObject.get("latestSource");
        }catch(Exception e){

        }try{
            latestU = (String) jsonObject.get("latestUpdate");
        }catch(Exception e){

        }try{
            latestVol = Integer.parseInt((String)jsonObject.get("latestVolume"));
        }catch(Exception e){

        }try{
            prevCl = Float.parseFloat((String)jsonObject.get("previousClose"));
        }catch(Exception e){

        }try{
            prevVol = Integer.parseInt((String)jsonObject.get(jsonObject.get("previousVolume")));
        }catch(Exception e){

        }try{
            change = Float.parseFloat((String)jsonObject.get("change"));
        }catch(Exception e){

        }try{
            chpercent = Float.parseFloat((String)jsonObject.get("changePercent"));
        }catch(Exception e){

        }try{
            avgVol = Integer.parseInt((String)jsonObject.get("avgTotalVolume"));
        }catch(Exception e){

        }try{
            week52High = Float.parseFloat((String)jsonObject.get("week52High"));
        }catch(Exception e){

        }try{
            week52Low = Float.parseFloat((String)jsonObject.get("week52Low"));
        }catch(Exception e){

        }try{
            lastTradeT = Integer.parseInt((String)jsonObject.get("lastTradeTime"));
        }catch(Exception e){

        }try {
            b = Boolean.parseBoolean((String) jsonObject.get("isUSMarketOpen"));
        }catch(Exception e){
            
        }
        //order to data sheet
        ArrayList arrl = new Model().getData().getAktienList().getValue();
        for (Object c: arrl) {
            Aktie aktie = (Aktie) c;
            if (aktie.getSymbol().equals(jsonObject.get("symbol"))) {
                Aktie ak = aktie;
                arrl.remove(aktie);
                ak.setadditionalData(company, primaryEx, calcPrice, open, opent, close, closet, high, highT, low, lowT, latestPrice, latestS, latestU, latestVol, prevCl, prevVol, change, chpercent, avgVol, week52High, week52Low, lastTradeT, b  );
                arrl.add(ak);
            }
        }
        new Model().getData().getAktienList().getValue().clear();
        new Model().getData().getAktienList().postValue(arrl);
    }
}
