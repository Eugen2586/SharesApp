package com.example.sharesapp.Model;

import com.example.sharesapp.Model.FromServerClasses.Data;

public class Model {
    //Hierdrin werden alle Daten gestored.
    static Data data;

    public Data getData(){
        if (data == null){
            //ToDo Hier die persistenz füllen!
            //Chris K.
            resetData();
        }
        return data;
    }

    public void resetData() {
        data = new Data();
        data.getDepot().setGeldwert(Constants.MONEY);
    }

}
