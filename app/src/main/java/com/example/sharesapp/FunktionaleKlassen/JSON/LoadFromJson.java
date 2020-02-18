package com.example.sharesapp.FunktionaleKlassen.JSON;
import android.net.ParseException;

import com.example.sharesapp.Model.DataJson;


import org.json.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

    public class LoadFromJson {
        DataJson h;

        public void setH(DataJson h) {
            this.h = h;
        }


        public void readJson() throws Exception {
            FileReader fr = new FileReader("keep.dat");
            BufferedReader br = new BufferedReader(fr);
            String st = new String();
            String line = new String();
            while(line != null) {
                line = br.readLine();
                if(line != null) {
                    st = st + line;
                }
            }
            JSONParser parser = new JSONParser();
            JSONArray jsonar = (JSONArray)parser.parse(st);
            //TODO pflege hier die Daten, die hier eingelesen werden.
            for (Object t:jsonar) {
                //ToDo Oberste Datenbene
                addAttribute1(((JSONObject)t).get("projektname").toString());
                for (Object ts:((JSONArray)((JSONObject)t).get("Array")));
                //ToDo zweite Datenebene
                /*
                Timestamp tsp;
                tsp = new Timestamp();
                tsp.setStemp(Long.parseLong(((JSONObject) ts).get("Time").toString()));
                tsp.setMonth(((JSONObject) ts).get("Month").toString());
                tsp.setMonth(((JSONObject) ts).get("Year").toString());
                tsp.setDay(((JSONObject) ts).get("Day").toString());
                p.addTimestamp(tsp);
                */
            }
        }

        private void addAttribute1(String projektname){
            try {
                //ToDo implementier hier entsprechend das Wegschreiben in die Classe
            } catch (Exception e) {
                // TODO LogFile schreiben ergänzen und hier halt bitte alles einzeln schreiben, je nach Exception!
            }
        }

    }
