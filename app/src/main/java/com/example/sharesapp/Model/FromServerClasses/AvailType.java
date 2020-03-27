package com.example.sharesapp.Model.FromServerClasses;

import com.example.sharesapp.Model.Constants;

import java.util.ArrayList;


/**
 * Diese Klasse ist relevant für die Taborganisation der AktienEinordnung im AktienFragment
 * Hier werden die verfügbaren Kategorien gespeichert
 */
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

    /**
     * Setzen der aktuellen Liste für die Tabs in der Aktienübersicht
     * @param type_abbr_list1 Array der unterschiedlichen Kategorien
     */
    public void setTypeAbbrList(String[] type_abbr_list1) {
        availableTypeAbbreviations = type_abbr_list1.clone();
        ArrayList<String> l_type_list = new ArrayList<>();
        for (String s : type_abbr_list1) {
            int i = 0;
            for (String t : Constants.TYPE_ABBREVIATIONS) {
                if (s.equals(t)) {
                    l_type_list.add(Constants.TYPES[i]);
                    break;
                }
                i++;
            }
        }
        availableTypes = new String[type_abbr_list1.length];
        int i = 0;
        for (Object j : l_type_list.toArray()) {
            availableTypes[i] = j.toString();
            i++;
        }
    }
}
