package com.cleancode.main.indentationlevels;

/**
* Created by mwittman on 25/03/2014.
*/
public class ProductPrices {
    public final int pricePerUnit;
    public final int pricePerSix;
    public final int pricePerUnitOverSix;

    public ProductPrices(int pricePerUnit, int pricePerSix, int pricePerUnitOverSix) {
        this.pricePerSix = pricePerSix;
        this.pricePerUnit = pricePerUnit;
        this.pricePerUnitOverSix = pricePerUnitOverSix;
    }
}
