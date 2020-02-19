package com.example.sharesapp.Model.FromServerClasses;

import android.content.Context;

import com.example.sharesapp.FunktionaleKlassen.JSON.LoadFromJson;
import com.example.sharesapp.FunktionaleKlassen.JSON.SaveToJSON;
import com.example.sharesapp.Model.FromServerClasses.Aktie;

import java.util.ArrayList;

public class Data {
    private ArrayList<Aktie> aktien;
    private ArrayList<Trade> tradelist;

    public Data(){
        //ToDo initialisation Stuff here!
        LoadFromJson j = new LoadFromJson();
        try {
        j.readJson();
        }
        catch(Exception e){
        }
    }



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
        try {
            // SaveToJSON stj = new SaveToJSON(aktien, );
        }catch(Exception e){

        }

    }
}
