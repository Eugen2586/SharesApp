package com.example.sharesapp;

import com.example.sharesapp.FunktionaleKlassen.JSON.ToModel.RequestHistoricalQuotePrices;
import com.example.sharesapp.FunktionaleKlassen.JSON.ToModel.RequestSymbol;
import com.example.sharesapp.FunktionaleKlassen.JSON.ToModel.RequestSearchURL;
import com.example.sharesapp.FunktionaleKlassen.JSON.ToModel.RequestTimeSeriesURL;
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
     public void testTimeSeriesRequest(){
         Requests req = new Requests();
        //ToDo Datenmapping ist momentan noch nicht relevant!
        String s = null;
        try {
            s =  req.run(RequestsBuilder.getTimeSeriesURL("PPL"));
            RequestTimeSeriesURL regs = new RequestTimeSeriesURL(s);

        } catch (Exception e) {
            e.printStackTrace();
        }
        if ( s != null) {
            System.out.print(s);
        }
    }

    @Test
    public void RequestSearchURL(){
        //ToDO Dieser Request füllt nicht das Model!
        Requests req = new Requests();
        String s = null;
        try {
            s =  req.run(RequestsBuilder.getSearchURL("APPLE"));
            RequestSearchURL regs = new RequestSearchURL(s);
            ArrayList st = regs.getURLS();

        } catch (Exception e) {
            e.printStackTrace();
        }
        if ( s != null) {
            System.out.print(s);
        }
    }

    @Test
    public void RequestHistory(){
        //ToDO Dieser Request füllt nicht das Model!
        Requests req = new Requests();
        String s = null;
        try {
            s =  req.run(RequestsBuilder.getHistoricalQuotePrices("APPL", Range.oneMonth));
            RequestHistoricalQuotePrices regs = new RequestHistoricalQuotePrices(s);
            ArrayList st = regs.getURLS();

        } catch (Exception e) {
            e.printStackTrace();
        }
        if ( s != null) {
            System.out.print(s);
        }
    }


    @Test
    public void testHistorical() {
        Requests req = new Requests();
        try {
             String s = req.run(RequestsBuilder.getHistoricalQuotePrices("AAPL", Range.oneMonth));
             System.out.println(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}