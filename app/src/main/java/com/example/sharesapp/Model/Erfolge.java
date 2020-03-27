package com.example.sharesapp.Model;

public class Erfolge {
    /**
    * Das ist eine Klasse, um die Erfolge zu speichern. Erfolge werden nie zurückgesetzt.
    * Die Klasse enthält binäre Masken um die Erfolge zu speichern, wenn bestimmte Parametern
    * Schwellwerte erreicht haben. So werden die Kauf- und Verkauferfolge eingeschaltet, wenn
    * die die Werte in tradesA Maske erreicht haben und Reseterfolge wenn dementsprechend
    * die Anzahl der Resets die Schwellwerte in resetA erreicht wurden.
    * Eine getrennte Erfolg All ist erst dann eingeschaltet, wenn man alle Kauf-, Verkauf- und Reseterfolge
    * erreicht hat.
    * */

    private static boolean[] kaufen = new boolean[4];
    private static boolean[] verkaufen = new boolean[4];
    private static boolean[] reset = new boolean[3];
//    private static boolean all;

    private final int[] tradesA = {1, 10, 100, 1000};
    private final int[] resetA = {1, 5, 10};

//    public Erfolge(boolean[] k, boolean[] v, boolean[] r) {
//        kaufen = k;
//        verkaufen = v;
//        reset = r;
//    }

    public Erfolge() {
        /**
        Leere Konstruktor
         */

    }

    public boolean[] getKaufen() {
        /**
        @return Maske mit erreichten Kauferfolgen zurück
         */
        this.checkKaufen();
        return kaufen;
    }

    private void checkKaufen() {
        /**
        Diese Methode füllt die Maske für Kauferfolge
         */
        int k = new Model().getData().getDepot().getKaufCounter();
        for (int i = 0; i < 4; i++) {
            if (k >= tradesA[i]) {
                kaufen[i] = true;
            } else {
                kaufen[i] = false;
            }
        }
    }

    public String[] getKaufenText() {
        /**
        @return Bedingungen, die nötig sind, um Kauferfolge zu bekommen
         */
        String[] s = {"Kaufe erste Aktie",
                "Kaufe erste 10 Aktien",
                "Kaufe erste 100 Aktien",
                "Kaufe erste 1000 Aktien"};
        return s;
    }

//    public void setKaufen(boolean[] k) {
//        kaufen = k;
//    }

    public boolean[] getVerkaufen() {
        /**
        @return Maske mit erreichten Verkauferfolgen zurück
         */
        this.checkVerkaufen();
        return verkaufen;
    }

    private void checkVerkaufen() {
        /**
        Diese Methode füllt die Maske für Verkauferfolge
         */
        int k = new Model().getData().getDepot().getVerkaufCounter();
        for (int i = 0; i < 4; i++) {
            if (k >= tradesA[i]) {
                verkaufen[i] = true;
            } else {
                verkaufen[i] = false;
            }
        }
    }

//    public void setVerkaufen(boolean[] v) {
//        verkaufen = v;
//    }

    public String[] getVerkaufenText() {
        /**
        @return Bedingungen, die nötig sind, um Verkauferfolge zu bekommen
         */
        String[] s = {"Verkaufe erste Aktie",
                "Verkaufe erste 10 Aktien",
                "Verkaufe erste 100 Aktien",
                "Verkaufe erste 1000 Aktien"};
        return s;
    }

    public boolean[] getReset() {
        /**
        @return Maske mit erreichten Reseterfolgen zurück
         */
        this.checkReset();
        return reset;
    }

    private void checkReset() {
        /**
        Diese Methode füllt die Maske für Reseterfolge
         */
        int k;

        try {
            k = new Model().getData().getResetCounter().getValue();
        } catch (NullPointerException e) {
            k = 0;
        }
        for (int i = 0; i < 3; i++) {
            if (k >= resetA[i]) {
                reset[i] = true;
            } else {
                reset[i] = false;
            }
        }
    }

//    public void setReset(boolean[] r) {
//        reset = r;
//    }

    public String[] getResetText() {
        /**
        @return Bedingungen, die nötig sind, um Reseterfolge zu bekommen
         */
        String[] s = {"Starte ein neues Spiel",
                "Fange neues ein Spiel 5 mal",
                "Fange neues ein Spiel 10 mal"};
        return s;
    }

    public boolean getAll() {
        /**
        @return ob alle Kauf-, Verkauf- und Reseterfolg gesammelt wurde
         */
        for (boolean b : kaufen) {
            if (!b) {
                return false;
            }
        }

        for (boolean b : verkaufen) {
            if (!b) {
                return false;
            }
        }

        for (boolean b : reset) {
            if (!b) {
                return false;
            }
        }

        return true;
    }

    public String getAllText() {
        /**
         * @return Textuelle Beschreibung für Sammlung von alle andere Erfolge
         */
        return "Bekomme alle Erfolge!";
    }


}
