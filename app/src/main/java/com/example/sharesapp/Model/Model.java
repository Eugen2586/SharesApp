package com.example.sharesapp.Model;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;

import com.example.sharesapp.DrawerActivity;
import com.example.sharesapp.FunktionaleKlassen.JSON.SaveToJSON;
import com.example.sharesapp.FunktionaleKlassen.JSON.ToModel.RequestSymbol;
import com.example.sharesapp.Model.FromServerClasses.Aktie;
import com.example.sharesapp.Model.FromServerClasses.Data;
import com.example.sharesapp.Model.FromServerClasses.Order;
import com.example.sharesapp.Model.FromServerClasses.Trade;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;

public class Model {
    public Context context;
    private static Data data;
    private static MutableLiveData<Boolean> writeFlag = new MutableLiveData<>();

    public Data getData() {
        if (data == null) {
            //ToDo Hier die persistenz füllen!
            //Chris K.
            data = new Data();
        }
        return data;
    }

    public boolean getWriteFlag() {
        if (writeFlag.getValue() == null) {
            return false;
        } else {
            return writeFlag.getValue();
        }
    }

    public void setWriteFlag(boolean writeFlag) {
        Model.writeFlag.postValue(writeFlag);
    }

    public Context getContext() {
        return context;
    }

    /*
    public void doPersistanceFBackground(){
        context = new DrawerActivity().getBaseContext();
        SharedPreferences prefs;
        try {
            prefs = new DrawerActivity().getSharedPreferences("SharesApp0815DataContent0815#0518", Context.MODE_PRIVATE);
            prefs.edit().clear();
            SharedPreferences.Editor editor = prefs.edit();
            doPersistanceforSellLists(editor);
            new SaveToJSON( editor);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void getPersistanceFBackground(){

        context  = new DrawerActivity().getBaseContext();
        SharedPreferences prefs;
        try{
            String s = null;
            prefs = new DrawerActivity().getSharedPreferences("SharesApp0815DataContent0815#0518", Context.MODE_PRIVATE);
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
                if(s != null && s.length() > 2) {
                    new Model().getData().setTradelist(getTradeListe(s));
                }
            }catch(Exception e){

            }
            try {
                s = prefs.getString("gBOSL", null);
                if(s != null && s.length() > 2) {
                    new Model().getData().getBuyOrderStockList().clear();
                    new Model().getData().getBuyOrderStockList().addAll(orderListFromJson( s ));
                }
            }catch(Exception e){
                s = prefs.getString("gSOSL", null);
                if(s != null && s.length() > 2) {
                    new Model().getData().getSellOrderStockList().clear();
                    new Model().getData().getSellOrderStockList().addAll(orderListFromJson( s ));
                }
            }

        }
        catch(Exception e){
            e.printStackTrace();
        }
    }


    private void doPersistanceforSellLists(SharedPreferences.Editor editor) {
        editor.commit();
        org.json.JSONArray jsonArray1 = new org.json.JSONArray();
        org.json.JSONArray jsonArray2 = new org.json.JSONArray();
        ArrayList ar1 = new Model().getData().getBuyOrderStockList();
        ArrayList ar2 = new Model().getData().getSellOrderStockList();
        for (Object t: ar1) {
            JSONObject json = new JSONObject();
            Order or  = (Order) t;
            try {
                //Aktie:
                json.put("menge", or.getStock().getAnzahl());
            }catch(Exception e){

            }
            try {
                json.put("exchange",  or.getStock().getExchange());
            }catch(Exception e){

            }
            try {
                json.put("symbol", or.getStock().getSymbol());
            }catch(Exception e){

            }
            try {
                json.put("name", or.getStock().getName());
            }catch(Exception e){

            }
            try {
                json.put("date", or.getStock().getDate());
            }catch(Exception e){

            }
            try {
                json.put("type", or.getStock().getType());
            }catch(Exception e){

            }
            try {
                json.put("region", or.getStock().getRegion());
            }catch(Exception e){

            }
            try {
                json.put("RequestCurrency", or.getStock().getCurrency());
            }catch(Exception e){

            }
            try {
                json.put("enabled", or.getStock().getEnabled());
            }catch(Exception e){

            }
            try {
                json.put("preis", or.getStock().getPreis());
            }catch(Exception e){

            }
            try {
                json.put("symbol", or.getSymbol());
            }catch(Exception e){

            }
            try {
                json.put("number", or.getNumber());
            }catch(Exception e){

            }
            try {
                json.put("limit", or.getLimit());
            }catch(Exception e){

            }
            ar1.add(json);
        }
        editor.putString("gBOSL", ar1.toString());
        editor.commit();
        editor.apply();
        for (Object t: ar2) {
            JSONObject json = new JSONObject();
            Order or  = (Order) t;
            try {
                //Aktie:
                json.put("menge", or.getStock().getAnzahl());
            }catch(Exception e){

            }
            try {
                json.put("exchange",  or.getStock().getExchange());
            }catch(Exception e){

            }
            try {
                json.put("symbol", or.getStock().getSymbol());
            }catch(Exception e){

            }
            try {
                json.put("name", or.getStock().getName());
            }catch(Exception e){

            }
            try {
                json.put("date", or.getStock().getDate());
            }catch(Exception e){

            }
            try {
                json.put("type", or.getStock().getType());
            }catch(Exception e){

            }
            try {
                json.put("region", or.getStock().getRegion());
            }catch(Exception e){

            }
            try {
                json.put("RequestCurrency", or.getStock().getCurrency());
            }catch(Exception e){

            }
            try {
                json.put("enabled", or.getStock().getEnabled());
            }catch(Exception e){

            }
            try {
                json.put("preis", or.getStock().getPreis());
            }catch(Exception e){

            }
            try {
                json.put("symbol", or.getSymbol());
            }catch(Exception e){

            }
            try {
                json.put("number", or.getNumber());
            }catch(Exception e){

            }
            try {
                json.put("limit", or.getLimit());
            }catch(Exception e){

            }
            ar2.add(json);
        }
        editor.putString("gSOSL", ar2.toString());
        editor.commit();
        editor.apply();

    }


    private ArrayList orderListFromJson(String s) throws ParseException {
        ArrayList ar = new ArrayList();
        JSONParser parse = new JSONParser();
        JSONArray jsonar = (JSONArray) parse.parse(s);
        for (Object t:jsonar) {
            JSONObject json = new JSONObject();
            Aktie ak = new Aktie();
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
                ak.setCurrency((String) json.get("RequestCurrency"));
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
            String symbol = null;
            int number = 0;
            float limit = 0;
            try {
                symbol = (String) json.get("symbol");
            }catch(Exception e){

            }
            try {
                number = Integer.parseInt((String) json.get("number"));
            }catch(Exception e){

            }
            try {
                limit = Float.parseFloat((String) json.get("limit"));
            }catch(Exception e){

            }
            Order or = new Order( ak, symbol, number, limit );
            ar.add(or);
        }
        return ar;
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
                ak.setCurrency((String) json.get("RequestCurrency"));
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
            int anzahlImTrade = Integer.parseInt(json.get("anzahl").toString());
            float ft = Float.parseFloat(json.get("preis").toString());
            long millis = Long.parseLong(json.get("date").toString());
            // (Aktie aktie, int anzahl, boolean kauf, float preis, Date date)
            tr = new Trade(ak, anzahlImTrade, isKauf  , ft , millis , ak.getSymbol());
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
            ak.setCurrency(json.get("RequestCurrency").toString());
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
                ak.setCurrency(json.get("RequestCurrency").toString());
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
     */
}
