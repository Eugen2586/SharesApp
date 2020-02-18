package com.example.sharesapp.Model.FromServerClasses;

import com.example.sharesapp.Model.FromServerClasses.Aktie;

import java.util.ArrayList;

public class Data {

    private ArrayList<Aktie> aktien;

    public void addAktie(Aktie aktie) {
        if (aktien == null) {
            aktien.add(aktie);
        }
    }

    public void addArrayList(ArrayList<Aktie> ar) {
        aktien = ar;
    }

    public ArrayList getAktienList() {
        return aktien;
    }
}
