package com.example.sharesapp.Model.FromServerClasses;

import com.example.sharesapp.Model.Constants;

import java.util.ArrayList;

public class AvailType {
    private String[] type_list = {"ADR", "REIT", "Stock and Bond Fund", "Secondary Issue", "Limited Partnerships", "Common Stock", "ETF", "Warrant", "Right", "Unit", "Temporary", "Not Available"};
    private String[] type_abbr_list = {"ad", "re", "ce", "si", "lp", "cs", "et", "wt", "rt", "ut", "temp", " "};

    public String[] getType_abbr_list() {
        return type_abbr_list;
    }

    public String[] getType_list() {
        return type_list;
    }

    public void setType_abbr_list(String[] type_abbr_list) {
        ArrayList i = new ArrayList();
        for (String s: type_abbr_list) {
            if (type_abbr_list[0].equals(s)){
                i.add(0);
            }
            if (type_abbr_list[1].equals(s)){
                i.add(1);
            }
            if (type_abbr_list[2].equals(s)){
                i.add(2);
            }
            if (type_abbr_list[3].equals(s)){
                i.add(3);
            }
            if (type_abbr_list[4].equals(s)){
                i.add(4);
            }
            if (type_abbr_list[5].equals(s)){
                i.add(5);
            }
            if (type_abbr_list[6].equals(s)){
                i.add(6);
            }
            if (type_abbr_list[7].equals(s)){
                i.add(7);
            }
        }
        ArrayList loktype_list = new ArrayList();
        for (Object z: i) {
            int h = (int) z;
            loktype_list.add(type_list[h]);
        }
        this.type_list = (String[]) loktype_list.toArray();
        this.type_abbr_list = type_abbr_list;

    }

}
