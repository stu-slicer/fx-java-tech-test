package org.example;

import java.util.ArrayList;
import java.util.List;

public class CSVToPriceTransformer {

    public List<Price> transform(String csvMessages) {
        String[] lines = csvMessages.split("\n"); // \n newline character
        List<Price> prices = new ArrayList<>();

        for (String line : lines) {
            try {
                Price price  = transformLine(line);
                prices.add( price );
            } catch (CSVPriceException e) {
                System.err.println("Exception thrown! " + e.getMessage() );
            }
        }

        return prices;
    }

    private Price transformLine(String csvMessage) throws CSVPriceException {

        //106, EUR/USD, 1.1000,1.2000,01-06-2020 12:01:01:001
        String[] split = csvMessage.split(",");

        if( split.length != 5 ) {
            throw new CSVPriceException("Not enough items in line: " + csvMessage);
        }

        try {
            int id = Integer.parseInt(split[0].trim());
            String instrumentName = split[1].trim();
            double bid = Double.parseDouble(split[2].trim());
            double ask = Double.parseDouble(split[3].trim());
            String timestamp = split[4].trim();

            Price price = new Price();
            price.setId(id);
            price.setInstrumentName(instrumentName);
            price.setBid(bid);
            price.setAsk(ask);
            price.setTimestamp(timestamp);

            return price;
        } catch (Exception e) {
            throw new CSVPriceException("Exception transforming line: " + csvMessage, e );
        }


    }

}
