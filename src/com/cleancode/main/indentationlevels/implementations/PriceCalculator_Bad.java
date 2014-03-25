package com.cleancode.main.indentationlevels.implementations;

import com.cleancode.main.indentationlevels.Order;
import com.cleancode.main.indentationlevels.OrderItem;
import com.cleancode.main.indentationlevels.PriceCalculator;
import com.cleancode.main.indentationlevels.ProductPrices;

import java.util.HashMap;
import java.util.Map;

import static com.cleancode.main.indentationlevels.ShippingDetails.Area.METRO;
import static com.cleancode.main.indentationlevels.ShippingDetails.Area.URBAN;
import static com.cleancode.main.indentationlevels.ShippingDetails.Type.EXPRESS;

public class PriceCalculator_Bad implements PriceCalculator {

    private static final float EMPLOYEE_DISCOUNT = 0.2f;
    private static final float GST = 0.15f;
    private static final int METRO_FREE_DELIVERY_MIN_AMOUNT = 300;
    private static final int METRO_STANDARD_DELIVERY_COST = 30;
    private static final int URBAN_STANDARD_DELIVERY_COST = 45;
    private static final int RURAL_STANDARD_DELIVERY_COST = 65;
    private static final int METRO_URBAN_EXPRESS_DELIVERY_COST = 75;
    private static final int NIL_AMOUNT = 0;

    Map<String, ProductPrices> priceForProduct = new HashMap<>();
    Map<String, Float> discountForProduct = new HashMap<>();
    Map<String, Integer> minPurchaseForDiscount = new HashMap<>();

    public PriceCalculator_Bad(
            Map<String, ProductPrices> priceForProduct,
            Map<String, Float> discountForProduct,
            Map<String, Integer> minPurchaseForDiscount
    ) {
        this.priceForProduct = priceForProduct;
        this.discountForProduct = discountForProduct;
        this.minPurchaseForDiscount = minPurchaseForDiscount;
    }

    public int calculateTotalPrice(Order order) {
        int total = 0;
        for(OrderItem orderItem : order.cart) {
            int result;
            if(orderItem.quantity >= 6) {
                int pricePerSix = priceForProduct.get(orderItem.productName).pricePerSix;
                int pricePerUnitOverSix = priceForProduct.get(orderItem.productName).pricePerUnitOverSix;
                result = orderItem.quantity % 6 * pricePerUnitOverSix + orderItem.quantity / 6 * pricePerSix;
                if(!order.isGSTExcempt)
                    result = (int) (result * (1 + GST));
                if(order.isEmployee)
                    result = (int) (result * (1 - EMPLOYEE_DISCOUNT));
            }
            else {
                result = orderItem.quantity * priceForProduct.get(orderItem.productName).pricePerUnit;
                if(!order.isGSTExcempt)
                    result = (int) (result * (1 + GST));
                if(order.isEmployee)
                    result = (int) (result * (1 - EMPLOYEE_DISCOUNT));
            }
            total += result;
        }
        if(order.shippingDetails.type != EXPRESS) {
            if(order.shippingDetails.area == METRO) {
                if(total > METRO_FREE_DELIVERY_MIN_AMOUNT) {
                    total += NIL_AMOUNT;
                }
                else {
                    total += METRO_STANDARD_DELIVERY_COST;
                }
            }
            else if(order.shippingDetails.area == URBAN) {
                total += URBAN_STANDARD_DELIVERY_COST;
            }
            else {
                total += RURAL_STANDARD_DELIVERY_COST;
            }
        }
        else {
            if(order.shippingDetails.area == METRO || order.shippingDetails.area == URBAN)
                total += METRO_URBAN_EXPRESS_DELIVERY_COST;
            else
                throw new RuntimeException("Cannot Deliver Express to Rural Areas");
        }
        return total;
    }

}