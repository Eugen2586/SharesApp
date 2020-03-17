package com.example.sharesapp.FunktionaleKlassen.JSON.ToModel;

import com.example.sharesapp.Model.FromServerClasses.Aktie;
import com.example.sharesapp.Model.Model;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.util.ArrayList;

public class RequestSymbol {


    ArrayList type = new ArrayList();
    Aktie ak = new Aktie();
    ArrayList<Aktie> akl = new ArrayList<>();

    public RequestSymbol(String st) throws Exception {
        JSONParser parser = new JSONParser();
        if (st != null && !st.isEmpty()) {
            JSONArray jsonar = (JSONArray) parser.parse(st);
            //TODO pflege hier die Daten, die hier eingelesen werden.
            for (Object t : jsonar) {
                //ToDo hier wird die Zerlegung der Nachrichtenvorgenommen.
                ak = new Aktie();
                org.json.simple.JSONObject json = (JSONObject) t;
                try {
                    ak.setSymbol(json.get("Symbol").toString());
                } catch (Exception e) {

                }
                try {
                    ak.setExchange(json.get("Exchange").toString());
                } catch (Exception e) {

                }
                try {
                    ak.setName(json.get("Name").toString());
                } catch (Exception e) {

                }
                try {
                    ak.setDate(json.get("Date").toString());
                } catch (Exception e) {

                }
                try {
                    ak.setType(json.get("Type").toString());
                } catch (Exception e) {

                }
                try {
                    ak.setRegion(json.get("Region").toString());
                } catch (Exception e) {

                }
                try {
                    ak.setCurrency(json.get("Currency").toString());
                } catch (Exception e) {

                }
                try {
                    ak.setEnabled(json.get("IsEnabled").toString());
                } catch (Exception e) {

                }
                try {
                    ak.setChange(Float.parseFloat(json.get("Change").toString()));
                } catch (Exception e) {

                }
                try {
                    ak.setAnzahl(Integer.getInteger(String.valueOf(json.get("Menge"))));
                } catch (Exception e) {

                }
                if (!akl.contains(ak)) {
                    akl.add(ak);
                }
                try {
                    if (((!type.contains(ak.getType())) && (!(ak.getSymbol().isEmpty())) && (!ak.getName().isEmpty()))) {
                        type.add(ak.getType());
                    }
                }catch(Exception e){

                }
            }
            Model m = new Model();
            m.getData().getAktienList().setValue(akl);
            Object[] data = type.toArray();
            String[] sts = new String[data.length];
            int i = 0;
            for (Object t : data) {
                sts[i] = t.toString();
                i++;
            }
            m.getData().getAvailType().setType_abbr_list(sts);
        }
    }


    public ArrayList<Aktie> getAk() {
        Model m = new Model();
        m.getData().addAktienList(akl);
        return akl;
    }
}