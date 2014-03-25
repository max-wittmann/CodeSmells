package com.cleancode.main.indentationlevels.implementations;

import com.cleancode.main.indentationlevels.ShippingDetails;

import java.util.ArrayList;
import java.util.Collection;

/**
* Created by mwittman on 25/03/2014.
*/
public class ShippingPriceCalculator {

    Collection<ShippingPrice> shippingPrices;

    public ShippingPriceCalculator(Collection<ShippingPrice> shippingPrices) {
        this.shippingPrices = shippingPrices;
    }

    public int calculateShippingPrice(ShippingDetails details, int total) {
        for(ShippingPrice price : shippingPrices) {
            if(price.isCorrectPrice(details, total))
                return price.getPrice();
        }
        throw new RuntimeException("Could not calculate price for " + details);
    }

    public static ShippingPriceCalculator createStandardShippingPriceCalculator() {
        Collection <ShippingPrice> shippingPrices = new ArrayList<ShippingPrice>();
        shippingPrices.add(new ExpressShippingPrice());
        shippingPrices.add(new FreeMetroShippingPrice());
        shippingPrices.add(new NormalMetroShippingPrice());
        shippingPrices.add(new NormalUrbanShippingPrice());
        shippingPrices.add(new NormalRuralShippingPrice());
        return new ShippingPriceCalculator(shippingPrices);
    }

    public static class ExpressShippingPrice implements ShippingPrice {

        @Override
        public boolean isCorrectPrice(ShippingDetails details, int total) {
            if(details.type != ShippingDetails.Type.EXPRESS)
                return false;
            if(details.area == ShippingDetails.Area.RURAL)
                throw new RuntimeException("Can't express ship to Rural");
            return true;
        }

        @Override
        public int getPrice() {
            return 75;
        }
    }

    public static class NormalMetroShippingPrice implements ShippingPrice {

        @Override
        public boolean isCorrectPrice(ShippingDetails details, int total) {
            if(details.type != ShippingDetails.Type.NORMAL)
                return false;
            if(details.area != ShippingDetails.Area.METRO)
                return false;
            return true;
        }

        @Override
        public int getPrice() {
            return 30;
        }
    }

    public static class NormalUrbanShippingPrice implements ShippingPrice {

        @Override
        public boolean isCorrectPrice(ShippingDetails details, int total) {
            if(details.type != ShippingDetails.Type.NORMAL)
                return false;
            if(details.area != ShippingDetails.Area.URBAN)
                return false;
            return true;
        }

        @Override
        public int getPrice() {
            return 45;
        }
    }

    public static class NormalRuralShippingPrice implements ShippingPrice {

        @Override
        public boolean isCorrectPrice(ShippingDetails details, int total) {
            if(details.type != ShippingDetails.Type.NORMAL)
                return false;
            if(details.area != ShippingDetails.Area.RURAL)
                return false;
            return true;
        }

        @Override
        public int getPrice() {
            return 65;
        }
    }

    public static class FreeMetroShippingPrice implements ShippingPrice {

        @Override
        public boolean isCorrectPrice(ShippingDetails details, int total) {
            if(details.type != ShippingDetails.Type.NORMAL)
                return false;
            if(details.area != ShippingDetails.Area.METRO)
                return false;
            if(total < 300)
                return false;
            return true;
        }

        @Override
        public int getPrice() {
            return 0;
        }
    }
}
