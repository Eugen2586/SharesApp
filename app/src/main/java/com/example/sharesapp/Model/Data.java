package com.example.sharesapp.Model;

import java.util.ArrayList;

public class Data {

    ArrayList aktien;

    public void addAktie(Aktie aktie){
        if (aktien == null) {
            aktien.add(aktie);
        }
    }

    public void addArrayList(ArrayList ar){
        aktien = ar;
    }
    public ArrayList getAktienList(){
        return aktien;
    }
}
