package com.cleancode.main.indentationlevels.implementations;

import com.cleancode.main.indentationlevels.ShippingDetails;

/**
* Created by mwittman on 25/03/2014.
*/
public interface ShippingPrice {
    public boolean isCorrectPrice(ShippingDetails details, int total);
    public int getPrice();
    //    private static final int METRO_FREE_DELIVERY_MIN_AMOUNT = 300;
//    private static final int METRO_STANDARD_DELIVERY_COST = 30;
//    private static final int URBAN_STANDARD_DELIVERY_COST = 45;
//    private static final int RURAL_STANDARD_DELIVERY_COST = 65;
//    private static final int METRO_URBAN_EXPRESS_DELIVERY_COST = 75;

}
