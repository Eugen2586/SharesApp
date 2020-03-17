package com.example.sharesapp.FunktionaleKlassen.JSON.ToModel;

import com.example.sharesapp.Model.FromServerClasses.Aktie;
import com.example.sharesapp.Model.Model;


import org.json.JSONArray;
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
            JSONArray jsonay;
            try {
                jsonay = (JSONArray) parser.parse(st);
            }catch(Exception e){
                jsonay = new JSONArray(st);
            }
            ArrayList jsonar = new ArrayList();
            for(int i = 0; i < jsonay.length(); i++ ){
                jsonar.add(jsonay.get(i));
            }
            for (Object t : jsonar) {
                //ToDo hier wird die Zerlegung der Nachrichtenvorgenommen.
                ak = new Aktie();
                org.json.JSONObject json = null;
                try {
                    json = (org.json.JSONObject) t;
                }catch(Exception e){
                    System.out.println(e);
                    System.out.println("BehinderterMongo");
                }
                try {
                    ak.setSymbol(json.getString("symbol").toString());
                } catch (Exception e) {
                    System.out.print(e);
                }
                try {
                    ak.setExchange(json.getString("exchange").toString());
                } catch (Exception e) {

                }
                try {
                    ak.setName(json.getString("name").toString());
                } catch (Exception e) {

                }
                try {
                    ak.setDate(json.getString("date").toString());
                } catch (Exception e) {

                }
                try {
                    ak.setType(json.getString("type").toString());
                } catch (Exception e) {

                }
                try {
                    ak.setRegion(json.getString("region").toString());
                } catch (Exception e) {

                }
                try {
                    ak.setCurrency(json.getString("currency").toString());
                } catch (Exception e) {

                }
                try {
                    ak.setEnabled(json.getString("IsEnabled").toString());
                } catch (Exception e) {

                }
                try {
                    ak.setChange(Float.parseFloat(json.getString("Change").toString()));
                } catch (Exception e) {

                }
                try {
                    ak.setAnzahl(Integer.getInteger(String.valueOf(json.getInt("Menge"))));
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
            if(m.getData().getAktienList().getValue() != null) {
                m.getData().getAktienList().postValue(akl);
            }
            else{
                m.getData().getAktienList().postValue(akl);
            }
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