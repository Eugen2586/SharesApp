package com.example.sharesapp.FunktionaleKlassen.JSON;
import android.net.ParseException;
import android.os.Environment;
import android.view.Display;

import com.example.sharesapp.Model.DataJson;
import com.example.sharesapp.Model.FromServerClasses.Aktie;
import com.example.sharesapp.Model.Model;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class LoadFromJson {
    DataJson h;
    private ArrayList aktien = new ArrayList();

    public void setH(DataJson h) {
        this.h = h;
    }


    public void readJson() throws Exception {
        FileReader fr = new FileReader(Environment.getDownloadCacheDirectory() + "keep.dat");
        BufferedReader br = new BufferedReader(fr);
        String st = new String();
        String line = new String();
        while (line != null) {
            line = br.readLine();
            if (line != null) {
                st = st + line;
            }
        }
        JSONParser parser = new JSONParser();
        JSONArray jsonar = (JSONArray) parser.parse(st);
        //TODO pflege hier die Daten, die hier eingelesen werden.
        int i = 1;
        for (Object t : jsonar) {
            if (i == 1) {
                //ToDo Oberste Datenbene
                for (Object ts : ((JSONArray) t)) {

                    JSONObject obj = (JSONObject) ts;
                    Aktie aktie = new Aktie();
                    //ToDo zweite Datenebene
                    aktie.setCurrency((String) obj.get("Currency"));
                    aktie.setDate((String) obj.get("Date"));
                    aktie.setEnabled((String) obj.get("Enabled"));
                    aktie.setExchange((String) obj.get("Exchange"));
                    aktie.setName((String) obj.get("Name"));
                    aktie.setRegion((String) obj.get("Region"));
                    aktie.setSymbol((String) obj.get("Symbol"));
                    aktie.setType((String) obj.get("Type"));
                    aktien.add(aktie);
                }
                i++;
            }
        }
        Model model = new Model();
        model.getDaten().addAktienList(aktien);
    }
}
