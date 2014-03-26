package com.cleancode.main.indentationlevels.implementations;

import com.cleancode.main.indentationlevels.*;

import java.util.*;

public class PriceCalculator_Good implements PriceCalculator {

    private static final float EMPLOYEE_DISCOUNT = 0.2f;
    private static final float GST = 0.15f;

    Map<String, ProductPrices> priceForProduct = new HashMap<>();
    Map<String, Float> discountForProduct = new HashMap<>();
    Map<String, Integer> minPurchaseForDiscount = new HashMap<>();
    ShippingPriceCalculator shippingPriceCalculator = new ShippingPriceCalculator();

    public PriceCalculator_Good(
            Map<String, ProductPrices> priceForProduct,
            Map<String, Float> discountForProduct,
            Map<String, Integer> minPurchaseForDiscount
    ) {
        this.priceForProduct = priceForProduct;
        this.discountForProduct = discountForProduct;
        this.minPurchaseForDiscount = minPurchaseForDiscount;
    }

    public int calculateTotalPrice(Order order) {
        if(order == null)
            return 0;

        List <Integer> itemPrices = new ArrayList <>();
        for(OrderItem orderItem : order.cart) {
            int itemCost = calculateTotalPriceForOrderItem(order, orderItem);
            itemPrices.add(itemCost);
        }
        int total = CollectionUtil.calculateSum(itemPrices);

        total += shippingPriceCalculator.calculateShippingPrice(order.shippingDetails, itemPrices);
        return total;
    }

    private int calculateTotalPriceForOrderItem(Order order, OrderItem orderItem) {
        int total = getPriceForOrderItem(orderItem);
        if(!order.isGSTExcempt)
            total = applyGST(total);
        if(order.isEmployee)
            total = applyEmployeeDiscount(total);
        return total;
    }

    private int getPriceForOrderItem(OrderItem orderItem) {
        if(orderItem.quantity >= 6) {
            return getPriceForMoreThanSixItems(orderItem);
        }
        else {
            return orderItem.quantity * priceForProduct.get(orderItem.productName).pricePerUnit;
        }
    }

    private int getPriceForMoreThanSixItems(OrderItem orderItem) {
        int pricePerSix = priceForProduct.get(orderItem.productName).pricePerSix;
        int pricePerUnitOverSix = priceForProduct.get(orderItem.productName).pricePerUnitOverSix;
        return orderItem.quantity % 6 * pricePerUnitOverSix + orderItem.quantity / 6 * pricePerSix;
    }

    private int applyEmployeeDiscount(int total) {
        return (int)(total * (1 - EMPLOYEE_DISCOUNT));
    }

    private int applyGST(int total) {
        return (int)(total * (1 + GST));
    }
}