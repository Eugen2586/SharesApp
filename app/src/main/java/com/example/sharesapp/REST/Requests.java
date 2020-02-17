package com.example.sharesapp.REST;

public class Requests {

    public Requests() {
        final IEXCloudClient cloudClient = IEXTradingClient.create(IEXTradingApiVersion.IEX_CLOUD_BETA_SANDBOX,
                new IEXCloudTokenBuilder()
                        .withPublishableToken("Tpk_f10f1ddb8a1d4baaa44d427f0ddbea19")
                        .withSecretToken("pk_f34e324d858241608b277acef9ba5faf")
                        .build());
    }
}
