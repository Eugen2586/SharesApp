package com.example.sharesapp.REST;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.YearMonth;
import java.util.List;

import pl.zankowski.iextrading4j.api.refdata.v1.ExchangeSymbol;
import pl.zankowski.iextrading4j.api.stats.HistoricalStats;
import pl.zankowski.iextrading4j.api.stocks.Quote;
import pl.zankowski.iextrading4j.client.IEXCloudClient;
import pl.zankowski.iextrading4j.client.IEXCloudTokenBuilder;
import pl.zankowski.iextrading4j.client.IEXTradingApiVersion;
import pl.zankowski.iextrading4j.client.IEXTradingClient;
import pl.zankowski.iextrading4j.client.rest.request.refdata.v1.SymbolsRequestBuilder;
import pl.zankowski.iextrading4j.client.rest.request.stats.HistoricalStatsRequestBuilder;
import pl.zankowski.iextrading4j.client.rest.request.stocks.LogoRequestBuilder;
import pl.zankowski.iextrading4j.client.rest.request.stocks.QuoteRequestBuilder;

public class Requests {

    static IEXCloudClient cloudClient;

    public Requests() {
        if (cloudClient == null) {
            cloudClient = IEXTradingClient.create(IEXTradingApiVersion.IEX_CLOUD_V1_SANDBOX,
                    new IEXCloudTokenBuilder()
                            .withPublishableToken("Tpk_f10f1ddb8a1d4baaa44d427f0ddbea19")
                            .withSecretToken("pk_f34e324d858241608b277acef9ba5faf")
                            .build());
        }
    }

    public Quote getQuote(String symbol) {
        return cloudClient.executeRequest(new QuoteRequestBuilder()
                .withSymbol(symbol)
                .build());
    }

    public List<ExchangeSymbol> getSymbols() {
        return cloudClient.executeRequest(new SymbolsRequestBuilder()
                .build());
    }

    public String getLogoURL(String symbol) {
        return cloudClient.executeRequest(new LogoRequestBuilder()
                .withSymbol(symbol)
                .build()).getUrl();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public List<HistoricalStats> getHistoricalStats() {
        return cloudClient.executeRequest(new HistoricalStatsRequestBuilder()
                .withDate(YearMonth.of(2017, 5))
                .build());
    }
}
