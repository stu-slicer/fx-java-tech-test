package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CSVToPriceTransformerTest {

    private CSVToPriceTransformer target;

    @BeforeEach
    void setUp() {
        target = new CSVToPriceTransformer();

    }

    @Test
    void transform_ValidInput_ReturnsPrice() {

        List<Price> result = target.transform("107, EUR/JPY, 119.60,119.90,01-06-2020 12:01:02:002");

        System.out.println(result);

//        assertTrue(  );
        assertEquals( 1, result.size() );
        assertEquals( 107, result.get(0).getId() );

    }

    @Test
    void transform_ValidInput_ReturnsPrices() {

        List<Price> result = target.transform("107, EUR/JPY, 119.60,119.90,01-06-2020 12:01:02:002\n" +
                "108, GBP/USD, 1.2500,1.2560,01-06-2020 12:01:02:002");

        System.out.println(result);

//        assertTrue(  );
        assertNotNull( result );
        assertEquals( 2, result.size() );
        assertEquals( 107, result.get(0).getId() );

    }
}