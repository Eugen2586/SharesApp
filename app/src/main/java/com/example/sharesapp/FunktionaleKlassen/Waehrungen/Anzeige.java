package com.example.sharesapp.FunktionaleKlassen.Waehrungen;

public class Anzeige {

    public String makeItBeautiful(float num) {
        String s;
        s = String.valueOf(num) + "0";
        System.out.println(s);
        int i = s.indexOf(".");
        System.out.println(i);
        s = s.substring(0, i + 3);
        return s;
    }

    public String makeItBeautifulEuro(float num) {
        return (makeItBeautiful(num) + "€");
    }

}
