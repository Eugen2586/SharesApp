package com.example.sharesapp.Model;

import com.example.sharesapp.Model.FromServerClasses.Data;

public class Model {
    //Hierdrin werden alle Daten gestored.
    static Data data;

    public Data getData(){
        if (data == null){
            //ToDo Hier die persistenz füllen!
            //Chris K.
            data = new Data();
        }
        return data;
    }
    public void doPersistanceFBackground(){


    }
    public void getPersistanceFBackground(){

    }
}
