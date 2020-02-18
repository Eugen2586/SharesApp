package com.example.sharesapp;

import com.example.sharesapp.REST.*;

import org.junit.Test;


import java.io.IOException;

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
        Requests requests = new Requests();
        String quote = null;
        try {
            quote = requests.run("time-series/REPORTED_FINANCIALS/AAPL");
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.print(quote);
    }

    @Test
    public void testHistorical() {
    }
}