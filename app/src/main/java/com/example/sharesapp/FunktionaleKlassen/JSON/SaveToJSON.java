package com.example.sharesapp.FunktionaleKlassen.JSON;

import android.content.SharedPreferences;

import com.example.sharesapp.Model.FromServerClasses.Aktie;
import com.example.sharesapp.Model.FromServerClasses.Data;
import com.example.sharesapp.Model.FromServerClasses.Trade;
import com.example.sharesapp.Model.Model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

    public class SaveToJSON {

        private Model model = new Model();

        public SaveToJSON(SharedPreferences.Editor editor){
            String s = null;
            //Here it should all Data from the Model get putted!

            //Trades
            try {
                s = tradesToString();
                editor.putString("Tr", s );
                editor.commit();
                System.out.println(s);
            }catch(Exception e) {
                System.out.print(e.getMessage());
            }
            s = null;
            //Depot Inhalt
            s = aktienImDepotToString();
            try {
                editor.putString("Depot", s);
                editor.commit();

            }catch(Exception e) {

            }
            s = null;
            Float f = 5000.0f;
            try{
                f = new Model().getData().getDepot().getGeldwert();
                editor.putFloat("Geldwert",f);
            }catch(Exception e){
                System.out.println(f);
            }


            //Portfolio Liste
            s = portfolioListeToString();
            try {
                editor.putString("Portfolioliste", s);
                editor.commit();
            }catch(Exception e) {
                s = null;
            }

            //Aktien
            s = aktienSymbolListToString();
            try {
                editor.putString("AktienSymbole", s);
                editor.commit();
            }catch(Exception e){
                
            }
            s = null;

            //This make it in the OS.
            try {
                editor.apply();
            }catch (Exception e){
                System.out.print(e.getMessage());
            }

        }

        private String aktienSymbolListToString() {
            /*
            This Method is for Building a String from the Aktien Symbol list.
             */
            org.json.simple.JSONArray ar = new org.json.simple.JSONArray();
            for (Object o:model.getData().getAktienList().getValue()) {
                Aktie ak = (Aktie) o;
                ar.add(ak.getJsonFromAktie());
            }
            String s = ar.toJSONString();
            return s;
        }

        private String portfolioListeToString() {
            String s = null;
            org.json.simple.JSONArray ar = new org.json.simple.JSONArray();
            if (model.getData().getPortfolioList().getValue() != null) {
                for (Object o:model.getData().getPortfolioList().getValue()) {
                    Aktie ak = (Aktie) o;
                    ar.add(ak.getJsonFromAktie());
                }
                s = ar.toJSONString();
            }
            return s;
        }

        private String aktienImDepotToString() {
            String s = null;
            org.json.simple.JSONArray ar = new org.json.simple.JSONArray();
            for (Object o:new Model().getData().getDepot().getAktienImDepot().getValue()) {
                Aktie ak = (Aktie) o;
                ar.add(ak.getJsonFromAktie());
            }
            s = ar.toJSONString();
            return s;
        }

        private String tradesToString() {
            String s = null;
            org.json.simple.JSONArray ar = new org.json.simple.JSONArray();
            for (Object o:new Model().getData().getTrades()) {
                Trade ak = (Trade) o;
                org.json.simple.JSONObject obj = new org.json.simple.JSONObject();
                try {
                    //Aktie:
                    obj.put("menge", ak.getAktie().getAnzahl());
                }catch(Exception e){

                }
                try {
                    obj.put("exchange", ak.getAktie().getExchange());
                }catch(Exception e){

                }
                try {
                    obj.put("symbol", ak.getAktie().getSymbol());
                }catch(Exception e){

                }
                try {
                    obj.put("name", ak.getAktie().getName());
                }catch(Exception e){

                }
                try {
                    obj.put("date", ak.getAktie().getDate());
                }catch(Exception e){

                }
                try {
                    obj.put("type", ak.getAktie().getType());
                }catch(Exception e){

                }
                try {
                    obj.put("region", ak.getAktie().getRegion());
                }catch(Exception e){

                }
                try {
                    obj.put("currency", ak.getAktie().getCurrency());
                }catch(Exception e){

                }
                try {
                    obj.put("enabled", ak.getAktie().getEnabled());
                }catch(Exception e){

                }
                try {
                    obj.put("preis", ak.getAktie().getPreis());
                }catch(Exception e){

                }
                try {
                    obj.put("anzahlak", ak.getAktie().getAnzahl());
                }catch(Exception e){

                }
                try {
                    obj.put("change", ak.getAktie().getChange());
                }catch(Exception e){

                }
                try {
                    obj.put("anzahl", String.valueOf(ak.getAnzahl()));
                }catch(Exception e){

                }
                try {
                    obj.put("date", String.valueOf(ak.getDate()));
                }catch(Exception e){

                }
                try {
                    obj.put("preisi", String.valueOf(ak.getPreis()));
                }catch(Exception e){

                }
                try {
                    obj.put("iskauf", String.valueOf(ak.isKauf()));
                }catch(Exception e){

                }

                ar.add(obj);
            }
            s = String.valueOf(ar);
            return s;

        }

        public String getJson() {
            Data d = new Model().getData();
            ArrayList p = d.getAktienList().getValue();
            //Arraylisten für die Aktien/Symbol Verknüpfung
            org.json.simple.JSONArray jsonArray = new org.json.simple.JSONArray();
            if(new Model().getData().getAktienList().getValue() != null) {
                for (Object e : p) {
                    JSONObject a = new JSONObject();
                    Aktie e1 = (Aktie) e;
                    try {
                        a.put("Currency", e1.getCurrency());
                    } catch (JSONException ex) {
                        ex.printStackTrace();
                    }
                    try {
                        a.put("Date", e1.getDate());
                    } catch (JSONException ex) {
                        ex.printStackTrace();
                    }
                    try {
                        a.put("Enabled", e1.getEnabled());
                    } catch (JSONException ex) {
                        ex.printStackTrace();
                    }
                    try {
                        a.put("Exchange", e1.getExchange());
                    } catch (JSONException ex) {
                        ex.printStackTrace();
                    }
                    try {
                        a.put("Name", e1.getName());
                    } catch (JSONException ex) {
                        ex.printStackTrace();
                    }
                    try {
                        a.put("Region", e1.getRegion());
                    } catch (JSONException ex) {
                        ex.printStackTrace();
                    }
                    try {
                        a.put("Symbol", e1.getSymbol());
                    } catch (JSONException ex) {
                        ex.printStackTrace();
                    }
                    try {
                        a.put("Type", e1.getType());
                    } catch (JSONException ex) {
                        ex.printStackTrace();
                    }
                    jsonArray.add(a);
                }

                //Arrayliste für das Depot Anlegen
                JSONObject depot = new JSONObject();
                //depot.put
            }
            //ArrayListe für die vergangenen Trades
            return ((String) jsonArray.toString());
        }
    }
