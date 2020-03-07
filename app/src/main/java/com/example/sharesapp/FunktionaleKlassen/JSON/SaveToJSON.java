package com.example.sharesapp.FunktionaleKlassen.JSON;

import android.content.SharedPreferences;

import com.example.sharesapp.Model.FromServerClasses.Aktie;
import com.example.sharesapp.Model.FromServerClasses.Data;
import com.example.sharesapp.Model.FromServerClasses.Trade;
import com.example.sharesapp.Model.Model;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;

    public class SaveToJSON {
        public SaveToJSON(SharedPreferences.Editor editor){
            String s = null;
            //Here it should all Data from the Model get putted!

            //Trades
            s = tradesToString();
            editor.putString("Trades", s);
            s = null;

            //Depot Inhalt
            s = aktienImDepotToString();
            editor.putString("Depot", s);
            editor.putFloat("Geldwert", new Model().getData().getDepot().getGeldwert());
            s = null;

            //Portfolio Liste
            s = portfolioListeToString();
            editor.putString("Portfolioliste", s);
            s = null;

            //Aktien
            s = aktienSymbolListToString();
            editor.putString("AktienSymbole", s);
            s = null;

            //This make it in the OS.
            editor.apply();
        }

        private String aktienSymbolListToString() {
            /*
            This Method is for Building a String from the Aktien Symbol list.
             */
            String s = new String();
            JSONArray ar = new JSONArray();
            for (Object o:new Model().getData().getAktienList().getValue()) {
                Aktie ak = (Aktie) o;
                ar.add(ak.getJsonFromAktie());
            }
            s = ar.toJSONString();
            return s;
        }

        private String portfolioListeToString() {
            String s = null;
            JSONArray ar = new JSONArray();
            for (Object o:new Model().getData().getPortfolioList()) {
                Aktie ak = (Aktie) o;
                ar.add(ak.getJsonFromAktie());
            }
            s = ar.toJSONString();
            return s;
        }

        private String aktienImDepotToString() {
            String s = null;
            JSONArray ar = new JSONArray();
            for (Object o:new Model().getData().getDepot().getAktienImDepot()) {
                Aktie ak = (Aktie) o;
                ar.add(ak.getJsonFromAktie());
            }
            s = ar.toJSONString();
            return s;
        }

        private String tradesToString() {
            String s = null;
            JSONArray ar = new JSONArray();
            for (Object o:new Model().getData().getDepot().getAktienImDepot()) {
                Trade ak = (Trade) o;
                JSONObject obj = new JSONObject();
                obj.put("Aktie", ak.getAktie().getJsonFromAktie());
                obj.put("Anzahl", ak.getAnzahl());
                obj.put("Date", ak.getDate());
                obj.put("Preis", ak.getPreis());
                obj.put("isKauf", ak.isKauf());
                ar.add(obj);
            }
            s = ar.toJSONString();
            return s;

        };

        public String getJson() {
            Data d = new Model().getData();
            ArrayList p = d.getAktienList().getValue();
            //Arraylisten für die Aktien/Symbol Verknüpfung
            JSONArray jsonArray = new JSONArray();
            if(new Model().getData().getAktienList().getValue() != null) {
                for (Object e : p) {
                    JSONObject a = new JSONObject();
                    Aktie e1 = (Aktie) e;
                    a.put("Currency", e1.getCurrency());
                    a.put("Date", e1.getDate());
                    a.put("Enabled", e1.getEnabled());
                    a.put("Exchange", e1.getExchange());
                    a.put("Name", e1.getName());
                    a.put("Region", e1.getRegion());
                    a.put("Symbol", e1.getSymbol());
                    a.put("Type", e1.getType());
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
