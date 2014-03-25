package com.cleancode.main.indentationlevels;

import com.cleancode.main.indentationlevels.implementations.PriceCalculator_Good;

import java.util.HashMap;
import java.util.Map;

/**
* Created by mwittman on 25/03/2014.
*/
public class Products {
    Map<String, Integer> productPrices = new HashMap<>();

    public void addProduct(String productName, int productPrice) {
        productPrices.put(productName, productPrice);
    }

    public int getUnitPrice(String productName) {
        if(!productPrices.containsKey(productName))
        {
            throw new NoSuchProductException(productName);
        }
        return productPrices.get(productName);
    }
}
