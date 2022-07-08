package org.example;

public class CSVPriceException extends Exception {

    public CSVPriceException() {
        super();
    }

    public CSVPriceException(String message) {
        super(message);
    }

    public CSVPriceException(String message, Throwable cause) {
        super(message, cause);
    }
}
