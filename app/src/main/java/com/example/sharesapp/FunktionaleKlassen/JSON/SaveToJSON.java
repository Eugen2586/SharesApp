package com.example.sharesapp.FunktionaleKlassen.JSON;

import com.example.sharesapp.Model.DataJson;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

    public class SaveToJSON {
        ArrayList objects = new ArrayList();
        JSONArray b = new JSONArray();

        public SaveToJSON(ArrayList aktien) {
            //ToDo persisatance Stuff.



        }

        public void printGson() throws IOException {
            JSONArray jar = new JSONArray();
            for (Object p:objects) {
                //ToDo Implementiert in diesem Style
                //jar.add(createJSON((Projektview)p));
            }
            FileWriter frw = new FileWriter("keep.dat");
            frw.write(String.valueOf(jar));
            frw.flush();
            frw.close();
        }
        private JSONObject createJSON(DataJson p){
            JSONObject a = new JSONObject();
        /*ToDo schreibt eure JSON in diesme Stil!
        a.put("projektname",p.getProjekatname());
        JSONArray c = new JSONArray();
        for (Object t:p.getTimestamps()) {
            JSONObject d = new JSONObject();
            d.put("Day",((Timestamp) t).getDay());
            d.put("Time", ((Timestamp) t).getStemp());
            d.put("Month",((Timestamp) t).getDay());
            d.put("Year",((Timestamp) t).getDay());
            c.add(d);
        }
        a.put("Array", c);
         */
            return a;
        }

}
