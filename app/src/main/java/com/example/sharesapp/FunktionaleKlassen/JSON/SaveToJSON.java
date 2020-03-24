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
        private Model model = new Model();
        public SaveToJSON(SharedPreferences.Editor editor){
            String s = null;
            //Here it should all Data from the Model get putted!

            //Trades
            try {
                s = tradesToString();
                editor.putString("Tr", s );
            }catch(Exception e) {
                System.out.print(e.getMessage());
            }
            s = null;
            //Depot Inhalt
            s = aktienImDepotToString();
            try {
                editor.putString("Depot", s);

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
            }catch(Exception e) {
                s = null;
            }

            //Aktien
            s = aktienSymbolListToString();
            try {
                editor.putString("AktienSymbole", s);
            }catch(Exception e){
                
            }
            s = null;

            //This make it in the OS.
            editor.apply();
        }

        private String aktienSymbolListToString() {
            /*
            This Method is for Building a String from the Aktien Symbol list.
             */
            JSONArray ar = new JSONArray();
            for (Object o:model.getData().getAktienList().getValue()) {
                Aktie ak = (Aktie) o;
                ar.add(ak.getJsonFromAktie());
            }
            String s = ar.toJSONString();
            return s;
        }

        private String portfolioListeToString() {
            String s = null;
            JSONArray ar = new JSONArray();
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
            JSONArray ar = new JSONArray();
            for (Object o:new Model().getData().getDepot().getAktienImDepot().getValue()) {
                Aktie ak = (Aktie) o;
                ar.add(ak.getJsonFromAktie());
            }
            s = ar.toJSONString();
            return s;
        }

        private String tradesToString() {
            String s = null;
            JSONArray ar = new JSONArray();
            for (Object o:new Model().getData().getTrades()) {
                Trade ak = (Trade) o;
                JSONObject obj = new JSONObject();
                obj.put("aktie", ak.getAktie().getJsonFromAktie());
                obj.put("anzahl", ak.getAnzahl());
                obj.put("date", ak.getDate());
                obj.put("preis", ak.getPreis());
                obj.put("iskauf", ak.isKauf());
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
