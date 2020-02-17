package com.example.sharesapp.REST;

import pl.zankowski.iextrading4j.api.stocks.Quote;
import pl.zankowski.iextrading4j.client.IEXCloudClient;
import pl.zankowski.iextrading4j.client.IEXCloudTokenBuilder;
import pl.zankowski.iextrading4j.client.IEXTradingApiVersion;
import pl.zankowski.iextrading4j.client.IEXTradingClient;
import pl.zankowski.iextrading4j.client.rest.request.stocks.QuoteRequestBuilder;

public class Requests {

    static IEXCloudClient cloudClient;

    public Requests() {
        if (cloudClient == null) {
            cloudClient = IEXTradingClient.create(IEXTradingApiVersion.IEX_CLOUD_BETA_SANDBOX,
                    new IEXCloudTokenBuilder()
                            .withPublishableToken("Tpk_f10f1ddb8a1d4baaa44d427f0ddbea19")
                            .withSecretToken("pk_f34e324d858241608b277acef9ba5faf")
                            .build());
        }
    }

    public Quote getQuote() {
        return cloudClient.executeRequest(new QuoteRequestBuilder()
                .withSymbol("AAPL")
                .build());
    }
}
