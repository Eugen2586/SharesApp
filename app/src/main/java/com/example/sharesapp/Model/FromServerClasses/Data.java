package com.example.sharesapp.Model.FromServerClasses;

import com.example.sharesapp.FunktionaleKlassen.JSON.LoadFromJson;
import com.example.sharesapp.FunktionaleKlassen.JSON.SaveToJSON;
import com.example.sharesapp.Model.FromServerClasses.Aktie;

import java.util.ArrayList;

public class Data {

    public Data(){
        //ToDo initialisation Stuff here!
        LoadFromJson j = new LoadFromJson();
        addArrayList(j.getAktien());
    }

    private ArrayList<Aktie> aktien;

    public void addAktie(Aktie aktie) {
        if (aktien == null) {
            aktien.add(aktie);
        }
    }

    public void addAktienList(ArrayList<Aktie> ar) {
        aktien = ar;
    }

    public ArrayList getAktienList() {
        return aktien;
    }

    protected void finalize(){
        //ToDo do Persistenz
        SaveToJSON stj = new SaveToJSON( aktien );

    }
}
