package com.cleancode.main.indentationlevels;

/**
* Created by mwittman on 25/03/2014.
*/
public class OrderItem {
    public OrderItem(String productName, int quantity) {
        this.productName = productName;
        this.quantity = quantity;
    }

    public int quantity;
    public String productName;
}
