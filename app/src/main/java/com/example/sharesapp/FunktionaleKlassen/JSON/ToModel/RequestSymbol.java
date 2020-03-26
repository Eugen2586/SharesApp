package com.example.sharesapp.FunktionaleKlassen.JSON.ToModel;

import com.example.sharesapp.Model.FromServerClasses.Aktie;
import com.example.sharesapp.Model.Model;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.simple.parser.JSONParser;

import java.util.ArrayList;

public class RequestSymbol {

    ArrayList type = new ArrayList();
    Aktie ak = new Aktie();
    ArrayList<Aktie> akl = new ArrayList<>();

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
            for (Object t : jsonar) {
                // hier wird die Zerlegung der Nachrichtenvorgenommen.
                ak = new Aktie();
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
                    if (((!type.contains(ak.getType())) && (!(ak.getSymbol().isEmpty())) && (!ak.getName().isEmpty()))) {
                        type.add(ak.getType());
                    }
                } catch (Exception e) {

                }
            }
            Model model = new Model();
            ArrayList<Aktie> stockList = model.getData().getAktienList().getValue();
            if (stockList == null) {
                stockList = new ArrayList<>();
            }
            stockList.addAll(akl);
            model.getData().getAktienList().postValue(stockList);

            Object[] data = type.toArray();
            if (data.length != 0 && data[0] != "crypto") {
                String[] sts = new String[data.length];
                int i = 0;
                for (Object t : data) {
                    sts[i] = t.toString();
                    i++;
                }
                model.getData().getAvailType().setType_abbr_list(sts);
            }
        }
    }


    public ArrayList<Aktie> getAk() {
        Model m = new Model();
        m.getData().addAktienList(akl);
        return akl;
    }
}