package com.cleancode.main.indentationlevels;

import java.util.HashMap;
import java.util.Map;

/**
* Created by mwittman on 25/03/2014.
*/
public class Discounts {
    Map<String, Float> percentageDiscountForProduct = new HashMap<>();
    Map <String, Integer> minAmountOfProductForDiscount = new HashMap <>();

    public void addDiscount(String productName, int minQuantity, float discountAmount) {
        percentageDiscountForProduct.put(productName, discountAmount);
        minAmountOfProductForDiscount.put(productName, minQuantity);
    }

    public float getDiscountPercentage(OrderItem orderItem) {
        if(!percentageDiscountForProduct.containsKey(orderItem.productName) ||
                orderItem.quantity < minAmountOfProductForDiscount.get(orderItem.productName)) {
            return 0.0f;
        }
        return percentageDiscountForProduct.get(orderItem.productName);
    }
}
