package com.example.sharesapp.Model.FromServerClasses;

import com.example.sharesapp.Model.Constants;

import java.util.ArrayList;

public class AvailType {
    private String[] type_list = {"ADR", "REIT", "Stock and Bond Fund", "Secondary Issue", "Limited Partnerships", "Common Stock", "ETF", "Warrant", "Right", "Unit", "Temporary", "Not Available"};
    private Object[] type_abbr_list = {"ad", "re", "ce", "si", "lp", "cs", "et", "wt", "rt", "ut", "temp", " "};

    public Object[] getType_abbr_list() {
        return type_abbr_list;
    }

    public String[] getType_list() {
        return type_list;
    }

    public void setType_abbr_list(Object[] type_abbr_list1) {
        ArrayList i = new ArrayList();
        for (Object s: type_abbr_list1) {
            try {
                if (((String) type_abbr_list1[0]).equals(s)) {
                    i.add(0);
                }
                if (((String) type_abbr_list1[1]).equals(s)) {
                    i.add(1);
                }
                if (((String) type_abbr_list1[2]).equals(s)) {
                    i.add(2);
                }
                if (((String) type_abbr_list1[3]).equals(s)) {
                    i.add(3);
                }
                if (((String) type_abbr_list1[4]).equals(s)) {
                    i.add(4);
                }
                if (((String) type_abbr_list1[5]).equals(s)) {
                    i.add(5);
                }
                if (((String) type_abbr_list1[6]).equals(s)) {
                    i.add(6);
                }
                if (((String) type_abbr_list1[7]).equals(s)) {
                    i.add(7);
                }
                if (((String) type_abbr_list1[8]).equals(s)) {
                    i.add(8);
                }
                if (((String) type_abbr_list1[9]).equals(s)) {
                    i.add(9);
                }
                if (((String) type_abbr_list1[10]).equals(s)) {
                    i.add(10);
                }
            }catch(IndexOutOfBoundsException e){

            }

        }
        ArrayList loktype_list = new ArrayList();
        for (Object z: i) {
            int h = (int) z;
            loktype_list.add(type_list[h]);
        }
        type_list = new String[type_abbr_list1.length];
        int zaehl = 0;
        for (Object o:loktype_list) {
            String st = (String)o;
            type_list[zaehl] = st;
            zaehl++;
        }
        for (Object o:i) {
            int st = (int)o;
            type_abbr_list[zaehl] = Constants.TYPE_ABBRE_LIST[st];
        }
    }
}
