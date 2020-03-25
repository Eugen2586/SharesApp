package com.example.sharesapp.Model;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sharesapp.FunktionaleKlassen.JSON.SaveToJSON;
import com.example.sharesapp.FunktionaleKlassen.JSON.ToModel.RequestSymbol;
import com.example.sharesapp.Model.FromServerClasses.Aktie;
import com.example.sharesapp.Model.FromServerClasses.Data;
import com.example.sharesapp.Model.FromServerClasses.Trade;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;

public class Model  extends AppCompatActivity {
    //Hierdrin werden alle Daten gestored.
    static Data data;

    public Data getData(){
        if (data == null){
            //ToDo Hier die persistenz füllen!
            //Chris K.
            data = new Data();
        }
        return data;
    }
    public void doPersistanceFBackground(Context context){
        SharedPreferences prefs;
        try {
            prefs = getSharedPreferences("SharesApp0815DataContent0815#0518", Context.MODE_PRIVATE);
            prefs.edit().clear();
            SharedPreferences.Editor editor = prefs.edit();
            new SaveToJSON( editor);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void getPersistanceFBackground(Context context){
        SharedPreferences prefs;
        try{
            String s = null;
            prefs = getSharedPreferences("SharesApp0815DataContent0815#0518", Context.MODE_PRIVATE);
            JSONParser parser = new JSONParser();
            try{
                s = prefs.getString("AktienSymbole", null);
                if(s != null && !s.isEmpty()) {
                    new RequestSymbol(s);
                }
            }catch(Exception e){

            }
            s = null;
            try {
                s = prefs.getString("Depot", null);
                if (s != null && !s.isEmpty()) {
                    new Model().getData().getDepot().setAktienImDepot(aktienList(s));
                }
            }catch(Exception e){

            }
            s = null;
            try {
                Float f = prefs.getFloat("Geldwert", (float) -10000000.0);
                if( f < -10000000.0+1 ){
                    f = (float) Constants.MONEY;
                }
                new Model().getData().getDepot().setGeldwert(f);
            }catch(Exception e){
                String t = e.getMessage();
            }
            s = null;
            try {
                s = prefs.getString("Portfolioliste", null);
                if (s != null && !s.isEmpty()) {
                    new Model().getData().setPortfolioList(aktienList(s));
                }
            }catch(Exception e){

            }
            s = null;
            try {
                s = prefs.getString("Tr", null);
                String p = s;
                if(s != null && s.length() > 2) {
                    new Model().getData().setTradelist(getTradeListe(p));
                }
            }catch(Exception e){
                System.out.print(e.getMessage());
                String g = new String();
                System.out.print(s.charAt(265));
            }

        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    private ArrayList<Trade> getTradeListe(String st) throws ParseException {
        Trade tr = null;
        Aktie ak = null;
        ArrayList<Trade> akl = new ArrayList<>();
        JSONParser parser = new JSONParser();
        JSONArray jsonar = (JSONArray) parser.parse(st);
        //TODO pflege hier die Daten, die hier eingelesen werden.
        for (Object t : jsonar) {
            //ToDo hier wird die Zerlegung der Nachrichtenvorgenommen.
            ak = new Aktie();
            org.json.simple.JSONObject json = (JSONObject) t;
            try {
                //Aktie:
                ak.setAnzahl((Integer) json.get("menge"));
            }catch(Exception e){

            }
            try {
                ak.setExchange((String) json.get("exchange"));
            }catch(Exception e){

            }
            try {
                ak.setSymbol((String) json.get("symbol"));
            }catch(Exception e){

            }
            try {
                ak.setName((String) json.get("name"));
            }catch(Exception e){

            }
            try {
                ak.setDate((String) json.get("date"));
            }catch(Exception e){

            }
            try {
                ak.setType((String) json.get("type"));
            }catch(Exception e){

            }
            try {
                ak.setRegion((String) json.get("region"));
            }catch(Exception e){

            }
            try {
                ak.setCurrency((String) json.get("currency"));
            }catch(Exception e){

            }
            try {
                ak.setEnabled((String) json.get("enabled"));
            }catch(Exception e){

            }
            try {
                ak.setPreis(Float.parseFloat((String) json.get("preis")));
            }catch(Exception e){

            }
            try {
                ak.setAnzahl(Integer.parseInt((String) json.get("anzahlak")));
            }catch(Exception e){

            }
            try {
                ak.setChange(Float.parseFloat((String) json.get("change")));
            }catch(Exception e){

            }
            try {
                ak.setAnzahl(Integer.parseInt((String) json.get("anzahl")));
            }catch(Exception e){

            }
            try {
                ak.setDate((String) json.get("date"));
            }catch(Exception e){

            }
            try {
                ak.setPreis(Float.parseFloat((String) json.get("preisi")));
            }catch(Exception e){

            }
            try {
                ak.setRegion(json.get("region").toString());
            } catch (Exception e) {

            }
            Boolean isKauf = null;
            try {
                isKauf = Boolean.parseBoolean(json.get("iskauf").toString());
            } catch (Exception e) {

            }
            int anzahlImTrade = Integer.parseInt((String) json.get("anzahl"));
            float ft = Float.parseFloat(json.get("preis").toString());
            String date = json.get("date").toString();
            // (Aktie aktie, int anzahl, boolean kauf, float preis, Date date)
            tr = new Trade(ak, anzahlImTrade, isKauf  , ft , date );
            new Model().getData().addTrade(tr);
            akl.add(tr);
        }
        return akl;
    }

    private Aktie getAktie(String st) throws ParseException {
        Aktie ak = null;
        ArrayList akl = new ArrayList();
        JSONParser parser = new JSONParser();
        org.json.simple.JSONObject json = (JSONObject) parser.parse(st);
        ak = new Aktie();
        try {
            ak.setSymbol(json.get("symbol").toString());

        } catch (Exception e) {

        }
        try {
            ak.setExchange(json.get("exchange").toString());
        } catch (Exception e) {

        }
        try {
            ak.setName(json.get("name").toString());
        } catch (Exception e) {

        }
        try {
            ak.setDate(json.get("date").toString());
        } catch (Exception e) {

        }
        try {
            ak.setType(json.get("type").toString());
        } catch (Exception e) {

        }
        try {
            ak.setRegion(json.get("region").toString());
        } catch (Exception e) {

        }
        try {
            ak.setCurrency(json.get("currency").toString());
        } catch (Exception e) {

        }
        try {
            ak.setEnabled(json.get("isEnabled").toString());
        } catch (Exception e) {

        }
        try {
            ak.setChange(Float.parseFloat(json.get("change").toString()));
        } catch (Exception e) {

        }
        try {
            ak.setAnzahl(Integer.parseInt(json.get("anzahl").toString()));
        } catch (Exception e) {

        }
        try{
            ak.setPreis(Float.parseFloat((json.get("preis").toString())));
        }
        catch(Exception e){

        }
        return ak;
    }

    private ArrayList aktienList(String st) throws ParseException {
        Aktie ak = null;
        ArrayList akl = new ArrayList();
        JSONParser parser = new JSONParser();
        org.json.simple.JSONArray jsonar = (JSONArray) parser.parse(st);
        jsonar.isEmpty();
        //TODO pflege hier die Daten, die hier eingelesen werden.
        for (Object t : jsonar) {
            //ToDo hier wird die Zerlegung der Nachrichtenvorgenommen.
            ak = new Aktie();
            org.json.simple.JSONObject json = (JSONObject) t;
            try {
                ak.setSymbol(json.get("symbol").toString());

            } catch (Exception e) {

            }
            try {
                ak.setExchange(json.get("exchange").toString());
            } catch (Exception e) {

            }
            try {
                ak.setName(json.get("name").toString());
            } catch (Exception e) {

            }
            try {
                ak.setDate(json.get("date").toString());
            } catch (Exception e) {

            }
            try {
                ak.setType(json.get("type").toString());
            } catch (Exception e) {

            }
            try {
                ak.setRegion(json.get("region").toString());
            } catch (Exception e) {

            }
            try {
                ak.setCurrency(json.get("currency").toString());
            } catch (Exception e) {

            }
            try {
                ak.setEnabled(json.get("isEnabled").toString());
            } catch (Exception e) {

            }
            try {
                ak.setChange(Float.parseFloat(json.get("change").toString()));
            } catch (Exception e) {

            }
            try {
                ak.setAnzahl(Integer.parseInt(json.get("anzahl").toString()));
            } catch (Exception e) {

            }
            try{
                ak.setPreis(Float.parseFloat((json.get("preis").toString())));
            }
            catch(Exception e){

            }
            akl.add(ak);
        }
        return akl;
    }
}
