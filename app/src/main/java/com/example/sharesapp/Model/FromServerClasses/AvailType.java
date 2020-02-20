package com.example.sharesapp.Model.FromServerClasses;

import com.example.sharesapp.Model.Constants;

import java.util.ArrayList;

public class AvailType {
    /*
    This Class is for Reorganize the Tabs from Stock-Trading!
     */
    private String[] type_list = Constants.TYPE_LIST;
    private String[] type_abbr_list = Constants.TYPE_ABBRE_LIST;

    public String[] getType_abbr_list() {
        return type_abbr_list;
    }

    public String[] getType_list() {
        return type_list;
    }

    public void setType_abbr_list(Object[] type_abbr_list1) {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(0);
        for (Object s: type_abbr_list1) {
            for (int i = 1; i < type_abbr_list1.length; i++) {
                try {
                    if ((type_abbr_list1[i]).equals(s)) {
                        list.add(i);
                    }
                } catch (Exception e) {

                }
            }
        }
        ArrayList<String> loktype_list = new ArrayList<>();
        for (Object z: list) {
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
        for (Object o: list) {
            int st = (int)o;
            type_abbr_list[zaehl] = Constants.TYPE_ABBRE_LIST[st];
        }
    }
}
