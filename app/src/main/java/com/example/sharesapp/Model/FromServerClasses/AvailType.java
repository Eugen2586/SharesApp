package com.example.sharesapp.Model.FromServerClasses;

import com.example.sharesapp.Model.Constants;

import java.util.ArrayList;

public class AvailType {
    /*
    This Class is for Reorganize the Tabs from Stock-Trading!
     */
    private String[] type_list;
    private String[] type_abbr_list;

    public String[] getType_abbr_list() {
        return type_abbr_list;
    }

    public String[] getType_list() {
        return type_list;
    }

    public void setType_abbr_list(String[] type_abbr_list1) {
        //ToDo Mapping here!
        ArrayList<String> l_type_list = new ArrayList<String>();
        for (String s: ( type_abbr_list1)) {
            int i = 0;
            for (String t: Constants.TYPE_ABBRE_LIST) {
                if(s.equals(t)){
                    l_type_list.add(Constants.TYPE_ABBRE_LIST[i]);
                    break;
                }
                i++;
            }
        }
        type_list = (String[]) type_abbr_list1.clone();
        type_abbr_list = new String[type_abbr_list1.length];
        int i = 0;
        for (Object j:l_type_list.toArray()) {
            type_abbr_list[i] = j.toString();
            i++;
        }
        if (2==3){
            return;
        }
    }
}
