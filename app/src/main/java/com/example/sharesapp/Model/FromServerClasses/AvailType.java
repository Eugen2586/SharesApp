package com.example.sharesapp.Model.FromServerClasses;

import com.example.sharesapp.Model.Constants;

import java.util.ArrayList;

public class AvailType {
    /*
    This Class is for Reorganize the Tabs from Stock-Trading!
     */
    private String[] type_list = {"ADR", "REIT", "Stock and Bond Fund", "Secondary Issue", "Limited Partnerships", "Common Stock", "ETF", "Warrant", "Right", "Unit", "Temporary", "Not Available"};
    private String[] type_abbr_list = {"ad", "re", "ce", "si", "lp", "cs", "et", "wt", "rt", "ut", "temp", " "};

    public String[] getType_abbr_list() {
        return type_abbr_list;
    }

    public String[] getType_list() {
        return type_list;
    }

    public void setType_abbr_list(Object[] type_abbr_list1) {
        String[] type_list = Constants.TYPE_LIST;
        String[] type_abbr_list = Constants.TYPE_ABBRE_LIST;
        ArrayList i = new ArrayList();

        for (Object s: type_abbr_list1) {
            try {
                if (((String) type_abbr_list1[0]).equals((String) s) && !i.contains(0)) {
                    i.add(0);
                }
                if (((String) type_abbr_list1[1]).equals((String) s) && !i.contains(1)) {
                    i.add(1);
                }
                if (((String) type_abbr_list1[2]).equals((String) s) && !i.contains(2)) {
                    i.add(2);
                }
                if (((String) type_abbr_list1[3]).equals((String) s)&& !i.contains(3)) {
                    i.add(3);
                }
                if (((String) type_abbr_list1[4]).equals((String) s) && !i.contains(4))  {
                    i.add(4);
                }
                if (((String) type_abbr_list1[5]).equals((String) s) && !i.contains(5)) {
                    i.add(5);
                }
                if (((String) type_abbr_list1[6]).equals((String) s) && !i.contains(6)) {
                    i.add(6);
                }
                if (((String) type_abbr_list1[7]).equals((String) s) && !i.contains(7)) {
                    i.add(7);
                }
                if (((String) type_abbr_list1[8]).equals((String) s) && !i.contains(8)) {
                    i.add(8);
                }
            }catch(IndexOutOfBoundsException e){

            }

        }
        ArrayList loktype_list = new ArrayList();
        for (Object z: i) {
            int h = (int) z;
            loktype_list.add(type_list[h]);
        }
        type_list = new String[loktype_list.size()];
        int zaehl = 0;
        type_list = new String[loktype_list.size()];
        type_abbr_list =new String[type_abbr_list1.length];
        for (Object o:loktype_list) {
            String st = (String)o;
            type_list[zaehl] = st;
            zaehl++;
        }
        zaehl = 0;
        for (Object o:i) {
            String st =  o.toString();
            int zahl;
            zahl = Integer.parseInt(st);
            type_abbr_list[zaehl] = Constants.TYPE_ABBRE_LIST[zahl];
            zaehl++;
        }
        System.out.print(type_list);
        System.out.print(type_abbr_list);
    }
}
