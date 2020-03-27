package com.example.sharesapp.Model.FromServerClasses;

public class SearchRequest {
    private String symbol;
//    private String securityName;
//    private String securityType;
//    private String region;
//    private String exchange;

    public String getSymbol() {
        return symbol;
    }

//    public String getSecurityName() {
//        return securityName;
//    }
//
//    public String getSecurityType() {
//        return securityType;
//    }
//
//    public String getRegion() {
//        return region;
//    }
//
//    public String getExchange() {
//        return exchange;
//    }

    public SearchRequest(String symbol, String securityName, String securityType, String region, String exchange) {
        this.symbol = symbol;
//        this.securityName = securityName;
//        this.securityType = securityType;
//        this.region = region;
//        this.exchange = exchange;
    }

}
