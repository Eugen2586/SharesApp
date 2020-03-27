package com.example.sharesapp.Model;

/**
 * Das ist eine Klasse, um die Erfolge zu speichern. Erfolge werden nie zurückgesetzt.
 * Die Klasse enthält binäre Masken um die Erfolge zu speichern, wenn bestimmte Parametern
 * Schwellwerte erreicht haben. So werden die Kauf- und Verkauferfolge eingeschaltet, wenn
 * die die Werte in tradesA Maske erreicht haben und Reseterfolge wenn dementsprechend
 * die Anzahl der Resets die Schwellwerte in resetA erreicht wurden.
 * Eine getrennte Erfolg All ist erst dann eingeschaltet, wenn man alle Kauf-, Verkauf- und Reseterfolge
 * erreicht hat.
 * */
public class Erfolge {

    private static boolean[] kaufen = new boolean[4];
    private static boolean[] verkaufen = new boolean[4];
    private static boolean[] reset = new boolean[3];

    private final int[] tradesA = {1, 10, 100, 1000};
    private final int[] resetA = {1, 5, 10};

    /**
     Leere Konstruktor
     */
    public Erfolge() {

    }

    /**
     @return Maske mit erreichten Kauferfolgen zurück
     */
    public boolean[] getKaufen() {
        this.checkKaufen();
        return kaufen;
    }

    /**
     Diese Methode füllt die Maske für Kauferfolge
     */
    private void checkKaufen() {
        int k = new Model().getData().getDepot().getKaufCounter();
        for (int i = 0; i < 4; i++) {
            if (k >= tradesA[i]) {
                kaufen[i] = true;
            } else {
                kaufen[i] = false;
            }
        }
    }

    /**
     @return Bedingungen, die nötig sind, um Kauferfolge zu bekommen
     */
    public String[] getKaufenText() {
        String[] s = {"Kaufe erste Aktie",
                "Kaufe erste 10 Aktien",
                "Kaufe erste 100 Aktien",
                "Kaufe erste 1000 Aktien"};
        return s;
    }

    /**
     @return Maske mit erreichten Verkauferfolgen zurück
     */
    public boolean[] getVerkaufen() {
        this.checkVerkaufen();
        return verkaufen;
    }

    /**
     Diese Methode füllt die Maske für Verkauferfolge
     */
    private void checkVerkaufen() {
        int k = new Model().getData().getDepot().getVerkaufCounter();
        for (int i = 0; i < 4; i++) {
            if (k >= tradesA[i]) {
                verkaufen[i] = true;
            } else {
                verkaufen[i] = false;
            }
        }
    }

    /**
     @return Bedingungen, die nötig sind, um Verkauferfolge zu bekommen
     */
    public String[] getVerkaufenText() {
        String[] s = {"Verkaufe erste Aktie",
                "Verkaufe erste 10 Aktien",
                "Verkaufe erste 100 Aktien",
                "Verkaufe erste 1000 Aktien"};
        return s;
    }

    /**
     @return Maske mit erreichten Reseterfolgen zurück
     */
    public boolean[] getReset() {
        this.checkReset();
        return reset;
    }

    /**
     Diese Methode füllt die Maske für Reseterfolge
     */
    private void checkReset() {
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

    /**
     @return Bedingungen, die nötig sind, um Reseterfolge zu bekommen
     */
    public String[] getResetText() {
        String[] s = {"Starte ein neues Spiel",
                "Fange neues ein Spiel 5 mal",
                "Fange neues ein Spiel 10 mal"};
        return s;
    }

    /**
     @return ob alle Kauf-, Verkauf- und Reseterfolg gesammelt wurde
     */
    public boolean getAll() {
        for (boolean b: kaufen) {
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

    /**
     * @return Textuelle Beschreibung für Sammlung von alle andere Erfolge
     */
    public String getAllText() {
        return "Bekomme alle Erfolge!";
    }


}
