package com.cleancode.main.indentationlevels;

import java.util.Collection;

/**
* Created by mwittman on 25/03/2014.
*/
public class Order {
    final public ShippingDetails shippingDetails;
    final public boolean isEmployee;
    final public boolean isGSTExcempt;
    final public Collection<OrderItem> cart;

    public Order(
            ShippingDetails shippingDetails,
            boolean isEmployee,
            boolean isGSTExcempt,
            Collection <OrderItem> cart
    ) {
        this.shippingDetails = shippingDetails;
        this.isEmployee = isEmployee;
        this.isGSTExcempt = isGSTExcempt;
        this.cart = cart;
    }

}
