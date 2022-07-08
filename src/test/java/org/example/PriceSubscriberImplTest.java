package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PriceSubscriberImplTest {

    public static final String VALID_PRICE = "107, EUR/JPY, 119.60,119.90,01-06-2020 12:01:02:002";
    public static final String SECOND_VALID_PRICE = "110, EUR/JPY, 119.61,119.91,01-06-2020 12:01:02:110";
    public static final String INVALID_PRICE = "108, EXTRA, EUR/JPY, 119.60,119.90,01-06-2020 12:01:02:002";
    public static final String EUR_CURRENCY = "EUR";
    public static final String JPY_CURRENCY = "JPY";

    private PriceSubscriberImpl target;

    @BeforeEach
    void setup() {
        target = new PriceSubscriberImpl();
    }

    @Test
    void getLatestPrice_ValidPrice_ReturnsLatestPrice() {

        // prepare
        // call onMessage
        target.onMessage( VALID_PRICE );

        // verify
        Price result = target.getLatestPrice(EUR_CURRENCY, JPY_CURRENCY);

        assertNotNull( result, "Latest price should be not null!" );

        assertEquals( 107, result.getId() );
        assertEquals( EUR_CURRENCY + "/" + JPY_CURRENCY, result.getInstrumentName() );
        assertEquals( 119.48, result.getBid(), 0.001 );
        assertEquals( 120.02, result.getAsk(), 0.001 );


    }

    @Test
    void getLatestPrice_ValidPrices_ReturnsLatestPrice() {

        // prepare
        // call onMessage
        target.onMessage( VALID_PRICE );
        target.onMessage( SECOND_VALID_PRICE );

        // verify
        Price result = target.getLatestPrice(EUR_CURRENCY, JPY_CURRENCY);

        assertNotNull( result, "Latest price should be not null!" );

        assertEquals( 110, result.getId() );
        assertEquals( EUR_CURRENCY + "/" + JPY_CURRENCY, result.getInstrumentName() );
        assertEquals( 119.49, result.getBid(), 0.001 );
        assertEquals( 120.03, result.getAsk(), 0.001 );


    }

    @Test
    void getLatestPrice_InvalidPrice_ReturnsNull() {

        // prepare
        // call onMessage
        target.onMessage( INVALID_PRICE );

        // verify
        Price result = target.getLatestPrice(EUR_CURRENCY, JPY_CURRENCY);

        assertNull( result, "Latest price should be null!" );

    }

    @Test
    void getLatestPrice_NoPrice_ReturnsNull() {

        // prepare
        // don't add any price

        // verify
        Price result = target.getLatestPrice(EUR_CURRENCY, JPY_CURRENCY);

        assertNull( result, "Latest price should be null!" );

    }
}