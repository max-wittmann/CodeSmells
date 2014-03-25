package com.cleancode.main.indentationlevels;

/**
* Created by mwittman on 25/03/2014.
*/
public class NoSuchProductException extends RuntimeException {
    public NoSuchProductException(String productName) {
        super("No such product: " + productName);
    }
}
