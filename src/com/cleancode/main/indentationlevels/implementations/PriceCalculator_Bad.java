package com.cleancode.main.indentationlevels.implementations;

import com.cleancode.main.indentationlevels.Order;
import com.cleancode.main.indentationlevels.OrderItem;
import com.cleancode.main.indentationlevels.ProductPrices;
import com.cleancode.main.indentationlevels.ShippingDetails;

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

    private static final float PERUNIT_METRO_STANDARD_DELIVERY_COST_PERC = 1.3f;
    private static final float PERUNIT_RURAL_STANDARD_DELIVERY_COST_PERC = 1.65f;
    private static final float PERUNIT_URBAN_STANDARD_DELIVERY_COST_PERC = 1.45f;

    private static final int PERUNIT_METRO_STANDARD_DELIVERY_COST_BASE = 15;
    private static final int PERUNIT_RURAL_STANDARD_DELIVERY_COST_BASE = 40;
    private static final int PERUNIT_URBAN_STANDARD_DELIVERY_COST_BASE = 20;

    private static final float PERUNIT_METRO_EXPRESS_DELIVERY_COST_PERC = 1.5f;
    private static final float PERUNIT_RURAL_EXPRESS_DELIVERY_COST_PERC = 1.85f;
    private static final float PERUNIT_URBAN_EXPRESS_DELIVERY_COST_PERC = 1.65f;

    private static final int PERUNIT_METRO_EXPRESS_DELIVERY_COST_BASE = 30;
    private static final int PERUNIT_RURAL_EXPRESS_DELIVERY_COST_BASE = 60;
    private static final int PERUNIT_URBAN_EXPRESS_DELIVERY_COST_BASE = 35;

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
        if (order != null) {
            int total = 0;
            for (OrderItem orderItem : order.cart) {
                int result;
                if (orderItem.quantity >= 6) {
                    int pricePerSix = priceForProduct.get(orderItem.productName).pricePerSix;
                    int pricePerUnitOverSix = priceForProduct.get(orderItem.productName).pricePerUnitOverSix;
                    result = orderItem.quantity % 6 * pricePerUnitOverSix + orderItem.quantity / 6 * pricePerSix;
                    if (!order.isGSTExcempt)
                        result = (int) (result * (1 + GST));
                    if (order.isEmployee)
                        result = (int) (result * (1 - EMPLOYEE_DISCOUNT));
                    if (order.shippingDetails.per == ShippingDetails.Per.ITEM) {
                        if (order.shippingDetails.type == ShippingDetails.Type.NORMAL) {
                            if (order.shippingDetails.area == METRO) {
                                result *= PERUNIT_METRO_STANDARD_DELIVERY_COST_PERC;
                            } else if (order.shippingDetails.area == URBAN) {
                                result *= PERUNIT_URBAN_STANDARD_DELIVERY_COST_PERC;
                            } else {
                                result *= PERUNIT_RURAL_STANDARD_DELIVERY_COST_PERC;
                            }
                        } else {
                            if (order.shippingDetails.area == METRO) {
                                result *= PERUNIT_METRO_EXPRESS_DELIVERY_COST_PERC;
                            } else if (order.shippingDetails.area == URBAN) {
                                result *= PERUNIT_URBAN_EXPRESS_DELIVERY_COST_PERC;
                            } else {
                                result *= PERUNIT_RURAL_EXPRESS_DELIVERY_COST_PERC;
                            }
                        }
                    }
                } else {
                    result = orderItem.quantity * priceForProduct.get(orderItem.productName).pricePerUnit;
                    if (!order.isGSTExcempt)
                        result = (int) (result * (1 + GST));
                    if (order.isEmployee)
                        result = (int) (result * (1 - EMPLOYEE_DISCOUNT));
                    if (order.shippingDetails.per == ShippingDetails.Per.ITEM) {
                        if (order.shippingDetails.type == ShippingDetails.Type.NORMAL) {
                            if (order.shippingDetails.area == METRO) {
                                result *= PERUNIT_METRO_STANDARD_DELIVERY_COST_PERC;
                            } else if (order.shippingDetails.area == URBAN) {
                                result *= PERUNIT_URBAN_STANDARD_DELIVERY_COST_PERC;
                            } else {
                                result *= PERUNIT_RURAL_STANDARD_DELIVERY_COST_PERC;
                            }
                        } else {
                            if (order.shippingDetails.area == METRO) {
                                result *= PERUNIT_METRO_EXPRESS_DELIVERY_COST_PERC;
                            } else if (order.shippingDetails.area == URBAN) {
                                result *= PERUNIT_URBAN_EXPRESS_DELIVERY_COST_PERC;
                            } else {
                                result *= PERUNIT_RURAL_EXPRESS_DELIVERY_COST_PERC;
                            }
                        }
                    }
                }
                total += result;
            }

            if (order.shippingDetails.type != EXPRESS) {
                if (order.shippingDetails.area == METRO) {
                    if (total > METRO_FREE_DELIVERY_MIN_AMOUNT && order.shippingDetails.per == ShippingDetails.Per.TOTAL) {
                        if (order.shippingDetails.per == ShippingDetails.Per.TOTAL)
                            total += NIL_AMOUNT;
                    } else {
                        if (order.shippingDetails.per == ShippingDetails.Per.TOTAL)
                            total += METRO_STANDARD_DELIVERY_COST;
                        else
                            total += this.PERUNIT_METRO_STANDARD_DELIVERY_COST_BASE;
                    }
                } else if (order.shippingDetails.area == URBAN) {
                    if (order.shippingDetails.per == ShippingDetails.Per.TOTAL)
                        total += URBAN_STANDARD_DELIVERY_COST;
                    else
                        total += PERUNIT_URBAN_STANDARD_DELIVERY_COST_BASE;
                } else {
                    if (order.shippingDetails.per == ShippingDetails.Per.TOTAL)
                        total += RURAL_STANDARD_DELIVERY_COST;
                    else
                        total += PERUNIT_RURAL_STANDARD_DELIVERY_COST_BASE;
                }
            } else {
                if ((order.shippingDetails.area == METRO || order.shippingDetails.area == URBAN) && order.shippingDetails.per == ShippingDetails.Per.TOTAL)
                    total += METRO_URBAN_EXPRESS_DELIVERY_COST;
                else if (order.shippingDetails.per == ShippingDetails.Per.TOTAL)
                    throw new RuntimeException("Cannot Deliver Express to Rural Areas");
                else {
                    if (order.shippingDetails.area == METRO)
                        total += PERUNIT_METRO_EXPRESS_DELIVERY_COST_BASE;
                    else if (order.shippingDetails.area == URBAN)
                        total += PERUNIT_URBAN_EXPRESS_DELIVERY_COST_BASE;
                    else
                        total += PERUNIT_RURAL_EXPRESS_DELIVERY_COST_BASE;
                }
            }

            return total;
        }
        return 0;
    }

}