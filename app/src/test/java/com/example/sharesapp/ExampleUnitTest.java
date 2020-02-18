package com.example.sharesapp;

import com.example.sharesapp.FunktionaleKlassen.JSON.ToModel.RequestSymbol;
import com.example.sharesapp.Model.Aktie;
import com.example.sharesapp.REST.*;

import org.junit.Test;


import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void testQuote() {
        Requests req = new Requests();
        // Loads all Symobols TODO ins Datenmodell
        String s = null;
        try {
           s =  req.run(RequestsBuilder.getAllSymbolsURL());
           RequestSymbol regs = new RequestSymbol(s);
           ArrayList a = regs.getAk();
           System.out.print("erreicht!");

        } catch (Exception e) {
            e.printStackTrace();
        }
        if ( s != null) {
            System.out.print(s);
        }
    }

    @Test
    public void testHistorical() {
    }
}