package com.example.sharesapp.Model;

import com.example.sharesapp.Model.FromServerClasses.Data;

public class Model {
    //Hierdrin werden alle Daten gestored.
    static Data daten;

    public Data getDaten(){
        if (daten == null){
            //ToDo Hier die persistenz füllen!
            //Chris K.
            daten = new Data();
        }
        return daten;
    }

}
