package com.example.sharesapp.FunktionaleKlassen.JSON.ToModel;

import com.example.sharesapp.Model.Constants;
import com.example.sharesapp.Model.FromServerClasses.Aktie;
import com.example.sharesapp.Model.Model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.simple.parser.JSONParser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RequestSymbol {

    private ArrayList<Aktie> akl = new ArrayList<>();

    public RequestSymbol(String st) throws Exception {
        JSONParser parser = new JSONParser();
        if (st != null && !st.isEmpty()) {
            JSONArray jsonay;
            try {
                jsonay = (JSONArray) parser.parse(st);
            } catch (Exception e) {
                jsonay = new JSONArray(st);
            }
            ArrayList jsonar = new ArrayList();
            for (int i = 0; i < jsonay.length(); i++) {
                jsonar.add(jsonay.get(i));
            }
            ArrayList<String> typeList = new ArrayList<>();
            List<String> existingTypeAbbr = Arrays.asList(Constants.TYPE_ABBREVIATIONS);
            for (Object t : jsonar) {
                // hier wird die Zerlegung der Nachrichtenvorgenommen.
                Aktie ak = new Aktie();
                org.json.JSONObject json = null;
                try {
                    json = (org.json.JSONObject) t;
                } catch (Exception e) {
                }
                try {
                    ak.setSymbol(json.getString("symbol"));
                } catch (Exception e) {
                }
                try {
                    ak.setExchange(json.getString("exchange"));
                } catch (Exception e) {

                }
                try {
                    ak.setName(json.getString("name"));
                } catch (Exception e) {

                }
                try {
                    ak.setDate(json.getString("date"));
                } catch (Exception e) {

                }
                try {
                    ak.setType(json.getString("type"));
                    if (ak.getType().equals("temp")) {
                        continue;
                    }
                } catch (Exception e) {

                }
                try {
                    ak.setRegion(json.getString("region"));
                } catch (Exception e) {

                }
                try {
                    ak.setCurrency(json.getString("RequestCurrency"));
                } catch (Exception e) {

                }
                try {
                    ak.setEnabled(json.getString("IsEnabled"));
                } catch (Exception e) {

                }
                try {
                    ak.setChange(Float.parseFloat(json.getString("Change")));
                } catch (Exception e) {

                }
                try {
                    ak.setAnzahl(json.getInt("Menge"));
                } catch (Exception e) {

                }
                try {
                    ak.setSymbol(json.get("symbol").toString());
                } catch (JSONException e) {
                }
                try {
                    ak.setName(json.get("name").toString());
                } catch (JSONException e) {
                }
                try {
                    ak.setExchange(json.get("exchange").toString());
                } catch (JSONException e) {
                }
                try {
                    ak.setDate(json.get("date").toString());
                } catch (JSONException e) {
                }
                try {
                    ak.setType(json.get("type").toString());
                } catch (JSONException e) {
                }
                try {
                    ak.setRegion(json.get("region").toString());
                } catch (JSONException e) {
                }
                try {
                    ak.setCurrency(json.get("currency").toString());
                } catch (JSONException e) {
                }
                if (!akl.contains(ak)) {
                    akl.add(ak);
                }
                try {
                    if ((!typeList.contains(ak.getType()) && !(ak.getSymbol().isEmpty()) && !ak.getName().isEmpty() && existingTypeAbbr.contains(ak.getType()))) {
                        typeList.add(ak.getType());
                    }
                } catch (Exception e) {

                }
            }
            Model model = new Model();
            while (model.getWriteFlag()) ;
            while (model.getWriteFlag()) ;
            model.setWriteFlag(true);
            ArrayList<Aktie> stockList = model.getData().getAktienList().getValue();
            if (stockList == null) {
                stockList = new ArrayList<>();
            }
            ArrayList<Aktie> stocksToRemove = new ArrayList<>();
            for (Aktie existingStock : stockList) {
                for (Aktie newStock : akl) {
                    if (newStock.getSymbol().equals(existingStock.getSymbol())) {
                        stocksToRemove.add(newStock);
                        break;
                    }
                }
            }
            ArrayList<Aktie> currentStockList = model.getData().getAktienList().getValue();
            if (currentStockList != null)
                akl.removeAll(stocksToRemove);
            akl.removeAll(stocksToRemove);
            stockList.addAll(akl);
            model.getData().getAktienList().postValue(stockList);
            model.setWriteFlag(false);

            typeList.remove("crypto");
            Object[] data = typeList.toArray();
            if (data.length != 0) {
                String[] sts = new String[data.length];
                int i = 0;
                for (Object t : data) {
                    sts[i] = t.toString();
                    i++;
                }
                model.getData().getAvailType().setTypeAbbrList(sts);
            }
        }
    }
}