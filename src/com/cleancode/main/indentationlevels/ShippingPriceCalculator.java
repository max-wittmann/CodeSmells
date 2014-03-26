package com.cleancode.main.indentationlevels;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
* Created by mwittman on 25/03/2014.
*/
public class ShippingPriceCalculator {

//    Collection<ShippingPrice> totalShippingPrices;

    private Map <ShippingDetails.Type, Map <ShippingDetails.Area, Integer>>totalPricings = new HashMap<ShippingDetails.Type, Map <ShippingDetails.Area, Integer>>();
    private Map <ShippingDetails.Type, Map <ShippingDetails.Area, Float>> perItemPercentagePricings = new HashMap<>();
    private Map<ShippingDetails.Type, Map<ShippingDetails.Area, Integer>> perItemBasePricings = new HashMap<>();

    public ShippingPriceCalculator() {
        initWithStandardPrices();
    }

//    private static final float EMPLOYEE_DISCOUNT = 0.2f;
//    private static final float GST = 0.15f;
//    private static final int METRO_FREE_DELIVERY_MIN_AMOUNT = 300;
//    private static final int METRO_STANDARD_DELIVERY_COST = 30;
//    private static final int URBAN_STANDARD_DELIVERY_COST = 45;
//    private static final int RURAL_STANDARD_DELIVERY_COST = 65;
//    private static final int METRO_URBAN_EXPRESS_DELIVERY_COST = 75;
//    private static final int NIL_AMOUNT = 0;
    private void initWithStandardPrices() {
        initDefaultTotalPricing();

        //Per Item
        //Price Per Item (Normal / Express)
        //Metro: 30% | 50%  + 15 / 30
        //Urban: 45% | 65% + 20 / 35
        //Rural: 65% | 85% + 40 / 60

        initPerUnitAdditionalPercentageCostPricing();

        Map<ShippingDetails.Area, Integer> pricings;
        pricings = new HashMap<>();
        pricings.put(ShippingDetails.Area.METRO, 15);
        pricings.put(ShippingDetails.Area.URBAN, 20);
        pricings.put(ShippingDetails.Area.RURAL, 40);
        perItemBasePricings.put(ShippingDetails.Type.NORMAL, pricings);

        pricings = new HashMap <>();
        pricings.put(ShippingDetails.Area.METRO, 30);
        pricings.put(ShippingDetails.Area.URBAN, 35);
        pricings.put(ShippingDetails.Area.RURAL, 60);
        perItemBasePricings.put(ShippingDetails.Type.EXPRESS, pricings);
    }

    private void initPerUnitAdditionalPercentageCostPricing() {
        Map<ShippingDetails.Area, Float> pricings;
        pricings = new HashMap<>();
        pricings.put(ShippingDetails.Area.METRO, 0.3f);
        pricings.put(ShippingDetails.Area.URBAN, 0.45f);
        pricings.put(ShippingDetails.Area.RURAL, 0.65f);
        perItemPercentagePricings.put(ShippingDetails.Type.NORMAL, pricings);

        pricings = new HashMap <>();
        pricings.put(ShippingDetails.Area.METRO, 0.5f);
        pricings.put(ShippingDetails.Area.URBAN, 0.65f);
        pricings.put(ShippingDetails.Area.RURAL, 0.85f);
        perItemPercentagePricings.put(ShippingDetails.Type.EXPRESS, pricings);
    }

    private void initDefaultTotalPricing() {
        //Total
        Map<ShippingDetails.Area, Integer> pricings = new HashMap<>();
        pricings.put(ShippingDetails.Area.METRO, 35);
        pricings.put(ShippingDetails.Area.URBAN, 45);
        pricings.put(ShippingDetails.Area.RURAL, 65);
        totalPricings.put(ShippingDetails.Type.NORMAL, pricings);

        pricings = new HashMap <>();
        pricings.put(ShippingDetails.Area.METRO, 75);
        pricings.put(ShippingDetails.Area.URBAN, 75);
        totalPricings.put(ShippingDetails.Type.EXPRESS, pricings);
    }

    public int calculateShippingPrice(ShippingDetails details, Collection <Integer> itemPrices) {
        if(details.per == ShippingDetails.Per.TOTAL)
        {
            return calculatePriceForTotal(details, itemPrices);
        }
        else {
            return calculatePricePerItem(details, itemPrices);
        }
    }

    private int calculatePricePerItem(ShippingDetails details, Collection<Integer> itemPrices) {
        int totalShipping = 0;
        for(Integer itemPrice : itemPrices) {
            totalShipping += calculatePriceForItem(details, itemPrice);
        }
        totalShipping += perItemBasePricings.get(details.type).get(details.area);
        return totalShipping;
    }

    private int calculatePriceForItem(ShippingDetails details, Integer itemPrice) {
        return (int)(perItemPercentagePricings.get(details.type).get(details.area) * itemPrice);
    }

    private int calculatePriceForTotal(ShippingDetails details, Collection<Integer> itemPrices) {
        if(isQualifiesForFreeShipping(details, itemPrices)) {
            return 0;
        }
        return totalPricings.get(details.type).get(details.area);
    }

    private boolean isQualifiesForFreeShipping(ShippingDetails details, Collection<Integer> itemPrices) {
        return CollectionUtil.calculateSum(itemPrices) >= 300 &&
                (details.area == ShippingDetails.Area.URBAN || details.area == ShippingDetails.Area.RURAL);
    }
}