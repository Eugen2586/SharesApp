package com.example.sharesapp;

import com.example.sharesapp.REST.*;

import org.junit.Test;

import pl.zankowski.iextrading4j.api.stocks.Quote;

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
        Quote quote = requests.getQuote("AAPL");
        System.out.print(quote);
    }

}