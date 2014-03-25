package com.cleancode.main.indentationlevels;

import com.cleancode.main.indentationlevels.implementations.PriceCalculator_Bad;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class PriceCalculatorTest_Bad {

    PriceCalculator indentationLevels;

    @Before
    public void setup() {
        Map<String, ProductPrices> priceForProduct = new HashMap<>();
        priceForProduct.put("prodA", new ProductPrices(3, 12, 2));

        Map<String, Float> discountForProduct = new HashMap<>();
        discountForProduct.put("prodA", 0.5f);

        Map<String, Integer> minPurchaseForDiscount = new HashMap<>();
        minPurchaseForDiscount.put("prodA", 12);

        this.indentationLevels = new PriceCalculator_Bad(priceForProduct, discountForProduct, minPurchaseForDiscount);
    }

    //1 x 3 = 3 Base
    //45 Urban
    //48
    @Test
    public void shouldCalculate_NoGST_NoDiscount_1_SingleBottle_Price() {
        List<OrderItem> cart = new ArrayList<OrderItem>();
        cart.add(new OrderItem("prodA", 1));
        ShippingDetails shippingDetails = new ShippingDetails(ShippingDetails.Area.URBAN, ShippingDetails.Type.NORMAL);
        Order order = new Order(shippingDetails, false, true, cart);
        assertThat(indentationLevels.calculateTotalPrice(order), is(48));
    }

    @Test
    public void shouldCalculate_NoGST_NoDiscount_2_SingleBottles_Price() {
        List<OrderItem> cart = new ArrayList<OrderItem>();
        cart.add(new OrderItem("prodA", 2));
        ShippingDetails shippingDetails = new ShippingDetails(ShippingDetails.Area.URBAN, ShippingDetails.Type.NORMAL);
        Order order = new Order(shippingDetails, false, true, cart);
        assertThat(indentationLevels.calculateTotalPrice(order), is(51));
    }

    @Test
    public void shouldChargeCorrectForNoGSTNoDiscounts_6_SixBottlePrice() {
        List<OrderItem> cart = new ArrayList<OrderItem>();
        cart.add(new OrderItem("prodA", 6));
        ShippingDetails shippingDetails = new ShippingDetails(ShippingDetails.Area.URBAN, ShippingDetails.Type.NORMAL);
        Order order = new Order(shippingDetails, false, true, cart);
        assertThat(indentationLevels.calculateTotalPrice(order), is(57));
    }

    @Test
    public void shouldChargeCorrectForNoGSTNoDiscounts_7_SixBottlePrice() {
        List<OrderItem> cart = new ArrayList<OrderItem>();
        cart.add(new OrderItem("prodA", 7));
        ShippingDetails shippingDetails = new ShippingDetails(ShippingDetails.Area.URBAN, ShippingDetails.Type.NORMAL);
        Order order = new Order(shippingDetails, false, true, cart);
        assertThat(indentationLevels.calculateTotalPrice(order), is(59));
    }

    @Test
    public void shouldChargeCorrectForNoGST_EmployeeDiscount_6_SixBottlePrice() {
        List<OrderItem> cart = new ArrayList<OrderItem>();
        cart.add(new OrderItem("prodA", 6));
        ShippingDetails shippingDetails = new ShippingDetails(ShippingDetails.Area.URBAN, ShippingDetails.Type.NORMAL);
        Order order = new Order(shippingDetails, true, true, cart);
        assertThat(indentationLevels.calculateTotalPrice(order), is(54));
    }

    @Test
    public void shouldChargeCorrectForNoGST_GSTAdded_6_SixBottlePrice() {
        List<OrderItem> cart = new ArrayList<OrderItem>();
        cart.add(new OrderItem("prodA", 6));
        ShippingDetails shippingDetails = new ShippingDetails(ShippingDetails.Area.URBAN, ShippingDetails.Type.NORMAL);
        Order order = new Order(shippingDetails, false, false, cart);
        assertThat(indentationLevels.calculateTotalPrice(order), is(58));
    }

    @Test
    public void shouldChargeCorrectForNoGSTNoDiscounts_6_SixBottlePrice_ExpressDelivery() {
        List<OrderItem> cart = new ArrayList<OrderItem>();
        cart.add(new OrderItem("prodA", 6));
        ShippingDetails shippingDetails = new ShippingDetails(ShippingDetails.Area.URBAN, ShippingDetails.Type.EXPRESS);
        Order order = new Order(shippingDetails, false, true, cart);
        assertThat(indentationLevels.calculateTotalPrice(order), is(87));
    }
}
