package com.example.sharesapp.FunktionaleKlassen.JSON;

import android.content.Context;
import android.os.Environment;

import com.example.sharesapp.Model.FromServerClasses.Aktie;
import com.example.sharesapp.Model.FromServerClasses.Data;
import com.example.sharesapp.Model.Model;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

    public class SaveToJSON {
        ArrayList objects = new ArrayList();
        JSONArray b = new JSONArray();

        public SaveToJSON() throws IOException {
            //Deklarationsbereich
            JSONArray jsonArray = new JSONArray();
            //ToDo Hier muss man alle Vairiablen ergänzen die gestashed werden sollen.
            //jsonArray.add(createJSONAktien(data));
            jsonArray.add(new JSONObject().put("Teest", "test"));
            //File Schreiben
            //context.openFileOutput("config.txt", Context.MODE_PRIVATE)
            // Wir suchen hier eine Context, der die entsprechenden einzelteile zusammenführt.
            FileWriter frw = new FileWriter(Environment.getDownloadCacheDirectory() + "keep.dat");
            frw.write(String.valueOf(jsonArray));
            frw.flush();
            frw.close();
        }


        private JSONArray createJSONAktien(){
            Data d = new Model().getDaten();
            ArrayList p = d.getAktienList().getValue();
            //Arraylisten für die Aktien/Symbol Verknüpfung
            JSONArray jsonArray = new JSONArray();
            for (Object e: p) {
                JSONObject a = new JSONObject();
                Aktie e1 = (Aktie)e;
                a.put("Currency", e1.getCurrency());
                a.put("Date", e1.getDate());
                a.put("Enabled", e1.getEnabled());
                a.put("Exchange",e1.getExchange());
                a.put("Name", e1.getName());
                a.put("Region", e1.getRegion());
                a.put("Symbol", e1.getSymbol());
                a.put("Type", e1.getType());
                jsonArray.add(a);
            }

            //Arrayliste für das Depot Anlegen
            JSONObject depot = new JSONObject();
            //depot.put

            //ArrayListe für die vergangenen Trades
            return jsonArray;
        }

}
