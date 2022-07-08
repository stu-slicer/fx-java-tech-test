package org.example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PriceSubscriberImpl implements PriceSubscriberInterface {

    private static final double BID_COMMISSION_PCT = 0.001;
    private static final double ASK_COMMISSION_PCT = 0.001;

    private CSVToPriceTransformer priceTransformer = new CSVToPriceTransformer();

    private Map<String, Price> priceMap = new HashMap<>();

    @Override
    public void onMessage(String message) {

        List<Price> prices = priceTransformer.transform(message);

        for (Price price : prices) {

            // add in commission
            price = calculateCommission(price);

            // store the prices
            priceMap.put( price.getInstrumentName(), price);
        }

    }

    private Price calculateCommission(Price price) {

        // calculate bid commission
        double bidCommission = Math.round( price.getBid() * BID_COMMISSION_PCT * 100d ) / 100d;
        double askCommission = Math.round( price.getAsk() * ASK_COMMISSION_PCT * 100d ) / 100d;

        price.setBid( price.getBid() - bidCommission );
        price.setAsk( price.getAsk() + askCommission );

        return price;
    }

    // /exchange-rates/latest/EUR/JPY
    public Price getLatestPrice(String fromCurrency, String toCurrency) {
        String instrumentName = fromCurrency.toUpperCase() + "/" + toCurrency.toUpperCase();
        return this.priceMap.get( instrumentName );
    }

    public static void main(String[] args) {
    }

}
