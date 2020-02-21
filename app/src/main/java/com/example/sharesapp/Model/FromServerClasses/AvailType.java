package com.example.sharesapp.Model.FromServerClasses;

import com.example.sharesapp.Model.Constants;

import java.util.ArrayList;

public class AvailType {
    /*
    This Class is for Reorganize the Tabs from Stock-Trading!
     */
    private String[] availableTypes;
    private String[] availableTypeAbbreviations;

    public String[] getAvailableTypeAbbreviations() {
        return availableTypeAbbreviations;
    }

    public String[] getAvailableTypes() {
        return availableTypes;
    }

    public void setType_abbr_list(String[] type_abbr_list1) {
        //ToDo Mapping here!
        ArrayList<String> l_type_list = new ArrayList<String>();
        for (String s: ( type_abbr_list1)) {
            int i = 0;
            for (String t: Constants.TYPE_ABBREVIATIONS) {
                if(s.equals(t)){
                    l_type_list.add(Constants.TYPES[i]);
                    break;
                }
                i++;
            }
        }
        availableTypeAbbreviations = (String[]) type_abbr_list1.clone();
        availableTypes = new String[type_abbr_list1.length];
        int i = 0;
        for (Object j: l_type_list.toArray()) {
            availableTypes[i] = j.toString();
            i++;
        }
    }
}
