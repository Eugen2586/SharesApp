package com.example.sharesapp.FunktionaleKlassen.JSON;
import android.os.Environment;
import android.view.Display;

import com.example.sharesapp.Model.DataJson;
import com.example.sharesapp.Model.FromServerClasses.Aktie;
import com.example.sharesapp.Model.Model;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

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

    }
}
